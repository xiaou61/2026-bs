package com.droneinspect.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.droneinspect.common.Result;
import com.droneinspect.entity.BatteryStation;
import com.droneinspect.service.BatteryStationService;
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
@RequestMapping("/api/station")
@RequiredArgsConstructor
public class BatteryStationController {
    private final BatteryStationService service;

    @GetMapping("/page")
    public Result<IPage<BatteryStation>> page(@RequestParam(required = false) Integer pageNum,
                                    @RequestParam(required = false) Integer pageSize,
                                    @RequestParam(required = false) String keyword,
                                    @RequestParam(required = false) String status) {
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestBody BatteryStation entity) {
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody BatteryStation entity) {
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/enable/{id}")
    public Result<Void> enable(@PathVariable Long id) {
        service.updateStatus(id, "NORMAL");
        return Result.success();
    }

    @PutMapping("/warn/{id}")
    public Result<Void> warn(@PathVariable Long id) {
        service.updateStatus(id, "WARNING");
        return Result.success();
    }

}
