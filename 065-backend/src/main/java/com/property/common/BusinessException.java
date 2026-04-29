package com.property.common;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final Integer code;

    public BusinessException(String message) {
        super(message);
        this.code = message != null && (message.contains("无权限") || message.startsWith("仅")) ? 403 : 500;
    }

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
