package com.xiaou.hair.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.hair.entity.ServiceEntity;
import com.xiaou.hair.mapper.ServiceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 服务项目服务类
 */
@Service
public class ServiceService {

    @Autowired
    private ServiceMapper serviceMapper;

    /**
     * 服务项目列表（分页）
     */
    public Page<ServiceEntity> getServiceList(Integer pageNum, Integer pageSize, Long storeId, String category) {
        Page<ServiceEntity> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<ServiceEntity> wrapper = new LambdaQueryWrapper<>();
        
        // 按门店筛选
        if (storeId != null) {
            wrapper.eq(ServiceEntity::getStoreId, storeId);
        }
        
        // 按分类筛选
        if (category != null && !category.isEmpty()) {
            wrapper.eq(ServiceEntity::getCategory, category);
        }
        
        // 只查询上架的服务
        wrapper.eq(ServiceEntity::getStatus, 1);
        wrapper.orderByDesc(ServiceEntity::getCreatedAt);
        
        return serviceMapper.selectPage(page, wrapper);
    }

    /**
     * 服务项目详情
     */
    public ServiceEntity getServiceById(Long id) {
        ServiceEntity service = serviceMapper.selectById(id);
        if (service == null) {
            throw new RuntimeException("服务项目不存在");
        }
        return service;
    }

    /**
     * 获取服务分类列表
     */
    public List<String> getServiceCategories() {
        return Arrays.asList("男士理发", "女士理发", "烫染护理", "儿童理发", "造型设计");
    }

    /**
     * 获取门店的所有服务项目
     */
    public List<ServiceEntity> getServicesByStoreId(Long storeId) {
        LambdaQueryWrapper<ServiceEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ServiceEntity::getStoreId, storeId)
               .eq(ServiceEntity::getStatus, 1)
               .orderByAsc(ServiceEntity::getPrice);
        return serviceMapper.selectList(wrapper);
    }
}
