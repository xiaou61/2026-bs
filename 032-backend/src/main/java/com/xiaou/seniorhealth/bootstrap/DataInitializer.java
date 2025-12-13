package com.xiaou.seniorhealth.bootstrap;

import com.xiaou.seniorhealth.domain.SysUser;
import com.xiaou.seniorhealth.repository.SysUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {
    private final SysUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public DataInitializer(SysUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public void run(String... args) {
        if (userRepository.findByUsername("admin").isEmpty()) {
            SysUser u = new SysUser();
            u.setUsername("admin");
            u.setPassword(passwordEncoder.encode("admin123"));
            u.setRole("ADMIN");
            u.setStatus(1);
            u.setCreatedAt(LocalDateTime.now());
            userRepository.save(u);
        }
    }
}
