package com.foodinspect.service;

import com.foodinspect.mapper.InspectionPlanMapper;
import com.foodinspect.mapper.FoodItemMapper;
import com.foodinspect.mapper.MerchantProfileMapper;
import com.foodinspect.mapper.SamplingTaskMapper;
import com.foodinspect.mapper.AgencyProfileMapper;
import com.foodinspect.mapper.SampleRecordMapper;
import com.foodinspect.mapper.TestResultMapper;
import com.foodinspect.mapper.RecheckApplicationMapper;
import com.foodinspect.mapper.DisposalRecordMapper;
import com.foodinspect.mapper.PublicReportMapper;
import com.foodinspect.mapper.RiskWarningMapper;
import com.foodinspect.mapper.InspectionNoticeMapper;
import com.foodinspect.mapper.OperationLogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final InspectionPlanMapper inspectionPlanMapper;
    private final FoodItemMapper foodItemMapper;
    private final MerchantProfileMapper merchantProfileMapper;
    private final SamplingTaskMapper samplingTaskMapper;
    private final AgencyProfileMapper agencyProfileMapper;
    private final SampleRecordMapper sampleRecordMapper;
    private final TestResultMapper testResultMapper;
    private final RecheckApplicationMapper recheckApplicationMapper;
    private final DisposalRecordMapper disposalRecordMapper;
    private final PublicReportMapper publicReportMapper;
    private final RiskWarningMapper riskWarningMapper;
    private final InspectionNoticeMapper inspectionNoticeMapper;
    private final OperationLogMapper operationLogMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("planCount", inspectionPlanMapper.countAll());
        data.put("agencyCount", agencyProfileMapper.countAll());
        data.put("resultCount", testResultMapper.countAll());
        data.put("warningCount", riskWarningMapper.countAll());
        data.put("inspectTrend", Arrays.asList(12, 22, 31, 44, 53, 62, 70));
        data.put("inspectPie", Arrays.asList(map("待检测", 35), map("检测中", 31), map("已公示", 34)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}









