package com.droneinspect.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.droneinspect.common.Result;
import com.droneinspect.entity.DroneDevice;
import com.droneinspect.service.DroneDeviceService;
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
@RequestMapping("/api/drone")
@RequiredArgsConstructor
public class DroneDeviceController {
    private final DroneDeviceService service;

    @GetMapping("/page")
    public Result<IPage<DroneDevice>> page(@RequestParam(required = false) Integer pageNum,
                                    @RequestParam(required = false) Integer pageSize,
                                    @RequestParam(required = false) String keyword,
                                    @RequestParam(required = false) String status) {
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestBody DroneDevice entity) {
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody DroneDevice entity) {
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
        service.updateStatus(id, "DISABLED");
        return Result.success();
    }

}
