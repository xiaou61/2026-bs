package com.apitestcase.service;

import com.apitestcase.mapper.ApiEndpointMapper;
import com.apitestcase.mapper.ApiProjectMapper;
import com.apitestcase.mapper.DocumentSnapshotMapper;
import com.apitestcase.mapper.TestCaseMapper;
import com.apitestcase.mapper.TestExecutionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final ApiProjectMapper projectMapper;
    private final ApiEndpointMapper endpointMapper;
    private final TestCaseMapper testCaseMapper;
    private final TestExecutionMapper executionMapper;
    private final DocumentSnapshotMapper documentMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("projectCount", projectMapper.selectCount(null));
        data.put("endpointCount", endpointMapper.selectCount(null));
        data.put("caseCount", testCaseMapper.selectCount(null));
        data.put("executionCount", executionMapper.selectCount(null));
        data.put("documentCount", documentMapper.selectCount(null));
        data.put("passTrend", Arrays.asList(72, 78, 81, 79, 86, 90, 92));
        data.put("methodPie", Arrays.asList(map("GET", 18), map("POST", 14), map("PUT", 5), map("DELETE", 3)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
