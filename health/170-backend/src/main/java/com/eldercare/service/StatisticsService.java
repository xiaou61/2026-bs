package com.eldercare.service;

import com.eldercare.mapper.CareAreaMapper;
import com.eldercare.mapper.RoomProfileMapper;
import com.eldercare.mapper.BedProfileMapper;
import com.eldercare.mapper.ElderProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final CareAreaMapper careAreaMapper;
    private final RoomProfileMapper roomProfileMapper;
    private final BedProfileMapper bedProfileMapper;
    private final ElderProfileMapper elderProfileMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("areaCount", careAreaMapper.countAll());
        data.put("roomCount", roomProfileMapper.countAll());
        data.put("bedCount", bedProfileMapper.countAll());
        data.put("elderCount", elderProfileMapper.countAll());
        data.put("trend", Arrays.asList(118, 142, 170, 201, 236, 258, 291));
        data.put("pie", Arrays.asList(map("待分配", 24), map("已入住", 58), map("护理中", 42), map("异常待处置", 13)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
