package com.harbin.tourism.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.harbin.tourism.common.Result;
import com.harbin.tourism.entity.ScenicSpot;
import com.harbin.tourism.service.SpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/spots")
public class SpotController {

    @Autowired
    private SpotService spotService;

    @GetMapping("/list")
    public Result<Page<ScenicSpot>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category) {
        return Result.success(spotService.page(pageNum, pageSize, keyword, category));
    }

    @GetMapping("/detail/{id}")
    public Result<Map<String, Object>> detail(@PathVariable Long id) {
        return Result.success(spotService.detail(id));
    }

    @GetMapping("/all")
    public Result<List<ScenicSpot>> all() {
        return Result.success(spotService.listAll());
    }

    @GetMapping("/top10")
    public Result<List<ScenicSpot>> top10() {
        return Result.success(spotService.top10());
    }

    @PostMapping
    public Result<Void> add(@RequestBody ScenicSpot spot) {
        spotService.save(spot);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody ScenicSpot spot) {
        spotService.update(spot);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        spotService.delete(id);
        return Result.success();
    }
}
