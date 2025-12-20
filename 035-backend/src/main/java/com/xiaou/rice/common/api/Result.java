package com.xiaou.rice.common.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class Result<T> {
    private Integer code;
    private String msg;
    private T data;

    public static <T> Result<T> ok() {
        return ok(null);
    }

    public static <T> Result<T> ok(T data) {
        return of(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), data);
    }

    public static <T> Result<T> fail(ResultCode code) {
        return of(code.getCode(), code.getMsg(), null);
    }

    public static <T> Result<T> fail(int code, String msg) {
        return of(code, msg, null);
    }
}
