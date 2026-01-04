package com.programming.learning.controller;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.programming.learning.common.Result;
import com.programming.learning.entity.User;
import com.programming.learning.service.UserService;
import com.programming.learning.util.JwtUtil;
import com.programming.learning.util.WeChatUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 认证控制器
 */
@Slf4j
@RestController
@RequestMapping("/auth")
@Tag(name = "认证接口")
public class AuthController {

    @Autowired
    private WeChatUtil weChatUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 微信小程序登录
     */
    @PostMapping("/wxlogin")
    @Operation(summary = "微信小程序登录")
    public Result<Map<String, Object>> wxLogin(@RequestBody WxLoginRequest request) {
        log.info("微信登录，code: {}", request.getCode());

        // 1. 通过code获取session信息
        WxMaJscode2SessionResult session = weChatUtil.code2Session(request.getCode());
        if (session == null) {
            return Result.error("微信登录失败");
        }

        String openId = session.getOpenid();
        log.info("openId: {}", openId);

        // 2. 查询或创建用户
        User user = userService.getUserByOpenId(openId);
        if (user == null) {
            user = userService.createUser(openId, request.getNickname(), request.getAvatar());
        }

        // 3. 更新最后登录信息
        userService.updateLastLogin(user.getId(), request.getIp());

        // 4. 生成JWT Token
        String token = jwtUtil.generateToken(user.getId(), user.getRole().name(), openId);

        // 5. 返回结果
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("userId", user.getId());
        data.put("nickname", user.getNickname());
        data.put("avatar", user.getAvatar());
        data.put("role", user.getRole());
        data.put("score", user.getScore());
        data.put("level", user.getLevel());

        return Result.success("登录成功", data);
    }

    /**
     * 获取用户信息
     */
    @GetMapping("/userinfo")
    @Operation(summary = "获取用户信息")
    public Result<User> getUserInfo(@RequestHeader("Authorization") String authorization) {
        // 从Token中获取用户ID
        String token = authorization.replace("Bearer ", "");
        Long userId = jwtUtil.getUserIdFromToken(token);

        if (userId == null) {
            return Result.error(401, "未登录");
        }

        User user = userService.getUserById(userId);
        return Result.success(user);
    }

    /**
     * 微信登录请求
     */
    @Data
    public static class WxLoginRequest {
        private String code;
        private String nickname;
        private String avatar;
        private String ip;
    }
}
