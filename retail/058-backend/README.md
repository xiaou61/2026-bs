# 058 鲜牛奶订购系统后端

## 项目说明

本项目是社区鲜牛奶订购配送管理系统后端服务，配套 `058-frontend` 管理端和用户端页面使用。系统覆盖牛奶分类、产品展示、订阅计划、收货地址、订单、配送任务、投诉反馈、通知和后台统计等流程。

## 技术栈

- Spring Boot 2.7.12
- MyBatis-Plus 3.5.3.1
- JDK 17
- H2 / MySQL
- JWT
- Redis 可选

## 默认运行模式

当前默认配置使用 H2 内存数据库，便于在毕业设计合集里直接启动演示，不需要提前安装 MySQL 或 Redis。

- 默认后端端口：`8058`
- H2 控制台：`http://localhost:8058/h2-console`
- H2 JDBC URL：`jdbc:h2:mem:milk_order`
- H2 用户名：`sa`
- H2 密码：空

启动命令：

```powershell
mvn spring-boot:run
```

## MySQL 模式

如需使用 MySQL，请先导入原始脚本创建并初始化 `milk_order` 数据库，再启用 `mysql` profile。

```powershell
mysql -uroot -p1234 < sql/init.sql
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
| 管理员 | `admin` | `123456` |
| 配送员 | `delivery` | `123456` |
| 普通用户 | `user` | `123456` |

## 配套前端

前端目录：

```text
../058-frontend
```

前端默认开发端口为 `3058`，代理目标为 `http://localhost:8058`。

```powershell
npm install --no-audit --no-fund
npm run dev
```

## 验证命令

后端自动化测试：

```powershell
mvn test
```

前端生产构建：

```powershell
cd ..\058-frontend
npm run build
```

## 本轮巡检修复摘要

1. 默认环境从 MySQL/Redis/8080 强依赖改为 H2/8058 自举，并保留 MySQL profile。
2. MySQL profile 按本机约定使用 `root / 1234`，可直接配合 `sql/init.sql` 切换真实 MySQL。
3. 增加 H2 与 Spring Boot Test 依赖，新增 H2 schema/data 初始化脚本。
4. MyBatis-Plus 分页插件改为自动识别数据库方言，兼容 H2 与 MySQL。
5. JWT 拦截器兼容 `Bearer` token、`OPTIONS` 预检请求，并返回真实 HTTP `401/403`。
6. JWT 拦截器校验用户存在和启用状态，并补充管理员、配送员、普通用户基础权限隔离。
7. 用户密码字段响应脱敏，新增用户默认密码补齐为 `123456`。
8. Redis 不可用时不阻断默认演示登录流程。
9. 前端端口改为 `3058`，代理目标改为 `http://localhost:8058`，请求头补齐 `Bearer`。
10. 新增后端冒烟测试，覆盖游客商品、登录脱敏、权限拦截、后台管理、用户订阅、配送任务和投诉处理主链路。
