package com.agriculture.service;

import com.agriculture.entity.ProductionTask;
import com.agriculture.mapper.ProductionTaskMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class TaskService extends ServiceImpl<ProductionTaskMapper, ProductionTask> {

    public Page<ProductionTask> getPage(Integer pageNum, Integer pageSize, Long planId, Integer status) {
        Page<ProductionTask> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<ProductionTask> wrapper = new LambdaQueryWrapper<>();
        if (planId != null) {
            wrapper.eq(ProductionTask::getPlanId, planId);
        }
        if (status != null) {
            wrapper.eq(ProductionTask::getStatus, status);
        }
        wrapper.orderByDesc(ProductionTask::getCreateTime);
        return this.page(page, wrapper);
    }

    public void updateStatus(Long id, Integer status) {
        ProductionTask task = new ProductionTask();
        task.setId(id);
        task.setStatus(status);
        if (status == 2) {
            task.setCompletionTime(LocalDateTime.now());
        }
        this.updateById(task);
    }
}
