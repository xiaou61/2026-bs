package com.xiaou.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.entity.Shop;

public interface ShopService extends IService<Shop> {
    Page<Shop> pageShops(int current, int size, String keyword);
    Shop getMyShop(Long ownerId);
}
