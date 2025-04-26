package com.vtx.jobscheduler.service.impl;

import com.vtx.jobscheduler.JobContractMapper;
import com.vtx.jobscheduler.entity.JobEntity;
import com.vtx.jobscheduler.enums.ScheduleTypeEnum;
import com.vtx.jobscheduler.model.JobRequestContract;
import com.vtx.jobscheduler.model.JobResponseContract;
import com.vtx.jobscheduler.repository.JobRepository;
import com.vtx.jobscheduler.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Optional;

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

        ZonedDateTime nextRunAt = null;
        if (jobRequestContract.getScheduleType() == ScheduleTypeEnum.CRON) {
            nextRunAt = calculateNextRun(jobRequestContract.getCronExpression());
        } else if (jobRequestContract.getScheduleType() == ScheduleTypeEnum.FIXED_RATE) {
            nextRunAt = ZonedDateTime.now().plus(Duration.ofMillis(jobRequestContract.getFixedRateInMilliSeconds()));
        }
        JobEntity jobEntity = jobContractMapper.translateToJobEntity(jobRequestContract);
        jobEntity.setNextRunAt(nextRunAt);
        jobEntity.setCreatedAt(ZonedDateTime.now());
        jobEntity.setUpdatedAt(ZonedDateTime.now());
        jobEntity.setCreatedBy("system"); // TODO: replace with actual user
        jobEntity.setUpdatedBy("system"); // TODO: replace with actual user

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

    private ZonedDateTime calculateNextRun(String cronExpr) {
        CronExpression cronExpression = CronExpression.parse(cronExpr);
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime nextRunAt = cronExpression.next(now);
        return nextRunAt;
    }

}
