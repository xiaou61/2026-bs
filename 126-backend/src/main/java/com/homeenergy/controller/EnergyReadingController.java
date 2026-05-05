package com.homeenergy.controller;

import com.github.pagehelper.PageInfo;
import com.homeenergy.common.Result;
import com.homeenergy.entity.EnergyReading;
import com.homeenergy.service.EnergyReadingService;
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
@RequestMapping("/api/reading")
@RequiredArgsConstructor
public class EnergyReadingController {
    private final EnergyReadingService service;

    @GetMapping("/page")
    public Result<PageInfo<EnergyReading>> page(@RequestParam(required = false) Integer pageNum,
                                    @RequestParam(required = false) Integer pageSize,
                                    @RequestParam(required = false) String keyword,
                                    @RequestParam(required = false) String status) {
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestBody EnergyReading entity) {
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody EnergyReading entity) {
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/warn/{id}")
    public Result<Void> warn(@PathVariable Long id) {
        service.updateStatus(id, "WARNING");
        return Result.success();
    }

    @PutMapping("/recover/{id}")
    public Result<Void> recover(@PathVariable Long id) {
        service.updateStatus(id, "NORMAL");
        return Result.success();
    }

}
