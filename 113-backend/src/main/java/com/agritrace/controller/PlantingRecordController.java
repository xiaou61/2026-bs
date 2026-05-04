package com.agritrace.controller;

import com.agritrace.common.Result;
import com.agritrace.entity.PlantingRecord;
import com.agritrace.service.PlantingRecordService;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
@RequestMapping("/api/planting")
@RequiredArgsConstructor
public class PlantingRecordController {
    private final PlantingRecordService service;

    @GetMapping("/page")
    public Result<IPage<PlantingRecord>> page(@RequestParam(required = false) Integer pageNum,
                                            @RequestParam(required = false) Integer pageSize,
                                            @RequestParam(required = false) String keyword,
                                            @RequestParam(required = false) String status) {
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestBody PlantingRecord entity) {
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody PlantingRecord entity) {
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/confirm/{id}")
    public Result<Void> confirm(@PathVariable Long id) {
        service.updateStatus(id, "CONFIRMED");
        return Result.success();
    }

    @PutMapping("/chain/{id}")
    public Result<Void> chain(@PathVariable Long id) {
        service.updateStatus(id, "CHAINED");
        return Result.success();
    }

}
