package com.examguard.service;

import com.examguard.mapper.ExamPlanMapper;
import com.examguard.mapper.InvigilatorProfileMapper;
import com.examguard.mapper.CandidateProfileMapper;
import com.examguard.mapper.ExamSessionMapper;
import com.examguard.mapper.SuspiciousBehaviorMapper;
import com.examguard.mapper.EvidenceRecordMapper;
import com.examguard.mapper.ReviewTaskMapper;
import com.examguard.mapper.ReviewDecisionMapper;
import com.examguard.mapper.WarningRuleMapper;
import com.examguard.mapper.DeviceMonitorMapper;
import com.examguard.mapper.ViolationAppealMapper;
import com.examguard.mapper.AlertNotificationMapper;
import com.examguard.mapper.OperationLogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final ExamPlanMapper researchProjectMapper;
    private final InvigilatorProfileMapper budgetCategoryMapper;
    private final CandidateProfileMapper budgetAllocationMapper;
    private final ExamSessionMapper expenseClaimMapper;
    private final SuspiciousBehaviorMapper invoiceRecordMapper;
    private final EvidenceRecordMapper approvalTaskMapper;
    private final ReviewTaskMapper paymentRecordMapper;
    private final ReviewDecisionMapper researchAchievementMapper;
    private final WarningRuleMapper paperRecordMapper;
    private final DeviceMonitorMapper patentRecordMapper;
    private final ViolationAppealMapper performanceStatisticMapper;
    private final AlertNotificationMapper riskWarningMapper;
    private final OperationLogMapper operationLogMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("planCount", researchProjectMapper.countAll());
        data.put("behaviorCount", expenseClaimMapper.countAll());
        data.put("evidenceCount", researchAchievementMapper.countAll());
        data.put("alertCount", riskWarningMapper.countAll());
        data.put("alertTrend", Arrays.asList(12, 22, 31, 44, 53, 62, 70));
        data.put("behaviorPie", Arrays.asList(map("切屏", 35), map("多人同屏", 31), map("异常音频", 34)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}




