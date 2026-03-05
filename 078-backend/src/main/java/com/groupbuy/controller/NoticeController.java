package com.groupbuy.controller;

import com.groupbuy.common.Result;
import com.groupbuy.entity.Notice;
import com.groupbuy.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          Integer type, Integer status) {
        return Result.success(noticeService.page(pageNum, pageSize, type, status));
    }

    @GetMapping("/detail/{id}")
    public Result<?> detail(@PathVariable Long id) {
        return Result.success(noticeService.detail(id));
    }

    @PostMapping("/add")
    public Result<?> add(@RequestBody Notice notice) {
        noticeService.add(notice);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<?> update(@RequestBody Notice notice) {
        noticeService.update(notice);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<?> delete(@PathVariable Long id) {
        noticeService.delete(id);
        return Result.success();
    }

    @GetMapping("/front")
    public Result<?> front() {
        return Result.success(noticeService.front());
    }
}
