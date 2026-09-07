# 041 在线心理评测与咨询系统后端

## 项目简介

本项目是基于 Spring Boot 3.2、MyBatis-Plus、JWT 的在线心理评测与咨询系统后端，提供用户登录注册、心理量表、题目查询、咨询师列表、心理文章、测评提交和咨询预约等核心接口。

## 默认演示环境

项目默认使用 H2 内存数据库自举，启动后会自动创建表并写入演示数据，无需提前安装 MySQL 或 Redis。

- 后端地址：`http://localhost:8041`
- H2 控制台：`http://localhost:8041/h2-console`
- H2 JDBC URL：`jdbc:h2:mem:psychology_system`
- 管理员账号：`admin / 123456`
- 咨询师账号：`counselor1 / 123456`
- 普通用户账号：`user1 / 123456`

## 常用命令

```bash
mvn test
mvn spring-boot:run
```

如需连接 MySQL，可先导入 `src/main/resources/init.sql`，再使用：

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=mysql
```

## 前端联调

前端开发服务默认运行在 `http://localhost:3041`，会将 `/api` 代理到 `http://localhost:8041`。
