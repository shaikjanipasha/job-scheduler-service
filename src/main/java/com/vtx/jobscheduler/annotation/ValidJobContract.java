package com.vtx.jobscheduler.annotation;

import com.vtx.jobscheduler.validator.JobContractValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {JobContractValidator.class})
public @interface ValidJobContract {
    String message() default "Invalid JobContract: cronExpression or fixedRate is missing based on scheduleType";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
