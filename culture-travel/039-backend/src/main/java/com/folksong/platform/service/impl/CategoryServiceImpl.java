package com.folksong.platform.service.impl;

import com.folksong.platform.dto.CategoryDTO;
import com.folksong.platform.entity.Category;
import com.folksong.platform.exception.BusinessException;
import com.folksong.platform.repository.CategoryRepository;
import com.folksong.platform.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllActiveCategories() {
        return categoryRepository.findAllActive();
    }

    @Override
    public List<Category> getAllCategories() {
        return StreamSupport.stream(categoryRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new BusinessException("分类不存在"));
    }

    @Override
    public void createCategory(CategoryDTO dto) {
        if (categoryRepository.existsByName(dto.getName())) {
            throw new BusinessException("分类名称已存在");
        }
        Category category = new Category();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setRegion(dto.getRegion());
        category.setCoverImage(dto.getCoverImage());
        category.setSortOrder(dto.getSortOrder() != null ? dto.getSortOrder() : 0);
        category.setStatus(dto.getStatus() != null ? dto.getStatus() : 1);
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        categoryRepository.save(category);
    }

    @Override
    public void updateCategory(CategoryDTO dto) {
        Category category = categoryRepository.findById(dto.getId())
                .orElseThrow(() -> new BusinessException("分类不存在"));
        if (dto.getName() != null) category.setName(dto.getName());
        if (dto.getDescription() != null) category.setDescription(dto.getDescription());
        if (dto.getRegion() != null) category.setRegion(dto.getRegion());
        if (dto.getCoverImage() != null) category.setCoverImage(dto.getCoverImage());
        if (dto.getSortOrder() != null) category.setSortOrder(dto.getSortOrder());
        if (dto.getStatus() != null) category.setStatus(dto.getStatus());
        category.setUpdateTime(LocalDateTime.now());
        categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<Category> getCategoriesByRegion(String region) {
        return categoryRepository.findByRegion(region);
    }
}
