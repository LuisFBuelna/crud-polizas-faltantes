package com.coppel.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class PolizasExceptionHandler {

    @ExceptionHandler(value = {IncorrectBodyException.class})
    public ResponseEntity<Object> handleIncorrectBodyException (IncorrectBodyException bodyException) {
        DTOException dtoException = new DTOException(
                bodyException.getMessage(),
                bodyException.getCause(), HttpStatus.BAD_REQUEST
        );
        return new ResponseEntity<>(dtoException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException
            (NotFoundException notFoundException) {
        DTOException dtoException = new DTOException(
                notFoundException.getMessage(),
                notFoundException.getCause(),
                HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(notFoundException, HttpStatus.NOT_FOUND);
    }
}
