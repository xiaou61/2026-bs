package com.xiaou.ordering.controller;

import com.xiaou.ordering.common.Result;
import com.xiaou.ordering.dto.CartAddRequest;
import com.xiaou.ordering.service.CartService;
import com.xiaou.ordering.util.UserContext;
import com.xiaou.ordering.vo.CartItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public Result<Void> addToCart(@RequestBody CartAddRequest request) {
        cartService.addToCart(UserContext.getCurrentUserId(), request.getDishId(),
                request.getQuantity() == null ? 1 : request.getQuantity());
        return Result.success();
    }

    @GetMapping
    public Result<List<CartItemVO>> getCart() {
        List<CartItemVO> carts = cartService.getCartByUser(UserContext.getCurrentUserId());
        return Result.success(carts);
    }

    @GetMapping("/list/{userId}")
    public Result<List<CartItemVO>> getCartCompat(@PathVariable Long userId) {
        if (!UserContext.getCurrentUserId().equals(userId)) {
            return Result.error("无权查看其他用户购物车");
        }
        return Result.success(cartService.getCartByUser(userId));
    }

    @PutMapping("/{id}")
    public Result<Void> updateQuantity(@PathVariable Long id, @RequestBody CartAddRequest request) {
        cartService.updateCartQuantity(UserContext.getCurrentUserId(), id, request.getQuantity());
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteCart(@PathVariable Long id) {
        cartService.deleteCart(UserContext.getCurrentUserId(), id);
        return Result.success();
    }

    @DeleteMapping("/clear")
    public Result<Void> clearCart() {
        cartService.clearCart(UserContext.getCurrentUserId());
        return Result.success();
    }

    @DeleteMapping("/clear/{userId}")
    public Result<Void> clearCartCompat(@PathVariable Long userId) {
        if (!UserContext.getCurrentUserId().equals(userId)) {
            return Result.error("无权清空其他用户购物车");
        }
        cartService.clearCart(userId);
        return Result.success();
    }
}

