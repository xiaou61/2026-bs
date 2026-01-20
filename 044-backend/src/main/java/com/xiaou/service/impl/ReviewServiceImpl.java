package com.xiaou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.common.BusinessException;
import com.xiaou.entity.Booking;
import com.xiaou.entity.Homestay;
import com.xiaou.entity.Review;
import com.xiaou.mapper.BookingMapper;
import com.xiaou.mapper.HomestayMapper;
import com.xiaou.mapper.ReviewMapper;
import com.xiaou.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl extends ServiceImpl<ReviewMapper, Review> implements ReviewService {

    private final BookingMapper bookingMapper;
    private final HomestayMapper homestayMapper;

    @Override
    public void createReview(Long userId, Long bookingId, Integer rating, String content, String images) {
        Booking booking = bookingMapper.selectById(bookingId);
        if (booking == null || !booking.getUserId().equals(userId)) {
            throw new BusinessException("订单不存在");
        }
        if (booking.getStatus() != 4) {
            throw new BusinessException("只能评价已完成的订单");
        }
        // 检查是否已评价
        LambdaQueryWrapper<Review> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Review::getBookingId, bookingId);
        if (count(wrapper) > 0) {
            throw new BusinessException("该订单已评价");
        }
        Review review = new Review();
        review.setUserId(userId);
        review.setHomestayId(booking.getHomestayId());
        review.setBookingId(bookingId);
        review.setRating(rating);
        review.setContent(content);
        review.setImages(images);
        save(review);
    }

    @Override
    public void replyReview(Long hostId, Long id, String reply) {
        Review review = getById(id);
        if (review == null) {
            throw new BusinessException("评价不存在");
        }
        Homestay homestay = homestayMapper.selectById(review.getHomestayId());
        if (homestay == null || !homestay.getHostId().equals(hostId)) {
            throw new BusinessException("无权回复此评价");
        }
        review.setHostReply(reply);
        review.setReplyTime(LocalDateTime.now());
        updateById(review);
    }

    @Override
    public IPage<Review> pageByHomestay(Long homestayId, Integer current, Integer size) {
        LambdaQueryWrapper<Review> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Review::getHomestayId, homestayId);
        wrapper.orderByDesc(Review::getCreateTime);
        return page(new Page<>(current, size), wrapper);
    }

    @Override
    public IPage<Review> pageByUser(Long userId, Integer current, Integer size) {
        LambdaQueryWrapper<Review> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Review::getUserId, userId);
        wrapper.orderByDesc(Review::getCreateTime);
        return page(new Page<>(current, size), wrapper);
    }
}
