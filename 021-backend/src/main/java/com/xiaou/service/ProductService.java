package com.xiaou.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.dto.ProductPublishDTO;
import com.xiaou.dto.ProductQueryDTO;
import com.xiaou.vo.ProductDetailVO;
import com.xiaou.vo.ProductVO;

public interface ProductService {

    Long publishProduct(Long userId, ProductPublishDTO publishDTO);

    void updateProduct(Long userId, Long productId, ProductPublishDTO updateDTO);

    void deleteProduct(Long userId, Long productId);

    void changeProductStatus(Long userId, Long productId, String status);

    Page<ProductVO> getProductList(ProductQueryDTO queryDTO);

    ProductDetailVO getProductDetail(Long productId);

    Page<ProductVO> getMyProducts(Long userId, Integer current, Integer size);
}