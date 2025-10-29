package com.xiaou.ordering.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.ordering.entity.Dish;
import com.xiaou.ordering.mapper.DishMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishService {

    @Autowired
    private DishMapper dishMapper;

    public List<Dish> getDishesByMerchant(Long merchantId) {
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Dish::getMerchantId, merchantId);
        wrapper.eq(Dish::getStatus, 1);
        wrapper.orderByDesc(Dish::getIsRecommend);
        wrapper.orderByDesc(Dish::getMonthSales);
        return dishMapper.selectList(wrapper);
    }

    public Dish getDishById(Long id) {
        return dishMapper.selectById(id);
    }

    public List<Dish> getRecommendDishes() {
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Dish::getStatus, 1);
        wrapper.eq(Dish::getIsRecommend, 1);
        wrapper.orderByDesc(Dish::getMonthSales);
        wrapper.last("LIMIT 12");
        return dishMapper.selectList(wrapper);
    }
}

