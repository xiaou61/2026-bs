package com.xiaou.campusclub.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.campusclub.common.Result;
import com.xiaou.campusclub.dto.LoginRequest;
import com.xiaou.campusclub.entity.Club;
import com.xiaou.campusclub.entity.User;
import com.xiaou.campusclub.service.AdminService;
import com.xiaou.campusclub.vo.LoginVO;
import com.xiaou.campusclub.vo.StatisticsVO;
import com.xiaou.campusclub.vo.TopicVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    
    @Autowired
    private AdminService adminService;
    
    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody LoginRequest request) {
        return Result.success(adminService.login(request));
    }
    
    @GetMapping("/users")
    public Result<IPage<User>> getUserList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String keyword) {
        return Result.success(adminService.getUserList(page, size, keyword));
    }
    
    @PutMapping("/users/{id}/status")
    public Result<Void> updateUserStatus(@PathVariable Long id, @RequestParam Integer status) {
        adminService.updateUserStatus(id, status);
        return Result.success();
    }
    
    @GetMapping("/clubs/pending")
    public Result<IPage<Club>> getPendingClubs(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return Result.success(adminService.getPendingClubs(page, size));
    }
    
    @PutMapping("/clubs/{id}/approve")
    public Result<Void> approveClub(@PathVariable Long id, @RequestParam Integer status) {
        adminService.approveClub(id, status);
        return Result.success();
    }
    
    @GetMapping("/topics")
    public Result<IPage<TopicVO>> getTopicList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return Result.success(adminService.getTopicList(page, size));
    }
    
    @PutMapping("/topics/{id}/top")
    public Result<Void> topTopic(@PathVariable Long id, @RequestParam Integer isTop) {
        adminService.topTopic(id, isTop);
        return Result.success();
    }
    
    @DeleteMapping("/topics/{id}")
    public Result<Void> deleteTopic(@PathVariable Long id) {
        adminService.deleteTopic(id);
        return Result.success();
    }
    
    @GetMapping("/statistics")
    public Result<StatisticsVO> getStatistics() {
        return Result.success(adminService.getStatistics());
    }
}

