package com.rental.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rental.entity.House;

import java.math.BigDecimal;
import java.util.Map;

public interface HouseService {

    /**
     * 发布房源
     */
    void publish(Long landlordId, House house);

    /**
     * 编辑房源
     */
    void update(Long landlordId, House house);

    /**
     * 删除房源
     */
    void delete(Long landlordId, Long houseId);

    /**
     * 获取房源列表（公开）
     */
    IPage<Map<String, Object>> getList(int page, int size, String city, String district, 
            BigDecimal minPrice, BigDecimal maxPrice, String roomType, String keyword);

    /**
     * 获取房源详情
     */
    Map<String, Object> getDetail(Long houseId, Long userId);

    /**
     * 获取我的房源（房东）
     */
    IPage<House> getMyHouses(Long landlordId, int page, int size, Integer status);

    /**
     * 上架/下架房源
     */
    void updateStatus(Long landlordId, Long houseId, Integer status);

    /**
     * 收藏/取消收藏
     */
    void toggleFavorite(Long userId, Long houseId);

    /**
     * 获取收藏列表
     */
    IPage<Map<String, Object>> getFavorites(Long userId, int page, int size);

    /**
     * 检查是否已收藏
     */
    boolean isFavorite(Long userId, Long houseId);
}
