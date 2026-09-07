package com.childcare.clerk;

import com.childcare.mapper.CareClassMapper;
import com.childcare.mapper.ChildProfileMapper;
import com.childcare.mapper.GuardianProfileMapper;
import com.childcare.mapper.AttendanceCheckinMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final CareClassMapper careClassMapper;
    private final ChildProfileMapper childProfileMapper;
    private final GuardianProfileMapper guardianProfileMapper;
    private final AttendanceCheckinMapper attendanceCheckinMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("classroomCount", careClassMapper.countAll());
        data.put("childCount", childProfileMapper.countAll());
        data.put("guardianCount", guardianProfileMapper.countAll());
        data.put("checkinCount", attendanceCheckinMapper.countAll());
        data.put("trend", Arrays.asList(58, 74, 88, 105, 121, 137, 154));
        data.put("pie", Arrays.asList(map("已登记", 31), map("已签到", 44), map("已接走", 19), map("已关闭", 68)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
