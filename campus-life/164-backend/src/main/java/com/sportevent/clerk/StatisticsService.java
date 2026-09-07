package com.sportevent.clerk;

import com.sportevent.mapper.SportEventMapper;
import com.sportevent.mapper.TeamProfileMapper;
import com.sportevent.mapper.AthleteProfileMapper;
import com.sportevent.mapper.EventRegistrationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final SportEventMapper sportEventMapper;
    private final TeamProfileMapper teamProfileMapper;
    private final AthleteProfileMapper athleteProfileMapper;
    private final EventRegistrationMapper eventRegistrationMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("eventCount", sportEventMapper.countAll());
        data.put("teamCount", teamProfileMapper.countAll());
        data.put("athleteCount", athleteProfileMapper.countAll());
        data.put("registrationCount", eventRegistrationMapper.countAll());
        data.put("trend", Arrays.asList(124, 148, 164, 192, 216, 238, 268));
        data.put("pie", Arrays.asList(map("报名中", 36), map("已编排", 54), map("评分中", 28), map("已公示", 12)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
