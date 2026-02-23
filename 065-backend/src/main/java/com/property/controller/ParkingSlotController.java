package com.property.controller;

import com.property.common.BusinessException;
import com.property.common.Result;
import com.property.entity.ParkingSlot;
import com.property.service.ParkingSlotService;
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
@RequestMapping("/api/parking")
public class ParkingSlotController {

    @Resource
    private ParkingSlotService parkingSlotService;

    @GetMapping("/list")
    public Result<?> list() {
        return Result.success(parkingSlotService.list());
    }

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String slotNo,
                          @RequestParam(required = false) Integer status,
                          @RequestParam(required = false) Long ownerId,
                          HttpServletRequest request) {
        checkStaffRole((String) request.getAttribute("role"));
        return Result.success(parkingSlotService.page(pageNum, pageSize, slotNo, status, ownerId));
    }

    @PostMapping
    public Result<?> add(@RequestBody ParkingSlot slot, HttpServletRequest request) {
        checkStaffRole((String) request.getAttribute("role"));
        parkingSlotService.save(slot);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody ParkingSlot slot, HttpServletRequest request) {
        checkStaffRole((String) request.getAttribute("role"));
        parkingSlotService.save(slot);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        checkStaffRole((String) request.getAttribute("role"));
        parkingSlotService.deleteById(id);
        return Result.success();
    }

    private void checkStaffRole(String role) {
        if (!"ADMIN".equals(role) && !"STAFF".equals(role)) {
            throw new BusinessException("无权限操作");
        }
    }
}
