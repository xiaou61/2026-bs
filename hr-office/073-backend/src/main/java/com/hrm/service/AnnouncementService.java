package com.hrm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hrm.entity.Announcement;
import com.hrm.mapper.AnnouncementMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class AnnouncementService {
    @Resource
    private AnnouncementMapper announcementMapper;

    public Announcement getById(Long id) {
        return announcementMapper.selectById(id);
    }

    public PageInfo<Announcement> getList(String title, Integer status, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(announcementMapper.selectList(title, status));
    }

    public List<Announcement> getTop(int limit) {
        return announcementMapper.selectTop(limit);
    }

    public void add(Announcement announcement) {
        announcement.setStatus(1);
        announcementMapper.insert(announcement);
    }

    public void update(Announcement announcement) {
        announcementMapper.update(announcement);
    }

    public void delete(Long id) {
        announcementMapper.deleteById(id);
    }
}
