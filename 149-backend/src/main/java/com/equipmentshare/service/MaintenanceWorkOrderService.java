package com.equipmentshare.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.equipmentshare.entity.MaintenanceWorkOrder;
import com.equipmentshare.mapper.MaintenanceWorkOrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MaintenanceWorkOrderService extends BaseCrudService<MaintenanceWorkOrder> {
    private final MaintenanceWorkOrderMapper outboundRecordMapper;

    @Override
    protected BaseMapper<MaintenanceWorkOrder> mapper() {
        return outboundRecordMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"outbound_no", "consumable_name", "lab_name", "outbound_qty"};
    }
}








