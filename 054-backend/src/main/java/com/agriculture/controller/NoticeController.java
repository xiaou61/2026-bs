package com.agriculture.controller;

import com.agriculture.common.Result;
import com.agriculture.entity.Notice;
import com.agriculture.service.NoticeService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping("/page")
    public Result<Page<Notice>> getPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
                                         @RequestParam(required = false) String title) {
        return Result.success(noticeService.getPage(pageNum, pageSize, title));
    }

    @GetMapping("/published")
    public Result<List<Notice>> getPublished() {
        return Result.success(noticeService.getPublished());
    }

    @PostMapping
    public Result<?> add(@RequestBody Notice notice, HttpServletRequest request) {
        Long userId = Long.parseLong(request.getAttribute("userId").toString());
        notice.setPublisherId(userId);
        noticeService.save(notice);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Notice notice) {
        noticeService.updateById(notice);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        noticeService.removeById(id);
        return Result.success();
    }
}
