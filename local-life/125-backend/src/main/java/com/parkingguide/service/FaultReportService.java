package com.parkingguide.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.parkingguide.entity.FaultReport;
import com.parkingguide.mapper.FaultReportMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FaultReportService extends BaseCrudService<FaultReport> {
    private final FaultReportMapper mapper;

    @Override
    protected BaseMapper<FaultReport> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"fault_no", "target_name", "fault_type", "severity_level"};
    }
}
