package com.example.productservice.core.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String id){
        super("Product with ID " + id + " does not exist.");
    }
}
