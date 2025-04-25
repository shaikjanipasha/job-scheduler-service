package com.vtx.jobscheduler;

import com.vtx.jobscheduler.entity.JobEntity;
import com.vtx.jobscheduler.enums.JobStatusEnum;
import com.vtx.jobscheduler.model.JobRequestContract;
import com.vtx.jobscheduler.model.JobResponseContract;
import org.springframework.stereotype.Component;

@Component
public class JobContractMapper {

    public JobEntity translateToJobEntity(JobRequestContract jobRequestContract) {
        JobEntity jobEntity = new JobEntity();
        jobEntity.setName(jobRequestContract.getName());
        jobEntity.setScheduleType(jobRequestContract.getScheduleType());
        jobEntity.setCronExpression(jobRequestContract.getCronExpression());
        jobEntity.setFixedRateInMilliseconds(jobRequestContract.getFixedRateInMilliSeconds());
        jobEntity.setRetryPolicy(jobRequestContract.getRetryPolicy());
        jobEntity.setPayload(jobRequestContract.getPayload());
        if (null != jobRequestContract.getStatus()) {
            jobEntity.setStatus(jobRequestContract.getStatus());
        } else {
            jobEntity.setStatus(JobStatusEnum.CREATED);
        }
        return jobEntity;
    }

    public JobResponseContract translateToJobResponseContract(JobEntity jobEntity) {
        JobResponseContract jobResponseContract = new JobResponseContract();
        jobResponseContract.setId(jobEntity.getId());
        jobResponseContract.setName(jobEntity.getName());
        jobResponseContract.setScheduleType(jobEntity.getScheduleType());
        jobResponseContract.setStatus(jobEntity.getStatus());
        jobResponseContract.setCronExpression(jobEntity.getCronExpression());
        jobResponseContract.setFixedRateInMilliSeconds(jobEntity.getFixedRateInMilliseconds());
        jobResponseContract.setPayload(jobEntity.getPayload());
        jobResponseContract.setLastRunAt(jobEntity.getLastRunAt());
        jobResponseContract.setNextRunAt(jobEntity.getNextRunAt());
        jobResponseContract.setCreatedAt(jobEntity.getCreatedAt());
        jobResponseContract.setCreatedBy(jobEntity.getCreatedBy());
        jobResponseContract.setUpdatedAt(jobEntity.getUpdatedAt());
        jobResponseContract.setUpdatedBy(jobEntity.getUpdatedBy());
        return jobResponseContract;
    }
}
