package com.banking.authservice.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Duration;

@RequiredArgsConstructor
@Getter
public enum AuthMfaMethod {

    TOTP(6, Duration.ofSeconds(30)),
    SMS(6 , Duration.ofMinutes(5)),
    EMAIL(8, Duration.ofMinutes(10));


    private final int codeLength;
    private final Duration ttl;


}
