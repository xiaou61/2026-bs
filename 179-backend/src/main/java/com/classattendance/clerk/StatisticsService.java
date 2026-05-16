package com.classattendance.clerk;

import com.classattendance.mapper.TeachingClassMapper;
import com.classattendance.mapper.StudentProfileMapper;
import com.classattendance.mapper.TeacherProfileMapper;
import com.classattendance.mapper.CourseScheduleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final TeachingClassMapper teachingClassMapper;
    private final StudentProfileMapper studentProfileMapper;
    private final TeacherProfileMapper teacherProfileMapper;
    private final CourseScheduleMapper courseScheduleMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("teachingCount", teachingClassMapper.countAll());
        data.put("studentCount", studentProfileMapper.countAll());
        data.put("teacherCount", teacherProfileMapper.countAll());
        data.put("scheduleCount", courseScheduleMapper.countAll());
        data.put("trend", Arrays.asList(58, 74, 88, 105, 121, 137, 154));
        data.put("pie", Arrays.asList(map("待考勤", 31), map("已签到", 44), map("申诉中", 19), map("已整改", 68)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
