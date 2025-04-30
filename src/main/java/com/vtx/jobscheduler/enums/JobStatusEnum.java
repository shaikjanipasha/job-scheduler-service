package com.vtx.jobscheduler.enums;

public enum JobStatusEnum {
    CREATED("CREATED", "Job is created"),
    SCHEDULED("SCHEDULED", "Job is scheduled"),
    RUNNING("RUNNING", "Job is running"),
    RETRYING("RETRYING", "Job is retrying"),
    FAILED("FAILED", "Job has failed"),
    COMPLETED("COMPLETED", "Job has completed"),
    PAUSED("PAUSED", "Job is paused"),
    RESUMED("RESUMED", "Job has resumed"),
    CANCELLED("CANCELLED", "Job has been cancelled"),
    ERROR("ERROR", "Job has encountered an error");

    private final String value;
    private final String description;

    JobStatusEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }
    public String getValue() {
        return this.value;
    }

    public String getDescription() {
        return this.description;
    }

    public static JobStatusEnum fromValue(String value) {
        for (JobStatusEnum status : JobStatusEnum.values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown job status: " + value);
    }
}
