package com.xiaou.dreamdonation.controller;

import com.xiaou.dreamdonation.common.Result;
import com.xiaou.dreamdonation.dto.LoginDTO;
import com.xiaou.dreamdonation.dto.RegisterDTO;
import com.xiaou.dreamdonation.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public Result&lt;?&gt; register(@Valid @RequestBody RegisterDTO dto) {
        try {
            return Result.success(userService.register(dto));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/login")
    public Result&lt;?&gt; login(@Valid @RequestBody LoginDTO dto) {
        try {
            return Result.success(userService.login(dto));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/info")
    public Result&lt;?&gt; getUserInfo(@RequestHeader("Authorization") String token) {
        try {
            return Result.success(userService.getUserInfo(1L));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
