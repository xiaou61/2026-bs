package com.xiaou.artist.service;

import com.xiaou.artist.entity.Review;
import java.util.List;

public interface ReviewService {
    Review addReview(Review review);
    List<Review> getReviewsByOrderId(Long orderId);
    List<Review> getReviewsByToUserId(Long toUserId);
}
