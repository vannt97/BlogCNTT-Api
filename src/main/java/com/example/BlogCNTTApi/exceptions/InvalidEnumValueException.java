package com.example.BlogCNTTApi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class InvalidEnumValueException extends RuntimeException{

    public InvalidEnumValueException(String message) {
        super(message);
    }
}
