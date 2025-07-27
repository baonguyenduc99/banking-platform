package com.banking.common.utils;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestContextUtils {

    private static final String HEADER_X_FORWARDED_FOR = "X-Forwarded-For";


    private  RequestContextUtils() {}

    public static UUID safeParseUUID(String raw) {
        if (StringUtils.isBlank(raw)) return null;
        try {
            return UUID.fromString(raw);
        } catch (IllegalArgumentException ex) {
            log.warn("Invalid UUID format: {}", raw);
            return null;
        }
    }

    public static @NotNull Set<String> parseRoles(String raw) {
        if (StringUtils.isBlank(raw)) return Collections.emptySet();
        return new LinkedHashSet<>(Arrays.asList(raw.split(",")));
    }

    public static String resolveClientIp(@NotNull HttpServletRequest request) {
        String forwarded = request.getHeader(HEADER_X_FORWARDED_FOR);
        if (StringUtils.isNotBlank(forwarded)) {
            return forwarded.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}
