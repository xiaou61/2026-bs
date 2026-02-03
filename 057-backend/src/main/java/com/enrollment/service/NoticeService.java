package com.enrollment.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.enrollment.entity.Notice;
import com.enrollment.mapper.NoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    public IPage<Notice> getPage(Integer pageNum, Integer pageSize, String title, Integer type) {
        Page<Notice> page = new Page<>(pageNum, pageSize);
        return noticeMapper.selectPageWithAdmin(page, title, type);
    }

    public Notice getById(Long id) {
        return noticeMapper.selectById(id);
    }

    public void add(Notice notice) {
        noticeMapper.insert(notice);
    }

    public void update(Notice notice) {
        noticeMapper.updateById(notice);
    }

    public void delete(Long id) {
        noticeMapper.deleteById(id);
    }

    public void publish(Long id) {
        Notice notice = new Notice();
        notice.setId(id);
        notice.setStatus(1);
        notice.setPublishTime(LocalDateTime.now());
        noticeMapper.updateById(notice);
    }
}
