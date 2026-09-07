package com.xiaou.seniorhealth.controller;

import com.xiaou.seniorhealth.common.ApiResponse;
import com.xiaou.seniorhealth.domain.SysUser;
import com.xiaou.seniorhealth.repository.SysUserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {

    private static final Set<String> VALID_ROLES = Set.of("ADMIN", "USER");

    private final SysUserRepository sysUserRepository;

    public AdminUserController(SysUserRepository sysUserRepository) {
        this.sysUserRepository = sysUserRepository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ApiResponse<List<SysUser>> all() {
        Iterable<SysUser> it = sysUserRepository.findAll();
        List<SysUser> list = new ArrayList<>();
        it.forEach(u -> list.add(toSafeUser(u)));
        return ApiResponse.ok(list);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/role")
    public ApiResponse<SysUser> role(@PathVariable Long id, @RequestParam String role) {
        if (!VALID_ROLES.contains(role.toUpperCase())) {
            return ApiResponse.fail("角色不合法");
        }
        SysUser u = sysUserRepository.findById(id).orElse(null);
        if (u == null) return ApiResponse.fail("not found");
        u.setRole(role.toUpperCase());
        SysUser saved = sysUserRepository.save(u);
        return ApiResponse.ok(toSafeUser(saved));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/status")
    public ApiResponse<SysUser> status(@PathVariable Long id, @RequestParam Integer status) {
        if (status == null || (status != 0 && status != 1)) {
            return ApiResponse.fail("状态不合法");
        }
        SysUser u = sysUserRepository.findById(id).orElse(null);
        if (u == null) return ApiResponse.fail("not found");
        u.setStatus(status);
        SysUser saved = sysUserRepository.save(u);
        return ApiResponse.ok(toSafeUser(saved));
    }

    private SysUser toSafeUser(SysUser user) {
        SysUser copy = new SysUser();
        copy.setId(user.getId());
        copy.setUsername(user.getUsername());
        copy.setRole(user.getRole());
        copy.setStatus(user.getStatus());
        copy.setCreatedAt(user.getCreatedAt());
        return copy;
    }
}
