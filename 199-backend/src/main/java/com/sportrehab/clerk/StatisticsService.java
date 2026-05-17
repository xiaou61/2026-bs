package com.sportrehab.clerk;

import com.sportrehab.mapper.RehabCenterMapper;
import com.sportrehab.mapper.MemberProfileMapper;
import com.sportrehab.mapper.CoachProfileMapper;
import com.sportrehab.mapper.AssessmentItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final RehabCenterMapper rehabCenterMapper;
    private final MemberProfileMapper memberProfileMapper;
    private final CoachProfileMapper coachProfileMapper;
    private final AssessmentItemMapper assessmentItemMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("centerCount", rehabCenterMapper.countAll());
        data.put("memberCount", memberProfileMapper.countAll());
        data.put("coachCount", coachProfileMapper.countAll());
        data.put("itemCount", assessmentItemMapper.countAll());
        data.put("trend", Arrays.asList(31, 46, 58, 72, 86, 101, 119));
        data.put("pie", Arrays.asList(map("已建档", 36), map("评估中", 24), map("训练中", 32), map("反馈中", 28), map("复评中", 12)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
