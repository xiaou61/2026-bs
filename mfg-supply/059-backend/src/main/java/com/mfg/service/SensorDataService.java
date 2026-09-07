package com.mfg.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mfg.entity.Equipment;
import com.mfg.entity.SensorData;
import com.mfg.mapper.EquipmentMapper;
import com.mfg.mapper.SensorDataMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SensorDataService {

    @Resource
    private SensorDataMapper sensorDataMapper;

    @Resource
    private EquipmentMapper equipmentMapper;

    public Page<SensorData> page(Integer pageNum, Integer pageSize, Long equipmentId) {
        QueryWrapper<SensorData> wrapper = new QueryWrapper<>();
        if (equipmentId != null) {
            wrapper.eq("equipment_id", equipmentId);
        }
        wrapper.orderByDesc("collect_time");
        Page<SensorData> page = sensorDataMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        page.getRecords().forEach(s -> {
            Equipment eq = equipmentMapper.selectById(s.getEquipmentId());
            if (eq != null) {
                s.setEquipmentName(eq.getName());
            }
        });
        return page;
    }

    public void add(SensorData sensorData) {
        sensorDataMapper.insert(sensorData);
    }

    public SensorData getLatest(Long equipmentId) {
        QueryWrapper<SensorData> wrapper = new QueryWrapper<>();
        wrapper.eq("equipment_id", equipmentId).orderByDesc("collect_time").last("LIMIT 1");
        return sensorDataMapper.selectOne(wrapper);
    }

    public List<SensorData> getTrend(Long equipmentId, Integer limit) {
        QueryWrapper<SensorData> wrapper = new QueryWrapper<>();
        wrapper.eq("equipment_id", equipmentId).orderByDesc("collect_time").last("LIMIT " + (limit != null ? limit : 20));
        List<SensorData> list = sensorDataMapper.selectList(wrapper);
        java.util.Collections.reverse(list);
        return list;
    }
}
