package com.vtx.jobscheduler.validator;

import com.vtx.jobscheduler.annotation.ValidJobContract;
import com.vtx.jobscheduler.enums.ScheduleTypeEnum;
import com.vtx.jobscheduler.model.Contract;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.scheduling.support.CronExpression;

import static com.vtx.jobscheduler.constants.Constants.*;

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

        if (ScheduleTypeEnum.isCron(value.getScheduleType())) {
            if (StringUtils.isBlank(value.getCronExpression())) {
                context.buildConstraintViolationWithTemplate(CRON_EXPRESSION
                                + " is required for CRON schedule")
                        .addPropertyNode(CRON_EXPRESSION)
                        .addConstraintViolation();
                return false;
            }

            String cronExpression = value.getCronExpression();
            if (cronExpression.split(" ").length == 5) {
                cronExpression += " *"; // Add the year field, spring scheduler/quartz cron requires 6 fields
            }
            if (!CronExpression.isValidExpression(cronExpression)) {
                context.buildConstraintViolationWithTemplate(CRON_EXPRESSION
                                + " is not a valid cron expression")
                        .addPropertyNode(CRON_EXPRESSION)
                        .addConstraintViolation();
                return false;
            }
        } else if (ScheduleTypeEnum.isFixedRate(value.getScheduleType())) {
                if (value.getFixedRateInMilliSeconds() == null || value.getFixedRateInMilliSeconds() <= 0) {
                    context.buildConstraintViolationWithTemplate(FIXED_RATE_IN_MILLI_SECONDS
                                    + " must be > 0 for FIXED_RATE schedule")
                            .addPropertyNode(FIXED_RATE_IN_MILLI_SECONDS)
                            .addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}
