package com.researchfund.service;

import com.researchfund.mapper.ResearchProjectMapper;
import com.researchfund.mapper.BudgetCategoryMapper;
import com.researchfund.mapper.BudgetAllocationMapper;
import com.researchfund.mapper.ExpenseClaimMapper;
import com.researchfund.mapper.InvoiceRecordMapper;
import com.researchfund.mapper.ApprovalTaskMapper;
import com.researchfund.mapper.PaymentRecordMapper;
import com.researchfund.mapper.ResearchAchievementMapper;
import com.researchfund.mapper.PaperRecordMapper;
import com.researchfund.mapper.PatentRecordMapper;
import com.researchfund.mapper.PerformanceStatisticMapper;
import com.researchfund.mapper.RiskWarningMapper;
import com.researchfund.mapper.OperationLogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final ResearchProjectMapper researchProjectMapper;
    private final BudgetCategoryMapper budgetCategoryMapper;
    private final BudgetAllocationMapper budgetAllocationMapper;
    private final ExpenseClaimMapper expenseClaimMapper;
    private final InvoiceRecordMapper invoiceRecordMapper;
    private final ApprovalTaskMapper approvalTaskMapper;
    private final PaymentRecordMapper paymentRecordMapper;
    private final ResearchAchievementMapper researchAchievementMapper;
    private final PaperRecordMapper paperRecordMapper;
    private final PatentRecordMapper patentRecordMapper;
    private final PerformanceStatisticMapper performanceStatisticMapper;
    private final RiskWarningMapper riskWarningMapper;
    private final OperationLogMapper operationLogMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("projectCount", researchProjectMapper.countAll());
        data.put("claimCount", expenseClaimMapper.countAll());
        data.put("achievementCount", researchAchievementMapper.countAll());
        data.put("riskCount", riskWarningMapper.countAll());
        data.put("claimTrend", Arrays.asList(12, 22, 31, 44, 53, 62, 70));
        data.put("achievementPie", Arrays.asList(map("论文", 35), map("专利", 31), map("其他成果", 34)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
