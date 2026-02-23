package com.blog.controller;

import com.blog.common.BusinessException;
import com.blog.common.Result;
import com.blog.entity.Notice;
import com.blog.service.NoticeService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping("/list")
    public Result<?> list() {
        return Result.success(noticeService.list());
    }

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String title,
                          @RequestParam(required = false) Integer status,
                          HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        return Result.success(noticeService.page(pageNum, pageSize, title, status));
    }

    @PostMapping
    public Result<?> add(@RequestBody Notice notice, HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        noticeService.save(notice, (Long) request.getAttribute("userId"));
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Notice notice, HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        noticeService.save(notice, (Long) request.getAttribute("userId"));
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        noticeService.deleteById(id);
        return Result.success();
    }

    private void checkAdmin(String role) {
        if (!"ADMIN".equals(role)) {
            throw new BusinessException("无权限操作");
        }
    }
}
