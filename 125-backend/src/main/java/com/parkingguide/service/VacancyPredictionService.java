package com.parkingguide.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.parkingguide.entity.VacancyPrediction;
import com.parkingguide.mapper.VacancyPredictionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VacancyPredictionService extends BaseCrudService<VacancyPrediction> {
    private final VacancyPredictionMapper mapper;

    @Override
    protected BaseMapper<VacancyPrediction> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"prediction_no", "lot_name", "predict_time", "model_version"};
    }
}
