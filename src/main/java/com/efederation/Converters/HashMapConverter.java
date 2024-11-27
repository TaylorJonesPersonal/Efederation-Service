package com.efederation.Converters;

import com.efederation.Model.WrestlerAttributes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import java.io.IOException;

public class HashMapConverter implements AttributeConverter<WrestlerAttributes, String> {

    @Override
    public String convertToDatabaseColumn(WrestlerAttributes info) {
        ObjectMapper objectMapper = new ObjectMapper();
        String infoJSON = null;
        try {
            infoJSON = objectMapper.writeValueAsString(info);
        } catch (final JsonProcessingException e) {
            e.printStackTrace();
        }
        return infoJSON;
    }

    @Override
    public WrestlerAttributes convertToEntityAttribute(String infoJSON) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        WrestlerAttributes info = null;
        try {
            info = objectMapper.readValue(infoJSON, new TypeReference<>() {
            });
        } catch(final IOException e) {
            e.printStackTrace();
        }
        return info;
    }
}
