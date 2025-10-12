package com.xiaou.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.common.Result;
import com.xiaou.entity.User;
import com.xiaou.service.UserService;
import com.xiaou.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public Result<?> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String role) {
        
        Page<User> pageObj = new Page<>(page, size);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.and(w -> w.like("username", keyword)
                    .or().like("real_name", keyword)
                    .or().like("phone", keyword));
        }
        
        if (role != null && !role.isEmpty()) {
            wrapper.eq("role", role);
        }
        
        wrapper.orderByDesc("create_time");
        Page<User> result = userService.page(pageObj, wrapper);
        result.getRecords().forEach(user -> user.setPassword(null));
        
        return Result.success(result);
    }

    @GetMapping("/{id}")
    public Result<?> detail(@PathVariable Long id) {
        User user = userService.getById(id);
        if (user == null) {
            return Result.error("用户不存在");
        }
        user.setPassword(null);
        return Result.success(user);
    }

    @PutMapping("/{id}")
    public Result<?> update(@PathVariable Long id, @RequestBody User user) {
        User dbUser = userService.getById(id);
        if (dbUser == null) {
            return Result.error("用户不存在");
        }
        user.setId(id);
        user.setPassword(null);
        userService.updateById(user);
        return Result.success("更新成功");
    }

    @PutMapping("/{id}/status")
    public Result<?> updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        User user = userService.getById(id);
        if (user == null) {
            return Result.error("用户不存在");
        }
        user.setStatus(status);
        userService.updateById(user);
        return Result.success("状态更新成功");
    }

    @PutMapping("/{id}/role")
    public Result<?> updateRole(@PathVariable Long id, @RequestParam String role) {
        User user = userService.getById(id);
        if (user == null) {
            return Result.error("用户不存在");
        }
        user.setRole(role);
        userService.updateById(user);
        return Result.success("角色更新成功");
    }

    @PutMapping("/{id}/password")
    public Result<?> updatePassword(@PathVariable Long id, @RequestBody Map<String, String> params) {
        User user = userService.getById(id);
        if (user == null) {
            return Result.error("用户不存在");
        }
        
        String oldPassword = params.get("oldPassword");
        String newPassword = params.get("newPassword");
        
        if (!user.getPassword().equals(MD5Util.encrypt(oldPassword))) {
            return Result.error("原密码错误");
        }
        
        user.setPassword(MD5Util.encrypt(newPassword));
        userService.updateById(user);
        return Result.success("密码修改成功");
    }
}

