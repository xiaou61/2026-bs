package com.charity.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.charity.common.Result;
import com.charity.entity.Announcement;
import com.charity.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/announcement")
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @GetMapping("/list")
    public Result<Page<Announcement>> getList(@RequestParam(defaultValue = "1") int pageNum,
                                              @RequestParam(defaultValue = "10") int pageSize,
                                              @RequestParam(required = false) String announcementType) {
        Page<Announcement> page = announcementService.getList(pageNum, pageSize, announcementType);
        return Result.success(page);
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody Announcement announcement) {
        announcementService.add(announcement);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody Announcement announcement) {
        announcementService.update(announcement);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id) {
        announcementService.delete(id);
        return Result.success();
    }
}
