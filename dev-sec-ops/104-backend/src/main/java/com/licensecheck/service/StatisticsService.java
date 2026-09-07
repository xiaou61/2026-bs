package com.licensecheck.service;

import com.licensecheck.mapper.AuditReportMapper;
import com.licensecheck.mapper.DependencyPackageMapper;
import com.licensecheck.mapper.ProjectRepositoryMapper;
import com.licensecheck.mapper.RiskIssueMapper;
import com.licensecheck.mapper.ScanTaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final ProjectRepositoryMapper repositoryMapper;
    private final DependencyPackageMapper dependencyMapper;
    private final ScanTaskMapper scanTaskMapper;
    private final RiskIssueMapper riskIssueMapper;
    private final AuditReportMapper reportMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("repositoryCount", repositoryMapper.countAll());
        data.put("dependencyCount", dependencyMapper.countAll());
        data.put("taskCount", scanTaskMapper.countAll());
        data.put("riskCount", riskIssueMapper.countAll());
        data.put("reportCount", reportMapper.countAll());
        data.put("riskTrend", Arrays.asList(3, 5, 4, 7, 6, 8, 4));
        data.put("licensePie", Arrays.asList(
                map("MIT", 18),
                map("Apache-2.0", 13),
                map("GPL-3.0", 4),
                map("Unknown", 3)
        ));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
