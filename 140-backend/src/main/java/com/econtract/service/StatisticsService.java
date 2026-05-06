package com.econtract.service;

import com.econtract.mapper.ContractTemplateMapper;
import com.econtract.mapper.CounterpartyProfileMapper;
import com.econtract.mapper.SignerProfileMapper;
import com.econtract.mapper.ContractDraftMapper;
import com.econtract.mapper.SealApplicationMapper;
import com.econtract.mapper.ApprovalFlowMapper;
import com.econtract.mapper.ContractSigningMapper;
import com.econtract.mapper.SealRecordMapper;
import com.econtract.mapper.ArchiveRecordMapper;
import com.econtract.mapper.ExpirationReminderMapper;
import com.econtract.mapper.RiskClauseMapper;
import com.econtract.mapper.ContractNoticeMapper;
import com.econtract.mapper.OperationLogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final ContractTemplateMapper researchProjectMapper;
    private final CounterpartyProfileMapper budgetCategoryMapper;
    private final SignerProfileMapper budgetAllocationMapper;
    private final ContractDraftMapper expenseClaimMapper;
    private final SealApplicationMapper invoiceRecordMapper;
    private final ApprovalFlowMapper approvalTaskMapper;
    private final ContractSigningMapper paymentRecordMapper;
    private final SealRecordMapper researchAchievementMapper;
    private final ArchiveRecordMapper paperRecordMapper;
    private final ExpirationReminderMapper patentRecordMapper;
    private final RiskClauseMapper performanceStatisticMapper;
    private final ContractNoticeMapper riskWarningMapper;
    private final OperationLogMapper operationLogMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("templateCount", researchProjectMapper.countAll());
        data.put("sealCount", expenseClaimMapper.countAll());
        data.put("signCount", researchAchievementMapper.countAll());
        data.put("reminderCount", riskWarningMapper.countAll());
        data.put("contractTrend", Arrays.asList(12, 22, 31, 44, 53, 62, 70));
        data.put("contractPie", Arrays.asList(map("草稿", 35), map("审批中", 31), map("已归档", 34)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}






