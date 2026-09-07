package com.xiaou.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.common.Result;
import com.xiaou.entity.Notice;
import com.xiaou.service.NoticeService;
import com.xiaou.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/page")
    public Result<?> pageNotices(@RequestParam(defaultValue = "1") Integer page,
                                 @RequestParam(defaultValue = "10") Integer size,
                                 @RequestParam(required = false) Integer type) {
        IPage<Notice> result = noticeService.pageNotices(page, size, type);
        return Result.success(result);
    }

    @GetMapping("/latest")
    public Result<?> getLatestNotices(@RequestParam(defaultValue = "5") Integer limit) {
        List<Notice> list = noticeService.getLatestNotices(limit);
        return Result.success(list);
    }

    @GetMapping("/{id}")
    public Result<?> getNoticeById(@PathVariable Long id) {
        return Result.success(noticeService.getById(id));
    }

    @PostMapping
    public Result<?> addNotice(@RequestHeader("Authorization") String token,
                               @RequestBody Notice notice) {
        Long userId = jwtUtil.getUserIdFromToken(token.replace("Bearer ", ""));
        notice.setPublisherId(userId);
        notice.setCreateTime(LocalDateTime.now());
        noticeService.save(notice);
        return Result.success("发布成功");
    }

    @PutMapping("/{id}")
    public Result<?> updateNotice(@PathVariable Long id, @RequestBody Notice notice) {
        notice.setId(id);
        notice.setUpdateTime(LocalDateTime.now());
        noticeService.updateById(notice);
        return Result.success("更新成功");
    }

    @DeleteMapping("/{id}")
    public Result<?> deleteNotice(@PathVariable Long id) {
        noticeService.removeById(id);
        return Result.success("删除成功");
    }
}
