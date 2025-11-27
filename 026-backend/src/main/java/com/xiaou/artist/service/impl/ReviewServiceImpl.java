package com.xiaou.artist.service.impl;

import com.xiaou.artist.entity.Review;
import com.xiaou.artist.mapper.ReviewMapper;
import com.xiaou.artist.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    
    @Autowired
    private ReviewMapper reviewMapper;
    
    @Override
    public Review addReview(Review review) {
        reviewMapper.insert(review);
        return review;
    }
    
    @Override
    public List<Review> getReviewsByOrderId(Long orderId) {
        return reviewMapper.selectByOrderId(orderId);
    }
    
    @Override
    public List<Review> getReviewsByToUserId(Long toUserId) {
        return reviewMapper.selectByToUserId(toUserId);
    }
}
