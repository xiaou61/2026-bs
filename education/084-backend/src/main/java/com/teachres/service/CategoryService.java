package com.teachres.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.teachres.common.BusinessException;
import com.teachres.entity.MaterialCategory;
import com.teachres.mapper.MaterialCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private MaterialCategoryMapper categoryMapper;

    public PageInfo<MaterialCategory> list(String name, Integer status, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<MaterialCategory> list = categoryMapper.selectList(name, status);
        return new PageInfo<>(list);
    }

    public List<MaterialCategory> enabledList() {
        return categoryMapper.selectEnabledList();
    }

    public void add(MaterialCategory category) {
        if (!StringUtils.hasText(category.getName()) || !StringUtils.hasText(category.getCode())) {
            throw new BusinessException("分类名称和编码不能为空");
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

    public void update(MaterialCategory category) {
        categoryMapper.update(category);
    }

    public void delete(Long id) {
        categoryMapper.deleteById(id);
    }
}
