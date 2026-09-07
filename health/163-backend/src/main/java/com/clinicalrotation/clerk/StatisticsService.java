package com.clinicalrotation.clerk;

import com.clinicalrotation.mapper.DepartmentProfileMapper;
import com.clinicalrotation.mapper.StudentProfileMapper;
import com.clinicalrotation.mapper.TeacherProfileMapper;
import com.clinicalrotation.mapper.RotationPlanMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final DepartmentProfileMapper departmentProfileMapper;
    private final StudentProfileMapper studentProfileMapper;
    private final TeacherProfileMapper teacherProfileMapper;
    private final RotationPlanMapper rotationPlanMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("departmentCount", departmentProfileMapper.countAll());
        data.put("studentCount", studentProfileMapper.countAll());
        data.put("teacherCount", teacherProfileMapper.countAll());
        data.put("rotationCount", rotationPlanMapper.countAll());
        data.put("trend", Arrays.asList(120, 146, 163, 188, 201, 234, 260));
        data.put("pie", Arrays.asList(map("轮转中", 32), map("病例学习", 58), map("待考核", 25), map("已出科", 8)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
