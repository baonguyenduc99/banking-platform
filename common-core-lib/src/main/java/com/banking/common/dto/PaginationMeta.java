package com.banking.common.dto;

import org.springframework.data.domain.Page;
import lombok.Getter;

@Getter
public final class PaginationMeta {

    private final int page; // Zero-based index
    private final int size; // Number of items per page
    private final long totalElements;
    private final long totalPages;

    private PaginationMeta(int page, int size, long totalElements, long totalPages) {

        if (page < 0) {
            throw new IllegalArgumentException("Page index must not be less than zero");
        }
        if (size <= 0) {
            throw new IllegalArgumentException("Page size must be greater than zero");
        }
        if (totalElements < 0) {
            throw new IllegalArgumentException("Total elements must not be less than zero");
        }
        if (totalPages > 0 && page >= totalPages) {
            throw new IllegalArgumentException("Page index must be less than total pages");
        }
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public static PaginationMeta of(int page, int size, long totalElements) {
        long totalPages = (totalElements == 0) ? 0 : ((totalElements + size - 1L) / size);
        return new PaginationMeta(page, size, totalElements, totalPages);
    }


    public static <T> PaginationMeta from(Page<T> page) {
        return new PaginationMeta(page.getNumber(), page.getSize(), page.getTotalElements(),
                page.getTotalPages());
    }
}
