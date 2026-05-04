package com.charity.common;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public Result<String> handleBusinessException(BusinessException e, HttpServletResponse response) {
        response.setStatus(e.getCode() == null ? 500 : e.getCode());
        return Result.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e, HttpServletResponse response) {
        response.setStatus(500);
        e.printStackTrace();
        return Result.error("系统异常");
    }
}
