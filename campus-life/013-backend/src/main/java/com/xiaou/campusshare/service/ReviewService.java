package com.xiaou.campusshare.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.campusshare.entity.Review;
import com.xiaou.campusshare.mapper.ReviewMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService extends ServiceImpl<ReviewMapper, Review> {

    public List<Review> getReviewsByTarget(Long targetId, String reviewType) {
        LambdaQueryWrapper<Review> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Review::getReviewedId, targetId);
        wrapper.eq(Review::getReviewType, reviewType);
        wrapper.orderByDesc(Review::getCreateTime);
        return list(wrapper);
    }

    public Review getReviewByOrder(Long orderId, Long reviewerId) {
        LambdaQueryWrapper<Review> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Review::getOrderId, orderId);
        wrapper.eq(Review::getReviewerId, reviewerId);
        return getOne(wrapper);
    }
}

