package com.alumni.controller;

import com.alumni.common.Result;
import com.alumni.entity.Banner;
import com.alumni.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/banner")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @GetMapping("/list")
    public Result<List<Banner>> list() {
        return Result.success(bannerService.list());
    }

    @GetMapping("/all")
    public Result<List<Banner>> listAll() {
        return Result.success(bannerService.listAll());
    }

    @PostMapping
    public Result<?> add(@RequestBody Banner banner) {
        bannerService.add(banner);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Banner banner) {
        bannerService.update(banner);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        bannerService.delete(id);
        return Result.success();
    }
}
