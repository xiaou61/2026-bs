package com.wallpaper.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wallpaper.common.BusinessException;
import com.wallpaper.entity.WallpaperCategory;
import com.wallpaper.mapper.WallpaperCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private WallpaperCategoryMapper categoryMapper;

    @Autowired
    private AuthService authService;

    public IPage<WallpaperCategory> list(String name, Integer status, Integer pageNum, Integer pageSize) {
        Page<WallpaperCategory> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<WallpaperCategory> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(name), WallpaperCategory::getName, name)
                .eq(status != null, WallpaperCategory::getStatus, status)
                .orderByAsc(WallpaperCategory::getSort)
                .orderByDesc(WallpaperCategory::getId);
        return categoryMapper.selectPage(page, wrapper);
    }

    public List<WallpaperCategory> enabledList() {
        LambdaQueryWrapper<WallpaperCategory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WallpaperCategory::getStatus, 1)
                .orderByAsc(WallpaperCategory::getSort)
                .orderByDesc(WallpaperCategory::getId);
        return categoryMapper.selectList(wrapper);
    }

    public void add(WallpaperCategory category, Long userId) {
        authService.assertAdmin(userId);
        validateCategory(category);
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

    public void update(WallpaperCategory category, Long userId) {
        authService.assertAdmin(userId);
        if (category.getId() == null) {
            throw new BusinessException("分类ID不能为空");
        }
        validateCategory(category);
        categoryMapper.updateById(category);
    }

    public void delete(Long id, Long userId) {
        authService.assertAdmin(userId);
        categoryMapper.deleteById(id);
    }

    private void validateCategory(WallpaperCategory category) {
        if (category == null || !StringUtils.hasText(category.getName()) || !StringUtils.hasText(category.getCode())) {
            throw new BusinessException("分类名称和编码不能为空");
        }
    }
}
