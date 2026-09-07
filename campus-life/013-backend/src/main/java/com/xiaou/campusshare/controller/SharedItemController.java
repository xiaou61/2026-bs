package com.xiaou.campusshare.controller;

import com.xiaou.campusshare.common.Result;
import com.xiaou.campusshare.entity.OrderInfo;
import com.xiaou.campusshare.entity.SharedItem;
import com.xiaou.campusshare.entity.User;
import com.xiaou.campusshare.service.OrderService;
import com.xiaou.campusshare.service.SharedItemService;
import com.xiaou.campusshare.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/shared")
public class SharedItemController {

    @Autowired
    private SharedItemService sharedItemService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @GetMapping("/nearby")
    public Result<List<SharedItem>> getNearby(
            @RequestParam(required = false) BigDecimal latitude,
            @RequestParam(required = false) BigDecimal longitude,
            @RequestParam(required = false) String itemType) {
        List<SharedItem> items = sharedItemService.getNearbyItems(latitude, longitude, itemType);
        return Result.success(items);
    }

    @GetMapping("/{id}")
    public Result<SharedItem> getDetail(@PathVariable Long id) {
        SharedItem item = sharedItemService.getById(id);
        return Result.success(item);
    }

    @PostMapping("/scan")
    public Result<SharedItem> scan(@RequestBody Map<String, String> params) {
        String itemNo = params.get("itemNo");
        SharedItem item = sharedItemService.getByItemNo(itemNo);
        if (item == null) {
            return Result.error("物品不存在");
        }
        return Result.success(item);
    }

    @PostMapping("/rent")
    public Result<?> rent(HttpServletRequest request, @RequestBody Map<String, Object> params) {
        Long userId = (Long) request.getAttribute("userId");
        Long itemId = Long.parseLong(params.get("itemId").toString());
        String orderType = params.get("orderType").toString();

        User user = userService.getUserById(userId);
        if (user.getCreditScore() < 50) {
            return Result.error("信用分过低，无法租借");
        }

        if (user.getAuthStatus() != 2) {
            return Result.error("请先完成实名认证");
        }

        SharedItem item = sharedItemService.getById(itemId);
        if (item == null || item.getStatus() != 0) {
            return Result.error("物品不可用");
        }

        OrderInfo order = orderService.createSharedOrder(userId, itemId, orderType);
        return Result.success(order);
    }

    @PostMapping("/return")
    public Result<?> returnItem(@RequestBody Map<String, Long> params) {
        Long orderId = params.get("orderId");
        boolean success = orderService.returnSharedItem(orderId);
        if (success) {
            return Result.success("归还成功");
        } else {
            return Result.error("归还失败");
        }
    }

    @GetMapping("/bikes")
    public Result<List<SharedItem>> getBikes(
            @RequestParam(required = false) BigDecimal latitude,
            @RequestParam(required = false) BigDecimal longitude) {
        List<SharedItem> items = sharedItemService.getNearbyItems(latitude, longitude, "BIKE");
        return Result.success(items);
    }

    @GetMapping("/chargers")
    public Result<List<SharedItem>> getChargers(
            @RequestParam(required = false) BigDecimal latitude,
            @RequestParam(required = false) BigDecimal longitude) {
        List<SharedItem> items = sharedItemService.getNearbyItems(latitude, longitude, "CHARGER");
        return Result.success(items);
    }

    @GetMapping("/umbrellas")
    public Result<List<SharedItem>> getUmbrellas(
            @RequestParam(required = false) BigDecimal latitude,
            @RequestParam(required = false) BigDecimal longitude) {
        List<SharedItem> items = sharedItemService.getNearbyItems(latitude, longitude, "UMBRELLA");
        return Result.success(items);
    }
}

