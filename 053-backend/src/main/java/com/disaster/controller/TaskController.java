package com.disaster.controller;

import com.disaster.common.Result;
import com.disaster.entity.RescueTask;
import com.disaster.service.TaskService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/create")
    public Result<Void> create(@RequestBody RescueTask task, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        taskService.create(task, userId);
        return Result.success();
    }

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") int pageNum,
                          @RequestParam(defaultValue = "10") int pageSize,
                          @RequestParam(required = false) Integer status,
                          @RequestParam(required = false) Integer priority,
                          @RequestParam(required = false) Long disasterId) {
        return Result.success(taskService.page(pageNum, pageSize, status, priority, disasterId));
    }

    @GetMapping("/my")
    public Result<?> my(@RequestParam(defaultValue = "1") int pageNum,
                        @RequestParam(defaultValue = "10") int pageSize,
                        @RequestParam(required = false) Integer status,
                        HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(taskService.myTasks(pageNum, pageSize, userId, status));
    }

    @GetMapping("/{id}")
    public Result<RescueTask> detail(@PathVariable Long id) {
        return Result.success(taskService.getById(id));
    }

    @PutMapping("/{id}/assign")
    public Result<Void> assign(@PathVariable Long id, @RequestBody Map<String, Long> params) {
        taskService.assign(id, params.get("assigneeId"));
        return Result.success();
    }

    @PutMapping("/{id}/feedback")
    public Result<Void> feedback(@PathVariable Long id, @RequestBody Map<String, String> params) {
        taskService.feedback(id, params.get("feedback"), params.get("images"));
        return Result.success();
    }

    @PutMapping("/{id}/complete")
    public Result<Void> complete(@PathVariable Long id) {
        taskService.complete(id);
        return Result.success();
    }

    @PutMapping("/{id}/cancel")
    public Result<Void> cancel(@PathVariable Long id) {
        taskService.cancel(id);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        taskService.delete(id);
        return Result.success();
    }

    @GetMapping("/stats")
    public Result<?> stats() {
        return Result.success(taskService.stats());
    }
}
