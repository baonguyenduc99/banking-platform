package com.banking.common.base.controller;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.banking.common.dto.ApiResponse;
import com.banking.common.dto.PaginationMeta;

/**
 * Base controller providing common REST API response patterns.
 * All controllers should extend this class for consistent responses.
 * 
 * @author Banking Platform Team
 * @since 1.0.0
 */
public abstract class BaseController {

    /**
     * Creates a success response with data.
     * 
     * @param data The response data
     * @return ResponseEntity with success status and data
     */
    protected <T> ResponseEntity<T> success(T data) {
        return ResponseEntity.ok(data);
    }

    protected <T extends Serializable> ResponseEntity<ApiResponse<T>> success(T data, PaginationMeta paginationMeta) {

        ApiResponse<T> response = ApiResponse.success(data, paginationMeta);
        return ResponseEntity.ok(response);
    }

    /**
     * Creates a success response without data.
     * 
     * @return ResponseEntity with no content
     */
    protected ResponseEntity<Void> success() {
        return ResponseEntity.noContent().build();
    }

    /**
     * Creates an error response with message.
     * 
     * @param message The error message
     * @return ResponseEntity with error status and message
     */
    protected ResponseEntity<String> error(String message) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
    }
}
