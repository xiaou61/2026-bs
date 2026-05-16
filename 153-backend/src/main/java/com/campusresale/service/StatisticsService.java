package com.campusresale.service;

import com.campusresale.mapper.ItemCategoryMapper;
import com.campusresale.mapper.StudentProfileMapper;
import com.campusresale.mapper.ConsignmentItemMapper;
import com.campusresale.mapper.ItemAuditMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final ItemCategoryMapper itemCategoryMapper;
    private final StudentProfileMapper studentProfileMapper;
    private final ConsignmentItemMapper consignmentItemMapper;
    private final ItemAuditMapper itemAuditMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("categoryCount", itemCategoryMapper.countAll());
        data.put("studentCount", studentProfileMapper.countAll());
        data.put("consignmentCount", consignmentItemMapper.countAll());
        data.put("auditCount", itemAuditMapper.countAll());
        data.put("trend", Arrays.asList(120, 146, 163, 188, 201, 234, 260));
        data.put("pie", Arrays.asList(map("待上架审核", 32), map("担保交易中", 58), map("交接确认中", 25), map("纠纷处理中", 8)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
