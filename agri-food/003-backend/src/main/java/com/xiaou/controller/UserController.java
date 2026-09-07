package com.xiaou.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.common.BusinessException;
import com.xiaou.common.Result;
import com.xiaou.entity.User;
import com.xiaou.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") Integer page,
                          @RequestParam(defaultValue = "10") Integer size,
                          @RequestParam(required = false) String role,
                          @RequestAttribute String userRole) {
        if (!"admin".equals(userRole)) {
            throw new BusinessException(403, "无权访问");
        }

        Page<User> userPage = new Page<>(page, size);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        if (role != null && !role.isEmpty()) {
            wrapper.eq(User::getRole, role);
        }
        wrapper.orderByDesc(User::getCreateTime);
        IPage<User> result = userService.page(userPage, wrapper);

        result.getRecords().forEach(user -> user.setPassword(null));

        return Result.success(result);
    }

    @PutMapping("/{id}")
    public Result<?> update(@PathVariable Long id, @RequestBody User user, @RequestAttribute String userRole) {
        if (!"admin".equals(userRole)) {
            throw new BusinessException(403, "无权访问");
        }

        user.setId(id);
        user.setPassword(null);
        userService.updateById(user);
        return Result.success("更新成功");
    }

    @PutMapping("/{id}/status")
    public Result<?> updateStatus(@PathVariable Long id,
                                   @RequestBody Map<String, Integer> params,
                                   @RequestAttribute String userRole) {
        if (!"admin".equals(userRole)) {
            throw new BusinessException(403, "无权访问");
        }

        User user = userService.getById(id);
        user.setStatus(params.get("status"));
        userService.updateById(user);
        return Result.success("状态更新成功");
    }

    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id) {
        User user = userService.getById(id);
        user.setPassword(null);
        return Result.success(user);
    }

    @PutMapping("/profile")
    public Result<?> updateProfile(@RequestBody User user, @RequestAttribute Long userId) {
        user.setId(userId);
        user.setPassword(null);
        user.setRole(null);
        user.setStatus(null);
        userService.updateById(user);
        return Result.success("更新成功");
    }
}

