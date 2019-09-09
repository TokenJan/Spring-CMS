package com.thoughtworks.cms.application.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import java.io.IOException;
import java.util.Map;

import static antlr.Utils.error;

public class HashMapConverter implements AttributeConverter<Map<String, String>, String> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<String, String> customerInfo) {

        String customerInfoJson = null;
        try {
            customerInfoJson = objectMapper.writeValueAsString(customerInfo);
        } catch (final JsonProcessingException e) {
            error("JSON writing error", e);
        }

        return customerInfoJson;
    }

    @Override
    public Map<String, String> convertToEntityAttribute(String customerInfoJSON) {

        Map<String, String> customerInfo = null;
        try {
            customerInfo = objectMapper.readValue(customerInfoJSON, Map.class);
        } catch (final IOException e) {
            error("JSON reading error", e);
        }

        return customerInfo;
    }

}