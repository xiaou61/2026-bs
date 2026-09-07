package com.agritrace.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.agritrace.entity.RegulationCheck;
import com.agritrace.mapper.RegulationCheckMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegulationCheckService extends BaseCrudService<RegulationCheck> {
    private final RegulationCheckMapper mapper;

    @Override
    protected BaseMapper<RegulationCheck> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"check_no", "target_name", "check_type", "regulator_name"};
    }

}
