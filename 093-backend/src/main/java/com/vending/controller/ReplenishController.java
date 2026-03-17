package com.vending.controller;

import com.vending.common.Result;
import com.vending.dto.ReplenishDTO;
import com.vending.service.ReplenishService;
import com.vending.utils.AuthUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/replenish")
public class ReplenishController {

    @Resource
    private ReplenishService replenishService;

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) Long machineId,
                          HttpServletRequest request) {
        String role = (String) request.getAttribute("role");
        AuthUtils.requireAdmin(role);
        Long userId = (Long) request.getAttribute("userId");
        Long operatorId = "STAFF".equalsIgnoreCase(role) ? userId : null;
        return Result.success(replenishService.page(pageNum, pageSize, machineId, operatorId));
    }

    @PostMapping("/save")
    public Result<?> save(@RequestBody ReplenishDTO dto, HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        replenishService.save((Long) request.getAttribute("userId"), dto);
        return Result.success();
    }
}
