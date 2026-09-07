package com.xiaou.hair.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.hair.entity.Store;
import com.xiaou.hair.mapper.StoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 门店服务类
 */
@Service
public class StoreService {

    @Autowired
    private StoreMapper storeMapper;

    /**
     * 门店列表（分页）
     */
    public Page<Store> getStoreList(Integer pageNum, Integer pageSize, String keyword, String sortBy) {
        Page<Store> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Store> wrapper = new LambdaQueryWrapper<>();
        
        // 只查询营业中的门店
        wrapper.eq(Store::getStatus, 1);
        
        // 关键词搜索
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.like(Store::getName, keyword)
                   .or()
                   .like(Store::getAddress, keyword);
        }
        
        // 排序
        if ("rating".equals(sortBy)) {
            wrapper.orderByDesc(Store::getRating);
        } else {
            // 默认按创建时间排序
            wrapper.orderByDesc(Store::getCreatedAt);
        }
        
        return storeMapper.selectPage(page, wrapper);
    }

    /**
     * 门店详情
     */
    public Store getStoreById(Long id) {
        Store store = storeMapper.selectById(id);
        if (store == null) {
            throw new RuntimeException("门店不存在");
        }
        return store;
    }

    /**
     * 获取所有营业中的门店
     */
    public List<Store> getAllActiveStores() {
        LambdaQueryWrapper<Store> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Store::getStatus, 1)
               .orderByDesc(Store::getRating);
        return storeMapper.selectList(wrapper);
    }
}
