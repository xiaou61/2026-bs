package com.xiaou.ticket.controller;

import com.xiaou.ticket.dto.Result;
import com.xiaou.ticket.entity.User;
import com.xiaou.ticket.service.UserService;
import com.xiaou.ticket.util.AuthContext;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public Result<User> getUserById(@PathVariable Long id) {
        if (!canAccessUser(id)) {
            return Result.error(403, "无权查看其他用户资料");
        }
        try {
            return Result.success(userService.getUserById(id));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Result<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        if (!canAccessUser(id)) {
            return Result.error(403, "无权修改其他用户资料");
        }
        try {
            return Result.success(userService.updateUser(id, user));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/{id}/recharge")
    public Result<User> recharge(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        if (!canAccessUser(id)) {
            return Result.error(403, "无权为其他用户充值");
        }
        try {
            BigDecimal amount = new BigDecimal(request.get("amount").toString());
            return Result.success(userService.recharge(id, amount));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/{id}/balance")
    public Result<BigDecimal> getBalance(@PathVariable Long id) {
        if (!canAccessUser(id)) {
            return Result.error(403, "无权查看其他用户余额");
        }
        try {
            return Result.success(userService.getBalance(id));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    private boolean canAccessUser(Long userId) {
        return AuthContext.isAdmin() || userId.equals(AuthContext.getUserId());
    }
}
