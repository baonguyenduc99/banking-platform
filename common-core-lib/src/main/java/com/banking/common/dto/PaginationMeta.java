package com.banking.common.dto;

import java.io.Serializable;
import lombok.Getter;

@Getter
public final class PaginationMeta implements Serializable {

    private final int page; // Zero-based index
    private final int size; // Number of items per page
    private final long totalElements;
    private final int totalPages;

    private PaginationMeta(int page, int size, long totalElements, int totalPages) {

        if (page < 0) {
            throw new IllegalArgumentException("Page index must not be less than zero");
        }
        if (size <= 0) {
            throw new IllegalArgumentException("Page size must be greater than zero");
        }
        if (totalElements < 0) {
            throw new IllegalArgumentException("Total elements must not be less than zero");
        }
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public static PaginationMeta of(int page, int size, long totalElements) {
        int totalPages = (int) Math.ceil((double) totalElements / size);
        return new PaginationMeta(page, size, totalElements, totalPages);
    }
}
