package com.bike.controller;

import com.bike.common.Result;
import com.bike.entity.Announcement;
import com.bike.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/announcement")
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @GetMapping("/list")
    public Result<?> getList(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam(required = false) String title,
                             @RequestParam(required = false) Integer type) {
        return Result.success(announcementService.getList(pageNum, pageSize, title, type));
    }

    @PostMapping
    public Result<?> add(@RequestBody Announcement announcement) {
        announcementService.add(announcement);
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result<?> update(@PathVariable Long id, @RequestBody Announcement announcement) {
        announcement.setId(id);
        announcementService.update(announcement);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        announcementService.delete(id);
        return Result.success();
    }
}
