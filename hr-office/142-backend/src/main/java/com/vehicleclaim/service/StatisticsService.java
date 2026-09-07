package com.vehicleclaim.service;

import com.vehicleclaim.mapper.InsurancePolicyMapper;
import com.vehicleclaim.mapper.VehicleProfileMapper;
import com.vehicleclaim.mapper.CustomerProfileMapper;
import com.vehicleclaim.mapper.ClaimApplicationMapper;
import com.vehicleclaim.mapper.AccidentReportMapper;
import com.vehicleclaim.mapper.MaterialChecklistMapper;
import com.vehicleclaim.mapper.MaterialReviewMapper;
import com.vehicleclaim.mapper.LossAssessmentMapper;
import com.vehicleclaim.mapper.CompensationRecordMapper;
import com.vehicleclaim.mapper.ProgressTrackMapper;
import com.vehicleclaim.mapper.FollowUpRecordMapper;
import com.vehicleclaim.mapper.ClaimNoticeMapper;
import com.vehicleclaim.mapper.OperationLogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final InsurancePolicyMapper insurancePolicyMapper;
    private final VehicleProfileMapper vehicleProfileMapper;
    private final CustomerProfileMapper customerProfileMapper;
    private final ClaimApplicationMapper claimApplicationMapper;
    private final AccidentReportMapper accidentReportMapper;
    private final MaterialChecklistMapper materialChecklistMapper;
    private final MaterialReviewMapper materialReviewMapper;
    private final LossAssessmentMapper lossAssessmentMapper;
    private final CompensationRecordMapper compensationRecordMapper;
    private final ProgressTrackMapper progressTrackMapper;
    private final FollowUpRecordMapper followUpRecordMapper;
    private final ClaimNoticeMapper claimNoticeMapper;
    private final OperationLogMapper operationLogMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("policyCount", insurancePolicyMapper.countAll());
        data.put("reportCount", accidentReportMapper.countAll());
        data.put("reviewCount", materialReviewMapper.countAll());
        data.put("progressCount", progressTrackMapper.countAll());
        data.put("claimTrend", Arrays.asList(12, 22, 31, 44, 53, 62, 70));
        data.put("claimPie", Arrays.asList(map("待审核", 35), map("处理中", 31), map("已赔付", 34)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}







