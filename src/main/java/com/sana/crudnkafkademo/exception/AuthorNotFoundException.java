package com.sana.crudnkafkademo.exception;

public class AuthorNotFoundException extends RuntimeException {
    public AuthorNotFoundException() {
    }

    public AuthorNotFoundException(String message) {
        super(message);
    }
}
