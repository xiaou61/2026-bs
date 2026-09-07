package com.datamasking.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.datamasking.entity.DataSourceConfig;
import com.datamasking.mapper.DataSourceConfigMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DataSourceConfigService extends BaseCrudService<DataSourceConfig> {
    private final DataSourceConfigMapper mapper;

    @Override
    protected BaseMapper<DataSourceConfig> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"source_name", "source_code", "source_type", "owner_name"};
    }

}
