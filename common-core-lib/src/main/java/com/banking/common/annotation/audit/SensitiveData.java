package com.banking.common.annotation.audit;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SensitiveData {

    /**
     * Type of masking to apply
     */
    MaskingType type() default MaskingType.FULL;

    /**
     * Custom masking pattern (if type is CUSTOM)
     */
    String pattern() default "";

    /**
     * Whether to completely exclude from audit logs
     */
    boolean excludeFromAudit() default false;
}
