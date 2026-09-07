package com.privacyauth.service;

import com.privacyauth.mapper.AccessApplicationMapper;
import com.privacyauth.mapper.AccessLogMapper;
import com.privacyauth.mapper.AuthorizationRecordMapper;
import com.privacyauth.mapper.AuditReportMapper;
import com.privacyauth.mapper.DataSubjectMapper;
import com.privacyauth.mapper.RevocationRequestMapper;
import com.privacyauth.mapper.RiskWarningMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final DataSubjectMapper subjectMapper;
    private final AuthorizationRecordMapper authorizationMapper;
    private final AccessApplicationMapper applicationMapper;
    private final AccessLogMapper logMapper;
    private final RevocationRequestMapper revocationMapper;
    private final RiskWarningMapper warningMapper;
    private final AuditReportMapper reportMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("subjectCount", subjectMapper.countAll());
        data.put("authorizationCount", authorizationMapper.countAll());
        data.put("applicationCount", applicationMapper.countAll());
        data.put("logCount", logMapper.countAll());
        data.put("revocationCount", revocationMapper.countAll());
        data.put("warningCount", warningMapper.countAll());
        data.put("reportCount", reportMapper.countAll());
        data.put("authTrend", Arrays.asList(12, 16, 18, 21, 19, 24, 28));
        data.put("riskPie", Arrays.asList(map("无授权访问", 32), map("过期访问", 24), map("高频访问", 28), map("导出风险", 16)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
