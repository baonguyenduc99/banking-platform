package com.banking.common.context;

import com.banking.common.constants.HeaderConstants;
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



    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
            @NonNull  HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        try {
            RequestContext context = RequestContext.builder()
                    .userId(RequestContextUtils.safeParseUUID(request.getHeader(HeaderConstants.HEADER_USER_ID)))
                    .userName(request.getHeader(HeaderConstants.HEADER_USERNAME))
                    .email(StringUtils.trimToNull(request.getHeader(HeaderConstants.HEADER_EMAIL)))
                    .roles(RequestContextUtils.parseRoles(request.getHeader(HeaderConstants.HEADER_ROLES)))
                    .tokenId(StringUtils.trimToNull(request.getHeader(HeaderConstants.HEADER_TOKEN_ID)))
                    .ipAddress(RequestContextUtils.resolveClientIp(request))
                    .requestId(StringUtils.defaultIfBlank(request.getHeader(HeaderConstants.HEADER_REQUEST_ID),
                            UUID.randomUUID().toString()))
                    .correlationId(
                            StringUtils.defaultIfBlank(request.getHeader(HeaderConstants.HEADER_CORRELATION_ID),
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
