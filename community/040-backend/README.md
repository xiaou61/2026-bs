# 040 社区车辆违停处理系统后端

## 项目简介

本项目是基于 Spring Boot 3.2、JPA、QueryDSL、JWT 的社区车辆违停处理系统后端，提供用户登录注册、违停类型、违停举报、举报审核等核心接口。

## 默认演示环境

项目默认使用 H2 内存数据库自举，启动后会自动创建表并写入演示数据，无需提前安装 MySQL。

- 后端地址：`http://localhost:8040`
- H2 控制台：`http://localhost:8040/h2-console`
- H2 JDBC URL：`jdbc:h2:mem:parking_management`
- 管理员账号：`admin / 123456`
- 普通用户账号：`user1 / 123456`
- 物业账号：`manager1 / 123456`

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

前端开发服务默认运行在 `http://localhost:3040`，会将 `/api` 代理到 `http://localhost:8040`。
