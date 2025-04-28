package com.vtx.jobscheduler.routes;

public final class Routes {
    private Routes() {
    }

    public static final String API_V1_JOBS = "/api/v1/jobs";
    public static final String JOBS_BY_JOB_NAME = "/byJobName/{jobName}";
    public static final String JOBS_BY_ID = "/{jobId}";
    public static final String JOBS_CREATE = "/";
    public static final String ROUTE_USER = "/user";
    public static final String USER_REGISTER = "/register";
    public static final String USER_LOGIN = "/login";
}
