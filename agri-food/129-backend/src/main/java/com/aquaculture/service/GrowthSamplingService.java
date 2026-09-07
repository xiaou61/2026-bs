package com.aquaculture.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.aquaculture.entity.GrowthSampling;
import com.aquaculture.mapper.GrowthSamplingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GrowthSamplingService extends BaseCrudService<GrowthSampling> {
    private final GrowthSamplingMapper growthSamplingMapper;

    @Override
    protected BaseMapper<GrowthSampling> mapper() {
        return growthSamplingMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"sample_no", "batch_no", "sample_date", "recorder_name"};
    }
}
