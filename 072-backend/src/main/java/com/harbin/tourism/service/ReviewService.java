package com.harbin.tourism.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.harbin.tourism.common.BusinessException;
import com.harbin.tourism.entity.*;
import com.harbin.tourism.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewMapper reviewMapper;

    @Autowired
    private ScenicSpotMapper spotMapper;

    @Autowired
    private HotelMapper hotelMapper;

    @Autowired
    private RestaurantMapper restaurantMapper;

    public Page<Review> page(Integer pageNum, Integer pageSize, String targetType, Long targetId) {
        LambdaQueryWrapper<Review> wrapper = new LambdaQueryWrapper<>();
        if (targetType != null) {
            wrapper.eq(Review::getTargetType, targetType);
        }
        if (targetId != null) {
            wrapper.eq(Review::getTargetId, targetId);
        }
        wrapper.eq(Review::getStatus, 1);
        wrapper.orderByDesc(Review::getCreatedAt);
        return reviewMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
    }

    public void save(Review review) {
        if (review.getTargetId() == null || review.getTargetType() == null) {
            throw new BusinessException(400, "评价对象不能为空");
        }
        if (review.getRating() == null || review.getRating() < 1 || review.getRating() > 5) {
            throw new BusinessException(400, "评分必须在1到5之间");
        }
        review.setStatus(1);
        reviewMapper.insert(review);
        updateTargetRating(review.getTargetType(), review.getTargetId());
    }

    public Review getById(Long id) {
        Review review = reviewMapper.selectById(id);
        if (review == null) {
            throw new BusinessException(404, "评价不存在");
        }
        return review;
    }

    public void delete(Long id, Long userId, boolean admin) {
        Review review = getById(id);
        if (!admin && !review.getUserId().equals(userId)) {
            throw new BusinessException(403, "无权删除该评价");
        }
        reviewMapper.deleteById(id);
        updateTargetRating(review.getTargetType(), review.getTargetId());
    }

    public void updateStatus(Long id, Integer status) {
        if (status == null || (status != 0 && status != 1)) {
            throw new BusinessException(400, "评价状态不合法");
        }
        Review existing = getById(id);
        Review review = new Review();
        review.setId(id);
        review.setStatus(status);
        reviewMapper.updateById(review);
        updateTargetRating(existing.getTargetType(), existing.getTargetId());
    }

    private void updateTargetRating(String targetType, Long targetId) {
        List<Review> reviews = reviewMapper.selectList(new LambdaQueryWrapper<Review>()
                .eq(Review::getTargetType, targetType)
                .eq(Review::getTargetId, targetId)
                .eq(Review::getStatus, 1));
        if (reviews.isEmpty()) return;
        double avgRating = reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0);
        BigDecimal rating = BigDecimal.valueOf(avgRating).setScale(1, RoundingMode.HALF_UP);
        switch (targetType) {
            case "spot":
                ScenicSpot spot = new ScenicSpot();
                spot.setId(targetId);
                spot.setRating(rating);
                spotMapper.updateById(spot);
                break;
            case "hotel":
                Hotel hotel = new Hotel();
                hotel.setId(targetId);
                hotel.setRating(rating);
                hotelMapper.updateById(hotel);
                break;
            case "restaurant":
                Restaurant restaurant = new Restaurant();
                restaurant.setId(targetId);
                restaurant.setRating(rating);
                restaurantMapper.updateById(restaurant);
                break;
        }
    }

    public List<Review> getByTarget(String targetType, Long targetId) {
        return reviewMapper.selectList(new LambdaQueryWrapper<Review>()
                .eq(Review::getTargetType, targetType)
                .eq(Review::getTargetId, targetId)
                .eq(Review::getStatus, 1)
                .orderByDesc(Review::getCreatedAt));
    }
}
