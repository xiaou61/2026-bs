package com.football.controller;

import com.football.common.Result;
import com.football.dto.MatchResultDTO;
import com.football.entity.MatchSchedule;
import com.football.service.MatchService;
import com.football.utils.AuthUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/match")
public class MatchController {

    @Resource
    private MatchService matchService;

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) Long seasonId,
                          @RequestParam(required = false) String status,
                          @RequestParam(required = false) Long teamId,
                          HttpServletRequest request) {
        AuthUtils.requireManager((String) request.getAttribute("role"));
        return Result.success(matchService.page(pageNum, pageSize, seasonId, status, teamId));
    }

    @GetMapping("/public/list")
    public Result<?> publicList(@RequestParam(required = false) Long seasonId) {
        return Result.success(matchService.publicList(seasonId));
    }

    @PostMapping("/save")
    public Result<?> save(@RequestBody MatchSchedule match, HttpServletRequest request) {
        AuthUtils.requireManager((String) request.getAttribute("role"));
        matchService.save(match);
        return Result.success();
    }

    @PutMapping("/result/{id}")
    public Result<?> result(@PathVariable Long id, @RequestBody MatchResultDTO dto, HttpServletRequest request) {
        AuthUtils.requireManager((String) request.getAttribute("role"));
        matchService.updateResult(id, dto);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        AuthUtils.requireManager((String) request.getAttribute("role"));
        matchService.deleteById(id);
        return Result.success();
    }
}
