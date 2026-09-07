package com.xiaou.controller;

import com.xiaou.common.Result;
import com.xiaou.entity.SystemConfig;
import com.xiaou.service.SystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system-config")
public class SystemConfigController {

    @Autowired
    private SystemConfigService systemConfigService;

    @GetMapping
    public Result<List<SystemConfig>> list() {
        List<SystemConfig> configs = systemConfigService.findAll();
        return Result.success(configs);
    }

    @GetMapping("/{id}")
    public Result<SystemConfig> getById(@PathVariable Long id) {
        SystemConfig config = systemConfigService.findById(id);
        return Result.success(config);
    }

    @GetMapping("/key/{key}")
    public Result<SystemConfig> getByKey(@PathVariable String key) {
        SystemConfig config = systemConfigService.findByKey(key);
        return Result.success(config);
    }

    @PutMapping("/{id}")
    public Result<SystemConfig> update(@PathVariable Long id, @RequestBody SystemConfig config) {
        config.setId(id);
        SystemConfig updatedConfig = systemConfigService.update(config);
        return Result.success(updatedConfig);
    }
}

