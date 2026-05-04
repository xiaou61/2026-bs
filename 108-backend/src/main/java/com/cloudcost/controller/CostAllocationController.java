package com.cloudcost.controller;

import com.cloudcost.common.Result;
import com.cloudcost.entity.CostAllocation;
import com.cloudcost.service.CostAllocationService;
import com.github.pagehelper.PageInfo;
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
@RequestMapping("/api/allocation")
@RequiredArgsConstructor
public class CostAllocationController {
    private final CostAllocationService service;

    @GetMapping("/page")
    public Result<PageInfo<CostAllocation>> page(@RequestParam(required = false) Integer pageNum,
                                      @RequestParam(required = false) Integer pageSize,
                                      @RequestParam(required = false) String keyword,
                                      @RequestParam(required = false) String status) {
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestBody CostAllocation entity) {
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody CostAllocation entity) {
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

    @PutMapping("/approve/{id}")
    public Result<Void> approve(@PathVariable Long id) {
        service.updateStatus(id, "APPROVED");
        return Result.success();
    }

    @PutMapping("/reject/{id}")
    public Result<Void> reject(@PathVariable Long id) {
        service.updateStatus(id, "REJECTED");
        return Result.success();
    }
}
