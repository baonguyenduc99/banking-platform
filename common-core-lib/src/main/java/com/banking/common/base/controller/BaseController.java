package com.banking.common.base.controller;

import java.io.Serializable;
import java.net.URI;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel.PageMetadata;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.banking.common.dto.ApiResponse;
import com.banking.common.dto.PaginationMeta;

/**
 * Base controller providing common REST API response patterns. All controllers should extend this
 * class for consistent responses.
 * 
 * @author Banking Platform Team
 * @since 1.0.0
 */
public abstract class BaseController {

    /** 200 OK with data envelope */
    protected <T> ResponseEntity<T> ok(T data) {
        return ResponseEntity.ok(data);
    }

    /** 201 Created with Location header + data envelope */
    protected <T> ResponseEntity<ApiResponse<T>> created(URI location, T data) {
        return ResponseEntity.created(location).body(ApiResponse.success(data, null));
    }

    protected <T> ResponseEntity<ApiResponse<T>> success(T data, PaginationMeta paginationMeta) {

        ApiResponse<T> response = ApiResponse.success(data, paginationMeta);
        return ResponseEntity.ok(response);
    }

    /** 204 No Content (option A: literally empty) */
    protected ResponseEntity<Void> noContent() {
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

    /**
     * Creates a successful response with paginated data
     */
    protected <T> ResponseEntity<ApiResponse<List<T>>> ok(Page<T> page) {
        PaginationMeta pageMetadata = PaginationMeta.from(page);
        return ResponseEntity.ok(ApiResponse.success(page.getContent(), pageMetadata));
    }

}
