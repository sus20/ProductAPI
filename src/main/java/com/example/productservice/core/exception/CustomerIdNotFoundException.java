package com.example.productservice.core.exception;

public class CustomerIdNotFoundException extends RuntimeException {
    public CustomerIdNotFoundException(String customerId) {
        super("Customer with ID " + customerId + " not found.");
    }
}