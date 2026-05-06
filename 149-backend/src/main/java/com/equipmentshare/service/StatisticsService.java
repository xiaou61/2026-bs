package com.equipmentshare.service;

import com.equipmentshare.mapper.EquipmentAssetMapper;
import com.equipmentshare.mapper.LaboratoryRoomMapper;
import com.equipmentshare.mapper.EquipmentCategoryMapper;
import com.equipmentshare.mapper.BorrowUserMapper;
import com.equipmentshare.mapper.ReservationRequestMapper;
import com.equipmentshare.mapper.BorrowRecordMapper;
import com.equipmentshare.mapper.UsageRegistrationMapper;
import com.equipmentshare.mapper.ViolationRecordMapper;
import com.equipmentshare.mapper.MaintenanceWorkOrderMapper;
import com.equipmentshare.mapper.ReturnConfirmationMapper;
import com.equipmentshare.mapper.InspectionPlanMapper;
import com.equipmentshare.mapper.SystemNoticeMapper;
import com.equipmentshare.mapper.OperationLogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final EquipmentAssetMapper equipmentAssetMapper;
    private final LaboratoryRoomMapper laboratoryRoomMapper;
    private final EquipmentCategoryMapper equipmentCategoryMapper;
    private final BorrowUserMapper borrowUserMapper;
    private final ReservationRequestMapper reservationRequestMapper;
    private final BorrowRecordMapper borrowRecordMapper;
    private final UsageRegistrationMapper usageRegistrationMapper;
    private final ViolationRecordMapper violationRecordMapper;
    private final MaintenanceWorkOrderMapper maintenanceWorkOrderMapper;
    private final ReturnConfirmationMapper returnConfirmationMapper;
    private final InspectionPlanMapper inspectionPlanMapper;
    private final SystemNoticeMapper systemNoticeMapper;
    private final OperationLogMapper operationLogMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("assetCount", equipmentAssetMapper.selectCount(null));
        data.put("reservationCount", reservationRequestMapper.selectCount(null));
        data.put("usageCount", usageRegistrationMapper.selectCount(null));
        data.put("noticeCount", systemNoticeMapper.selectCount(null));
        data.put("equipTrend", Arrays.asList(12, 22, 31, 44, 53, 62, 70));
        data.put("equipPie", Arrays.asList(map("空闲", 35), map("借出中", 31), map("维修中", 34)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}











