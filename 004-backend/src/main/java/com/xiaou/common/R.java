package com.xiaou.common;

import lombok.Data;
import java.util.HashMap;
import java.util.Map;

@Data
public class R {
    private Integer code;
    private String msg;
    private Object data;
    
    public static R ok() {
        R r = new R();
        r.setCode(200);
        r.setMsg("success");
        return r;
    }
    
    public static R ok(Object data) {
        R r = new R();
        r.setCode(200);
        r.setMsg("success");
        r.setData(data);
        return r;
    }
    
    public static R ok(String msg, Object data) {
        R r = new R();
        r.setCode(200);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }
    
    public static R error() {
        R r = new R();
        r.setCode(500);
        r.setMsg("error");
        return r;
    }
    
    public static R error(String msg) {
        R r = new R();
        r.setCode(500);
        r.setMsg(msg);
        return r;
    }
    
    public static R error(Integer code, String msg) {
        R r = new R();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }
    
    public R put(String key, Object value) {
        if (this.data == null) {
            this.data = new HashMap<>();
        }
        ((Map<String, Object>) this.data).put(key, value);
        return this;
    }
}

