package com.xiaou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.entity.Shop;
import com.xiaou.mapper.ShopMapper;
import com.xiaou.service.ShopService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements ShopService {

    @Override
    public Page<Shop> pageShops(int current, int size, String keyword) {
        LambdaQueryWrapper<Shop> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Shop::getStatus, 1);
        if (StringUtils.hasText(keyword)) {
            wrapper.like(Shop::getName, keyword).or().like(Shop::getAddress, keyword);
        }
        wrapper.orderByDesc(Shop::getRating);
        return page(new Page<>(current, size), wrapper);
    }

    @Override
    public Shop getMyShop(Long ownerId) {
        return getOne(new LambdaQueryWrapper<Shop>().eq(Shop::getOwnerId, ownerId));
    }
}
