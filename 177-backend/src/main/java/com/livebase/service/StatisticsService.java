package com.livebase.service;

import com.livebase.mapper.LiveStudioMapper;
import com.livebase.mapper.AnchorProfileMapper;
import com.livebase.mapper.MerchantProfileMapper;
import com.livebase.mapper.ProductCatalogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final LiveStudioMapper liveStudioMapper;
    private final AnchorProfileMapper anchorProfileMapper;
    private final MerchantProfileMapper merchantProfileMapper;
    private final ProductCatalogMapper productCatalogMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("studioCount", liveStudioMapper.countAll());
        data.put("anchorCount", anchorProfileMapper.countAll());
        data.put("merchantCount", merchantProfileMapper.countAll());
        data.put("productCount", productCatalogMapper.countAll());
        data.put("trend", Arrays.asList(58, 74, 88, 105, 121, 137, 154));
        data.put("pie", Arrays.asList(map("待排班", 31), map("选品中", 44), map("样品流转", 19), map("已复盘", 68)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
