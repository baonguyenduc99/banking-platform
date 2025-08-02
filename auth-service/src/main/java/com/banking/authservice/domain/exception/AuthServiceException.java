package com.banking.authservice.domain.exception;

import com.banking.common.exception.CoreException;
import com.banking.common.exception.ErrorCode;

public class AuthServiceException extends CoreException {

    public AuthServiceException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public AuthServiceException(ErrorCode errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }
}
