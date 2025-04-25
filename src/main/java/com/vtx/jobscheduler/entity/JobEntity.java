package com.vtx.jobscheduler.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vtx.jobscheduler.enums.JobStatusEnum;
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

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private JobStatusEnum status;

    @Enumerated(EnumType.STRING)
    @Column(name = "schedule_type", nullable = false)
    private ScheduleTypeEnum scheduleType;

    @Column(name = "cron_expression", nullable = false)
    private String cronExpression;

    @Column(name = "fixed_rate_ms", nullable = false)
    private Long fixedRateInMilliseconds;

    @Column(name = "retry_policy", nullable = false)
    private String retryPolicy;

    @JdbcTypeCode(SqlTypes.JSON)
    private JobDetailsPayload payload;

    @Column(name = "last_run_at")
    private ZonedDateTime lastRunAt;

    @Column(name = "next_run_at")
    private ZonedDateTime nextRunAt;

    @Version
    private Integer version; // This field is used for optimistic locking

}
