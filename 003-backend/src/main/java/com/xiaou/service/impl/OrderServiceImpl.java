package com.xiaou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.common.BusinessException;
import com.xiaou.entity.Order;
import com.xiaou.entity.OrderItem;
import com.xiaou.entity.Product;
import com.xiaou.mapper.OrderMapper;
import com.xiaou.service.OrderItemService;
import com.xiaou.service.OrderService;
import com.xiaou.service.ProductService;
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
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderItemService orderItemService;

    @Override
    @Transactional
    public Order createOrder(Long userId, List<Map<String, Object>> items, Map<String, String> shippingInfo) {
        BigDecimal totalPrice = BigDecimal.ZERO;

        Order order = new Order();
        order.setBuyerId(userId);
        order.setOrderNo(generateOrderNo());
        order.setStatus("pending");
        order.setShippingAddress(shippingInfo.get("address"));
        order.setReceiverName(shippingInfo.get("receiverName"));
        order.setReceiverPhone(shippingInfo.get("receiverPhone"));
        order.setRemark(shippingInfo.get("remark"));

        for (Map<String, Object> item : items) {
            Long productId = Long.valueOf(item.get("productId").toString());
            Integer quantity = Integer.valueOf(item.get("quantity").toString());

            Product product = productService.getById(productId);
            if (product == null) {
                throw new BusinessException("商品不存在：" + productId);
            }

            if (!"approved".equals(product.getStatus())) {
                throw new BusinessException("商品未审核通过：" + product.getName());
            }

            if (product.getStock() < quantity) {
                throw new BusinessException("库存不足：" + product.getName());
            }

            BigDecimal itemTotal = product.getPrice().multiply(new BigDecimal(quantity));
            totalPrice = totalPrice.add(itemTotal);

            product.setStock(product.getStock() - quantity);
            productService.updateById(product);
        }

        order.setTotalPrice(totalPrice);
        this.save(order);

        for (Map<String, Object> item : items) {
            Long productId = Long.valueOf(item.get("productId").toString());
            Integer quantity = Integer.valueOf(item.get("quantity").toString());
            Product product = productService.getById(productId);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(order.getId());
            orderItem.setProductId(productId);
            orderItem.setProductName(product.getName());
            orderItem.setProductCover(product.getCoverUrl());
            orderItem.setPrice(product.getPrice());
            orderItem.setQuantity(quantity);
            orderItem.setFarmerId(product.getFarmerId());
            orderItemService.save(orderItem);
        }

        return order;
    }

    @Override
    public void updateOrderStatus(Long orderId, String status, Long userId) {
        Order order = this.getById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }

        order.setStatus(status);
        this.updateById(order);
    }

    @Override
    public IPage<Order> getOrdersByBuyer(Long buyerId, Integer page, Integer size) {
        Page<Order> orderPage = new Page<>(page, size);
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Order::getBuyerId, buyerId);
        wrapper.orderByDesc(Order::getCreateTime);
        return this.page(orderPage, wrapper);
    }

    @Override
    public IPage<Order> getOrdersByFarmer(Long farmerId, Integer page, Integer size) {
        Page<Order> orderPage = new Page<>(page, size);
        List<Order> orders = baseMapper.selectList(null);
        
        LambdaQueryWrapper<OrderItem> itemWrapper = new LambdaQueryWrapper<>();
        itemWrapper.eq(OrderItem::getFarmerId, farmerId);
        List<OrderItem> farmerItems = orderItemService.list(itemWrapper);
        
        List<Long> orderIds = farmerItems.stream().map(OrderItem::getOrderId).distinct().toList();
        
        if (orderIds.isEmpty()) {
            return new Page<>(page, size);
        }
        
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Order::getId, orderIds);
        wrapper.orderByDesc(Order::getCreateTime);
        return this.page(orderPage, wrapper);
    }

    @Override
    public Map<String, Object> getOrderDetail(Long orderId, Long userId) {
        Order order = this.getById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }

        LambdaQueryWrapper<OrderItem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderItem::getOrderId, orderId);
        List<OrderItem> items = orderItemService.list(wrapper);

        Map<String, Object> result = new HashMap<>();
        result.put("order", order);
        result.put("items", items);
        return result;
    }

    private String generateOrderNo() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String random = String.valueOf((int)(Math.random() * 10000));
        return "ORD" + timestamp + String.format("%04d", Integer.parseInt(random));
    }
}

