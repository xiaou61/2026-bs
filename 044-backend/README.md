# 044 特色民宿预订平台后端

## 项目简介

本项目是基于 Spring Boot 3.2、MyBatis-Plus、Spring Security、JWT 的特色民宿预订平台后端，提供用户登录注册、民宿浏览搜索、房型查看、收藏、预订、房东民宿管理和房东订单确认等核心接口。

## 默认演示环境

项目默认使用 H2 内存数据库自举，启动后会自动创建表并写入演示数据，无需提前安装 MySQL 或 Redis。

- 后端地址：`http://localhost:8044`
- 接口文档：`http://localhost:8044/doc.html`
- H2 控制台：`http://localhost:8044/h2-console`
- H2 JDBC URL：`jdbc:h2:mem:homestay_booking`
- 管理员账号：`admin / admin123`
- 房东账号：`host1 / 123456`
- 游客账号：`guest1 / 123456`

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

前端开发服务默认运行在 `http://localhost:3044`，会将 `/api` 代理到 `http://localhost:8044`。
