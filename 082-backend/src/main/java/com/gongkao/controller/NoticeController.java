package com.gongkao.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gongkao.common.Result;
import com.gongkao.entity.Notice;
import com.gongkao.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public Result<Page<Notice>> list(@RequestParam(defaultValue = "1") int pageNum,
                                     @RequestParam(defaultValue = "10") int pageSize,
                                     @RequestParam(required = false) String title,
                                     @RequestParam(required = false) String type,
                                     @RequestParam(required = false) Integer status) {
        return Result.success(noticeService.getList(pageNum, pageSize, title, type, status));
    }

    @GetMapping("/public/list")
    public Result<List<Notice>> publicList() {
        return Result.success(noticeService.getPublicList());
    }

    @GetMapping("/public/detail/{id}")
    public Result<Notice> publicDetail(@PathVariable Long id) {
        return Result.success(noticeService.getPublicDetail(id));
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody Notice notice) {
        noticeService.add(notice);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody Notice notice) {
        noticeService.update(notice);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id) {
        noticeService.delete(id);
        return Result.success();
    }
}
