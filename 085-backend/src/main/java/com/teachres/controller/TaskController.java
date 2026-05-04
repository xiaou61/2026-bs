package com.teachres.controller;

import com.github.pagehelper.PageInfo;
import com.teachres.common.Result;
import com.teachres.entity.EvalTask;
import com.teachres.service.TaskService;
import com.teachres.utils.AuthUtils;
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

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/list")
    public Result<PageInfo<EvalTask>> list(@RequestParam(defaultValue = "1") Integer pageNum,
                                           @RequestParam(defaultValue = "10") Integer pageSize,
                                           @RequestParam(required = false) String taskName,
                                           @RequestParam(required = false) Long courseId,
                                           @RequestParam(required = false) Integer status,
                                           @RequestParam(required = false) String term,
                                           @RequestAttribute("role") String role) {
        AuthUtils.requireRole(role, "admin", "teacher");
        return Result.success(taskService.list(taskName, courseId, status, term, pageNum, pageSize));
    }

    @GetMapping("/active")
    public Result<List<EvalTask>> active() {
        return Result.success(taskService.activeList());
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody EvalTask task,
                              @RequestAttribute("userId") Long userId,
                              @RequestAttribute("role") String role) {
        AuthUtils.requireAdmin(role);
        taskService.add(task, userId);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody EvalTask task,
                                 @RequestAttribute("role") String role) {
        AuthUtils.requireAdmin(role);
        taskService.update(task);
        return Result.success();
    }

    @PutMapping("/status")
    public Result<String> updateStatus(@RequestParam Long id,
                                       @RequestParam Integer status,
                                       @RequestAttribute("role") String role) {
        AuthUtils.requireAdmin(role);
        taskService.updateStatus(id, status);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id,
                                 @RequestAttribute("role") String role) {
        AuthUtils.requireAdmin(role);
        taskService.delete(id);
        return Result.success();
    }
}
