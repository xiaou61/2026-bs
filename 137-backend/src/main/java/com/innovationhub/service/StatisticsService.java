package com.innovationhub.service;

import com.innovationhub.mapper.IncubationProjectMapper;
import com.innovationhub.mapper.MentorProfileMapper;
import com.innovationhub.mapper.TeamProfileMapper;
import com.innovationhub.mapper.ProjectApplicationMapper;
import com.innovationhub.mapper.IncubationPlanMapper;
import com.innovationhub.mapper.MentorCoachingMapper;
import com.innovationhub.mapper.RoadshowEventMapper;
import com.innovationhub.mapper.RoadshowScoreMapper;
import com.innovationhub.mapper.FundingRecordMapper;
import com.innovationhub.mapper.MilestoneTaskMapper;
import com.innovationhub.mapper.AchievementShowcaseMapper;
import com.innovationhub.mapper.IncubationNoticeMapper;
import com.innovationhub.mapper.OperationLogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final IncubationProjectMapper consumableCatalogMapper;
    private final MentorProfileMapper supplierProfileMapper;
    private final TeamProfileMapper labRoomMapper;
    private final ProjectApplicationMapper stockItemMapper;
    private final IncubationPlanMapper purchaseRequestMapper;
    private final MentorCoachingMapper purchaseApprovalMapper;
    private final RoadshowEventMapper purchaseOrderMapper;
    private final RoadshowScoreMapper inboundRecordMapper;
    private final FundingRecordMapper outboundRecordMapper;
    private final MilestoneTaskMapper inventoryCheckMapper;
    private final AchievementShowcaseMapper warningRuleMapper;
    private final IncubationNoticeMapper stockWarningMapper;
    private final OperationLogMapper operationLogMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("projectCount", consumableCatalogMapper.selectCount(null));
        data.put("applicationCount", stockItemMapper.selectCount(null));
        data.put("roadshowCount", purchaseRequestMapper.selectCount(null));
        data.put("fundingCount", stockWarningMapper.selectCount(null));
        data.put("applicationTrend", Arrays.asList(12, 22, 31, 44, 53, 62, 70));
        data.put("stagePie", Arrays.asList(map("立项期", 35), map("孵化期", 31), map("路演期", 34)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}




