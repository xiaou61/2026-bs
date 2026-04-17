package com.xiaou.controller;

import com.xiaou.common.Result;
import com.xiaou.dto.UserLoginDTO;
import com.xiaou.dto.UserRegisterDTO;
import com.xiaou.dto.UserUpdateDTO;
import com.xiaou.service.UserService;
import com.xiaou.vo.UserInfoVO;
import com.xiaou.vo.UserLoginVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public Result<String> register(@Valid @RequestBody UserRegisterDTO registerDTO) {
        return Result.success(userService.register(registerDTO));
    }

    @PostMapping("/login")
    public Result<UserLoginVO> login(@Valid @RequestBody UserLoginDTO loginDTO) {
        return Result.success(userService.login(loginDTO));
    }

    @GetMapping("/info")
    public Result<UserInfoVO> getUserInfo(@RequestAttribute("userId") Long userId) {
        return Result.success(userService.getUserInfo(userId));
    }

    @PutMapping("/update")
    public Result<String> updateUser(@RequestAttribute("userId") Long userId,
                                   @Valid @RequestBody UserUpdateDTO updateDTO) {
        userService.updateUser(userId, updateDTO);
        return Result.success("更新成功");
    }

    @GetMapping("/profile/{id}")
    public Result<UserInfoVO> getUserProfile(@PathVariable Long id) {
        return Result.success(userService.getUserProfile(id));
    }
}
