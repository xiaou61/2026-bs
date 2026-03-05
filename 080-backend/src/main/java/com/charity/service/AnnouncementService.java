package com.charity.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.charity.entity.Announcement;
import com.charity.mapper.AnnouncementMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnnouncementService {

    @Autowired
    private AnnouncementMapper announcementMapper;

    public Page<Announcement> getList(int pageNum, int pageSize, String announcementType) {
        Page<Announcement> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Announcement> wrapper = new QueryWrapper<>();
        if (announcementType != null && !announcementType.isEmpty()) {
            wrapper.eq("announcement_type", announcementType);
        }
        wrapper.eq("publish_status", 1);
        wrapper.orderByDesc("publish_time");
        return announcementMapper.selectPage(page, wrapper);
    }

    public void add(Announcement announcement) {
        announcementMapper.insert(announcement);
    }

    public void update(Announcement announcement) {
        announcementMapper.updateById(announcement);
    }

    public void delete(Long id) {
        announcementMapper.deleteById(id);
    }
}
