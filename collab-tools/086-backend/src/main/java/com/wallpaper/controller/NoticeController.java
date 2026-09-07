package com.wallpaper.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wallpaper.common.Result;
import com.wallpaper.entity.SystemNotice;
import com.wallpaper.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping("/list")
    public Result<IPage<SystemNotice>> list(@RequestParam(defaultValue = "1") Integer pageNum,
                                            @RequestParam(defaultValue = "10") Integer pageSize,
                                            @RequestParam(required = false) String title,
                                            @RequestParam(required = false) Integer status,
                                            @RequestAttribute("userId") Long userId) {
        return Result.success(noticeService.list(title, status, pageNum, pageSize, userId));
    }

    @GetMapping("/public/list")
    public Result<List<SystemNotice>> publicList() {
        return Result.success(noticeService.publicList());
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody SystemNotice notice, @RequestAttribute("userId") Long userId) {
        noticeService.add(notice, userId);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody SystemNotice notice, @RequestAttribute("userId") Long userId) {
        noticeService.update(notice, userId);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id, @RequestAttribute("userId") Long userId) {
        noticeService.delete(id, userId);
        return Result.success();
    }
}
