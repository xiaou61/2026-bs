package com.rental.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rental.entity.Review;

public interface ReviewService {

    void create(Long tenantId, Review review);

    IPage<Review> getByHouseId(Long houseId, int page, int size);
}
