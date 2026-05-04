package com.teachres.service;

import com.teachres.mapper.EvalRecordMapper;
import com.teachres.mapper.EvalTaskMapper;
import com.teachres.mapper.MathCourseMapper;
import com.teachres.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private MathCourseMapper courseMapper;

    @Autowired
    private EvalTaskMapper taskMapper;

    @Autowired
    private EvalRecordMapper recordMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> result = new HashMap<>();
        result.put("userCount", sysUserMapper.selectList(null, null, 1).size());
        result.put("courseCount", courseMapper.countAll());
        result.put("taskCount", taskMapper.countAll());
        result.put("recordCount", recordMapper.countAll());
        BigDecimal avgScore = recordMapper.avgScoreAll();
        result.put("avgScore", avgScore == null ? BigDecimal.ZERO : avgScore);
        result.put("monthTrend", buildMonthTrend(recordMapper.selectMonthTrend()));
        result.put("courseTop", buildCourseTop(recordMapper.selectCourseTop5()));
        return result;
    }

    private List<Map<String, Object>> buildMonthTrend(List<Map<String, Object>> rows) {
        Map<String, Long> counts = new LinkedHashMap<>();
        for (Map<String, Object> row : rows) {
            Object value = row.get("submit_time");
            if (value == null) {
                value = row.get("SUBMIT_TIME");
            }
            if (value == null) {
                continue;
            }
            String text = String.valueOf(value);
            if (text.length() < 7) {
                continue;
            }
            String month = text.substring(0, 7);
            counts.put(month, counts.getOrDefault(month, 0L) + 1);
        }
        List<Map<String, Object>> trend = new ArrayList<>();
        for (Map.Entry<String, Long> entry : counts.entrySet()) {
            Map<String, Object> item = new HashMap<>();
            item.put("month", entry.getKey());
            item.put("value", entry.getValue());
            trend.add(item);
        }
        return trend;
    }

    private List<Map<String, Object>> buildCourseTop(List<Map<String, Object>> rows) {
        List<Map<String, Object>> top = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            Object name = row.get("course_name");
            if (name == null) {
                name = row.get("COURSE_NAME");
            }
            Object value = row.get("avg_score");
            if (value == null) {
                value = row.get("AVG_SCORE");
            }
            Map<String, Object> item = new HashMap<>();
            item.put("name", name);
            item.put("value", value);
            top.add(item);
        }
        return top;
    }
}
