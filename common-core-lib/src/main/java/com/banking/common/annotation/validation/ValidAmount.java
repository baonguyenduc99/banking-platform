package com.banking.common.annotation.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.banking.common.annotation.validation.banking.AmountValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AmountValidator.class)
@Documented
public @interface ValidAmount {

    String message() default "Invalid amount";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * Minimum allowed amount (inclusive)
     */
    String min() default "0.01";

    /**
     * Maximum allowed amount (inclusive)
     */
    String max() default "999999999.99";

    /**
     * Maximum decimal places allowed
     */
    int decimalPlaces() default 2;

    /**
     * Currency code for validation
     */
    String currency() default "";

    /**
     * Whether negative amounts are allowed
     */
    boolean allowNegative() default false;
}
