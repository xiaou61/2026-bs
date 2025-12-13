package com.xiaou.seniorhealth.controller;

import com.xiaou.seniorhealth.common.ApiResponse;
import com.xiaou.seniorhealth.domain.SysUser;
import com.xiaou.seniorhealth.repository.SysUserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {
    private final SysUserRepository sysUserRepository;
    public AdminUserController(SysUserRepository sysUserRepository) {
        this.sysUserRepository = sysUserRepository;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ApiResponse<List<SysUser>> all() {
        Iterable<SysUser> it = sysUserRepository.findAll();
        List<SysUser> list = new ArrayList<>();
        it.forEach(list::add);
        return ApiResponse.ok(list);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/role")
    public ApiResponse<SysUser> role(@PathVariable Long id, @RequestParam String role) {
        SysUser u = sysUserRepository.findById(id).orElse(null);
        if (u == null) return ApiResponse.fail("not found");
        u.setRole(role.toUpperCase());
        return ApiResponse.ok(sysUserRepository.save(u));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/status")
    public ApiResponse<SysUser> status(@PathVariable Long id, @RequestParam Integer status) {
        SysUser u = sysUserRepository.findById(id).orElse(null);
        if (u == null) return ApiResponse.fail("not found");
        u.setStatus(status);
        return ApiResponse.ok(sysUserRepository.save(u));
    }
}
