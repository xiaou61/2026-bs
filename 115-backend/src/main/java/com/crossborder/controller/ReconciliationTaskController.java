package com.crossborder.controller;

import com.crossborder.common.Result;
import com.crossborder.entity.ReconciliationTask;
import com.crossborder.service.ReconciliationTaskService;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
@RequestMapping("/api/reconciliation")
@RequiredArgsConstructor
public class ReconciliationTaskController {
    private final ReconciliationTaskService service;

    @GetMapping("/page")
    public Result<IPage<ReconciliationTask>> page(@RequestParam(required = false) Integer pageNum,
                                     @RequestParam(required = false) Integer pageSize,
                                     @RequestParam(required = false) String keyword,
                                     @RequestParam(required = false) String status) {
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestBody ReconciliationTask entity) {
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody ReconciliationTask entity) {
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/execute/{id}")
    public Result<Void> execute(@PathVariable Long id) {
        service.updateStatus(id, "CHECKING");
        return Result.success();
    }

    @PutMapping("/pass/{id}")
    public Result<Void> pass(@PathVariable Long id) {
        service.updateStatus(id, "MATCHED");
        return Result.success();
    }

    @PutMapping("/exception/{id}")
    public Result<Void> exception(@PathVariable Long id) {
        service.updateStatus(id, "DIFFERENCE");
        return Result.success();
    }

}
