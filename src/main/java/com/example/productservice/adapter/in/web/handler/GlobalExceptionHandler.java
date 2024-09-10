package com.example.productservice.adapter.in.web.handler;

import com.example.productservice.core.exception.ImageProcessingException;
import com.example.productservice.core.exception.InvalidImageFileException;
import com.example.productservice.core.exception.InvalidParameterException;
import com.example.productservice.core.exception.ProductNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ImageProcessingException.class)
    public ResponseEntity<ErrorDetails> handleImageProcessingException(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidImageFileException.class)
    public ResponseEntity<ErrorDetails> handleInvalidImageFileException(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<ErrorDetails> handleInvalidParameterException(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleProductNotFoundException(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> errorMessages = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = fieldName + ": " + error.getDefaultMessage();
            errorMessages.add(errorMessage);
        });

        return new ResponseEntity<>(getErrorsMap(errorMessages), HttpStatus.BAD_REQUEST);
    }


    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> errorMessages = new ArrayList<>();
        errorMessages.add(ex.getRequestPartName() + ": " + ex.getMessage());
        return new ResponseEntity<>(getErrorsMap(errorMessages), HttpStatus.BAD_REQUEST);
    }

    // Handle general Multipart exceptions (e.g., file upload errors)
    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<Map<String, List<String>>> handleMultipartException(MultipartException ex) {
        List<String> errorMessages = new ArrayList<>();
        errorMessages.add("Error processing file: " + ex.getMessage());
        return new ResponseEntity<>(getErrorsMap(errorMessages), HttpStatus.BAD_REQUEST);
    }

    // Generic error handler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, List<String>>> handleGenericException(Exception ex) {
        List<String> errorMessages = new ArrayList<>();
        errorMessages.add("Error: " + ex.getMessage());
        return new ResponseEntity<>(getErrorsMap(errorMessages), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }
}