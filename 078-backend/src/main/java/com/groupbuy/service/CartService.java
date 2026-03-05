package com.groupbuy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.groupbuy.entity.Cart;
import com.groupbuy.entity.GroupActivity;
import com.groupbuy.entity.Product;
import com.groupbuy.mapper.CartMapper;
import com.groupbuy.mapper.GroupActivityMapper;
import com.groupbuy.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private GroupActivityMapper groupActivityMapper;

    public List<Cart> list(Long userId) {
        QueryWrapper<Cart> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId).orderByDesc("create_time");
        List<Cart> carts = cartMapper.selectList(wrapper);
        carts.forEach(this::fillCartInfo);
        return carts;
    }

    public void add(Long userId, Long productId, Long activityId, Integer quantity) {
        QueryWrapper<Cart> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId).eq("product_id", productId);
        if (activityId != null) {
            wrapper.eq("activity_id", activityId);
        } else {
            wrapper.isNull("activity_id");
        }
        Cart cart = cartMapper.selectOne(wrapper);
        if (cart != null) {
            cart.setQuantity(cart.getQuantity() + quantity);
            cartMapper.updateById(cart);
        } else {
            cart = new Cart();
            cart.setUserId(userId);
            cart.setProductId(productId);
            cart.setActivityId(activityId);
            cart.setQuantity(quantity);
            cart.setSelected(1);
            cartMapper.insert(cart);
        }
    }

    public void updateQuantity(Long id, Integer quantity) {
        Cart cart = new Cart();
        cart.setId(id);
        cart.setQuantity(quantity);
        cartMapper.updateById(cart);
    }

    public void delete(Long id) {
        cartMapper.deleteById(id);
    }

    public void clear(Long userId) {
        QueryWrapper<Cart> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        cartMapper.delete(wrapper);
    }

    private void fillCartInfo(Cart cart) {
        Product product = productMapper.selectById(cart.getProductId());
        cart.setProduct(product);
        if (cart.getActivityId() != null) {
            GroupActivity activity = groupActivityMapper.selectById(cart.getActivityId());
            if (activity != null) {
                cart.setGroupPrice(activity.getGroupPrice());
            }
        }
    }
}
