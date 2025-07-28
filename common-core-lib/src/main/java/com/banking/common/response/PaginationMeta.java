package com.banking.common.response;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaginationMeta implements Serializable {

    private final int page;
    private final int size;
    private final long totalElements;
    private final int totalPages;

    public static PaginationMeta of(int page, int size, long totalElements){
        int totalPages = (int) Math.ceil((double) totalElements / size);
        return new PaginationMeta(page, size, totalElements, totalPages);
    }
}
