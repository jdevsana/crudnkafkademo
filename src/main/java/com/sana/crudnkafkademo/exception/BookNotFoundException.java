package com.sana.crudnkafkademo.exception;

public class BookNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 7708967236556190549L;

    public BookNotFoundException() {
    }

    public BookNotFoundException(String message) {
        super(message);
    }
}
