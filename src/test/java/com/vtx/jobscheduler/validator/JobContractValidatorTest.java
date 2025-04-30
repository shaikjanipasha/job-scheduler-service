package com.vtx.jobscheduler.validator;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.vtx.jobscheduler.enums.RetryPolicyEnum;
import com.vtx.jobscheduler.enums.ScheduleTypeEnum;
import com.vtx.jobscheduler.model.Contract;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class JobContractValidatorTest {

    private final JobContractValidator validator = new JobContractValidator();
    private final ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
    private final ConstraintValidatorContext.ConstraintViolationBuilder builder =
            mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);

    @BeforeEach
    void setUp() {
        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(builder);
        when(builder.addPropertyNode(anyString())).thenReturn(
                mock(ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext.class));
    }

    @Test
    void shouldFailWhenContractIsNull() {
        boolean result = validator.isValid(null, context);
        Assertions.assertFalse(result);
    }

    @Test
    void shouldFailWhenCronScheduleHasNoExpression() {
        Contract contract = new Contract();
        contract.setScheduleType(ScheduleTypeEnum.CRON);
        contract.setCronExpression("");

        boolean result = validator.isValid(contract, context);
        Assertions.assertFalse(result);
    }

    @Test
    void shouldFailWhenCronExpressionIsInvalid() {
        Contract contract = new Contract();
        contract.setScheduleType(ScheduleTypeEnum.CRON);
        contract.setCronExpression("invalid cron");
        boolean result = validator.isValid(contract, context);
        Assertions.assertFalse(result);
    }

    @Test
    void shouldFailWhenFixedRateIsNullOrZero() {
        Contract contract = new Contract();
        contract.setScheduleType(ScheduleTypeEnum.FIXED_RATE);
        contract.setFixedRateInMilliSeconds(0L);
        boolean result = validator.isValid(contract, context);
        Assertions.assertFalse(result);
    }

    @Test
    void shouldPassWithNoRetryPolicy() {
        Contract contract = new Contract();
        contract.setScheduleType(ScheduleTypeEnum.FIXED_RATE);
        contract.setFixedRateInMilliSeconds(5000L);
        contract.setRetryPolicy(RetryPolicyEnum.NONE);
        boolean result = validator.isValid(contract, context);
        Assertions.assertTrue(result);
    }

    @Test
    void shouldFailWithInvalidFixedRetryPolicy() {
        Contract contract = new Contract();
        contract.setScheduleType(ScheduleTypeEnum.FIXED_RATE);
        contract.setFixedRateInMilliSeconds(1000L);
        contract.setRetryPolicy(RetryPolicyEnum.FIXED);
        contract.setRetryDelayInSeconds(-1);
        contract.setMaxRetries(null);
        boolean result = validator.isValid(contract, context);
        Assertions.assertFalse(result);
    }

    @Test
    void shouldFailWithInvalidExponentialRetryPolicy() {
        Contract contract = new Contract();
        contract.setScheduleType(ScheduleTypeEnum.FIXED_RATE);
        contract.setFixedRateInMilliSeconds(1000L);
        contract.setRetryPolicy(RetryPolicyEnum.EXPONENTIAL);
        contract.setExponentialBase(1);
        contract.setExponentialInitialDelayInSeconds(-1);
        contract.setMaxRetries(0);
        boolean result = validator.isValid(contract, context);
        Assertions.assertFalse(result);
    }

    @Test
    void shouldPassWithValidCronAndRetryPolicy() {
        Contract contract = new Contract();
        contract.setScheduleType(ScheduleTypeEnum.CRON);
        contract.setCronExpression("0 0 * * *");
        contract.setRetryPolicy(RetryPolicyEnum.FIXED);
        contract.setRetryDelayInSeconds(3);
        contract.setMaxRetries(5);
        boolean result = validator.isValid(contract, context);
        Assertions.assertTrue(result);
    }

}
