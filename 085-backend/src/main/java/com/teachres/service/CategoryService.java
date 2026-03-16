package com.teachres.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.teachres.common.BusinessException;
import com.teachres.entity.CourseCategory;
import com.teachres.mapper.CourseCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CourseCategoryMapper categoryMapper;

    public PageInfo<CourseCategory> list(String name, Integer status, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<CourseCategory> list = categoryMapper.selectList(name, status);
        return new PageInfo<>(list);
    }

    public List<CourseCategory> enabledList() {
        return categoryMapper.selectEnabledList();
    }

    public void add(CourseCategory category) {
        if (!StringUtils.hasText(category.getName()) || !StringUtils.hasText(category.getCode())) {
            throw new BusinessException("分类名称和编码不能为空");
        }
        if (categoryMapper.countByCode(category.getCode()) > 0) {
            throw new BusinessException("分类编码已存在");
        }
        if (category.getParentId() == null) {
            category.setParentId(0L);
        }
        if (category.getSort() == null) {
            category.setSort(0);
        }
        if (category.getStatus() == null) {
            category.setStatus(1);
        }
        categoryMapper.insert(category);
    }

    public void update(CourseCategory category) {
        if (category.getId() == null) {
            throw new BusinessException("参数错误");
        }
        categoryMapper.update(category);
    }

    public void delete(Long id) {
        categoryMapper.deleteById(id);
    }
}
