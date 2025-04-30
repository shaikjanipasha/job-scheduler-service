package com.vtx.jobscheduler.model;

import com.vtx.jobscheduler.annotation.ValidJobContract;
import com.vtx.jobscheduler.enums.JobStatusEnum;
import com.vtx.jobscheduler.enums.RetryPolicyEnum;
import com.vtx.jobscheduler.enums.ScheduleTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobPatchRequestContract {

    private String name;
    private JobStatusEnum status;
    private ScheduleTypeEnum scheduleType;
    private String cronExpression;
    private Long fixedRateInMilliSeconds;
    private RetryPolicyEnum retryPolicy;
    private JobDetailsPayload payload;
    private Integer maxRetries;
    private Integer retryDelayInSeconds;
    private Integer exponentialBase;
    private Integer exponentialInitialDelayInSeconds;
}
