package com.groupbuy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.groupbuy.common.BusinessException;
import com.groupbuy.entity.*;
import com.groupbuy.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewMapper reviewMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProductMapper productMapper;

    public void add(Long userId, Long orderId, Integer rating, String content, String images) {
        Orders order = ordersMapper.selectById(orderId);
        if (order == null || !order.getUserId().equals(userId)) {
            throw new BusinessException("订单不存在");
        }
        if (order.getStatus() != 3) {
            throw new BusinessException("订单状态异常，无法评价");
        }
        QueryWrapper<Review> checkWrapper = new QueryWrapper<>();
        checkWrapper.eq("order_id", orderId);
        if (reviewMapper.selectCount(checkWrapper) > 0) {
            throw new BusinessException("该订单已评价");
        }
        QueryWrapper<OrderItem> itemWrapper = new QueryWrapper<>();
        itemWrapper.eq("order_id", orderId);
        List<OrderItem> items = orderItemMapper.selectList(itemWrapper);
        for (OrderItem item : items) {
            Review review = new Review();
            review.setOrderId(orderId);
            review.setUserId(userId);
            review.setProductId(item.getProductId());
            review.setMerchantId(order.getMerchantId());
            review.setRating(rating);
            review.setContent(content);
            review.setImages(images);
            review.setStatus(0);
            reviewMapper.insert(review);
        }
    }

    public Page<Review> page(Integer pageNum, Integer pageSize, Long productId, Long merchantId, Integer status) {
        Page<Review> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Review> wrapper = new QueryWrapper<>();
        if (productId != null) {
            wrapper.eq("product_id", productId);
        }
        if (merchantId != null) {
            wrapper.eq("merchant_id", merchantId);
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        wrapper.orderByDesc("create_time");
        Page<Review> result = reviewMapper.selectPage(page, wrapper);
        result.getRecords().forEach(this::fillReviewInfo);
        return result;
    }

    public void audit(Long id, Integer status) {
        Review review = new Review();
        review.setId(id);
        review.setStatus(status);
        reviewMapper.updateById(review);
    }

    public Page<Review> productReviews(Long productId, Integer pageNum, Integer pageSize) {
        Page<Review> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Review> wrapper = new QueryWrapper<>();
        wrapper.eq("product_id", productId).eq("status", 1).orderByDesc("create_time");
        Page<Review> result = reviewMapper.selectPage(page, wrapper);
        result.getRecords().forEach(this::fillReviewInfo);
        return result;
    }

    private void fillReviewInfo(Review review) {
        User user = userMapper.selectById(review.getUserId());
        if (user != null) {
            review.setUsername(user.getNickname());
            review.setAvatar(user.getAvatar());
        }
        Product product = productMapper.selectById(review.getProductId());
        if (product != null) {
            review.setProductName(product.getName());
        }
    }
}
