package com.drugreaction.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.drugreaction.entity.AdverseReport;
import com.drugreaction.mapper.AdverseReportMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdverseReportService extends BaseCrudService<AdverseReport> {
    private final AdverseReportMapper adverseReportMapper;

    @Override
    protected BaseMapper<AdverseReport> mapper() {
        return adverseReportMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"report_no", "patient_no", "drug_name", "report_time"};
    }
}
