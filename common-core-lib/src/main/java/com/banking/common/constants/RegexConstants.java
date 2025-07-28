package com.banking.common.constants;

public final class RegexConstants {

    private RegexConstants() {}

    public static final String UUID_V4 = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-4[0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$";

    public static final String EMAIL = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    public static final String USERNAME = "^[a-zA-Z0-9._-]{3,32}$";
    public static final String FULL_NAME = "^[a-zA-ZÀ-ỹ\\s.'-]{2,64}$";
    public static final String PHONE_VN = "^(\\+84|0)[1-9][0-9]{8}$";

    public static final String PASSWORD_STRONG = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*#?&^])[A-Za-z\\d@$!%*#?&^]{8,64}$";
    public static final String PASSWORD_WEAK = "^.{6,}$"; // minimum length fallback

    public static final String ISO_DATE = "^\\d{4}-\\d{2}-\\d{2}$";
    public static final String ISO_DATETIME = "^\\d{4}-\\d{2}-\\d{2}[T ]\\d{2}:\\d{2}(:\\d{2})?([Zz]|[+-]\\d{2}:\\d{2})?$";

    public static final String INTEGER = "^-?\\d+$";
    public static final String DECIMAL = "^-?\\d+(\\.\\d+)?$";
    public static final String POSITIVE_AMOUNT = "^\\d{1,13}(\\.\\d{1,4})?$"; // up to trillions with 4 decimal places

    public static final String CURRENCY_CODE = "^[A-Z]{3}$";         // USD, EUR, VND, etc.
    public static final String COUNTRY_CODE = "^[A-Z]{2}$";          // ISO 3166-1 alpha-2 (e.g., VN, US, GB)

    public static final String IBAN = "^[A-Z]{2}\\d{2}[A-Z0-9]{11,30}$";
    public static final String SWIFT_CODE = "^[A-Z]{6}[A-Z0-9]{2}([A-Z0-9]{3})?$";
    public static final String BANK_ACCOUNT_NUMBER = "^[0-9]{8,20}$";

    public static final String IPV4 = "^((25[0-5]|2[0-4]\\d|[0-1]?\\d\\d?)\\.){3}(25[0-5]|2[0-4]\\d|[0-1]?\\d\\d?)$";
    public static final String URL = "^(https?://)?([\\w.-]+)(:[0-9]{1,5})?(/[\\w./%-]*)?$";
    public static final String USER_AGENT = "^[\\w\\s/;().,-]{3,512}$";

    public static final String NON_BLANK = ".*\\S.*";
    public static final String BASE64 = "^[A-Za-z0-9+/=]+$";
    public static final String HEX_64 = "^[0-9a-fA-F]{64}$";

}
