package com.repair.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.repair.entity.ApplianceCategory;
import com.repair.mapper.ApplianceCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ApplianceCategoryService {

    @Autowired
    private ApplianceCategoryMapper categoryMapper;

    public Page<ApplianceCategory> getList(int pageNum, int pageSize, String name, Integer status) {
        Page<ApplianceCategory> page = new Page<>(pageNum, pageSize);
        QueryWrapper<ApplianceCategory> wrapper = new QueryWrapper<>();
        if (StringUtils.hasText(name)) {
            wrapper.like("name", name);
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        wrapper.orderByAsc("sort").orderByDesc("create_time");
        return categoryMapper.selectPage(page, wrapper);
    }

    public List<ApplianceCategory> getEnabledList() {
        QueryWrapper<ApplianceCategory> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 1).orderByAsc("sort");
        return categoryMapper.selectList(wrapper);
    }

    public void add(ApplianceCategory category) {
        categoryMapper.insert(category);
    }

    public void update(ApplianceCategory category) {
        categoryMapper.updateById(category);
    }

    public void delete(Long id) {
        categoryMapper.deleteById(id);
    }
}
