package com.xiaou.hair.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.hair.entity.Hairdresser;
import com.xiaou.hair.mapper.HairdresserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 理发师服务类
 */
@Service
public class HairdresserService {

    @Autowired
    private HairdresserMapper hairdresserMapper;

    /**
     * 理发师列表（分页）
     */
    public Page<Hairdresser> getHairdresserList(Integer pageNum, Integer pageSize, Long storeId, String sortBy) {
        Page<Hairdresser> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Hairdresser> wrapper = new LambdaQueryWrapper<>();
        
        // 按门店筛选
        if (storeId != null) {
            wrapper.eq(Hairdresser::getStoreId, storeId);
        }
        
        // 只查询在岗的理发师
        wrapper.eq(Hairdresser::getStatus, 1);
        
        // 排序
        if ("rating".equals(sortBy)) {
            wrapper.orderByDesc(Hairdresser::getRating);
        } else {
            wrapper.orderByDesc(Hairdresser::getCreatedAt);
        }
        
        return hairdresserMapper.selectPage(page, wrapper);
    }

    /**
     * 理发师详情
     */
    public Hairdresser getHairdresserById(Long id) {
        Hairdresser hairdresser = hairdresserMapper.selectById(id);
        if (hairdresser == null) {
            throw new RuntimeException("理发师不存在");
        }
        return hairdresser;
    }

    /**
     * 获取门店的所有理发师
     */
    public List<Hairdresser> getHairdressersByStoreId(Long storeId) {
        LambdaQueryWrapper<Hairdresser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Hairdresser::getStoreId, storeId)
               .eq(Hairdresser::getStatus, 1)
               .orderByDesc(Hairdresser::getRating);
        return hairdresserMapper.selectList(wrapper);
    }
}
