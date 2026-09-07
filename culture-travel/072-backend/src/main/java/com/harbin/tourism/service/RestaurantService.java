package com.harbin.tourism.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.harbin.tourism.entity.Restaurant;
import com.harbin.tourism.mapper.RestaurantMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantMapper restaurantMapper;

    public Page<Restaurant> page(Integer pageNum, Integer pageSize, String keyword, String cuisineType) {
        LambdaQueryWrapper<Restaurant> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(keyword)) {
            wrapper.like(Restaurant::getName, keyword);
        }
        if (StrUtil.isNotBlank(cuisineType)) {
            wrapper.eq(Restaurant::getCuisineType, cuisineType);
        }
        wrapper.eq(Restaurant::getStatus, 1);
        wrapper.orderByDesc(Restaurant::getRating);
        return restaurantMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
    }

    public Restaurant getById(Long id) {
        return restaurantMapper.selectById(id);
    }

    public void save(Restaurant restaurant) {
        restaurant.setStatus(1);
        restaurantMapper.insert(restaurant);
    }

    public void update(Restaurant restaurant) {
        restaurantMapper.updateById(restaurant);
    }

    public void delete(Long id) {
        restaurantMapper.deleteById(id);
    }

    public List<Restaurant> listAll() {
        return restaurantMapper.selectList(new LambdaQueryWrapper<Restaurant>()
                .eq(Restaurant::getStatus, 1));
    }
}
