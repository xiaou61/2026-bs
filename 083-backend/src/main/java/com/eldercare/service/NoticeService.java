package com.eldercare.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eldercare.entity.SystemNotice;
import com.eldercare.mapper.SystemNoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NoticeService {

    @Autowired
    private SystemNoticeMapper systemNoticeMapper;

    public Page<SystemNotice> page(int pageNum, int pageSize, String title, Integer status) {
        Page<SystemNotice> page = new Page<>(pageNum, pageSize);
        QueryWrapper<SystemNotice> wrapper = new QueryWrapper<>();
        if (StringUtils.hasText(title)) {
            wrapper.like("title", title);
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        wrapper.orderByDesc("create_time");
        return systemNoticeMapper.selectPage(page, wrapper);
    }

    public List<SystemNotice> publicList() {
        QueryWrapper<SystemNotice> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 1).orderByDesc("publish_time");
        return systemNoticeMapper.selectList(wrapper);
    }

    public SystemNotice publicDetail(Long id) {
        SystemNotice notice = systemNoticeMapper.selectById(id);
        if (notice != null) {
            Integer viewCount = notice.getViewCount() == null ? 0 : notice.getViewCount();
            notice.setViewCount(viewCount + 1);
            systemNoticeMapper.updateById(notice);
        }
        return notice;
    }

    public void add(SystemNotice notice, Long userId) {
        notice.setPublisherId(userId);
        if (notice.getStatus() == null) {
            notice.setStatus(0);
        }
        if (notice.getStatus() == 1 && notice.getPublishTime() == null) {
            notice.setPublishTime(LocalDateTime.now());
        }
        if (notice.getViewCount() == null) {
            notice.setViewCount(0);
        }
        systemNoticeMapper.insert(notice);
    }

    public void update(SystemNotice notice) {
        if (notice.getStatus() != null && notice.getStatus() == 1 && notice.getPublishTime() == null) {
            notice.setPublishTime(LocalDateTime.now());
        }
        systemNoticeMapper.updateById(notice);
    }

    public void delete(Long id) {
        systemNoticeMapper.deleteById(id);
    }
}
