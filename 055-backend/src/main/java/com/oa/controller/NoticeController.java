package com.oa.controller;

import com.oa.common.Result;
import com.oa.entity.Notice;
import com.oa.service.NoticeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notice")
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;

    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       String keyword) {
        return Result.success(noticeService.getList(pageNum, pageSize, keyword));
    }

    @GetMapping("/published")
    public Result published(@RequestParam(defaultValue = "1") Integer pageNum,
                            @RequestParam(defaultValue = "10") Integer pageSize,
                            String keyword) {
        return Result.success(noticeService.getPublished(pageNum, pageSize, keyword));
    }

    @PostMapping
    public Result add(HttpServletRequest request, @RequestBody Notice notice) {
        Long userId = (Long) request.getAttribute("userId");
        noticeService.add(userId, notice);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody Notice notice) {
        noticeService.update(notice);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        noticeService.delete(id);
        return Result.success();
    }

    @PutMapping("/publish/{id}")
    public Result publish(@PathVariable Long id) {
        noticeService.publish(id);
        return Result.success();
    }

    @PostMapping("/read/{id}")
    public Result read(HttpServletRequest request, @PathVariable Long id) {
        Long userId = (Long) request.getAttribute("userId");
        noticeService.read(userId, id);
        return Result.success();
    }
}
