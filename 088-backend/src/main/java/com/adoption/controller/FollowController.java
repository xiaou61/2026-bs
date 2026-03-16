package com.adoption.controller;

import com.adoption.common.Result;
import com.adoption.entity.FollowUpRecord;
import com.adoption.service.AuthService;
import com.adoption.service.FollowService;
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
@RequestMapping("/api/follow")
public class FollowController {

    @Autowired
    private FollowService followService;

    @Autowired
    private AuthService authService;

    @GetMapping("/list")
    public Result<Map<String, Object>> list(@RequestAttribute("role") String role,
                                            @RequestParam(required = false) Integer pageNum,
                                            @RequestParam(required = false) Integer pageSize) {
        authService.assertStaff(role);
        return Result.success(followService.page(pageNum, pageSize));
    }

    @PostMapping("/add")
    public Result<String> add(@RequestAttribute("userId") Long userId,
                              @RequestAttribute("role") String role,
                              @RequestBody FollowUpRecord record) {
        authService.assertStaff(role);
        record.setReviewerId(userId);
        followService.add(record);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestAttribute("role") String role, @RequestBody FollowUpRecord record) {
        authService.assertStaff(role);
        followService.update(record);
        return Result.success();
    }
}
