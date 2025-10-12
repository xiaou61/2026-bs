package com.xiaou.controller;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.common.Result;
import com.xiaou.entity.User;
import com.xiaou.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public Result<?> list(@RequestParam(defaultValue = "1") Integer page,
                         @RequestParam(defaultValue = "10") Integer size,
                         @RequestParam(required = false) String name,
                         @RequestParam(required = false) String role) {
        Page<User> pageParam = new Page<>(page, size);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        
        if (name != null && !name.isEmpty()) {
            wrapper.like("name", name);
        }
        if (role != null && !role.isEmpty()) {
            wrapper.eq("role", role);
        }
        
        wrapper.orderByDesc("create_time");
        IPage<User> result = userService.page(pageParam, wrapper);
        
        result.getRecords().forEach(user -> user.setPassword(null));
        
        return Result.success(result);
    }

    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id) {
        User user = userService.getById(id);
        if (user != null) {
            user.setPassword(null);
        }
        return Result.success(user);
    }

    @PutMapping("/{id}")
    public Result<?> update(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        user.setUpdateTime(LocalDateTime.now());
        userService.updateById(user);
        return Result.success();
    }

    @PutMapping("/{id}/status")
    public Result<?> updateStatus(@PathVariable Long id, @RequestBody Map<String, Integer> params) {
        User user = userService.getById(id);
        if (user == null) {
            return Result.error("用户不存在");
        }
        
        user.setStatus(params.get("status"));
        user.setUpdateTime(LocalDateTime.now());
        userService.updateById(user);
        return Result.success();
    }

    @PutMapping("/{id}/password")
    public Result<?> updatePassword(@PathVariable Long id, 
                                   @RequestBody Map<String, String> params,
                                   @RequestAttribute Long userId) {
        if (!id.equals(userId)) {
            return Result.error("无权修改他人密码");
        }

        User user = userService.getById(id);
        if (user == null) {
            return Result.error("用户不存在");
        }

        String oldPassword = DigestUtil.md5Hex(params.get("oldPassword"));
        if (!oldPassword.equals(user.getPassword())) {
            return Result.error("原密码错误");
        }

        user.setPassword(DigestUtil.md5Hex(params.get("newPassword")));
        user.setUpdateTime(LocalDateTime.now());
        userService.updateById(user);
        return Result.success();
    }

    @GetMapping("/{id}/stats")
    public Result<?> getUserStats(@PathVariable Long id) {
        User user = userService.getById(id);
        if (user == null) {
            return Result.error("用户不存在");
        }

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalPoints", user.getTotalPoints());
        stats.put("availablePoints", user.getAvailablePoints());
        stats.put("volunteerHours", user.getVolunteerHours());
        
        return Result.success(stats);
    }
}

