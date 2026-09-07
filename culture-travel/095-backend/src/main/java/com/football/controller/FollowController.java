package com.football.controller;

import com.football.common.Result;
import com.football.dto.FollowDTO;
import com.football.service.FollowService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/follow")
public class FollowController {

    @Resource
    private FollowService followService;

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        return Result.success(followService.page(pageNum, pageSize, userId, role));
    }

    @PostMapping("/toggle")
    public Result<?> toggle(@RequestBody FollowDTO dto, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        boolean followed = followService.toggle(userId, dto.getTeamId());
        Map<String, Object> data = new HashMap<>();
        data.put("followed", followed);
        return Result.success(data);
    }
}
