package com.topicselect.service;

import com.topicselect.mapper.TopicReleaseMapper;
import com.topicselect.mapper.TeacherProfileMapper;
import com.topicselect.mapper.StudentProfileMapper;
import com.topicselect.mapper.TopicApplicationMapper;
import com.topicselect.mapper.SelectionReviewMapper;
import com.topicselect.mapper.MutualSelectionMapper;
import com.topicselect.mapper.TaskBookMapper;
import com.topicselect.mapper.ProposalRecordMapper;
import com.topicselect.mapper.OpeningDefenseMapper;
import com.topicselect.mapper.MidtermCheckMapper;
import com.topicselect.mapper.GuidanceMeetingMapper;
import com.topicselect.mapper.MilestoneNoticeMapper;
import com.topicselect.mapper.OperationLogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final TopicReleaseMapper researchProjectMapper;
    private final TeacherProfileMapper budgetCategoryMapper;
    private final StudentProfileMapper budgetAllocationMapper;
    private final TopicApplicationMapper expenseClaimMapper;
    private final SelectionReviewMapper invoiceRecordMapper;
    private final MutualSelectionMapper approvalTaskMapper;
    private final TaskBookMapper paymentRecordMapper;
    private final ProposalRecordMapper researchAchievementMapper;
    private final OpeningDefenseMapper paperRecordMapper;
    private final MidtermCheckMapper patentRecordMapper;
    private final GuidanceMeetingMapper performanceStatisticMapper;
    private final MilestoneNoticeMapper riskWarningMapper;
    private final OperationLogMapper operationLogMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("topicCount", researchProjectMapper.countAll());
        data.put("applicationCount", expenseClaimMapper.countAll());
        data.put("proposalCount", researchAchievementMapper.countAll());
        data.put("noticeCount", riskWarningMapper.countAll());
        data.put("selectionTrend", Arrays.asList(12, 22, 31, 44, 53, 62, 70));
        data.put("stagePie", Arrays.asList(map("开题材料", 35), map("开题答辩", 31), map("中期检查", 34)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}


