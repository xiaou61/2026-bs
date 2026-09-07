package com.xiaou.ordering.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.ordering.common.BusinessException;
import com.xiaou.ordering.entity.Cart;
import com.xiaou.ordering.entity.Dish;
import com.xiaou.ordering.entity.Merchant;
import com.xiaou.ordering.mapper.CartMapper;
import com.xiaou.ordering.mapper.DishMapper;
import com.xiaou.ordering.mapper.MerchantMapper;
import com.xiaou.ordering.vo.CartItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private MerchantMapper merchantMapper;

    public void addToCart(Long userId, Long dishId, Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new BusinessException("购买数量必须大于0");
        }

        Dish dish = dishMapper.selectById(dishId);
        if (dish == null) {
            throw new BusinessException("菜品不存在");
        }

        if (dish.getStatus() != 1) {
            throw new BusinessException("菜品已下架");
        }

        if (dish.getStock() != null && dish.getStock() > 0 && quantity > dish.getStock()) {
            throw new BusinessException("菜品库存不足");
        }

        LambdaQueryWrapper<Cart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Cart::getUserId, userId);
        wrapper.eq(Cart::getDishId, dishId);
        Cart existCart = cartMapper.selectOne(wrapper);

        if (existCart != null) {
            int newQuantity = existCart.getQuantity() + quantity;
            if (dish.getStock() != null && dish.getStock() > 0 && newQuantity > dish.getStock()) {
                throw new BusinessException("菜品库存不足");
            }
            existCart.setQuantity(newQuantity);
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

    public List<CartItemVO> getCartByUser(Long userId) {
        LambdaQueryWrapper<Cart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Cart::getUserId, userId);
        wrapper.orderByDesc(Cart::getCreateTime);
        List<Cart> carts = cartMapper.selectList(wrapper);
        if (carts.isEmpty()) {
            return List.of();
        }

        Set<Long> merchantIds = carts.stream().map(Cart::getMerchantId).collect(Collectors.toSet());
        Map<Long, String> merchantNameMap = merchantMapper.selectBatchIds(merchantIds).stream()
                .collect(Collectors.toMap(Merchant::getId, Merchant::getMerchantName, (left, right) -> left));

        return carts.stream().map(cart -> {
            CartItemVO item = new CartItemVO();
            item.setId(cart.getId());
            item.setMerchantId(cart.getMerchantId());
            item.setMerchantName(merchantNameMap.getOrDefault(cart.getMerchantId(), "未知商家"));
            item.setDishId(cart.getDishId());
            item.setDishName(cart.getDishName());
            item.setDishImage(cart.getDishImage());
            item.setPrice(cart.getPrice());
            item.setQuantity(cart.getQuantity());
            item.setAmount(cart.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())));
            item.setUpdateTime(cart.getUpdateTime());
            return item;
        }).toList();
    }

    public List<Cart> getCartEntities(Long userId, Long merchantId) {
        LambdaQueryWrapper<Cart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Cart::getUserId, userId)
                .eq(Cart::getMerchantId, merchantId)
                .orderByAsc(Cart::getId);
        return cartMapper.selectList(wrapper);
    }

    public void updateCartQuantity(Long userId, Long cartId, Integer quantity) {
        if (quantity == null || quantity <= 0) {
            deleteCart(userId, cartId);
            return;
        }

        Cart cart = requireOwnedCart(userId, cartId);
        Dish dish = dishMapper.selectById(cart.getDishId());
        if (dish != null && dish.getStock() != null && dish.getStock() > 0 && quantity > dish.getStock()) {
            throw new BusinessException("菜品库存不足");
        }
        cart.setQuantity(quantity);
        cartMapper.updateById(cart);
    }

    public void deleteCart(Long userId, Long cartId) {
        Cart cart = requireOwnedCart(userId, cartId);
        cartMapper.deleteById(cart.getId());
    }

    public void clearCart(Long userId) {
        LambdaQueryWrapper<Cart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Cart::getUserId, userId);
        cartMapper.delete(wrapper);
    }

    public void clearMerchantCart(Long userId, Long merchantId) {
        LambdaQueryWrapper<Cart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Cart::getUserId, userId)
                .eq(Cart::getMerchantId, merchantId);
        cartMapper.delete(wrapper);
    }

    private Cart requireOwnedCart(Long userId, Long cartId) {
        Cart cart = cartMapper.selectById(cartId);
        if (cart == null || !cart.getUserId().equals(userId)) {
            throw new BusinessException("购物车记录不存在");
        }
        return cart;
    }
}

