package com.assetrfid.service;

import com.assetrfid.mapper.AssetInfoMapper;
import com.assetrfid.mapper.AssetCategoryMapper;
import com.assetrfid.mapper.RfidTagMapper;
import com.assetrfid.mapper.StorageLocationMapper;
import com.assetrfid.mapper.InventoryTaskMapper;
import com.assetrfid.mapper.InventoryRecordMapper;
import com.assetrfid.mapper.BorrowApplicationMapper;
import com.assetrfid.mapper.ReturnRecordMapper;
import com.assetrfid.mapper.RepairRecordMapper;
import com.assetrfid.mapper.DepreciationRecordMapper;
import com.assetrfid.mapper.DisposalRecordMapper;
import com.assetrfid.mapper.WarningNoticeMapper;
import com.assetrfid.mapper.OperationLogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final AssetInfoMapper assetInfoMapper;
    private final AssetCategoryMapper assetCategoryMapper;
    private final RfidTagMapper rfidTagMapper;
    private final StorageLocationMapper storageLocationMapper;
    private final InventoryTaskMapper inventoryTaskMapper;
    private final InventoryRecordMapper inventoryRecordMapper;
    private final BorrowApplicationMapper borrowApplicationMapper;
    private final ReturnRecordMapper returnRecordMapper;
    private final RepairRecordMapper repairRecordMapper;
    private final DepreciationRecordMapper depreciationRecordMapper;
    private final DisposalRecordMapper disposalRecordMapper;
    private final WarningNoticeMapper warningNoticeMapper;
    private final OperationLogMapper operationLogMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("assetCount", assetInfoMapper.selectCount(null));
        data.put("inventoryCount", inventoryTaskMapper.selectCount(null));
        data.put("borrowCount", borrowApplicationMapper.selectCount(null));
        data.put("warningCount", warningNoticeMapper.selectCount(null));
        data.put("assetTrend", Arrays.asList(12, 22, 31, 44, 53, 62, 70));
        data.put("assetPie", Arrays.asList(map("在库", 35), map("借出", 31), map("维修中", 34)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}







