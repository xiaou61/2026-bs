package com.mfg.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mfg.entity.AlertRecord;
import com.mfg.entity.Equipment;
import com.mfg.mapper.AlertRecordMapper;
import com.mfg.mapper.EquipmentMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class AlertRecordService {

    @Resource
    private AlertRecordMapper alertRecordMapper;

    @Resource
    private EquipmentMapper equipmentMapper;

    public Page<AlertRecord> page(Integer pageNum, Integer pageSize, Long equipmentId, String alertLevel, Integer status) {
        QueryWrapper<AlertRecord> wrapper = new QueryWrapper<>();
        if (equipmentId != null) {
            wrapper.eq("equipment_id", equipmentId);
        }
        if (StrUtil.isNotBlank(alertLevel)) {
            wrapper.eq("alert_level", alertLevel);
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        wrapper.orderByDesc("create_time");
        Page<AlertRecord> page = alertRecordMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        page.getRecords().forEach(a -> {
            Equipment eq = equipmentMapper.selectById(a.getEquipmentId());
            if (eq != null) {
                a.setEquipmentName(eq.getName());
            }
        });
        return page;
    }

    public void handle(Long id) {
        AlertRecord record = new AlertRecord();
        record.setId(id);
        record.setStatus(1);
        record.setHandleTime(LocalDateTime.now());
        alertRecordMapper.updateById(record);
    }

    public Map<String, Object> stats() {
        Map<String, Object> result = new HashMap<>();
        result.put("total", alertRecordMapper.selectCount(null));
        result.put("unhandled", alertRecordMapper.selectCount(new QueryWrapper<AlertRecord>().eq("status", 0)));
        result.put("urgent", alertRecordMapper.selectCount(new QueryWrapper<AlertRecord>().eq("alert_level", "urgent").eq("status", 0)));
        return result;
    }
}
