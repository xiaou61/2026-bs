package com.harbin.tourism.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
        review.setStatus(1);
        reviewMapper.insert(review);
        updateTargetRating(review.getTargetType(), review.getTargetId());
    }

    public void delete(Long id) {
        Review review = reviewMapper.selectById(id);
        reviewMapper.deleteById(id);
        if (review != null) {
            updateTargetRating(review.getTargetType(), review.getTargetId());
        }
    }

    public void updateStatus(Long id, Integer status) {
        Review review = new Review();
        review.setId(id);
        review.setStatus(status);
        reviewMapper.updateById(review);
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
