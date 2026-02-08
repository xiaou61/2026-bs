package com.movie.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.movie.entity.Announcement;
import com.movie.mapper.AnnouncementMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AnnouncementService {

    @Resource
    private AnnouncementMapper announcementMapper;

    public PageInfo<Announcement> getPage(Integer pageNum, Integer pageSize, String title) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(announcementMapper.selectPage(title));
    }

    public List<Announcement> getLatest() {
        return announcementMapper.selectLatest();
    }

    public void add(Announcement announcement) {
        announcementMapper.insert(announcement);
    }

    public void update(Announcement announcement) {
        announcementMapper.update(announcement);
    }

    public void delete(Long id) {
        announcementMapper.deleteById(id);
    }
}
