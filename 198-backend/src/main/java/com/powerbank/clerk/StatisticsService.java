package com.powerbank.clerk;

import com.powerbank.mapper.PlacementSiteMapper;
import com.powerbank.mapper.CabinetProfileMapper;
import com.powerbank.mapper.PowerBankDeviceMapper;
import com.powerbank.mapper.PlacementPlanMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final PlacementSiteMapper placementSiteMapper;
    private final CabinetProfileMapper cabinetProfileMapper;
    private final PowerBankDeviceMapper powerBankDeviceMapper;
    private final PlacementPlanMapper placementPlanMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("siteCount", placementSiteMapper.countAll());
        data.put("cabinetCount", cabinetProfileMapper.countAll());
        data.put("deviceCount", powerBankDeviceMapper.countAll());
        data.put("planCount", placementPlanMapper.countAll());
        data.put("trend", Arrays.asList(31, 46, 58, 72, 86, 101, 119));
        data.put("pie", Arrays.asList(map("已投放", 36), map("规划中", 24), map("租借中", 32), map("已结算", 28), map("异常处理", 12)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
