package com.charity.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.charity.common.BusinessException;
import com.charity.entity.Announcement;
import com.charity.entity.User;
import com.charity.mapper.AnnouncementMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class AnnouncementService {

    @Autowired
    private AnnouncementMapper announcementMapper;

    @Autowired
    private UserService userService;

    public Page<Announcement> getList(int pageNum, int pageSize, String announcementType, Long currentUserId) {
        Page<Announcement> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Announcement> wrapper = new QueryWrapper<>();
        User currentUser = userService.requireActiveUser(currentUserId);
        if (announcementType != null && !announcementType.isEmpty()) {
            wrapper.eq("announcement_type", announcementType);
        }
        if (!userService.isAdmin(currentUser)) {
            wrapper.eq("publish_status", 1);
        }
        wrapper.orderByDesc("publish_time");
        return announcementMapper.selectPage(page, wrapper);
    }

    public void add(Announcement announcement, Long currentUserId) {
        userService.requireAdmin(currentUserId);
        if (announcement.getPublishStatus() == null) {
            announcement.setPublishStatus(0);
        }
        if (announcement.getPublishStatus() == 1 && announcement.getPublishTime() == null) {
            announcement.setPublishTime(LocalDateTime.now());
        }
        announcement.setPublisherId(currentUserId);
        announcementMapper.insert(announcement);
    }

    public void update(Announcement announcement, Long currentUserId) {
        userService.requireAdmin(currentUserId);
        if (announcement.getId() == null || announcementMapper.selectById(announcement.getId()) == null) {
            throw new BusinessException(404, "公告不存在");
        }
        if (announcement.getPublishStatus() != null && announcement.getPublishStatus() == 1 && announcement.getPublishTime() == null) {
            announcement.setPublishTime(LocalDateTime.now());
        }
        announcementMapper.updateById(announcement);
    }

    public void delete(Long id, Long currentUserId) {
        userService.requireAdmin(currentUserId);
        announcementMapper.deleteById(id);
    }
}
