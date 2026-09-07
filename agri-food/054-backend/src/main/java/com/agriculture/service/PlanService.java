package com.agriculture.service;

import com.agriculture.entity.ProductionPlan;
import com.agriculture.mapper.ProductionPlanMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class PlanService extends ServiceImpl<ProductionPlanMapper, ProductionPlan> {

    public Page<ProductionPlan> getPage(Integer pageNum, Integer pageSize, String planName, Integer status) {
        Page<ProductionPlan> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<ProductionPlan> wrapper = new LambdaQueryWrapper<>();
        if (planName != null && !planName.isEmpty()) {
            wrapper.like(ProductionPlan::getPlanName, planName);
        }
        if (status != null) {
            wrapper.eq(ProductionPlan::getStatus, status);
        }
        wrapper.orderByDesc(ProductionPlan::getCreateTime);
        return this.page(page, wrapper);
    }

    public void updateStatus(Long id, Integer status) {
        this.lambdaUpdate().eq(ProductionPlan::getId, id).set(ProductionPlan::getStatus, status).update();
    }

    public void updateProgress(Long id, Integer progress) {
        this.lambdaUpdate().eq(ProductionPlan::getId, id).set(ProductionPlan::getProgress, progress).update();
    }
}
