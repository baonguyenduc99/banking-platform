package com.banking.authservice.domain.service;

import com.banking.authservice.domain.model.AuthMfaMethod;
import org.springframework.stereotype.Service;

@Service
public class MfaVerificationService {

    public void verify(String code, AuthMfaMethod expectedMethod){
        if (isValid(code, expectedMethod)){
//            throw new MfaVe
        }
    }

    private boolean isValid(String code, AuthMfaMethod expectedMethod) {
        return true;
    }
}
