package com.banking.common.context;

import com.banking.common.utils.RequestContextUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
public class RequestContextFilter extends OncePerRequestFilter {

    private static final String HEADER_USER_ID = "X-User-Id";
    private static final String HEADER_USERNAME = "X-Username";
    private static final String HEADER_ROLES = "X-Roles";
    private static final String HEADER_EMAIL = "X-User-Email";
    private static final String HEADER_TOKEN_ID = "X-JTI";
    private static final String HEADER_REQUEST_ID = "X-Request-ID";
    private static final String HEADER_CORRELATION_ID = "X-Correlation-ID";


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
            @NonNull  HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        try {
            RequestContext context = RequestContext.builder()
                    .userId(RequestContextUtils.safeParseUUID(request.getHeader(HEADER_USER_ID)))
                    .userName(request.getHeader(HEADER_USERNAME))
                    .email(StringUtils.trimToNull(request.getHeader(HEADER_EMAIL)))
                    .roles(RequestContextUtils.parseRoles(request.getHeader(HEADER_ROLES)))
                    .tokenId(StringUtils.trimToNull(request.getHeader(HEADER_TOKEN_ID)))
                    .ipAddress(RequestContextUtils.resolveClientIp(request))
                    .requestId(StringUtils.defaultIfBlank(request.getHeader(HEADER_REQUEST_ID),
                            UUID.randomUUID().toString()))
                    .correlationId(
                            StringUtils.defaultIfBlank(request.getHeader(HEADER_CORRELATION_ID),
                                    UUID.randomUUID().toString())).build();

            RequestContextHolder.setContext(context);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("failed to populate RequestContext", e);
            throw e;
        } finally {
            RequestContextHolder.clearContext();
        }
    }

}
