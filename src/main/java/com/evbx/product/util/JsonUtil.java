package com.evbx.product.util;

import com.evbx.product.exception.JsonParseException;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;

public final class JsonUtil {

    private static final String MODEL_MATCHING_ERROR_MESSAGE = "Parsing error: json structure doesn't match model.";

    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper().configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
                .configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        mapper.setVisibility(mapper.getVisibilityChecker().withGetterVisibility(JsonAutoDetect.Visibility.NONE));
        mapper.registerModule(new JavaTimeModule());
    }

    private JsonUtil() {
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        try {
            return mapper.readValue(json, classOfT);
        } catch (IOException ioEx) {
            throw new JsonParseException(MODEL_MATCHING_ERROR_MESSAGE, ioEx);
        }
    }

    public static <T> T fromJson(String json, TypeReference<T> typeReference) {
        try {
            return mapper.readValue(json, typeReference);
        } catch (IOException ioEx) {
            throw new JsonParseException(MODEL_MATCHING_ERROR_MESSAGE, ioEx);
        }
    }

    public static <T> T fromJson(File file, Class<T> classOfT) {
        try {
            return mapper.readValue(file, classOfT);
        } catch (IOException ioEx) {
            throw new JsonParseException(MODEL_MATCHING_ERROR_MESSAGE, ioEx);
        }
    }

    public static <T> String toJson(T object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (IOException ioEx) {
            throw new JsonParseException(ioEx.getMessage(), ioEx);
        }
    }
}
