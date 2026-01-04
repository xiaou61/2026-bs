package com.xiaou.dreamdonation.config;

import com.xiaou.dreamdonation.common.Result;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public Result&lt;?&gt; handleRuntimeException(RuntimeException e) {
        return Result.error(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result&lt;?&gt; handleValidationException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldError() != null
                ? e.getBindingResult().getFieldError().getDefaultMessage()
                : "参数验证失败";
        return Result.error(400, message);
    }

    @ExceptionHandler(BindException.class)
    public Result&lt;?&gt; handleBindException(BindException e) {
        String message = e.getBindingResult().getFieldError() != null
                ? e.getBindingResult().getFieldError().getDefaultMessage()
                : "参数验证失败";
        return Result.error(400, message);
    }

    @ExceptionHandler(Exception.class)
    public Result&lt;?&gt; handleException(Exception e) {
        e.printStackTrace();
        return Result.error("系统内部错误");
    }
}
