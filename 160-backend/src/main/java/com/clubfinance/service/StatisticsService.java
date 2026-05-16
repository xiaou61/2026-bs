package com.clubfinance.service;

import com.clubfinance.mapper.ClubProfileMapper;
import com.clubfinance.mapper.MemberProfileMapper;
import com.clubfinance.mapper.ActivityPlanMapper;
import com.clubfinance.mapper.BudgetRequestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final ClubProfileMapper clubProfileMapper;
    private final MemberProfileMapper memberProfileMapper;
    private final ActivityPlanMapper activityPlanMapper;
    private final BudgetRequestMapper budgetRequestMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("clubCount", clubProfileMapper.countAll());
        data.put("memberCount", memberProfileMapper.countAll());
        data.put("activityCount", activityPlanMapper.countAll());
        data.put("budgetCount", budgetRequestMapper.countAll());
        data.put("trend", Arrays.asList(120, 146, 163, 188, 201, 234, 260));
        data.put("pie", Arrays.asList(map("待审批", 32), map("预算通过", 58), map("报销处理中", 25), map("物资已归还", 8)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
