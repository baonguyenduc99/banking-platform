package com.banking.authservice.domain.exception.impl;

import com.banking.authservice.domain.exception.AuthSecurityException;
import com.banking.common.exception.ErrorCode;

public class InvalidCredentialsException extends AuthSecurityException {

    public InvalidCredentialsException(){
        super(ErrorCode.AUTH_INVALID_CREDENTIALS, "Invalid email/password combination.");
    }

    public InvalidCredentialsException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
