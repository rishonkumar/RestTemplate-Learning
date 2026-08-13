package com.rishon.orderintegration.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.rishon.orderintegration.dto.response.ErrorResponse;

/**
 * Maps exceptions to HTTP responses (400, 401, 403, 500, timeouts, connection failures).
 */

@RestControllerAdvice

public class GlobalExceptionHandler {

    @ExceptionHandler(DownstreamServiceException.class)

    public ResponseEntity<ErrorResponse> handleDownstreamException(

        DownstreamServiceException ex) {
        ErrorResponse errorResponse = new ErrorResponse("DOWNSTREAM_ERROR", ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(errorResponse);

    }

}
