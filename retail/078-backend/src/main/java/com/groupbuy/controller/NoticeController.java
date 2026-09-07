package com.groupbuy.controller;

import com.groupbuy.common.Result;
import com.groupbuy.entity.Notice;
import com.groupbuy.service.NoticeService;
import com.groupbuy.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          Integer type, Integer status,
                          HttpServletRequest request) {
        AuthUtils.requireAdmin(request);
        return Result.success(noticeService.page(pageNum, pageSize, type, status));
    }

    @GetMapping("/detail/{id}")
    public Result<?> detail(HttpServletRequest request, @PathVariable Long id) {
        AuthUtils.requireAdmin(request);
        return Result.success(noticeService.detail(id));
    }

    @PostMapping("/add")
    public Result<?> add(HttpServletRequest request, @RequestBody Notice notice) {
        AuthUtils.requireAdmin(request);
        noticeService.add(notice);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<?> update(HttpServletRequest request, @RequestBody Notice notice) {
        AuthUtils.requireAdmin(request);
        noticeService.update(notice);
        return Result.success();
    }

    @PutMapping("/status/{id}")
    public Result<?> updateStatus(HttpServletRequest request, @PathVariable Long id, @RequestBody java.util.Map<String, Integer> params) {
        AuthUtils.requireAdmin(request);
        noticeService.updateStatus(id, params.get("status"));
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<?> delete(HttpServletRequest request, @PathVariable Long id) {
        AuthUtils.requireAdmin(request);
        noticeService.delete(id);
        return Result.success();
    }

    @GetMapping("/front")
    public Result<?> front() {
        return Result.success(noticeService.front());
    }
}
