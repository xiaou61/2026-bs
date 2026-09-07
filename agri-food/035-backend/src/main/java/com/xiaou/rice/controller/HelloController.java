package com.xiaou.rice.controller;

import com.xiaou.rice.common.api.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/health")
public class HelloController {
    @GetMapping
    public Result<String> ping() {
        return Result.ok("ok");
    }
}
