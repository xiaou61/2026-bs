package com.datamasking.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.datamasking.entity.DataSetCatalog;
import com.datamasking.mapper.DataSetCatalogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DataSetCatalogService extends BaseCrudService<DataSetCatalog> {
    private final DataSetCatalogMapper mapper;

    @Override
    protected BaseMapper<DataSetCatalog> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"dataset_name", "dataset_code", "table_name", "business_domain"};
    }

}
