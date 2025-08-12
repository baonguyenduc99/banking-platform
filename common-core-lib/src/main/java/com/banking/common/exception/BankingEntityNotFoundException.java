package com.banking.common.exception;

import java.io.Serial;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.ToString;

/**
 * Production-safe exception for "entity not found" cases. Extends JPA's EntityNotFoundException so
 * transaction rollback semantics apply.
 *
 * Design goals: - Immutable metadata (entity, identifier/criteria, correlationId, errorCode) - Safe
 * message (no sensitive data / PII) - Works across microservices (no Spring dependency)
 */
@Getter
@ToString(onlyExplicitlyIncluded = true)
public class BankingEntityNotFoundException extends EntityNotFoundException {


    @Serial
    private static final long serialVersionUID = 1L;

    /** Stable code for cross-service error contracts (e.g. maps to HTTP 404). */
    public static final String ERROR_CODE = "ERR_NOT_FOUND";

    /** Fully-qualified or simple entity name (domain type). */
    private final String entity;


    /** When lookup by single id is used. */
    // internally sets: identifierName="id", identifierValue=accountId
    private final String identifierName;
    private final Object identifierValue;

    /** When lookup by composite or arbitrary criteria is used. */
    private final Map<String, Object> criteria;

    /** Correlation / trace ID for observability across services. */
    private final String correlationId;

    /** Cached, safe message. */
    private final String message;

    public static BankingEntityNotFoundException byId(Class<?> entityClass, Object id,
            String correlationId) {
        return new BankingEntityNotFoundException(safeEntityName(entityClass), "id", id, null,
                correlationId);
    }


    @Override
    public String getMessage() {
        return message;
    }

    private BankingEntityNotFoundException(String entity, String identifierName,
            Object identifierValue, Map<String, Object> criteria, String correlationId) {
        this.entity = Objects.requireNonNull(entity, "entity");
        this.identifierName = identifierName;
        this.identifierValue = identifierValue;
        this.criteria = criteria == null ? Collections.emptyMap()
                : Map.copyOf(new LinkedHashMap<>(criteria));
        this.correlationId = correlationId;

        this.message = buildMessage();

    }


    private String buildMessage() {
        StringBuilder sb = new StringBuilder(96);
        sb.append("Entity not found").append(" [entity=").append(entity);
        if (identifierName != null) {
            // Do not render the raw value to avoid leaking PII; only indicate presence.
            sb.append(", ").append(identifierName).append("=<hidden>");
        }
        if (!criteria.isEmpty()) {
            sb.append(", criteriaKeys=").append(criteria.keySet());
        }
        if (correlationId != null && !correlationId.isBlank()) {
            sb.append(", cid=").append(correlationId);
        }
        sb.append(", code=").append(ERROR_CODE).append(']');
        return sb.toString();
    }

    private static String safeEntityName(Class<?> type) {
        return type == null ? "Unknown" : type.getSimpleName();
    }
}
