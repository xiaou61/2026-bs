package com.textintegrity.controller;

import com.github.pagehelper.PageInfo;
import com.textintegrity.common.Result;
import com.textintegrity.service.AccessLogService;
import com.textintegrity.service.AuthService;
import com.textintegrity.service.CrudService;
import com.textintegrity.service.IntegrityService;
import com.textintegrity.utils.Filters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/rectification")
public class RectificationController {

    @Autowired
    private CrudService crudService;

    @Autowired
    private IntegrityService integrityService;

    @Autowired
    private AuthService authService;

    @Autowired
    private AccessLogService accessLogService;

    @GetMapping("/page")
    public Result<PageInfo<Map<String, Object>>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                                      @RequestParam(defaultValue = "10") Integer pageSize,
                                                      String keyword,
                                                      Long warningId,
                                                      Long studentId,
                                                      Integer status) {
        return Result.success(crudService.page("rectification", pageNum, pageSize, keyword, Filters.of("warningId", warningId, "studentId", studentId, "status", status)));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody Map<String, Object> data) {
        authService.assertStudent(role);
        data.putIfAbsent("studentId", userId);
        data.putIfAbsent("status", 0);
        crudService.save("rectification", data);
        accessLogService.record(userId, "整改跟踪", "新增", "新增整改：" + data.get("warningId"));
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody Map<String, Object> data) {
        authService.assertStudent(role);
        crudService.save("rectification", data);
        accessLogService.record(userId, "整改跟踪", "编辑", "编辑整改：" + data.get("id"));
        return Result.success();
    }

    @PutMapping("/review")
    public Result<Void> review(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody Map<String, Object> data) {
        authService.assertReviewer(role);
        integrityService.reviewRectification(data);
        accessLogService.record(userId, "整改跟踪", "审核", "审核整改：" + data.get("id"));
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertStudent(role);
        crudService.delete("rectification", id);
        accessLogService.record(userId, "整改跟踪", "删除", "删除整改：" + id);
        return Result.success();
    }
}
