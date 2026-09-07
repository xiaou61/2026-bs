package com.bike.controller;

import com.bike.common.Result;
import com.bike.entity.FaultReport;
import com.bike.service.FaultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/fault")
public class FaultController {

    @Autowired
    private FaultService faultService;

    @PostMapping
    public Result<?> report(HttpServletRequest request, @RequestBody FaultReport report) {
        Long userId = (Long) request.getAttribute("userId");
        report.setUserId(userId);
        faultService.report(report);
        return Result.success();
    }

    @GetMapping("/list")
    public Result<?> getList(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam(required = false) Integer status,
                             @RequestParam(required = false) Integer type) {
        return Result.success(faultService.getList(pageNum, pageSize, status, type));
    }

    @GetMapping("/my")
    public Result<?> getMyList(HttpServletRequest request,
                               @RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "10") Integer pageSize) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(faultService.getMyList(userId, pageNum, pageSize));
    }

    @PutMapping("/handle/{id}")
    public Result<?> handle(@PathVariable Long id, HttpServletRequest request, @RequestBody Map<String, Object> params) {
        Long handlerId = (Long) request.getAttribute("userId");
        faultService.handle(id, handlerId, (String) params.get("handleResult"), (Integer) params.get("status"));
        return Result.success();
    }
}
