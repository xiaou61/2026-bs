package com.agriculture.service;

import com.agriculture.entity.TreatmentRecord;
import com.agriculture.mapper.TreatmentRecordMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class TreatmentService extends ServiceImpl<TreatmentRecordMapper, TreatmentRecord> {

    public Page<TreatmentRecord> getPage(Integer pageNum, Integer pageSize, Long planId) {
        Page<TreatmentRecord> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<TreatmentRecord> wrapper = new LambdaQueryWrapper<>();
        if (planId != null) {
            wrapper.eq(TreatmentRecord::getPlanId, planId);
        }
        wrapper.orderByDesc(TreatmentRecord::getTreatmentDate);
        return this.page(page, wrapper);
    }
}
