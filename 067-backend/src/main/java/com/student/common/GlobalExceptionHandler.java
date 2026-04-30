package com.student.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Result<?>> handleBusinessException(BusinessException e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (e.getCode() != null && e.getCode() == 401) {
            status = HttpStatus.UNAUTHORIZED;
        } else if (e.getCode() != null && e.getCode() == 403) {
            status = HttpStatus.FORBIDDEN;
        }
        return ResponseEntity.status(status).body(Result.error(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result<?>> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.error(e.getMessage()));
    }
}
