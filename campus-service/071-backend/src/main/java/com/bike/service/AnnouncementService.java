package com.bike.service;

import com.bike.entity.Announcement;
import com.bike.mapper.AnnouncementMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnnouncementService {

    @Autowired
    private AnnouncementMapper announcementMapper;

    public PageInfo<Announcement> getList(Integer pageNum, Integer pageSize, String title, Integer type) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(announcementMapper.findList(title, type));
    }

    public void add(Announcement announcement) {
        if (announcement.getStatus() == null) {
            announcement.setStatus(1);
        }
        announcementMapper.insert(announcement);
    }

    public void update(Announcement announcement) {
        announcementMapper.update(announcement);
    }

    public void delete(Long id) {
        announcementMapper.deleteById(id);
    }
}
