package com.railway.controller;

import com.railway.common.Result;
import com.railway.entity.SystemNotice;
import com.railway.service.NoticeService;
import com.railway.utils.AuthUtils;
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
                          @RequestParam(required = false) Integer publishStatus,
                          HttpServletRequest request) {
        AuthUtils.requireStaff((String) request.getAttribute("role"));
        return Result.success(noticeService.page(pageNum, pageSize, title, publishStatus));
    }

    @PostMapping("/save")
    public Result<?> save(@RequestBody SystemNotice notice, HttpServletRequest request) {
        AuthUtils.requireStaff((String) request.getAttribute("role"));
        noticeService.save(notice);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        AuthUtils.requireStaff((String) request.getAttribute("role"));
        noticeService.deleteById(id);
        return Result.success();
    }
}
