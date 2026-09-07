package com.xiaou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.entity.HealthRecord;
import com.xiaou.mapper.HealthRecordMapper;
import com.xiaou.service.HealthRecordService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class HealthRecordServiceImpl extends ServiceImpl<HealthRecordMapper, HealthRecord> implements HealthRecordService {

    @Override
    public IPage<HealthRecord> pageByElder(Long elderId, Integer current, Integer size) {
        LambdaQueryWrapper<HealthRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HealthRecord::getElderId, elderId);
        wrapper.orderByDesc(HealthRecord::getRecordDate);
        return page(new Page<>(current, size), wrapper);
    }

    @Override
    public void addRecord(HealthRecord record, Long recorderId) {
        record.setRecorderId(recorderId);
        if (record.getRecordDate() == null) {
            record.setRecordDate(LocalDate.now());
        }
        save(record);
    }
}
