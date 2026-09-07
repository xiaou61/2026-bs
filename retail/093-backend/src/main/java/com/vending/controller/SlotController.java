package com.vending.controller;

import com.vending.common.Result;
import com.vending.entity.MachineSlot;
import com.vending.service.SlotService;
import com.vending.utils.AuthUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/slot")
public class SlotController {

    @Resource
    private SlotService slotService;

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) Long machineId,
                          @RequestParam(required = false) String status,
                          HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        return Result.success(slotService.page(pageNum, pageSize, machineId, status));
    }

    @GetMapping("/machine/{machineId}")
    public Result<?> byMachine(@PathVariable Long machineId) {
        return Result.success(slotService.listByMachine(machineId));
    }

    @PostMapping("/save")
    public Result<?> save(@RequestBody MachineSlot slot, HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        slotService.save(slot);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        slotService.deleteById(id);
        return Result.success();
    }
}
