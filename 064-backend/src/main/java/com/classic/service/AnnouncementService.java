package com.classic.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.classic.common.BusinessException;
import com.classic.common.PageResult;
import com.classic.entity.Announcement;
import com.classic.mapper.AnnouncementMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AnnouncementService {

    @Resource
    private AnnouncementMapper announcementMapper;

    public List<Announcement> list() {
        return announcementMapper.selectList(new LambdaQueryWrapper<Announcement>().eq(Announcement::getStatus, 1).orderByDesc(Announcement::getId).last("limit 10"));
    }

    public PageResult<Announcement> page(Integer pageNum, Integer pageSize, String title, Integer status) {
        LambdaQueryWrapper<Announcement> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(title != null && !title.trim().isEmpty(), Announcement::getTitle, title == null ? null : title.trim());
        wrapper.eq(status != null, Announcement::getStatus, status);
        wrapper.orderByDesc(Announcement::getId);
        Page<Announcement> page = announcementMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        PageResult<Announcement> result = new PageResult<>();
        result.setTotal(page.getTotal());
        result.setRecords(page.getRecords());
        return result;
    }

    public void save(Announcement announcement, Long adminId) {
        if (announcement == null || announcement.getTitle() == null || announcement.getTitle().trim().isEmpty()) {
            throw new BusinessException("公告标题不能为空");
        }
        if (announcement.getContent() == null || announcement.getContent().trim().isEmpty()) {
            throw new BusinessException("公告内容不能为空");
        }
        announcement.setTitle(announcement.getTitle().trim());
        announcement.setContent(announcement.getContent().trim());
        announcement.setStatus(announcement.getStatus() == null ? 1 : announcement.getStatus());
        announcement.setAdminId(adminId);
        if (announcement.getId() == null) {
            announcementMapper.insert(announcement);
        } else {
            if (announcementMapper.selectById(announcement.getId()) == null) {
                throw new BusinessException("公告不存在");
            }
            announcementMapper.updateById(announcement);
        }
    }

    public void deleteById(Long id) {
        announcementMapper.deleteById(id);
    }
}
