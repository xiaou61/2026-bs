package com.xiaou.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.common.Result;
import com.xiaou.entity.Notice;
import com.xiaou.mapper.NoticeMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "公告通知")
@RestController
@RequestMapping("/api/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeMapper noticeMapper;

    @Operation(summary = "公告列表")
    @GetMapping("/list")
    public Result<IPage<Notice>> list(@RequestParam(defaultValue = "1") Integer current,
                                      @RequestParam(defaultValue = "10") Integer size,
                                      @RequestParam(required = false) Integer type) {
        LambdaQueryWrapper<Notice> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Notice::getStatus, 1);
        if (type != null) {
            wrapper.eq(Notice::getType, type);
        }
        wrapper.orderByDesc(Notice::getPublishTime);
        return Result.success(noticeMapper.selectPage(new Page<>(current, size), wrapper));
    }

    @Operation(summary = "公告详情")
    @GetMapping("/{id}")
    public Result<Notice> detail(@PathVariable Long id) {
        return Result.success(noticeMapper.selectById(id));
    }
}
