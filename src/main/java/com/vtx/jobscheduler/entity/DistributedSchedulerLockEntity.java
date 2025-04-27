package com.vtx.jobscheduler.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.ZonedDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity(name = "distributed_scheduler_lock")
public class DistributedSchedulerLockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lock_key")
    private String lockKey;

    @Column(name = "locked")
    private boolean locked;

    @Column(name = "lock_time")
    private ZonedDateTime lockTime;

    @Column(name = "expiry_time")
    private ZonedDateTime expiryTime;

    @Column(name = "last_heartbeat_time")
    private ZonedDateTime lastHeartbeatTime;
}
