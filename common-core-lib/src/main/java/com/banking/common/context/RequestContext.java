package com.banking.common.context;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Per-request metadata for authentication, tracing, and auditing.
 * Used throughout the system via ThreadLocal (RequestContextHolder),
 * headers (X-User-Id, X-Correlation-Id), and Kafka events.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestContext implements Serializable {

    private static final long serialVersionUID = 1L;


    private UUID userId;
    private String userName;
    private String email;
    private Set<String> roles;

    private String tokenId;
    private String ipAddress;

    private String requestId;
    private String correlationId;

    public boolean isAdmin(){
        return roles != null && roles.contains("ADMIN");
    }

    public boolean isSystemUser(){
        return roles != null && roles.contains("SYSTEM");
    }

    public boolean isCustomer() {
        return roles != null && roles.contains("CUSTOMER");
    }

}
