package com.xiaou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.entity.Homestay;
import com.xiaou.mapper.HomestayMapper;
import com.xiaou.service.HomestayService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

@Service
public class HomestayServiceImpl extends ServiceImpl<HomestayMapper, Homestay> implements HomestayService {

    @Override
    public IPage<Homestay> pageList(Integer current, Integer size, String city, BigDecimal minPrice, BigDecimal maxPrice, String keyword) {
        LambdaQueryWrapper<Homestay> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Homestay::getStatus, 1);
        if (StringUtils.hasText(city)) {
            wrapper.eq(Homestay::getCity, city);
        }
        if (minPrice != null) {
            wrapper.ge(Homestay::getMinPrice, minPrice);
        }
        if (maxPrice != null) {
            wrapper.le(Homestay::getMinPrice, maxPrice);
        }
        if (StringUtils.hasText(keyword)) {
            wrapper.and(w -> w.like(Homestay::getName, keyword)
                    .or().like(Homestay::getDescription, keyword)
                    .or().like(Homestay::getAddress, keyword));
        }
        wrapper.orderByDesc(Homestay::getRating);
        return page(new Page<>(current, size), wrapper);
    }

    @Override
    public Homestay detail(Long id) {
        return getById(id);
    }

    @Override
    public IPage<Homestay> pageByHost(Long hostId, Integer current, Integer size) {
        LambdaQueryWrapper<Homestay> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Homestay::getHostId, hostId);
        wrapper.orderByDesc(Homestay::getCreateTime);
        return page(new Page<>(current, size), wrapper);
    }
}
