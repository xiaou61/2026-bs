package com.equipmentshare.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.equipmentshare.entity.MaintenanceWorkOrder;
import com.equipmentshare.mapper.MaintenanceWorkOrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MaintenanceWorkOrderService extends BaseCrudService<MaintenanceWorkOrder> {
    private final MaintenanceWorkOrderMapper maintenanceWorkOrderMapper;

    @Override
    protected BaseMapper<MaintenanceWorkOrder> mapper() {
        return maintenanceWorkOrderMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"work_order_no", "equipment_name", "fault_type", "reporter_name"};
    }
}








