package com.xiaou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.common.BusinessException;
import com.xiaou.entity.Product;
import com.xiaou.mapper.ProductMapper;
import com.xiaou.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Override
    public IPage<Product> searchProducts(Integer page, Integer size, String keyword, Long categoryId, String status, String sortBy) {
        Page<Product> productPage = new Page<>(page, size);
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();

        if (keyword != null && !keyword.isEmpty()) {
            wrapper.and(w -> w.like(Product::getName, keyword)
                    .or().like(Product::getDescription, keyword)
                    .or().like(Product::getRegion, keyword));
        }

        if (categoryId != null) {
            wrapper.eq(Product::getCategoryId, categoryId);
        }

        if (status != null && !status.isEmpty()) {
            wrapper.eq(Product::getStatus, status);
        }

        if ("price_asc".equals(sortBy)) {
            wrapper.orderByAsc(Product::getPrice);
        } else if ("price_desc".equals(sortBy)) {
            wrapper.orderByDesc(Product::getPrice);
        } else {
            wrapper.orderByDesc(Product::getCreateTime);
        }

        return this.page(productPage, wrapper);
    }

    @Override
    public void publishProduct(Product product) {
        product.setStatus("pending");
        this.save(product);
    }

    @Override
    public void updateProduct(Product product, Long userId) {
        Product existingProduct = this.getById(product.getId());
        if (existingProduct == null) {
            throw new BusinessException("商品不存在");
        }

        if (!existingProduct.getFarmerId().equals(userId)) {
            throw new BusinessException("无权修改此商品");
        }

        this.updateById(product);
    }

    @Override
    public void deleteProduct(Long id, Long userId) {
        Product product = this.getById(id);
        if (product == null) {
            throw new BusinessException("商品不存在");
        }

        if (!product.getFarmerId().equals(userId)) {
            throw new BusinessException("无权删除此商品");
        }

        this.removeById(id);
    }

    @Override
    public void approveProduct(Long id, String status) {
        Product product = this.getById(id);
        if (product == null) {
            throw new BusinessException("商品不存在");
        }

        product.setStatus(status);
        this.updateById(product);
    }
}

