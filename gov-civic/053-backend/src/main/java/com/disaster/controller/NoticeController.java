package com.disaster.controller;

import com.disaster.common.Result;
import com.disaster.entity.Notice;
import com.disaster.service.NoticeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") int pageNum,
                          @RequestParam(defaultValue = "10") int pageSize,
                          @RequestParam(required = false) Integer type,
                          @RequestParam(required = false) Integer status) {
        return Result.success(noticeService.page(pageNum, pageSize, type, status));
    }

    @GetMapping("/published")
    public Result<?> published(@RequestParam(defaultValue = "1") int pageNum,
                               @RequestParam(defaultValue = "10") int pageSize) {
        return Result.success(noticeService.published(pageNum, pageSize));
    }

    @GetMapping("/{id}")
    public Result<Notice> detail(@PathVariable Long id) {
        return Result.success(noticeService.getById(id));
    }

    @PostMapping("/add")
    public Result<Void> add(@RequestBody Notice notice, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        noticeService.add(notice, userId);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<Void> update(@RequestBody Notice notice) {
        noticeService.update(notice);
        return Result.success();
    }

    @PutMapping("/{id}/publish")
    public Result<Void> publish(@PathVariable Long id) {
        noticeService.publish(id);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        noticeService.delete(id);
        return Result.success();
    }
}
