package com.game.service;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.game.common.BusinessException;
import com.game.entity.GameItem;
import com.game.entity.TradeOrder;
import com.game.entity.User;
import com.game.mapper.GameItemMapper;
import com.game.mapper.TradeOrderMapper;
import com.game.mapper.UserMapper;
import com.game.vo.TradeOrderVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class TradeOrderService {

    @Resource
    private TradeOrderMapper orderMapper;

    @Resource
    private GameItemService itemService;

    @Resource
    private UserService userService;

    @Resource
    private UserMapper userMapper;

    @Resource
    private GameItemMapper gameItemMapper;

    @Transactional(rollbackFor = Exception.class)
    public TradeOrder create(Long userId, Long itemId, Integer quantity, String remark) {
        if (quantity == null || quantity <= 0) {
            throw new BusinessException("数量必须大于0");
        }
        if (remark != null) {
            remark = remark.trim();
            if (remark.length() > 255) {
                throw new BusinessException("备注长度不能超过255字符");
            }
        }
        GameItem item = itemService.mustGetById(itemId);
        if (item.getStatus() == null || item.getStatus() != 1) {
            throw new BusinessException("商品已下架");
        }
        if (item.getSellerId().equals(userId)) {
            throw new BusinessException("不能购买自己的商品");
        }
        int stock = item.getStock() == null ? 0 : item.getStock();
        if (stock < quantity) {
            throw new BusinessException("库存不足");
        }
        TradeOrder order = new TradeOrder();
        order.setOrderNo("GT" + IdUtil.getSnowflakeNextIdStr());
        order.setUserId(userId);
        order.setItemId(itemId);
        order.setSellerId(item.getSellerId());
        order.setQuantity(quantity);
        order.setTotalPrice(item.getPrice().multiply(new BigDecimal(quantity)));
        order.setStatus(0);
        order.setRemark(remark);
        orderMapper.insert(order);
        return order;
    }

    public Page<TradeOrderVO> page(Integer pageNum, Integer pageSize, String orderNo, Integer status, Long userId) {
        QueryWrapper<TradeOrder> wrapper = new QueryWrapper<>();
        if (orderNo != null && !orderNo.trim().isEmpty()) {
            wrapper.like("order_no", orderNo.trim());
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        if (userId != null) {
            wrapper.eq("user_id", userId);
        }
        wrapper.orderByDesc("id");
        Page<TradeOrder> orderPage = orderMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return convertOrderPage(orderPage);
    }

    public Page<TradeOrderVO> myOrders(Long userId, Integer pageNum, Integer pageSize, Integer status, String orderNo) {
        QueryWrapper<TradeOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        if (status != null) {
            wrapper.eq("status", status);
        }
        if (orderNo != null && !orderNo.trim().isEmpty()) {
            wrapper.like("order_no", orderNo.trim());
        }
        wrapper.orderByDesc("id");
        Page<TradeOrder> orderPage = orderMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return convertOrderPage(orderPage);
    }

    public Page<TradeOrderVO> saleOrders(Long sellerId, Integer pageNum, Integer pageSize, Integer status, String orderNo) {
        QueryWrapper<TradeOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("seller_id", sellerId);
        if (status != null) {
            wrapper.eq("status", status);
        }
        if (orderNo != null && !orderNo.trim().isEmpty()) {
            wrapper.like("order_no", orderNo.trim());
        }
        wrapper.orderByDesc("id");
        Page<TradeOrder> orderPage = orderMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return convertOrderPage(orderPage);
    }

    @Transactional(rollbackFor = Exception.class)
    public void pay(Long id, Long userId) {
        TradeOrder order = mustGetById(id);
        if (!order.getUserId().equals(userId)) {
            throw new BusinessException("无权限操作该订单");
        }
        if (order.getStatus() != 0) {
            throw new BusinessException("订单状态不支持支付");
        }
        itemService.decreaseStockAndIncreaseSold(order.getItemId(), order.getQuantity());
        userService.updateBalance(order.getUserId(), order.getTotalPrice().negate());
        userService.updateBalance(order.getSellerId(), order.getTotalPrice());
        order.setStatus(1);
        order.setPayTime(LocalDateTime.now());
        orderMapper.updateById(order);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deliver(Long id, Long operatorId, String role) {
        TradeOrder order = mustGetById(id);
        if (!"ADMIN".equals(role) && !order.getSellerId().equals(operatorId)) {
            throw new BusinessException("无权限发货");
        }
        if (order.getStatus() != 1) {
            throw new BusinessException("仅已支付订单可发货");
        }
        order.setStatus(2);
        orderMapper.updateById(order);
    }

    @Transactional(rollbackFor = Exception.class)
    public void complete(Long id, Long userId) {
        TradeOrder order = mustGetById(id);
        if (!order.getUserId().equals(userId)) {
            throw new BusinessException("无权限操作该订单");
        }
        if (order.getStatus() != 2) {
            throw new BusinessException("仅已发货订单可完成");
        }
        order.setStatus(3);
        order.setFinishTime(LocalDateTime.now());
        orderMapper.updateById(order);
    }

    @Transactional(rollbackFor = Exception.class)
    public void cancel(Long id, Long operatorId, String role) {
        TradeOrder order = mustGetById(id);
        boolean isAdmin = "ADMIN".equals(role);
        if (!isAdmin && !order.getUserId().equals(operatorId)) {
            throw new BusinessException("无权限取消该订单");
        }
        if (order.getStatus() == 3 || order.getStatus() == 4) {
            throw new BusinessException("订单不可取消");
        }
        if (order.getStatus() == 5) {
            throw new BusinessException("订单申诉处理中，请先处理申诉");
        }
        if (!isAdmin && order.getStatus() == 2) {
            throw new BusinessException("订单已发货，请走申诉流程");
        }
        if (order.getStatus() == 1 || order.getStatus() == 2) {
            itemService.restoreStockAndSold(order.getItemId(), order.getQuantity());
            userService.updateBalance(order.getSellerId(), order.getTotalPrice().negate());
            userService.updateBalance(order.getUserId(), order.getTotalPrice());
        }
        order.setStatus(4);
        orderMapper.updateById(order);
    }

    public TradeOrder mustGetById(Long id) {
        TradeOrder order = orderMapper.selectById(id);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        return order;
    }

    private Page<TradeOrderVO> convertOrderPage(Page<TradeOrder> orderPage) {
        Page<TradeOrderVO> voPage = new Page<>(orderPage.getCurrent(), orderPage.getSize(), orderPage.getTotal());
        if (orderPage.getRecords() == null || orderPage.getRecords().isEmpty()) {
            voPage.setRecords(new ArrayList<>());
            return voPage;
        }
        Set<Long> userIds = new HashSet<>();
        Set<Long> itemIds = new HashSet<>();
        for (TradeOrder order : orderPage.getRecords()) {
            if (order.getUserId() != null) {
                userIds.add(order.getUserId());
            }
            if (order.getSellerId() != null) {
                userIds.add(order.getSellerId());
            }
            if (order.getItemId() != null) {
                itemIds.add(order.getItemId());
            }
        }
        Map<Long, User> userMap = new HashMap<>();
        if (!userIds.isEmpty()) {
            List<User> users = userMapper.selectBatchIds(userIds);
            for (User user : users) {
                userMap.put(user.getId(), user);
            }
        }
        Map<Long, GameItem> itemMap = new HashMap<>();
        if (!itemIds.isEmpty()) {
            List<GameItem> items = gameItemMapper.selectBatchIds(itemIds);
            for (GameItem item : items) {
                itemMap.put(item.getId(), item);
            }
        }
        List<TradeOrderVO> records = new ArrayList<>();
        for (TradeOrder order : orderPage.getRecords()) {
            TradeOrderVO vo = new TradeOrderVO();
            BeanUtils.copyProperties(order, vo);
            User buyer = userMap.get(order.getUserId());
            User seller = userMap.get(order.getSellerId());
            GameItem item = itemMap.get(order.getItemId());
            vo.setBuyerName(buyer == null ? "未知用户" : (buyer.getNickname() == null || buyer.getNickname().trim().isEmpty() ? buyer.getUsername() : buyer.getNickname()));
            vo.setSellerName(seller == null ? "未知用户" : (seller.getNickname() == null || seller.getNickname().trim().isEmpty() ? seller.getUsername() : seller.getNickname()));
            vo.setItemTitle(item == null ? "商品已删除" : item.getTitle());
            records.add(vo);
        }
        voPage.setRecords(records);
        return voPage;
    }
}
