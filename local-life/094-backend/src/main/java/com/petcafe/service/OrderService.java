package com.petcafe.service;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.petcafe.common.BusinessException;
import com.petcafe.common.PageResult;
import com.petcafe.dto.OrderCreateDTO;
import com.petcafe.entity.CafeShop;
import com.petcafe.entity.ConsumeOrder;
import com.petcafe.entity.MenuItem;
import com.petcafe.entity.User;
import com.petcafe.mapper.CafeShopMapper;
import com.petcafe.mapper.ConsumeOrderMapper;
import com.petcafe.mapper.MenuItemMapper;
import com.petcafe.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService {

    public static final String STATUS_WAIT_PAY = "WAIT_PAY";
    public static final String STATUS_PAID = "PAID";
    public static final String STATUS_COMPLETED = "COMPLETED";
    public static final String STATUS_CANCELED = "CANCELED";

    @Resource
    private ConsumeOrderMapper orderMapper;

    @Resource
    private MenuItemMapper menuMapper;

    @Resource
    private CafeShopMapper shopMapper;

    @Resource
    private UserMapper userMapper;

    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> create(Long userId, OrderCreateDTO dto) {
        if (dto == null || dto.getShopId() == null || dto.getMenuId() == null || dto.getQuantity() == null || dto.getQuantity() <= 0) {
            throw new BusinessException("订单参数不完整");
        }
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        CafeShop shop = shopMapper.selectById(dto.getShopId());
        if (shop == null || !"OPEN".equalsIgnoreCase(shop.getStatus())) {
            throw new BusinessException("门店不可下单");
        }
        MenuItem menu = menuMapper.selectById(dto.getMenuId());
        if (menu == null || menu.getStatus() == null || menu.getStatus() == 0) {
            throw new BusinessException("菜单不可下单");
        }
        int stock = menu.getStock() == null ? 0 : menu.getStock();
        if (stock < dto.getQuantity()) {
            throw new BusinessException("菜单库存不足");
        }
        BigDecimal unitPrice = menu.getPrice() == null ? BigDecimal.ZERO : menu.getPrice();
        BigDecimal totalAmount = unitPrice.multiply(BigDecimal.valueOf(dto.getQuantity()));
        ConsumeOrder order = new ConsumeOrder();
        order.setOrderNo("CO" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + RandomUtil.randomNumbers(4));
        order.setUserId(userId);
        order.setShopId(dto.getShopId());
        order.setMenuId(dto.getMenuId());
        order.setQuantity(dto.getQuantity());
        order.setDineType(StringUtils.hasText(dto.getDineType()) ? dto.getDineType().trim().toUpperCase() : "IN_STORE");
        order.setTotalAmount(totalAmount);
        order.setPayAmount(totalAmount);
        order.setStatus(STATUS_WAIT_PAY);
        order.setRemark(dto.getRemark());
        orderMapper.insert(order);
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", order.getId());
        map.put("orderNo", order.getOrderNo());
        map.put("payAmount", order.getPayAmount());
        return map;
    }

    public PageResult<ConsumeOrder> page(Integer pageNum, Integer pageSize, String orderNo, String status, Long userId, String role) {
        Page<ConsumeOrder> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<ConsumeOrder> wrapper = new LambdaQueryWrapper<ConsumeOrder>()
                .like(StringUtils.hasText(orderNo), ConsumeOrder::getOrderNo, orderNo == null ? null : orderNo.trim())
                .eq(StringUtils.hasText(status), ConsumeOrder::getStatus, status == null ? null : status.trim().toUpperCase())
                .eq("CUSTOMER".equalsIgnoreCase(role), ConsumeOrder::getUserId, userId)
                .orderByDesc(ConsumeOrder::getId);
        Page<ConsumeOrder> resultPage = orderMapper.selectPage(page, wrapper);
        fillDetail(resultPage.getRecords());
        PageResult<ConsumeOrder> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    public ConsumeOrder getById(Long id, Long userId, String role) {
        ConsumeOrder order = orderMapper.selectById(id);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        if ("CUSTOMER".equalsIgnoreCase(role) && !order.getUserId().equals(userId)) {
            throw new BusinessException(403, "无权限");
        }
        fillDetail(java.util.Collections.singletonList(order));
        return order;
    }

    public ConsumeOrder getPayableOrder(Long userId, Long orderId) {
        ConsumeOrder order = orderMapper.selectById(orderId);
        if (order == null || !order.getUserId().equals(userId)) {
            throw new BusinessException("订单不存在");
        }
        if (!STATUS_WAIT_PAY.equals(order.getStatus())) {
            throw new BusinessException("订单状态不可支付");
        }
        return order;
    }

    public void cancel(Long id, Long userId, String role) {
        ConsumeOrder order = getById(id, userId, role);
        if (!STATUS_WAIT_PAY.equals(order.getStatus())) {
            throw new BusinessException("当前状态不可取消");
        }
        order.setStatus(STATUS_CANCELED);
        orderMapper.updateById(order);
    }

    public void markPaid(Long orderId) {
        ConsumeOrder order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        order.setStatus(STATUS_PAID);
        order.setPayTime(LocalDateTime.now());
        orderMapper.updateById(order);
    }

    public void markCompleted(Long orderId) {
        ConsumeOrder order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        order.setStatus(STATUS_COMPLETED);
        orderMapper.updateById(order);
    }

    public Long countAll() {
        return orderMapper.selectCount(null);
    }

    public Long countTodayOrders() {
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = LocalDate.now().plusDays(1).atStartOfDay();
        return orderMapper.selectCount(new LambdaQueryWrapper<ConsumeOrder>()
                .ge(ConsumeOrder::getCreateTime, start)
                .lt(ConsumeOrder::getCreateTime, end));
    }

    public List<ConsumeOrder> listFinishedOrders() {
        List<ConsumeOrder> list = orderMapper.selectList(new LambdaQueryWrapper<ConsumeOrder>()
                .in(ConsumeOrder::getStatus, STATUS_PAID, STATUS_COMPLETED)
                .orderByDesc(ConsumeOrder::getPayTime));
        fillDetail(list);
        return list;
    }

    private void fillDetail(List<ConsumeOrder> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        Map<Long, User> userMap = userMapper.selectList(new LambdaQueryWrapper<User>()
                        .in(User::getId, list.stream().map(ConsumeOrder::getUserId).distinct().collect(Collectors.toList())))
                .stream()
                .collect(Collectors.toMap(User::getId, item -> item, (a, b) -> a, HashMap::new));
        Map<Long, String> shopMap = shopMapper.selectList(new LambdaQueryWrapper<CafeShop>()
                        .in(CafeShop::getId, list.stream().map(ConsumeOrder::getShopId).distinct().collect(Collectors.toList())))
                .stream()
                .collect(Collectors.toMap(CafeShop::getId, CafeShop::getName, (a, b) -> a, HashMap::new));
        Map<Long, String> menuMap = menuMapper.selectList(new LambdaQueryWrapper<MenuItem>()
                        .in(MenuItem::getId, list.stream().map(ConsumeOrder::getMenuId).distinct().collect(Collectors.toList())))
                .stream()
                .collect(Collectors.toMap(MenuItem::getId, MenuItem::getName, (a, b) -> a, HashMap::new));
        list.forEach(item -> {
            User user = userMap.get(item.getUserId());
            item.setUsername(user == null ? null : user.getUsername());
            item.setNickname(user == null ? null : user.getNickname());
            item.setShopName(shopMap.get(item.getShopId()));
            item.setMenuName(menuMap.get(item.getMenuId()));
        });
    }
}
