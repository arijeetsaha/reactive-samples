package com.arijeet.exception;

public class PostException extends RuntimeException {
    String message;
    public PostException(String message) {
        super(message);
        this.message = message;
    }

    public PostException(Throwable ex) {
        super(ex);
        this.message = ex.getMessage();
    }
}
