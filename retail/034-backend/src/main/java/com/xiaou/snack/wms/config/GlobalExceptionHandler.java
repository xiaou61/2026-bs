package com.xiaou.snack.wms.config;

import com.xiaou.snack.wms.common.ApiException;
import com.xiaou.snack.wms.common.Result;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ApiException.class)
    public Result<Object> handleApi(ApiException ex) {
        return Result.error(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class, HttpMessageNotReadableException.class})
    public Result<Object> handleBadRequest(Exception ex) {
        return Result.error(400, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result<Object> handleOther(Exception ex) {
        return Result.error(500, ex.getMessage());
    }
}
