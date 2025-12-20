package com.xiaou.snack.wms.common;

public class ApiException extends RuntimeException {
    private final Integer code;

    public ApiException(String message) {
        super(message);
        this.code = 500;
    }

    public ApiException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
