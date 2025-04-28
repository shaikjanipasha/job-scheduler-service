package com.vtx.jobscheduler.constants;

public final class Constants {
    private Constants() {
    }
    public static final String UTC_TIME_ZONE = "UTC";
    public static final String FIXED_RATE_IN_MILLI_SECONDS = "fixedRateInMilliSeconds";
    public static final String CRON_EXPRESSION = "cronExpression";
    public static final String SCHEDULE_TYPE = "scheduleType";
    public static final String SYSTEM_USER = "system";
    public static final String DISTRIBUTED_JOB_SCHEDULER_LOCK = "distributedJobSchedulerLock";
    public static final String BEARER_AUTH = "bearerAuth";
    public static final String SCHEME_BEARER = "bearer";
    public static final int RETRY_DELAY_IN_SECONDS_DEFAULT = 60;
    public static final int EXPONENTIAL_BASE_DEFAULT = 2;
    public static final int EXPONENTIAL_INITIAL_DELAY_IN_SECONDS_DEFAULT = 30;
    public static final String RETRY_DELAY_IN_SECONDS = "retryDelayInSeconds";
    public static final String EXPONENTIAL_BASE = "exponentialBase";
    public static final String EXPONENTIAL_INITIAL_DELAY_IN_SECONDS = "exponentialInitialDelayInSeconds";

}
