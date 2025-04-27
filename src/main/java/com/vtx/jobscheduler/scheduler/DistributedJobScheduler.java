package com.vtx.jobscheduler.scheduler;

import static com.vtx.jobscheduler.constants.Constants.DISTRIBUTED_JOB_SCHEDULER_LOCK;
import com.vtx.jobscheduler.entity.JobEntity;
import com.vtx.jobscheduler.enums.JobStatusEnum;
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
            initialDelayString = "${job.scheduler.initialDelay:5000}")
    public void processJobs() {
        log.info("Start --> DistributedJobSchedulerService started at: {} ", System.currentTimeMillis());

        try {
                List<String> statuses = List.of(JobStatusEnum.CREATED.getValue(), JobStatusEnum.SCHEDULED.getValue());
                List<JobEntity> jobsToProcess = jobService.findJobsToProcess(statuses, ZonedDateTime.now());
                if (jobsToProcess.isEmpty()) {
                    log.warn("No jobs to process");
                    return;
                }
                log.info("Found {} jobs to process", jobsToProcess.size());
                for (JobEntity job: jobsToProcess) {
                    String lockName = DISTRIBUTED_JOB_SCHEDULER_LOCK + job.getName();
                    if (distributedSchedulerLockService.tryAcquireLock(
                            DISTRIBUTED_JOB_SCHEDULER_LOCK + job.getName())) {
                        log.info(lockName + " Acquired lock for job: {}", job.getName());
                        processJob(job);
                    } else {
                        log.info(lockName + " Another instance is already processing job: {}", job.getName());
                    }
                    distributedSchedulerLockService.releaseLock(lockName);
                }
            MDC.clear();
            } catch (Exception e) {
            log.error("DistributedJobSchedulerService Error occurred while processing jobs: {}", e.getMessage(), e);
        }
        log.info("End --> DistributedJobSchedulerService end at: {} ", System.currentTimeMillis());
    }

    private void processJob(JobEntity job) {
        try {
            log.info("Processing job: {}", job.getName());
            executeJob(job);
        } catch (Exception e) {
            log.error("Error occurred while processing job {}: {}", job.getName(), e.getMessage(), e);
        }
    }

    private void executeJob(JobEntity job) {
        try {
            jobExecutorFactory.getExecutor(job.getName()).execute(job);
            updateJobAfterExecution(job, JobStatusEnum.SCHEDULED);
        } catch (Exception e) {
            log.error("Error occurred while executing job {}: {}", job.getName(), e.getMessage(), e);
            handleFailedJobExecution(job);
        }
    }

    private void updateJobAfterExecution(JobEntity job, JobStatusEnum status) {
        job.setStatus(status);
        job.setLastRunAt(ZonedDateTime.now());
        job.setNextRunAt(jobService.calculateNextRun(job.getScheduleType(), job.getCronExpression(),
                job.getFixedRateInMilliseconds()));
        jobService.updateJob(job);
        log.info("Job {} successfully updated with status {}", job.getName(), status);
    }


    private void handleFailedJobExecution(JobEntity job) {
        job.setStatus(JobStatusEnum.FAILED);
        job.setLastRunAt(ZonedDateTime.now());
        job.setNextRunAt(jobService.calculateNextRun(job.getScheduleType(), job.getCronExpression(),
                job.getFixedRateInMilliseconds()));
        jobService.updateJob(job);
        log.info("Job {} failed, status updated to FAILED", job.getName());
    }
}
