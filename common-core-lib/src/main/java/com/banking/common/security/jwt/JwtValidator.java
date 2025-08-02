package com.banking.common.security.jwt;

import com.banking.common.security.JwtValidationResult;

public interface JwtValidator {

    JwtValidationResult validationToken(String jwt);

}
