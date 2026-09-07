package com.hazardwork.service;

import com.hazardwork.mapper.WorkAreaMapper;
import com.hazardwork.mapper.HazardSourceMapper;
import com.hazardwork.mapper.WorkerProfileMapper;
import com.hazardwork.mapper.WorkPermitMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final WorkAreaMapper workAreaMapper;
    private final HazardSourceMapper hazardSourceMapper;
    private final WorkerProfileMapper workerProfileMapper;
    private final WorkPermitMapper workPermitMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("areaCount", workAreaMapper.countAll());
        data.put("hazardCount", hazardSourceMapper.countAll());
        data.put("workerCount", workerProfileMapper.countAll());
        data.put("work-ticketCount", workPermitMapper.countAll());
        data.put("trend", Arrays.asList(120, 146, 163, 188, 201, 234, 260));
        data.put("pie", Arrays.asList(map("作业待审批", 32), map("现场监护中", 58), map("隐患整改中", 25), map("已闭环", 8)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
