package com.vtx.jobscheduler.mapper;

import com.vtx.jobscheduler.entity.JobEntity;
import com.vtx.jobscheduler.enums.JobStatusEnum;
import com.vtx.jobscheduler.model.JobPatchRequestContract;
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

    public JobPatchRequestContract mapMergePatchWithExistingJobEntity(JobEntity existingJobEntity,
                                                                       JobPatchRequestContract patchRequest) {
        // Note: this mapping is needed to perfom the valiadation on mergedPatchRequest before saving.
        JobPatchRequestContract mergedPatchRequest = new JobPatchRequestContract();

        mergedPatchRequest.setName(patchRequest.getName() != null
                ? patchRequest.getName() : existingJobEntity.getName());

        mergedPatchRequest.setStatus(patchRequest.getStatus() != null
                ? patchRequest.getStatus() : existingJobEntity.getStatus());

        mergedPatchRequest.setScheduleType(patchRequest.getScheduleType() != null
                ? patchRequest.getScheduleType() : existingJobEntity.getScheduleType());

        mergedPatchRequest.setCronExpression(patchRequest.getCronExpression() != null
                ? patchRequest.getCronExpression() : existingJobEntity.getCronExpression());

        mergedPatchRequest.setFixedRateInMilliSeconds(patchRequest.getFixedRateInMilliSeconds() != null
                ? patchRequest.getFixedRateInMilliSeconds() : existingJobEntity.getFixedRateInMilliseconds());

        mergedPatchRequest.setRetryPolicy(patchRequest.getRetryPolicy() != null
                ? patchRequest.getRetryPolicy() : existingJobEntity.getRetryPolicy());

        mergedPatchRequest.setMaxRetries(patchRequest.getMaxRetries() != null
                ? patchRequest.getMaxRetries() : existingJobEntity.getMaxRetries());

        mergedPatchRequest.setRetryDelayInSeconds(patchRequest.getRetryDelayInSeconds() != null
                ? patchRequest.getRetryDelayInSeconds() : existingJobEntity.getRetryDelayInSeconds());

        mergedPatchRequest.setExponentialBase(patchRequest.getExponentialBase() != null
                ? patchRequest.getExponentialBase() : existingJobEntity.getExponentialBase());

        mergedPatchRequest.setExponentialInitialDelayInSeconds(patchRequest.getExponentialInitialDelayInSeconds() != null
                ? patchRequest.getExponentialInitialDelayInSeconds() : existingJobEntity.getExponentialInitialDelayInSeconds());

        mergedPatchRequest.setPayload(patchRequest.getPayload() != null
                ? patchRequest.getPayload() : existingJobEntity.getPayload());

        return mergedPatchRequest;
    }

    public void mapAndApplyPatchToExistingJobEntity(JobPatchRequestContract mergedPatchRequest,
                                                    JobEntity existingEntity) {
        if (mergedPatchRequest.getName() != null) {
            existingEntity.setName(mergedPatchRequest.getName());
        }

        if (mergedPatchRequest.getStatus() != null) {
            existingEntity.setStatus(mergedPatchRequest.getStatus());
        }

        if (mergedPatchRequest.getScheduleType() != null) {
            existingEntity.setScheduleType(mergedPatchRequest.getScheduleType());
        }

        if (mergedPatchRequest.getCronExpression() != null) {
            existingEntity.setCronExpression(mergedPatchRequest.getCronExpression());
        }
        if (mergedPatchRequest.getFixedRateInMilliSeconds() != null) {
            existingEntity.setFixedRateInMilliseconds(mergedPatchRequest.getFixedRateInMilliSeconds());
        }
        if (mergedPatchRequest.getRetryPolicy() != null) {
            existingEntity.setRetryPolicy(mergedPatchRequest.getRetryPolicy());
        }
        if (mergedPatchRequest.getMaxRetries() != null) {
            existingEntity.setMaxRetries(mergedPatchRequest.getMaxRetries());
        }
        if (mergedPatchRequest.getRetryDelayInSeconds() != null) {
            existingEntity.setRetryDelayInSeconds(mergedPatchRequest.getRetryDelayInSeconds());
        }
        if (mergedPatchRequest.getExponentialBase() != null) {
            existingEntity.setExponentialBase(mergedPatchRequest.getExponentialBase());
        }
        if (mergedPatchRequest.getExponentialInitialDelayInSeconds() != null) {
            existingEntity.setExponentialInitialDelayInSeconds(mergedPatchRequest.getExponentialInitialDelayInSeconds());
        }
        if (mergedPatchRequest.getPayload() != null) {
            existingEntity.setPayload(mergedPatchRequest.getPayload());
        }
        if (mergedPatchRequest.getStatus() != null) {
            existingEntity.setStatus(mergedPatchRequest.getStatus());
        }
    }

}
