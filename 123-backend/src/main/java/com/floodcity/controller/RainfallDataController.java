package com.floodcity.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.floodcity.common.Result;
import com.floodcity.entity.RainfallData;
import com.floodcity.service.RainfallDataService;
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
@RequestMapping("/api/raindata")
@RequiredArgsConstructor
public class RainfallDataController {
    private final RainfallDataService service;

    @GetMapping("/page")
    public Result<IPage<RainfallData>> page(@RequestParam(required = false) Integer pageNum,
                                    @RequestParam(required = false) Integer pageSize,
                                    @RequestParam(required = false) String keyword,
                                    @RequestParam(required = false) String status) {
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestBody RainfallData entity) {
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody RainfallData entity) {
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
