package com.servicequality.service;

import com.servicequality.common.BusinessException;
import com.servicequality.entity.OrderMessage;
import com.servicequality.mapper.OrderMessageMapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class OrderMessageService extends ServiceImpl<OrderMessageMapper, OrderMessage> {
    public Page<OrderMessage> page(Integer pageNum, Integer pageSize, String keyword, Long orderId, Integer sensitiveFlag) {
        LambdaQueryWrapper<OrderMessage> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(StringUtils.hasText(keyword), item -> item.like(OrderMessage::getMessageContent, keyword).or().like(OrderMessage::getSenderType, keyword));
        wrapper.eq(orderId != null, OrderMessage::getOrderId, orderId);
        wrapper.eq(sensitiveFlag != null, OrderMessage::getSensitiveFlag, sensitiveFlag);
        wrapper.orderByDesc(OrderMessage::getId);
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    public void saveEntity(OrderMessage entity) {
        if (entity.getId() == null) {
            
            entity.setCreateTime(LocalDateTime.now());
        }
        
        saveOrUpdate(entity);
    }

}
