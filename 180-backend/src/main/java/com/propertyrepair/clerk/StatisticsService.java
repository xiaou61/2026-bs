package com.propertyrepair.clerk;

import com.propertyrepair.mapper.CommunityAreaMapper;
import com.propertyrepair.mapper.OwnerProfileMapper;
import com.propertyrepair.mapper.RepairCategoryMapper;
import com.propertyrepair.mapper.RepairOrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final CommunityAreaMapper communityAreaMapper;
    private final OwnerProfileMapper ownerProfileMapper;
    private final RepairCategoryMapper repairCategoryMapper;
    private final RepairOrderMapper repairOrderMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("areaCount", communityAreaMapper.countAll());
        data.put("ownerCount", ownerProfileMapper.countAll());
        data.put("categoryCount", repairCategoryMapper.countAll());
        data.put("repairCount", repairOrderMapper.countAll());
        data.put("trend", Arrays.asList(58, 74, 88, 105, 121, 137, 154));
        data.put("pie", Arrays.asList(map("新建报修", 31), map("已派单", 44), map("上门中", 19), map("已关闭", 68)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
