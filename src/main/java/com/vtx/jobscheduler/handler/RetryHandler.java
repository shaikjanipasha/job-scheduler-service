package com.vtx.jobscheduler.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RetryHandler {
    public void handleRetry(String jobName, String errorMessage) {
        // Add retry logic here
    }
}
