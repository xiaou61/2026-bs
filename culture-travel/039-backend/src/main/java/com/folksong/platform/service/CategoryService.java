package com.folksong.platform.service;

import com.folksong.platform.dto.CategoryDTO;
import com.folksong.platform.entity.Category;
import java.util.List;

public interface CategoryService {
    List<Category> getAllActiveCategories();
    List<Category> getAllCategories();
    Category getCategoryById(Long id);
    void createCategory(CategoryDTO dto);
    void updateCategory(CategoryDTO dto);
    void deleteCategory(Long id);
    List<Category> getCategoriesByRegion(String region);
}
