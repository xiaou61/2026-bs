package com.smartwarehouse.controller;

import com.github.pagehelper.PageInfo;
import com.smartwarehouse.common.Result;
import com.smartwarehouse.entity.AgvTask;
import com.smartwarehouse.service.AgvTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class AgvTaskController {
    private final AgvTaskService service;

    @GetMapping("/page")
    public Result<PageInfo<AgvTask>> page(@RequestParam(required = false) Integer pageNum,
                                         @RequestParam(required = false) Integer pageSize,
                                         @RequestParam(required = false) String keyword,
                                         @RequestParam(required = false) String status) {
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestBody AgvTask entity) {
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody AgvTask entity) {
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/dispatch/{id}")
    public Result<Void> dispatch(@PathVariable Long id) {
        service.updateStatus(id, "DISPATCHED");
        return Result.success();
    }

    @PutMapping("/execute/{id}")
    public Result<Void> execute(@PathVariable Long id) {
        service.updateStatus(id, "RUNNING");
        return Result.success();
    }

    @PutMapping("/complete/{id}")
    public Result<Void> complete(@PathVariable Long id) {
        service.updateStatus(id, "COMPLETED");
        return Result.success();
    }

    @PutMapping("/cancel/{id}")
    public Result<Void> cancel(@PathVariable Long id) {
        service.updateStatus(id, "CANCELLED");
        return Result.success();
    }

}
