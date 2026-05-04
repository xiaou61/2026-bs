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
                                              @RequestParam(required = false) String announcementType,
                                              @RequestAttribute("userId") String userId) {
        Page<Announcement> page = announcementService.getList(pageNum, pageSize, announcementType, Long.parseLong(userId));
        return Result.success(page);
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody Announcement announcement,
                              @RequestAttribute("userId") String userId) {
        announcementService.add(announcement, Long.parseLong(userId));
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody Announcement announcement,
                                 @RequestAttribute("userId") String userId) {
        announcementService.update(announcement, Long.parseLong(userId));
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id,
                                 @RequestAttribute("userId") String userId) {
        announcementService.delete(id, Long.parseLong(userId));
        return Result.success();
    }
}
