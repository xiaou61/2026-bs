package com.enterprise.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.enterprise.common.BusinessException;
import com.enterprise.common.Result;
import com.enterprise.entity.User;
import com.enterprise.service.UserService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String username,
                          @RequestParam(required = false) String role,
                          @RequestParam(required = false) Integer status,
                          HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        Page<User> page = userService.page(pageNum, pageSize, username, role, status);
        return Result.success(page);
    }

    @PostMapping
    public Result<?> add(@RequestBody User user, HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        userService.save(user);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody User user, HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        userService.save(user);
        return Result.success();
    }

    @PutMapping("/status")
    public Result<?> updateStatus(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        if (params.get("id") == null || params.get("status") == null) {
            throw new BusinessException("参数不完整");
        }
        Long id = ((Number) params.get("id")).longValue();
        Integer status = ((Number) params.get("status")).intValue();
        userService.updateStatus(id, status);
        return Result.success();
    }

    @PutMapping("/profile")
    public Result<?> updateProfile(@RequestBody User user, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        userService.updateProfile(userId, user);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        userService.deleteById(id);
        return Result.success();
    }

    private void checkAdmin(String role) {
        if (!"ADMIN".equals(role)) {
            throw new BusinessException("无权限操作");
        }
    }
}




