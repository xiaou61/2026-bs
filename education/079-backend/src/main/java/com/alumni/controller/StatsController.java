package com.alumni.controller;

import com.alumni.common.Result;
import com.alumni.service.StatsService;
import com.alumni.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/stats")
public class StatsController {

    @Autowired
    private StatsService statsService;

    @GetMapping("/overview")
    public Result<Map<String, Object>> overview(HttpServletRequest request) {
        AuthUtils.requireAdmin(request);
        return Result.success(statsService.overview());
    }
}
