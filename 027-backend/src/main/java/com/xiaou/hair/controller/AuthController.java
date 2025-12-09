package com.xiaou.hair.controller;

import com.xiaou.hair.common.Result;
import com.xiaou.hair.dto.LoginDTO;
import com.xiaou.hair.dto.RegisterDTO;
import com.xiaou.hair.service.UserService;
import com.xiaou.hair.vo.LoginVO;
import com.xiaou.hair.vo.UserInfoVO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 认证控制器
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public Result<Void> register(@Valid @RequestBody RegisterDTO dto) {
        userService.register(dto);
        return Result.success("注册成功");
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Result<LoginVO> login(@Valid @RequestBody LoginDTO dto) {
        LoginVO loginVO = userService.login(dto);
        return Result.success(loginVO);
    }

    /**
     * 获取当前用户信息
     */
    @GetMapping("/info")
    public Result<UserInfoVO> getCurrentUserInfo() {
        UserInfoVO userInfo = userService.getCurrentUserInfo();
        return Result.success(userInfo);
    }

    /**
     * 更新个人信息
     */
    @PutMapping("/profile")
    public Result<Void> updateProfile(@RequestBody UserInfoVO vo) {
        userService.updateUserInfo(vo);
        return Result.success("更新成功");
    }
}
