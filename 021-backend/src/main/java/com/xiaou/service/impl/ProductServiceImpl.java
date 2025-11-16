package com.xiaou.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.dto.ProductPublishDTO;
import com.xiaou.dto.ProductQueryDTO;
import com.xiaou.entity.Product;
import com.xiaou.exception.BusinessException;
import com.xiaou.mapper.ProductMapper;
import com.xiaou.service.ProductService;
import com.xiaou.vo.ProductDetailVO;
import com.xiaou.vo.ProductVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;

    @Override
    @Transactional
    public Long publishProduct(Long userId, ProductPublishDTO publishDTO) {
        // 验证分类是否存在
        // TODO: 添加分类验证逻辑

        Product product = new Product();
        BeanUtil.copyProperties(publishDTO, product);
        product.setSellerId(userId);
        product.setStatus("on_sale");
        product.setViewCount(0);
        product.setFavoriteCount(0);

        // 处理图片列表
        if (publishDTO.getImages() != null && !publishDTO.getImages().isEmpty()) {
            product.setImages(String.join(",", publishDTO.getImages()));
        }

        productMapper.insert(product);
        return product.getId();
    }

    @Override
    @Transactional
    public void updateProduct(Long userId, Long productId, ProductPublishDTO updateDTO) {
        Product product = productMapper.selectById(productId);
        if (product == null) {
            throw new BusinessException("商品不存在");
        }

        if (!product.getSellerId().equals(userId)) {
            throw new BusinessException("无权限修改该商品");
        }

        if ("sold".equals(product.getStatus())) {
            throw new BusinessException("已售出的商品不能修改");
        }

        BeanUtil.copyProperties(updateDTO, product, "id", "sellerId", "status", "viewCount", "favoriteCount");

        // 处理图片列表
        if (updateDTO.getImages() != null && !updateDTO.getImages().isEmpty()) {
            product.setImages(String.join(",", updateDTO.getImages()));
        }

        productMapper.updateById(product);
    }

    @Override
    @Transactional
    public void deleteProduct(Long userId, Long productId) {
        Product product = productMapper.selectById(productId);
        if (product == null) {
            throw new BusinessException("商品不存在");
        }

        if (!product.getSellerId().equals(userId)) {
            throw new BusinessException("无权限删除该商品");
        }

        if ("sold".equals(product.getStatus())) {
            throw new BusinessException("已售出的商品不能删除");
        }

        // 逻辑删除
        productMapper.deleteById(productId);
    }

    @Override
    @Transactional
    public void changeProductStatus(Long userId, Long productId, String status) {
        Product product = productMapper.selectById(productId);
        if (product == null) {
            throw new BusinessException("商品不存在");
        }

        if (!product.getSellerId().equals(userId)) {
            throw new BusinessException("无权限操作该商品");
        }

        if ("sold".equals(product.getStatus())) {
            throw new BusinessException("已售出的商品不能改变状态");
        }

        product.setStatus(status);
        productMapper.updateById(product);
    }

    @Override
    public Page<ProductVO> getProductList(ProductQueryDTO queryDTO) {
        Page<ProductVO> page = new Page<>(queryDTO.getCurrent(), queryDTO.getSize());
        return productMapper.getProductList(page, queryDTO.getCategoryId(),
                queryDTO.getKeyword(), queryDTO.getCondition(),
                queryDTO.getSortBy(), queryDTO.getSortOrder());
    }

    @Override
    public ProductDetailVO getProductDetail(Long productId) {
        ProductDetailVO productDetail = productMapper.getProductDetail(productId);
        if (productDetail == null) {
            throw new BusinessException("商品不存在");
        }

        // 增加浏览次数
        Product product = new Product();
        product.setId(productId);
        product.setViewCount(productDetail.getViewCount() + 1);
        productMapper.updateById(product);
        productDetail.setViewCount(productDetail.getViewCount() + 1);

        return productDetail;
    }

    @Override
    public Page<ProductVO> getMyProducts(Long userId, Integer current, Integer size) {
        Page<ProductVO> page = new Page<>(current, size);
        return productMapper.getMyProducts(page, userId);
    }
}