package com.xiaou.controller;

import com.xiaou.common.Result;
import com.xiaou.entity.Notice;
import com.xiaou.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          Integer type, Integer status, String keyword) {
        return Result.success(noticeService.getList(pageNum, pageSize, type, status, keyword));
    }

    @GetMapping("/public/list")
    public Result<?> publicList(Integer type, Long competitionId) {
        return Result.success(noticeService.getPublicList(type, competitionId));
    }

    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id) {
        return Result.success(noticeService.getById(id));
    }

    @GetMapping("/public/{id}")
    public Result<?> getPublicById(@PathVariable Long id) {
        return Result.success(noticeService.getById(id));
    }

    @PostMapping("/save")
    public Result<?> save(@RequestBody Notice notice, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        noticeService.save(notice, userId);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        noticeService.delete(id);
        return Result.success();
    }
}
