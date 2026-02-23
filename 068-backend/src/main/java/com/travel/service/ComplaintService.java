package com.travel.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.common.BusinessException;
import com.travel.common.PageResult;
import com.travel.entity.TravelOrder;
import com.travel.entity.User;
import com.travel.entity.UserComplaint;
import com.travel.mapper.TravelOrderMapper;
import com.travel.mapper.UserComplaintMapper;
import com.travel.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ComplaintService {

    public static final String STATUS_WAITING = "WAITING";
    public static final String STATUS_HANDLED = "HANDLED";

    @Resource
    private UserComplaintMapper userComplaintMapper;

    @Resource
    private TravelOrderMapper travelOrderMapper;

    @Resource
    private UserMapper userMapper;

    public UserComplaint save(Long userId, UserComplaint complaint) {
        if (complaint == null || complaint.getOrderId() == null || !StringUtils.hasText(complaint.getType()) || !StringUtils.hasText(complaint.getContent())) {
            throw new BusinessException("投诉参数不完整");
        }
        TravelOrder order = travelOrderMapper.selectById(complaint.getOrderId());
        if (order == null || !order.getUserId().equals(userId)) {
            throw new BusinessException("订单不存在");
        }
        complaint.setUserId(userId);
        complaint.setType(complaint.getType().trim());
        complaint.setContent(complaint.getContent().trim());
        complaint.setAttachmentUrls(StringUtils.hasText(complaint.getAttachmentUrls()) ? complaint.getAttachmentUrls().trim() : "[]");
        complaint.setStatus(STATUS_WAITING);
        complaint.setHandleResult("");
        userComplaintMapper.insert(complaint);
        return complaint;
    }

    public void handle(Long id, String handleResult, Long handleBy) {
        if (!StringUtils.hasText(handleResult)) {
            throw new BusinessException("处理结果不能为空");
        }
        UserComplaint db = userComplaintMapper.selectById(id);
        if (db == null) {
            throw new BusinessException("投诉不存在");
        }
        if (STATUS_HANDLED.equals(db.getStatus())) {
            throw new BusinessException("投诉已处理");
        }
        db.setStatus(STATUS_HANDLED);
        db.setHandleResult(handleResult.trim());
        db.setHandleBy(handleBy);
        db.setHandleTime(LocalDateTime.now());
        userComplaintMapper.updateById(db);
    }

    public PageResult<Map<String, Object>> myPage(Long userId, Integer pageNum, Integer pageSize, String status) {
        Page<UserComplaint> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<UserComplaint> wrapper = new LambdaQueryWrapper<UserComplaint>()
                .eq(UserComplaint::getUserId, userId)
                .eq(StringUtils.hasText(status), UserComplaint::getStatus, status == null ? null : status.trim())
                .orderByDesc(UserComplaint::getId);
        return buildPageResult(userComplaintMapper.selectPage(page, wrapper));
    }

    public PageResult<Map<String, Object>> page(Integer pageNum, Integer pageSize, String status, String username) {
        Page<UserComplaint> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<UserComplaint> wrapper = new LambdaQueryWrapper<UserComplaint>()
                .eq(StringUtils.hasText(status), UserComplaint::getStatus, status == null ? null : status.trim())
                .orderByDesc(UserComplaint::getId);
        if (StringUtils.hasText(username)) {
            List<User> users = userMapper.selectList(new LambdaQueryWrapper<User>()
                    .and(w -> w.like(User::getUsername, username.trim()).or().like(User::getNickname, username.trim())));
            if (users.isEmpty()) {
                PageResult<Map<String, Object>> result = new PageResult<>();
                result.setTotal(0L);
                result.setRecords(Collections.emptyList());
                return result;
            }
            wrapper.in(UserComplaint::getUserId, users.stream().map(User::getId).collect(Collectors.toSet()));
        }
        return buildPageResult(userComplaintMapper.selectPage(page, wrapper));
    }

    public Long countByUser(Long userId) {
        return userComplaintMapper.selectCount(new LambdaQueryWrapper<UserComplaint>().eq(UserComplaint::getUserId, userId));
    }

    public Long countPending() {
        return userComplaintMapper.selectCount(new LambdaQueryWrapper<UserComplaint>().eq(UserComplaint::getStatus, STATUS_WAITING));
    }

    private PageResult<Map<String, Object>> buildPageResult(Page<UserComplaint> resultPage) {
        List<UserComplaint> list = resultPage.getRecords();
        List<Map<String, Object>> records = new ArrayList<>();
        if (!list.isEmpty()) {
            Set<Long> userIds = list.stream().map(UserComplaint::getUserId).collect(Collectors.toSet());
            Set<Long> orderIds = list.stream().map(UserComplaint::getOrderId).collect(Collectors.toSet());
            Map<Long, User> userMap = userMapper.selectBatchIds(userIds).stream()
                    .collect(Collectors.toMap(User::getId, e -> e));
            Map<Long, TravelOrder> orderMap = travelOrderMapper.selectBatchIds(orderIds).stream()
                    .collect(Collectors.toMap(TravelOrder::getId, e -> e));
            for (UserComplaint complaint : list) {
                User user = userMap.get(complaint.getUserId());
                TravelOrder order = orderMap.get(complaint.getOrderId());
                Map<String, Object> item = new LinkedHashMap<>();
                item.put("id", complaint.getId());
                item.put("userId", complaint.getUserId());
                item.put("userName", user == null ? "" : (StringUtils.hasText(user.getNickname()) ? user.getNickname() : user.getUsername()));
                item.put("orderId", complaint.getOrderId());
                item.put("orderNo", order == null ? "" : order.getOrderNo());
                item.put("type", complaint.getType());
                item.put("content", complaint.getContent());
                item.put("attachmentUrls", complaint.getAttachmentUrls());
                item.put("status", complaint.getStatus());
                item.put("handleResult", complaint.getHandleResult());
                item.put("handleBy", complaint.getHandleBy());
                item.put("handleTime", complaint.getHandleTime());
                item.put("createTime", complaint.getCreateTime());
                records.add(item);
            }
        }
        PageResult<Map<String, Object>> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(records);
        return result;
    }
}
