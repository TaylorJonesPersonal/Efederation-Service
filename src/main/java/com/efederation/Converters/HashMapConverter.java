package com.efederation.Converters;

import com.efederation.Model.WrestlerAttributes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class HashMapConverter implements AttributeConverter<WrestlerAttributes, String> {

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public String convertToDatabaseColumn(WrestlerAttributes info) {
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
