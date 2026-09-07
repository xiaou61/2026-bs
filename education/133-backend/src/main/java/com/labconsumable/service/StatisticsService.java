package com.labconsumable.service;

import com.labconsumable.mapper.ConsumableCatalogMapper;
import com.labconsumable.mapper.SupplierProfileMapper;
import com.labconsumable.mapper.LabRoomMapper;
import com.labconsumable.mapper.StockItemMapper;
import com.labconsumable.mapper.PurchaseRequestMapper;
import com.labconsumable.mapper.PurchaseApprovalMapper;
import com.labconsumable.mapper.PurchaseOrderMapper;
import com.labconsumable.mapper.InboundRecordMapper;
import com.labconsumable.mapper.OutboundRecordMapper;
import com.labconsumable.mapper.InventoryCheckMapper;
import com.labconsumable.mapper.WarningRuleMapper;
import com.labconsumable.mapper.StockWarningMapper;
import com.labconsumable.mapper.OperationLogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final ConsumableCatalogMapper consumableCatalogMapper;
    private final SupplierProfileMapper supplierProfileMapper;
    private final LabRoomMapper labRoomMapper;
    private final StockItemMapper stockItemMapper;
    private final PurchaseRequestMapper purchaseRequestMapper;
    private final PurchaseApprovalMapper purchaseApprovalMapper;
    private final PurchaseOrderMapper purchaseOrderMapper;
    private final InboundRecordMapper inboundRecordMapper;
    private final OutboundRecordMapper outboundRecordMapper;
    private final InventoryCheckMapper inventoryCheckMapper;
    private final WarningRuleMapper warningRuleMapper;
    private final StockWarningMapper stockWarningMapper;
    private final OperationLogMapper operationLogMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("catalogCount", consumableCatalogMapper.selectCount(null));
        data.put("stockCount", stockItemMapper.selectCount(null));
        data.put("requestCount", purchaseRequestMapper.selectCount(null));
        data.put("warningCount", stockWarningMapper.selectCount(null));
        data.put("purchaseTrend", Arrays.asList(12, 22, 31, 44, 53, 62, 70));
        data.put("stockPie", Arrays.asList(map("充足", 35), map("临界", 31), map("短缺", 34)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
