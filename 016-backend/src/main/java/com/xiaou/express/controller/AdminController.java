package com.xiaou.express.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.express.common.Result;
import com.xiaou.express.dto.LoginRequest;
import com.xiaou.express.entity.Complaint;
import com.xiaou.express.entity.Orders;
import com.xiaou.express.entity.Transaction;
import com.xiaou.express.entity.User;
import com.xiaou.express.service.AdminService;
import com.xiaou.express.service.AuthService;
import com.xiaou.express.vo.LoginVO;
import com.xiaou.express.vo.StatisticsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody LoginRequest request) {
        LoginVO loginVO = authService.adminLogin(request);
        return Result.success(loginVO);
    }

    @GetMapping("/statistics")
    public Result<StatisticsVO> getStatistics() {
        StatisticsVO statistics = adminService.getStatistics();
        return Result.success(statistics);
    }

    @GetMapping("/users")
    public Result<Page<User>> getUsers(@RequestParam(defaultValue = "1") int pageNum,
                                         @RequestParam(defaultValue = "10") int pageSize) {
        Page<User> page = adminService.getUsers(pageNum, pageSize);
        return Result.success(page);
    }

    @PutMapping("/users/{id}/status")
    public Result<Void> updateUserStatus(@PathVariable Long id, @RequestBody Map<String, Integer> request) {
        Integer status = request.get("status");
        adminService.updateUserStatus(id, status);
        return Result.success();
    }

    @GetMapping("/orders")
    public Result<Page<Orders>> getOrders(@RequestParam(defaultValue = "1") int pageNum,
                                            @RequestParam(defaultValue = "10") int pageSize) {
        Page<Orders> page = adminService.getOrders(pageNum, pageSize);
        return Result.success(page);
    }

    @GetMapping("/complaints")
    public Result<Page<Complaint>> getComplaints(@RequestParam(defaultValue = "1") int pageNum,
                                                   @RequestParam(defaultValue = "10") int pageSize) {
        Page<Complaint> page = adminService.getComplaints(pageNum, pageSize);
        return Result.success(page);
    }

    @GetMapping("/transactions")
    public Result<Page<Transaction>> getTransactions(@RequestParam(defaultValue = "1") int pageNum,
                                                       @RequestParam(defaultValue = "10") int pageSize) {
        Page<Transaction> page = adminService.getTransactions(pageNum, pageSize);
        return Result.success(page);
    }
}

