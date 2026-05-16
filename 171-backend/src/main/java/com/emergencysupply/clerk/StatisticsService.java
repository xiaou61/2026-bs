package com.emergencysupply.clerk;

import com.emergencysupply.mapper.ReserveWarehouseMapper;
import com.emergencysupply.mapper.MaterialCategoryMapper;
import com.emergencysupply.mapper.MaterialLedgerMapper;
import com.emergencysupply.mapper.StockBatchMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final ReserveWarehouseMapper reserveWarehouseMapper;
    private final MaterialCategoryMapper materialCategoryMapper;
    private final MaterialLedgerMapper materialLedgerMapper;
    private final StockBatchMapper stockBatchMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("warehouseCount", reserveWarehouseMapper.countAll());
        data.put("categoryCount", materialCategoryMapper.countAll());
        data.put("materialCount", materialLedgerMapper.countAll());
        data.put("batchCount", stockBatchMapper.countAll());
        data.put("trend", Arrays.asList(86, 104, 121, 137, 152, 168, 184));
        data.put("pie", Arrays.asList(map("待盘点", 18), map("待审批", 26), map("调拨中", 34), map("库存预警", 11)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
