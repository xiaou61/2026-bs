package com.datamasking.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.datamasking.entity.FieldLineage;
import com.datamasking.mapper.FieldLineageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FieldLineageService extends BaseCrudService<FieldLineage> {
    private final FieldLineageMapper mapper;

    @Override
    protected BaseMapper<FieldLineage> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"field_name", "dataset_name", "source_path", "target_path"};
    }

}
