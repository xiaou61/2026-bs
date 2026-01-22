package com.xiaou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.entity.Notice;
import com.xiaou.mapper.NoticeMapper;
import com.xiaou.service.NoticeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

    @Override
    public IPage<Notice> pageNotices(Integer page, Integer size, Integer type) {
        LambdaQueryWrapper<Notice> wrapper = new LambdaQueryWrapper<>();
        if (type != null) {
            wrapper.eq(Notice::getType, type);
        }
        wrapper.eq(Notice::getStatus, 1) // 只查询已发布的
                .orderByDesc(Notice::getCreateTime);
        return this.page(new Page<>(page, size), wrapper);
    }

    @Override
    public List<Notice> getLatestNotices(Integer limit) {
        return this.list(new LambdaQueryWrapper<Notice>()
                .eq(Notice::getStatus, 1)
                .orderByDesc(Notice::getCreateTime)
                .last("LIMIT " + limit));
    }
}
