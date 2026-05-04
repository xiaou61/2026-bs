package com.coldchain.controller;

import com.coldchain.common.Result;
import com.coldchain.entity.ColdDevice;
import com.coldchain.service.ColdDeviceService;
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
@RequestMapping("/api/device")
@RequiredArgsConstructor
public class ColdDeviceController {
    private final ColdDeviceService service;

    @GetMapping("/page")
    public Result<PageInfo<ColdDevice>> page(@RequestParam(required = false) Integer pageNum,
                                        @RequestParam(required = false) Integer pageSize,
                                        @RequestParam(required = false) String keyword,
                                        @RequestParam(required = false) String status) {
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestBody ColdDevice entity) {
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody ColdDevice entity) {
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/online/{id}")
    public Result<Void> online(@PathVariable Long id) {
        service.updateStatus(id, "ACTIVE");
        return Result.success();
    }

    @PutMapping("/offline/{id}")
    public Result<Void> offline(@PathVariable Long id) {
        service.updateStatus(id, "OFFLINE");
        return Result.success();
    }

    @PutMapping("/repair/{id}")
    public Result<Void> repair(@PathVariable Long id) {
        service.updateStatus(id, "REPAIRING");
        return Result.success();
    }

}
