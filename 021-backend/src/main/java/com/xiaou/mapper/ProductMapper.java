package com.xiaou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.entity.Product;
import com.xiaou.vo.ProductDetailVO;
import com.xiaou.vo.ProductVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductMapper extends BaseMapper<Product> {

    Page<ProductVO> getProductList(Page<ProductVO> page, @Param("categoryId") Long categoryId,
                                   @Param("keyword") String keyword, @Param("condition") String condition,
                                   @Param("sortBy") String sortBy, @Param("sortOrder") String sortOrder);

    ProductDetailVO getProductDetail(@Param("productId") Long productId);

    Page<ProductVO> getMyProducts(Page<ProductVO> page, @Param("sellerId") Long sellerId);
}