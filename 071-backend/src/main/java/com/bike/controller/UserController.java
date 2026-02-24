package com.bike.controller;

import com.bike.common.Result;
import com.bike.entity.User;
import com.bike.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result<?> login(@RequestBody Map<String, String> params) {
        return Result.success(userService.login(params.get("username"), params.get("password")));
    }

    @PostMapping("/register")
    public Result<?> register(@RequestBody User user) {
        userService.register(user);
        return Result.success();
    }

    @GetMapping("/info")
    public Result<?> getInfo(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(userService.getInfo(userId));
    }

    @PutMapping("/info")
    public Result<?> updateInfo(HttpServletRequest request, @RequestBody User user) {
        Long userId = (Long) request.getAttribute("userId");
        user.setId(userId);
        userService.updateInfo(user);
        return Result.success();
    }

    @PutMapping("/password")
    public Result<?> updatePassword(HttpServletRequest request, @RequestBody Map<String, String> params) {
        Long userId = (Long) request.getAttribute("userId");
        userService.updatePassword(userId, params.get("oldPassword"), params.get("newPassword"));
        return Result.success();
    }

    @GetMapping("/list")
    public Result<?> getList(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam(required = false) String username,
                             @RequestParam(required = false) String role,
                             @RequestParam(required = false) Integer status) {
        return Result.success(userService.getList(pageNum, pageSize, username, role, status));
    }

    @PutMapping("/status/{id}")
    public Result<?> updateStatus(@PathVariable Long id, @RequestBody Map<String, Integer> params) {
        userService.updateStatus(id, params.get("status"));
        return Result.success();
    }
}
