package com.vending.controller;

import com.vending.common.Result;
import com.vending.entity.SystemNotice;
import com.vending.service.NoticeService;
import com.vending.utils.AuthUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/notice")
public class NoticeController {

    @Resource
    private NoticeService noticeService;

    @GetMapping("/public/list")
    public Result<?> publicList() {
        return Result.success(noticeService.publicList());
    }

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String title,
                          @RequestParam(required = false) Integer status,
                          HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        return Result.success(noticeService.page(pageNum, pageSize, title, status));
    }

    @PostMapping("/save")
    public Result<?> save(@RequestBody SystemNotice notice, HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        noticeService.save((Long) request.getAttribute("userId"), notice);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        noticeService.deleteById(id);
        return Result.success();
    }
}
