package com.repair.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.repair.entity.Notice;
import com.repair.mapper.NoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    public Page<Notice> getList(int pageNum, int pageSize, String title, String type, Integer status) {
        Page<Notice> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Notice> wrapper = new QueryWrapper<>();
        if (StringUtils.hasText(title)) {
            wrapper.like("title", title);
        }
        if (StringUtils.hasText(type)) {
            wrapper.eq("type", type);
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        wrapper.orderByDesc("create_time");
        return noticeMapper.selectPage(page, wrapper);
    }

    public Page<Notice> getPublicList(int pageNum, int pageSize, String type) {
        Page<Notice> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Notice> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 1);
        if (StringUtils.hasText(type)) {
            wrapper.eq("type", type);
        }
        wrapper.orderByDesc("publish_time").orderByDesc("create_time");
        return noticeMapper.selectPage(page, wrapper);
    }

    public Notice getDetail(Long id) {
        return noticeMapper.selectById(id);
    }

    public Notice getPublicDetail(Long id) {
        Notice notice = noticeMapper.selectById(id);
        if (notice != null) {
            Integer viewCount = notice.getViewCount() == null ? 0 : notice.getViewCount();
            notice.setViewCount(viewCount + 1);
            noticeMapper.updateById(notice);
        }
        return notice;
    }

    public void add(Notice notice) {
        if (notice.getStatus() != null && notice.getStatus() == 1 && notice.getPublishTime() == null) {
            notice.setPublishTime(LocalDateTime.now());
        }
        noticeMapper.insert(notice);
    }

    public void update(Notice notice) {
        if (notice.getStatus() != null && notice.getStatus() == 1 && notice.getPublishTime() == null) {
            notice.setPublishTime(LocalDateTime.now());
        }
        noticeMapper.updateById(notice);
    }

    public void delete(Long id) {
        noticeMapper.deleteById(id);
    }
}
