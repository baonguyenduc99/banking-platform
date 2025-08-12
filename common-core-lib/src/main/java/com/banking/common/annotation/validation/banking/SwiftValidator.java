package com.banking.common.annotation.validation.banking;

import org.apache.commons.lang3.StringUtils;

import com.banking.common.annotation.validation.ValidSwift;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SwiftValidator implements ConstraintValidator<ValidSwift, String> {

    private boolean allowShort;

    @Override
    public void initialize(ValidSwift constraintAnnotation) {
        this.allowShort = constraintAnnotation.allowShort();
    }

    @Override
    public boolean isValid(String swift, ConstraintValidatorContext context) {
        if (StringUtils.isBlank(swift)) {
            return true;
        }

        String cleanSwift = swift.replaceAll("\\s", "").toUpperCase();

        try {
            if (!isValidLength(cleanSwift)) {
                addViolation(context, "Invalid SWIFT code length");
                return false;
            }

            if (!isValidFormat(cleanSwift)) {
                addViolation(context, "Invalid SWIFT code format");
                return false;
            }

            return true;

        } catch (Exception e) {
            log.warn("SWIFT validation error for value: {}", swift, e);
            addViolation(context, "SWIFT validation failed");
            return false;
        }
    }

    private boolean isValidLength(String swift) {
        int length = swift.length();
        return (allowShort && length == 8) || length == 11;
    }

    private boolean isValidFormat(String swift) {
        if (swift.length() == 8) {
            return swift.matches("[A-Z]{6}[A-Z0-9]{2}");
        } else if (swift.length() == 11) {
            return swift.matches("[A-Z]{6}[A-Z0-9]{2}[A-Z0-9]{3}");
        }
        return false;
    }

    private void addViolation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }
}
