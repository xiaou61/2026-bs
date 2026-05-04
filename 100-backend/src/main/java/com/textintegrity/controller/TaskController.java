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
@RequestMapping("/api/task")
public class TaskController {

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
                                                      Integer status,
                                                      String priority) {
        return Result.success(crudService.page("task", pageNum, pageSize, keyword, Filters.of("status", status, "priority", priority)));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody Map<String, Object> data) {
        authService.assertReviewer(role);
        data.putIfAbsent("reviewerId", userId);
        data.putIfAbsent("status", 0);
        data.putIfAbsent("taskNo", "DT" + System.currentTimeMillis());
        crudService.save("task", data);
        accessLogService.record(userId, "检测任务", "新增", "新增检测任务：" + data.get("taskName"));
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody Map<String, Object> data) {
        authService.assertReviewer(role);
        crudService.save("task", data);
        accessLogService.record(userId, "检测任务", "编辑", "编辑检测任务：" + data.get("taskName"));
        return Result.success();
    }

    @PutMapping("/run/{id}")
    public Result<Void> run(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertReviewer(role);
        integrityService.runTask(id);
        accessLogService.record(userId, "检测任务", "启动", "启动检测任务：" + id);
        return Result.success();
    }

    @PutMapping("/finish/{id}")
    public Result<Void> finish(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertReviewer(role);
        integrityService.finishTask(id);
        accessLogService.record(userId, "检测任务", "完成", "完成检测任务：" + id);
        return Result.success();
    }

    @PutMapping("/reject/{id}")
    public Result<Void> reject(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertReviewer(role);
        integrityService.rejectTask(id);
        accessLogService.record(userId, "检测任务", "驳回", "驳回检测任务：" + id);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertReviewer(role);
        crudService.delete("task", id);
        accessLogService.record(userId, "检测任务", "删除", "删除检测任务：" + id);
        return Result.success();
    }
}
