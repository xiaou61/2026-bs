package com.worksite.service;

import com.worksite.mapper.ConstructionProjectMapper;
import com.worksite.mapper.InspectionTaskMapper;
import com.worksite.mapper.HazardReportMapper;
import com.worksite.mapper.RectificationOrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final ConstructionProjectMapper constructionProjectMapper;
    private final InspectionTaskMapper inspectionTaskMapper;
    private final HazardReportMapper hazardReportMapper;
    private final RectificationOrderMapper rectificationOrderMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("projectCount", constructionProjectMapper.countAll());
        data.put("taskCount", inspectionTaskMapper.countAll());
        data.put("hazardCount", hazardReportMapper.countAll());
        data.put("rectifyCount", rectificationOrderMapper.countAll());
        data.put("hazardTrend", Arrays.asList(12, 19, 26, 33, 41, 52, 68));
        data.put("hazardPie", Arrays.asList(map("一般隐患", 36), map("较大隐患", 28), map("重大隐患", 22), map("已验收", 14)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
