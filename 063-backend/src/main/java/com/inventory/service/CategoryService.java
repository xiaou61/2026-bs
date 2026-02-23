package com.inventory.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.inventory.common.BusinessException;
import com.inventory.common.PageResult;
import com.inventory.entity.Category;
import com.inventory.mapper.CategoryMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    public PageResult<Category> page(Integer pageNum, Integer pageSize, String name, Integer status) {
        PageHelper.startPage(pageNum, pageSize);
        List<Category> list = categoryMapper.selectPageList(name, status);
        PageInfo<Category> pageInfo = new PageInfo<>(list);
        PageResult<Category> result = new PageResult<>();
        result.setTotal(pageInfo.getTotal());
        result.setRecords(pageInfo.getList());
        return result;
    }

    public List<Category> list() {
        return categoryMapper.selectEnabledList();
    }

    public void save(Category category) {
        if (category == null || category.getName() == null || category.getName().trim().isEmpty()) {
            throw new BusinessException("分类名称不能为空");
        }
        category.setName(category.getName().trim());
        if (category.getSort() == null) {
            category.setSort(0);
        }
        if (category.getStatus() == null) {
            category.setStatus(1);
        }
        if (category.getId() == null) {
            categoryMapper.insert(category);
        } else {
            if (categoryMapper.selectById(category.getId()) == null) {
                throw new BusinessException("分类不存在");
            }
            categoryMapper.update(category);
        }
    }

    public void deleteById(Long id) {
        categoryMapper.deleteById(id);
    }

    public Category mustGetById(Long id) {
        Category category = categoryMapper.selectById(id);
        if (category == null) {
            throw new BusinessException("分类不存在");
        }
        return category;
    }
}
