package com.repair.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.repair.common.Result;
import com.repair.entity.User;
import com.repair.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody User user) {
        return Result.success(userService.login(user.getUsername(), user.getPassword()));
    }

    @PostMapping("/register")
    public Result<String> register(@RequestBody User user) {
        userService.register(user);
        return Result.success();
    }

    @GetMapping("/info")
    public Result<User> getUserInfo(@RequestAttribute("userId") String userId) {
        return Result.success(userService.getUserInfo(Long.parseLong(userId)));
    }

    @GetMapping("/list")
    public Result<Page<User>> getList(@RequestParam(defaultValue = "1") int pageNum,
                                      @RequestParam(defaultValue = "10") int pageSize,
                                      @RequestParam(required = false) String username,
                                      @RequestParam(required = false) String role,
                                      @RequestParam(required = false) Integer status) {
        return Result.success(userService.getList(pageNum, pageSize, username, role, status));
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody User user) {
        userService.register(user);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody User user) {
        userService.update(user);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id) {
        userService.delete(id);
        return Result.success();
    }
}
