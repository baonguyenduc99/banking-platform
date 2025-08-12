package com.banking.common.annotation.audit;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuditAction {

    /**
     * The action being performed (e.g., "LOGIN", "TRANSFER", "VIEW_ACCOUNT")
     */
    String action();

    /**
     * The resource being accessed (e.g., "ACCOUNT", "TRANSACTION", "USER")
     */
    String resource();

    /**
     * Whether to log method parameters (be careful with sensitive data)
     */
    boolean logParams() default false;

    /**
     * Whether to log the result of the method execution
     */
    boolean logResult() default false;

    /**
     * Severity level of this audit action
     */
    AuditSeverity severity() default AuditSeverity.INFO;

    /**
     * Whether this is a sensitive operation requiring special handling
     */
    boolean sensitive() default false;
}