package com.kindergarten.controller;

import com.github.pagehelper.PageInfo;
import com.kindergarten.common.Result;
import com.kindergarten.entity.HealthRecord;
import com.kindergarten.service.HealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/health")
public class HealthController {

    @Autowired
    private HealthService healthService;

    @GetMapping("/list")
    public Result<PageInfo<HealthRecord>> list(@RequestParam(defaultValue = "1") Integer pageNum,
                                               @RequestParam(defaultValue = "10") Integer pageSize,
                                               @RequestParam(required = false) Long childId,
                                               @RequestAttribute("userId") Long userId,
                                               @RequestAttribute("role") String role) {
        return Result.success(healthService.list(childId, userId, role, pageNum, pageSize));
    }

    @PostMapping("/save")
    public Result<String> save(@RequestBody HealthRecord entity,
                               @RequestAttribute("userId") Long userId,
                               @RequestAttribute("role") String role) {
        healthService.save(entity, userId, role);
        return Result.success();
    }
}
