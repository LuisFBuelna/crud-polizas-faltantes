package com.coppel.exceptions;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;


public class DTOException {

    private final String message;

    private final HttpStatus httpStatus;


    public DTOException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}

