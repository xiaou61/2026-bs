package com.xiaou.sport.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaou.sport.common.Result;
import com.xiaou.sport.entity.SportRecord;
import com.xiaou.sport.entity.User;
import com.xiaou.sport.service.SportRecordService;
import com.xiaou.sport.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@RestController
@RequestMapping("/api/rank")
public class RankController {

    @Autowired
    private UserService userService;

    @Autowired
    private SportRecordService sportRecordService;

    @GetMapping("/points")
    public Result<List<User>> pointsRank(@RequestParam(defaultValue = "50") Integer limit) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("points")
                .last("LIMIT " + limit);
        List<User> users = userService.list(wrapper);
        users.forEach(user -> user.setPassword(null));
        return Result.success(users);
    }

    @GetMapping("/steps")
    public Result<List<Map<String, Object>>> stepsRank(@RequestParam(defaultValue = "50") Integer limit) {
        return Result.success(buildSportRank(limit, SportRecord::getSteps, "steps"));
    }

    @GetMapping("/duration")
    public Result<List<Map<String, Object>>> durationRank(@RequestParam(defaultValue = "50") Integer limit) {
        return Result.success(buildSportRank(limit, SportRecord::getDuration, "duration"));
    }

    private List<Map<String, Object>> buildSportRank(Integer limit, Function<SportRecord, Integer> metricGetter,
            String metricKey) {
        Map<Long, Integer> totals = new HashMap<>();
        for (SportRecord record : sportRecordService.list()) {
            int metricValue = metricGetter.apply(record) == null ? 0 : metricGetter.apply(record);
            totals.merge(record.getUserId(), metricValue, Integer::sum);
        }

        long size = limit == null ? 50L : limit.longValue();
        return userService.list().stream()
                .peek(user -> user.setPassword(null))
                .map(user -> {
                    Map<String, Object> item = new HashMap<>();
                    item.put("id", user.getId());
                    item.put("username", user.getUsername());
                    item.put("nickname", user.getNickname());
                    item.put("avatar", user.getAvatar());
                    item.put("role", user.getRole());
                    item.put("points", user.getPoints());
                    item.put(metricKey, totals.getOrDefault(user.getId(), 0));
                    return item;
                })
                .sorted((left, right) -> Integer.compare((Integer) right.get(metricKey), (Integer) left.get(metricKey)))
                .limit(size)
                .toList();
    }
}
