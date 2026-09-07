package com.communityparty.service;

import com.communityparty.mapper.PartyBranchMapper;
import com.communityparty.mapper.MemberProfileMapper;
import com.communityparty.mapper.PartyActivityMapper;
import com.communityparty.mapper.ActivityRegistrationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final PartyBranchMapper partyBranchMapper;
    private final MemberProfileMapper memberProfileMapper;
    private final PartyActivityMapper partyActivityMapper;
    private final ActivityRegistrationMapper activityRegistrationMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("branchCount", partyBranchMapper.countAll());
        data.put("memberCount", memberProfileMapper.countAll());
        data.put("activityCount", partyActivityMapper.countAll());
        data.put("registrationCount", activityRegistrationMapper.countAll());
        data.put("trend", Arrays.asList(120, 146, 163, 188, 201, 234, 260));
        data.put("pie", Arrays.asList(map("报名待审核", 32), map("活动进行中", 58), map("积分已发放", 25), map("榜单已发布", 8)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
