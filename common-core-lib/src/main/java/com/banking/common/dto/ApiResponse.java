package com.banking.common.dto;

import com.banking.common.context.RequestContextHolder;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class ApiResponse<T> {

    private final String status;
    private final String requestId;
    private final T data;
    private final PaginationMeta paginationMeta;

    private static final String SUCCESS_STATUS = "success";

    public static <T extends Serializable> ApiResponse<T> success(T data) {
        Objects.requireNonNull(data, "ApiResponse data must not be null");

        return ApiResponse.<T>builder().status(SUCCESS_STATUS)
                .requestId(RequestContextHolder.getContext().getRequestId()).data(data).build();
    }

    public static <T> ApiResponse<List<T>> success(List<T> data, PaginationMeta paginationMeta) {
        Objects.requireNonNull(data, "ApiResponse data must not be null");
        Objects.requireNonNull(paginationMeta, "ApiResponse paginationMeta must not be null");

        return ApiResponse.<List<T>>builder().status(SUCCESS_STATUS)
                .requestId(RequestContextHolder.getContext().getRequestId()).data(data)
                .paginationMeta(paginationMeta).build();
    }

    public static ApiResponse<NoContent> empty() {
        return ApiResponse.<NoContent>builder().status(SUCCESS_STATUS)
                .requestId(RequestContextHolder.getContext().getRequestId())
                .data(NoContent.INSTANCE).build();
    }
}
