package com.xiaou.campusshare.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.campusshare.common.Result;
import com.xiaou.campusshare.entity.IdleItem;
import com.xiaou.campusshare.entity.User;
import com.xiaou.campusshare.entity.UserAuth;
import com.xiaou.campusshare.mapper.UserAuthMapper;
import com.xiaou.campusshare.service.IdleItemService;
import com.xiaou.campusshare.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserAuthMapper userAuthMapper;

    @Autowired
    private IdleItemService idleItemService;

    @GetMapping("/user/list")
    public Result<Page<User>> getUserList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<User> result = userService.page(new Page<>(page, size));
        return Result.success(result);
    }

    @PutMapping("/user/{id}/status")
    public Result<?> updateUserStatus(@PathVariable Long id, @RequestBody Map<String, Integer> params) {
        User user = userService.getById(id);
        if (user != null) {
            user.setStatus(params.get("status"));
            userService.updateById(user);
        }
        return Result.success("更新成功");
    }

    @GetMapping("/auth/list")
    public Result<Page<UserAuth>> getAuthList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        LambdaQueryWrapper<UserAuth> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserAuth::getAuthStatus, 0);
        wrapper.orderByDesc(UserAuth::getCreateTime);
        Page<UserAuth> result = userAuthMapper.selectPage(new Page<>(page, size), wrapper);
        return Result.success(result);
    }

    @PutMapping("/auth/{id}/audit")
    public Result<?> auditAuth(HttpServletRequest request, @PathVariable Long id, @RequestBody Map<String, Object> params) {
        Long adminId = (Long) request.getAttribute("userId");
        Integer status = (Integer) params.get("status");
        String reason = (String) params.get("rejectReason");
        
        UserAuth userAuth = userAuthMapper.selectById(id);
        if (userAuth != null) {
            userAuth.setAuthStatus(status);
            userAuth.setRejectReason(reason);
            userAuth.setAuditAdminId(adminId);
            userAuth.setAuditTime(LocalDateTime.now());
            userAuthMapper.updateById(userAuth);
            
            User user = userService.getById(userAuth.getUserId());
            user.setAuthStatus(status == 1 ? 2 : 3);
            if (status == 1) {
                user.setRealName(userAuth.getRealName());
                user.setIdCard(userAuth.getIdCard());
            }
            userService.updateById(user);
        }
        return Result.success("审核成功");
    }

    @GetMapping("/idle/list")
    public Result<Page<IdleItem>> getIdleList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        LambdaQueryWrapper<IdleItem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(IdleItem::getStatus, 0);
        wrapper.orderByDesc(IdleItem::getCreateTime);
        Page<IdleItem> result = idleItemService.page(new Page<>(page, size), wrapper);
        return Result.success(result);
    }

    @PutMapping("/idle/{id}/audit")
    public Result<?> auditIdle(@PathVariable Long id, @RequestBody Map<String, Object> params) {
        Integer status = (Integer) params.get("status");
        String reason = (String) params.get("auditReason");
        
        IdleItem item = idleItemService.getById(id);
        if (item != null) {
            item.setStatus(status);
            item.setAuditReason(reason);
            idleItemService.updateById(item);
        }
        return Result.success("审核成功");
    }

    @GetMapping("/stats/overview")
    public Result<Map<String, Object>> getStatsOverview() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("userCount", userService.count());
        stats.put("idleItemCount", idleItemService.count());
        return Result.success(stats);
    }
}

