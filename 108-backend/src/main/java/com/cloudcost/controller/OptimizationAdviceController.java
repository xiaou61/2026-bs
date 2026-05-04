package com.cloudcost.controller;

import com.cloudcost.common.Result;
import com.cloudcost.entity.OptimizationAdvice;
import com.cloudcost.service.OptimizationAdviceService;
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
@RequestMapping("/api/advice")
@RequiredArgsConstructor
public class OptimizationAdviceController {
    private final OptimizationAdviceService service;

    @GetMapping("/page")
    public Result<PageInfo<OptimizationAdvice>> page(@RequestParam(required = false) Integer pageNum,
                                      @RequestParam(required = false) Integer pageSize,
                                      @RequestParam(required = false) String keyword,
                                      @RequestParam(required = false) String status) {
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestBody OptimizationAdvice entity) {
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody OptimizationAdvice entity) {
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/accept/{id}")
    public Result<Void> accept(@PathVariable Long id) {
        service.updateStatus(id, "ACCEPTED");
        return Result.success();
    }

    @PutMapping("/finish/{id}")
    public Result<Void> finish(@PathVariable Long id) {
        service.updateStatus(id, "FINISHED");
        return Result.success();
    }

    @PutMapping("/reject/{id}")
    public Result<Void> reject(@PathVariable Long id) {
        service.updateStatus(id, "REJECTED");
        return Result.success();
    }
}
