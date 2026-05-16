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
        data.put("laboratoryCount", laboratoryRoomMapper.selectCount(null));
        data.put("reservationCount", reservationRequestMapper.selectCount(null));
        data.put("violationCount", violationRecordMapper.selectCount(null));
        data.put("usageCount", usageRegistrationMapper.selectCount(null));
        data.put("noticeCount", systemNoticeMapper.selectCount(null));
        data.put("reservationTrend", Arrays.asList(8, 12, 16, 19, 22, 18, 25));
        data.put("equipmentStatusPie", Arrays.asList(
                map("可预约", 36),
                map("借用中", 28),
                map("维修中", 12),
                map("待归还", 24)
        ));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}











