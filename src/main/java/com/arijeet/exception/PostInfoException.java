package com.arijeet.exception;

public class PostInfoException extends RuntimeException {
    String message;

    public PostInfoException(String message) {
        super(message);
        this.message = message;

    }
}
