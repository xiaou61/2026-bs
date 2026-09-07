package com.adoption.controller;

import com.adoption.common.Result;
import com.adoption.entity.MatchRecord;
import com.adoption.service.AuthService;
import com.adoption.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/match")
public class MatchController {

    @Autowired
    private MatchService matchService;

    @Autowired
    private AuthService authService;

    @GetMapping("/list")
    public Result<Map<String, Object>> list(@RequestAttribute("role") String role,
                                            @RequestParam(required = false) Integer pageNum,
                                            @RequestParam(required = false) Integer pageSize) {
        authService.assertStaff(role);
        return Result.success(matchService.page(pageNum, pageSize));
    }

    @PostMapping("/add")
    public Result<String> add(@RequestAttribute("userId") Long userId,
                              @RequestAttribute("role") String role,
                              @RequestBody MatchRecord record) {
        authService.assertStaff(role);
        record.setReviewerId(userId);
        matchService.add(record);
        return Result.success();
    }

    @PutMapping("/approve")
    public Result<String> approve(@RequestAttribute("userId") Long userId,
                                  @RequestAttribute("role") String role,
                                  @RequestParam Long matchId,
                                  @RequestParam Integer status,
                                  @RequestParam(required = false) String remark) {
        authService.assertStaff(role);
        matchService.approve(userId, matchId, status, remark);
        return Result.success();
    }
}
