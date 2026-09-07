# 039 民歌民谣交流平台后端

## 项目简介

本项目是基于 Spring Boot 3.2、Spring Data JDBC、Spring Security、JWT 的民歌民谣交流平台后端，提供用户注册登录、民歌分类、民歌发布审核、点赞收藏、评论、公告和后台管理等接口。

## 默认演示环境

项目默认使用 H2 内存数据库自举，启动后会自动创建表并写入演示数据，无需提前安装 PostgreSQL。

- 后端地址：`http://localhost:8039`
- H2 控制台：`http://localhost:8039/h2-console`
- H2 JDBC URL：`jdbc:h2:mem:folksong_platform`
- 管理员账号：`admin / 123456`
- 普通用户账号：`user / 123456`

## 常用命令

```bash
mvn test
mvn spring-boot:run
```

如需连接 PostgreSQL，可先导入 `sql/init.sql`，再使用：

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=postgresql
```

## 前端联调

前端开发服务默认运行在 `http://localhost:3039`，会将 `/api` 与 `/uploads` 代理到 `http://localhost:8039`。
