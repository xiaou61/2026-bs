# 043 宠物寄养服务系统后端

## 项目简介

本项目是基于 Spring Boot 3.2、MyBatis-Plus、JWT 的宠物寄养服务系统后端，提供用户登录注册、宠物档案、寄养服务商浏览、服务项目查询、寄养预约和订单状态管理等核心接口。

## 默认演示环境

项目默认使用 H2 内存数据库自举，启动后会自动创建表并写入演示数据，无需提前安装 MySQL 或 Redis。

- 后端地址：`http://localhost:8043`
- 接口文档：`http://localhost:8043/doc.html`
- H2 控制台：`http://localhost:8043/h2-console`
- H2 JDBC URL：`jdbc:h2:mem:pet_boarding`
- 管理员账号：`admin / admin123`
- 普通用户账号：`petuser / 123456`
- 服务商账号：`provider / 123456`

## 常用命令

```bash
mvn test
mvn spring-boot:run
```

如需连接 MySQL，可先导入 `sql/init.sql`，再使用：

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=mysql
```

## 前端联调

前端开发服务默认运行在 `http://localhost:3043`，会将 `/api` 代理到 `http://localhost:8043`。
