package com.livecommerce.controller;

import com.livecommerce.common.Result;
import com.livecommerce.entity.SampleRequest;
import com.livecommerce.service.SampleRequestService;
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
@RequestMapping("/api/sample")
@RequiredArgsConstructor
public class SampleRequestController {
    private final SampleRequestService service;

    @GetMapping("/page")
    public Result<PageInfo<SampleRequest>> page(@RequestParam(required = false) Integer pageNum,
                                        @RequestParam(required = false) Integer pageSize,
                                        @RequestParam(required = false) String keyword,
                                        @RequestParam(required = false) String status) {
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestBody SampleRequest entity) {
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody SampleRequest entity) {
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/send/{id}")
    public Result<Void> send(@PathVariable Long id) {
        service.updateStatus(id, "SENT");
        return Result.success();
    }

    @PutMapping("/receive/{id}")
    public Result<Void> receive(@PathVariable Long id) {
        service.updateStatus(id, "RECEIVED");
        return Result.success();
    }

}
