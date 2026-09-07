package com.enrollment.controller;

import com.enrollment.common.Result;
import com.enrollment.entity.Application;
import com.enrollment.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/application")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @GetMapping("/page")
    public Result<?> getPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam(required = false) String studentName,
                             @RequestParam(required = false) Integer status) {
        return Result.success(applicationService.getPage(pageNum, pageSize, studentName, status));
    }

    @PostMapping
    public Result<?> add(@RequestBody Application application) {
        applicationService.add(application);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Application application) {
        applicationService.update(application);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        applicationService.delete(id);
        return Result.success();
    }

    @PutMapping("/audit/{id}")
    public Result<?> audit(@PathVariable Long id, @RequestBody Map<String, Object> params) {
        Integer status = (Integer) params.get("status");
        String remark = (String) params.get("remark");
        applicationService.audit(id, status, remark);
        return Result.success();
    }
}
