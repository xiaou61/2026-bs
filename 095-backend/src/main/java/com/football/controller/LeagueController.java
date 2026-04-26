package com.football.controller;

import com.football.common.Result;
import com.football.entity.LeagueInfo;
import com.football.service.LeagueService;
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
@RequestMapping("/api/league")
public class LeagueController {

    @Resource
    private LeagueService leagueService;

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String name,
                          @RequestParam(required = false) Integer status,
                          HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        return Result.success(leagueService.page(pageNum, pageSize, name, status));
    }

    @GetMapping("/all")
    public Result<?> all(HttpServletRequest request) {
        AuthUtils.requireManager((String) request.getAttribute("role"));
        return Result.success(leagueService.listAll());
    }

    @PostMapping("/save")
    public Result<?> save(@RequestBody LeagueInfo info, HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        leagueService.save(info);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        leagueService.deleteById(id);
        return Result.success();
    }
}
