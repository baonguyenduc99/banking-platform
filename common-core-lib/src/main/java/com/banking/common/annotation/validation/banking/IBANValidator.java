package com.banking.common.annotation.validation.banking;

import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.banking.common.annotation.validation.ValidIBAN;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

/**
 * Validator for IBAN (International Bank Account Number).
 * Performs format validation and MOD-97 check digit verification.
 * 
 * @author Banking Platform Team
 * @since 1.0.0
 */
@Slf4j
public class IBANValidator implements ConstraintValidator<ValidIBAN, String> {

    private Set<String> allowedCountries;
    private Map<String, Integer> ibanLengths;

    private static final Pattern COUNTRY_CODE_PATTERN = Pattern.compile("[A-Z]{2}");
    private static final Pattern CHECK_DIGITS_PATTERN = Pattern.compile("\\d{2}");
    private static final Pattern ACCOUNT_PATTERN = Pattern.compile("[A-Z0-9]+");
    private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\s+");

    // IBAN lengths by country code
    private static final Map<String, Integer> DEFAULT_IBAN_LENGTHS = Map.ofEntries(
            Map.entry("AD", 24), Map.entry("AE", 23), Map.entry("AL", 28), Map.entry("AT", 20),
            Map.entry("AZ", 28), Map.entry("BA", 20), Map.entry("BE", 16), Map.entry("BG", 22),
            Map.entry("BH", 22), Map.entry("BR", 29), Map.entry("BY", 28), Map.entry("CH", 21),
            Map.entry("CR", 22), Map.entry("CY", 28), Map.entry("CZ", 24), Map.entry("DE", 22),
            Map.entry("DK", 18), Map.entry("DO", 28), Map.entry("EE", 20), Map.entry("EG", 29),
            Map.entry("ES", 24), Map.entry("FI", 18), Map.entry("FO", 18), Map.entry("FR", 27),
            Map.entry("GB", 22), Map.entry("GE", 22), Map.entry("GI", 23), Map.entry("GL", 18),
            Map.entry("GR", 27), Map.entry("GT", 28), Map.entry("HR", 21), Map.entry("HU", 28),
            Map.entry("IE", 22), Map.entry("IL", 23), Map.entry("IS", 26), Map.entry("IT", 27),
            Map.entry("JO", 30), Map.entry("KW", 30), Map.entry("KZ", 20), Map.entry("LB", 28),
            Map.entry("LC", 32), Map.entry("LI", 21), Map.entry("LT", 20), Map.entry("LU", 20),
            Map.entry("LV", 21), Map.entry("MC", 27), Map.entry("MD", 24), Map.entry("ME", 22),
            Map.entry("MK", 19), Map.entry("MR", 27), Map.entry("MT", 31), Map.entry("MU", 30),
            Map.entry("NL", 18), Map.entry("NO", 15), Map.entry("PK", 24), Map.entry("PL", 28),
            Map.entry("PS", 29), Map.entry("PT", 25), Map.entry("QA", 29), Map.entry("RO", 24),
            Map.entry("RS", 22), Map.entry("SA", 24), Map.entry("SE", 24), Map.entry("SI", 19),
            Map.entry("SK", 24), Map.entry("SM", 27), Map.entry("TN", 24), Map.entry("TR", 26),
            Map.entry("UA", 29), Map.entry("VG", 24), Map.entry("XK", 20));

    @Override
    public void initialize(ValidIBAN constraintAnnotation) {
        String[] countries = constraintAnnotation.countries();
        if (countries.length > 0) {
            this.allowedCountries = Set.of(countries);
        }
        this.ibanLengths = DEFAULT_IBAN_LENGTHS;
    }

    @Override
    public boolean isValid(String iban, ConstraintValidatorContext context) {
        if (StringUtils.isBlank(iban)) {
            return true; // use @NotBlank for null or empty checks
        }

        if (iban.length() > 40) {
            addViolation(context, "IBAN exceeds maximum length");
            return false;
        }

        String cleanIban = WHITESPACE_PATTERN.matcher(iban).replaceAll("").toUpperCase();

        if (cleanIban.length() > 34) {
            addViolation(context, "IBAN exceeds maximum length");
            return false;
        }

        try {
            if (!isValidFormat(cleanIban)) {
                addViolation(context, "Invalid IBAN format");
                return false;
            }

            String countryCode = cleanIban.substring(0, 2);
            if (allowedCountries != null && !allowedCountries.contains(countryCode)) {
                addViolation(context, "Country not allowed: " + countryCode);
                return false;
            }

            // Length validation
            if (!isValidLength(cleanIban, countryCode)) {
                addViolation(context, "Invalid IBAN length for country: " + countryCode);
                return false;
            }

            // MOD-97 check digit validation
            if (!isValidCheckDigits(cleanIban)) {
                addViolation(context, "Invalid IBAN check digits");
                return false;
            }

            return true;
        } catch (Exception e) {
            log.error("Error validating IBAN: {}", e.getMessage());
            addViolation(context, "Error validating IBAN");
            return false;
        }
    }

    private boolean isValidFormat(String iban) {
        if (iban.length() < 4)
            return false;
        return COUNTRY_CODE_PATTERN.matcher(iban.substring(0, 2)).matches()
                && CHECK_DIGITS_PATTERN.matcher(iban.substring(2, 4)).matches()
                && ACCOUNT_PATTERN.matcher(iban.substring(4)).matches();
    }

    private void addViolation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }

    private boolean isValidLength(String iban, String countryCode) {
        Integer expectedLength = ibanLengths.get(countryCode);
        return expectedLength != null && iban.length() == expectedLength;
    }

    private boolean isValidCheckDigits(String iban) {
        String rearranged = iban.substring(4) + iban.substring(0, 4);
        StringBuilder numeric = new StringBuilder();
        for (char c : rearranged.toCharArray()) {
            numeric.append(Character.isLetter(c) ? c - 'A' + 10 : c);
        }

        String numStr = numeric.toString();
        int remainder = 0;
        for (int i = 0; i < numStr.length(); i += 7) { // chunk processing
            int end = Math.min(i + 7, numStr.length());
            remainder = Integer.parseInt(remainder + numStr.substring(i, end)) % 97;
        }
        return remainder == 1;
    }
}
