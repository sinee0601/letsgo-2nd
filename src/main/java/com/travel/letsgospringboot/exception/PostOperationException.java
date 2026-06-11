package com.travel.letsgospringboot.exception;

public class PostOperationException extends RuntimeException {
    public PostOperationException(String message) {
        super(message);
    }
}
