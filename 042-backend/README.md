# 042 房屋租赁管理系统后端

## 项目简介

本项目是基于 Spring Boot 3.2、MyBatis-Plus、JWT 的房屋租赁管理系统后端，提供用户登录注册、房源浏览与发布、收藏、预约看房、合同签署、租金账单、报修和消息通知等核心接口。

## 默认演示环境

项目默认使用 H2 内存数据库自举，启动后会自动创建表并写入演示数据，无需提前安装 MySQL。

- 后端地址：`http://localhost:8042`
- H2 控制台：`http://localhost:8042/h2-console`
- H2 JDBC URL：`jdbc:h2:mem:house_rental`
- 管理员账号：`admin / admin123`
- 房东账号：`landlord / 123456`
- 租客账号：`tenant / 123456`

## 常用命令

```bash
mvn test
mvn spring-boot:run
```

如需连接 MySQL，可先导入 `src/main/resources/sql/init.sql`，再使用：

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=mysql
```

## 前端联调

前端开发服务默认运行在 `http://localhost:3042`，会将 `/api` 和 `/uploads` 代理到 `http://localhost:8042`。
