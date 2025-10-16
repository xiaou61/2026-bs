package com.xiaou.campusshare.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.campusshare.entity.SkillService;
import com.xiaou.campusshare.mapper.SkillServiceMapper;

@org.springframework.stereotype.Service
public class SkillServiceService extends ServiceImpl<SkillServiceMapper, SkillService> {

    public Page<SkillService> getServiceList(int page, int size, String category, String keyword) {
        LambdaQueryWrapper<SkillService> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SkillService::getStatus, 1);
        wrapper.eq(SkillService::getIsDeleted, 0);
        
        if (category != null && !category.isEmpty()) {
            wrapper.eq(SkillService::getCategory, category);
        }
        
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.and(w -> w.like(SkillService::getTitle, keyword)
                    .or().like(SkillService::getDescription, keyword));
        }
        
        wrapper.orderByDesc(SkillService::getCreateTime);
        return page(new Page<>(page, size), wrapper);
    }

    public boolean increaseViewCount(Long serviceId) {
        SkillService service = getById(serviceId);
        if (service == null) {
            return false;
        }
        service.setViewCount(service.getViewCount() + 1);
        return updateById(service);
    }

    public boolean increaseOrderCount(Long serviceId) {
        SkillService service = getById(serviceId);
        if (service == null) {
            return false;
        }
        service.setOrderCount(service.getOrderCount() + 1);
        return updateById(service);
    }
}

