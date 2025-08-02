package com.banking.common.security;

public enum MfaType {

    TOTP,
    SMS,
    EMAIL,
    BIOMETRIC;

    public static boolean isValid(String type){
        try{
            MfaType.valueOf(type);
            return true;
        }catch (IllegalArgumentException e){
            return false;
        }
    }
}
