package com.repair.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.repair.entity.Project;
import com.repair.mapper.ProjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectMapper projectMapper;

    public Page<Project> getList(int pageNum, int pageSize, Integer projectStatus) {
        Page<Project> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Project> wrapper = new QueryWrapper<>();
        if (projectStatus != null) {
            wrapper.eq("project_status", projectStatus);
        }
        wrapper.orderByDesc("create_time");
        return projectMapper.selectPage(page, wrapper);
    }

    public void add(Project project) {
        projectMapper.insert(project);
    }

    public void update(Project project) {
        projectMapper.updateById(project);
    }

    public void delete(Long id) {
        projectMapper.deleteById(id);
    }
}

