package com.sparelife.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sparelife.entity.LifePrediction;
import com.sparelife.mapper.LifePredictionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LifePredictionService extends BaseCrudService<LifePrediction> {
    private final LifePredictionMapper mapper;

    @Override
    protected BaseMapper<LifePrediction> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"prediction_no", "part_name", "equipment_name", "risk_level"};
    }
}
