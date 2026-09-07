package com.floodcity.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.floodcity.entity.DrainagePump;
import com.floodcity.mapper.DrainagePumpMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DrainagePumpService extends BaseCrudService<DrainagePump> {
    private final DrainagePumpMapper mapper;

    @Override
    protected BaseMapper<DrainagePump> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"pump_no", "pump_name", "district_name", "manager_name"};
    }
}
