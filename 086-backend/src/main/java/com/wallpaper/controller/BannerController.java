package com.wallpaper.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wallpaper.common.Result;
import com.wallpaper.entity.CarouselBanner;
import com.wallpaper.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/banner")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @GetMapping("/list")
    public Result<IPage<CarouselBanner>> list(@RequestParam(defaultValue = "1") Integer pageNum,
                                              @RequestParam(defaultValue = "10") Integer pageSize,
                                              @RequestParam(required = false) String title,
                                              @RequestParam(required = false) Integer status,
                                              @RequestAttribute("userId") Long userId) {
        return Result.success(bannerService.list(title, status, pageNum, pageSize, userId));
    }

    @GetMapping("/enabled")
    public Result<List<CarouselBanner>> enabled() {
        return Result.success(bannerService.enabledList());
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody CarouselBanner banner, @RequestAttribute("userId") Long userId) {
        bannerService.add(banner, userId);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody CarouselBanner banner, @RequestAttribute("userId") Long userId) {
        bannerService.update(banner, userId);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id, @RequestAttribute("userId") Long userId) {
        bannerService.delete(id, userId);
        return Result.success();
    }
}
