package com.property.controller;

import com.property.common.BusinessException;
import com.property.common.Result;
import com.property.entity.RepairOrder;
import com.property.service.RepairOrderService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/repair")
public class RepairOrderController {

    @Resource
    private RepairOrderService repairOrderService;

    @GetMapping("/my-page")
    public Result<?> myPage(@RequestParam(defaultValue = "1") Integer pageNum,
                            @RequestParam(defaultValue = "10") Integer pageSize,
                            @RequestParam(required = false) Integer status,
                            @RequestParam(required = false) String orderNo,
                            HttpServletRequest request) {
        checkOwner((String) request.getAttribute("role"));
        return Result.success(repairOrderService.myPage((Long) request.getAttribute("userId"), pageNum, pageSize, status, orderNo));
    }

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) Long ownerId,
                          @RequestParam(required = false) Integer status,
                          @RequestParam(required = false) Long assigneeId,
                          @RequestParam(required = false) String orderNo,
                          HttpServletRequest request) {
        checkStaffRole((String) request.getAttribute("role"));
        return Result.success(repairOrderService.page(pageNum, pageSize, ownerId, status, assigneeId, orderNo));
    }

    @PostMapping
    public Result<?> add(@RequestBody RepairOrder order, HttpServletRequest request) {
        checkOwner((String) request.getAttribute("role"));
        repairOrderService.save(order, (Long) request.getAttribute("userId"));
        return Result.success();
    }

    @PutMapping("/status")
    public Result<?> updateStatus(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        checkStaffRole((String) request.getAttribute("role"));
        if (params.get("id") == null || params.get("status") == null) {
            throw new BusinessException("参数不完整");
        }
        Long id = ((Number) params.get("id")).longValue();
        Integer status = ((Number) params.get("status")).intValue();
        Long assigneeId = params.get("assigneeId") == null ? null : ((Number) params.get("assigneeId")).longValue();
        String reply = params.get("reply") == null ? null : params.get("reply").toString();
        repairOrderService.updateStatus(id, status, assigneeId, reply);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        repairOrderService.deleteById(id, (Long) request.getAttribute("userId"), (String) request.getAttribute("role"));
        return Result.success();
    }

    private void checkStaffRole(String role) {
        if (!"ADMIN".equals(role) && !"STAFF".equals(role)) {
            throw new BusinessException("无权限操作");
        }
    }

    private void checkOwner(String role) {
        if (!"OWNER".equals(role)) {
            throw new BusinessException("无权限操作");
        }
    }
}
