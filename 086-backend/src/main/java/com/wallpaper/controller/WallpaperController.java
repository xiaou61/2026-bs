package com.wallpaper.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wallpaper.common.Result;
import com.wallpaper.entity.WallpaperInfo;
import com.wallpaper.service.AuthService;
import com.wallpaper.service.WallpaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/wallpaper")
public class WallpaperController {

    @Autowired
    private WallpaperService wallpaperService;

    @Autowired
    private AuthService authService;

    @GetMapping("/list")
    public Result<IPage<WallpaperInfo>> list(@RequestParam(defaultValue = "1") Integer pageNum,
                                             @RequestParam(defaultValue = "10") Integer pageSize,
                                             @RequestParam(required = false) String title,
                                             @RequestParam(required = false) Long categoryId,
                                             @RequestParam(required = false) Integer auditStatus,
                                             @RequestParam(required = false) Integer publishStatus,
                                             @RequestParam(required = false) Long uploaderId,
                                             @RequestParam(required = false) String wallpaperType) {
        return Result.success(wallpaperService.list(title, categoryId, auditStatus, publishStatus, uploaderId, wallpaperType, pageNum, pageSize));
    }

    @GetMapping("/public/list")
    public Result<IPage<WallpaperInfo>> publicList(@RequestParam(defaultValue = "1") Integer pageNum,
                                                   @RequestParam(defaultValue = "12") Integer pageSize,
                                                   @RequestParam(required = false) String title,
                                                   @RequestParam(required = false) Long categoryId,
                                                   @RequestParam(required = false) Long tagId,
                                                   @RequestParam(required = false) String wallpaperType,
                                                   @RequestParam(required = false) String colorHex,
                                                   @RequestParam(required = false) String resolution,
                                                   @RequestParam(required = false) String sortBy) {
        return Result.success(wallpaperService.publicList(title, categoryId, tagId, wallpaperType, colorHex, resolution, sortBy, pageNum, pageSize));
    }

    @GetMapping("/detail/{id}")
    public Result<Map<String, Object>> detail(@PathVariable Long id,
                                              @RequestHeader(value = "Authorization", required = false) String token) {
        return Result.success(wallpaperService.detail(id, authService.resolveUserId(token)));
    }

    @GetMapping("/recommend")
    public Result<List<WallpaperInfo>> recommend(@RequestParam(defaultValue = "8") Integer size) {
        return Result.success(wallpaperService.recommend(size));
    }

    @GetMapping("/my/list")
    public Result<IPage<WallpaperInfo>> myList(@RequestAttribute("userId") Long userId,
                                               @RequestParam(defaultValue = "1") Integer pageNum,
                                               @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(wallpaperService.myList(userId, pageNum, pageSize));
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody Map<String, Object> params, @RequestAttribute("userId") Long userId) {
        wallpaperService.add(params, userId);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody Map<String, Object> params, @RequestAttribute("userId") Long userId) {
        wallpaperService.update(params, userId);
        return Result.success();
    }

    @PutMapping("/publish")
    public Result<String> publish(@RequestParam Long id,
                                  @RequestParam Integer publishStatus,
                                  @RequestAttribute("userId") Long userId) {
        wallpaperService.publish(id, publishStatus, userId);
        return Result.success();
    }

    @PostMapping("/download/{id}")
    public Result<String> download(@PathVariable Long id,
                                   @RequestHeader(value = "Authorization", required = false) String token) {
        wallpaperService.download(id, authService.resolveUserId(token));
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id, @RequestAttribute("userId") Long userId) {
        wallpaperService.delete(id, userId);
        return Result.success();
    }
}
