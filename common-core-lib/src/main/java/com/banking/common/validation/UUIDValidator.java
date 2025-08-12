package com.banking.common.validation;

import java.util.UUID;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UUIDValidator implements ConstraintValidator<ValidUUID, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            UUID.fromString(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
