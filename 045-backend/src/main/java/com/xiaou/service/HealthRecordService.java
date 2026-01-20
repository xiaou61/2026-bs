package com.xiaou.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.entity.HealthRecord;

public interface HealthRecordService extends IService<HealthRecord> {
    IPage<HealthRecord> pageByElder(Long elderId, Integer current, Integer size);
    void addRecord(HealthRecord record, Long recorderId);
}
