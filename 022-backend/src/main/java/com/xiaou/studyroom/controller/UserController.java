package com.xiaou.studyroom.controller;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.studyroom.common.Result;
import com.xiaou.studyroom.entity.User;
import com.xiaou.studyroom.exception.BusinessException;
import com.xiaou.studyroom.service.UserService;
import com.xiaou.studyroom.utils.AuthHelper;
import com.xiaou.studyroom.utils.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthHelper authHelper;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody Map<String, String> loginMap) {
        String username = loginMap.get("username");
        String password = loginMap.get("password");

        User user = userService.login(username, password);
        if (user == null) {
            return Result.error("用户名或密码错误");
        }

        Map<String, Object> tokenMap = new HashMap<>();
        String token = jwtUtil.generateToken(user.getId(), user.getUsername());
        User safeUser = toSafeUser(user);
        tokenMap.put("token", token);
        tokenMap.put("user", safeUser);

        return Result.success("登录成功", tokenMap);
    }

    @PostMapping("/register")
    public Result<String> register(@Valid @RequestBody User user) {
        if (userService.register(user)) {
            return Result.success("注册成功");
        } else {
            return Result.error("注册失败，用户名已存在");
        }
    }

    @GetMapping("/info")
    public Result<User> getUserInfo(@RequestHeader("Authorization") String token) {
        Long userId = authHelper.getUserId(token);
        User user = userService.getById(userId);
        if (user == null) {
            return Result.error("用户不存在");
        }
        return Result.success(toSafeUser(user));
    }

    @PutMapping("/update")
    public Result<String> updateUser(@RequestHeader("Authorization") String token, @RequestBody User user) {
        Long userId = authHelper.getUserId(token);
        User existing = userService.getById(userId);
        if (existing == null) {
            return Result.error("用户不存在");
        }

        existing.setRealName(user.getRealName());
        existing.setDepartment(user.getDepartment());
        existing.setGrade(user.getGrade());
        existing.setPhone(user.getPhone());

        if (userService.updateById(existing)) {
            return Result.success("更新成功");
        }
        return Result.error("更新失败");
    }

    @PutMapping("/password")
    public Result<String> updatePassword(@RequestHeader("Authorization") String token, @RequestBody Map<String, String> passwordMap) {
        Long userId = authHelper.getUserId(token);
        User existing = userService.getById(userId);
        if (existing == null) {
            return Result.error("用户不存在");
        }

        String oldPassword = passwordMap.get("oldPassword");
        String newPassword = passwordMap.get("newPassword");
        if (oldPassword == null || newPassword == null) {
            return Result.error("缺少密码参数");
        }

        if (!SecureUtil.md5(oldPassword).equals(existing.getPassword())) {
            return Result.error("原密码错误");
        }

        existing.setPassword(SecureUtil.md5(newPassword));
        if (userService.updateById(existing)) {
            return Result.success("密码修改成功");
        }
        return Result.error("密码修改失败");
    }

    @GetMapping({"/page", "/list"})
    public Result<Page<User>> getUserPage(
            @RequestHeader("Authorization") String token,
            @RequestParam(required = false) Integer current,
            @RequestParam(required = false) Integer page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword) {
        authHelper.requireAdmin(token);
        int pageNo = current != null ? current : (page != null ? page : 1);
        Page<User> userPage = userService.getUserPage(pageNo, size, keyword);
        userPage.getRecords().replaceAll(this::toSafeUser);
        return Result.success(userPage);
    }

    @PutMapping("/{id}/status")
    public Result<String> updateUserStatus(@RequestHeader("Authorization") String token,
                                           @PathVariable Long id,
                                           @RequestBody Map<String, Integer> body) {
        authHelper.requireAdmin(token);
        User user = userService.getById(id);
        if (user == null) {
            return Result.error("用户不存在");
        }

        Integer status = body.get("status");
        if (status == null) {
            throw new BusinessException(400, "缺少状态参数");
        }
        user.setStatus(status);
        return userService.updateById(user) ? Result.success("用户状态更新成功") : Result.error("用户状态更新失败");
    }

    @PutMapping("/{id}/credit")
    public Result<String> adjustCreditScore(@RequestHeader("Authorization") String token,
                                            @PathVariable Long id,
                                            @RequestBody Map<String, Object> body) {
        authHelper.requireAdmin(token);
        Integer scoreChange = Integer.valueOf(body.getOrDefault("scoreChange", 0).toString());
        String reason = String.valueOf(body.getOrDefault("changeReason", "管理员调整"));
        return userService.updateUserCredit(id, scoreChange, reason) ? Result.success("信用分调整成功") : Result.error("信用分调整失败");
    }

    private User toSafeUser(User user) {
        user.setPassword(null);
        user.setRole(userService.isAdmin(user.getId()) ? "admin" : "student");
        return user;
    }
}
