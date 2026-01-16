package com.community.parking.controller;

import com.community.parking.common.Result;
import com.community.parking.dto.LoginDTO;
import com.community.parking.dto.RegisterDTO;
import com.community.parking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public Result<Map<String, Object>> register(@RequestBody RegisterDTO dto) {
        try {
            Map<String, Object> result = userService.register(dto);
            return Result.success(result);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody LoginDTO dto) {
        try {
            Map<String, Object> result = userService.login(dto);
            return Result.success(result);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
