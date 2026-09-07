package com.course.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.course.common.BusinessException;
import com.course.entity.CourseResource;
import com.course.mapper.CourseResourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ResourceService {

    @Autowired
    private CourseResourceMapper resourceMapper;

    @Autowired
    private AuthService authService;

    public PageInfo<CourseResource> list(Long scheduleId, String keyword, Long userId, String role, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<CourseResource> list = "student".equals(role)
                ? resourceMapper.selectStudentList(userId, keyword)
                : resourceMapper.selectList(scheduleId, "teacher".equals(role) ? userId : null, keyword);
        return new PageInfo<>(list);
    }

    public void add(CourseResource entity, Long userId, String role) {
        authService.assertTeacher(role);
        validate(entity);
        entity.setTeacherId(userId);
        if (entity.getStatus() == null) {
            entity.setStatus(1);
        }
        resourceMapper.insert(entity);
    }

    public void update(CourseResource entity, Long userId, String role) {
        authService.assertTeacher(role);
        if (entity.getId() == null) {
            throw new BusinessException(400, "资源ID不能为空");
        }
        CourseResource exists = resourceMapper.selectById(entity.getId());
        if (exists == null) {
            throw new BusinessException(404, "资源不存在");
        }
        if ("teacher".equals(role) && !userId.equals(exists.getTeacherId())) {
            throw new BusinessException(403, "无权限修改该资源");
        }
        validate(entity);
        entity.setTeacherId(exists.getTeacherId());
        resourceMapper.update(entity);
    }

    public void delete(Long id, Long userId, String role) {
        authService.assertTeacher(role);
        CourseResource exists = resourceMapper.selectById(id);
        if (exists == null) {
            throw new BusinessException(404, "资源不存在");
        }
        if ("teacher".equals(role) && !userId.equals(exists.getTeacherId())) {
            throw new BusinessException(403, "无权限删除该资源");
        }
        resourceMapper.deleteById(id);
    }

    private void validate(CourseResource entity) {
        if (entity == null || entity.getScheduleId() == null || entity.getCourseId() == null || !StringUtils.hasText(entity.getTitle())) {
            throw new BusinessException(400, "排课、课程和资源标题不能为空");
        }
    }
}
