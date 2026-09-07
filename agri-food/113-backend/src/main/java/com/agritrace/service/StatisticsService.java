package com.agritrace.service;

import com.agritrace.mapper.ProductBatchMapper;
import com.agritrace.mapper.QualityInspectionMapper;
import com.agritrace.mapper.ChainBlockMapper;
import com.agritrace.mapper.TraceNodeMapper;
import com.agritrace.mapper.LogisticsRecordMapper;
import com.agritrace.mapper.RecallEventMapper;
import com.agritrace.mapper.RegulationCheckMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final ProductBatchMapper batchMapper;
    private final QualityInspectionMapper inspectionMapper;
    private final ChainBlockMapper blockMapper;
    private final TraceNodeMapper nodeMapper;
    private final LogisticsRecordMapper logisticsMapper;
    private final RecallEventMapper recallMapper;
    private final RegulationCheckMapper regulationMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("batchCount", batchMapper.selectCount(null));
        data.put("inspectionCount", inspectionMapper.selectCount(null));
        data.put("blockCount", blockMapper.selectCount(null));
        data.put("nodeCount", nodeMapper.selectCount(null));
        data.put("logisticsCount", logisticsMapper.selectCount(null));
        data.put("recallCount", recallMapper.selectCount(null));
        data.put("regulationCount", regulationMapper.selectCount(null));
        data.put("chainTrend", Arrays.asList(6, 9, 12, 13, 18, 21, 24));
        data.put("riskPie", Arrays.asList(map("低风险", 48), map("中风险", 30), map("高风险", 17), map("严重风险", 5)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
