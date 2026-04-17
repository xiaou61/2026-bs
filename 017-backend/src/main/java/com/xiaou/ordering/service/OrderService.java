package com.xiaou.ordering.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.ordering.common.BusinessException;
import com.xiaou.ordering.dto.OrderCreateRequest;
import com.xiaou.ordering.entity.Cart;
import com.xiaou.ordering.entity.Dish;
import com.xiaou.ordering.entity.Merchant;
import com.xiaou.ordering.entity.OrderDetail;
import com.xiaou.ordering.entity.Orders;
import com.xiaou.ordering.entity.User;
import com.xiaou.ordering.mapper.DishMapper;
import com.xiaou.ordering.mapper.MerchantMapper;
import com.xiaou.ordering.mapper.OrderDetailMapper;
import com.xiaou.ordering.mapper.OrdersMapper;
import com.xiaou.ordering.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class OrderService {

    private final OrdersMapper ordersMapper;
    private final OrderDetailMapper orderDetailMapper;
    private final DishMapper dishMapper;
    private final UserMapper userMapper;
    private final MerchantMapper merchantMapper;
    private final CartService cartService;

    public OrderService(OrdersMapper ordersMapper,
                        OrderDetailMapper orderDetailMapper,
                        DishMapper dishMapper,
                        UserMapper userMapper,
                        MerchantMapper merchantMapper,
                        CartService cartService) {
        this.ordersMapper = ordersMapper;
        this.orderDetailMapper = orderDetailMapper;
        this.dishMapper = dishMapper;
        this.userMapper = userMapper;
        this.merchantMapper = merchantMapper;
        this.cartService = cartService;
    }

    @Transactional
    public Orders createOrder(Long userId, OrderCreateRequest request) {
        if (request.getMerchantId() == null) {
            throw new BusinessException("商家不能为空");
        }

        User user = userMapper.selectById(userId);
        if (user == null || user.getStatus() == 1) {
            throw new BusinessException("用户不存在或已被禁用");
        }

        Merchant merchant = merchantMapper.selectById(request.getMerchantId());
        if (merchant == null || merchant.getStatus() != 1 || merchant.getAuditStatus() != 1) {
            throw new BusinessException("商家当前不可下单");
        }

        List<Cart> cartItems = cartService.getCartEntities(userId, request.getMerchantId());
        if (cartItems.isEmpty()) {
            throw new BusinessException("购物车为空，无法下单");
        }

        BigDecimal dishAmount = BigDecimal.ZERO;
        int totalQuantity = 0;
        for (Cart cartItem : cartItems) {
            Dish dish = dishMapper.selectById(cartItem.getDishId());
            if (dish == null || dish.getStatus() != 1) {
                throw new BusinessException("存在已下架菜品，请刷新购物车");
            }
            if (dish.getStock() != null && dish.getStock() > 0 && cartItem.getQuantity() > dish.getStock()) {
                throw new BusinessException(dish.getDishName() + " 库存不足");
            }
            dishAmount = dishAmount.add(cartItem.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            totalQuantity += cartItem.getQuantity();
        }

        BigDecimal deliveryFee = merchant.getDeliveryFee() == null ? BigDecimal.ZERO : merchant.getDeliveryFee();
        BigDecimal totalAmount = dishAmount.add(deliveryFee);
        if (user.getBalance().compareTo(totalAmount) < 0) {
            throw new BusinessException("余额不足，请先充值");
        }

        Orders order = new Orders();
        order.setOrderNo(generateOrderNo());
        order.setPickupNo(generatePickupNo(request.getMerchantId()));
        order.setUserId(userId);
        order.setMerchantId(merchant.getId());
        order.setMerchantName(merchant.getMerchantName());
        order.setDishAmount(dishAmount);
        order.setDeliveryFee(deliveryFee);
        order.setDiscountAmount(BigDecimal.ZERO);
        order.setTotalAmount(totalAmount);
        order.setPayAmount(totalAmount);
        order.setPickupType(request.getPickupType() == null ? 1 : request.getPickupType());
        order.setPickupTime(request.getPickupTime());
        order.setUserRemark(request.getUserRemark());
        order.setStatus(1);
        order.setPayTime(LocalDateTime.now());
        ordersMapper.insert(order);

        for (Cart cartItem : cartItems) {
            Dish dish = dishMapper.selectById(cartItem.getDishId());

            OrderDetail detail = new OrderDetail();
            detail.setOrderId(order.getId());
            detail.setOrderNo(order.getOrderNo());
            detail.setDishId(cartItem.getDishId());
            detail.setDishName(cartItem.getDishName());
            detail.setDishImage(cartItem.getDishImage());
            detail.setPrice(cartItem.getPrice());
            detail.setQuantity(cartItem.getQuantity());
            detail.setAmount(cartItem.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            detail.setSpecs(cartItem.getSpecs());
            orderDetailMapper.insert(detail);

            if (dish.getStock() != null && dish.getStock() > 0) {
                dish.setStock(dish.getStock() - cartItem.getQuantity());
            }
            dish.setSales((dish.getSales() == null ? 0 : dish.getSales()) + cartItem.getQuantity());
            dish.setMonthSales((dish.getMonthSales() == null ? 0 : dish.getMonthSales()) + cartItem.getQuantity());
            dishMapper.updateById(dish);
        }

        user.setBalance(user.getBalance().subtract(totalAmount));
        userMapper.updateById(user);

        merchant.setTotalSales((merchant.getTotalSales() == null ? 0 : merchant.getTotalSales()) + totalQuantity);
        merchant.setMonthSales((merchant.getMonthSales() == null ? 0 : merchant.getMonthSales()) + totalQuantity);
        merchantMapper.updateById(merchant);

        cartService.clearMerchantCart(userId, request.getMerchantId());
        return order;
    }

    public List<Orders> getUserOrders(Long userId, Integer status) {
        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Orders::getUserId, userId);
        if (status != null) {
            wrapper.eq(Orders::getStatus, status);
        }
        wrapper.orderByDesc(Orders::getCreateTime).orderByDesc(Orders::getId);
        return ordersMapper.selectList(wrapper);
    }

    public Map<String, Object> getUserOrderDetail(Long userId, Long orderId) {
        Orders order = requireUserOrder(userId, orderId);
        LambdaQueryWrapper<OrderDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderDetail::getOrderId, orderId).orderByAsc(OrderDetail::getId);
        List<OrderDetail> details = orderDetailMapper.selectList(wrapper);

        Map<String, Object> data = new HashMap<>();
        data.put("order", order);
        data.put("details", details);
        return data;
    }

    @Transactional
    public void cancelUserOrder(Long userId, Long orderId) {
        Orders order = requireUserOrder(userId, orderId);
        if (order.getStatus() != 1) {
            throw new BusinessException("当前订单状态不可取消");
        }

        order.setStatus(5);
        order.setCancelTime(LocalDateTime.now());
        order.setCancelReason("用户取消");
        ordersMapper.updateById(order);

        restoreInventoryAndSales(orderId, order.getMerchantId());

        User user = userMapper.selectById(userId);
        user.setBalance(user.getBalance().add(order.getPayAmount()));
        userMapper.updateById(user);
    }

    @Transactional
    public void confirmPickup(Long userId, Long orderId) {
        Orders order = requireUserOrder(userId, orderId);
        if (order.getStatus() != 3) {
            throw new BusinessException("当前订单还不能确认取餐");
        }
        order.setStatus(4);
        order.setFinishTime(LocalDateTime.now());
        ordersMapper.updateById(order);
    }

    public List<Orders> getMerchantOrders(Long merchantId, Integer status) {
        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Orders::getMerchantId, merchantId);
        if (status != null) {
            wrapper.eq(Orders::getStatus, status);
        }
        wrapper.orderByDesc(Orders::getCreateTime).orderByDesc(Orders::getId);
        return ordersMapper.selectList(wrapper);
    }

    @Transactional
    public void acceptOrder(Long merchantId, Long orderId) {
        Orders order = requireMerchantOrder(merchantId, orderId);
        if (order.getStatus() != 1) {
            throw new BusinessException("当前订单不能接单");
        }
        order.setStatus(2);
        order.setAcceptTime(LocalDateTime.now());
        ordersMapper.updateById(order);
    }

    @Transactional
    public void readyOrder(Long merchantId, Long orderId) {
        Orders order = requireMerchantOrder(merchantId, orderId);
        if (order.getStatus() != 2) {
            throw new BusinessException("当前订单不能标记为待取餐");
        }
        order.setStatus(3);
        ordersMapper.updateById(order);
    }

    private Orders requireUserOrder(Long userId, Long orderId) {
        Orders order = ordersMapper.selectById(orderId);
        if (order == null || !order.getUserId().equals(userId)) {
            throw new BusinessException("订单不存在");
        }
        return order;
    }

    private Orders requireMerchantOrder(Long merchantId, Long orderId) {
        Orders order = ordersMapper.selectById(orderId);
        if (order == null || !order.getMerchantId().equals(merchantId)) {
            throw new BusinessException("订单不存在");
        }
        return order;
    }

    private void restoreInventoryAndSales(Long orderId, Long merchantId) {
        LambdaQueryWrapper<OrderDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderDetail::getOrderId, orderId);
        List<OrderDetail> details = orderDetailMapper.selectList(wrapper);

        int quantity = 0;
        for (OrderDetail detail : details) {
            Dish dish = dishMapper.selectById(detail.getDishId());
            if (dish == null) {
                continue;
            }
            if (dish.getStock() != null && dish.getStock() > 0) {
                dish.setStock(dish.getStock() + detail.getQuantity());
            }
            dish.setSales(Math.max(0, (dish.getSales() == null ? 0 : dish.getSales()) - detail.getQuantity()));
            dish.setMonthSales(Math.max(0, (dish.getMonthSales() == null ? 0 : dish.getMonthSales()) - detail.getQuantity()));
            dishMapper.updateById(dish);
            quantity += detail.getQuantity();
        }

        Merchant merchant = merchantMapper.selectById(merchantId);
        if (merchant != null) {
            merchant.setTotalSales(Math.max(0, (merchant.getTotalSales() == null ? 0 : merchant.getTotalSales()) - quantity));
            merchant.setMonthSales(Math.max(0, (merchant.getMonthSales() == null ? 0 : merchant.getMonthSales()) - quantity));
            merchantMapper.updateById(merchant);
        }
    }

    private String generateOrderNo() {
        return DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS").format(LocalDateTime.now())
                + ThreadLocalRandom.current().nextInt(100, 1000);
    }

    private String generatePickupNo(Long merchantId) {
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = start.plusDays(1);
        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Orders::getMerchantId, merchantId)
                .ge(Orders::getCreateTime, start)
                .lt(Orders::getCreateTime, end);
        long count = ordersMapper.selectCount(wrapper);
        return String.format("%03d", count + 1);
    }
}
