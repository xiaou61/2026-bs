package com.xiaou.seniorhealth.controller;

import com.xiaou.seniorhealth.common.ApiResponse;
import com.xiaou.seniorhealth.domain.SysUser;
import com.xiaou.seniorhealth.dto.AuthResponse;
import com.xiaou.seniorhealth.dto.LoginDTO;
import com.xiaou.seniorhealth.dto.RegisterDTO;
import com.xiaou.seniorhealth.repository.SysUserRepository;
import com.xiaou.seniorhealth.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final SysUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    public AuthController(SysUserRepository userRepository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }
    @PostMapping("/register")
    public ApiResponse<AuthResponse> register(@Valid @RequestBody RegisterDTO dto) {
        String role = dto.getRole() == null || dto.getRole().isBlank() ? "ELDER" : dto.getRole().toUpperCase();
        SysUser u = new SysUser();
        u.setUsername(dto.getUsername());
        u.setPassword(passwordEncoder.encode(dto.getPassword()));
        u.setRole(role);
        u.setStatus(1);
        u.setCreatedAt(LocalDateTime.now());
        userRepository.save(u);
        String token = tokenService.create(u.getUsername(), u.getRole(), 86400);
        return ApiResponse.ok(new AuthResponse(token, u.getUsername(), u.getRole()));
    }
    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@Valid @RequestBody LoginDTO dto) {
        SysUser u = userRepository.findByUsername(dto.getUsername()).orElse(null);
        if (u == null || u.getStatus() == null || u.getStatus() != 1) return ApiResponse.fail("invalid user");
        if (!passwordEncoder.matches(dto.getPassword(), u.getPassword())) return ApiResponse.fail("invalid password");
        String token = tokenService.create(u.getUsername(), u.getRole(), 86400);
        return ApiResponse.ok(new AuthResponse(token, u.getUsername(), u.getRole()));
    }
    @GetMapping("/me")
    public ApiResponse<Map<String, Object>> me(@RequestHeader(value = "Authorization", required = false) String header) {
        if (header == null || !header.startsWith("Bearer ")) return ApiResponse.fail("no token");
        String token = header.substring(7);
        return ApiResponse.ok(tokenService.parse(token));
    }
}
