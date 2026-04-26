package com.petcafe.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.petcafe.common.BusinessException;
import com.petcafe.common.PageResult;
import com.petcafe.entity.MenuCategory;
import com.petcafe.entity.MenuItem;
import com.petcafe.mapper.MenuCategoryMapper;
import com.petcafe.mapper.MenuItemMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CategoryService {

    @Resource
    private MenuCategoryMapper categoryMapper;

    @Resource
    private MenuItemMapper menuMapper;

    public PageResult<MenuCategory> page(Integer pageNum, Integer pageSize, String name, Integer status) {
        Page<MenuCategory> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<MenuCategory> wrapper = new LambdaQueryWrapper<MenuCategory>()
                .like(StringUtils.hasText(name), MenuCategory::getName, name == null ? null : name.trim())
                .eq(status != null, MenuCategory::getStatus, status)
                .orderByAsc(MenuCategory::getSort)
                .orderByDesc(MenuCategory::getId);
        Page<MenuCategory> resultPage = categoryMapper.selectPage(page, wrapper);
        PageResult<MenuCategory> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    public List<MenuCategory> listAll() {
        return categoryMapper.selectList(new LambdaQueryWrapper<MenuCategory>()
                .eq(MenuCategory::getStatus, 1)
                .orderByAsc(MenuCategory::getSort)
                .orderByDesc(MenuCategory::getId));
    }

    public MenuCategory getById(Long id) {
        MenuCategory category = categoryMapper.selectById(id);
        if (category == null) {
            throw new BusinessException("分类不存在");
        }
        return category;
    }

    public void save(MenuCategory category) {
        if (category == null || !StringUtils.hasText(category.getName())) {
            throw new BusinessException("分类名称不能为空");
        }
        if (category.getId() == null) {
            category.setStatus(category.getStatus() == null ? 1 : category.getStatus());
            category.setSort(category.getSort() == null ? 0 : category.getSort());
            categoryMapper.insert(category);
            return;
        }
        MenuCategory db = getById(category.getId());
        db.setName(category.getName().trim());
        db.setSort(category.getSort() == null ? db.getSort() : category.getSort());
        if (category.getStatus() != null) {
            db.setStatus(category.getStatus());
        }
        categoryMapper.updateById(db);
    }

    public void deleteById(Long id) {
        if (menuMapper.selectCount(new LambdaQueryWrapper<MenuItem>().eq(MenuItem::getCategoryId, id)) > 0) {
            throw new BusinessException("该分类下存在菜单，无法删除");
        }
        categoryMapper.deleteById(id);
    }
}
