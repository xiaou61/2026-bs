package com.xiaou.studyroom.controller;

import com.xiaou.studyroom.common.Result;
import com.xiaou.studyroom.entity.SystemConfig;
import com.xiaou.studyroom.service.SystemConfigService;
import com.xiaou.studyroom.utils.AuthHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/system-config")
@CrossOrigin
public class SystemConfigController {

    @Autowired
    private SystemConfigService systemConfigService;

    @Autowired
    private AuthHelper authHelper;

    @GetMapping("/list")
    public Result<List<SystemConfig>> listConfigs(@RequestHeader("Authorization") String token) {
        authHelper.requireAdmin(token);
        return Result.success(systemConfigService.listAll());
    }

    @PutMapping
    public Result<String> updateConfig(@RequestHeader("Authorization") String token,
                                       @RequestBody Map<String, String> body) {
        authHelper.requireAdmin(token);
        String configKey = body.get("configKey");
        String configValue = body.get("configValue");
        String description = body.get("description");
        return systemConfigService.updateConfig(configKey, configValue, description)
                ? Result.success("系统配置更新成功")
                : Result.error("系统配置更新失败");
    }
}
