package com.vtx.jobscheduler.entity;

import static com.vtx.jobscheduler.constants.Constants.SYSTEM_USER;
import static com.vtx.jobscheduler.constants.Constants.UTC_TIME_ZONE;

import java.time.ZonedDateTime;
import java.util.TimeZone;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity<T> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private T id;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        TimeZone timeZone = TimeZone.getTimeZone(UTC_TIME_ZONE);
        this.createdAt = ZonedDateTime.now();
        this.createdBy = SYSTEM_USER; // TODO: replace with actual user
    }

    @PreUpdate
    protected void onUpdate() {
        TimeZone timeZone = TimeZone.getTimeZone(UTC_TIME_ZONE);
        this.updatedAt = ZonedDateTime.now();
        this.updatedBy = SYSTEM_USER; // TODO: replace with actual user

    }

}
