package com.inventory.controller;

import com.inventory.common.BusinessException;
import com.inventory.common.Result;
import com.inventory.entity.PurchaseOrder;
import com.inventory.service.PurchaseOrderService;
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

@RestController
@RequestMapping("/api/purchase")
public class PurchaseOrderController {

    @Resource
    private PurchaseOrderService purchaseOrderService;

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String orderNo,
                          @RequestParam(required = false) Long supplierId,
                          @RequestParam(required = false) Integer status,
                          HttpServletRequest request) {
        checkStaffRole((String) request.getAttribute("role"));
        return Result.success(purchaseOrderService.page(pageNum, pageSize, orderNo, supplierId, status));
    }

    @PostMapping
    public Result<?> add(@RequestBody PurchaseOrder order, HttpServletRequest request) {
        checkStaffRole((String) request.getAttribute("role"));
        purchaseOrderService.save(order, (Long) request.getAttribute("userId"));
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody PurchaseOrder order, HttpServletRequest request) {
        checkStaffRole((String) request.getAttribute("role"));
        purchaseOrderService.save(order, (Long) request.getAttribute("userId"));
        return Result.success();
    }

    @PutMapping("/approve/{id}")
    public Result<?> approve(@PathVariable Long id, HttpServletRequest request) {
        checkStaffRole((String) request.getAttribute("role"));
        purchaseOrderService.approve(id, (Long) request.getAttribute("userId"));
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        checkStaffRole((String) request.getAttribute("role"));
        purchaseOrderService.deleteById(id);
        return Result.success();
    }

    private void checkStaffRole(String role) {
        if (!"ADMIN".equals(role) && !"STAFF".equals(role)) {
            throw new BusinessException("无权限操作");
        }
    }
}
