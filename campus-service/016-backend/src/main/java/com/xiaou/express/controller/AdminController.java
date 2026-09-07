package com.xiaou.express.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.express.common.Result;
import com.xiaou.express.dto.AdminComplaintHandleRequest;
import com.xiaou.express.dto.AdminOrderHandleRequest;
import com.xiaou.express.dto.LoginRequest;
import com.xiaou.express.entity.Complaint;
import com.xiaou.express.entity.Orders;
import com.xiaou.express.entity.Transaction;
import com.xiaou.express.entity.User;
import com.xiaou.express.service.AdminService;
import com.xiaou.express.service.AuthService;
import com.xiaou.express.util.UserContext;
import com.xiaou.express.vo.ComplaintVO;
import com.xiaou.express.vo.LoginVO;
import com.xiaou.express.vo.OrderVO;
import com.xiaou.express.vo.StatisticsVO;
import com.xiaou.express.vo.TransactionVO;
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
                                       @RequestParam(defaultValue = "10") int pageSize,
                                       @RequestParam(required = false) String keyword,
                                       @RequestParam(required = false) Integer status) {
        Page<User> page = adminService.getUsers(pageNum, pageSize, keyword, status);
        return Result.success(page);
    }

    @PutMapping("/users/{id}/status")
    public Result<Void> updateUserStatus(@PathVariable Long id, @RequestBody Map<String, Integer> request) {
        Integer status = request.get("status");
        adminService.updateUserStatus(id, status);
        return Result.success();
    }

    @GetMapping("/orders")
    public Result<Page<OrderVO>> getOrders(@RequestParam(defaultValue = "1") int pageNum,
                                           @RequestParam(defaultValue = "10") int pageSize,
                                           @RequestParam(required = false) String orderNo,
                                           @RequestParam(required = false) Integer status) {
        Page<OrderVO> page = adminService.getOrders(pageNum, pageSize, orderNo, status);
        return Result.success(page);
    }

    @GetMapping("/complaints")
    public Result<Page<ComplaintVO>> getComplaints(@RequestParam(defaultValue = "1") int pageNum,
                                                   @RequestParam(defaultValue = "10") int pageSize,
                                                   @RequestParam(required = false) Integer status) {
        Page<ComplaintVO> page = adminService.getComplaints(pageNum, pageSize, status);
        return Result.success(page);
    }

    @GetMapping("/transactions")
    public Result<Page<TransactionVO>> getTransactions(@RequestParam(defaultValue = "1") int pageNum,
                                                       @RequestParam(defaultValue = "10") int pageSize,
                                                       @RequestParam(required = false) Integer type,
                                                       @RequestParam(required = false) Long userId) {
        Page<TransactionVO> page = adminService.getTransactions(pageNum, pageSize, type, userId);
        return Result.success(page);
    }

    @PutMapping("/orders/{id}/handle")
    public Result<Void> handleOrder(@PathVariable Long id, @RequestBody AdminOrderHandleRequest request) {
        Long adminId = UserContext.getCurrentUserId();
        adminService.handleOrder(adminId, id, request);
        return Result.success();
    }

    @PutMapping("/complaints/{id}/handle")
    public Result<Void> handleComplaint(@PathVariable Long id, @RequestBody AdminComplaintHandleRequest request) {
        Long adminId = UserContext.getCurrentUserId();
        adminService.handleComplaint(adminId, id, request);
        return Result.success();
    }
}

