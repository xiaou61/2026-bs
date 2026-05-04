package com.groupbuy.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.groupbuy.common.BusinessException;
import com.groupbuy.entity.*;
import com.groupbuy.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private GroupActivityMapper groupActivityMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MerchantMapper merchantMapper;

    @Autowired
    private GroupOrderMapper groupOrderMapper;

    @Transactional
    public String create(Long userId, List<Long> cartIds, Long addressId, String remark) {
        if (cartIds == null || cartIds.isEmpty()) {
            throw new BusinessException("请选择要结算的商品");
        }
        List<Long> ownedCartIds = new java.util.ArrayList<>(new java.util.LinkedHashSet<>(cartIds));
        Address address = addressMapper.selectById(addressId);
        if (address == null) {
            throw new BusinessException("收货地址不存在");
        }
        if (!userId.equals(address.getUserId())) {
            throw new BusinessException(403, "无权使用该收货地址");
        }
        QueryWrapper<Cart> cartWrapper = new QueryWrapper<>();
        cartWrapper.in("id", ownedCartIds).eq("user_id", userId);
        List<Cart> carts = cartMapper.selectList(cartWrapper);
        if (carts.size() != ownedCartIds.size()) {
            throw new BusinessException(403, "购物车中存在无权结算的商品");
        }
        Map<Long, List<Cart>> merchantCarts = new HashMap<>();
        for (Cart cart : carts) {
            Product product = productMapper.selectById(cart.getProductId());
            if (product == null || !Integer.valueOf(1).equals(product.getStatus())) {
                throw new BusinessException("商品不存在或已下架");
            }
            cart.setProduct(product);
            merchantCarts.computeIfAbsent(product.getMerchantId(), k -> new java.util.ArrayList<>()).add(cart);
        }
        String orderNo = null;
        for (Map.Entry<Long, List<Cart>> entry : merchantCarts.entrySet()) {
            Long merchantId = entry.getKey();
            List<Cart> items = entry.getValue();
            BigDecimal total = BigDecimal.ZERO;
            for (Cart cart : items) {
                BigDecimal price = cart.getProduct().getOriginalPrice();
                if (cart.getActivityId() != null) {
                    GroupActivity activity = groupActivityMapper.selectById(cart.getActivityId());
                    if (activity != null && activity.getStatus() == 1) {
                        price = activity.getGroupPrice();
                    }
                }
                total = total.add(price.multiply(new BigDecimal(cart.getQuantity())));
            }
            Orders order = new Orders();
            order.setOrderNo(generateOrderNo());
            order.setUserId(userId);
            order.setMerchantId(merchantId);
            order.setTotalAmount(total);
            order.setPayAmount(total);
            Map<String, String> addrInfo = new HashMap<>();
            addrInfo.put("name", address.getName());
            addrInfo.put("phone", address.getPhone());
            addrInfo.put("address", address.getProvince() + address.getCity() + address.getDistrict() + address.getDetail());
            order.setAddressInfo(JSON.toJSONString(addrInfo));
            order.setStatus(0);
            order.setRemark(remark);
            ordersMapper.insert(order);
            if (orderNo == null) {
                orderNo = order.getOrderNo();
            }
            for (Cart cart : items) {
                BigDecimal price = cart.getProduct().getOriginalPrice();
                if (cart.getActivityId() != null) {
                    GroupActivity activity = groupActivityMapper.selectById(cart.getActivityId());
                    if (activity != null && activity.getStatus() == 1) {
                        price = activity.getGroupPrice();
                    }
                }
                OrderItem item = new OrderItem();
                item.setOrderId(order.getId());
                item.setProductId(cart.getProductId());
                item.setProductName(cart.getProduct().getName());
                item.setProductCover(cart.getProduct().getCover());
                item.setPrice(price);
                item.setQuantity(cart.getQuantity());
                item.setSubtotal(price.multiply(new BigDecimal(cart.getQuantity())));
                orderItemMapper.insert(item);
            }
        }
        cartMapper.deleteBatchIds(ownedCartIds);
        return orderNo;
    }

    private String generateOrderNo() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + 
               String.format("%04d", (int)(Math.random() * 10000));
    }

    public Page<Orders> page(Long userId, Integer role, Long merchantId, Integer pageNum, Integer pageSize, Integer status, String orderNo) {
        Page<Orders> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Orders> wrapper = new QueryWrapper<>();
        if (role == 2) {
            wrapper.eq("user_id", userId);
        } else if (role == 1) {
            if (merchantId == null) {
                wrapper.eq("merchant_id", -1);
            } else {
                wrapper.eq("merchant_id", merchantId);
            }
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        if (orderNo != null && !orderNo.isEmpty()) {
            wrapper.like("order_no", orderNo);
        }
        wrapper.orderByDesc("create_time");
        Page<Orders> result = ordersMapper.selectPage(page, wrapper);
        result.getRecords().forEach(this::fillOrderInfo);
        return result;
    }

    public Orders detail(Long id, Long userId, Integer role, Long merchantId) {
        Orders order = requireOrder(id);
        ensureOrderAccess(order, userId, role, merchantId);
        fillOrderInfo(order);
        return order;
    }

    private void fillOrderInfo(Orders order) {
        QueryWrapper<OrderItem> itemWrapper = new QueryWrapper<>();
        itemWrapper.eq("order_id", order.getId());
        order.setItems(orderItemMapper.selectList(itemWrapper));
        User user = userMapper.selectById(order.getUserId());
        if (user != null) {
            order.setUsername(user.getNickname());
        }
        Merchant merchant = merchantMapper.selectById(order.getMerchantId());
        if (merchant != null) {
            order.setMerchantName(merchant.getName());
        }
        if (order.getGroupOrderId() != null) {
            order.setGroupOrder(groupOrderMapper.selectById(order.getGroupOrderId()));
        }
    }

    @Transactional
    public void pay(Long id, Long userId) {
        Orders order = requireOrder(id);
        ensureUserOwner(order, userId);
        if (order == null || order.getStatus() != 0) {
            throw new BusinessException("订单不存在或状态异常");
        }
        order.setStatus(1);
        order.setPayTime(LocalDateTime.now());
        ordersMapper.updateById(order);
        QueryWrapper<OrderItem> itemWrapper = new QueryWrapper<>();
        itemWrapper.eq("order_id", id);
        orderItemMapper.selectList(itemWrapper).forEach(item -> {
            Product product = productMapper.selectById(item.getProductId());
            product.setSales(product.getSales() + item.getQuantity());
            product.setStock(product.getStock() - item.getQuantity());
            productMapper.updateById(product);
        });
    }

    public void ship(Long id, Long merchantId) {
        Orders order = requireOrder(id);
        ensureMerchantOwner(order, merchantId);
        if (order == null || order.getStatus() != 1) {
            throw new BusinessException("订单不存在或状态异常");
        }
        order.setStatus(2);
        order.setShipTime(LocalDateTime.now());
        ordersMapper.updateById(order);
    }

    public void receive(Long id, Long userId) {
        Orders order = requireOrder(id);
        ensureUserOwner(order, userId);
        if (order == null || order.getStatus() != 2) {
            throw new BusinessException("订单不存在或状态异常");
        }
        order.setStatus(3);
        order.setReceiveTime(LocalDateTime.now());
        ordersMapper.updateById(order);
    }

    public void cancel(Long id, Long userId) {
        Orders order = requireOrder(id);
        ensureUserOwner(order, userId);
        if (order == null || order.getStatus() != 0) {
            throw new BusinessException("订单不存在或状态异常");
        }
        order.setStatus(4);
        ordersMapper.updateById(order);
    }

    public void applyRefund(Long id, Long userId, String reason) {
        Orders order = requireOrder(id);
        ensureUserOwner(order, userId);
        if (order == null || (order.getStatus() != 1 && order.getStatus() != 2 && order.getStatus() != 3)) {
            throw new BusinessException("订单不存在或状态异常");
        }
        order.setStatus(5);
        order.setRemark(reason);
        ordersMapper.updateById(order);
    }

    public void handleRefund(Long id, Long merchantId, Boolean agree, String remark) {
        Orders order = requireOrder(id);
        ensureMerchantOwner(order, merchantId);
        if (order == null || order.getStatus() != 5) {
            throw new BusinessException("订单不存在或状态异常");
        }
        if (agree) {
            order.setStatus(6);
        } else {
            order.setStatus(3);
        }
        ordersMapper.updateById(order);
    }

    private Orders requireOrder(Long id) {
        Orders order = ordersMapper.selectById(id);
        if (order == null) {
            throw new BusinessException(404, "订单不存在");
        }
        return order;
    }

    private void ensureOrderAccess(Orders order, Long userId, Integer role, Long merchantId) {
        if (Integer.valueOf(0).equals(role)) {
            return;
        }
        if (Integer.valueOf(2).equals(role)) {
            ensureUserOwner(order, userId);
            return;
        }
        if (Integer.valueOf(1).equals(role)) {
            ensureMerchantOwner(order, merchantId);
            return;
        }
        throw new BusinessException(403, "无权查看该订单");
    }

    private void ensureUserOwner(Orders order, Long userId) {
        if (userId == null || !userId.equals(order.getUserId())) {
            throw new BusinessException(403, "无权操作该订单");
        }
    }

    private void ensureMerchantOwner(Orders order, Long merchantId) {
        if (merchantId == null || !merchantId.equals(order.getMerchantId())) {
            throw new BusinessException(403, "无权操作该订单");
        }
    }
}
