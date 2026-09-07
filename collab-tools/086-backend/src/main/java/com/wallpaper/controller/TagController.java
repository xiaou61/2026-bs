package com.wallpaper.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wallpaper.common.Result;
import com.wallpaper.entity.WallpaperTag;
import com.wallpaper.service.TagService;
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
@RequestMapping("/api/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    public Result<IPage<WallpaperTag>> list(@RequestParam(defaultValue = "1") Integer pageNum,
                                            @RequestParam(defaultValue = "10") Integer pageSize,
                                            @RequestParam(required = false) String name,
                                            @RequestParam(required = false) Integer status) {
        return Result.success(tagService.list(name, status, pageNum, pageSize));
    }

    @GetMapping("/enabled")
    public Result<List<WallpaperTag>> enabled() {
        return Result.success(tagService.enabledList());
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody WallpaperTag tag, @RequestAttribute("userId") Long userId) {
        tagService.add(tag, userId);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody WallpaperTag tag, @RequestAttribute("userId") Long userId) {
        tagService.update(tag, userId);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id, @RequestAttribute("userId") Long userId) {
        tagService.delete(id, userId);
        return Result.success();
    }
}
