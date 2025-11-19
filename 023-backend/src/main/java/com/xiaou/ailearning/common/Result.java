package com.xiaou.ailearning.common;

import lombok.Data;

/**
 * 统一响应结果
 */
@Data
public class Result<T> {
    
    private Integer code;
    private String message;
    private T data;
    
    private Result() {}
    
    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.code = 200;
        result.message = "成功";
        return result;
    }
    
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.code = 200;
        result.message = "成功";
        result.data = data;
        return result;
    }
    
    public static <T> Result<T> success(String message, T data) {
        Result<T> result = new Result<>();
        result.code = 200;
        result.message = message;
        result.data = data;
        return result;
    }
    
    public static <T> Result<T> error() {
        Result<T> result = new Result<>();
        result.code = 500;
        result.message = "系统错误";
        return result;
    }
    
    public static <T> Result<T> error(String message) {
        Result<T> result = new Result<>();
        result.code = 500;
        result.message = message;
        return result;
    }
    
    public static <T> Result<T> error(Integer code, String message) {
        Result<T> result = new Result<>();
        result.code = code;
        result.message = message;
        return result;
    }
}