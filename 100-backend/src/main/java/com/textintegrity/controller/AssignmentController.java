package com.textintegrity.controller;

import com.github.pagehelper.PageInfo;
import com.textintegrity.common.Result;
import com.textintegrity.service.AccessLogService;
import com.textintegrity.service.AuthService;
import com.textintegrity.service.CrudService;
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
@RequestMapping("/api/assignment")
public class AssignmentController {

    @Autowired
    private CrudService crudService;

    @Autowired
    private AuthService authService;

    @Autowired
    private AccessLogService accessLogService;

    @GetMapping("/page")
    public Result<PageInfo<Map<String, Object>>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                                      @RequestParam(defaultValue = "10") Integer pageSize,
                                                      String keyword,
                                                      Long courseId,
                                                      String assignmentType,
                                                      Integer status) {
        return Result.success(crudService.page("assignment", pageNum, pageSize, keyword, Filters.of("courseId", courseId, "assignmentType", assignmentType, "status", status)));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody Map<String, Object> data) {
        authService.assertTeacher(role);
        data.putIfAbsent("teacherId", userId);
        crudService.save("assignment", data);
        accessLogService.record(userId, "作业任务", "新增", "新增作业：" + data.get("title"));
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody Map<String, Object> data) {
        authService.assertTeacher(role);
        crudService.save("assignment", data);
        accessLogService.record(userId, "作业任务", "编辑", "编辑作业：" + data.get("title"));
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertTeacher(role);
        crudService.delete("assignment", id);
        accessLogService.record(userId, "作业任务", "删除", "删除作业：" + id);
        return Result.success();
    }
}
