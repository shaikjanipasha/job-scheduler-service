package com.vtx.jobscheduler.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vtx.jobscheduler.enums.JobStatusEnum;
import com.vtx.jobscheduler.enums.RetryPolicyEnum;
import com.vtx.jobscheduler.enums.ScheduleTypeEnum;
import com.vtx.jobscheduler.model.JobDetailsPayload;
import java.time.ZonedDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity(name = "job")
public class JobEntity extends BaseEntity<Long> {

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private JobStatusEnum status;

    @Enumerated(EnumType.STRING)
    @Column(name = "schedule_type")
    private ScheduleTypeEnum scheduleType;

    @Column(name = "cron_expression")
    private String cronExpression;

    @Column(name = "fixed_rate_ms")
    private Long fixedRateInMilliseconds;

    @Enumerated(EnumType.STRING)
    @Column(name = "retry_policy")
    private RetryPolicyEnum retryPolicy;

    @JdbcTypeCode(SqlTypes.JSON)
    private JobDetailsPayload payload;

    @Column(name = "last_run_at")
    private ZonedDateTime lastRunAt;

    @Column(name = "next_run_at")
    private ZonedDateTime nextRunAt;

    @Column(name = "max_retries")
    private Integer maxRetries;

    @Column(name = "retry_delay_in_seconds")
    private Integer retryDelayInSeconds;

    @Column(name = "exponential_base")
    private Integer exponentialBase;

    @Column(name = "exponential_initial_delay")
    private Integer exponentialInitialDelayInSeconds;

    @Column(name = "retries_attempted")
    private Integer retriesAttempted;

    @Version
    private Integer version; // This field is used for optimistic locking

}
