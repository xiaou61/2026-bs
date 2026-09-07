package com.game.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.game.common.BusinessException;
import com.game.entity.Complaint;
import com.game.entity.GameItem;
import com.game.entity.TradeOrder;
import com.game.entity.User;
import com.game.mapper.ComplaintMapper;
import com.game.mapper.GameItemMapper;
import com.game.mapper.TradeOrderMapper;
import com.game.mapper.UserMapper;
import com.game.vo.ComplaintVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ComplaintService {

    @Resource
    private ComplaintMapper complaintMapper;

    @Resource
    private TradeOrderMapper orderMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private GameItemMapper gameItemMapper;

    public Page<ComplaintVO> page(Integer pageNum, Integer pageSize, Integer status, String orderNo) {
        QueryWrapper<Complaint> wrapper = new QueryWrapper<>();
        if (status != null) {
            wrapper.eq("status", status);
        }
        appendOrderNoFilter(wrapper, orderNo);
        wrapper.orderByDesc("id");
        Page<Complaint> page = complaintMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return convertComplaintPage(page);
    }

    public Page<ComplaintVO> myPage(Long userId, Integer pageNum, Integer pageSize, Integer status, String orderNo) {
        QueryWrapper<Complaint> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        if (status != null) {
            wrapper.eq("status", status);
        }
        appendOrderNoFilter(wrapper, orderNo);
        wrapper.orderByDesc("id");
        Page<Complaint> page = complaintMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return convertComplaintPage(page);
    }

    @Transactional(rollbackFor = Exception.class)
    public void add(Complaint complaint, Long userId) {
        if (complaint == null || complaint.getOrderId() == null) {
            throw new BusinessException("请选择关联订单");
        }
        if (complaint.getContent() == null || complaint.getContent().trim().isEmpty()) {
            throw new BusinessException("申诉内容不能为空");
        }
        complaint.setContent(complaint.getContent().trim());
        if (complaint.getContent().length() > 1000) {
            throw new BusinessException("申诉内容不能超过1000字符");
        }
        if (StrUtil.isBlank(complaint.getType())) {
            complaint.setType("ORDER");
        } else {
            complaint.setType(complaint.getType().trim());
        }
        if (complaint.getType().length() > 30) {
            throw new BusinessException("申诉类型长度不能超过30字符");
        }
        if (complaint.getImages() != null) {
            complaint.setImages(complaint.getImages().trim());
            if (complaint.getImages().length() > 500) {
                throw new BusinessException("图片地址长度不能超过500字符");
            }
        }
        TradeOrder order = orderMapper.selectById(complaint.getOrderId());
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        if (!order.getUserId().equals(userId)) {
            throw new BusinessException("只能申诉自己的订单");
        }
        if (order.getStatus() == null || (order.getStatus() != 1 && order.getStatus() != 2 && order.getStatus() != 3)) {
            throw new BusinessException("当前订单状态不可申诉");
        }
        Long processingCount = complaintMapper.selectCount(new QueryWrapper<Complaint>()
                .eq("order_id", complaint.getOrderId())
                .eq("status", 0));
        if (processingCount != null && processingCount > 0) {
            throw new BusinessException("该订单已有待处理申诉");
        }
        complaint.setUserId(userId);
        complaint.setTargetUserId(order.getSellerId());
        complaint.setOrderStatusSnapshot(order.getStatus());
        complaint.setStatus(0);
        complaintMapper.insert(complaint);
        order.setStatus(5);
        orderMapper.updateById(order);
    }

    @Transactional(rollbackFor = Exception.class)
    public void reply(Long id, String reply, Long adminId) {
        if (reply == null || reply.trim().isEmpty()) {
            throw new BusinessException("回复内容不能为空");
        }
        reply = reply.trim();
        if (reply.length() > 1000) {
            throw new BusinessException("回复内容不能超过1000字符");
        }
        Complaint complaint = complaintMapper.selectById(id);
        if (complaint == null) {
            throw new BusinessException("申诉不存在");
        }
        if (complaint.getStatus() == null || complaint.getStatus() != 0) {
            throw new BusinessException("该申诉已处理");
        }
        complaint.setReply(reply);
        complaint.setReplyAdminId(adminId);
        complaint.setReplyTime(LocalDateTime.now());
        complaint.setStatus(1);
        complaintMapper.updateById(complaint);
        TradeOrder order = orderMapper.selectById(complaint.getOrderId());
        if (order != null && order.getStatus() == 5 && complaint.getOrderStatusSnapshot() != null) {
            order.setStatus(complaint.getOrderStatusSnapshot());
            orderMapper.updateById(order);
        }
    }

    private Page<ComplaintVO> convertComplaintPage(Page<Complaint> page) {
        Page<ComplaintVO> voPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        if (page.getRecords() == null || page.getRecords().isEmpty()) {
            voPage.setRecords(new ArrayList<>());
            return voPage;
        }
        Set<Long> userIds = new HashSet<>();
        Set<Long> orderIds = new HashSet<>();
        for (Complaint complaint : page.getRecords()) {
            if (complaint.getUserId() != null) {
                userIds.add(complaint.getUserId());
            }
            if (complaint.getTargetUserId() != null) {
                userIds.add(complaint.getTargetUserId());
            }
            if (complaint.getOrderId() != null) {
                orderIds.add(complaint.getOrderId());
            }
        }
        Map<Long, User> userMap = new HashMap<>();
        if (!userIds.isEmpty()) {
            List<User> users = userMapper.selectBatchIds(userIds);
            for (User user : users) {
                userMap.put(user.getId(), user);
            }
        }
        Map<Long, TradeOrder> orderMap = new HashMap<>();
        Set<Long> itemIds = new HashSet<>();
        if (!orderIds.isEmpty()) {
            List<TradeOrder> orders = orderMapper.selectBatchIds(orderIds);
            for (TradeOrder order : orders) {
                orderMap.put(order.getId(), order);
                if (order.getItemId() != null) {
                    itemIds.add(order.getItemId());
                }
            }
        }
        Map<Long, GameItem> itemMap = new HashMap<>();
        if (!itemIds.isEmpty()) {
            List<GameItem> items = gameItemMapper.selectBatchIds(itemIds);
            for (GameItem item : items) {
                itemMap.put(item.getId(), item);
            }
        }
        List<ComplaintVO> records = new ArrayList<>();
        for (Complaint complaint : page.getRecords()) {
            ComplaintVO vo = new ComplaintVO();
            BeanUtils.copyProperties(complaint, vo);
            User user = userMap.get(complaint.getUserId());
            User targetUser = userMap.get(complaint.getTargetUserId());
            vo.setUserName(user == null ? "未知用户" : (user.getNickname() == null || user.getNickname().trim().isEmpty() ? user.getUsername() : user.getNickname()));
            vo.setTargetUserName(targetUser == null ? "未知用户" : (targetUser.getNickname() == null || targetUser.getNickname().trim().isEmpty() ? targetUser.getUsername() : targetUser.getNickname()));
            TradeOrder order = orderMap.get(complaint.getOrderId());
            if (order != null) {
                vo.setOrderNo(order.getOrderNo());
                GameItem item = itemMap.get(order.getItemId());
                vo.setItemTitle(item == null ? "商品已删除" : item.getTitle());
            } else {
                vo.setOrderNo("订单已删除");
                vo.setItemTitle("商品已删除");
            }
            records.add(vo);
        }
        voPage.setRecords(records);
        return voPage;
    }

    private void appendOrderNoFilter(QueryWrapper<Complaint> wrapper, String orderNo) {
        if (StrUtil.isBlank(orderNo)) {
            return;
        }
        List<TradeOrder> orders = orderMapper.selectList(new QueryWrapper<TradeOrder>()
                .select("id")
                .like("order_no", orderNo.trim()));
        if (orders == null || orders.isEmpty()) {
            wrapper.eq("order_id", -1L);
            return;
        }
        List<Long> orderIds = orders.stream().map(TradeOrder::getId).collect(Collectors.toList());
        wrapper.in("order_id", orderIds);
    }
}
