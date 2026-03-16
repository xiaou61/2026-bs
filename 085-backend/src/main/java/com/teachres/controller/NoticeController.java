package com.teachres.controller;

import com.github.pagehelper.PageInfo;
import com.teachres.common.Result;
import com.teachres.entity.SystemNotice;
import com.teachres.service.NoticeService;
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
    public Result<PageInfo<SystemNotice>> list(@RequestParam(defaultValue = "1") Integer pageNum,
                                               @RequestParam(defaultValue = "10") Integer pageSize,
                                               @RequestParam(required = false) String title,
                                               @RequestParam(required = false) Integer status) {
        return Result.success(noticeService.list(title, status, pageNum, pageSize));
    }

    @GetMapping("/public/list")
    public Result<List<SystemNotice>> publicList() {
        return Result.success(noticeService.publicList());
    }

    @GetMapping("/public/detail/{id}")
    public Result<SystemNotice> publicDetail(@PathVariable Long id) {
        return Result.success(noticeService.publicDetail(id));
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody SystemNotice notice,
                              @RequestAttribute("userId") Long userId) {
        noticeService.add(notice, userId);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody SystemNotice notice) {
        noticeService.update(notice);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id) {
        noticeService.delete(id);
        return Result.success();
    }
}
