package com.adoption.service;

import com.adoption.entity.SystemNotice;
import com.adoption.mapper.SystemNoticeMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NoticeService {

    @Autowired
    private SystemNoticeMapper systemNoticeMapper;

    public Map<String, Object> page(Integer pageNum, Integer pageSize) {
        Page<SystemNotice> page = new Page<>(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        Page<SystemNotice> result = systemNoticeMapper.selectPage(page, Wrappers.<SystemNotice>lambdaQuery().orderByDesc(SystemNotice::getPublishTime));
        Map<String, Object> data = new HashMap<>();
        data.put("list", result.getRecords());
        data.put("total", result.getTotal());
        return data;
    }

    public List<SystemNotice> publicList() {
        return systemNoticeMapper.selectList(Wrappers.<SystemNotice>lambdaQuery()
                .eq(SystemNotice::getStatus, 1)
                .orderByDesc(SystemNotice::getPublishTime));
    }

    public void add(SystemNotice notice) {
        if (notice.getPublishTime() == null) {
            notice.setPublishTime(LocalDateTime.now());
        }
        notice.setStatus(notice.getStatus() == null ? 1 : notice.getStatus());
        systemNoticeMapper.insert(notice);
    }

    public void update(SystemNotice notice) {
        systemNoticeMapper.updateById(notice);
    }

    public void delete(Long id) {
        systemNoticeMapper.deleteById(id);
    }
}
