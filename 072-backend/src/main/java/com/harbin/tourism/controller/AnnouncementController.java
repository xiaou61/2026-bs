package com.harbin.tourism.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.harbin.tourism.common.Result;
import com.harbin.tourism.entity.Announcement;
import com.harbin.tourism.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/announcements")
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @GetMapping("/list")
    public Result<Page<Announcement>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword) {
        return Result.success(announcementService.page(pageNum, pageSize, keyword));
    }

    @GetMapping("/top")
    public Result<List<Announcement>> topList() {
        return Result.success(announcementService.topList());
    }

    @GetMapping("/{id}")
    public Result<Announcement> getById(@PathVariable Long id) {
        return Result.success(announcementService.getById(id));
    }

    @PostMapping
    public Result<Void> add(@RequestBody Announcement announcement) {
        announcementService.save(announcement);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody Announcement announcement) {
        announcementService.update(announcement);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        announcementService.delete(id);
        return Result.success();
    }
}
