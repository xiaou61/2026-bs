package com.phishingtraining.service;

import com.phishingtraining.mapper.CampaignTargetMapper;
import com.phishingtraining.mapper.ClickTrackingMapper;
import com.phishingtraining.mapper.EmployeeProfileMapper;
import com.phishingtraining.mapper.ExamAttemptMapper;
import com.phishingtraining.mapper.MailSendRecordMapper;
import com.phishingtraining.mapper.PhishingCampaignMapper;
import com.phishingtraining.mapper.RiskScoreMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final EmployeeProfileMapper employeeMapper;
    private final PhishingCampaignMapper campaignMapper;
    private final CampaignTargetMapper targetMapper;
    private final MailSendRecordMapper sendMapper;
    private final ClickTrackingMapper clickMapper;
    private final ExamAttemptMapper attemptMapper;
    private final RiskScoreMapper riskMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("employeeCount", employeeMapper.selectCount(null));
        data.put("campaignCount", campaignMapper.selectCount(null));
        data.put("targetCount", targetMapper.selectCount(null));
        data.put("sendCount", sendMapper.selectCount(null));
        data.put("clickCount", clickMapper.selectCount(null));
        data.put("attemptCount", attemptMapper.selectCount(null));
        data.put("riskCount", riskMapper.selectCount(null));
        data.put("campaignTrend", Arrays.asList(3, 5, 4, 8, 7, 9, 11));
        data.put("riskPie", Arrays.asList(map("低风险", 42), map("中风险", 31), map("高风险", 21), map("严重风险", 6)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
