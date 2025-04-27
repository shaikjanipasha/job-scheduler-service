package com.vtx.jobscheduler.service.impl;

import com.vtx.jobscheduler.JobContractMapper;
import com.vtx.jobscheduler.entity.JobEntity;
import com.vtx.jobscheduler.enums.ScheduleTypeEnum;
import com.vtx.jobscheduler.model.JobRequestContract;
import com.vtx.jobscheduler.model.JobResponseContract;
import com.vtx.jobscheduler.repository.JobRepository;
import com.vtx.jobscheduler.service.JobService;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Service;

import static com.vtx.jobscheduler.constants.Constants.SYSTEM_USER;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    private final JobContractMapper jobContractMapper;

    @Override
    public JobResponseContract createJob(JobRequestContract jobRequestContract) {
        Optional<JobEntity> optJobEntity = jobRepository.findByName(jobRequestContract.getName());
        if (optJobEntity.isPresent()) {
            // Note:: Idempotency handled
            // Even though POST call triggered many times always returns the same response for same job name
            return jobContractMapper.translateToJobResponseContract(optJobEntity.get());
        }

        ZonedDateTime nextRunAt = calculateNextRun(jobRequestContract.getScheduleType(),
                jobRequestContract.getCronExpression(), jobRequestContract.getFixedRateInMilliSeconds());
        if (nextRunAt == null) {
            throw new RuntimeException("Invalid schedule type or cron expression");
        }
        JobEntity jobEntity = jobContractMapper.translateToJobEntity(jobRequestContract);
        jobEntity.setNextRunAt(nextRunAt);
        jobEntity.setCreatedAt(ZonedDateTime.now());
        jobEntity.setUpdatedAt(ZonedDateTime.now());
        jobEntity.setCreatedBy(SYSTEM_USER); // TODO: replace with actual user
        jobEntity.setUpdatedBy(SYSTEM_USER); // TODO: replace with actual user

        JobEntity savedJobEntity = jobRepository.save(jobEntity);
        return jobContractMapper.translateToJobResponseContract(savedJobEntity);
    }

    @Override
    public JobResponseContract getJobByName(String jobName) {
        JobEntity jobEntity = jobRepository.findByName(jobName)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        return jobContractMapper.translateToJobResponseContract(jobEntity);
    }

    @Override
    public JobResponseContract getJobById(Long jobId) {
        JobEntity jobEntity = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        return jobContractMapper.translateToJobResponseContract(jobEntity);
    }

    @Override
    public List<JobEntity> findJobsToProcess(List<String> statuses, ZonedDateTime currentTime) {
        return jobRepository.findJobsToProcess(statuses, currentTime);
    }

    @Override
    @Transactional
    public void updateJob(JobEntity jobEntity) {
        jobEntity.setUpdatedAt(ZonedDateTime.now());
        jobEntity.setUpdatedBy(SYSTEM_USER); // TODO: replace with actual user
        jobRepository.save(jobEntity);
    }

    @Override
    public ZonedDateTime calculateNextRun(ScheduleTypeEnum scheduleType, String cronExpression,
                                          Long fixedRateInMilliSeconds) {
        if (scheduleType == ScheduleTypeEnum.CRON) {
            CronExpression cronExpressionResult = CronExpression.parse(cronExpression);
            ZonedDateTime now = ZonedDateTime.now();
            return cronExpressionResult.next(now);
        } else if (scheduleType == ScheduleTypeEnum.FIXED_RATE) {
            return ZonedDateTime.now().plus(Duration.ofMillis(fixedRateInMilliSeconds));
        }
        return null;
    }

}
