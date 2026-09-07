package com.adoption.controller;

import com.adoption.common.Result;
import com.adoption.entity.HomeVisitRecord;
import com.adoption.service.AuthService;
import com.adoption.service.HomeVisitService;
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
@RequestMapping("/api/home-visit")
public class HomeVisitController {

    @Autowired
    private HomeVisitService homeVisitService;

    @Autowired
    private AuthService authService;

    @GetMapping("/list")
    public Result<Map<String, Object>> list(@RequestAttribute("role") String role,
                                            @RequestParam(required = false) Integer pageNum,
                                            @RequestParam(required = false) Integer pageSize) {
        authService.assertStaff(role);
        return Result.success(homeVisitService.page(pageNum, pageSize));
    }

    @PostMapping("/add")
    public Result<String> add(@RequestAttribute("userId") Long userId,
                              @RequestAttribute("role") String role,
                              @RequestBody HomeVisitRecord record) {
        authService.assertStaff(role);
        record.setReviewerId(userId);
        homeVisitService.add(record);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestAttribute("role") String role, @RequestBody HomeVisitRecord record) {
        authService.assertStaff(role);
        homeVisitService.update(record);
        return Result.success();
    }
}
