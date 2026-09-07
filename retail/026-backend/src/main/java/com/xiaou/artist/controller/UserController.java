package com.xiaou.artist.controller;

import com.xiaou.artist.common.Result;
import com.xiaou.artist.entity.User;
import com.xiaou.artist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/login")
    public Result<User> login(@RequestBody User user) {
        User loginUser = userService.login(user.getUsername(), user.getPassword());
        return Result.success(loginUser);
    }
    
    @PostMapping("/register")
    public Result<User> register(@RequestBody User user) {
        User newUser = userService.register(user);
        return Result.success(newUser);
    }
    
    @GetMapping("/{id}")
    public Result<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return Result.success(user);
    }
    
    @GetMapping("/list")
    public Result<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return Result.success(users);
    }
    
    @PutMapping("/update")
    public Result<String> updateUser(@RequestBody User user) {
        boolean success = userService.updateUser(user);
        return success ? Result.success("更新成功") : Result.error("更新失败");
    }
    
    @PutMapping("/password")
    public Result<String> updatePassword(@RequestParam Long id, @RequestParam String password) {
        boolean success = userService.updatePassword(id, password);
        return success ? Result.success("密码修改成功") : Result.error("密码修改失败");
    }
    
    @PutMapping("/balance")
    public Result<String> updateBalance(@RequestParam Long id, @RequestParam BigDecimal amount) {
        boolean success = userService.updateBalance(id, amount);
        return success ? Result.success("余额更新成功") : Result.error("余额更新失败");
    }
}
