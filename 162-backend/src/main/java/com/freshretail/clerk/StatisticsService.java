package com.freshretail.clerk;

import com.freshretail.mapper.StoreProfileMapper;
import com.freshretail.mapper.SupplierProfileMapper;
import com.freshretail.mapper.FreshCategoryMapper;
import com.freshretail.mapper.ProductBatchMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final StoreProfileMapper storeProfileMapper;
    private final SupplierProfileMapper supplierProfileMapper;
    private final FreshCategoryMapper freshCategoryMapper;
    private final ProductBatchMapper productBatchMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("storeCount", storeProfileMapper.countAll());
        data.put("supplierCount", supplierProfileMapper.countAll());
        data.put("categoryCount", freshCategoryMapper.countAll());
        data.put("batchCount", productBatchMapper.countAll());
        data.put("trend", Arrays.asList(120, 146, 163, 188, 201, 234, 260));
        data.put("pie", Arrays.asList(map("临期预警", 32), map("促销执行", 58), map("待报损", 25), map("已处置", 8)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
