package com.xiaou.sport.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaou.sport.common.Result;
import com.xiaou.sport.entity.User;
import com.xiaou.sport.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/rank")
public class RankController {

    @Autowired
    private UserService userService;

    @GetMapping("/points")
    public Result<List<User>> pointsRank(@RequestParam(defaultValue = "50") Integer limit) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("points")
                .last("LIMIT " + limit);
        List<User> users = userService.list(wrapper);
        users.forEach(user -> user.setPassword(null));
        return Result.success(users);
    }
}
