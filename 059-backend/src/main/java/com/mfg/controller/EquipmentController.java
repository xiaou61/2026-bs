package com.mfg.controller;

import com.mfg.common.Result;
import com.mfg.entity.Equipment;
import com.mfg.service.EquipmentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/api/equipment")
public class EquipmentController {

    @Resource
    private EquipmentService equipmentService;

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String name,
                          @RequestParam(required = false) String status,
                          @RequestParam(required = false) Long categoryId) {
        return Result.success(equipmentService.page(pageNum, pageSize, name, status, categoryId));
    }

    @PostMapping
    public Result<?> add(@RequestBody Equipment equipment) {
        equipmentService.add(equipment);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Equipment equipment) {
        equipmentService.update(equipment);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        equipmentService.delete(id);
        return Result.success();
    }

    @PutMapping("/status")
    public Result<?> updateStatus(@RequestBody Map<String, Object> params) {
        Long id = Long.valueOf(params.get("id").toString());
        String status = params.get("status").toString();
        equipmentService.updateStatus(id, status);
        return Result.success();
    }
}
