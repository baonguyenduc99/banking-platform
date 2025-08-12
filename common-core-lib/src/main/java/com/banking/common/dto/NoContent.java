package com.banking.common.dto;

import java.io.Serializable;

public final class NoContent implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final NoContent INSTANCE = new NoContent();

    private NoContent() {
    }

}
