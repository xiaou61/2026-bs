package com.kindergarten.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kindergarten.common.BusinessException;
import com.kindergarten.entity.WeeklyRecipe;
import com.kindergarten.mapper.WeeklyRecipeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class RecipeService {

    @Autowired
    private WeeklyRecipeMapper resourceMapper;

    @Autowired
    private AuthService authService;

    public PageInfo<WeeklyRecipe> list(Long scheduleId, String keyword, Long userId, String role, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<WeeklyRecipe> list = "parent".equals(role)
                ? resourceMapper.selectStudentList(userId, keyword)
                : resourceMapper.selectList(scheduleId, "teacher".equals(role) ? userId : null, keyword);
        return new PageInfo<>(list);
    }

    public void add(WeeklyRecipe entity, Long userId, String role) {
        authService.assertTeacher(role);
        validate(entity);
        entity.setTeacherId(userId);
        if (entity.getStatus() == null) {
            entity.setStatus(1);
        }
        resourceMapper.insert(entity);
    }

    public void update(WeeklyRecipe entity, String role, Long userId) {
        authService.assertTeacher(role);
        if (entity.getId() == null) {
            throw new BusinessException("资源ID不能为空");
        }
        WeeklyRecipe current = resourceMapper.selectById(entity.getId());
        if (current == null) {
            throw new BusinessException("食谱不存在");
        }
        if ("teacher".equals(role) && !userId.equals(current.getTeacherId())) {
            throw new BusinessException(403, "无权限修改其他教师食谱");
        }
        if ("teacher".equals(role)) {
            entity.setTeacherId(current.getTeacherId());
        }
        validate(entity);
        resourceMapper.update(entity);
    }

    public void delete(Long id, String role, Long userId) {
        authService.assertTeacher(role);
        WeeklyRecipe current = resourceMapper.selectById(id);
        if (current == null) {
            throw new BusinessException("食谱不存在");
        }
        if ("teacher".equals(role) && !userId.equals(current.getTeacherId())) {
            throw new BusinessException(403, "无权限删除其他教师食谱");
        }
        resourceMapper.deleteById(id);
    }

    private void validate(WeeklyRecipe entity) {
        if (entity == null || entity.getScheduleId() == null || entity.getCourseId() == null || !StringUtils.hasText(entity.getTitle())) {
            throw new BusinessException("排课、活动和食谱标题不能为空");
        }
    }
}
