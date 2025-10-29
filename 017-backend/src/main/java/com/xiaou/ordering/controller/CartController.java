package com.xiaou.ordering.controller;

import com.xiaou.ordering.common.Result;
import com.xiaou.ordering.entity.Cart;
import com.xiaou.ordering.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public Result<Void> addToCart(@RequestBody Map<String, Object> params) {
        Long userId = Long.parseLong(params.get("userId").toString());
        Long dishId = Long.parseLong(params.get("dishId").toString());
        Integer quantity = Integer.parseInt(params.get("quantity").toString());
        
        cartService.addToCart(userId, dishId, quantity);
        return Result.success();
    }

    @GetMapping("/list/{userId}")
    public Result<List<Cart>> getCart(@PathVariable Long userId) {
        List<Cart> carts = cartService.getCartByUser(userId);
        return Result.success(carts);
    }

    @PutMapping("/{id}")
    public Result<Void> updateQuantity(@PathVariable Long id, @RequestBody Map<String, Integer> params) {
        Integer quantity = params.get("quantity");
        cartService.updateCartQuantity(id, quantity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteCart(@PathVariable Long id) {
        cartService.deleteCart(id);
        return Result.success();
    }

    @DeleteMapping("/clear/{userId}")
    public Result<Void> clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
        return Result.success();
    }
}

