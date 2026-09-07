package com.fraud.service;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fraud.common.BusinessException;
import com.fraud.entity.FraudAlert;
import com.fraud.entity.RiskEvent;
import com.fraud.entity.RiskRule;
import com.fraud.entity.User;
import com.fraud.mapper.FraudAlertMapper;
import com.fraud.mapper.RiskEventMapper;
import com.fraud.mapper.UserMapper;
import com.fraud.vo.RiskEventVO;
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
import java.util.stream.Collectors;

@Service
public class RiskEventService {

    @Resource
    private RiskEventMapper eventMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private RiskRuleService riskRuleService;

    @Resource
    private BlacklistService blacklistService;

    @Resource
    private FraudAlertMapper alertMapper;

    @Transactional(rollbackFor = Exception.class)
    public RiskEvent report(RiskEvent event, Long userId) {
        if (event == null) {
            throw new BusinessException("事件参数不能为空");
        }
        if (event.getAccountNo() == null || event.getAccountNo().trim().isEmpty()) {
            throw new BusinessException("账号不能为空");
        }
        if (event.getAmount() == null || event.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("金额必须大于0");
        }
        if (event.getEventType() == null || event.getEventType().trim().isEmpty()) {
            throw new BusinessException("事件类型不能为空");
        }
        if (event.getChannel() == null || event.getChannel().trim().isEmpty()) {
            throw new BusinessException("渠道不能为空");
        }
        event.setAccountNo(event.getAccountNo().trim());
        event.setEventType(event.getEventType().trim());
        event.setChannel(event.getChannel().trim());
        if (event.getDeviceId() != null) {
            event.setDeviceId(event.getDeviceId().trim());
        }
        if (event.getIp() != null) {
            event.setIp(event.getIp().trim());
        }
        if (event.getRemark() != null) {
            event.setRemark(event.getRemark().trim());
            if (event.getRemark().length() > 255) {
                throw new BusinessException("备注不能超过255字符");
            }
        }

        int score = 0;
        List<String> hitRules = new ArrayList<>();

        if (blacklistService.hit("ACCOUNT", event.getAccountNo())) {
            score += 40;
            hitRules.add("BLACKLIST_ACCOUNT");
        }
        if (event.getDeviceId() != null && !event.getDeviceId().isEmpty() && blacklistService.hit("DEVICE", event.getDeviceId())) {
            score += 35;
            hitRules.add("BLACKLIST_DEVICE");
        }
        if (event.getIp() != null && !event.getIp().isEmpty() && blacklistService.hit("IP", event.getIp())) {
            score += 30;
            hitRules.add("BLACKLIST_IP");
        }

        List<RiskRule> rules = riskRuleService.activeRules();
        Long recentCount = eventMapper.selectCount(new QueryWrapper<RiskEvent>()
                .eq("user_id", userId)
                .ge("create_time", LocalDateTime.now().minusHours(24)));
        int currentCount = (recentCount == null ? 0 : recentCount.intValue()) + 1;
        for (RiskRule rule : rules) {
            boolean hit = false;
            if ("AMOUNT".equals(rule.getRuleType()) && rule.getThreshold() != null) {
                hit = event.getAmount().compareTo(rule.getThreshold()) >= 0;
            }
            if ("FREQUENCY".equals(rule.getRuleType()) && rule.getThreshold() != null) {
                hit = currentCount >= rule.getThreshold().intValue();
            }
            if (hit) {
                score += rule.getWeight() == null ? 0 : rule.getWeight();
                hitRules.add(rule.getRuleCode());
                riskRuleService.increaseHitCount(rule.getId());
            }
        }

        String riskLevel;
        if (score >= 80) {
            riskLevel = "HIGH";
        } else if (score >= 50) {
            riskLevel = "MEDIUM";
        } else {
            riskLevel = "LOW";
        }

        event.setEventNo("EV" + IdUtil.getSnowflakeNextIdStr());
        event.setUserId(userId);
        event.setRiskScore(score);
        event.setRiskLevel(riskLevel);
        event.setStatus(score >= 50 ? 2 : 1);
        event.setHitRules(hitRules.isEmpty() ? "" : String.join(",", hitRules));
        eventMapper.insert(event);

        if (score >= 50) {
            FraudAlert alert = new FraudAlert();
            alert.setAlertNo("AL" + IdUtil.getSnowflakeNextIdStr());
            alert.setEventId(event.getId());
            alert.setUserId(userId);
            alert.setRiskScore(score);
            alert.setRiskLevel(riskLevel);
            alert.setStatus(0);
            alertMapper.insert(alert);
        }
        return event;
    }

    public Page<RiskEventVO> page(Integer pageNum, Integer pageSize, String eventNo, String riskLevel, Integer status, Long userId) {
        QueryWrapper<RiskEvent> wrapper = new QueryWrapper<>();
        if (eventNo != null && !eventNo.trim().isEmpty()) {
            wrapper.like("event_no", eventNo.trim());
        }
        if (riskLevel != null && !riskLevel.trim().isEmpty()) {
            wrapper.eq("risk_level", riskLevel.trim().toUpperCase());
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        if (userId != null) {
            wrapper.eq("user_id", userId);
        }
        wrapper.orderByDesc("id");
        Page<RiskEvent> page = eventMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return convertPage(page);
    }

    public RiskEventVO detail(Long id, Long userId, String role) {
        RiskEvent event = eventMapper.selectById(id);
        if (event == null) {
            throw new BusinessException("风险事件不存在");
        }
        if (!"ADMIN".equals(role) && !"ANALYST".equals(role) && !event.getUserId().equals(userId)) {
            throw new BusinessException("无权限查看该事件");
        }
        List<RiskEvent> list = new ArrayList<>();
        list.add(event);
        List<RiskEventVO> voList = convertList(list);
        return voList.isEmpty() ? null : voList.get(0);
    }

    public void updateStatus(Long id, Integer status) {
        if (status == null || (status != 1 && status != 2 && status != 3)) {
            throw new BusinessException("状态不合法");
        }
        RiskEvent event = eventMapper.selectById(id);
        if (event == null) {
            throw new BusinessException("风险事件不存在");
        }
        event.setStatus(status);
        eventMapper.updateById(event);
    }

    public RiskEvent mustGetById(Long id) {
        RiskEvent event = eventMapper.selectById(id);
        if (event == null) {
            throw new BusinessException("风险事件不存在");
        }
        return event;
    }

    private Page<RiskEventVO> convertPage(Page<RiskEvent> page) {
        Page<RiskEventVO> voPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        voPage.setRecords(convertList(page.getRecords()));
        return voPage;
    }

    private List<RiskEventVO> convertList(List<RiskEvent> list) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        Set<Long> userIds = new HashSet<>();
        for (RiskEvent event : list) {
            if (event.getUserId() != null) {
                userIds.add(event.getUserId());
            }
        }
        Map<Long, User> userMap = new HashMap<>();
        if (!userIds.isEmpty()) {
            List<User> users = userMapper.selectBatchIds(userIds);
            userMap = users.stream().collect(Collectors.toMap(User::getId, item -> item));
        }
        List<RiskEventVO> result = new ArrayList<>();
        for (RiskEvent event : list) {
            RiskEventVO vo = new RiskEventVO();
            BeanUtils.copyProperties(event, vo);
            User user = userMap.get(event.getUserId());
            vo.setUserName(user == null ? "未知用户" : (user.getNickname() == null || user.getNickname().trim().isEmpty() ? user.getUsername() : user.getNickname()));
            result.add(vo);
        }
        return result;
    }
}
