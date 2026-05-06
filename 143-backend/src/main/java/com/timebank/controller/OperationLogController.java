package com.timebank.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.timebank.common.Result;
import com.timebank.entity.OperationLog;
import com.timebank.service.OperationLogService;
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
@RequestMapping("/api/log")
@RequiredArgsConstructor
public class OperationLogController {
    private final OperationLogService service;

    @GetMapping("/page")
    public Result<IPage<OperationLog>> page(@RequestParam(required = false) Integer pageNum,
                                    @RequestParam(required = false) Integer pageSize,
                                    @RequestParam(required = false) String keyword,
                                    @RequestParam(required = false) String status) {
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestBody OperationLog entity) {
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody OperationLog entity) {
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return Result.success();
    }

}





