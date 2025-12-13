package com.xiaou.ticket.controller;

import com.xiaou.ticket.dto.Result;
import com.xiaou.ticket.entity.User;
import com.xiaou.ticket.service.UserService;
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
        try {
            return Result.success(userService.getUserById(id));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public Result<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            return Result.success(userService.updateUser(id, user));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    @PostMapping("/{id}/recharge")
    public Result<User> recharge(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        try {
            BigDecimal amount = new BigDecimal(request.get("amount").toString());
            return Result.success(userService.recharge(id, amount));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    @GetMapping("/{id}/balance")
    public Result<BigDecimal> getBalance(@PathVariable Long id) {
        try {
            return Result.success(userService.getBalance(id));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
