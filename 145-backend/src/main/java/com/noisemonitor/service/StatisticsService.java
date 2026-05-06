package com.noisemonitor.service;

import com.noisemonitor.mapper.ComplaintTicketMapper;
import com.noisemonitor.mapper.MonitoringSiteMapper;
import com.noisemonitor.mapper.NoiseSourceMapper;
import com.noisemonitor.mapper.OfficerProfileMapper;
import com.noisemonitor.mapper.HandlingTaskMapper;
import com.noisemonitor.mapper.PatrolRecordMapper;
import com.noisemonitor.mapper.RectificationNoticeMapper;
import com.noisemonitor.mapper.RetestRecordMapper;
import com.noisemonitor.mapper.PenaltyDecisionMapper;
import com.noisemonitor.mapper.PublicFeedbackMapper;
import com.noisemonitor.mapper.WarningRuleMapper;
import com.noisemonitor.mapper.PublicNoticeMapper;
import com.noisemonitor.mapper.OperationLogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final ComplaintTicketMapper complaintTicketMapper;
    private final MonitoringSiteMapper monitoringSiteMapper;
    private final NoiseSourceMapper noiseSourceMapper;
    private final OfficerProfileMapper officerProfileMapper;
    private final HandlingTaskMapper handlingTaskMapper;
    private final PatrolRecordMapper patrolRecordMapper;
    private final RectificationNoticeMapper rectificationNoticeMapper;
    private final RetestRecordMapper retestRecordMapper;
    private final PenaltyDecisionMapper penaltyDecisionMapper;
    private final PublicFeedbackMapper publicFeedbackMapper;
    private final WarningRuleMapper warningRuleMapper;
    private final PublicNoticeMapper publicNoticeMapper;
    private final OperationLogMapper operationLogMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("complaintCount", complaintTicketMapper.selectCount(null));
        data.put("taskCount", handlingTaskMapper.selectCount(null));
        data.put("rectifyCount", rectificationNoticeMapper.selectCount(null));
        data.put("publicCount", publicNoticeMapper.selectCount(null));
        data.put("noiseTrend", Arrays.asList(12, 22, 31, 44, 53, 62, 70));
        data.put("noisePie", Arrays.asList(map("待处理", 35), map("处理中", 31), map("已办结", 34)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}









