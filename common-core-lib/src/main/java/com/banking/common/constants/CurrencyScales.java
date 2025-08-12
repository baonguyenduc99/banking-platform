package com.banking.common.constants;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Currency decimal precision rules based on ISO-4217.
 * Key: ISO currency code
 * Value: allowed decimal places (scale)
 */
public final class CurrencyScales {

    /**
     * Immutable map of currency decimal precision.
     */
    public static final Map<String, Integer> SCALE_MAP;

    static {
        Map<String, Integer> map = new HashMap<>();
        map.put("JPY", 0);
        map.put("KRW", 0);
        map.put("VND", 0);
        map.put("USD", 2);
        map.put("EUR", 2);
        map.put("GBP", 2);
        map.put("CAD", 2);
        map.put("AUD", 2);
        map.put("CHF", 2);
        map.put("SGD", 2);
        map.put("THB", 2);

        map.put("BHD", 3);
        map.put("KWD", 3);
        map.put("OMR", 3);

        SCALE_MAP = Collections.unmodifiableMap(map);
    }

    private CurrencyScales() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Gets allowed scale for the given currency code.
     * Defaults to 2 if unknown.
     */
    public static int getScale(String currencyCode) {
        return SCALE_MAP.getOrDefault(currencyCode, 2);
    }
}