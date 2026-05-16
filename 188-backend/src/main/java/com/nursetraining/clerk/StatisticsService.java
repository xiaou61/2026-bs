package com.nursetraining.clerk;

import com.nursetraining.mapper.TrainingProgramMapper;
import com.nursetraining.mapper.NurseProfileMapper;
import com.nursetraining.mapper.TrainingClassMapper;
import com.nursetraining.mapper.TrainingCheckinMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final TrainingProgramMapper trainingProgramMapper;
    private final NurseProfileMapper nurseProfileMapper;
    private final TrainingClassMapper trainingClassMapper;
    private final TrainingCheckinMapper trainingCheckinMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("programCount", trainingProgramMapper.countAll());
        data.put("nurseCount", nurseProfileMapper.countAll());
        data.put("classCount", trainingClassMapper.countAll());
        data.put("checkinCount", trainingCheckinMapper.countAll());
        data.put("trend", Arrays.asList(58, 74, 88, 105, 121, 137, 154));
        data.put("pie", Arrays.asList(map("已报名", 31), map("已签到", 44), map("待考核", 19), map("已闭环", 68)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
