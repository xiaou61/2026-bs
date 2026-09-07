package com.cloudcost.service;

import com.cloudcost.mapper.AnomalyEventMapper;
import com.cloudcost.mapper.CloudAccountMapper;
import com.cloudcost.mapper.CostBillMapper;
import com.cloudcost.mapper.CostItemMapper;
import com.cloudcost.mapper.IdleResourceMapper;
import com.cloudcost.mapper.OptimizationAdviceMapper;
import com.cloudcost.mapper.SavingPlanMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final CloudAccountMapper accountMapper;
    private final CostBillMapper billMapper;
    private final CostItemMapper itemMapper;
    private final IdleResourceMapper idleMapper;
    private final OptimizationAdviceMapper adviceMapper;
    private final SavingPlanMapper planMapper;
    private final AnomalyEventMapper anomalyMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("accountCount", accountMapper.countAll());
        data.put("billCount", billMapper.countAll());
        data.put("itemCount", itemMapper.countAll());
        data.put("idleCount", idleMapper.countAll());
        data.put("adviceCount", adviceMapper.countAll());
        data.put("planCount", planMapper.countAll());
        data.put("anomalyCount", anomalyMapper.countAll());
        data.put("costTrend", Arrays.asList(7.8, 8.4, 8.1, 9.2, 8.7, 7.9, 7.3));
        data.put("savingPie", Arrays.asList(map("闲置清理", 28), map("规格降配", 36), map("节省计划", 24), map("存储归档", 12)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
