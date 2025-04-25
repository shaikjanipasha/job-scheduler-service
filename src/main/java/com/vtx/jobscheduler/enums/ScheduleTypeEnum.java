package com.vtx.jobscheduler.enums;

public enum ScheduleTypeEnum {
    CRON("cron", "Cron expression for scheduling"),
    FIXED_RATE("fixed_rate", "Fixed rate scheduling"),
    UNKNOWN("unknown", "Unknown scheduling type");

    private final String value;
    private final String description;
    ScheduleTypeEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }
    public String getValue() {
        return this.value;
    }
    public String getDescription() {
        return this.description;
    }
    public static ScheduleTypeEnum fromValue(String value) {
        for (ScheduleTypeEnum type : ScheduleTypeEnum.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown schedule type: " + value);
    }
}
