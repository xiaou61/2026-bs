package com.orsterile.clerk;

import com.orsterile.mapper.OperatingRoomMapper;
import com.orsterile.mapper.InstrumentPackMapper;
import com.orsterile.mapper.InstrumentItemMapper;
import com.orsterile.mapper.PackTraceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final OperatingRoomMapper operatingRoomMapper;
    private final InstrumentPackMapper instrumentPackMapper;
    private final InstrumentItemMapper instrumentItemMapper;
    private final PackTraceMapper packTraceMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("roomCount", operatingRoomMapper.countAll());
        data.put("packCount", instrumentPackMapper.countAll());
        data.put("itemCount", instrumentItemMapper.countAll());
        data.put("traceCount", packTraceMapper.countAll());
        data.put("trend", Arrays.asList(58, 74, 88, 105, 121, 137, 154));
        data.put("pie", Arrays.asList(map("待灭菌", 31), map("灭菌中", 44), map("待放行", 19), map("已放行", 68)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
