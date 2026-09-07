package com.xiaou.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.entity.Product;

public interface ProductService extends IService<Product> {
    IPage<Product> searchProducts(Integer page, Integer size, String keyword, Long categoryId, String status, String sortBy);
    void publishProduct(Product product);
    void updateProduct(Product product, Long userId);
    void deleteProduct(Long id, Long userId);
    void approveProduct(Long id, String status);
}

