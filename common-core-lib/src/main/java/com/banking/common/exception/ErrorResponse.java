package com.banking.common.exception;

import java.time.Instant;

public class ErrorResponse {

    private final String code;
    private final String message;
    private final Instant timestamp;
    private final String path;
    private final String correlationId;

    public ErrorResponse(String code, String message, String path, String correlationId) {
        this.code = code;
        this.message = message;
        this.timestamp = Instant.now();
        this.path = path;
        this.correlationId = correlationId;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getPath() {
        return path;
    }

    public String getCorrelationId() {
        return correlationId;
    }

}
