package com.droneinspect.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.droneinspect.entity.DefectReport;
import com.droneinspect.mapper.DefectReportMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefectReportService extends BaseCrudService<DefectReport> {
    private final DefectReportMapper mapper;

    @Override
    protected BaseMapper<DefectReport> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"defect_no", "zone_name", "defect_type", "severity_level"};
    }
}
