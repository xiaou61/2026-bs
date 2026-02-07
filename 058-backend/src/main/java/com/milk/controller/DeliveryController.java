package com.milk.controller;

import com.milk.common.Result;
import com.milk.service.DeliveryRecordService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/delivery")
public class DeliveryController {

    @Resource
    private DeliveryRecordService deliveryRecordService;

    @GetMapping("/today")
    public Result<?> today(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(deliveryRecordService.todayTasks(userId));
    }

    @GetMapping("/history")
    public Result<?> history(HttpServletRequest request,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(deliveryRecordService.history(userId, pageNum, pageSize));
    }

    @PutMapping("/complete/{id}")
    public Result<?> complete(@PathVariable Long id) {
        deliveryRecordService.complete(id);
        return Result.success();
    }

    @PutMapping("/exception/{id}")
    public Result<?> exception(@PathVariable Long id, @RequestBody Map<String, String> params) {
        deliveryRecordService.exception(id, params.get("remark"));
        return Result.success();
    }
}
