package com.xiaou.wedding.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.xiaou.wedding.mapper")
public class MyBatisConfig {
}
