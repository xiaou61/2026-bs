package com.football.controller;

import com.football.common.Result;
import com.football.entity.PlayerInfo;
import com.football.service.PlayerService;
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
@RequestMapping("/api/player")
public class PlayerController {

    @Resource
    private PlayerService playerService;

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) Long teamId,
                          @RequestParam(required = false) String name,
                          @RequestParam(required = false) String position,
                          @RequestParam(required = false) Integer status,
                          HttpServletRequest request) {
        AuthUtils.requireManager((String) request.getAttribute("role"));
        return Result.success(playerService.page(pageNum, pageSize, teamId, name, position, status));
    }

    @GetMapping("/public/list")
    public Result<?> publicList() {
        return Result.success(playerService.publicList());
    }

    @PostMapping("/save")
    public Result<?> save(@RequestBody PlayerInfo info, HttpServletRequest request) {
        AuthUtils.requireManager((String) request.getAttribute("role"));
        playerService.save(info);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        AuthUtils.requireManager((String) request.getAttribute("role"));
        playerService.deleteById(id);
        return Result.success();
    }
}
