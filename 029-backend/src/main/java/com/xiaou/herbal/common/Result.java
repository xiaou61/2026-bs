package com.xiaou.herbal.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private Integer code;
    private String message;
    private T data;

    public static <T> Result<T> success() {
        return Result.builder()
                .code(200)
                .message("success")
                .build();
    }

    public static <T> Result<T> success(T data) {
        return Result.builder()
                .code(200)
                .message("success")
                .data(data)
                .build();
    }

    public static <T> Result<T> error(Integer code, String message) {
        return Result.builder()
                .code(code)
                .message(message)
                .build();
    }

    public static <T> Result<T> error(String message) {
        return Result.builder()
                .code(500)
                .message(message)
                .build();
    }
}
