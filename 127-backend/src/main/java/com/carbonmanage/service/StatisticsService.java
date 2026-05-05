package com.carbonmanage.service;

import com.carbonmanage.mapper.CompanyProfileMapper;
import com.carbonmanage.mapper.EmissionFactorMapper;
import com.carbonmanage.mapper.AccountingPeriodMapper;
import com.carbonmanage.mapper.EnergyConsumptionMapper;
import com.carbonmanage.mapper.EmissionRecordMapper;
import com.carbonmanage.mapper.ReductionTaskMapper;
import com.carbonmanage.mapper.ReductionMeasureMapper;
import com.carbonmanage.mapper.CarbonQuotaMapper;
import com.carbonmanage.mapper.VerificationReportMapper;
import com.carbonmanage.mapper.DataAttachmentMapper;
import com.carbonmanage.mapper.AlertRuleMapper;
import com.carbonmanage.mapper.CarbonWarningMapper;
import com.carbonmanage.mapper.OperationLogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final CompanyProfileMapper companyProfileMapper;
    private final EmissionFactorMapper emissionFactorMapper;
    private final AccountingPeriodMapper accountingPeriodMapper;
    private final EnergyConsumptionMapper energyConsumptionMapper;
    private final EmissionRecordMapper emissionRecordMapper;
    private final ReductionTaskMapper reductionTaskMapper;
    private final ReductionMeasureMapper reductionMeasureMapper;
    private final CarbonQuotaMapper carbonQuotaMapper;
    private final VerificationReportMapper verificationReportMapper;
    private final DataAttachmentMapper dataAttachmentMapper;
    private final AlertRuleMapper alertRuleMapper;
    private final CarbonWarningMapper carbonWarningMapper;
    private final OperationLogMapper operationLogMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("companyCount", companyProfileMapper.selectCount(null));
        data.put("emissionCount", emissionRecordMapper.selectCount(null));
        data.put("taskCount", reductionTaskMapper.selectCount(null));
        data.put("warningCount", carbonWarningMapper.selectCount(null));
        data.put("carbonTrend", Arrays.asList(18, 27, 36, 45, 58, 66, 78));
        data.put("scopePie", Arrays.asList(map("范围一", 38), map("范围二", 32), map("范围三", 30)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
