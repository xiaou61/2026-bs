package com.fraud.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fraud.common.BusinessException;
import com.fraud.entity.FraudAlert;
import com.fraud.entity.RiskEvent;
import com.fraud.entity.User;
import com.fraud.mapper.FraudAlertMapper;
import com.fraud.mapper.RiskEventMapper;
import com.fraud.mapper.UserMapper;
import com.fraud.vo.FraudAlertVO;
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
public class FraudAlertService {

    @Resource
    private FraudAlertMapper alertMapper;

    @Resource
    private RiskEventMapper eventMapper;

    @Resource
    private UserMapper userMapper;

    public Page<FraudAlertVO> page(Integer pageNum, Integer pageSize, String alertNo, Integer status, String riskLevel, String eventNo) {
        QueryWrapper<FraudAlert> wrapper = new QueryWrapper<>();
        if (alertNo != null && !alertNo.trim().isEmpty()) {
            wrapper.like("alert_no", alertNo.trim());
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        if (riskLevel != null && !riskLevel.trim().isEmpty()) {
            wrapper.eq("risk_level", riskLevel.trim().toUpperCase());
        }
        appendEventNoFilter(wrapper, eventNo);
        wrapper.orderByDesc("id");
        Page<FraudAlert> page = alertMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return convertPage(page);
    }

    public Page<FraudAlertVO> myPage(Long userId, Integer pageNum, Integer pageSize, Integer status) {
        QueryWrapper<FraudAlert> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        if (status != null) {
            wrapper.eq("status", status);
        }
        wrapper.orderByDesc("id");
        Page<FraudAlert> page = alertMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return convertPage(page);
    }

    public FraudAlertVO detail(Long id, Long userId, String role) {
        FraudAlert alert = alertMapper.selectById(id);
        if (alert == null) {
            throw new BusinessException("预警不存在");
        }
        if (!"ADMIN".equals(role) && !"ANALYST".equals(role) && !alert.getUserId().equals(userId)) {
            throw new BusinessException("无权限查看该预警");
        }
        List<FraudAlert> list = new ArrayList<>();
        list.add(alert);
        List<FraudAlertVO> voList = convertList(list);
        return voList.isEmpty() ? null : voList.get(0);
    }

    @Transactional(rollbackFor = Exception.class)
    public void assign(Long id, Long assigneeId) {
        FraudAlert alert = alertMapper.selectById(id);
        if (alert == null) {
            throw new BusinessException("预警不存在");
        }
        if (alert.getStatus() != null && (alert.getStatus() == 2 || alert.getStatus() == 3)) {
            throw new BusinessException("已结束预警不可再指派");
        }
        User assignee = userMapper.selectById(assigneeId);
        if (assignee == null) {
            throw new BusinessException("指派人不存在");
        }
        if (!"ADMIN".equals(assignee.getRole()) && !"ANALYST".equals(assignee.getRole())) {
            throw new BusinessException("只能指派给管理员或分析员");
        }
        alert.setAssigneeId(assigneeId);
        alert.setStatus(1);
        alertMapper.updateById(alert);

        RiskEvent event = eventMapper.selectById(alert.getEventId());
        if (event != null) {
            event.setStatus(3);
            eventMapper.updateById(event);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void handle(Long id, Integer status, String handleResult, String handleRemark, Long operatorId) {
        if (status == null || (status != 1 && status != 2 && status != 3)) {
            throw new BusinessException("预警状态不合法");
        }
        FraudAlert alert = alertMapper.selectById(id);
        if (alert == null) {
            throw new BusinessException("预警不存在");
        }
        if (handleResult != null) {
            handleResult = handleResult.trim();
            if (handleResult.length() > 100) {
                throw new BusinessException("处置结果不能超过100字符");
            }
        }
        if (handleRemark != null) {
            handleRemark = handleRemark.trim();
            if (handleRemark.length() > 500) {
                throw new BusinessException("处置备注不能超过500字符");
            }
        }
        alert.setStatus(status);
        alert.setAssigneeId(operatorId);
        alert.setHandleResult(handleResult);
        alert.setHandleRemark(handleRemark);
        if (status == 2 || status == 3) {
            alert.setHandleTime(LocalDateTime.now());
        }
        alertMapper.updateById(alert);

        RiskEvent event = eventMapper.selectById(alert.getEventId());
        if (event != null) {
            if (status == 2) {
                event.setStatus(2);
            } else if (status == 3) {
                event.setStatus(1);
            } else {
                event.setStatus(3);
            }
            eventMapper.updateById(event);
        }
    }

    public FraudAlert mustGetById(Long id) {
        FraudAlert alert = alertMapper.selectById(id);
        if (alert == null) {
            throw new BusinessException("预警不存在");
        }
        return alert;
    }

    private void appendEventNoFilter(QueryWrapper<FraudAlert> wrapper, String eventNo) {
        if (eventNo == null || eventNo.trim().isEmpty()) {
            return;
        }
        List<RiskEvent> events = eventMapper.selectList(new QueryWrapper<RiskEvent>()
                .select("id")
                .like("event_no", eventNo.trim()));
        if (events == null || events.isEmpty()) {
            wrapper.eq("event_id", -1L);
            return;
        }
        List<Long> eventIds = events.stream().map(RiskEvent::getId).collect(Collectors.toList());
        wrapper.in("event_id", eventIds);
    }

    private Page<FraudAlertVO> convertPage(Page<FraudAlert> page) {
        Page<FraudAlertVO> voPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        voPage.setRecords(convertList(page.getRecords()));
        return voPage;
    }

    private List<FraudAlertVO> convertList(List<FraudAlert> list) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        Set<Long> userIds = new HashSet<>();
        Set<Long> eventIds = new HashSet<>();
        for (FraudAlert alert : list) {
            if (alert.getUserId() != null) {
                userIds.add(alert.getUserId());
            }
            if (alert.getAssigneeId() != null) {
                userIds.add(alert.getAssigneeId());
            }
            if (alert.getEventId() != null) {
                eventIds.add(alert.getEventId());
            }
        }
        Map<Long, User> userMap = new HashMap<>();
        if (!userIds.isEmpty()) {
            List<User> users = userMapper.selectBatchIds(userIds);
            userMap = users.stream().collect(Collectors.toMap(User::getId, item -> item));
        }
        Map<Long, RiskEvent> eventMap = new HashMap<>();
        if (!eventIds.isEmpty()) {
            List<RiskEvent> events = eventMapper.selectBatchIds(eventIds);
            eventMap = events.stream().collect(Collectors.toMap(RiskEvent::getId, item -> item));
        }
        List<FraudAlertVO> result = new ArrayList<>();
        for (FraudAlert alert : list) {
            FraudAlertVO vo = new FraudAlertVO();
            BeanUtils.copyProperties(alert, vo);
            User user = userMap.get(alert.getUserId());
            User assignee = userMap.get(alert.getAssigneeId());
            RiskEvent event = eventMap.get(alert.getEventId());
            vo.setUserName(user == null ? "未知用户" : (user.getNickname() == null || user.getNickname().trim().isEmpty() ? user.getUsername() : user.getNickname()));
            vo.setAssigneeName(assignee == null ? "-" : (assignee.getNickname() == null || assignee.getNickname().trim().isEmpty() ? assignee.getUsername() : assignee.getNickname()));
            vo.setEventNo(event == null ? "事件已删除" : event.getEventNo());
            result.add(vo);
        }
        return result;
    }
}
