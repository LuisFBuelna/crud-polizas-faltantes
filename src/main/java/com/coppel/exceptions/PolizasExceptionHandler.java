package com.coppel.exceptions;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.Min;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@ControllerAdvice
public class PolizasExceptionHandler {

    @ExceptionHandler(value = {IncorrectBodyException.class})
    public ResponseEntity<Object> handleIncorrectBodyException
            (IncorrectBodyException bodyException) {
        DTOException dtoException = new DTOException(
                bodyException.getMessage(), HttpStatus.BAD_REQUEST
        );
        return new ResponseEntity<>(dtoException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException
            (NotFoundException notFoundException) {
        DTOException dtoException = new DTOException(
                notFoundException.getMessage(),
                HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(dtoException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {InternalException.class})
    public ResponseEntity<Object> handleInternalException
            (InternalException internalException) {
        DTOException dtoException = new DTOException(
                internalException.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(dtoException, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {PolizasTimeOutException.class})
    public ResponseEntity<Object> handlePolizasTimeOutException
            (DataAccessException exception) {
        DTOException dtoException = new DTOException(
                exception.getMessage(),
                HttpStatus.GATEWAY_TIMEOUT);
        return new ResponseEntity<>(dtoException, HttpStatus.GATEWAY_TIMEOUT);
    }

    @ExceptionHandler(value = {DataAccessException.class})
    public ResponseEntity<Object> handleDataAccessException
            (DataAccessException exception) {
        DTOException dtoException = new DTOException(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(dtoException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<DTOException> handleNullPointerException(NullPointerException e) {
        DTOException exception = new DTOException(
                e.getMessage(),
                HttpStatus.BAD_REQUEST);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<DTOException> handleGlobalException(ConstraintViolationException e) {
        DTOException exception = new DTOException(
                e.getMessage(),
                HttpStatus.BAD_REQUEST);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<DTOException> InvalidParam(MethodArgumentNotValidException e) {
        DTOException exception = new DTOException(
                e.getBody().getDetail(),
                HttpStatus.BAD_REQUEST);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<DTOException> handleException
            (Exception exception) {
        DTOException dtoException = new DTOException(
                exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(dtoException, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
