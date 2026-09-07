package com.xiaou.seniorhealth.security;

import com.xiaou.seniorhealth.domain.Elder;
import com.xiaou.seniorhealth.domain.SysUser;
import com.xiaou.seniorhealth.repository.ElderRepository;
import com.xiaou.seniorhealth.repository.SysUserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserSupport {
    private final SysUserRepository sysUserRepository;
    private final ElderRepository elderRepository;

    public CurrentUserSupport(SysUserRepository sysUserRepository, ElderRepository elderRepository) {
        this.sysUserRepository = sysUserRepository;
        this.elderRepository = elderRepository;
    }

    public SysUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null) {
            return null;
        }
        String username = String.valueOf(authentication.getPrincipal());
        return sysUserRepository.findByUsername(username).orElse(null);
    }

    public Elder getCurrentElderProfile() {
        SysUser currentUser = getCurrentUser();
        if (currentUser == null || currentUser.getId() == null) {
            return null;
        }
        return elderRepository.findByUserId(currentUser.getId()).orElse(null);
    }
}
