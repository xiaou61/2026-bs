package com.teambuilding.clerk;

import com.teambuilding.mapper.AgencyInfoMapper;
import com.teambuilding.mapper.TeamProfileMapper;
import com.teambuilding.mapper.TripPlanMapper;
import com.teambuilding.mapper.SignupRegistrationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final AgencyInfoMapper agencyInfoMapper;
    private final TeamProfileMapper teamProfileMapper;
    private final TripPlanMapper tripPlanMapper;
    private final SignupRegistrationMapper signupRegistrationMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("agencyCount", agencyInfoMapper.countAll());
        data.put("teamCount", teamProfileMapper.countAll());
        data.put("tripCount", tripPlanMapper.countAll());
        data.put("signupCount", signupRegistrationMapper.countAll());
        data.put("trend", Arrays.asList(58, 74, 88, 105, 121, 137, 154));
        data.put("pie", Arrays.asList(map("已登记", 31), map("已下单", 44), map("备餐中", 19), map("已关闭", 68)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
