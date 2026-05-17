package com.heritageworkshop.clerk;

import com.heritageworkshop.mapper.WorkshopProfileMapper;
import com.heritageworkshop.mapper.InheritorProfileMapper;
import com.heritageworkshop.mapper.CourseCatalogMapper;
import com.heritageworkshop.mapper.CourseScheduleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final WorkshopProfileMapper workshopProfileMapper;
    private final InheritorProfileMapper inheritorProfileMapper;
    private final CourseCatalogMapper courseCatalogMapper;
    private final CourseScheduleMapper courseScheduleMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("workshopCount", workshopProfileMapper.countAll());
        data.put("inheritorCount", inheritorProfileMapper.countAll());
        data.put("courseCount", courseCatalogMapper.countAll());
        data.put("scheduleCount", courseScheduleMapper.countAll());
        data.put("trend", Arrays.asList(31, 46, 58, 72, 86, 101, 119));
        data.put("pie", Arrays.asList(map("已建档", 36), map("已排期", 24), map("预约中", 32), map("展销中", 28), map("结算中", 12)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
