package com.groupbuy.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.groupbuy.common.BusinessException;
import com.groupbuy.entity.*;
import com.groupbuy.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GroupOrderService {

    @Autowired
    private GroupOrderMapper groupOrderMapper;

    @Autowired
    private GroupActivityMapper groupActivityMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private UserMapper userMapper;

    @Transactional
    public Map<String, Long> create(Long userId, Long activityId, Long addressId) {
        GroupActivity activity = groupActivityMapper.selectById(activityId);
        if (activity == null || activity.getStatus() != 1) {
            throw new BusinessException("团购活动不存在或已结束");
        }
        Product product = productMapper.selectById(activity.getProductId());
        if (product.getStock() <= 0) {
            throw new BusinessException("商品库存不足");
        }
        Address address = addressMapper.selectById(addressId);
        if (address == null) {
            throw new BusinessException("收货地址不存在");
        }
        GroupOrder groupOrder = new GroupOrder();
        groupOrder.setActivityId(activityId);
        groupOrder.setLeaderId(userId);
        groupOrder.setCurrentCount(1);
        groupOrder.setTargetCount(activity.getMinCount());
        groupOrder.setStatus(0);
        groupOrder.setExpireTime(LocalDateTime.now().plusHours(activity.getLimitHours()));
        groupOrderMapper.insert(groupOrder);
        Orders order = createOrder(userId, activity, product, address, groupOrder.getId());
        Map<String, Long> result = new HashMap<>();
        result.put("groupOrderId", groupOrder.getId());
        result.put("orderId", order.getId());
        return result;
    }

    @Transactional
    public Long join(Long userId, Long groupOrderId, Long addressId) {
        GroupOrder groupOrder = groupOrderMapper.selectById(groupOrderId);
        if (groupOrder == null || groupOrder.getStatus() != 0) {
            throw new BusinessException("拼团不存在或已结束");
        }
        if (groupOrder.getExpireTime().isBefore(LocalDateTime.now())) {
            throw new BusinessException("拼团已过期");
        }
        QueryWrapper<Orders> checkWrapper = new QueryWrapper<>();
        checkWrapper.eq("user_id", userId).eq("group_order_id", groupOrderId);
        if (ordersMapper.selectCount(checkWrapper) > 0) {
            throw new BusinessException("您已参与此拼团");
        }
        GroupActivity activity = groupActivityMapper.selectById(groupOrder.getActivityId());
        Product product = productMapper.selectById(activity.getProductId());
        if (product.getStock() <= 0) {
            throw new BusinessException("商品库存不足");
        }
        Address address = addressMapper.selectById(addressId);
        if (address == null) {
            throw new BusinessException("收货地址不存在");
        }
        Orders order = createOrder(userId, activity, product, address, groupOrderId);
        groupOrder.setCurrentCount(groupOrder.getCurrentCount() + 1);
        if (groupOrder.getCurrentCount() >= groupOrder.getTargetCount()) {
            groupOrder.setStatus(1);
        }
        groupOrderMapper.updateById(groupOrder);
        return order.getId();
    }

    private Orders createOrder(Long userId, GroupActivity activity, Product product, Address address, Long groupOrderId) {
        Orders order = new Orders();
        order.setOrderNo(generateOrderNo());
        order.setUserId(userId);
        order.setMerchantId(activity.getMerchantId());
        order.setGroupOrderId(groupOrderId);
        order.setTotalAmount(activity.getGroupPrice());
        order.setPayAmount(activity.getGroupPrice());
        Map<String, String> addrInfo = new HashMap<>();
        addrInfo.put("name", address.getName());
        addrInfo.put("phone", address.getPhone());
        addrInfo.put("address", address.getProvince() + address.getCity() + address.getDistrict() + address.getDetail());
        order.setAddressInfo(JSON.toJSONString(addrInfo));
        order.setStatus(0);
        ordersMapper.insert(order);
        OrderItem item = new OrderItem();
        item.setOrderId(order.getId());
        item.setProductId(product.getId());
        item.setProductName(product.getName());
        item.setProductCover(product.getCover());
        item.setPrice(activity.getGroupPrice());
        item.setQuantity(1);
        item.setSubtotal(activity.getGroupPrice());
        orderItemMapper.insert(item);
        return order;
    }

    private String generateOrderNo() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + 
               String.format("%04d", (int)(Math.random() * 10000));
    }

    public GroupOrder detail(Long id) {
        GroupOrder groupOrder = groupOrderMapper.selectById(id);
        if (groupOrder != null) {
            fillGroupOrderInfo(groupOrder);
        }
        return groupOrder;
    }

    public Page<GroupOrder> myGroups(Long userId, Integer pageNum, Integer pageSize, Integer status) {
        Page<GroupOrder> page = new Page<>(pageNum, pageSize);
        QueryWrapper<GroupOrder> wrapper = new QueryWrapper<>();
        wrapper.inSql("id", "SELECT group_order_id FROM orders WHERE user_id = " + userId);
        if (status != null) {
            wrapper.eq("status", status);
        }
        wrapper.orderByDesc("create_time");
        Page<GroupOrder> result = groupOrderMapper.selectPage(page, wrapper);
        result.getRecords().forEach(this::fillGroupOrderInfo);
        return result;
    }

    public List<GroupOrder> ongoing(Long activityId) {
        QueryWrapper<GroupOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("activity_id", activityId)
               .eq("status", 0)
               .gt("expire_time", LocalDateTime.now())
               .orderByDesc("create_time")
               .last("LIMIT 10");
        List<GroupOrder> list = groupOrderMapper.selectList(wrapper);
        list.forEach(this::fillGroupOrderInfo);
        return list;
    }

    private void fillGroupOrderInfo(GroupOrder groupOrder) {
        GroupActivity activity = groupActivityMapper.selectById(groupOrder.getActivityId());
        if (activity != null) {
            Product product = productMapper.selectById(activity.getProductId());
            activity.setProduct(product);
        }
        groupOrder.setActivity(activity);
        User leader = userMapper.selectById(groupOrder.getLeaderId());
        if (leader != null) {
            groupOrder.setLeaderName(leader.getNickname());
            groupOrder.setLeaderAvatar(leader.getAvatar());
        }
    }

    @Scheduled(fixedRate = 60000)
    public void checkExpiredGroups() {
        QueryWrapper<GroupOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 0).le("expire_time", LocalDateTime.now());
        groupOrderMapper.selectList(wrapper).forEach(go -> {
            go.setStatus(2);
            groupOrderMapper.updateById(go);
            QueryWrapper<Orders> orderWrapper = new QueryWrapper<>();
            orderWrapper.eq("group_order_id", go.getId()).eq("status", 0);
            ordersMapper.selectList(orderWrapper).forEach(o -> {
                o.setStatus(4);
                ordersMapper.updateById(o);
            });
        });
    }
}
