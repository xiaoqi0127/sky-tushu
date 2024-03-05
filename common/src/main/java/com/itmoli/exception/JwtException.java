package com.itmoli.exception;

public class JwtException extends RuntimeException{
    public JwtException() {
        super();
    }

    public JwtException(String message) {
        super(message);
    }
}
