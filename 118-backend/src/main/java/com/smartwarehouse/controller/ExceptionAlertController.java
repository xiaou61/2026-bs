package com.smartwarehouse.controller;

import com.github.pagehelper.PageInfo;
import com.smartwarehouse.common.Result;
import com.smartwarehouse.entity.ExceptionAlert;
import com.smartwarehouse.service.ExceptionAlertService;
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
@RequestMapping("/api/alert")
@RequiredArgsConstructor
public class ExceptionAlertController {
    private final ExceptionAlertService service;

    @GetMapping("/page")
    public Result<PageInfo<ExceptionAlert>> page(@RequestParam(required = false) Integer pageNum,
                                         @RequestParam(required = false) Integer pageSize,
                                         @RequestParam(required = false) String keyword,
                                         @RequestParam(required = false) String status) {
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestBody ExceptionAlert entity) {
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody ExceptionAlert entity) {
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/handle/{id}")
    public Result<Void> handle(@PathVariable Long id) {
        service.updateStatus(id, "HANDLING");
        return Result.success();
    }

    @PutMapping("/close/{id}")
    public Result<Void> close(@PathVariable Long id) {
        service.updateStatus(id, "CLOSED");
        return Result.success();
    }

}
