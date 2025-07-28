package com.banking.common.utils;

import com.banking.common.exception.ApiException;
import com.banking.common.exception.ErrorCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    private JsonUtils() {
    }

    public static String toJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new ApiException(ErrorCode.JSON_SERIALIZATION_ERROR, "Failed to serialize object to JSON",
                    e);
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new ApiException(ErrorCode.JSON_DESERIALIZATION_FAILED, "Failed to deserialize JSON to "+clazz.getSimpleName() ,e);
        }
    }


    public static boolean isValidJson(String json) {
        try {
            mapper.readTree(json);
            return true;
        } catch (JsonProcessingException e) {
            return false;
        }
    }
}
