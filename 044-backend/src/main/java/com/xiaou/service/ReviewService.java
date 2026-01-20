package com.xiaou.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.entity.Review;

public interface ReviewService extends IService<Review> {
    void createReview(Long userId, Long bookingId, Integer rating, String content, String images);
    void replyReview(Long hostId, Long id, String reply);
    IPage<Review> pageByHomestay(Long homestayId, Integer current, Integer size);
    IPage<Review> pageByUser(Long userId, Integer current, Integer size);
}
