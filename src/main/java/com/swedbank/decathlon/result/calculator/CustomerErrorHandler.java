package com.swedbank.decathlon.result.calculator;

import com.swedbank.decathlon.result.calculator.api.DecathlonResultCalculatorController;
import com.swedbank.decathlon.result.calculator.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = DecathlonResultCalculatorController.class)
public class CustomerErrorHandler {
    private static final String INTERNAL_SERVER_ERROR = "Internal server error";

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<CustomError> applicationException(ApplicationException e) {
        return new ResponseEntity<>(
                new CustomError(
                        HttpStatus.BAD_REQUEST.value(),
                        e.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<CustomError> applicationException() {
        return new ResponseEntity<>(
                new CustomError(
                        HttpStatus.BAD_REQUEST.value(),
                        INTERNAL_SERVER_ERROR),
                HttpStatus.BAD_REQUEST
        );
    }
}

class CustomError {
    private int status;
    private String message;

    CustomError(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
