package com.groupbuy.controller;

import com.groupbuy.common.Result;
import com.groupbuy.service.CartService;
import com.groupbuy.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/list")
    public Result<?> list(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(cartService.list(userId));
    }

    @PostMapping("/add")
    public Result<?> add(HttpServletRequest request, @RequestBody Map<String, Object> params) {
        Long userId = (Long) request.getAttribute("userId");
        Long productId = Long.valueOf(params.get("productId").toString());
        Long activityId = params.get("activityId") != null ? Long.valueOf(params.get("activityId").toString()) : null;
        Integer quantity = params.get("quantity") != null ? (Integer) params.get("quantity") : 1;
        cartService.add(userId, productId, activityId, quantity);
        return Result.success();
    }

    @PutMapping("/quantity")
    public Result<?> updateQuantity(HttpServletRequest request, @RequestBody Map<String, Object> params) {
        Long userId = AuthUtils.getUserId(request);
        Long id = Long.valueOf(params.get("id").toString());
        Integer quantity = (Integer) params.get("quantity");
        cartService.updateQuantity(userId, id, quantity);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<?> delete(HttpServletRequest request, @PathVariable Long id) {
        Long userId = AuthUtils.getUserId(request);
        cartService.delete(userId, id);
        return Result.success();
    }

    @DeleteMapping("/clear")
    public Result<?> clear(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        cartService.clear(userId);
        return Result.success();
    }
}
