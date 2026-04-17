package com.xiaou.artist.common;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e) {
        e.printStackTrace();
        String message = e.getMessage();
        if ((message == null || message.isBlank()) && e.getCause() != null) {
            message = e.getCause().getMessage();
        }
        if (message == null || message.isBlank()) {
            message = "服务器内部异常";
        }
        return Result.error(message);
    }
}
