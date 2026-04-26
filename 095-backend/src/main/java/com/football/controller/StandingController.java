package com.football.controller;

import com.football.common.Result;
import com.football.service.StandingService;
import com.football.utils.AuthUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/standing")
public class StandingController {

    @Resource
    private StandingService standingService;

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) Long seasonId,
                          @RequestParam(required = false) String teamName,
                          HttpServletRequest request) {
        return Result.success(standingService.page(pageNum, pageSize, seasonId, teamName));
    }

    @PutMapping("/refresh/{seasonId}")
    public Result<?> refresh(@PathVariable Long seasonId, HttpServletRequest request) {
        AuthUtils.requireManager((String) request.getAttribute("role"));
        standingService.rebuildSeasonStandings(seasonId);
        return Result.success();
    }
}
