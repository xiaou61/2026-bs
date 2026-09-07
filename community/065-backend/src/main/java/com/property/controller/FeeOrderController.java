package com.property.controller;

import com.property.common.BusinessException;
import com.property.common.Result;
import com.property.entity.FeeOrder;
import com.property.service.FeeOrderService;
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
@RequestMapping("/api/fee")
public class FeeOrderController {

    @Resource
    private FeeOrderService feeOrderService;

    @GetMapping("/my-page")
    public Result<?> myPage(@RequestParam(defaultValue = "1") Integer pageNum,
                            @RequestParam(defaultValue = "10") Integer pageSize,
                            @RequestParam(required = false) Integer status,
                            @RequestParam(required = false) String feeMonth,
                            @RequestParam(required = false) String orderNo,
                            HttpServletRequest request) {
        checkOwner((String) request.getAttribute("role"));
        return Result.success(feeOrderService.myPage((Long) request.getAttribute("userId"), pageNum, pageSize, status, feeMonth, orderNo));
    }

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) Long ownerId,
                          @RequestParam(required = false) Long houseId,
                          @RequestParam(required = false) Integer status,
                          @RequestParam(required = false) String feeMonth,
                          @RequestParam(required = false) String orderNo,
                          HttpServletRequest request) {
        checkStaffRole((String) request.getAttribute("role"));
        return Result.success(feeOrderService.page(pageNum, pageSize, ownerId, houseId, status, feeMonth, orderNo));
    }

    @PostMapping
    public Result<?> add(@RequestBody FeeOrder order, HttpServletRequest request) {
        checkStaffRole((String) request.getAttribute("role"));
        feeOrderService.save(order, (Long) request.getAttribute("userId"));
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody FeeOrder order, HttpServletRequest request) {
        checkStaffRole((String) request.getAttribute("role"));
        feeOrderService.save(order, (Long) request.getAttribute("userId"));
        return Result.success();
    }

    @PutMapping("/pay/{id}")
    public Result<?> pay(@PathVariable Long id, HttpServletRequest request) {
        feeOrderService.pay(id, (Long) request.getAttribute("userId"), (String) request.getAttribute("role"));
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        checkStaffRole((String) request.getAttribute("role"));
        feeOrderService.deleteById(id);
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
