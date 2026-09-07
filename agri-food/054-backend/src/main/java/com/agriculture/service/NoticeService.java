package com.agriculture.service;

import com.agriculture.entity.Notice;
import com.agriculture.mapper.NoticeMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NoticeService extends ServiceImpl<NoticeMapper, Notice> {

    public Page<Notice> getPage(Integer pageNum, Integer pageSize, String title) {
        Page<Notice> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Notice> wrapper = new LambdaQueryWrapper<>();
        if (title != null && !title.isEmpty()) {
            wrapper.like(Notice::getTitle, title);
        }
        wrapper.orderByDesc(Notice::getCreateTime);
        return this.page(page, wrapper);
    }

    public List<Notice> getPublished() {
        return this.lambdaQuery().eq(Notice::getStatus, 1).orderByDesc(Notice::getCreateTime).list();
    }
}
