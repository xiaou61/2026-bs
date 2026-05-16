package com.markettrace.clerk;

import com.markettrace.mapper.MarketAreaMapper;
import com.markettrace.mapper.StallProfileMapper;
import com.markettrace.mapper.VendorProfileMapper;
import com.markettrace.mapper.ProductTraceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final MarketAreaMapper marketAreaMapper;
    private final StallProfileMapper stallProfileMapper;
    private final VendorProfileMapper vendorProfileMapper;
    private final ProductTraceMapper productTraceMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("areaCount", marketAreaMapper.countAll());
        data.put("stallCount", stallProfileMapper.countAll());
        data.put("vendorCount", vendorProfileMapper.countAll());
        data.put("productCount", productTraceMapper.countAll());
        data.put("trend", Arrays.asList(88, 112, 136, 166, 185, 214, 241));
        data.put("pie", Arrays.asList(map("待巡检", 31), map("整改中", 46), map("已抽检", 29), map("已归档", 16)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
