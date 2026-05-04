package com.coldchain.controller;

import com.coldchain.common.Result;
import com.coldchain.entity.ResponsibilityTrace;
import com.coldchain.service.ResponsibilityTraceService;
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
@RequestMapping("/api/responsibility")
@RequiredArgsConstructor
public class ResponsibilityTraceController {
    private final ResponsibilityTraceService service;

    @GetMapping("/page")
    public Result<PageInfo<ResponsibilityTrace>> page(@RequestParam(required = false) Integer pageNum,
                                        @RequestParam(required = false) Integer pageSize,
                                        @RequestParam(required = false) String keyword,
                                        @RequestParam(required = false) String status) {
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestBody ResponsibilityTrace entity) {
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody ResponsibilityTrace entity) {
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/review/{id}")
    public Result<Void> review(@PathVariable Long id) {
        service.updateStatus(id, "REVIEWED");
        return Result.success();
    }

    @PutMapping("/confirm/{id}")
    public Result<Void> confirm(@PathVariable Long id) {
        service.updateStatus(id, "CONFIRMED");
        return Result.success();
    }

    @PutMapping("/close/{id}")
    public Result<Void> close(@PathVariable Long id) {
        service.updateStatus(id, "CLOSED");
        return Result.success();
    }

}
