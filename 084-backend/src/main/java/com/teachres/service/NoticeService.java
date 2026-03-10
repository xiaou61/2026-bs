package com.teachres.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.teachres.entity.SystemNotice;
import com.teachres.mapper.SystemNoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NoticeService {

    @Autowired
    private SystemNoticeMapper noticeMapper;

    public PageInfo<SystemNotice> list(String title, Integer status, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<SystemNotice> list = noticeMapper.selectList(title, status);
        return new PageInfo<>(list);
    }

    public List<SystemNotice> publicList() {
        return noticeMapper.selectPublicList();
    }

    public SystemNotice publicDetail(Long id) {
        noticeMapper.incrementViewCount(id);
        return noticeMapper.selectById(id);
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
        noticeMapper.insert(notice);
    }

    public void update(SystemNotice notice) {
        if (notice.getStatus() != null && notice.getStatus() == 1 && notice.getPublishTime() == null) {
            notice.setPublishTime(LocalDateTime.now());
        }
        noticeMapper.update(notice);
    }

    public void delete(Long id) {
        noticeMapper.deleteById(id);
    }
}
