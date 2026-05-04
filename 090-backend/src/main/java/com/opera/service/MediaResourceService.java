package com.opera.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.opera.common.BusinessException;
import com.opera.entity.MediaResource;
import com.opera.mapper.MediaResourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class MediaResourceService {

    @Autowired
    private MediaResourceMapper resourceMapper;

    @Autowired
    private AuthService authService;

    public PageInfo<MediaResource> list(Long scheduleId, String keyword, Long userId, String role, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<MediaResource> list = "member".equals(role)
                ? resourceMapper.selectStudentList(userId, keyword)
                : resourceMapper.selectList(scheduleId, "artist".equals(role) ? userId : null, keyword);
        return new PageInfo<>(list);
    }

    public void add(MediaResource entity, Long userId, String role) {
        authService.assertTeacher(role);
        validate(entity);
        entity.setArtistId(userId);
        if (entity.getStatus() == null) {
            entity.setStatus(1);
        }
        resourceMapper.insert(entity);
    }

    public void update(MediaResource entity, Long userId, String role) {
        authService.assertTeacher(role);
        if (entity.getId() == null) {
            throw new BusinessException("资源ID不能为空");
        }
        assertResourcePermission(entity.getId(), userId, role);
        validate(entity);
        resourceMapper.update(entity);
    }

    public void delete(Long id, Long userId, String role) {
        authService.assertTeacher(role);
        assertResourcePermission(id, userId, role);
        resourceMapper.deleteById(id);
    }

    private void validate(MediaResource entity) {
        if (entity == null || entity.getScheduleId() == null || entity.getCourseId() == null || !StringUtils.hasText(entity.getTitle())) {
            throw new BusinessException("排期、剧目和资源标题不能为空");
        }
    }

    private void assertResourcePermission(Long id, Long userId, String role) {
        MediaResource db = resourceMapper.selectById(id);
        if (db == null) {
            throw new BusinessException("资源不存在");
        }
        if ("artist".equals(role) && !userId.equals(db.getArtistId())) {
            throw new BusinessException(403, "无权限操作");
        }
    }
}


