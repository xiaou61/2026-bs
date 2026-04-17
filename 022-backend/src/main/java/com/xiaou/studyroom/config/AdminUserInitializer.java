package com.xiaou.studyroom.config;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaou.studyroom.entity.User;
import com.xiaou.studyroom.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminUserInitializer {

    @Autowired
    private UserMapper userMapper;

    @Bean
    public ApplicationRunner adminAccountRunner() {
        return args -> {
            QueryWrapper<User> adminQuery = new QueryWrapper<>();
            adminQuery.eq("username", "admin");
            if (userMapper.selectCount(adminQuery) == 0) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(SecureUtil.md5("123456"));
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
