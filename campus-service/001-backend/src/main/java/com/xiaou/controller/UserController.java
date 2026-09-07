package com.xiaou.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.common.Result;
import com.xiaou.entity.User;
import com.xiaou.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 用户管理控制器
 * @author xiaou
 */
@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    /**
     * 分页查询用户列表
     */
    @GetMapping("/list")
    public Result<Page<User>> getUserList(@RequestParam(defaultValue = "1") int pageNum,
                                          @RequestParam(defaultValue = "10") int pageSize,
                                          @RequestParam(required = false) String keyword,
                                          HttpServletRequest request) {
        // 检查权限（只有管理员可以查看用户列表）
        String role = (String) request.getAttribute("role");
        if (!"admin".equals(role)) {
            return Result.error(403, "权限不足");
        }
        
        Page<User> page = userService.getUserPage(pageNum, pageSize, keyword);
        // 清除密码字段
        page.getRecords().forEach(user -> user.setPassword(null));
        return Result.success(page);
    }
    
    /**
     * 添加用户
     */
    @PostMapping("/add")
    public Result<Void> addUser(@RequestBody User user, HttpServletRequest request) {
        // 检查权限（只有管理员可以添加用户）
        String role = (String) request.getAttribute("role");
        if (!"admin".equals(role)) {
            return Result.error(403, "权限不足");
        }
        
        userService.register(user);
        return Result.success();
    }
    
    /**
     * 更新用户信息
     */
    @PutMapping("/update")
    public Result<Void> updateUser(@RequestBody User user, HttpServletRequest request) {
        Long currentUserId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        
        // 普通用户只能修改自己的信息，管理员可以修改所有用户信息
        if (!"admin".equals(role) && !currentUserId.equals(user.getId())) {
            return Result.error(403, "权限不足");
        }
        
        // 不允许修改密码（通过专门的接口修改）
        User existUser = userService.getById(user.getId());
        user.setPassword(existUser.getPassword());
        
        userService.updateById(user);
        return Result.success();
    }
    
    /**
     * 删除用户
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteUser(@PathVariable Long id, HttpServletRequest request) {
        // 检查权限（只有管理员可以删除用户）
        String role = (String) request.getAttribute("role");
        if (!"admin".equals(role)) {
            return Result.error(403, "权限不足");
        }
        
        userService.removeById(id);
        return Result.success();
    }
    
    /**
     * 根据ID查询用户信息
     */
    @GetMapping("/{id}")
    public Result<User> getUserById(@PathVariable Long id, HttpServletRequest request) {
        Long currentUserId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        
        // 普通用户只能查看自己的信息，管理员可以查看所有用户信息
        if (!"admin".equals(role) && !currentUserId.equals(id)) {
            return Result.error(403, "权限不足");
        }
        
        User user = userService.getById(id);
        if (user != null) {
            user.setPassword(null); // 清除密码字段
        }
        return Result.success(user);
    }
}
