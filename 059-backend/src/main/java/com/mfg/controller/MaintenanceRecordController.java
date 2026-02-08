package com.mfg.controller;

import com.mfg.common.Result;
import com.mfg.entity.MaintenanceRecord;
import com.mfg.service.MaintenanceRecordService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/api/maintenanceRecord")
public class MaintenanceRecordController {

    @Resource
    private MaintenanceRecordService maintenanceRecordService;

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) Long equipmentId,
                          @RequestParam(required = false) String status) {
        return Result.success(maintenanceRecordService.page(pageNum, pageSize, equipmentId, status));
    }

    @PostMapping
    public Result<?> add(@RequestBody MaintenanceRecord record) {
        maintenanceRecordService.add(record);
        return Result.success();
    }

    @PutMapping("/status")
    public Result<?> updateStatus(@RequestBody Map<String, Object> params) {
        Long id = Long.valueOf(params.get("id").toString());
        String status = params.get("status").toString();
        maintenanceRecordService.updateStatus(id, status);
        return Result.success();
    }
}
