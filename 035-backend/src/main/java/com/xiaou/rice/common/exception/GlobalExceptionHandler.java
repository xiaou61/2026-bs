package com.xiaou.rice.common.exception;

import com.xiaou.rice.common.api.Result;
import com.xiaou.rice.common.api.ResultCode;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBusiness(BusinessException e) {
        return Result.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class, ConstraintViolationException.class, HttpMessageNotReadableException.class})
    public Result<Void> handleValidation(Exception e) {
        return Result.fail(ResultCode.BAD_REQUEST.getCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result<Void> handleOther(Exception e) {
        return Result.fail(ResultCode.SERVER_ERROR.getCode(), resolveMessage(e));
    }

    private String resolveMessage(Throwable throwable) {
        Throwable current = throwable;
        String message = null;
        while (current != null) {
            if (StringUtils.hasText(current.getMessage())) {
                message = current.getMessage();
            }
            current = current.getCause();
        }
        return StringUtils.hasText(message) ? message : ResultCode.SERVER_ERROR.getMsg();
    }
}
