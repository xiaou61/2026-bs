package com.xiaou.wedding.controller;

import com.xiaou.wedding.common.Result;
import com.xiaou.wedding.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/stats")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/dashboard")
    public Result<Map<String, Object>> dashboard(@RequestParam(required = false) String date) {
        return Result.success(statisticsService.dashboard(date));
    }
}
