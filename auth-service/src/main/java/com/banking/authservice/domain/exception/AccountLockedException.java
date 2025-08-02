package com.banking.authservice.domain.exception;

import com.banking.common.exception.ErrorCode;

import java.time.Duration;

public class AccountLockedException extends AuthSecurityException {

    public AccountLockedException(Duration lockDuration) {
        super(ErrorCode.AUTH_ACCOUNT_LOCKED, String.format("Account locked for %d minutes due to too many failed login attempts.", lockDuration.toMinutes()));
    }
}
