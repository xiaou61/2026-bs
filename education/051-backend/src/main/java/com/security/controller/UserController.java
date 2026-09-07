package com.security.controller;

import com.security.common.Result;
import com.security.dto.LoginDTO;
import com.security.dto.UserUpdateDTO;
import com.security.service.UserService;
import com.security.vo.LoginVO;
import com.security.vo.UserStatsVO;
import com.security.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody LoginDTO dto) {
        return Result.success(userService.login(dto));
    }

    @GetMapping("/info")
    public Result<UserVO> getUserInfo(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(userService.getUserInfo(userId));
    }

    @PutMapping("/info")
    public Result<?> updateUserInfo(HttpServletRequest request, @RequestBody UserUpdateDTO dto) {
        Long userId = (Long) request.getAttribute("userId");
        userService.updateUserInfo(userId, dto);
        return Result.success();
    }

    @GetMapping("/stats")
    public Result<UserStatsVO> getUserStats(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(userService.getUserStats(userId));
    }
}
