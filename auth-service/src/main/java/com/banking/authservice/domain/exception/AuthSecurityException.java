package com.banking.authservice.domain.exception;

import com.banking.common.exception.CoreException;
import com.banking.common.exception.ErrorCode;

public class AuthSecurityException extends CoreException {


    public AuthSecurityException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public AuthSecurityException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }

}
