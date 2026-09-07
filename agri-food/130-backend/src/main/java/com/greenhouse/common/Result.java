package com.greenhouse.common;

import lombok.Data;

@Data
public class Result<T> {
    private int code;
    private String message;
    private T data;

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage("success");
        result.setData(data);
        return result;
    }

    public static Result<Void> success() {
        return success(null);
    }

    public static Result<Void> fail(String message) {
        Result<Void> result = new Result<>();
        result.setCode(500);
        result.setMessage(message);
        return result;
    }
}
