package com.smartwarehouse.controller;

import com.github.pagehelper.PageInfo;
import com.smartwarehouse.common.Result;
import com.smartwarehouse.entity.OutboundOrder;
import com.smartwarehouse.service.OutboundOrderService;
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
@RequestMapping("/api/outbound")
@RequiredArgsConstructor
public class OutboundOrderController {
    private final OutboundOrderService service;

    @GetMapping("/page")
    public Result<PageInfo<OutboundOrder>> page(@RequestParam(required = false) Integer pageNum,
                                         @RequestParam(required = false) Integer pageSize,
                                         @RequestParam(required = false) String keyword,
                                         @RequestParam(required = false) String status) {
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestBody OutboundOrder entity) {
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody OutboundOrder entity) {
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/allocate/{id}")
    public Result<Void> allocate(@PathVariable Long id) {
        service.updateStatus(id, "ALLOCATED");
        return Result.success();
    }

    @PutMapping("/pick/{id}")
    public Result<Void> pick(@PathVariable Long id) {
        service.updateStatus(id, "PICKING");
        return Result.success();
    }

    @PutMapping("/ship/{id}")
    public Result<Void> ship(@PathVariable Long id) {
        service.updateStatus(id, "SHIPPED");
        return Result.success();
    }

}
