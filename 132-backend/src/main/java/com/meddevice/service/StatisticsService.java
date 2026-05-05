package com.meddevice.service;

import com.meddevice.mapper.DeviceProfileMapper;
import com.meddevice.mapper.DeviceCategoryMapper;
import com.meddevice.mapper.DepartmentInfoMapper;
import com.meddevice.mapper.BorrowRequestMapper;
import com.meddevice.mapper.BorrowRecordMapper;
import com.meddevice.mapper.ReturnRecordMapper;
import com.meddevice.mapper.SterilizationBatchMapper;
import com.meddevice.mapper.SterilizationRecordMapper;
import com.meddevice.mapper.QrTraceMapper;
import com.meddevice.mapper.MaintenanceRecordMapper;
import com.meddevice.mapper.InspectionTaskMapper;
import com.meddevice.mapper.RiskAlertMapper;
import com.meddevice.mapper.OperationLogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final DeviceProfileMapper deviceProfileMapper;
    private final DeviceCategoryMapper deviceCategoryMapper;
    private final DepartmentInfoMapper departmentInfoMapper;
    private final BorrowRequestMapper borrowRequestMapper;
    private final BorrowRecordMapper borrowRecordMapper;
    private final ReturnRecordMapper returnRecordMapper;
    private final SterilizationBatchMapper sterilizationBatchMapper;
    private final SterilizationRecordMapper sterilizationRecordMapper;
    private final QrTraceMapper qrTraceMapper;
    private final MaintenanceRecordMapper maintenanceRecordMapper;
    private final InspectionTaskMapper inspectionTaskMapper;
    private final RiskAlertMapper riskAlertMapper;
    private final OperationLogMapper operationLogMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("deviceCount", deviceProfileMapper.countAll());
        data.put("borrowCount", borrowRecordMapper.countAll());
        data.put("sterilizationCount", sterilizationRecordMapper.countAll());
        data.put("alertCount", riskAlertMapper.countAll());
        data.put("borrowTrend", Arrays.asList(12, 22, 31, 44, 53, 62, 70));
        data.put("devicePie", Arrays.asList(map("高风险", 30), map("中风险", 36), map("低风险", 34)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
