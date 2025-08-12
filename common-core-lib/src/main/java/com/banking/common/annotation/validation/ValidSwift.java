package com.banking.common.annotation.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.banking.common.annotation.validation.banking.SwiftValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * Validates SWIFT/BIC code format according to ISO 9362 standard.
 * 
 * @author Banking Platform Team
 * @since 1.0.0
 */
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SwiftValidator.class)
@Documented
public @interface ValidSwift {

    String message() default "Invalid SWIFT code";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * Whether to allow 8-character codes (without branch code)
     */
    boolean allowShort() default true;

}
