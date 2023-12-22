package com.coppel.exceptions;

public class PolizasTimeOutException extends RuntimeException{

    public PolizasTimeOutException(String message) {
        super(message);
    }

    public PolizasTimeOutException(String message, Throwable cause) {
        super(message, cause);
    }

}
