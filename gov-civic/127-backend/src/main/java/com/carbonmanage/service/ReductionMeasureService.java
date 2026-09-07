package com.carbonmanage.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.carbonmanage.entity.ReductionMeasure;
import com.carbonmanage.mapper.ReductionMeasureMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReductionMeasureService extends BaseCrudService<ReductionMeasure> {
    private final ReductionMeasureMapper reductionMeasureMapper;

    @Override
    protected BaseMapper<ReductionMeasure> mapper() {
        return reductionMeasureMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"measure_no", "task_no", "measure_name"};
    }
}
