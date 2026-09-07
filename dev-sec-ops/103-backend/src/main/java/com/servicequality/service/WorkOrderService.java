package com.servicequality.service;

import com.servicequality.common.BusinessException;
import com.servicequality.entity.WorkOrder;
import com.servicequality.mapper.WorkOrderMapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class WorkOrderService extends ServiceImpl<WorkOrderMapper, WorkOrder> {
    public Page<WorkOrder> page(Integer pageNum, Integer pageSize, String keyword, Long customerId, Long agentId, Integer status, String priority) {
        LambdaQueryWrapper<WorkOrder> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(StringUtils.hasText(keyword), item -> item.like(WorkOrder::getOrderNo, keyword).or().like(WorkOrder::getTitle, keyword).or().like(WorkOrder::getContent, keyword).or().like(WorkOrder::getSolution, keyword));
        wrapper.eq(customerId != null, WorkOrder::getCustomerId, customerId);
        wrapper.eq(agentId != null, WorkOrder::getAgentId, agentId);
        wrapper.eq(status != null, WorkOrder::getStatus, status);
        wrapper.eq(StringUtils.hasText(priority), WorkOrder::getPriority, priority);
        wrapper.orderByDesc(WorkOrder::getId);
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    public void saveEntity(WorkOrder entity) {
        if (entity.getId() == null) {
            entity.setOrderNo(entity.getOrderNo() == null ? "WO" + System.currentTimeMillis() : entity.getOrderNo());
            entity.setStatus(entity.getStatus() == null ? 0 : entity.getStatus());
            entity.setCreateTime(LocalDateTime.now());
        }
        entity.setUpdateTime(LocalDateTime.now());
        saveOrUpdate(entity);
    }

    public void accept(Long id, Long userId) {
        WorkOrder entity = getById(id);
        if (entity == null) {
            throw new BusinessException(400, "工单不存在");
        }
        entity.setAgentId(userId);
        entity.setStatus(1);
        entity.setUpdateTime(LocalDateTime.now());
        updateById(entity);
    }

    public void resolve(Long id) {
        WorkOrder entity = getById(id);
        if (entity == null) {
            throw new BusinessException(400, "工单不存在");
        }
        entity.setStatus(2);
        entity.setSolution(entity.getSolution() == null ? "已给出解决方案，等待客户确认" : entity.getSolution());
        entity.setUpdateTime(LocalDateTime.now());
        updateById(entity);
    }

    public void close(Long id) {
        WorkOrder entity = getById(id);
        if (entity == null) {
            throw new BusinessException(400, "工单不存在");
        }
        entity.setStatus(3);
        entity.setUpdateTime(LocalDateTime.now());
        updateById(entity);
    }

}
