package com.repair.common;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public Result<String> handleBusinessException(BusinessException e, HttpServletResponse response) {
        response.setStatus(toHttpStatus(e.getCode()));
        return Result.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e, HttpServletResponse response) {
        e.printStackTrace();
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return Result.error("系统异常");
    }

    private int toHttpStatus(Integer code) {
        if (code == null) {
            return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        }
        if (code == 400) {
            return HttpServletResponse.SC_BAD_REQUEST;
        }
        if (code == 401) {
            return HttpServletResponse.SC_UNAUTHORIZED;
        }
        if (code == 403) {
            return HttpServletResponse.SC_FORBIDDEN;
        }
        if (code == 404) {
            return HttpServletResponse.SC_NOT_FOUND;
        }
        return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
    }
}

