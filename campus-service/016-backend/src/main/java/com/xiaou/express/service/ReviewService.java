package com.xiaou.express.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.express.common.BusinessException;
import com.xiaou.express.dto.ReviewRequest;
import com.xiaou.express.entity.Orders;
import com.xiaou.express.entity.Review;
import com.xiaou.express.entity.User;
import com.xiaou.express.mapper.OrdersMapper;
import com.xiaou.express.mapper.ReviewMapper;
import com.xiaou.express.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {

    @Autowired
    private ReviewMapper reviewMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private UserMapper userMapper;

    @Transactional
    public void createReview(Long userId, ReviewRequest request) {
        Orders order = ordersMapper.selectById(request.getOrderId());
        if (order == null) {
            throw new BusinessException("订单不存在");
        }

        if (!order.getPublisherId().equals(userId)) {
            throw new BusinessException("只有发单人可以评价");
        }

        if (order.getStatus() != 4) {
            throw new BusinessException("订单未完成");
        }

        LambdaQueryWrapper<Review> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Review::getOrderId, request.getOrderId());
        if (reviewMapper.selectCount(wrapper) > 0) {
            throw new BusinessException("已评价过该订单");
        }

        Review review = new Review();
        review.setOrderId(request.getOrderId());
        review.setOrderNo(order.getOrderNo());
        review.setReviewerId(userId);
        review.setRevieweeId(order.getTakerId());
        review.setRating(request.getRating());
        review.setTags(request.getTags());
        review.setContent(request.getContent());
        review.setIsAnonymous(request.getIsAnonymous());
        reviewMapper.insert(review);

        User taker = userMapper.selectById(order.getTakerId());
        if (request.getRating() == 5) {
            taker.setCreditScore(taker.getCreditScore() + 2);
        } else if (request.getRating() >= 3) {
            taker.setCreditScore(taker.getCreditScore() + 1);
        } else {
            taker.setCreditScore(taker.getCreditScore() - 5);
        }
        userMapper.updateById(taker);
    }

    public Page<Review> getUserReviews(Long userId, int pageNum, int pageSize) {
        Page<Review> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Review> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Review::getRevieweeId, userId)
                .orderByDesc(Review::getCreateTime);
        return reviewMapper.selectPage(page, wrapper);
    }
}

