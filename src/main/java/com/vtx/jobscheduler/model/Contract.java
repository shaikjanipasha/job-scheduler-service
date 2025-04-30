package com.vtx.jobscheduler.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vtx.jobscheduler.enums.JobStatusEnum;
import com.vtx.jobscheduler.enums.RetryPolicyEnum;
import com.vtx.jobscheduler.enums.ScheduleTypeEnum;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Contract {
    @NotNull(message = "name cannot be null")
    private String name;

    private JobStatusEnum status = JobStatusEnum.CREATED;

    @NotNull(message = "scheduleType cannot be null")
    private ScheduleTypeEnum scheduleType;

    private String cronExpression;
    private Long fixedRateInMilliSeconds;

    private RetryPolicyEnum retryPolicy = RetryPolicyEnum.NONE;

    private JobDetailsPayload payload;

    private Integer maxRetries = 3;

    private Integer retryDelayInSeconds = 3;

    private Integer exponentialBase = 2;

    private Integer exponentialInitialDelayInSeconds = 3;

}
