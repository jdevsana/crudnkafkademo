package com.sana.crudnkafkademo.exception;

public class BookIsbnNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -2700071549463751900L;
    public BookIsbnNotFoundException() {
    }

    public BookIsbnNotFoundException(String message) {
        super(message);
    }
}
