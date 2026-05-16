package com.medicalwaste.service;

import com.medicalwaste.mapper.WasteSourceMapper;
import com.medicalwaste.mapper.WasteCategoryMapper;
import com.medicalwaste.mapper.WastePackageMapper;
import com.medicalwaste.mapper.CollectionOrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final WasteSourceMapper wasteSourceMapper;
    private final WasteCategoryMapper wasteCategoryMapper;
    private final WastePackageMapper wastePackageMapper;
    private final CollectionOrderMapper collectionOrderMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("wasteCount", wasteSourceMapper.countAll());
        data.put("categoryCount", wasteCategoryMapper.countAll());
        data.put("packageCount", wastePackageMapper.countAll());
        data.put("orderCount", collectionOrderMapper.countAll());
        data.put("trend", Arrays.asList(120, 146, 163, 188, 201, 234, 260));
        data.put("pie", Arrays.asList(map("待收集", 32), map("转运中", 58), map("待处置", 25), map("闭环完成", 8)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
