package com.example.productservice.adapter.in.kafka.serde;

import com.example.productservice.core.model.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ObjectDeserializer {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Customer fromByte(byte[] data, Class<Customer> clazz) throws IOException {
        return objectMapper.readValue(data, clazz);
    }
}
