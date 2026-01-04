package com.xiaou.dreamdonation.common;

import lombok.Data;

@Data
public class Result&lt;T&gt; {
    private Integer code;
    private String message;
    private T data;

    public static &lt;T&gt; Result&lt;T&gt; success() {
        Result&lt;T&gt; result = new Result&lt;&gt;();
        result.setCode(200);
        result.setMessage("success");
        return result;
    }

    public static &lt;T&gt; Result&lt;T&gt; success(T data) {
        Result&lt;T&gt; result = new Result&lt;&gt;();
        result.setCode(200);
        result.setMessage("success");
        result.setData(data);
        return result;
    }

    public static &lt;T&gt; Result&lt;T&gt; error(String message) {
        Result&lt;T&gt; result = new Result&lt;&gt;();
        result.setCode(500);
        result.setMessage(message);
        return result;
    }

    public static &lt;T&gt; Result&lt;T&gt; error(Integer code, String message) {
        Result&lt;T&gt; result = new Result&lt;&gt;();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
}
