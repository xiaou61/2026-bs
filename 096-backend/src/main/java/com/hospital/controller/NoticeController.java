package com.hospital.controller;

import com.github.pagehelper.PageInfo;
import com.hospital.common.Result;
import com.hospital.entity.NewsNotice;
import com.hospital.service.NoticeService;
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

    @GetMapping("/page")
    public Result<PageInfo<NewsNotice>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                             @RequestParam(defaultValue = "10") Integer pageSize,
                                             @RequestParam(required = false) String keyword,
                                             @RequestParam(required = false) Integer status,
                                             @RequestAttribute("role") String role) {
        return Result.success(noticeService.page(keyword, status, pageNum, pageSize, role));
    }

    @GetMapping("/public/list")
    public Result<List<NewsNotice>> publicList() {
        return Result.success(noticeService.publicList());
    }

    @PostMapping
    public Result<String> add(@RequestBody NewsNotice entity,
                              @RequestAttribute("userId") Long userId,
                              @RequestAttribute("role") String role) {
        noticeService.save(entity, userId, role);
        return Result.success();
    }

    @PutMapping
    public Result<String> update(@RequestBody NewsNotice entity,
                                 @RequestAttribute("userId") Long userId,
                                 @RequestAttribute("role") String role) {
        noticeService.save(entity, userId, role);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id,
                                 @RequestAttribute("userId") Long userId,
                                 @RequestAttribute("role") String role) {
        noticeService.delete(id, userId, role);
        return Result.success();
    }
}
