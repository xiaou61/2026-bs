package com.property.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.property.common.BusinessException;
import com.property.common.PageResult;
import com.property.entity.Announcement;
import com.property.mapper.AnnouncementMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AnnouncementService {

    @Resource
    private AnnouncementMapper announcementMapper;

    public List<Announcement> list() {
        return announcementMapper.selectPublicList();
    }

    public PageResult<Announcement> page(Integer pageNum, Integer pageSize, String title, Integer status) {
        PageHelper.startPage(pageNum, pageSize);
        List<Announcement> list = announcementMapper.selectPageList(title, status);
        PageInfo<Announcement> pageInfo = new PageInfo<>(list);
        PageResult<Announcement> result = new PageResult<>();
        result.setTotal(pageInfo.getTotal());
        result.setRecords(pageInfo.getList());
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
            announcementMapper.update(announcement);
        }
    }

    public void deleteById(Long id) {
        announcementMapper.deleteById(id);
    }
}
