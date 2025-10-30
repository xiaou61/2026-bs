package com.xiaou.sport.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.sport.entity.SportRecord;

import java.util.Map;

public interface SportRecordService extends IService<SportRecord> {
    Map<String, Object> getStats(Long userId);
}
