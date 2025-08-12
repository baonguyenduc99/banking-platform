package com.banking.common.annotation.security;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequireRole {

    /**
     * Required roles (user must have ALL listed roles)
     */
    String[] value();

    /**
     * Alternative roles (user must have ANY of these roles)
     */
    String[] anyOf() default {};

    /**
     * Whether to check role hierarchy
     */
    boolean hierarchical() default true;
}
