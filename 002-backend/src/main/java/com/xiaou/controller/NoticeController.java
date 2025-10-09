package com.xiaou.controller;

import com.xiaou.common.Result;
import com.xiaou.entity.Notice;
import com.xiaou.service.NoticeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notices")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping
    public Result<List<Notice>> list(@RequestParam(required = false) String keyword,
                                     @RequestParam(required = false) String type) {
        List<Notice> notices = noticeService.findAll(keyword, type);
        return Result.success(notices);
    }

    @GetMapping("/{id}")
    public Result<Notice> getById(@PathVariable Long id) {
        Notice notice = noticeService.findById(id);
        return Result.success(notice);
    }

    @PostMapping
    public Result<Notice> create(@RequestBody Notice notice, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        String username = (String) request.getAttribute("username");
        notice.setPublisherId(userId);
        notice.setPublisherName(username);
        Notice newNotice = noticeService.create(notice);
        return Result.success(newNotice);
    }

    @PutMapping("/{id}")
    public Result<Notice> update(@PathVariable Long id, @RequestBody Notice notice) {
        notice.setId(id);
        Notice updatedNotice = noticeService.update(notice);
        return Result.success(updatedNotice);
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        noticeService.delete(id);
        return Result.success();
    }
}

