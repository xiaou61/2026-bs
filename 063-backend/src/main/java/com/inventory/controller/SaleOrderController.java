package com.inventory.controller;

import com.inventory.common.BusinessException;
import com.inventory.common.Result;
import com.inventory.entity.SaleOrder;
import com.inventory.service.SaleOrderService;
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
@RequestMapping("/api/sale")
public class SaleOrderController {

    @Resource
    private SaleOrderService saleOrderService;

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String orderNo,
                          @RequestParam(required = false) Long customerId,
                          @RequestParam(required = false) Integer status,
                          HttpServletRequest request) {
        checkStaffRole((String) request.getAttribute("role"));
        return Result.success(saleOrderService.page(pageNum, pageSize, orderNo, customerId, status));
    }

    @PostMapping
    public Result<?> add(@RequestBody SaleOrder order, HttpServletRequest request) {
        checkStaffRole((String) request.getAttribute("role"));
        saleOrderService.save(order, (Long) request.getAttribute("userId"));
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody SaleOrder order, HttpServletRequest request) {
        checkStaffRole((String) request.getAttribute("role"));
        saleOrderService.save(order, (Long) request.getAttribute("userId"));
        return Result.success();
    }

    @PutMapping("/approve/{id}")
    public Result<?> approve(@PathVariable Long id, HttpServletRequest request) {
        checkStaffRole((String) request.getAttribute("role"));
        saleOrderService.approve(id, (Long) request.getAttribute("userId"));
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        checkStaffRole((String) request.getAttribute("role"));
        saleOrderService.deleteById(id);
        return Result.success();
    }

    private void checkStaffRole(String role) {
        if (!"ADMIN".equals(role) && !"STAFF".equals(role)) {
            throw new BusinessException("无权限操作");
        }
    }
}
