package com.trainingrefund.service;

import com.trainingrefund.mapper.CampusBranchMapper;
import com.trainingrefund.mapper.CourseCatalogMapper;
import com.trainingrefund.mapper.StudentProfileMapper;
import com.trainingrefund.mapper.TeacherProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final CampusBranchMapper campusBranchMapper;
    private final CourseCatalogMapper courseCatalogMapper;
    private final StudentProfileMapper studentProfileMapper;
    private final TeacherProfileMapper teacherProfileMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("branchCount", campusBranchMapper.countAll());
        data.put("courseCount", courseCatalogMapper.countAll());
        data.put("studentCount", studentProfileMapper.countAll());
        data.put("teacherCount", teacherProfileMapper.countAll());
        data.put("trend", Arrays.asList(120, 146, 163, 188, 201, 234, 260));
        data.put("pie", Arrays.asList(map("待上课", 32), map("已消课", 58), map("退费审批中", 25), map("财务已结清", 8)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
