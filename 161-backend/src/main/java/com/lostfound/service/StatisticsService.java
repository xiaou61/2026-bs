package com.lostfound.service;

import com.lostfound.mapper.ScenicAreaMapper;
import com.lostfound.mapper.LostItemMapper;
import com.lostfound.mapper.FoundReportMapper;
import com.lostfound.mapper.ClaimApplicationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final ScenicAreaMapper scenicAreaMapper;
    private final LostItemMapper lostItemMapper;
    private final FoundReportMapper foundReportMapper;
    private final ClaimApplicationMapper claimApplicationMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("areaCount", scenicAreaMapper.countAll());
        data.put("lostCount", lostItemMapper.countAll());
        data.put("foundCount", foundReportMapper.countAll());
        data.put("claimCount", claimApplicationMapper.countAll());
        data.put("trend", Arrays.asList(120, 146, 163, 188, 201, 234, 260));
        data.put("pie", Arrays.asList(map("待认领", 32), map("核验中", 58), map("待归还", 25), map("已寻回", 8)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
