package com.banking.common.validation;

import java.lang.annotation.ElementType;

@Documented
@Constraint(validatedBy = UUIDValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUUID {

    String message() default "Invalid UUID";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
