package com.vtx.jobscheduler.enums;

public enum JobStatusEnum {
    READY("ready", "Job is ready to be scheduling"),
    SCHEDULED("scheduled", "Job is scheduled"),
    RUNNING("running", "Job is running"),
    PAUSED("paused", "Job is paused"),
    RESUMED("resumed", "Job has resumed"),
    COMPLETED("completed", "Job has completed"),
    FAILED("failed", "Job has failed"),
    CANCELLED("cancelled", "Job has been cancelled"),
    ERROR("error", "Job has encountered an error");

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
