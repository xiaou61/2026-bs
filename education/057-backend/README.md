# 057 招生管理系统后端

## 项目说明

本项目是高校招生管理系统后端服务，配套 `057-frontend` 管理端使用。系统覆盖院系、专业、招生计划、考生、报名申请、成绩、录取、分数线、公告和统计分析等招生管理流程。

## 技术栈

- Spring Boot 2.7.0
- MyBatis-Plus 3.5.2
- JDK 17
- H2 / MySQL
- JWT
- Redis 可选

## 默认运行模式

当前默认配置使用 H2 内存数据库，便于在毕业设计合集里直接启动演示，不需要提前安装 MySQL 或 Redis。

- 默认后端端口：`8057`
- H2 控制台：`http://localhost:8057/h2-console`
- H2 JDBC URL：`jdbc:h2:mem:enrollment_db`
- H2 用户名：`sa`
- H2 密码：空

启动命令：

```powershell
mvn spring-boot:run
```

## MySQL 模式

如需使用 MySQL，请先创建并初始化 `enrollment_db` 数据库，再启用 `mysql` profile。

```powershell
mvn spring-boot:run -Dspring-boot.run.profiles=mysql
```

MySQL 配置文件位于：

```text
src/main/resources/application-mysql.yml
```

原始 MySQL 脚本位于：

```text
sql/init.sql
```

## 默认账号

| 角色 | 用户名 | 密码 |
| --- | --- | --- |
| 超级管理员 | `admin` | `123456` |
| 普通管理员 | `user` | `123456` |

## 配套前端

管理端目录：

```text
../057-frontend
```

管理端默认开发端口为 `3057`，代理目标为 `http://localhost:8057`。

```powershell
npm install --no-audit --no-fund
npm run dev
```

## 验证命令

后端自动化测试：

```powershell
mvn test
```

管理端生产构建：

```powershell
cd ..\057-frontend
npm run build
```

## 本轮巡检修复摘要

1. 默认环境从 MySQL/8080 强依赖改为 H2/8057 自举，并保留 MySQL profile。
2. 增加 H2 与 Spring Boot Test 依赖，新增 H2 schema/data 初始化脚本。
3. MyBatis-Plus 分页插件改为自动识别数据库方言，兼容 H2 与 MySQL。
4. JWT 拦截器兼容 `Bearer` token、`OPTIONS` 预检请求，并返回真实 HTTP `401/403`。
5. JWT 拦截器校验管理员存在和启用状态，并限制普通管理员访问管理员管理接口。
6. 管理员密码字段响应脱敏，修改密码补充用户不存在和新密码为空校验。
7. Redis 不可用时不阻断默认演示登录和退出流程。
8. 管理端端口改为 `3057`，代理目标改为 `http://localhost:8057`，请求头补齐 `Bearer`。
9. 新增后端冒烟测试，覆盖登录、权限、统计、院系、专业、计划、考生、成绩、报名、录取和公告主链路。
