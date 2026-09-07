package com.datamasking.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.datamasking.entity.RiskAlert;
import com.datamasking.mapper.RiskAlertMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RiskAlertService extends BaseCrudService<RiskAlert> {
    private final RiskAlertMapper mapper;

    @Override
    protected BaseMapper<RiskAlert> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"alert_no", "dataset_name", "alert_type", "risk_level"};
    }

}
