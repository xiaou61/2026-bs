package com.xiaou.studyroom.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaou.studyroom.entity.User;
import com.xiaou.studyroom.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AdminUserInitializer {

    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final String adminPassword;

    public AdminUserInitializer(UserMapper userMapper,
                                BCryptPasswordEncoder passwordEncoder,
                                @Value("${app.admin.default-password:123456}") String adminPassword) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.adminPassword = adminPassword;
    }

    @Bean
    public ApplicationRunner adminAccountRunner() {
        return args -> {
            QueryWrapper<User> adminQuery = new QueryWrapper<>();
            adminQuery.eq("username", "admin");
            if (userMapper.selectCount(adminQuery) == 0) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode(adminPassword));
                admin.setRealName("系统管理员");
                admin.setDepartment("信息中心");
                admin.setGrade("管理员");
                admin.setPhone("13800000001");
                admin.setCreditScore(120);
                admin.setStatus(1);
                userMapper.insert(admin);
            }
        };
    }
}
