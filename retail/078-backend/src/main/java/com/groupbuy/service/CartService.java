package com.groupbuy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.groupbuy.common.BusinessException;
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
        if (quantity == null || quantity <= 0) {
            throw new BusinessException("购买数量必须大于0");
        }
        Product product = productMapper.selectById(productId);
        if (product == null || !Integer.valueOf(1).equals(product.getStatus())) {
            throw new BusinessException(404, "商品不存在或已下架");
        }
        if (activityId != null) {
            GroupActivity activity = groupActivityMapper.selectById(activityId);
            if (activity == null || !Integer.valueOf(1).equals(activity.getStatus()) || !productId.equals(activity.getProductId())) {
                throw new BusinessException("团购活动不存在或已结束");
            }
        }
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

    public void updateQuantity(Long userId, Long id, Integer quantity) {
        requireOwnedCart(userId, id);
        if (quantity == null || quantity <= 0) {
            throw new BusinessException("购买数量必须大于0");
        }
        Cart cart = new Cart();
        cart.setId(id);
        cart.setQuantity(quantity);
        cartMapper.updateById(cart);
    }

    public void delete(Long userId, Long id) {
        requireOwnedCart(userId, id);
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

    private Cart requireOwnedCart(Long userId, Long id) {
        Cart cart = cartMapper.selectById(id);
        if (cart == null) {
            throw new BusinessException(404, "购物车记录不存在");
        }
        if (!userId.equals(cart.getUserId())) {
            throw new BusinessException(403, "无权操作该购物车记录");
        }
        return cart;
    }
}
