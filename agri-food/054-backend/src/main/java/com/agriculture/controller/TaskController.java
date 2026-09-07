package com.agriculture.controller;

import com.agriculture.common.Result;
import com.agriculture.entity.ProductionTask;
import com.agriculture.service.TaskService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/page")
    public Result<Page<ProductionTask>> getPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
                                                 @RequestParam(required = false) Long planId,
                                                 @RequestParam(required = false) Integer status) {
        return Result.success(taskService.getPage(pageNum, pageSize, planId, status));
    }

    @PostMapping
    public Result<?> add(@RequestBody ProductionTask task) {
        taskService.save(task);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody ProductionTask task) {
        taskService.updateById(task);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        taskService.removeById(id);
        return Result.success();
    }

    @PutMapping("/{id}/status")
    public Result<?> updateStatus(@PathVariable Long id, @RequestBody Map<String, Integer> body) {
        taskService.updateStatus(id, body.get("status"));
        return Result.success();
    }
}
