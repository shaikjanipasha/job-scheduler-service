package com.vtx.jobscheduler.handler;

import java.time.ZonedDateTime;
import com.vtx.jobscheduler.exception.JobNotFoundException;
import com.vtx.jobscheduler.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(JobNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(JobNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ZonedDateTime.now(),
                "Error-001-Job Not Found",
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ZonedDateTime.now(),
                "Internal Server Error",
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
