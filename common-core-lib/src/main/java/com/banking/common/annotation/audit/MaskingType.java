package com.banking.common.annotation.audit;

/**
 * Defines types of data masking for sensitive information
 */
public enum MaskingType {
    FULL, // Completely mask: "****"
    PARTIAL, // Show first/last chars: "A***@***.com"
    CARD_NUMBER, // Show last 4 digits: "**** **** **** 1234"
    PHONE, // Show country code: "+1 *** *** **34"
    EMAIL, // Show domain: "***@example.com"
    CUSTOM // Use custom pattern

}
