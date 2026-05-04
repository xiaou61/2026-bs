package com.zerotrust.service;

import com.zerotrust.mapper.AccessApplicationMapper;
import com.zerotrust.mapper.AccessSessionMapper;
import com.zerotrust.mapper.AuditEventMapper;
import com.zerotrust.mapper.DeviceAssetMapper;
import com.zerotrust.mapper.DeviceCertificateMapper;
import com.zerotrust.mapper.RiskAssessmentMapper;
import com.zerotrust.mapper.AccessPolicyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final DeviceAssetMapper deviceMapper;
    private final RiskAssessmentMapper assessmentMapper;
    private final AccessPolicyMapper policyMapper;
    private final AccessApplicationMapper applicationMapper;
    private final AccessSessionMapper sessionMapper;
    private final DeviceCertificateMapper certificateMapper;
    private final AuditEventMapper eventMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("deviceCount", deviceMapper.countAll());
        data.put("assessmentCount", assessmentMapper.countAll());
        data.put("policyCount", policyMapper.countAll());
        data.put("applicationCount", applicationMapper.countAll());
        data.put("sessionCount", sessionMapper.countAll());
        data.put("certificateCount", certificateMapper.countAll());
        data.put("eventCount", eventMapper.countAll());
        data.put("accessTrend", Arrays.asList(8, 12, 11, 16, 14, 18, 21));
        data.put("riskPie", Arrays.asList(map("低风险", 36), map("中风险", 32), map("高风险", 24), map("严重风险", 8)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
