package com.agritrace.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.agritrace.entity.QualityInspection;
import com.agritrace.mapper.QualityInspectionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QualityInspectionService extends BaseCrudService<QualityInspection> {
    private final QualityInspectionMapper mapper;

    @Override
    protected BaseMapper<QualityInspection> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"report_no", "batch_no", "product_name", "inspector_name"};
    }

}
