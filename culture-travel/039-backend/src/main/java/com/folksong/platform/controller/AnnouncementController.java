package com.folksong.platform.controller;

import com.folksong.platform.common.PageResult;
import com.folksong.platform.common.Result;
import com.folksong.platform.entity.Announcement;
import com.folksong.platform.service.AnnouncementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "公告管理")
@RestController
@RequestMapping("/api/announcements")
@RequiredArgsConstructor
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @Operation(summary = "获取公告列表")
    @GetMapping
    public Result<PageResult<Announcement>> getActiveAnnouncements(@RequestParam(defaultValue = "1") Integer pageNum,
                                                                    @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(announcementService.getActiveAnnouncements(pageNum, pageSize));
    }

    @Operation(summary = "获取最新公告")
    @GetMapping("/latest")
    public Result<List<Announcement>> getLatestAnnouncements(@RequestParam(defaultValue = "5") Integer limit) {
        return Result.success(announcementService.getLatestAnnouncements(limit));
    }

    @Operation(summary = "获取公告详情")
    @GetMapping("/{id}")
    public Result<Announcement> getAnnouncementById(@PathVariable Long id) {
        return Result.success(announcementService.getAnnouncementById(id));
    }
}
