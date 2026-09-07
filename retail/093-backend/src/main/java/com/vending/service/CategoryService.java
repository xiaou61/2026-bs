package com.vending.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vending.common.BusinessException;
import com.vending.common.PageResult;
import com.vending.entity.ProductCategory;
import com.vending.entity.ProductInfo;
import com.vending.mapper.ProductCategoryMapper;
import com.vending.mapper.ProductInfoMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CategoryService {

    @Resource
    private ProductCategoryMapper categoryMapper;

    @Resource
    private ProductInfoMapper productMapper;

    public PageResult<ProductCategory> page(Integer pageNum, Integer pageSize, String name, Integer status) {
        Page<ProductCategory> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<ProductCategory> wrapper = new LambdaQueryWrapper<ProductCategory>()
                .like(StringUtils.hasText(name), ProductCategory::getName, name == null ? null : name.trim())
                .eq(status != null, ProductCategory::getStatus, status)
                .orderByAsc(ProductCategory::getSort)
                .orderByDesc(ProductCategory::getId);
        Page<ProductCategory> resultPage = categoryMapper.selectPage(page, wrapper);
        PageResult<ProductCategory> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    public List<ProductCategory> listAll() {
        return categoryMapper.selectList(new LambdaQueryWrapper<ProductCategory>()
                .eq(ProductCategory::getStatus, 1)
                .orderByAsc(ProductCategory::getSort)
                .orderByDesc(ProductCategory::getId));
    }

    public ProductCategory getById(Long id) {
        ProductCategory category = categoryMapper.selectById(id);
        if (category == null) {
            throw new BusinessException("分类不存在");
        }
        return category;
    }

    public void save(ProductCategory category) {
        if (category == null || !StringUtils.hasText(category.getName())) {
            throw new BusinessException("分类名称不能为空");
        }
        if (category.getId() == null) {
            category.setStatus(category.getStatus() == null ? 1 : category.getStatus());
            category.setSort(category.getSort() == null ? 0 : category.getSort());
            categoryMapper.insert(category);
        } else {
            ProductCategory db = getById(category.getId());
            db.setName(category.getName().trim());
            db.setSort(category.getSort() == null ? db.getSort() : category.getSort());
            if (category.getStatus() != null) {
                db.setStatus(category.getStatus());
            }
            categoryMapper.updateById(db);
        }
    }

    public void deleteById(Long id) {
        if (productMapper.selectCount(new LambdaQueryWrapper<ProductInfo>().eq(ProductInfo::getCategoryId, id)) > 0) {
            throw new BusinessException("该分类下存在商品，无法删除");
        }
        categoryMapper.deleteById(id);
    }
}
