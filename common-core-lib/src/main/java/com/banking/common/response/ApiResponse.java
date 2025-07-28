package com.banking.common.response;

import com.banking.common.context.RequestContextHolder;
import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiResponse<T> implements Serializable {

    private final String status;
    private final String requestId;
    private final T data;
    private final PaginationMeta paginationMeta;

    private static final  String SUCESS_STATUS = "success";

    public static <T> ApiResponse<T> sucess(T data) {
        return ApiResponse.<T>builder()
                .status(SUCESS_STATUS)
                .requestId(RequestContextHolder.getContext().getRequestId())
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> success(T data, PaginationMeta paginationMeta) {
        return ApiResponse.<T>builder()
                .status(SUCESS_STATUS)
                .requestId(RequestContextHolder.getContext().getRequestId())
                .data(data)
                .paginationMeta(paginationMeta)
                .build();
    }

    public static ApiResponse<Void> empty() {
        return ApiResponse.<Void>builder().status(SUCESS_STATUS)
                .requestId(RequestContextHolder.getContext().getRequestId())
                .build();
    }
}
