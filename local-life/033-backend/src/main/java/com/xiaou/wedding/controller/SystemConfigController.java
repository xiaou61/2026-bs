package com.xiaou.wedding.controller;

import com.xiaou.wedding.common.Result;
import com.xiaou.wedding.entity.SystemConfig;
import com.xiaou.wedding.service.SystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/config")
public class SystemConfigController {

    @Autowired
    private SystemConfigService systemConfigService;

    @GetMapping("/{key}")
    public Result<SystemConfig> get(@PathVariable String key) {
        return Result.success(systemConfigService.get(key));
    }

    @GetMapping("/list")
    public Result<List<SystemConfig>> list() {
        return Result.success(systemConfigService.list());
    }

    @PostMapping
    public Result<Void> save(@RequestBody SystemConfig config) {
        systemConfigService.save(config);
        return Result.success();
    }
}
