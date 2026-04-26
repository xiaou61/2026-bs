package com.football.controller;

import com.football.common.Result;
import com.football.entity.CoachInfo;
import com.football.service.CoachService;
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
@RequestMapping("/api/coach")
public class CoachController {

    @Resource
    private CoachService coachService;

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) Long teamId,
                          @RequestParam(required = false) String name,
                          @RequestParam(required = false) Integer status,
                          HttpServletRequest request) {
        AuthUtils.requireManager((String) request.getAttribute("role"));
        return Result.success(coachService.page(pageNum, pageSize, teamId, name, status));
    }

    @PostMapping("/save")
    public Result<?> save(@RequestBody CoachInfo info, HttpServletRequest request) {
        AuthUtils.requireManager((String) request.getAttribute("role"));
        coachService.save(info);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        AuthUtils.requireManager((String) request.getAttribute("role"));
        coachService.deleteById(id);
        return Result.success();
    }
}
