package com.disaster.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.disaster.entity.MaterialCategory;
import com.disaster.mapper.MaterialCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private MaterialCategoryMapper categoryMapper;

    public List<MaterialCategory> list() {
        return categoryMapper.selectList(new LambdaQueryWrapper<MaterialCategory>()
                .eq(MaterialCategory::getStatus, 1)
                .orderByAsc(MaterialCategory::getSort));
    }

    public List<MaterialCategory> listAll() {
        return categoryMapper.selectList(new LambdaQueryWrapper<MaterialCategory>()
                .orderByAsc(MaterialCategory::getSort));
    }

    public void add(MaterialCategory category) {
        category.setStatus(1);
        categoryMapper.insert(category);
    }

    public void update(MaterialCategory category) {
        categoryMapper.updateById(category);
    }

    public void delete(Long id) {
        categoryMapper.deleteById(id);
    }
}
