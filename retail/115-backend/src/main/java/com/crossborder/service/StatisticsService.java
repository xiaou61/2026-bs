package com.crossborder.service;

import com.crossborder.mapper.ClearanceDeclarationMapper;
import com.crossborder.mapper.CrossBorderOrderMapper;
import com.crossborder.mapper.ExchangeRateMapper;
import com.crossborder.mapper.LogisticsTrackMapper;
import com.crossborder.mapper.ReconciliationTaskMapper;
import com.crossborder.mapper.SettlementBillMapper;
import com.crossborder.mapper.TaxFeeRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final CrossBorderOrderMapper orderMapper;
    private final ClearanceDeclarationMapper declarationMapper;
    private final TaxFeeRecordMapper taxMapper;
    private final ExchangeRateMapper rateMapper;
    private final SettlementBillMapper settlementMapper;
    private final LogisticsTrackMapper logisticsMapper;
    private final ReconciliationTaskMapper reconciliationMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("orderCount", orderMapper.selectCount(null));
        data.put("declarationCount", declarationMapper.selectCount(null));
        data.put("taxCount", taxMapper.selectCount(null));
        data.put("rateCount", rateMapper.selectCount(null));
        data.put("settlementCount", settlementMapper.selectCount(null));
        data.put("logisticsCount", logisticsMapper.selectCount(null));
        data.put("reconciliationCount", reconciliationMapper.selectCount(null));
        data.put("rateTrend", Arrays.asList(7.08, 7.09, 7.11, 7.10, 7.12, 7.13, 7.12));
        data.put("statusPie", Arrays.asList(map("清关中", 34), map("已放行", 28), map("待结算", 22), map("差异处理", 16)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
