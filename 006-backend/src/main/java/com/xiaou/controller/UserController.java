package com.xiaou.controller;

import com.xiaou.common.Result;
import com.xiaou.entity.User;
import com.xiaou.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result<User> login(@RequestBody User loginUser, HttpSession session) {
        try {
            User user = userService.login(loginUser.getUsername(), loginUser.getPassword());
            session.setAttribute("user", user);
            return Result.success(user);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/register")
    public Result<?> register(@RequestBody User user) {
        try {
            userService.register(user);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/logout")
    public Result<?> logout(HttpSession session) {
        session.invalidate();
        return Result.success();
    }

    @GetMapping("/current")
    public Result<User> getCurrentUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return Result.success(user);
    }

    @PutMapping("/profile")
    public Result<?> updateProfile(@RequestBody User user, HttpSession session) {
        try {
            User currentUser = (User) session.getAttribute("user");
            user.setId(currentUser.getId());
            userService.updateUser(user);
            User updated = userService.getUserById(currentUser.getId());
            session.setAttribute("user", updated);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/list")
    public Result<List<User>> getUserList(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (!"admin".equals(user.getRole())) {
            return Result.error(403, "无权限");
        }
        List<User> users = userService.getAllUsers();
        return Result.success(users);
    }

    @DeleteMapping("/{id}")
    public Result<?> deleteUser(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (!"admin".equals(user.getRole())) {
            return Result.error(403, "无权限");
        }
        try {
            userService.deleteUser(id);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PutMapping("/{id}/status")
    public Result<?> updateStatus(@PathVariable Long id, @RequestParam Integer status, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (!"admin".equals(user.getRole())) {
            return Result.error(403, "无权限");
        }
        try {
            userService.updateStatus(id, status);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}

