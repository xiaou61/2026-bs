package com.eldercare.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eldercare.common.Result;
import com.eldercare.entity.SystemNotice;
import com.eldercare.service.NoticeService;
import com.eldercare.utils.AuthUtils;
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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping("/list")
    public Result<Page<SystemNotice>> list(@RequestParam(defaultValue = "1") int pageNum,
                                           @RequestParam(defaultValue = "10") int pageSize,
                                           @RequestParam(required = false) String title,
                                           @RequestParam(required = false) Integer status,
                                           HttpServletRequest request) {
        AuthUtils.requireAnyRole(request, "admin", "doctor", "nurse", "reception");
        return Result.success(noticeService.page(pageNum, pageSize, title, status));
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
                              @RequestAttribute("userId") String userId,
                              HttpServletRequest request) {
        AuthUtils.requireAdmin(request);
        noticeService.add(notice, Long.valueOf(userId));
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody SystemNotice notice, HttpServletRequest request) {
        AuthUtils.requireAdmin(request);
        noticeService.update(notice);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id, HttpServletRequest request) {
        AuthUtils.requireAdmin(request);
        noticeService.delete(id);
        return Result.success();
    }
}
