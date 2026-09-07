package com.servicequality.service;

import com.servicequality.common.BusinessException;
import com.servicequality.entity.CustomerProfile;
import com.servicequality.mapper.CustomerProfileMapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class CustomerProfileService extends ServiceImpl<CustomerProfileMapper, CustomerProfile> {
    public Page<CustomerProfile> page(Integer pageNum, Integer pageSize, String keyword, String levelName, Integer status) {
        LambdaQueryWrapper<CustomerProfile> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(StringUtils.hasText(keyword), item -> item.like(CustomerProfile::getCustomerName, keyword).or().like(CustomerProfile::getPhone, keyword).or().like(CustomerProfile::getTags, keyword));
        wrapper.eq(StringUtils.hasText(levelName), CustomerProfile::getLevelName, levelName);
        wrapper.eq(status != null, CustomerProfile::getStatus, status);
        wrapper.orderByDesc(CustomerProfile::getId);
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    public void saveEntity(CustomerProfile entity) {
        if (entity.getId() == null) {
            entity.setStatus(entity.getStatus() == null ? 1 : entity.getStatus());
            entity.setCreateTime(LocalDateTime.now());
        }
        entity.setUpdateTime(LocalDateTime.now());
        saveOrUpdate(entity);
    }

}
