package com.banking.common.exception.auth;

import com.banking.common.exception.CoreException;
import com.banking.common.exception.ErrorCode;

public class MfaException extends CoreException {

    public MfaException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
