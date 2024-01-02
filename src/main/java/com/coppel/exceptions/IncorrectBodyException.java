package com.coppel.exceptions;

public class IncorrectBodyException extends RuntimeException{
    public IncorrectBodyException(String message) {
        super(message);
    }

    public IncorrectBodyException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectBodyException(String message, RuntimeException ex){
        super(message, ex);
    }
}
