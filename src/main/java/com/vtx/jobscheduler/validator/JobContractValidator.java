package com.vtx.jobscheduler.validator;

import static com.vtx.jobscheduler.constants.Constants.CRON_EXPRESSION;
import static com.vtx.jobscheduler.constants.Constants.EXPONENTIAL_BASE;
import static com.vtx.jobscheduler.constants.Constants.EXPONENTIAL_INITIAL_DELAY_IN_SECONDS;
import static com.vtx.jobscheduler.constants.Constants.FIXED_RATE_IN_MILLI_SECONDS;
import static com.vtx.jobscheduler.constants.Constants.RETRY_DELAY_IN_SECONDS;

import com.vtx.jobscheduler.annotation.ValidJobContract;
import com.vtx.jobscheduler.enums.RetryPolicyEnum;
import com.vtx.jobscheduler.enums.ScheduleTypeEnum;
import com.vtx.jobscheduler.model.Contract;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.scheduling.support.CronExpression;

public class JobContractValidator implements ConstraintValidator<ValidJobContract, Contract> {
    @Override
    public void initialize(ValidJobContract constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Contract value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        context.disableDefaultConstraintViolation();

        if (!validateScheduleType(value, context)) {
            return false;
        }
        return validateRetryPolicy(value, context);
    }
    private boolean validateScheduleType(Contract value, ConstraintValidatorContext context) {
        if (ScheduleTypeEnum.isCron(value.getScheduleType())) {
            return validateCronExpression(value.getCronExpression(), context);
        }

        if (ScheduleTypeEnum.isFixedRate(value.getScheduleType())) {
            if (value.getFixedRateInMilliSeconds() == null || value.getFixedRateInMilliSeconds() <= 0) {
                buildViolation(context, FIXED_RATE_IN_MILLI_SECONDS + " must be > 0 for FIXED_RATE schedule",
                        FIXED_RATE_IN_MILLI_SECONDS);
                return false;
            }
        }

        return true;
    }
    private boolean validateCronExpression(String cronExpression, ConstraintValidatorContext context) {
        if (StringUtils.isBlank(cronExpression)) {
            buildViolation(context, CRON_EXPRESSION + " is required for CRON schedule", CRON_EXPRESSION);
            return false;
        }

        String normalizedExpression = cronExpression.trim();
        if (normalizedExpression.split(" ").length == 5) {
            normalizedExpression += " *"; // append year field if missing
        }

        if (!CronExpression.isValidExpression(normalizedExpression)) {
            buildViolation(context, CRON_EXPRESSION + " is not a valid cron expression", CRON_EXPRESSION);
            return false;
        }

        return true;
    }

    private boolean validateRetryPolicy(Contract value, ConstraintValidatorContext context) {
        RetryPolicyEnum policy = value.getRetryPolicy();

        if (policy == null) {
            buildViolation(context, "retryPolicy is required", "retryPolicy");
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

        buildViolation(context, "retryPolicy is Invalid", "retryPolicy");
        return false;
    }

    private boolean validateFixedRetryPolicy(Contract value, ConstraintValidatorContext context) {
        boolean valid = true;

        if (value.getRetryDelayInSeconds() == null || value.getRetryDelayInSeconds() <= 0) {
            buildViolation(context, RETRY_DELAY_IN_SECONDS + " must be > 0 for FIXED retry policy", RETRY_DELAY_IN_SECONDS);
            valid = false;
        }

        if (value.getMaxRetries() == null || value.getMaxRetries() <= 0) {
            buildViolation(context, "maxRetries must be > 0 for FIXED retry policy", "maxRetries");
            valid = false;
        }

        return valid;
    }

    private boolean validateExponentialRetryPolicy(Contract value, ConstraintValidatorContext context) {
        boolean valid = true;

        if (value.getExponentialBase() == null || value.getExponentialBase() <= 1) {
            buildViolation(context, EXPONENTIAL_BASE + " must be > 1 for EXPONENTIAL retry policy", EXPONENTIAL_BASE);
            valid = false;
        }

        if (value.getExponentialInitialDelayInSeconds() == null || value.getExponentialInitialDelayInSeconds() <= 0) {
            buildViolation(context, EXPONENTIAL_INITIAL_DELAY_IN_SECONDS
                    + " must be > 0 for EXPONENTIAL retry policy", EXPONENTIAL_INITIAL_DELAY_IN_SECONDS);
            valid = false;
        }

        if (value.getMaxRetries() == null || value.getMaxRetries() <= 0) {
            buildViolation(context, "maxRetries must be > 0 for EXPONENTIAL retry policy", "maxRetries");
            valid = false;
        }

        return valid;
    }

    private void buildViolation(ConstraintValidatorContext context, String message, String property) {
        context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(property)
                .addConstraintViolation();
    }
}
