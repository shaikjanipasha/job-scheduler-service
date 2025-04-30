package com.vtx.jobscheduler.validator;

import static com.vtx.jobscheduler.constants.Constants.CRON_EXPRESSION;
import static com.vtx.jobscheduler.constants.Constants.EXPONENTIAL_BASE;
import static com.vtx.jobscheduler.constants.Constants.EXPONENTIAL_INITIAL_DELAY_IN_SECONDS;
import static com.vtx.jobscheduler.constants.Constants.FIXED_RATE_IN_MILLI_SECONDS;
import static com.vtx.jobscheduler.constants.Constants.RETRY_DELAY_IN_SECONDS;

import com.vtx.jobscheduler.enums.RetryPolicyEnum;
import com.vtx.jobscheduler.enums.ScheduleTypeEnum;
import com.vtx.jobscheduler.model.Contract;
import com.vtx.jobscheduler.model.JobPatchRequestContract;
import com.vtx.jobscheduler.model.ValidationError;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Component;

@Component
public class JobPatchValidator {
    public boolean isValid(JobPatchRequestContract patchRequest, ValidationError error) {
        if (patchRequest == null || patchRequest.getName() == null) {
            return false;
        }

        if (!validateScheduleType(patchRequest, error)) {
            return false;
        }
        return validateRetryPolicy(patchRequest, error);
    }
    private boolean validateScheduleType(JobPatchRequestContract value, ValidationError context) {
        if (ScheduleTypeEnum.isCron(value.getScheduleType())) {
            return validateCronExpression(value.getCronExpression(), context);
        }

        if (ScheduleTypeEnum.isFixedRate(value.getScheduleType())) {
            if (value.getFixedRateInMilliSeconds() == null || value.getFixedRateInMilliSeconds() <= 0) {
                context.setErrorMessage(FIXED_RATE_IN_MILLI_SECONDS + " must be > 0 for FIXED_RATE schedule");
                return false;
            }
        }

        return true;
    }
    private boolean validateCronExpression(String cronExpression, ValidationError context) {
        if (StringUtils.isBlank(cronExpression)) {
            context.setErrorMessage(CRON_EXPRESSION + " is required for CRON schedule");
            return false;
        }

        String normalizedExpression = cronExpression.trim();
        if (normalizedExpression.split(" ").length == 5) {
            normalizedExpression += " *"; // append year field if missing
        }

        if (!CronExpression.isValidExpression(normalizedExpression)) {
            context.setErrorMessage(CRON_EXPRESSION + " is not a valid cron expression" + normalizedExpression);
            return false;
        }

        return true;
    }

    private boolean validateRetryPolicy(JobPatchRequestContract value, ValidationError context) {
        RetryPolicyEnum policy = value.getRetryPolicy();

        if (policy == null) {
            context.setErrorMessage("retryPolicy is required" + value.getRetryPolicy()
                    + " is not a valid retry policy" + value.getRetryPolicy());
            return false;
        }

        if (policy == RetryPolicyEnum.NONE) {
            return true;
        }

        if (policy == RetryPolicyEnum.FIXED) {
            return validateFixedRetryPolicy(value, context);
        }

        if (policy == RetryPolicyEnum.EXPONENTIAL) {
            return validateExponentialRetryPolicy(value, context);
        }

        context.setErrorMessage(value.getRetryPolicy() + " is not a valid retry policy");
        return false;
    }

    private boolean validateFixedRetryPolicy(JobPatchRequestContract value, ValidationError context) {
        boolean valid = true;

        if (value.getRetryDelayInSeconds() == null || value.getRetryDelayInSeconds() <= 0) {
            context.setErrorMessage(RETRY_DELAY_IN_SECONDS + " must be > 0 for FIXED retry policy");
            valid = false;
        }

        if (value.getMaxRetries() == null || value.getMaxRetries() <= 0) {
            context.setErrorMessage("maxRetries must be > 0 for FIXED retry policy");
            valid = false;
        }

        return valid;
    }

    private boolean validateExponentialRetryPolicy(JobPatchRequestContract value, ValidationError context) {
        boolean valid = true;

        if (value.getExponentialBase() == null || value.getExponentialBase() <= 1) {
            context.setErrorMessage(EXPONENTIAL_BASE + " must be > 1 for EXPONENTIAL retry policy");
            valid = false;
        }

        if (value.getExponentialInitialDelayInSeconds() == null || value.getExponentialInitialDelayInSeconds() <= 0) {
            context.setErrorMessage(EXPONENTIAL_INITIAL_DELAY_IN_SECONDS
                    + " must be > 0 for EXPONENTIAL retry policy");
            valid = false;
        }

        if (value.getMaxRetries() == null || value.getMaxRetries() <= 0) {
            context.setErrorMessage("maxRetries must be > 0 for EXPONENTIAL retry policy");
            valid = false;
        }

        return valid;
    }
}
