package com.vtx.jobscheduler.mapper;

import com.vtx.jobscheduler.entity.JobEntity;
import com.vtx.jobscheduler.enums.JobStatusEnum;
import com.vtx.jobscheduler.model.JobPatchRequestContract;
import com.vtx.jobscheduler.model.JobRequestContract;
import com.vtx.jobscheduler.model.JobResponseContract;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class JobContractMapper {

    public JobEntity translateToJobEntity(JobRequestContract jobRequestContract) {
        JobEntity jobEntity = new JobEntity();
        jobEntity.setName(jobRequestContract.getName());
        jobEntity.setScheduleType(jobRequestContract.getScheduleType());
        jobEntity.setCronExpression(jobRequestContract.getCronExpression());
        jobEntity.setFixedRateInMilliseconds(jobRequestContract.getFixedRateInMilliSeconds());
        jobEntity.setRetryPolicy(jobRequestContract.getRetryPolicy());
        jobEntity.setMaxRetries(jobRequestContract.getMaxRetries());
        jobEntity.setRetryDelayInSeconds(jobRequestContract.getRetryDelayInSeconds());
        jobEntity.setExponentialBase(jobRequestContract.getExponentialBase());
        jobEntity.setExponentialInitialDelayInSeconds(jobRequestContract.getExponentialInitialDelayInSeconds());
        jobEntity.setRetriesAttempted(jobRequestContract.getRetriesAttempted());
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
        jobResponseContract.setRetryPolicy(jobEntity.getRetryPolicy());
        jobResponseContract.setMaxRetries(jobEntity.getMaxRetries());
        jobResponseContract.setRetryDelayInSeconds(jobEntity.getRetryDelayInSeconds());
        jobResponseContract.setExponentialBase(jobEntity.getExponentialBase());
        jobResponseContract.setExponentialInitialDelayInSeconds(jobEntity.getExponentialInitialDelayInSeconds());
        jobResponseContract.setRetriesAttempted(jobEntity.getRetriesAttempted());
        jobResponseContract.setPayload(jobEntity.getPayload());
        jobResponseContract.setLastRunAt(jobEntity.getLastRunAt());
        jobResponseContract.setNextRunAt(jobEntity.getNextRunAt());
        jobResponseContract.setCreatedAt(jobEntity.getCreatedAt());
        jobResponseContract.setCreatedBy(jobEntity.getCreatedBy());
        jobResponseContract.setUpdatedAt(jobEntity.getUpdatedAt());
        jobResponseContract.setUpdatedBy(jobEntity.getUpdatedBy());
        return jobResponseContract;
    }

    public void mapPatchRequestToExistingJobEntity(JobEntity existingJobEntity,JobPatchRequestContract patchRequest) {

        if (patchRequest.getStatus() == null) {
            throw new IllegalArgumentException("status must be provided to patch a job");
        }
        existingJobEntity.setStatus(patchRequest.getStatus());

        if (patchRequest.getScheduleType() != null) {
            existingJobEntity.setScheduleType(patchRequest.getScheduleType());
        }
        if (patchRequest.getCronExpression() != null) {
            existingJobEntity.setCronExpression(patchRequest.getCronExpression());
        }
        if (patchRequest.getFixedRateInMilliSeconds() != null) {
            existingJobEntity.setFixedRateInMilliseconds(patchRequest.getFixedRateInMilliSeconds());
        }
        if (patchRequest.getRetryPolicy() != null) {
            existingJobEntity.setRetryPolicy(patchRequest.getRetryPolicy());
        }
        if (patchRequest.getMaxRetries() != null) {
            existingJobEntity.setMaxRetries(patchRequest.getMaxRetries());
        }
        if (patchRequest.getRetryDelayInSeconds() != null) {
            existingJobEntity.setRetryDelayInSeconds(patchRequest.getRetryDelayInSeconds());
        }
        if (patchRequest.getExponentialBase() != null) {
            existingJobEntity.setExponentialBase(patchRequest.getExponentialBase());
        }
        if (patchRequest.getExponentialInitialDelayInSeconds() != null) {
            existingJobEntity.setExponentialInitialDelayInSeconds(patchRequest.getExponentialInitialDelayInSeconds());
        }
        if (patchRequest.getRetriesAttempted() != null) {
            existingJobEntity.setRetriesAttempted(patchRequest.getRetriesAttempted());
        }
        if (patchRequest.getPayload() != null) {
            existingJobEntity.setPayload(patchRequest.getPayload());
        }
        existingJobEntity.setUpdatedAt(ZonedDateTime.now());
        existingJobEntity.setUpdatedBy(existingJobEntity.getUpdatedBy());
    }
}
