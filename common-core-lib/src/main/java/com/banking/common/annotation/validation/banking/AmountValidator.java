package com.banking.common.annotation.validation.banking;

import java.math.BigDecimal;

import com.banking.common.annotation.validation.ValidAmount;
import com.banking.common.constants.CurrencyScales;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

/**
 * Validator for monetary amounts in banking operations.
 * 
 * @author Banking Platform Team
 * @since 1.0.0
 */
@Slf4j
public class AmountValidator implements ConstraintValidator<ValidAmount, BigDecimal> {

    private BigDecimal min;
    private BigDecimal max;
    private int decimalPlaces;
    private String currency;
    private boolean allowNegative;

    @Override
    public void initialize(ValidAmount constraintAnnotation) {
        this.min = new BigDecimal(constraintAnnotation.min());
        this.max = new BigDecimal(constraintAnnotation.max());
        this.decimalPlaces = constraintAnnotation.decimalPlaces();
        this.currency = constraintAnnotation.currency();
        this.allowNegative = constraintAnnotation.allowNegative();
    }

    @Override
    public boolean isValid(BigDecimal amount, ConstraintValidatorContext context) {

        try {
            if (!allowNegative && amount.compareTo(BigDecimal.ZERO) < 0) {
                addViolation(context, "Negative amounts not allowed");
                return false;
            }

            if (amount.compareTo(min) < 0) {
                addViolation(context, "Amount below minimum: " + min);
                return false;
            }

            if (amount.compareTo(max) > 0) {
                addViolation(context, "Amount above maximum: " + max);
                return false;
            }

            if (!isValidDecimalPlaces(amount)) {
                addViolation(context, "Too many decimal places. Maximum: " + decimalPlaces);
                return false;
            }

            if (!currency.isEmpty() && !isValidForCurrency(amount)) {
                addViolation(context, "Invalid amount for currency: " + currency);
                return false;
            }

            return true;

        } catch (Exception e) {
            log.warn("Amount validation error for value: {}", amount, e);
            addViolation(context, "Amount validation failed");
            return false;
        }
    }

    private boolean isValidDecimalPlaces(BigDecimal amount) {
        return amount.scale() <= decimalPlaces;
    }

    private boolean isValidForCurrency(BigDecimal amount) {
        int allowedScale = CurrencyScales.getScale(currency);
        return amount.scale() <= allowedScale;
    }

    private void addViolation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }
}
