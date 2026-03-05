package com.repair.controller;

import com.repair.common.Result;
import com.repair.entity.RepairProcess;
import com.repair.entity.Technician;
import com.repair.service.RepairOrderService;
import com.repair.service.RepairProcessService;
import com.repair.service.TechnicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/process")
public class RepairProcessController {

    @Autowired
    private RepairProcessService repairProcessService;

    @Autowired
    private RepairOrderService repairOrderService;

    @Autowired
    private TechnicianService technicianService;

    @GetMapping("/list/{orderId}")
    public Result<List<RepairProcess>> getList(@PathVariable Long orderId) {
        return Result.success(repairProcessService.getByOrderId(orderId));
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody RepairProcess process) {
        repairProcessService.add(process);
        return Result.success();
    }

    @PostMapping("/technician/add")
    public Result<String> technicianAdd(@RequestBody Map<String, Object> params,
                                        @RequestAttribute("userId") String userId) {
        Long uid = Long.parseLong(userId);
        Technician technician = technicianService.getByUserId(uid);
        if (technician == null) {
            return Result.error("当前账号不是技师账号");
        }
        Long orderId = Long.parseLong(params.get("orderId").toString());
        String content = params.get("content") == null ? null : params.get("content").toString();
        String images = params.get("images") == null ? null : params.get("images").toString();
        repairOrderService.technicianAddProcess(orderId, technician.getId(), uid, content, images);
        return Result.success();
    }
}
