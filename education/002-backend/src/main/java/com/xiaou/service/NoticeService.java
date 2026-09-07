package com.xiaou.service;

import com.xiaou.common.BusinessException;
import com.xiaou.entity.Notice;
import com.xiaou.mapper.NoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    public Notice findById(Long id) {
        Notice notice = noticeMapper.findById(id);
        if (notice == null) {
            throw new BusinessException("公告不存在");
        }
        return notice;
    }

    public List<Notice> findAll(String keyword, String type) {
        return noticeMapper.findAll(keyword, type);
    }

    public Notice create(Notice notice) {
        if (notice.getStatus() == null) {
            notice.setStatus(1);
        }
        noticeMapper.insert(notice);
        return notice;
    }

    public Notice update(Notice notice) {
        Notice existNotice = noticeMapper.findById(notice.getId());
        if (existNotice == null) {
            throw new BusinessException("公告不存在");
        }
        noticeMapper.update(notice);
        return noticeMapper.findById(notice.getId());
    }

    public void delete(Long id) {
        Notice existNotice = noticeMapper.findById(id);
        if (existNotice == null) {
            throw new BusinessException("公告不存在");
        }
        noticeMapper.deleteById(id);
    }
}

