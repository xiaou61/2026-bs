package com.groupbuy.controller;

import com.groupbuy.common.Result;
import com.groupbuy.service.GroupOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/group")
public class GroupOrderController {

    @Autowired
    private GroupOrderService groupOrderService;

    @PostMapping("/create")
    public Result<?> create(HttpServletRequest request, @RequestBody Map<String, Long> params) {
        Long userId = (Long) request.getAttribute("userId");
        Long activityId = params.get("activityId");
        Long addressId = params.get("addressId");
        return Result.success(groupOrderService.create(userId, activityId, addressId));
    }

    @PostMapping("/join")
    public Result<?> join(HttpServletRequest request, @RequestBody Map<String, Long> params) {
        Long userId = (Long) request.getAttribute("userId");
        Long groupOrderId = params.get("groupOrderId");
        Long addressId = params.get("addressId");
        return Result.success(groupOrderService.join(userId, groupOrderId, addressId));
    }

    @GetMapping("/detail/{id}")
    public Result<?> detail(@PathVariable Long id) {
        return Result.success(groupOrderService.detail(id));
    }

    @GetMapping("/my")
    public Result<?> myGroups(HttpServletRequest request,
                              @RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              Integer status) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(groupOrderService.myGroups(userId, pageNum, pageSize, status));
    }

    @GetMapping("/ongoing/{activityId}")
    public Result<?> ongoing(@PathVariable Long activityId) {
        return Result.success(groupOrderService.ongoing(activityId));
    }
}
