package com.outpatientexam.service;

import com.outpatientexam.mapper.ExamDepartmentMapper;
import com.outpatientexam.mapper.ExamItemMapper;
import com.outpatientexam.mapper.ExamReportMapper;
import com.outpatientexam.mapper.QueueCallMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final ExamItemMapper examItemMapper;
    private final ExamDepartmentMapper examDepartmentMapper;
    private final ExamReportMapper examReportMapper;
    private final QueueCallMapper queueCallMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("itemCount", examItemMapper.countAll());
        data.put("departmentCount", examDepartmentMapper.countAll());
        data.put("reportCount", examReportMapper.countAll());
        data.put("queueCount", queueCallMapper.countAll());
        data.put("examTrend", Arrays.asList(16, 24, 21, 29, 33, 31, 38));
        data.put("examPie", Arrays.asList(
                map("预约提交", 28),
                map("报告处理中", 22),
                map("报告完成", 50)
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
