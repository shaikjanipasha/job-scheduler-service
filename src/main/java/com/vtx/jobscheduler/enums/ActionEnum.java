package com.vtx.jobscheduler.enums;
public enum ActionEnum {
    NOTIFICATION("notification", "Push, SMS, or in-app notifications"),
    EMAIL("email", "Scheduled or triggered emails"),
    REPORTING("reporting", "Generate reports or data exports"),
    DATA_SYNC("datasync", "Sync with external or internal APIs"),
    MAINTENANCE("maintainance", "System cleanup and upkeep"),
    RETRY("retry", "Retry failed operations"),
    INTEGRATION("integration", "3rd-party or message queue interaction"),
    MONITORING("monitoring", "Health checks and monitoring tasks"),
    BACKUP("backup", "Data backup and recovery"),
    OTHER("other", "Other types of job");

    private final String value;
    private final String description;

    ActionEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return this.value;
    }

    public String getDescription() {
        return this.description;
    }

    public static ActionEnum fromValue(String value) {
        for (ActionEnum type : ActionEnum.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown job type: " + value);
    }

    public static boolean isValid(String value) {
        for (ActionEnum type : ActionEnum.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}
