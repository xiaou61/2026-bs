package com.movie.controller;

import com.movie.common.Result;
import com.movie.entity.Announcement;
import com.movie.service.AnnouncementService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/announcement")
public class AnnouncementController {

    @Resource
    private AnnouncementService announcementService;

    @GetMapping("/page")
    public Result<?> getPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam(required = false) String title) {
        return Result.success(announcementService.getPage(pageNum, pageSize, title));
    }

    @GetMapping("/list")
    public Result<?> getLatest() {
        return Result.success(announcementService.getLatest());
    }

    @PostMapping
    public Result<?> add(@RequestBody Announcement announcement, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        announcement.setAdminId(userId);
        announcementService.add(announcement);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Announcement announcement) {
        announcementService.update(announcement);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        announcementService.delete(id);
        return Result.success();
    }
}
