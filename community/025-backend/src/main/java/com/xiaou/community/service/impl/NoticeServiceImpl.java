package com.xiaou.community.service.impl;

import com.xiaou.community.entity.Notice;
import com.xiaou.community.mapper.NoticeMapper;
import com.xiaou.community.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public void add(Notice notice) {
        noticeMapper.insert(notice);
    }

    @Override
    public void update(Notice notice) {
        noticeMapper.update(notice);
    }

    @Override
    public void delete(Integer id) {
        noticeMapper.deleteById(id);
    }

    @Override
    public Notice getById(Integer id) {
        return noticeMapper.findById(id);
    }

    @Override
    public List<Notice> getAll() {
        return noticeMapper.findAll();
    }
}
