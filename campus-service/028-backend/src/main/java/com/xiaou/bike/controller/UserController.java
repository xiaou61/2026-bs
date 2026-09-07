package com.xiaou.bike.controller;

import com.xiaou.bike.common.Result;
import com.xiaou.bike.dto.LoginDTO;
import com.xiaou.bike.dto.RegisterDTO;
import com.xiaou.bike.entity.User;
import com.xiaou.bike.service.UserService;
import com.xiaou.bike.vo.LoginVO;
import com.xiaou.bike.vo.UserInfoVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public Result<Void> register(@RequestBody @Valid RegisterDTO dto) {
        userService.register(dto);
        return Result.success();
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody @Valid LoginDTO dto) {
        LoginVO vo = userService.login(dto);
        return Result.success(vo);
    }

    /**
     * 获取当前用户信息
     */
    @GetMapping("/info")
    public Result<UserInfoVO> getUserInfo() {
        UserInfoVO vo = userService.getUserInfo();
        return Result.success(vo);
    }

    /**
     * 更新用户信息
     */
    @PutMapping("/info")
    public Result<Void> updateUserInfo(@RequestBody User user) {
        userService.updateUserInfo(user);
        return Result.success();
    }

    /**
     * 修改密码
     */
    @PutMapping("/password")
    public Result<Void> changePassword(@RequestParam String oldPassword, @RequestParam String newPassword) {
        userService.changePassword(oldPassword, newPassword);
        return Result.success();
    }

    /**
     * 提交实名认证
     */
    @PostMapping("/auth")
    public Result<Void> submitAuth(@RequestParam String realName,
                                   @RequestParam String idCard,
                                   @RequestParam String authImage) {
        userService.submitAuth(realName, idCard, authImage);
        return Result.success();
    }
}
