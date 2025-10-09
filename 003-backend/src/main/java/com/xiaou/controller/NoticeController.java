package com.xiaou.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.common.BusinessException;
import com.xiaou.common.Result;
import com.xiaou.entity.Notice;
import com.xiaou.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notices")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping("/list")
    public Result<?> list() {
        List<Notice> notices = noticeService.getPublishedNotices();
        return Result.success(notices);
    }

    @GetMapping("/{id}")
    public Result<?> detail(@PathVariable Long id) {
        Notice notice = noticeService.getById(id);
        return Result.success(notice);
    }

    @GetMapping("/all")
    public Result<?> all(@RequestParam(defaultValue = "1") Integer page,
                         @RequestParam(defaultValue = "10") Integer size,
                         @RequestAttribute(required = false) String userRole) {
        if (!"admin".equals(userRole)) {
            throw new BusinessException(403, "无权访问");
        }

        Page<Notice> noticePage = new Page<>(page, size);
        LambdaQueryWrapper<Notice> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Notice::getCreateTime);
        IPage<Notice> result = noticeService.page(noticePage, wrapper);
        return Result.success(result);
    }

    @PostMapping
    public Result<?> create(@RequestBody Notice notice, @RequestAttribute(required = false) String userRole) {
        if (!"admin".equals(userRole)) {
            throw new BusinessException(403, "无权访问");
        }
        notice.setStatus(1);
        noticeService.save(notice);
        return Result.success("发布成功");
    }

    @PutMapping("/{id}")
    public Result<?> update(@PathVariable Long id, @RequestBody Notice notice, @RequestAttribute(required = false) String userRole) {
        if (!"admin".equals(userRole)) {
            throw new BusinessException(403, "无权访问");
        }
        notice.setId(id);
        noticeService.updateById(notice);
        return Result.success("更新成功");
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, @RequestAttribute(required = false) String userRole) {
        if (!"admin".equals(userRole)) {
            throw new BusinessException(403, "无权访问");
        }
        noticeService.removeById(id);
        return Result.success("删除成功");
    }
}

