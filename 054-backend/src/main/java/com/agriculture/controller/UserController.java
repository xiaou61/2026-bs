package com.agriculture.controller;

import com.agriculture.common.Result;
import com.agriculture.dto.LoginDTO;
import com.agriculture.entity.User;
import com.agriculture.service.UserService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result<String> login(@RequestBody LoginDTO dto) {
        String token = userService.login(dto);
        return Result.success(token);
    }

    @GetMapping("/info")
    public Result<User> getUserInfo(HttpServletRequest request) {
        Long userId = Long.parseLong(request.getAttribute("userId").toString());
        User user = userService.getUserInfo(userId);
        return Result.success(user);
    }

    @GetMapping("/page")
    public Result<Page<User>> getUserPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
                                           @RequestParam(required = false) String username,
                                           @RequestParam(required = false) String role) {
        Page<User> page = userService.getUserPage(pageNum, pageSize, username, role);
        return Result.success(page);
    }

    @PostMapping
    public Result<?> addUser(@RequestBody User user) {
        userService.save(user);
        return Result.success();
    }

    @PutMapping
    public Result<?> updateUser(@RequestBody User user) {
        userService.updateById(user);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> deleteUser(@PathVariable Long id) {
        userService.removeById(id);
        return Result.success();
    }

    @PutMapping("/password")
    public Result<?> updatePassword(@RequestBody User user, HttpServletRequest request) {
        Long userId = Long.parseLong(request.getAttribute("userId").toString());
        user.setId(userId);
        userService.updateById(user);
        return Result.success();
    }
}
