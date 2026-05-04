package com.datamasking.controller;

import com.datamasking.common.Result;
import com.datamasking.entity.MaskingTask;
import com.datamasking.service.MaskingTaskService;
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
@RequestMapping("/api/masking-task")
@RequiredArgsConstructor
public class MaskingTaskController {
    private final MaskingTaskService service;

    @GetMapping("/page")
    public Result<IPage<MaskingTask>> page(@RequestParam(required = false) Integer pageNum,
                                            @RequestParam(required = false) Integer pageSize,
                                            @RequestParam(required = false) String keyword,
                                            @RequestParam(required = false) String status) {
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestBody MaskingTask entity) {
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody MaskingTask entity) {
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/submit/{id}")
    public Result<Void> submit(@PathVariable Long id) {
        service.updateStatus(id, "SUBMITTED");
        return Result.success();
    }

    @PutMapping("/start/{id}")
    public Result<Void> start(@PathVariable Long id) {
        service.updateStatus(id, "RUNNING");
        return Result.success();
    }

    @PutMapping("/finish/{id}")
    public Result<Void> finish(@PathVariable Long id) {
        service.updateStatus(id, "FINISHED");
        return Result.success();
    }

    @PutMapping("/fail/{id}")
    public Result<Void> fail(@PathVariable Long id) {
        service.updateStatus(id, "FAILED");
        return Result.success();
    }

}
