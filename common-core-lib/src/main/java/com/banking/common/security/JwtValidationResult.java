package com.banking.common.security;

import com.banking.common.exception.ErrorCode;

import java.util.Set;
import java.util.UUID;

public record JwtValidationResult (boolean isValid, UUID userId, Set<String> roles, ErrorCode errorCode){}
