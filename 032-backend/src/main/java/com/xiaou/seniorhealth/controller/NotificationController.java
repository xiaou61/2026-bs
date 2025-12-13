package com.xiaou.seniorhealth.controller;

import com.xiaou.seniorhealth.common.ApiResponse;
import com.xiaou.seniorhealth.domain.Notification;
import com.xiaou.seniorhealth.domain.SysUser;
import com.xiaou.seniorhealth.repository.NotificationRepository;
import com.xiaou.seniorhealth.repository.SysUserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationRepository notificationRepository;
    private final SysUserRepository sysUserRepository;
    public NotificationController(NotificationRepository notificationRepository, SysUserRepository sysUserRepository) {
        this.notificationRepository = notificationRepository;
        this.sysUserRepository = sysUserRepository;
    }
    @GetMapping
    public ApiResponse<List<Notification>> my(@RequestParam(defaultValue = "20") int size) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) return ApiResponse.ok(new ArrayList<>());
        String username = String.valueOf(auth.getPrincipal());
        SysUser u = sysUserRepository.findByUsername(username).orElse(null);
        if (u == null) return ApiResponse.ok(new ArrayList<>());
        return ApiResponse.ok(notificationRepository.findLatestByUser(u.getId(), size));
    }
    @PostMapping("/{id}/read")
    public ApiResponse<Notification> read(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = String.valueOf(auth.getPrincipal());
        SysUser u = sysUserRepository.findByUsername(username).orElse(null);
        Notification n = notificationRepository.findById(id).orElse(null);
        if (u == null || n == null || !u.getId().equals(n.getUserId())) return ApiResponse.fail("not allowed");
        n.setStatus("READ");
        n.setCreatedAt(n.getCreatedAt() == null ? LocalDateTime.now() : n.getCreatedAt());
        return ApiResponse.ok(notificationRepository.save(n));
    }
}
