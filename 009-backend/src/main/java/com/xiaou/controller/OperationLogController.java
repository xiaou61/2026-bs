package com.xiaou.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.common.Result;
import com.xiaou.entity.OperationLog;
import com.xiaou.service.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/log")
@CrossOrigin
public class OperationLogController {

    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("/list")
    public Result<?> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String operationType,
            @RequestParam(required = false) String keyword) {
        
        Page<OperationLog> pageObj = new Page<>(page, size);
        QueryWrapper<OperationLog> wrapper = new QueryWrapper<>();
        
        if (operationType != null && !operationType.isEmpty()) {
            wrapper.eq("operation_type", operationType);
        }
        
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.and(w -> w.like("operator_name", keyword)
                    .or().like("operation_desc", keyword));
        }
        
        wrapper.orderByDesc("create_time");
        Page<OperationLog> result = operationLogService.page(pageObj, wrapper);
        
        return Result.success(result);
    }
}

