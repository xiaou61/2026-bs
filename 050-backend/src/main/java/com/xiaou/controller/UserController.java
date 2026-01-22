package com.xiaou.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.common.Result;
import com.xiaou.entity.User;
import com.xiaou.service.UserService;
import com.xiaou.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @GetMapping("/info")
    public Result<?> getUserInfo(@RequestHeader("Authorization") String token) {
        Long userId = jwtUtil.getUserIdFromToken(token.replace("Bearer ", ""));
        User user = userService.getById(userId);
        return Result.success(user);
    }

    @PutMapping("/update")
    public Result<?> updateUser(@RequestHeader("Authorization") String token, @RequestBody User user) {
        Long userId = jwtUtil.getUserIdFromToken(token.replace("Bearer ", ""));
        user.setId(userId);
        user.setPassword(null); // 不更新密码
        user.setUpdateTime(LocalDateTime.now());
        userService.updateById(user);
        return Result.success("更新成功");
    }

    @PutMapping("/password")
    public Result<?> updatePassword(@RequestHeader("Authorization") String token,
                                    @RequestBody Map<String, String> params) {
        Long userId = jwtUtil.getUserIdFromToken(token.replace("Bearer ", ""));
        String oldPassword = params.get("oldPassword");
        String newPassword = params.get("newPassword");
        
        boolean success = userService.updatePassword(userId, oldPassword, newPassword);
        if (success) {
            return Result.success("密码修改成功");
        }
        return Result.error("原密码错误");
    }

    @GetMapping("/page")
    public Result<?> pageUsers(@RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(defaultValue = "10") Integer size,
                               @RequestParam(required = false) Integer role,
                               @RequestParam(required = false) String keyword) {
        IPage<User> result = userService.pageUsers(page, size, role, keyword);
        return Result.success(result);
    }

    @PostMapping
    public Result<?> addUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreateTime(LocalDateTime.now());
        userService.save(user);
        return Result.success("添加成功");
    }

    @PutMapping("/{id}")
    public Result<?> updateUserById(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            user.setPassword(null);
        }
        user.setUpdateTime(LocalDateTime.now());
        userService.updateById(user);
        return Result.success("更新成功");
    }

    @DeleteMapping("/{id}")
    public Result<?> deleteUser(@PathVariable Long id) {
        userService.removeById(id);
        return Result.success("删除成功");
    }

    @GetMapping("/{id}")
    public Result<?> getUserById(@PathVariable Long id) {
        return Result.success(userService.getById(id));
    }
}
