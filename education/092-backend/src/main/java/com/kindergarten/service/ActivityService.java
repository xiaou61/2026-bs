package com.kindergarten.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kindergarten.common.BusinessException;
import com.kindergarten.entity.ActivityInfo;
import com.kindergarten.mapper.ActivityInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ActivityService {

    @Autowired
    private ActivityInfoMapper courseMapper;

    @Autowired
    private AuthService authService;

    public PageInfo<ActivityInfo> list(String courseName, Long teacherId, Long termId, Integer status, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ActivityInfo> list = courseMapper.selectList(courseName, teacherId, termId, status);
        return new PageInfo<>(list);
    }

    public List<ActivityInfo> enabledList() {
        return courseMapper.selectEnabled();
    }

    public void add(ActivityInfo entity, String role) {
        authService.assertAdmin(role);
        validate(entity);
        if (entity.getStatus() == null) {
            entity.setStatus(1);
        }
        courseMapper.insert(entity);
    }

    public void update(ActivityInfo entity, String role) {
        authService.assertAdmin(role);
        if (entity.getId() == null) {
            throw new BusinessException("活动ID不能为空");
        }
        validate(entity);
        courseMapper.update(entity);
    }

    public void delete(Long id, String role) {
        authService.assertAdmin(role);
        courseMapper.deleteById(id);
    }

    public ActivityInfo getById(Long id) {
        return courseMapper.selectById(id);
    }

    private void validate(ActivityInfo entity) {
        if (entity == null || !StringUtils.hasText(entity.getCourseName()) || !StringUtils.hasText(entity.getCourseCode())) {
            throw new BusinessException("活动名称和活动编码不能为空");
        }
    }
}
