package com.repair.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.repair.common.Result;
import com.repair.entity.Notice;
import com.repair.service.NoticeService;
import com.repair.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public Result<Page<Notice>> getList(@RequestParam(defaultValue = "1") int pageNum,
                                        @RequestParam(defaultValue = "10") int pageSize,
                                        @RequestParam(required = false) String title,
                                        @RequestParam(required = false) String type,
                                        @RequestParam(required = false) Integer status,
                                        @RequestAttribute("userId") String userId) {
        userService.requireAdmin(Long.parseLong(userId));
        return Result.success(noticeService.getList(pageNum, pageSize, title, type, status));
    }

    @GetMapping("/public/list")
    public Result<Page<Notice>> getPublicList(@RequestParam(defaultValue = "1") int pageNum,
                                              @RequestParam(defaultValue = "10") int pageSize,
                                              @RequestParam(required = false) String type) {
        return Result.success(noticeService.getPublicList(pageNum, pageSize, type));
    }

    @GetMapping("/detail/{id}")
    public Result<Notice> getDetail(@PathVariable Long id, @RequestAttribute("userId") String userId) {
        userService.requireAdmin(Long.parseLong(userId));
        return Result.success(noticeService.getDetail(id));
    }

    @GetMapping("/public/detail/{id}")
    public Result<Notice> getPublicDetail(@PathVariable Long id) {
        return Result.success(noticeService.getPublicDetail(id));
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody Notice notice, @RequestAttribute("userId") String userId) {
        userService.requireAdmin(Long.parseLong(userId));
        notice.setPublisherId(Long.parseLong(userId));
        noticeService.add(notice);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody Notice notice, @RequestAttribute("userId") String userId) {
        userService.requireAdmin(Long.parseLong(userId));
        noticeService.update(notice);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id, @RequestAttribute("userId") String userId) {
        userService.requireAdmin(Long.parseLong(userId));
        noticeService.delete(id);
        return Result.success();
    }
}
