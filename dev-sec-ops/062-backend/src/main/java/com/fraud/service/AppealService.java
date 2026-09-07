package com.fraud.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fraud.common.BusinessException;
import com.fraud.entity.Appeal;
import com.fraud.entity.FraudAlert;
import com.fraud.entity.RiskEvent;
import com.fraud.entity.User;
import com.fraud.mapper.AppealMapper;
import com.fraud.mapper.FraudAlertMapper;
import com.fraud.mapper.RiskEventMapper;
import com.fraud.mapper.UserMapper;
import com.fraud.vo.AppealVO;
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
public class AppealService {

    @Resource
    private AppealMapper appealMapper;

    @Resource
    private FraudAlertMapper alertMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private RiskEventMapper eventMapper;

    public Page<AppealVO> page(Integer pageNum, Integer pageSize, Integer status, String alertNo) {
        QueryWrapper<Appeal> wrapper = new QueryWrapper<>();
        if (status != null) {
            wrapper.eq("status", status);
        }
        appendAlertNoFilter(wrapper, alertNo);
        wrapper.orderByDesc("id");
        Page<Appeal> page = appealMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return convertPage(page);
    }

    public Page<AppealVO> myPage(Long userId, Integer pageNum, Integer pageSize, Integer status, String alertNo) {
        QueryWrapper<Appeal> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        if (status != null) {
            wrapper.eq("status", status);
        }
        appendAlertNoFilter(wrapper, alertNo);
        wrapper.orderByDesc("id");
        Page<Appeal> page = appealMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return convertPage(page);
    }

    @Transactional(rollbackFor = Exception.class)
    public void add(Appeal appeal, Long userId) {
        if (appeal == null || appeal.getAlertId() == null) {
            throw new BusinessException("请选择预警记录");
        }
        if (appeal.getContent() == null || appeal.getContent().trim().isEmpty()) {
            throw new BusinessException("申诉内容不能为空");
        }
        appeal.setContent(appeal.getContent().trim());
        if (appeal.getContent().length() > 1000) {
            throw new BusinessException("申诉内容不能超过1000字符");
        }
        FraudAlert alert = alertMapper.selectById(appeal.getAlertId());
        if (alert == null) {
            throw new BusinessException("预警不存在");
        }
        if (!alert.getUserId().equals(userId)) {
            throw new BusinessException("只能申诉自己的预警");
        }
        if (alert.getStatus() == null || (alert.getStatus() != 2 && alert.getStatus() != 3)) {
            throw new BusinessException("当前预警状态不可申诉");
        }
        Long pendingCount = appealMapper.selectCount(new QueryWrapper<Appeal>()
                .eq("alert_id", appeal.getAlertId())
                .eq("status", 0));
        if (pendingCount != null && pendingCount > 0) {
            throw new BusinessException("该预警已有待处理申诉");
        }
        appeal.setUserId(userId);
        appeal.setStatus(0);
        appealMapper.insert(appeal);
    }

    @Transactional(rollbackFor = Exception.class)
    public void reply(Long id, String reply, Long adminId, Integer alertStatus) {
        if (reply == null || reply.trim().isEmpty()) {
            throw new BusinessException("回复内容不能为空");
        }
        reply = reply.trim();
        if (reply.length() > 1000) {
            throw new BusinessException("回复内容不能超过1000字符");
        }
        Appeal appeal = appealMapper.selectById(id);
        if (appeal == null) {
            throw new BusinessException("申诉不存在");
        }
        if (appeal.getStatus() != null && appeal.getStatus() == 1) {
            throw new BusinessException("该申诉已回复");
        }
        appeal.setReply(reply);
        appeal.setReplyAdminId(adminId);
        appeal.setReplyTime(LocalDateTime.now());
        appeal.setStatus(1);
        appealMapper.updateById(appeal);

        if (alertStatus != null) {
            if (alertStatus != 2 && alertStatus != 3) {
                throw new BusinessException("预警状态不合法");
            }
            FraudAlert alert = alertMapper.selectById(appeal.getAlertId());
            if (alert != null) {
                alert.setStatus(alertStatus);
                alertMapper.updateById(alert);
                RiskEvent event = eventMapper.selectById(alert.getEventId());
                if (event != null) {
                    if (alertStatus == 2) {
                        event.setStatus(2);
                    } else if (alertStatus == 3) {
                        event.setStatus(1);
                    }
                    eventMapper.updateById(event);
                }
            }
        }
    }

    private void appendAlertNoFilter(QueryWrapper<Appeal> wrapper, String alertNo) {
        if (alertNo == null || alertNo.trim().isEmpty()) {
            return;
        }
        List<FraudAlert> alerts = alertMapper.selectList(new QueryWrapper<FraudAlert>()
                .select("id")
                .like("alert_no", alertNo.trim()));
        if (alerts == null || alerts.isEmpty()) {
            wrapper.eq("alert_id", -1L);
            return;
        }
        List<Long> alertIds = alerts.stream().map(FraudAlert::getId).collect(Collectors.toList());
        wrapper.in("alert_id", alertIds);
    }

    private Page<AppealVO> convertPage(Page<Appeal> page) {
        Page<AppealVO> voPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        voPage.setRecords(convertList(page.getRecords()));
        return voPage;
    }

    private List<AppealVO> convertList(List<Appeal> list) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        Set<Long> userIds = new HashSet<>();
        Set<Long> alertIds = new HashSet<>();
        for (Appeal appeal : list) {
            if (appeal.getUserId() != null) {
                userIds.add(appeal.getUserId());
            }
            if (appeal.getReplyAdminId() != null) {
                userIds.add(appeal.getReplyAdminId());
            }
            if (appeal.getAlertId() != null) {
                alertIds.add(appeal.getAlertId());
            }
        }
        Map<Long, User> userMap = new HashMap<>();
        if (!userIds.isEmpty()) {
            List<User> users = userMapper.selectBatchIds(userIds);
            userMap = users.stream().collect(Collectors.toMap(User::getId, item -> item));
        }
        Map<Long, FraudAlert> alertMap = new HashMap<>();
        if (!alertIds.isEmpty()) {
            List<FraudAlert> alerts = alertMapper.selectBatchIds(alertIds);
            alertMap = alerts.stream().collect(Collectors.toMap(FraudAlert::getId, item -> item));
        }
        List<AppealVO> result = new ArrayList<>();
        for (Appeal appeal : list) {
            AppealVO vo = new AppealVO();
            BeanUtils.copyProperties(appeal, vo);
            User user = userMap.get(appeal.getUserId());
            User admin = userMap.get(appeal.getReplyAdminId());
            FraudAlert alert = alertMap.get(appeal.getAlertId());
            vo.setUserName(user == null ? "未知用户" : (user.getNickname() == null || user.getNickname().trim().isEmpty() ? user.getUsername() : user.getNickname()));
            vo.setReplyAdminName(admin == null ? "-" : (admin.getNickname() == null || admin.getNickname().trim().isEmpty() ? admin.getUsername() : admin.getNickname()));
            vo.setAlertNo(alert == null ? "预警已删除" : alert.getAlertNo());
            result.add(vo);
        }
        return result;
    }
}
