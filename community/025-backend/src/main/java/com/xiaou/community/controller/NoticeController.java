package com.xiaou.community.controller;

import com.xiaou.community.common.Result;
import com.xiaou.community.entity.Notice;
import com.xiaou.community.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    @PostMapping("/add")
    public Result<String> add(@RequestBody Notice notice) {
        noticeService.add(notice);
        return Result.success("Added successfully");
    }

    @PostMapping("/update")
    public Result<String> update(@RequestBody Notice notice) {
        noticeService.update(notice);
        return Result.success("Updated successfully");
    }

    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Integer id) {
        noticeService.delete(id);
        return Result.success("Deleted successfully");
    }

    @GetMapping("/{id}")
    public Result<Notice> getById(@PathVariable Integer id) {
        return Result.success(noticeService.getById(id));
    }

    @GetMapping("/list")
    public Result<List<Notice>> list() {
        return Result.success(noticeService.getAll());
    }
}
