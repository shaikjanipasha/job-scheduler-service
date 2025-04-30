package com.vtx.jobscheduler.scheduler;


import static com.vtx.jobscheduler.constants.Constants.DISTRIBUTED_JOB_SCHEDULER_LOCK;
import static com.vtx.jobscheduler.constants.Constants.EXPONENTIAL_BASE_DEFAULT;
import static com.vtx.jobscheduler.constants.Constants.EXPONENTIAL_INITIAL_DELAY_IN_SECONDS_DEFAULT;
import static com.vtx.jobscheduler.constants.Constants.RETRY_DELAY_IN_SECONDS_DEFAULT;

import com.vtx.jobscheduler.entity.JobEntity;
import com.vtx.jobscheduler.enums.JobStatusEnum;
import com.vtx.jobscheduler.enums.RetryPolicyEnum;
import com.vtx.jobscheduler.factory.JobExecutorFactory;
import com.vtx.jobscheduler.service.DistributedSchedulerLockService;
import com.vtx.jobscheduler.service.JobService;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
@Service
@Slf4j
@RequiredArgsConstructor
public class DistributedJobScheduler {

    private final JobService jobService;

    private final JobExecutorFactory jobExecutorFactory;

    private final DistributedSchedulerLockService distributedSchedulerLockService;

    @Scheduled(fixedRateString = "${scheduler.fixedRate:6000}",
            initialDelayString = "${scheduler.initialDelay:5000}")
    public void processJobs() {
        log.info("Start --> DistributedJobSchedulerService started at: {} ", System.currentTimeMillis());

        try {
                List<String> statuses = List.of(JobStatusEnum.CREATED.getValue(), JobStatusEnum.SCHEDULED.getValue(),
                        JobStatusEnum.RESUMED.getValue());
                List<JobEntity> jobsToProcess = jobService.findJobsToProcess(statuses, ZonedDateTime.now());
                if (jobsToProcess.isEmpty()) {
                    log.warn("No jobs to process");
                    return;
                }
                log.info("Found {} jobs to process", jobsToProcess.size());
                for (JobEntity job: jobsToProcess) {
                    String lockName = DISTRIBUTED_JOB_SCHEDULER_LOCK + job.getName();
                    boolean lockAcquired = distributedSchedulerLockService.tryAcquireLock(lockName);
                    if (lockAcquired) {
                        try {
                            MDC.put("jobId", job.getId().toString());
                            MDC.put("jobName", job.getName());
                            log.info("Executing job: {}", job.getName());
                            jobExecutorFactory.getExecutor(job.getName()).execute(job);

                            job.setStatus(JobStatusEnum.SCHEDULED);
                            job.setNextRunAt(jobService.computeNextRunForJob(job.getScheduleType(),
                                    job.getCronExpression(), job.getFixedRateInMilliseconds()));
                            job.setRetriesAttempted(0);
                        } catch (Exception e) {
                            log.error("Error occurred while processing job {}: {}", job.getName(), e.getMessage(), e);
                            handleRetryJobExecution(job);
                        } finally {
                            job.setLastRunAt(ZonedDateTime.now());
                            jobService.updateJob(job);
                            distributedSchedulerLockService.releaseLock(lockName);
                            MDC.clear();
                        }
                    } else {
                        log.info(lockName + " Another instance is already processing job: {}", job.getName());
                    }
                }
            } catch (Exception e) {
            log.error("DistributedJobSchedulerService Error occurred while processing jobs: {}", e.getMessage(), e);
        }
        log.info("End --> DistributedJobSchedulerService end at: {} ", System.currentTimeMillis());
    }

    private void handleRetryJobExecution(JobEntity job) {
        if (job.getMaxRetries() != null && job.getRetriesAttempted() != null &&
                job.getRetriesAttempted() < job.getMaxRetries()) {

            int retries = job.getRetriesAttempted() + 1;
            job.setRetriesAttempted(retries);
            ZonedDateTime nextRetryAt = ZonedDateTime.now();

            if (job.getRetryPolicy() == RetryPolicyEnum.FIXED) {
                int delaySeconds = job.getRetryDelayInSeconds() != null ? job.getRetryDelayInSeconds() :
                        RETRY_DELAY_IN_SECONDS_DEFAULT;
                nextRetryAt = nextRetryAt.plusSeconds(delaySeconds);

            } else if (job.getRetryPolicy() == RetryPolicyEnum.EXPONENTIAL) {
                int base = job.getExponentialBase() != null ? job.getExponentialBase() : EXPONENTIAL_BASE_DEFAULT;
                int initialDelay = job.getExponentialInitialDelayInSeconds() != null
                        ? job.getExponentialInitialDelayInSeconds() : EXPONENTIAL_INITIAL_DELAY_IN_SECONDS_DEFAULT;
                long delay = (long) (initialDelay * Math.pow(base, retries - 1));
                nextRetryAt = nextRetryAt.plusSeconds(delay);

            }

            job.setStatus(JobStatusEnum.SCHEDULED);
            job.setNextRunAt(nextRetryAt);
        } else {
            log.warn("Max retries exceeded for job: {}", job.getName());
            job.setStatus(JobStatusEnum.FAILED);
        }
    }
}
