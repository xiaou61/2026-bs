package com.carbonmanage.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.carbonmanage.entity.EnergyConsumption;
import com.carbonmanage.mapper.EnergyConsumptionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnergyConsumptionService extends BaseCrudService<EnergyConsumption> {
    private final EnergyConsumptionMapper energyConsumptionMapper;

    @Override
    protected BaseMapper<EnergyConsumption> mapper() {
        return energyConsumptionMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"record_no", "company_no", "energy_type", "unit_name"};
    }
}
