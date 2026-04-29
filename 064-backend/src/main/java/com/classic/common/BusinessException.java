package com.classic.common;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final Integer code;

    public BusinessException(String message) {
        this(isPermissionMessage(message) ? 403 : 500, message);
    }

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    private static boolean isPermissionMessage(String message) {
        return message != null && (message.contains("无权限") || message.contains("仅用户"));
    }
}
