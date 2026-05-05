package com.carbonmanage.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.carbonmanage.entity.VerificationReport;
import com.carbonmanage.mapper.VerificationReportMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationReportService extends BaseCrudService<VerificationReport> {
    private final VerificationReportMapper verificationReportMapper;

    @Override
    protected BaseMapper<VerificationReport> mapper() {
        return verificationReportMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"report_no", "company_no", "report_month", "auditor_name"};
    }
}
