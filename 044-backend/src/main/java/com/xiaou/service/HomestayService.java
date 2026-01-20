package com.xiaou.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.entity.Homestay;

import java.math.BigDecimal;

public interface HomestayService extends IService<Homestay> {
    IPage<Homestay> pageList(Integer current, Integer size, String city, BigDecimal minPrice, BigDecimal maxPrice, String keyword);
    Homestay detail(Long id);
    IPage<Homestay> pageByHost(Long hostId, Integer current, Integer size);
}
