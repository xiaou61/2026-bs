package com.xiaou.rice.common.exception;

import com.xiaou.rice.common.api.ResultCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final int code;

    public BusinessException(ResultCode code) {
        super(code.getMsg());
        this.code = code.getCode();
    }

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }
}
