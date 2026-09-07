package com.xiaou.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaou.common.Result;
import com.xiaou.entity.SystemConfig;
import com.xiaou.service.SystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/config")
@CrossOrigin
public class SystemConfigController {

    @Autowired
    private SystemConfigService configService;

    @GetMapping("/list")
    public Result<?> list() {
        return Result.success(configService.list());
    }

    @GetMapping("/{key}")
    public Result<?> getByKey(@PathVariable String key) {
        QueryWrapper<SystemConfig> wrapper = new QueryWrapper<>();
        wrapper.eq("config_key", key);
        SystemConfig config = configService.getOne(wrapper);
        
        if (config == null) {
            return Result.error("配置不存在");
        }
        
        return Result.success(config);
    }

    @PutMapping
    public Result<?> update(@RequestBody SystemConfig config) {
        QueryWrapper<SystemConfig> wrapper = new QueryWrapper<>();
        wrapper.eq("config_key", config.getConfigKey());
        SystemConfig dbConfig = configService.getOne(wrapper);
        
        if (dbConfig == null) {
            return Result.error("配置不存在");
        }
        
        dbConfig.setConfigValue(config.getConfigValue());
        configService.updateById(dbConfig);
        
        return Result.success("更新成功");
    }

    @PostMapping
    public Result<?> add(@RequestBody SystemConfig config) {
        configService.save(config);
        return Result.success("添加成功");
    }
}

