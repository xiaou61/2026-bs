package com.xiaou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.entity.CarePlan;
import com.xiaou.entity.CareRecord;
import com.xiaou.mapper.CarePlanMapper;
import com.xiaou.mapper.CareRecordMapper;
import com.xiaou.service.CareService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CareServiceImpl implements CareService {

    private final CarePlanMapper carePlanMapper;
    private final CareRecordMapper careRecordMapper;

    @Override
    public List<CarePlan> getPlansByElder(Long elderId) {
        LambdaQueryWrapper<CarePlan> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CarePlan::getElderId, elderId);
        wrapper.eq(CarePlan::getStatus, 1);
        wrapper.orderByDesc(CarePlan::getStartDate);
        return carePlanMapper.selectList(wrapper);
    }

    @Override
    public void addPlan(CarePlan plan) {
        plan.setStatus(1);
        carePlanMapper.insert(plan);
    }

    @Override
    public IPage<CareRecord> pageRecords(Long elderId, Integer current, Integer size) {
        LambdaQueryWrapper<CareRecord> wrapper = new LambdaQueryWrapper<>();
        if (elderId != null) {
            wrapper.eq(CareRecord::getElderId, elderId);
        }
        wrapper.orderByDesc(CareRecord::getCareTime);
        return careRecordMapper.selectPage(new Page<>(current, size), wrapper);
    }

    @Override
    public void addRecord(CareRecord record, Long caregiverId) {
        record.setCaregiverId(caregiverId);
        if (record.getCareTime() == null) {
            record.setCareTime(LocalDateTime.now());
        }
        careRecordMapper.insert(record);
    }
}
