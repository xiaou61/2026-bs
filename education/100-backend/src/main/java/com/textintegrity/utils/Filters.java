package com.textintegrity.utils;

import java.util.HashMap;
import java.util.Map;

public class Filters {

    public static Map<String, Object> of(Object... values) {
        Map<String, Object> result = new HashMap<>();
        for (int i = 0; i < values.length - 1; i += 2) {
            Object value = values[i + 1];
            if (value != null && !"".equals(String.valueOf(value))) {
                result.put(String.valueOf(values[i]), value);
            }
        }
        return result;
    }
}

