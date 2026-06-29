package com.powerbank.clerk;

import com.powerbank.mapper.AbnormalRecycleMapper;
import com.powerbank.mapper.CabinetProfileMapper;
import com.powerbank.mapper.FaultRepairMapper;
import com.powerbank.mapper.InspectionTaskMapper;
import com.powerbank.mapper.InventoryTransferMapper;
import com.powerbank.mapper.LeaseOrderMapper;
import com.powerbank.mapper.MerchantIncomeMapper;
import com.powerbank.mapper.OperationLogMapper;
import com.powerbank.mapper.PlacementPlanMapper;
import com.powerbank.mapper.PlacementSiteMapper;
import com.powerbank.mapper.PowerBankDeviceMapper;
import com.powerbank.mapper.SettlementRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final PlacementSiteMapper placementSiteMapper;
    private final CabinetProfileMapper cabinetProfileMapper;
    private final PowerBankDeviceMapper powerBankDeviceMapper;
    private final PlacementPlanMapper placementPlanMapper;
    private final InspectionTaskMapper inspectionTaskMapper;
    private final FaultRepairMapper faultRepairMapper;
    private final AbnormalRecycleMapper abnormalRecycleMapper;
    private final InventoryTransferMapper inventoryTransferMapper;
    private final LeaseOrderMapper leaseOrderMapper;
    private final MerchantIncomeMapper merchantIncomeMapper;
    private final SettlementRecordMapper settlementRecordMapper;
    private final OperationLogMapper operationLogMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();

        long siteCount = placementSiteMapper.countAll();
        long cabinetCount = cabinetProfileMapper.countAll();
        long deviceCount = powerBankDeviceMapper.countAll();
        long planCount = placementPlanMapper.countAll();
        long inspectionCount = inspectionTaskMapper.countAll();
        long repairCount = faultRepairMapper.countAll();
        long recycleCount = abnormalRecycleMapper.countAll();
        long transferCount = inventoryTransferMapper.countAll();
        long orderCount = leaseOrderMapper.countAll();
        long incomeCount = merchantIncomeMapper.countAll();
        long settlementCount = settlementRecordMapper.countAll();
        long logCount = operationLogMapper.countAll();

        long totalCount = siteCount + cabinetCount + deviceCount + planCount + inspectionCount
                + repairCount + recycleCount + transferCount + orderCount + incomeCount + settlementCount;

        data.put("siteCount", siteCount);
        data.put("cabinetCount", cabinetCount);
        data.put("deviceCount", deviceCount);
        data.put("planCount", planCount);
        data.put("inspectionCount", inspectionCount);
        data.put("repairCount", repairCount);
        data.put("recycleCount", recycleCount);
        data.put("transferCount", transferCount);
        data.put("orderCount", orderCount);
        data.put("incomeCount", incomeCount);
        data.put("settlementCount", settlementCount);
        data.put("logCount", logCount);
        data.put("totalCount", totalCount);

        data.put("trend", Arrays.asList(
                map("orders", 31, "repairs", 5),
                map("orders", 46, "repairs", 8),
                map("orders", 58, "repairs", 6),
                map("orders", 72, "repairs", 10),
                map("orders", 86, "repairs", 7),
                map("orders", 101, "repairs", 9),
                map("orders", 119, "repairs", 11)
        ));

        data.put("pie", Arrays.asList(
                map("name", "已投放", "value", 36),
                map("name", "规划中", "value", 24),
                map("name", "租借中", "value", 32),
                map("name", "已结算", "value", 28),
                map("name", "异常处理", "value", 12)
        ));

        data.put("categoryPie", Arrays.asList(
                map("name", "商业区", "value", 45),
                map("name", "交通枢纽", "value", 30),
                map("name", "社区", "value", 25),
                map("name", "景区", "value", 20),
                map("name", "其他", "value", 10)
        ));

        return data;
    }

    private Map<String, Object> map(String key1, Object val1, String key2, Object val2) {
        Map<String, Object> item = new HashMap<>();
        item.put(key1, val1);
        item.put(key2, val2);
        return item;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
