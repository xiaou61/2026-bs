package com.vending.controller;

import com.vending.common.Result;
import com.vending.entity.VendingMachine;
import com.vending.service.MachineService;
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
@RequestMapping("/api/machine")
public class MachineController {

    @Resource
    private MachineService machineService;

    @GetMapping("/public/list")
    public Result<?> publicList() {
        return Result.success(machineService.publicList());
    }

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String name,
                          @RequestParam(required = false) String status,
                          @RequestParam(required = false) Long locationId,
                          HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        return Result.success(machineService.page(pageNum, pageSize, name, status, locationId));
    }

    @GetMapping("/all")
    public Result<?> all(HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        return Result.success(machineService.listAll());
    }

    @PostMapping("/save")
    public Result<?> save(@RequestBody VendingMachine machine, HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        machineService.save(machine);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        machineService.deleteById(id);
        return Result.success();
    }
}
