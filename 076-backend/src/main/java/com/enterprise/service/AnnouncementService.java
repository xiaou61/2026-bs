package com.enterprise.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.enterprise.common.BusinessException;
import com.enterprise.entity.Announcement;
import com.enterprise.mapper.AnnouncementMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AnnouncementService {

    @Resource
    private AnnouncementMapper announcementMapper;

    public List<Announcement> list() {
        return announcementMapper.selectList(new QueryWrapper<Announcement>()
                .eq("status", 1)
                .orderByDesc("id")
                .last("limit 10"));
    }

    public Page<Announcement> page(Integer pageNum, Integer pageSize, String title, Integer status) {
        QueryWrapper<Announcement> wrapper = new QueryWrapper<>();
        if (title != null && !title.trim().isEmpty()) {
            wrapper.like("title", title.trim());
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        wrapper.orderByDesc("id");
        return announcementMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
    }

    public void save(Announcement announcement, Long adminId) {
        if (announcement == null || announcement.getTitle() == null || announcement.getTitle().trim().isEmpty()) {
            throw new BusinessException("公告标题不能为空");
        }
        if (announcement.getContent() == null || announcement.getContent().trim().isEmpty()) {
            throw new BusinessException("公告内容不能为空");
        }
        if (announcement.getStatus() != null && announcement.getStatus() != 0 && announcement.getStatus() != 1) {
            throw new BusinessException("公告状态不合法");
        }
        announcement.setTitle(announcement.getTitle().trim());
        announcement.setContent(announcement.getContent().trim());
        if (announcement.getTitle().length() > 100) {
            throw new BusinessException("公告标题不能超过100字符");
        }
        if (announcement.getContent().length() > 5000) {
            throw new BusinessException("公告内容不能超过5000字符");
        }
        announcement.setAdminId(adminId);
        if (announcement.getId() == null) {
            if (announcement.getStatus() == null) {
                announcement.setStatus(1);
            }
            announcementMapper.insert(announcement);
        } else {
            Announcement db = announcementMapper.selectById(announcement.getId());
            if (db == null) {
                throw new BusinessException("公告不存在");
            }
            announcementMapper.updateById(announcement);
        }
    }

    public void deleteById(Long id) {
        announcementMapper.deleteById(id);
    }
}




