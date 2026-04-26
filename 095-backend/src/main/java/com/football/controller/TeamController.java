package com.football.controller;

import com.football.common.Result;
import com.football.entity.TeamInfo;
import com.football.service.TeamService;
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
@RequestMapping("/api/team")
public class TeamController {

    @Resource
    private TeamService teamService;

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) Long seasonId,
                          @RequestParam(required = false) Long clubId,
                          @RequestParam(required = false) String teamName,
                          @RequestParam(required = false) Integer status,
                          HttpServletRequest request) {
        AuthUtils.requireManager((String) request.getAttribute("role"));
        return Result.success(teamService.page(pageNum, pageSize, seasonId, clubId, teamName, status));
    }

    @GetMapping("/all")
    public Result<?> all(@RequestParam(required = false) Long seasonId, HttpServletRequest request) {
        AuthUtils.requireManager((String) request.getAttribute("role"));
        return Result.success(teamService.listAll(seasonId));
    }

    @GetMapping("/public/list")
    public Result<?> publicList(@RequestParam(required = false) Long seasonId) {
        return Result.success(teamService.listAll(seasonId));
    }

    @PostMapping("/save")
    public Result<?> save(@RequestBody TeamInfo info, HttpServletRequest request) {
        AuthUtils.requireManager((String) request.getAttribute("role"));
        teamService.save(info);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        AuthUtils.requireManager((String) request.getAttribute("role"));
        teamService.deleteById(id);
        return Result.success();
    }
}
