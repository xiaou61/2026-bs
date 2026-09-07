package com.aquaculture.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.aquaculture.entity.ProductionStatistic;
import com.aquaculture.mapper.ProductionStatisticMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductionStatisticService extends BaseCrudService<ProductionStatistic> {
    private final ProductionStatisticMapper productionStatisticMapper;

    @Override
    protected BaseMapper<ProductionStatistic> mapper() {
        return productionStatisticMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"stat_no", "pond_no", "stat_month"};
    }
}
