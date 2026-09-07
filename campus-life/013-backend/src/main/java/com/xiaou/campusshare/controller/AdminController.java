package com.xiaou.campusshare.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.campusshare.common.Result;
import com.xiaou.campusshare.entity.IdleItem;
import com.xiaou.campusshare.entity.OrderInfo;
import com.xiaou.campusshare.entity.SharedItem;
import com.xiaou.campusshare.entity.User;
import com.xiaou.campusshare.entity.UserAuth;
import com.xiaou.campusshare.mapper.UserAuthMapper;
import com.xiaou.campusshare.service.IdleItemService;
import com.xiaou.campusshare.service.OrderService;
import com.xiaou.campusshare.service.SharedItemService;
import com.xiaou.campusshare.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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

    @Autowired
    private SharedItemService sharedItemService;

    @Autowired
    private OrderService orderService;

    private Result<?> requireRoles(HttpServletRequest request, String... roles) {
        Long userId = (Long) request.getAttribute("userId");
        if (userId == null) {
            return Result.error(401, "未登录或登录已过期");
        }

        User user = userService.getById(userId);
        if (user == null) {
            return Result.error(401, "用户不存在");
        }

        for (String role : roles) {
            if (role.equalsIgnoreCase(user.getRole())) {
                return null;
            }
        }
        return Result.error(403, "无管理员权限");
    }

    @GetMapping("/user/list")
    public Result<Page<User>> getUserList(
            HttpServletRequest request,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        Result<?> auth = requireRoles(request, "ADMIN");
        if (auth != null) {
            return (Result<Page<User>>) auth;
        }
        Page<User> result = userService.page(new Page<>(page, size));
        result.getRecords().forEach(user -> user.setPassword(null));
        return Result.success(result);
    }

    @PutMapping("/user/{id}/status")
    public Result<?> updateUserStatus(HttpServletRequest request, @PathVariable Long id, @RequestBody Map<String, Integer> params) {
        Result<?> auth = requireRoles(request, "ADMIN");
        if (auth != null) {
            return auth;
        }
        User user = userService.getById(id);
        if (user != null) {
            user.setStatus(params.get("status"));
            userService.updateById(user);
        }
        return Result.success("更新成功");
    }

    @GetMapping("/auth/list")
    public Result<Page<UserAuth>> getAuthList(
            HttpServletRequest request,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        Result<?> auth = requireRoles(request, "ADMIN");
        if (auth != null) {
            return (Result<Page<UserAuth>>) auth;
        }
        LambdaQueryWrapper<UserAuth> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserAuth::getAuthStatus, 0);
        wrapper.orderByDesc(UserAuth::getCreateTime);
        Page<UserAuth> result = userAuthMapper.selectPage(new Page<>(page, size), wrapper);
        return Result.success(result);
    }

    @PutMapping("/auth/{id}/audit")
    public Result<?> auditAuth(HttpServletRequest request, @PathVariable Long id, @RequestBody Map<String, Object> params) {
        Result<?> auth = requireRoles(request, "ADMIN");
        if (auth != null) {
            return auth;
        }
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
            HttpServletRequest request,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        Result<?> auth = requireRoles(request, "ADMIN");
        if (auth != null) {
            return (Result<Page<IdleItem>>) auth;
        }
        LambdaQueryWrapper<IdleItem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(IdleItem::getStatus, 0);
        wrapper.orderByDesc(IdleItem::getCreateTime);
        Page<IdleItem> result = idleItemService.page(new Page<>(page, size), wrapper);
        return Result.success(result);
    }

    @PutMapping("/idle/{id}/audit")
    public Result<?> auditIdle(HttpServletRequest request, @PathVariable Long id, @RequestBody Map<String, Object> params) {
        Result<?> auth = requireRoles(request, "ADMIN");
        if (auth != null) {
            return auth;
        }
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

    @GetMapping("/shared/list")
    public Result<Page<SharedItem>> getSharedList(
            HttpServletRequest request,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String itemType,
            @RequestParam(required = false) Integer status) {
        Result<?> auth = requireRoles(request, "ADMIN", "OPERATOR");
        if (auth != null) {
            return (Result<Page<SharedItem>>) auth;
        }

        LambdaQueryWrapper<SharedItem> wrapper = new LambdaQueryWrapper<>();
        if (itemType != null && !itemType.isBlank()) {
            wrapper.eq(SharedItem::getItemType, itemType);
        }
        if (status != null) {
            wrapper.eq(SharedItem::getStatus, status);
        }
        wrapper.orderByAsc(SharedItem::getId);
        Page<SharedItem> result = sharedItemService.page(new Page<>(page, size), wrapper);
        return Result.success(result);
    }

    @PostMapping("/shared/add")
    public Result<?> addSharedItem(HttpServletRequest request, @RequestBody SharedItem item) {
        Result<?> auth = requireRoles(request, "ADMIN", "OPERATOR");
        if (auth != null) {
            return auth;
        }

        if (item.getItemNo() == null || item.getItemNo().isBlank()) {
            return Result.error("物品编号不能为空");
        }
        if (item.getItemType() == null || item.getItemType().isBlank()) {
            return Result.error("物品类型不能为空");
        }
        if (item.getLocationName() == null || item.getLocationName().isBlank()) {
            return Result.error("位置名称不能为空");
        }
        if (item.getHourlyPrice() == null) {
            return Result.error("小时价格不能为空");
        }
        if (sharedItemService.getByItemNo(item.getItemNo()) != null) {
            return Result.error("物品编号已存在");
        }

        if (item.getStatus() == null) {
            item.setStatus(0);
        }
        if (item.getItemModel() == null || item.getItemModel().isBlank()) {
            item.setItemModel("后台新增设备");
        }
        if (item.getDailyMaxPrice() == null) {
            item.setDailyMaxPrice(defaultDailyMaxPrice(item.getItemType()));
        }
        if (item.getDepositAmount() == null) {
            item.setDepositAmount(defaultDepositAmount(item.getItemType()));
        }
        if (item.getBatteryLevel() == null && !"UMBRELLA".equalsIgnoreCase(item.getItemType())) {
            item.setBatteryLevel(100);
        }
        if (item.getTotalUsageCount() == null) {
            item.setTotalUsageCount(0);
        }
        if (item.getTotalDistance() == null) {
            item.setTotalDistance(BigDecimal.ZERO);
        }

        sharedItemService.save(item);
        return Result.success("添加成功");
    }

    @PutMapping("/shared/{id}")
    public Result<?> updateSharedItem(HttpServletRequest request, @PathVariable Long id, @RequestBody SharedItem item) {
        Result<?> auth = requireRoles(request, "ADMIN", "OPERATOR");
        if (auth != null) {
            return auth;
        }

        SharedItem existing = sharedItemService.getById(id);
        if (existing == null) {
            return Result.error("共享物品不存在");
        }

        if (item.getItemNo() != null && !item.getItemNo().isBlank() && !item.getItemNo().equals(existing.getItemNo())) {
            SharedItem duplicate = sharedItemService.getByItemNo(item.getItemNo());
            if (duplicate != null && !duplicate.getId().equals(id)) {
                return Result.error("物品编号已存在");
            }
            existing.setItemNo(item.getItemNo());
        }
        if (item.getItemType() != null && !item.getItemType().isBlank()) {
            existing.setItemType(item.getItemType());
        }
        if (item.getItemModel() != null) {
            existing.setItemModel(item.getItemModel());
        }
        if (item.getLocationName() != null) {
            existing.setLocationName(item.getLocationName());
        }
        if (item.getLatitude() != null) {
            existing.setLatitude(item.getLatitude());
        }
        if (item.getLongitude() != null) {
            existing.setLongitude(item.getLongitude());
        }
        if (item.getBatteryLevel() != null) {
            existing.setBatteryLevel(item.getBatteryLevel());
        }
        if (item.getStatus() != null) {
            existing.setStatus(item.getStatus());
        }
        if (item.getHourlyPrice() != null) {
            existing.setHourlyPrice(item.getHourlyPrice());
        }
        if (item.getDailyMaxPrice() != null) {
            existing.setDailyMaxPrice(item.getDailyMaxPrice());
        }
        if (item.getDepositAmount() != null) {
            existing.setDepositAmount(item.getDepositAmount());
        }
        sharedItemService.updateById(existing);
        return Result.success("更新成功");
    }

    private BigDecimal defaultDailyMaxPrice(String itemType) {
        return switch (itemType == null ? "" : itemType.toUpperCase()) {
            case "BIKE" -> BigDecimal.valueOf(20);
            case "CHARGER" -> BigDecimal.valueOf(10);
            case "UMBRELLA" -> BigDecimal.valueOf(5);
            default -> BigDecimal.ZERO;
        };
    }

    private BigDecimal defaultDepositAmount(String itemType) {
        return switch (itemType == null ? "" : itemType.toUpperCase()) {
            case "BIKE" -> BigDecimal.valueOf(99);
            case "CHARGER" -> BigDecimal.ZERO;
            case "UMBRELLA" -> BigDecimal.valueOf(10);
            default -> BigDecimal.ZERO;
        };
    }

    @GetMapping("/stats/overview")
    public Result<Map<String, Object>> getStatsOverview(HttpServletRequest request) {
        Result<?> auth = requireRoles(request, "ADMIN", "OPERATOR");
        if (auth != null) {
            return (Result<Map<String, Object>>) auth;
        }

        Map<String, Object> stats = new HashMap<>();
        stats.put("userCount", userService.count());
        stats.put("idleItemCount", idleItemService.count());
        stats.put("sharedItemCount", sharedItemService.count());
        stats.put("orderCount", orderService.count());

        BigDecimal platformRevenue = orderService.list().stream()
                .map(OrderInfo::getPlatformFee)
                .filter(fee -> fee != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        stats.put("platformRevenue", platformRevenue);
        return Result.success(stats);
    }
}

