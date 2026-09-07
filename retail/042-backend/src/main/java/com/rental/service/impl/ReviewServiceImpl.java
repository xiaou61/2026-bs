package com.rental.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rental.common.BusinessException;
import com.rental.entity.House;
import com.rental.entity.Review;
import com.rental.mapper.HouseMapper;
import com.rental.mapper.ReviewMapper;
import com.rental.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewMapper reviewMapper;

    @Autowired
    private HouseMapper houseMapper;

    @Override
    public void create(Long tenantId, Review review) {
        House house = houseMapper.selectById(review.getHouseId());
        if (house == null) {
            throw new BusinessException("房源不存在");
        }

        review.setTenantId(tenantId);
        review.setLandlordId(house.getLandlordId());
        reviewMapper.insert(review);
    }

    @Override
    public IPage<Review> getByHouseId(Long houseId, int page, int size) {
        LambdaQueryWrapper<Review> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Review::getHouseId, houseId);
        wrapper.orderByDesc(Review::getCreateTime);
        return reviewMapper.selectPage(new Page<>(page, size), wrapper);
    }
}
