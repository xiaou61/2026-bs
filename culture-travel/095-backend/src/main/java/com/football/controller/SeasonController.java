package com.football.controller;

import com.football.common.Result;
import com.football.entity.SeasonInfo;
import com.football.service.SeasonService;
import com.football.utils.AuthUtils;
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
@RequestMapping("/api/season")
public class SeasonController {

    @Resource
    private SeasonService seasonService;

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) Long leagueId,
                          @RequestParam(required = false) String seasonName,
                          @RequestParam(required = false) Integer status,
                          HttpServletRequest request) {
        AuthUtils.requireManager((String) request.getAttribute("role"));
        return Result.success(seasonService.page(pageNum, pageSize, leagueId, seasonName, status));
    }

    @GetMapping("/all")
    public Result<?> all(@RequestParam(required = false) Long leagueId, HttpServletRequest request) {
        AuthUtils.requireManager((String) request.getAttribute("role"));
        return Result.success(seasonService.listAll(leagueId));
    }

    @PostMapping("/save")
    public Result<?> save(@RequestBody SeasonInfo info, HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        seasonService.save(info);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        seasonService.deleteById(id);
        return Result.success();
    }
}
