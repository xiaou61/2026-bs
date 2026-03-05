package com.charity.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.charity.common.Result;
import com.charity.entity.User;
import com.charity.service.UserService;
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
        Map<String, Object> result = userService.login(user.getUsername(), user.getPassword());
        return Result.success(result);
    }

    @PostMapping("/register")
    public Result<String> register(@RequestBody User user) {
        userService.add(user);
        return Result.success();
    }

    @GetMapping("/info")
    public Result<User> getUserInfo(@RequestAttribute("userId") String userId) {
        User user = userService.getUserInfo(Long.parseLong(userId));
        return Result.success(user);
    }

    @GetMapping("/list")
    public Result<Page<User>> getList(@RequestParam(defaultValue = "1") int pageNum,
                                      @RequestParam(defaultValue = "10") int pageSize,
                                      @RequestParam(required = false) String username,
                                      @RequestParam(required = false) String role) {
        Page<User> page = userService.getList(pageNum, pageSize, username, role);
        return Result.success(page);
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
