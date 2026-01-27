package com.disaster.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.disaster.entity.Notice;
import com.disaster.mapper.NoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    public Page<Notice> page(int pageNum, int pageSize, Integer type, Integer status) {
        LambdaQueryWrapper<Notice> wrapper = new LambdaQueryWrapper<>();
        if (type != null) {
            wrapper.eq(Notice::getType, type);
        }
        if (status != null) {
            wrapper.eq(Notice::getStatus, status);
        }
        wrapper.orderByDesc(Notice::getCreateTime);
        return noticeMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
    }

    public Page<Notice> published(int pageNum, int pageSize) {
        LambdaQueryWrapper<Notice> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Notice::getStatus, 1);
        wrapper.orderByDesc(Notice::getPublishTime);
        return noticeMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
    }

    public Notice getById(Long id) {
        return noticeMapper.selectById(id);
    }

    public void add(Notice notice, Long publisherId) {
        notice.setPublisherId(publisherId);
        notice.setStatus(0);
        noticeMapper.insert(notice);
    }

    public void update(Notice notice) {
        noticeMapper.updateById(notice);
    }

    public void publish(Long id) {
        Notice notice = new Notice();
        notice.setId(id);
        notice.setStatus(1);
        notice.setPublishTime(LocalDateTime.now());
        noticeMapper.updateById(notice);
    }

    public void delete(Long id) {
        noticeMapper.deleteById(id);
    }
}
