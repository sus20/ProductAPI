package com.example.productservice.core.exception;

public class InvalidParameterException extends RuntimeException{
    public InvalidParameterException(String name){
        super("Invalid parameter: " + name);
    }
}
