package com.wastesorting.clerk;

import com.wastesorting.mapper.CommunityAreaMapper;
import com.wastesorting.mapper.BuildingProfileMapper;
import com.wastesorting.mapper.ResidentProfileMapper;
import com.wastesorting.mapper.SortingCategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final CommunityAreaMapper communityAreaMapper;
    private final BuildingProfileMapper buildingProfileMapper;
    private final ResidentProfileMapper residentProfileMapper;
    private final SortingCategoryMapper sortingCategoryMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("areaCount", communityAreaMapper.countAll());
        data.put("buildingCount", buildingProfileMapper.countAll());
        data.put("residentCount", residentProfileMapper.countAll());
        data.put("categoryCount", sortingCategoryMapper.countAll());
        data.put("trend", Arrays.asList(102, 128, 167, 196, 224, 248, 286));
        data.put("pie", Arrays.asList(map("已打卡", 68), map("督导中", 34), map("待整改", 18), map("已兑换", 27)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
