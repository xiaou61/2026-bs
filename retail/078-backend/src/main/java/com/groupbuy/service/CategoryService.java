package com.groupbuy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.groupbuy.common.BusinessException;
import com.groupbuy.entity.Category;
import com.groupbuy.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    public List<Category> list() {
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("sort", "id");
        return categoryMapper.selectList(wrapper);
    }

    public List<Category> tree() {
        List<Category> all = list();
        List<Category> roots = all.stream()
                .filter(c -> c.getParentId() == 0)
                .collect(Collectors.toList());
        roots.forEach(root -> {
            root.setChildren(all.stream()
                    .filter(c -> c.getParentId().equals(root.getId()))
                    .collect(Collectors.toList()));
        });
        return roots;
    }

    public void add(Category category) {
        categoryMapper.insert(category);
    }

    public void update(Category category) {
        categoryMapper.updateById(category);
    }

    public void delete(Long id) {
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", id);
        if (categoryMapper.selectCount(wrapper) > 0) {
            throw new BusinessException("存在子分类，无法删除");
        }
        categoryMapper.deleteById(id);
    }

    public Category getById(Long id) {
        return categoryMapper.selectById(id);
    }
}
