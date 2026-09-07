package com.wallpaper.controller;

import com.wallpaper.common.Result;
import com.wallpaper.entity.WallpaperInfo;
import com.wallpaper.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/favorite")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @GetMapping("/list")
    public Result<List<WallpaperInfo>> list(@RequestAttribute("userId") Long userId) {
        return Result.success(favoriteService.list(userId));
    }

    @PostMapping("/add/{wallpaperId}")
    public Result<String> add(@PathVariable Long wallpaperId, @RequestAttribute("userId") Long userId) {
        favoriteService.add(wallpaperId, userId);
        return Result.success();
    }

    @DeleteMapping("/cancel/{wallpaperId}")
    public Result<String> cancel(@PathVariable Long wallpaperId, @RequestAttribute("userId") Long userId) {
        favoriteService.cancel(wallpaperId, userId);
        return Result.success();
    }
}
