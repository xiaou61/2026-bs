package com.xiaou.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.entity.CarePlan;
import com.xiaou.entity.CareRecord;

import java.util.List;

public interface CareService {
    List<CarePlan> getPlansByElder(Long elderId);
    void addPlan(CarePlan plan);
    IPage<CareRecord> pageRecords(Long elderId, Integer current, Integer size);
    void addRecord(CareRecord record, Long caregiverId);
}
