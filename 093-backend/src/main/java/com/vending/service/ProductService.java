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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Resource
    private ProductInfoMapper productMapper;

    @Resource
    private ProductCategoryMapper categoryMapper;

    public PageResult<ProductInfo> page(Integer pageNum, Integer pageSize, String name, Long categoryId, Integer status) {
        Page<ProductInfo> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<ProductInfo> wrapper = new LambdaQueryWrapper<ProductInfo>()
                .like(StringUtils.hasText(name), ProductInfo::getName, name == null ? null : name.trim())
                .eq(categoryId != null, ProductInfo::getCategoryId, categoryId)
                .eq(status != null, ProductInfo::getStatus, status)
                .orderByDesc(ProductInfo::getId);
        Page<ProductInfo> resultPage = productMapper.selectPage(page, wrapper);
        fillCategoryName(resultPage.getRecords());
        PageResult<ProductInfo> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    public List<ProductInfo> listAll() {
        List<ProductInfo> list = productMapper.selectList(new LambdaQueryWrapper<ProductInfo>().orderByDesc(ProductInfo::getId));
        fillCategoryName(list);
        return list;
    }

    public List<ProductInfo> publicList() {
        List<ProductInfo> list = productMapper.selectList(new LambdaQueryWrapper<ProductInfo>()
                .eq(ProductInfo::getStatus, 1)
                .orderByDesc(ProductInfo::getId));
        fillCategoryName(list);
        return list;
    }

    public ProductInfo getById(Long id) {
        ProductInfo product = productMapper.selectById(id);
        if (product == null) {
            throw new BusinessException("商品不存在");
        }
        fillCategoryName(java.util.Collections.singletonList(product));
        return product;
    }

    public void save(ProductInfo product) {
        if (product == null || !StringUtils.hasText(product.getProductNo()) || !StringUtils.hasText(product.getName()) || product.getCategoryId() == null) {
            throw new BusinessException("商品信息不完整");
        }
        ProductCategory category = categoryMapper.selectById(product.getCategoryId());
        if (category == null) {
            throw new BusinessException("分类不存在");
        }
        validateProductNo(product);
        if (product.getId() == null) {
            product.setStatus(product.getStatus() == null ? 1 : product.getStatus());
            productMapper.insert(product);
        } else {
            ProductInfo db = getById(product.getId());
            db.setProductNo(product.getProductNo().trim());
            db.setName(product.getName().trim());
            db.setCategoryId(product.getCategoryId());
            db.setBrand(StringUtils.hasText(product.getBrand()) ? product.getBrand().trim() : null);
            db.setSpec(StringUtils.hasText(product.getSpec()) ? product.getSpec().trim() : null);
            db.setCover(StringUtils.hasText(product.getCover()) ? product.getCover().trim() : null);
            db.setCostPrice(product.getCostPrice());
            db.setSalePrice(product.getSalePrice());
            db.setStockWarn(product.getStockWarn());
            if (product.getStatus() != null) {
                db.setStatus(product.getStatus());
            }
            productMapper.updateById(db);
        }
    }

    public void deleteById(Long id) {
        productMapper.deleteById(id);
    }

    public Long countAll() {
        return productMapper.selectCount(null);
    }

    private void validateProductNo(ProductInfo product) {
        ProductInfo exist = productMapper.selectOne(new LambdaQueryWrapper<ProductInfo>()
                .eq(ProductInfo::getProductNo, product.getProductNo().trim())
                .last("limit 1"));
        if (exist != null && (product.getId() == null || !exist.getId().equals(product.getId()))) {
            throw new BusinessException("商品编号已存在");
        }
    }

    private void fillCategoryName(List<ProductInfo> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        List<Long> categoryIds = list.stream().map(ProductInfo::getCategoryId).distinct().collect(Collectors.toList());
        Map<Long, String> categoryMap = categoryMapper.selectList(new LambdaQueryWrapper<ProductCategory>().in(ProductCategory::getId, categoryIds))
                .stream()
                .collect(Collectors.toMap(ProductCategory::getId, ProductCategory::getName, (a, b) -> a, HashMap::new));
        list.forEach(item -> item.setCategoryName(categoryMap.get(item.getCategoryId())));
    }
}
