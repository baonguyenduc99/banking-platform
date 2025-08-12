package com.banking.common.annotation.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.banking.common.annotation.validation.banking.IBANValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * Validates IBAN (International Bank Account Number) format and check digits.
 * Supports all IBAN countries and performs MOD-97 validation.
 * 
 * @author Banking Platform Team
 * @since 1.0.0
 */
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IBANValidator.class)
@Documented
public @interface ValidIBAN {

    String message() default "Invalid IBAN format or check digits";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * Allowed countries (ISO 3166-1 alpha-2). Empty means all countries.
     */
    String[] countries() default {};

}
