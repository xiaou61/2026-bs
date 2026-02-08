package com.mfg.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mfg.entity.*;
import com.mfg.mapper.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
public class DashboardService {

    @Resource
    private EquipmentMapper equipmentMapper;

    @Resource
    private ProductionOrderMapper productionOrderMapper;

    @Resource
    private AlertRecordMapper alertRecordMapper;

    @Resource
    private QualityInspectionMapper qualityInspectionMapper;

    @Resource
    private MaterialMapper materialMapper;

    public Map<String, Object> equipmentStatus() {
        Map<String, Object> result = new HashMap<>();
        result.put("running", equipmentMapper.selectCount(new QueryWrapper<Equipment>().eq("status", "running")));
        result.put("stopped", equipmentMapper.selectCount(new QueryWrapper<Equipment>().eq("status", "stopped")));
        result.put("repairing", equipmentMapper.selectCount(new QueryWrapper<Equipment>().eq("status", "repairing")));
        result.put("scrapped", equipmentMapper.selectCount(new QueryWrapper<Equipment>().eq("status", "scrapped")));
        return result;
    }

    public Map<String, Object> orderStats() {
        Map<String, Object> result = new HashMap<>();
        result.put("pending", productionOrderMapper.selectCount(new QueryWrapper<ProductionOrder>().eq("status", "pending")));
        result.put("producing", productionOrderMapper.selectCount(new QueryWrapper<ProductionOrder>().eq("status", "producing")));
        result.put("completed", productionOrderMapper.selectCount(new QueryWrapper<ProductionOrder>().eq("status", "completed")));
        result.put("cancelled", productionOrderMapper.selectCount(new QueryWrapper<ProductionOrder>().eq("status", "cancelled")));
        return result;
    }

    public Map<String, Object> alertStats() {
        Map<String, Object> result = new HashMap<>();
        LocalDateTime todayStart = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        Long todayTotal = alertRecordMapper.selectCount(new QueryWrapper<AlertRecord>().ge("create_time", todayStart));
        Long todayUnhandled = alertRecordMapper.selectCount(new QueryWrapper<AlertRecord>().ge("create_time", todayStart).eq("status", 0));
        result.put("todayTotal", todayTotal);
        result.put("todayUnhandled", todayUnhandled);
        return result;
    }

    public List<Map<String, Object>> qualityTrend() {
        List<QualityInspection> list = qualityInspectionMapper.selectList(
                new QueryWrapper<QualityInspection>().orderByAsc("inspect_time").last("LIMIT 10"));
        List<Map<String, Object>> trend = new ArrayList<>();
        for (QualityInspection q : list) {
            Map<String, Object> item = new HashMap<>();
            item.put("time", q.getInspectTime());
            item.put("inspectQuantity", q.getInspectQuantity());
            item.put("qualifiedQuantity", q.getQualifiedQuantity());
            if (q.getInspectQuantity() != null && q.getInspectQuantity() > 0) {
                item.put("rate", Math.round(q.getQualifiedQuantity() * 100.0 / q.getInspectQuantity()));
            } else {
                item.put("rate", 0);
            }
            trend.add(item);
        }
        return trend;
    }

    public Map<String, Object> overview() {
        Map<String, Object> result = new HashMap<>();
        result.put("equipmentTotal", equipmentMapper.selectCount(null));
        result.put("orderTotal", productionOrderMapper.selectCount(null));
        result.put("materialTotal", materialMapper.selectCount(null));
        Long lowStock = materialMapper.selectCount(
                new QueryWrapper<Material>().apply("stock_quantity <= safe_stock"));
        result.put("lowStockCount", lowStock);
        result.put("alertUnhandled", alertRecordMapper.selectCount(new QueryWrapper<AlertRecord>().eq("status", 0)));
        return result;
    }
}
