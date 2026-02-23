package com.teacher.new.controller;

import com.teacher.new.common.BusinessException;
import com.teacher.new.common.Result;
import com.teacher.new.entity.EvaluationTask;
import com.teacher.new.service.TaskService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Resource
    private TaskService taskService;

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String taskName,
                          @RequestParam(required = false) String termName,
                          @RequestParam(required = false) String status,
                          @RequestParam(required = false) Long classId,
                          @RequestParam(required = false) Long teacherId,
                          HttpServletRequest request) {
        String role = (String) request.getAttribute("role");
        Long userId = (Long) request.getAttribute("userId");
        if ("ADMIN".equals(role)) {
            return Result.success(taskService.page(pageNum, pageSize, taskName, termName, status, classId, teacherId));
        }
        return Result.success(taskService.myPage(userId, role, pageNum, pageSize, taskName, status));
    }

    @GetMapping("/my-page")
    public Result<?> myPage(@RequestParam(defaultValue = "1") Integer pageNum,
                            @RequestParam(defaultValue = "10") Integer pageSize,
                            @RequestParam(required = false) String taskName,
                            @RequestParam(required = false) String status,
                            HttpServletRequest request) {
        String role = (String) request.getAttribute("role");
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(taskService.myPage(userId, role, pageNum, pageSize, taskName, status));
    }

    @PostMapping
    public Result<?> add(@RequestBody EvaluationTask task, HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        taskService.save(task, (Long) request.getAttribute("userId"));
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody EvaluationTask task, HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        taskService.save(task, (Long) request.getAttribute("userId"));
        return Result.success();
    }

    @PutMapping("/status")
    public Result<?> updateStatus(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        if (params.get("id") == null || params.get("status") == null) {
            throw new BusinessException("参数不完整");
        }
        taskService.updateStatus(((Number) params.get("id")).longValue(), String.valueOf(params.get("status")));
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        taskService.deleteById(id);
        return Result.success();
    }

    private void checkAdmin(String role) {
        if (!"ADMIN".equals(role)) {
            throw new BusinessException("无权限操作");
        }
    }
}
