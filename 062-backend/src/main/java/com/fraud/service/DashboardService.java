package com.fraud.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fraud.entity.FraudAlert;
import com.fraud.entity.RiskEvent;
import com.fraud.entity.RiskRule;
import com.fraud.entity.User;
import com.fraud.mapper.FraudAlertMapper;
import com.fraud.mapper.RiskEventMapper;
import com.fraud.mapper.RiskRuleMapper;
import com.fraud.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RiskEventMapper eventMapper;

    @Resource
    private FraudAlertMapper alertMapper;

    @Resource
    private RiskRuleMapper ruleMapper;

    public Map<String, Object> stats() {
        Map<String, Object> map = new HashMap<>();
        map.put("userCount", userMapper.selectCount(null));
        map.put("eventCount", eventMapper.selectCount(null));
        map.put("pendingAlertCount", alertMapper.selectCount(new QueryWrapper<FraudAlert>().eq("status", 0)));
        map.put("highRiskCount", eventMapper.selectCount(new QueryWrapper<RiskEvent>().eq("risk_level", "HIGH")));
        return map;
    }

    public List<Map<String, Object>> riskTrend() {
        List<Map<String, Object>> data = new ArrayList<>();
        List<RiskEvent> events = eventMapper.selectList(new QueryWrapper<RiskEvent>()
                .ge("create_time", LocalDate.now().minusDays(6))
                .orderByAsc("create_time"));
        Map<String, Integer> counter = new LinkedHashMap<>();
        for (int i = 6; i >= 0; i--) {
            String key = LocalDate.now().minusDays(i).toString();
            counter.put(key, 0);
        }
        for (RiskEvent event : events) {
            if (event.getCreateTime() != null) {
                String day = event.getCreateTime().toLocalDate().toString();
                counter.put(day, counter.getOrDefault(day, 0) + 1);
            }
        }
        for (Map.Entry<String, Integer> entry : counter.entrySet()) {
            Map<String, Object> map = new HashMap<>();
            map.put("date", entry.getKey());
            map.put("count", entry.getValue());
            data.add(map);
        }
        return data;
    }

    public List<Map<String, Object>> riskLevelDist() {
        List<Map<String, Object>> data = new ArrayList<>();
        Long high = eventMapper.selectCount(new QueryWrapper<RiskEvent>().eq("risk_level", "HIGH"));
        Long medium = eventMapper.selectCount(new QueryWrapper<RiskEvent>().eq("risk_level", "MEDIUM"));
        Long low = eventMapper.selectCount(new QueryWrapper<RiskEvent>().eq("risk_level", "LOW"));
        Map<String, Object> highMap = new HashMap<>();
        highMap.put("name", "高风险");
        highMap.put("value", high == null ? 0 : high);
        data.add(highMap);
        Map<String, Object> mediumMap = new HashMap<>();
        mediumMap.put("name", "中风险");
        mediumMap.put("value", medium == null ? 0 : medium);
        data.add(mediumMap);
        Map<String, Object> lowMap = new HashMap<>();
        lowMap.put("name", "低风险");
        lowMap.put("value", low == null ? 0 : low);
        data.add(lowMap);
        return data;
    }

    public List<Map<String, Object>> topRules() {
        List<RiskRule> rules = ruleMapper.selectList(new QueryWrapper<RiskRule>()
                .orderByDesc("hit_count", "id")
                .last("limit 10"));
        List<Map<String, Object>> data = new ArrayList<>();
        for (RiskRule rule : rules) {
            Map<String, Object> map = new HashMap<>();
            map.put("ruleName", rule.getRuleName());
            map.put("ruleCode", rule.getRuleCode());
            map.put("hitCount", rule.getHitCount() == null ? 0 : rule.getHitCount());
            data.add(map);
        }
        return data;
    }

    public List<Map<String, Object>> topUsers() {
        List<RiskEvent> events = eventMapper.selectList(new QueryWrapper<RiskEvent>()
                .select("user_id", "risk_score", "risk_level"));
        Map<Long, int[]> statMap = new HashMap<>();
        for (RiskEvent event : events) {
            if (event.getUserId() == null) {
                continue;
            }
            int[] stat = statMap.computeIfAbsent(event.getUserId(), k -> new int[]{0, 0, 0});
            stat[0] = stat[0] + 1;
            stat[1] = stat[1] + (event.getRiskScore() == null ? 0 : event.getRiskScore());
            if ("HIGH".equals(event.getRiskLevel())) {
                stat[2] = stat[2] + 1;
            }
        }
        if (statMap.isEmpty()) {
            return new ArrayList<>();
        }
        Set<Long> userIds = new HashSet<>(statMap.keySet());
        Map<Long, User> userMap = userMapper.selectBatchIds(userIds).stream().collect(Collectors.toMap(User::getId, i -> i));
        List<Map<String, Object>> result = new ArrayList<>();
        for (Map.Entry<Long, int[]> entry : statMap.entrySet()) {
            Long userId = entry.getKey();
            int[] stat = entry.getValue();
            User user = userMap.get(userId);
            Map<String, Object> row = new HashMap<>();
            row.put("userId", userId);
            row.put("userName", user == null ? ("用户#" + userId) : (user.getNickname() == null || user.getNickname().trim().isEmpty() ? user.getUsername() : user.getNickname()));
            row.put("eventCount", stat[0]);
            row.put("highRiskCount", stat[2]);
            row.put("avgScore", stat[0] == 0 ? 0 : (stat[1] * 1.0 / stat[0]));
            result.add(row);
        }
        result.sort((a, b) -> {
            int highA = ((Number) a.get("highRiskCount")).intValue();
            int highB = ((Number) b.get("highRiskCount")).intValue();
            if (highA != highB) {
                return Integer.compare(highB, highA);
            }
            int eventA = ((Number) a.get("eventCount")).intValue();
            int eventB = ((Number) b.get("eventCount")).intValue();
            return Integer.compare(eventB, eventA);
        });
        return result.size() > 10 ? result.subList(0, 10) : result;
    }
}
