package com.xiaou.service;

import com.xiaou.entity.Category;
import com.xiaou.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    public List<Category> getAllCategories() {
        return categoryMapper.findAll();
    }

    public List<Category> getActiveCategories() {
        return categoryMapper.findByStatus(1);
    }

    public Category getCategoryById(Long id) {
        return categoryMapper.findById(id);
    }

    public void addCategory(Category category) {
        if (category.getStatus() == null) {
            category.setStatus(1);
        }
        categoryMapper.insert(category);
    }

    public void updateCategory(Category category) {
        categoryMapper.update(category);
    }

    public void deleteCategory(Long id) {
        categoryMapper.deleteById(id);
    }
}

