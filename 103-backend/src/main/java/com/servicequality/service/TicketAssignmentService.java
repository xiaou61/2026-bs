package com.servicequality.service;

import com.servicequality.common.BusinessException;
import com.servicequality.entity.TicketAssignment;
import com.servicequality.mapper.TicketAssignmentMapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class TicketAssignmentService extends ServiceImpl<TicketAssignmentMapper, TicketAssignment> {
    public Page<TicketAssignment> page(Integer pageNum, Integer pageSize, String keyword, Long orderId, Integer status) {
        LambdaQueryWrapper<TicketAssignment> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(StringUtils.hasText(keyword), item -> item.like(TicketAssignment::getAssignReason, keyword));
        wrapper.eq(orderId != null, TicketAssignment::getOrderId, orderId);
        wrapper.eq(status != null, TicketAssignment::getStatus, status);
        wrapper.orderByDesc(TicketAssignment::getId);
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    public void saveEntity(TicketAssignment entity) {
        if (entity.getId() == null) {
            entity.setStatus(entity.getStatus() == null ? 1 : entity.getStatus());
            entity.setCreateTime(LocalDateTime.now());
        }
        entity.setUpdateTime(LocalDateTime.now());
        saveOrUpdate(entity);
    }

}
