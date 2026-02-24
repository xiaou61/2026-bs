package com.craft.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.craft.common.BusinessException;
import com.craft.entity.CraftCategory;
import com.craft.mapper.CraftCategoryMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CraftCategoryService {

    @Resource
    private CraftCategoryMapper categoryMapper;

    public List<CraftCategory> list() {
        return categoryMapper.selectList(new QueryWrapper<CraftCategory>()
                .eq("status", 1)
                .orderByAsc("sort", "id"));
    }

    public Page<CraftCategory> page(Integer pageNum, Integer pageSize, String name, Integer status) {
        QueryWrapper<CraftCategory> wrapper = new QueryWrapper<>();
        if (name != null && !name.trim().isEmpty()) {
            wrapper.like("name", name.trim());
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        wrapper.orderByAsc("sort", "id");
        return categoryMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
    }

    public void save(CraftCategory category) {
        if (category == null || category.getName() == null || category.getName().trim().isEmpty()) {
            throw new BusinessException("分类名称不能为空");
        }
        category.setName(category.getName().trim());
        if (category.getName().length() > 50) {
            throw new BusinessException("分类名称不能超过50字符");
        }
        if (category.getSort() == null) {
            category.setSort(0);
        }
        if (category.getStatus() != null && category.getStatus() != 0 && category.getStatus() != 1) {
            throw new BusinessException("分类状态不合法");
        }
        if (category.getId() == null) {
            Long count = categoryMapper.selectCount(new QueryWrapper<CraftCategory>().eq("name", category.getName().trim()));
            if (count > 0) {
                throw new BusinessException("分类名称已存在");
            }
            if (category.getStatus() == null) {
                category.setStatus(1);
            }
            categoryMapper.insert(category);
        } else {
            CraftCategory db = categoryMapper.selectById(category.getId());
            if (db == null) {
                throw new BusinessException("分类不存在");
            }
            Long count = categoryMapper.selectCount(new QueryWrapper<CraftCategory>()
                    .eq("name", category.getName().trim())
                    .ne("id", category.getId()));
            if (count > 0) {
                throw new BusinessException("分类名称已存在");
            }
            categoryMapper.updateById(category);
        }
    }

    public void deleteById(Long id) {
        categoryMapper.deleteById(id);
    }
}

