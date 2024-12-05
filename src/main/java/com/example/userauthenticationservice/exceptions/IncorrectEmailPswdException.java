package com.example.userauthenticationservice.exceptions;

public class IncorrectEmailPswdException extends RuntimeException {
    public IncorrectEmailPswdException(String message) {
        super(message);
    }
}
