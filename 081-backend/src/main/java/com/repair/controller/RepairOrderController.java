package com.repair.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.repair.common.BusinessException;
import com.repair.common.Result;
import com.repair.entity.RepairOrder;
import com.repair.entity.Technician;
import com.repair.service.RepairOrderService;
import com.repair.service.TechnicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/order")
public class RepairOrderController {

    @Autowired
    private RepairOrderService repairOrderService;

    @Autowired
    private TechnicianService technicianService;

    @GetMapping("/list")
    public Result<Page<RepairOrder>> getList(@RequestParam(defaultValue = "1") int pageNum,
                                             @RequestParam(defaultValue = "10") int pageSize,
                                             @RequestParam(required = false) String orderNo,
                                             @RequestParam(required = false) Integer status,
                                             @RequestParam(required = false) Long technicianId,
                                             @RequestParam(required = false) String contactPhone,
                                             @RequestParam(required = false) Long userId) {
        return Result.success(repairOrderService.getList(pageNum, pageSize, orderNo, status, technicianId, contactPhone, userId));
    }

    @GetMapping("/detail/{id}")
    public Result<RepairOrder> getDetail(@PathVariable Long id) {
        return Result.success(repairOrderService.getDetail(id));
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody RepairOrder order) {
        repairOrderService.add(order);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody RepairOrder order) {
        repairOrderService.update(order);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id) {
        repairOrderService.delete(id);
        return Result.success();
    }

    @PutMapping("/assign")
    public Result<String> assign(@RequestBody Map<String, Long> params,
                                 @RequestAttribute("userId") String userId) {
        repairOrderService.assign(params.get("orderId"), params.get("technicianId"), Long.parseLong(userId));
        return Result.success();
    }

    @PutMapping("/status")
    public Result<String> updateStatus(@RequestBody Map<String, Object> params,
                                       @RequestAttribute("userId") String userId) {
        Long orderId = Long.parseLong(params.get("orderId").toString());
        Integer status = Integer.parseInt(params.get("status").toString());
        String content = params.get("content") == null ? null : params.get("content").toString();
        String operatorRole = params.get("operatorRole") == null ? "admin" : params.get("operatorRole").toString();
        repairOrderService.updateStatus(orderId, status, content, Long.parseLong(userId), operatorRole);
        return Result.success();
    }

    @GetMapping("/user/list")
    public Result<Page<RepairOrder>> getUserList(@RequestParam(defaultValue = "1") int pageNum,
                                                  @RequestParam(defaultValue = "10") int pageSize,
                                                  @RequestParam(required = false) Integer status,
                                                  @RequestAttribute("userId") String userId) {
        return Result.success(repairOrderService.getUserOrders(pageNum, pageSize, Long.parseLong(userId), status));
    }

    @GetMapping("/user/detail/{id}")
    public Result<RepairOrder> getUserDetail(@PathVariable Long id,
                                             @RequestAttribute("userId") String userId) {
        RepairOrder detail = repairOrderService.getDetail(id);
        if (detail != null && detail.getUserId() != null && detail.getUserId().equals(Long.parseLong(userId))) {
            return Result.success(detail);
        }
        return Result.error("工单不存在");
    }

    @PostMapping("/user/create")
    public Result<Map<String, Object>> userCreate(@RequestBody RepairOrder order,
                                                   @RequestAttribute("userId") String userId) {
        repairOrderService.createByUser(order, Long.parseLong(userId));
        Map<String, Object> result = new HashMap<>();
        result.put("orderNo", order.getOrderNo());
        result.put("orderId", order.getId());
        return Result.success(result);
    }

    @PutMapping("/user/cancel/{id}")
    public Result<String> userCancel(@PathVariable Long id,
                                     @RequestAttribute("userId") String userId) {
        repairOrderService.cancelByUser(id, Long.parseLong(userId));
        return Result.success();
    }

    @PutMapping("/user/pay/{id}")
    public Result<String> userPay(@PathVariable Long id,
                                  @RequestBody(required = false) Map<String, Object> params,
                                  @RequestAttribute("userId") String userId) {
        String content = null;
        if (params != null && params.get("content") != null) {
            content = params.get("content").toString();
        }
        repairOrderService.payByUser(id, Long.parseLong(userId), content);
        return Result.success();
    }

    @GetMapping("/technician/list")
    public Result<Page<RepairOrder>> getTechnicianList(@RequestParam(defaultValue = "1") int pageNum,
                                                        @RequestParam(defaultValue = "10") int pageSize,
                                                        @RequestParam(required = false) Integer status,
                                                        @RequestAttribute("userId") String userId) {
        Long uid = Long.parseLong(userId);
        Technician technician = technicianService.getByUserId(uid);
        if (technician == null) {
            throw new BusinessException("当前账号不是技师账号");
        }
        return Result.success(repairOrderService.getTechnicianOrders(pageNum, pageSize, technician.getId(), status));
    }

    @PutMapping("/technician/status")
    public Result<String> technicianUpdateStatus(@RequestBody Map<String, Object> params,
                                                 @RequestAttribute("userId") String userId) {
        Long uid = Long.parseLong(userId);
        Technician technician = technicianService.getByUserId(uid);
        if (technician == null) {
            throw new BusinessException("当前账号不是技师账号");
        }
        Long orderId = Long.parseLong(params.get("orderId").toString());
        Integer status = Integer.parseInt(params.get("status").toString());
        String content = params.get("content") == null ? null : params.get("content").toString();
        repairOrderService.technicianUpdateStatus(orderId, technician.getId(), uid, status, content);
        return Result.success();
    }
}
