package com.mfg.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mfg.entity.Equipment;
import com.mfg.entity.MaintenancePlan;
import com.mfg.entity.MaintenanceRecord;
import com.mfg.mapper.EquipmentMapper;
import com.mfg.mapper.MaintenancePlanMapper;
import com.mfg.mapper.MaintenanceRecordMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Service
public class MaintenanceRecordService {

    @Resource
    private MaintenanceRecordMapper maintenanceRecordMapper;

    @Resource
    private EquipmentMapper equipmentMapper;

    @Resource
    private MaintenancePlanMapper maintenancePlanMapper;

    public Page<MaintenanceRecord> page(Integer pageNum, Integer pageSize, Long equipmentId, String status) {
        QueryWrapper<MaintenanceRecord> wrapper = new QueryWrapper<>();
        if (equipmentId != null) {
            wrapper.eq("equipment_id", equipmentId);
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        wrapper.orderByDesc("create_time");
        Page<MaintenanceRecord> page = maintenanceRecordMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        page.getRecords().forEach(r -> {
            Equipment eq = equipmentMapper.selectById(r.getEquipmentId());
            if (eq != null) r.setEquipmentName(eq.getName());
            if (r.getPlanId() != null) {
                MaintenancePlan plan = maintenancePlanMapper.selectById(r.getPlanId());
                if (plan != null) r.setPlanName(plan.getPlanName());
            }
        });
        return page;
    }

    public void add(MaintenanceRecord record) {
        maintenanceRecordMapper.insert(record);
    }

    public void updateStatus(Long id, String status) {
        MaintenanceRecord record = maintenanceRecordMapper.selectById(id);
        if (record != null) {
            record.setStatus(status);
            if ("completed".equals(status)) {
                record.setEndTime(LocalDateTime.now());
            }
            maintenanceRecordMapper.updateById(record);
        }
    }
}
