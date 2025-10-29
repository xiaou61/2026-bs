package com.xiaou.ordering.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.ordering.common.BusinessException;
import com.xiaou.ordering.entity.Cart;
import com.xiaou.ordering.entity.Dish;
import com.xiaou.ordering.mapper.CartMapper;
import com.xiaou.ordering.mapper.DishMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private DishMapper dishMapper;

    public void addToCart(Long userId, Long dishId, Integer quantity) {
        Dish dish = dishMapper.selectById(dishId);
        if (dish == null) {
            throw new BusinessException("菜品不存在");
        }

        if (dish.getStatus() != 1) {
            throw new BusinessException("菜品已下架");
        }

        LambdaQueryWrapper<Cart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Cart::getUserId, userId);
        wrapper.eq(Cart::getDishId, dishId);
        Cart existCart = cartMapper.selectOne(wrapper);

        if (existCart != null) {
            existCart.setQuantity(existCart.getQuantity() + quantity);
            cartMapper.updateById(existCart);
        } else {
            Cart cart = new Cart();
            cart.setUserId(userId);
            cart.setMerchantId(dish.getMerchantId());
            cart.setDishId(dishId);
            cart.setDishName(dish.getDishName());
            cart.setDishImage(dish.getImage());
            cart.setPrice(dish.getPrice());
            cart.setQuantity(quantity);
            cartMapper.insert(cart);
        }
    }

    public List<Cart> getCartByUser(Long userId) {
        LambdaQueryWrapper<Cart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Cart::getUserId, userId);
        wrapper.orderByDesc(Cart::getCreateTime);
        return cartMapper.selectList(wrapper);
    }

    public void updateCartQuantity(Long cartId, Integer quantity) {
        Cart cart = cartMapper.selectById(cartId);
        if (cart != null) {
            cart.setQuantity(quantity);
            cartMapper.updateById(cart);
        }
    }

    public void deleteCart(Long cartId) {
        cartMapper.deleteById(cartId);
    }

    public void clearCart(Long userId) {
        LambdaQueryWrapper<Cart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Cart::getUserId, userId);
        cartMapper.delete(wrapper);
    }
}

