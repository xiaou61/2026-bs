package com.charity.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.charity.common.BusinessException;
import com.charity.entity.Project;
import com.charity.mapper.ProjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProjectService {

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private UserService userService;

    public Page<Project> getList(int pageNum, int pageSize, Integer projectStatus) {
        Page<Project> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Project> wrapper = new QueryWrapper<>();
        if (projectStatus != null) {
            wrapper.eq("project_status", projectStatus);
        }
        wrapper.orderByDesc("create_time");
        return projectMapper.selectPage(page, wrapper);
    }

    public void add(Project project, Long currentUserId) {
        userService.requireAdmin(currentUserId);
        if (project.getTargetAmount() != null && project.getTargetAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(400, "目标金额必须大于0");
        }
        if (project.getCurrentAmount() == null) {
            project.setCurrentAmount(BigDecimal.ZERO);
        }
        if (project.getProjectStatus() == null) {
            project.setProjectStatus(0);
        }
        projectMapper.insert(project);
    }

    public void update(Project project, Long currentUserId) {
        userService.requireAdmin(currentUserId);
        if (project.getId() == null || projectMapper.selectById(project.getId()) == null) {
            throw new BusinessException(404, "项目不存在");
        }
        projectMapper.updateById(project);
    }

    public void delete(Long id, Long currentUserId) {
        userService.requireAdmin(currentUserId);
        projectMapper.deleteById(id);
    }
}
