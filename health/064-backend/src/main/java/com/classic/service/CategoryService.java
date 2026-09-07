package com.classic.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.classic.common.BusinessException;
import com.classic.common.PageResult;
import com.classic.entity.Category;
import com.classic.mapper.CategoryMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    public List<Category> list() {
        return categoryMapper.selectList(new LambdaQueryWrapper<Category>().eq(Category::getStatus, 1).orderByAsc(Category::getSort).orderByDesc(Category::getId));
    }

    public PageResult<Category> page(Integer pageNum, Integer pageSize, String name, Integer status) {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(name != null && !name.trim().isEmpty(), Category::getName, name == null ? null : name.trim());
        wrapper.eq(status != null, Category::getStatus, status);
        wrapper.orderByAsc(Category::getSort).orderByDesc(Category::getId);
        Page<Category> page = categoryMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        PageResult<Category> result = new PageResult<>();
        result.setTotal(page.getTotal());
        result.setRecords(page.getRecords());
        return result;
    }

    public void save(Category category) {
        if (category == null || category.getName() == null || category.getName().trim().isEmpty()) {
            throw new BusinessException("分类名称不能为空");
        }
        category.setName(category.getName().trim());
        category.setSort(category.getSort() == null ? 0 : category.getSort());
        category.setStatus(category.getStatus() == null ? 1 : category.getStatus());
        if (category.getId() == null) {
            categoryMapper.insert(category);
        } else {
            if (categoryMapper.selectById(category.getId()) == null) {
                throw new BusinessException("分类不存在");
            }
            categoryMapper.updateById(category);
        }
    }

    public void deleteById(Long id) {
        categoryMapper.deleteById(id);
    }
}
