package com.banking.common.annotation.security;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimit {

    /**
     * Maximum number of requests allowed
     */
    int limit() default 100;

    /**
     * Time window for the limit
     */
    long window() default 1;

    /**
     * Time unit for the window
     */
    TimeUnit unit() default TimeUnit.MINUTES;

    /**
     * Key for rate limiting (SpEL expression supported)
     * Default uses user ID + method name
     */
    String key() default "";

    /**
     * Error message when rate limit exceeded
     */
    String message() default "Rate limit exceeded";
}
