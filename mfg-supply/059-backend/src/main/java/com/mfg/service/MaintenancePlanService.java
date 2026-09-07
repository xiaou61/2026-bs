package com.mfg.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mfg.entity.Equipment;
import com.mfg.entity.MaintenancePlan;
import com.mfg.mapper.EquipmentMapper;
import com.mfg.mapper.MaintenancePlanMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MaintenancePlanService {

    @Resource
    private MaintenancePlanMapper maintenancePlanMapper;

    @Resource
    private EquipmentMapper equipmentMapper;

    public Page<MaintenancePlan> page(Integer pageNum, Integer pageSize, Long equipmentId, String status) {
        QueryWrapper<MaintenancePlan> wrapper = new QueryWrapper<>();
        if (equipmentId != null) {
            wrapper.eq("equipment_id", equipmentId);
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        wrapper.orderByDesc("create_time");
        Page<MaintenancePlan> page = maintenancePlanMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        page.getRecords().forEach(p -> {
            Equipment eq = equipmentMapper.selectById(p.getEquipmentId());
            if (eq != null) {
                p.setEquipmentName(eq.getName());
            }
        });
        return page;
    }

    public void add(MaintenancePlan plan) {
        maintenancePlanMapper.insert(plan);
    }

    public void update(MaintenancePlan plan) {
        maintenancePlanMapper.updateById(plan);
    }

    public void delete(Long id) {
        maintenancePlanMapper.deleteById(id);
    }
}
