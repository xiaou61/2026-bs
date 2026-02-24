package com.hrm.controller;

import com.hrm.common.Result;
import com.hrm.entity.Announcement;
import com.hrm.service.AnnouncementService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/announcement")
public class AnnouncementController {
    @Resource
    private AnnouncementService announcementService;

    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        return Result.success(announcementService.getById(id));
    }

    @GetMapping("/list")
    public Result getList(@RequestParam(required = false) String title,
                          @RequestParam(required = false) Integer status,
                          @RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(announcementService.getList(title, status, pageNum, pageSize));
    }

    @GetMapping("/top")
    public Result getTop(@RequestParam(defaultValue = "5") Integer limit) {
        return Result.success(announcementService.getTop(limit));
    }

    @PostMapping
    public Result add(@RequestBody Announcement announcement, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        announcement.setPublisherId(userId);
        announcementService.add(announcement);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody Announcement announcement) {
        announcementService.update(announcement);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        announcementService.delete(id);
        return Result.success();
    }
}
