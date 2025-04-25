package com.vtx.jobscheduler.entity;

import static com.vtx.jobscheduler.constants.Constants.UTC_TIME_ZONE;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.TimeZone;

import jakarta.persistence.Column;
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
    @Column(name = "id")
    private T id;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @PrePersist
    protected void onCreate() {
        TimeZone timeZone = TimeZone.getTimeZone(UTC_TIME_ZONE);
        this.createdAt = Timestamp.from(Instant.now().atZone(timeZone.toZoneId()).toInstant());
        // ToDo:: use context holder to update the userId createdBy
        this.createdBy = "system"; // placeholder for current user
    }

    @PreUpdate
    protected void onUpdate() {
        TimeZone timeZone = TimeZone.getTimeZone(UTC_TIME_ZONE);
        this.updatedAt = Timestamp.from(Instant.now().atZone(timeZone.toZoneId()).toInstant());
        // ToDo:: use context holder to update the userId updatedBy
        this.updatedBy = "system"; // placeholder for currentuser
    }

}
