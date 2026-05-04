package com.zerotrust.controller;

import com.zerotrust.common.Result;
import com.zerotrust.entity.DeviceAsset;
import com.zerotrust.service.DeviceAssetService;
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
public class DeviceAssetController {
    private final DeviceAssetService service;

    @GetMapping("/page")
    public Result<PageInfo<DeviceAsset>> page(@RequestParam(required = false) Integer pageNum,
                                        @RequestParam(required = false) Integer pageSize,
                                        @RequestParam(required = false) String keyword,
                                        @RequestParam(required = false) String status) {
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestBody DeviceAsset entity) {
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody DeviceAsset entity) {
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/approve/{id}")
    public Result<Void> approve(@PathVariable Long id) {
        service.updateStatus(id, "APPROVED");
        return Result.success();
    }

    @PutMapping("/block/{id}")
    public Result<Void> block(@PathVariable Long id) {
        service.updateStatus(id, "BLOCKED");
        return Result.success();
    }

    @PutMapping("/retire/{id}")
    public Result<Void> retire(@PathVariable Long id) {
        service.updateStatus(id, "RETIRED");
        return Result.success();
    }

}
