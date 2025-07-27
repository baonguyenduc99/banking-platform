package com.banking.common.context;

import java.util.UUID;

/**
 * Utility class for managing the {@link RequestContext} in a thread-local scope.
 * <p>
 * Provides static methods to set, get, clear, and check the existence of a request context
 * for the current thread.
 */
public class RequestContextHolder {

    private RequestContextHolder(){}

    private static final ThreadLocal<RequestContext> CONTEXT = new ThreadLocal<>();

    public static void setContext(RequestContext requestContext) {
        CONTEXT.set(requestContext);
    }

    public static RequestContext getContext(){
        return CONTEXT.get();
    }

    public static void clearContext(){
        CONTEXT.remove();
    }

    public static boolean exists(){
        return CONTEXT.get() != null;
    }

    /**
     * Checks if the current context represents a system-level user.
     */
    public static boolean isSystemUser() {
        RequestContext ctx = CONTEXT.get();
        return ctx != null && ctx.isSystemUser();
    }

    /**
     * Gets user ID safely, or throws exception if missing.
     */
    public static UUID requireUserId() {
        RequestContext ctx = CONTEXT.get();
        if (ctx == null || ctx.getUserId() == null) {
            throw new IllegalStateException("User ID not found in RequestContext");
        }
        return ctx.getUserId();
    }

}
