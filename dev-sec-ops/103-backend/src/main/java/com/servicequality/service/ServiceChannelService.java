package com.servicequality.service;

import com.servicequality.common.BusinessException;
import com.servicequality.entity.ServiceChannel;
import com.servicequality.mapper.ServiceChannelMapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class ServiceChannelService extends ServiceImpl<ServiceChannelMapper, ServiceChannel> {
    public Page<ServiceChannel> page(Integer pageNum, Integer pageSize, String keyword, String channelType, Integer status) {
        LambdaQueryWrapper<ServiceChannel> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(StringUtils.hasText(keyword), item -> item.like(ServiceChannel::getChannelName, keyword).or().like(ServiceChannel::getChannelType, keyword).or().like(ServiceChannel::getOwnerTeam, keyword));
        wrapper.eq(StringUtils.hasText(channelType), ServiceChannel::getChannelType, channelType);
        wrapper.eq(status != null, ServiceChannel::getStatus, status);
        wrapper.orderByDesc(ServiceChannel::getId);
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    public void saveEntity(ServiceChannel entity) {
        if (entity.getId() == null) {
            entity.setStatus(entity.getStatus() == null ? 1 : entity.getStatus());
            entity.setCreateTime(LocalDateTime.now());
        }
        entity.setUpdateTime(LocalDateTime.now());
        saveOrUpdate(entity);
    }

}
