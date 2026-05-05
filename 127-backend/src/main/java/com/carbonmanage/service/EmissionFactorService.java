package com.carbonmanage.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.carbonmanage.entity.EmissionFactor;
import com.carbonmanage.mapper.EmissionFactorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmissionFactorService extends BaseCrudService<EmissionFactor> {
    private final EmissionFactorMapper emissionFactorMapper;

    @Override
    protected BaseMapper<EmissionFactor> mapper() {
        return emissionFactorMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"factor_no", "factor_name", "energy_type", "unit_name"};
    }
}
