package com.repair.controller;

import com.repair.common.Result;
import com.repair.common.BusinessException;
import com.repair.entity.RepairProcess;
import com.repair.entity.RepairOrder;
import com.repair.entity.Technician;
import com.repair.service.RepairOrderService;
import com.repair.service.RepairProcessService;
import com.repair.service.TechnicianService;
import com.repair.service.UserService;
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

    @Autowired
    private UserService userService;

    @GetMapping("/list/{orderId}")
    public Result<List<RepairProcess>> getList(@PathVariable Long orderId,
                                               @RequestAttribute("userId") String userId) {
        assertProcessVisible(orderId, Long.parseLong(userId));
        return Result.success(repairProcessService.getByOrderId(orderId));
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody RepairProcess process, @RequestAttribute("userId") String userId) {
        userService.requireAdmin(Long.parseLong(userId));
        repairProcessService.add(process);
        return Result.success();
    }

    @PostMapping("/technician/add")
    public Result<String> technicianAdd(@RequestBody Map<String, Object> params,
                                        @RequestAttribute("userId") String userId) {
        Long uid = Long.parseLong(userId);
        Technician technician = technicianService.getByUserId(uid);
        if (technician == null) {
            throw new BusinessException(403, "当前账号不是技师账号");
        }
        Long orderId = Long.parseLong(params.get("orderId").toString());
        String content = params.get("content") == null ? null : params.get("content").toString();
        String images = params.get("images") == null ? null : params.get("images").toString();
        repairOrderService.technicianAddProcess(orderId, technician.getId(), uid, content, images);
        return Result.success();
    }

    private void assertProcessVisible(Long orderId, Long userId) {
        RepairOrder order = repairOrderService.getDetail(orderId);
        if (order == null) {
            throw new BusinessException(404, "工单不存在");
        }
        if (userService.isAdmin(userId)) {
            return;
        }
        if (order.getUserId() != null && order.getUserId().equals(userId)) {
            return;
        }
        Technician technician = technicianService.getByUserId(userId);
        if (technician != null && order.getTechnicianId() != null && order.getTechnicianId().equals(technician.getId())) {
            return;
        }
        throw new BusinessException(403, "无权查看工单进度");
    }
}
