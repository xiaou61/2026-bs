package com.xiaou.seniorhealth.exception;

import com.xiaou.seniorhealth.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public ApiResponse<?> handleValidation(Exception e) {
        String msg = e instanceof MethodArgumentNotValidException
                ? ((MethodArgumentNotValidException) e).getBindingResult().getAllErrors().get(0).getDefaultMessage()
                : ((BindException) e).getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ApiResponse.fail(msg);
    }
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ApiResponse<?> handleException(Exception e) {
        return ApiResponse.fail(e.getMessage());
    }
}
