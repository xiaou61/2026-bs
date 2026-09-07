package com.xiaou.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.common.Result;
import com.xiaou.entity.Express;
import com.xiaou.entity.OverdueRecord;
import com.xiaou.service.ExpressService;
import com.xiaou.service.OverdueRecordService;
import com.xiaou.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@RestController
@RequestMapping("/api/overdue")
@CrossOrigin
public class OverdueController {

    @Autowired
    private ExpressService expressService;

    @Autowired
    private OverdueRecordService overdueRecordService;

    @GetMapping("/list")
    public Result<?> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Long stationId) {
        
        Page<Express> pageObj = new Page<>(page, size);
        QueryWrapper<Express> wrapper = new QueryWrapper<>();
        
        wrapper.eq("status", 0);
        
        if (stationId != null) {
            wrapper.eq("station_id", stationId);
        }
        
        wrapper.orderByAsc("in_time");
        Page<Express> result = expressService.page(pageObj, wrapper);
        
        result.getRecords().forEach(express -> {
            long days = ChronoUnit.DAYS.between(express.getInTime().toLocalDate(), LocalDateTime.now().toLocalDate());
            int overdueDays = (int) Math.max(0, days - 3);
            express.setOverdueDays(overdueDays);
        });
        
        return Result.success(result);
    }

    @GetMapping("/my")
    public Result<?> myOverdue(@RequestHeader("Authorization") String token) {
        try {
            Long userId = JwtUtil.getUserId(token);
            
            QueryWrapper<Express> wrapper = new QueryWrapper<>();
            wrapper.eq("recipient_id", userId);
            wrapper.eq("status", 0);
            wrapper.orderByAsc("in_time");
            
            return Result.success(expressService.list(wrapper));
        } catch (Exception e) {
            return Result.error("查询失败");
        }
    }

    @GetMapping("/records")
    public Result<?> records(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        
        Page<OverdueRecord> pageObj = new Page<>(page, size);
        QueryWrapper<OverdueRecord> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("create_time");
        
        Page<OverdueRecord> result = overdueRecordService.page(pageObj, wrapper);
        return Result.success(result);
    }
}

