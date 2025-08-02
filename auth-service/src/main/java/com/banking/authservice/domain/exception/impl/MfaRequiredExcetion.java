package com.banking.authservice.domain.exception.impl;

import com.banking.authservice.domain.exception.AuthServiceException;
import com.banking.authservice.domain.model.AuthMfaMethod;
import com.banking.common.exception.ErrorCode;
import com.banking.common.exception.auth.MfaException;
import lombok.Getter;

import java.time.Instant;

@Getter
public class MfaRequiredExcetion extends MfaException {

    private final AuthMfaMethod method;
    private final Instant expiresAt;

    public MfaRequiredExcetion(AuthMfaMethod method, Instant expiresAt) {
        super(ErrorCode.AUTH_MFA_REQUIRED, method.name() + " verification is required.");
        this.method = method;
        this.expiresAt = Instant.now().plus(method.getTtl());
    }
}
