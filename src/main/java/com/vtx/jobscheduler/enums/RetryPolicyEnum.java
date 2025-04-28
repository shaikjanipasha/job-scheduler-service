package com.vtx.jobscheduler.enums;

public enum RetryPolicyEnum {
        FIXED("FIXED", "Fixed Interval"),
        EXPONENTIAL("EXPONENTIAL", "Exponential Backoff"),
        NONE("NONE", "No Retry");

        private final String value;
        private final String description;

        RetryPolicyEnum(String value, String description) {
            this.value = value;
            this.description = description;
        }
        public String getValue() {
            return this.value;
        }
        public String getDescription() {
            return this.description;
        }
        public static RetryPolicyEnum fromValue(String value) {
            for (RetryPolicyEnum type : RetryPolicyEnum.values()) {
                if (type.value.equalsIgnoreCase(value)) {
                    return type;
                }
            }
            return RetryPolicyEnum.NONE;
        }

}
