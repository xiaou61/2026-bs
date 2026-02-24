package com.harbin.tourism.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.harbin.tourism.entity.Announcement;
import com.harbin.tourism.mapper.AnnouncementMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnouncementService {

    @Autowired
    private AnnouncementMapper announcementMapper;

    public Page<Announcement> page(Integer pageNum, Integer pageSize, String keyword) {
        LambdaQueryWrapper<Announcement> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(keyword)) {
            wrapper.like(Announcement::getTitle, keyword);
        }
        wrapper.eq(Announcement::getStatus, 1);
        wrapper.orderByDesc(Announcement::getIsTop).orderByDesc(Announcement::getCreatedAt);
        return announcementMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
    }

    public Announcement getById(Long id) {
        return announcementMapper.selectById(id);
    }

    public void save(Announcement announcement) {
        announcement.setStatus(1);
        announcementMapper.insert(announcement);
    }

    public void update(Announcement announcement) {
        announcementMapper.updateById(announcement);
    }

    public void delete(Long id) {
        announcementMapper.deleteById(id);
    }

    public List<Announcement> topList() {
        return announcementMapper.selectList(new LambdaQueryWrapper<Announcement>()
                .eq(Announcement::getStatus, 1)
                .eq(Announcement::getIsTop, 1)
                .orderByDesc(Announcement::getCreatedAt));
    }
}
