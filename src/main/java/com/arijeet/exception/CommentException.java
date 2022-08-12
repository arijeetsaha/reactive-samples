package com.arijeet.exception;

public class CommentException extends RuntimeException {
    String message;
    public CommentException(String message) {
        super(message);
        this.message = message;
    }
}
