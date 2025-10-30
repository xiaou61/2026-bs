package com.xiaou.sport.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.sport.entity.SportRecord;
import com.xiaou.sport.mapper.SportRecordMapper;
import com.xiaou.sport.service.SportRecordService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SportRecordServiceImpl extends ServiceImpl<SportRecordMapper, SportRecord> implements SportRecordService {

    @Override
    public Map<String, Object> getStats(Long userId) {
        LambdaQueryWrapper<SportRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SportRecord::getUserId, userId);
        List<SportRecord> records = this.list(wrapper);

        int totalDuration = 0;
        BigDecimal totalDistance = BigDecimal.ZERO;
        int totalCalories = 0;
        int totalSteps = 0;

        for (SportRecord record : records) {
            totalDuration += record.getDuration() != null ? record.getDuration() : 0;
            totalDistance = totalDistance.add(record.getDistance() != null ? record.getDistance() : BigDecimal.ZERO);
            totalCalories += record.getCalories() != null ? record.getCalories() : 0;
            totalSteps += record.getSteps() != null ? record.getSteps() : 0;
        }

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalRecords", records.size());
        stats.put("totalDuration", totalDuration);
        stats.put("totalDistance", totalDistance);
        stats.put("totalCalories", totalCalories);
        stats.put("totalSteps", totalSteps);

        return stats;
    }
}
