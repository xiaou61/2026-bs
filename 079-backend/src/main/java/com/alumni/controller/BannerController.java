package com.alumni.controller;

import com.alumni.common.Result;
import com.alumni.entity.Banner;
import com.alumni.service.BannerService;
import com.alumni.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    public Result<List<Banner>> listAll(HttpServletRequest request) {
        AuthUtils.requireAdmin(request);
        return Result.success(bannerService.listAll());
    }

    @PostMapping
    public Result<?> add(@RequestBody Banner banner, HttpServletRequest request) {
        AuthUtils.requireAdmin(request);
        bannerService.add(banner);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Banner banner, HttpServletRequest request) {
        AuthUtils.requireAdmin(request);
        bannerService.update(banner);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        AuthUtils.requireAdmin(request);
        bannerService.delete(id);
        return Result.success();
    }
}
