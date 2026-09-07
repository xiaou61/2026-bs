package com.adoption.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ResultTest {

    @Test
    void shouldBuildSuccessResult() {
        Result<String> result = Result.success("ok");
        Assertions.assertEquals(Integer.valueOf(200), result.getCode());
        Assertions.assertEquals("success", result.getMessage());
        Assertions.assertEquals("ok", result.getData());
    }
}
