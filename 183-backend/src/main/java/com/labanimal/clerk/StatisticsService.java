package com.labanimal.clerk;

import com.labanimal.mapper.AnimalRoomMapper;
import com.labanimal.mapper.AnimalProfileMapper;
import com.labanimal.mapper.FeedingScheduleMapper;
import com.labanimal.mapper.FeedingRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final AnimalRoomMapper animalRoomMapper;
    private final AnimalProfileMapper animalProfileMapper;
    private final FeedingScheduleMapper feedingScheduleMapper;
    private final FeedingRecordMapper feedingRecordMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("roomCount", animalRoomMapper.countAll());
        data.put("animalCount", animalProfileMapper.countAll());
        data.put("scheduleCount", feedingScheduleMapper.countAll());
        data.put("feedingCount", feedingRecordMapper.countAll());
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
