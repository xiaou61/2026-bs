package com.fraud.common;

public class BusinessException extends RuntimeException {

    private Integer code;

    public BusinessException(String message) {
        this(isPermissionMessage(message) ? 403 : 500, message);
    }

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    private static boolean isPermissionMessage(String message) {
        return message != null && message.contains("无权限");
    }
}
