# 060 电影订票及评论网站后端

## 项目说明

本项目是电影订票及评论网站后端服务，配套 `060-frontend` 使用。系统覆盖用户登录注册、电影浏览、影院影厅、排片、在线订票、收藏、评论、公告和数据看板等流程。

## 技术栈

- Spring Boot 2.7.18
- MyBatis Spring Boot Starter 2.3.2
- PageHelper 1.4.7
- JDK 17
- H2 / MySQL
- JWT
- Redis 可选

## 默认运行模式

当前默认配置使用 H2 内存数据库，便于在毕业设计合集里直接启动演示，不需要提前安装 MySQL 或 Redis。

- 默认后端端口：`8060`
- H2 控制台：`http://localhost:8060/h2-console`
- H2 JDBC URL：`jdbc:h2:mem:movie_ticket`
- H2 用户名：`sa`
- H2 密码：空

启动命令：

```powershell
mvn spring-boot:run
```

## MySQL 模式

如需使用 MySQL，请先确认脚本中的 `DROP DATABASE` 符合预期，再导入 `movie_ticket` 初始化脚本并启用 `mysql` profile。

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
| 普通用户 | `user1` | `123456` |
| 普通用户 | `user2` | `123456` |

## 配套前端

前端目录：

```text
../060-frontend
```

前端默认开发端口为 `3060`，代理目标为 `http://localhost:8060`。

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
cd ..\060-frontend
npm run build
```

## 本轮巡检修复摘要

1. 默认环境从 MySQL/Redis/8080 强依赖改为 H2/8060 自举，并保留 MySQL profile。
2. MySQL profile 按本机约定使用 `root / 1234`，可配合 `sql/init.sql` 切换真实 MySQL。
3. 修复 MySQL 驱动坐标和 MyBatis Starter 版本，增加 H2 与 Spring Boot Test 依赖。
4. 新增 H2 schema/data 初始化脚本，并将演示排片日期更新到 2026 年 5 月，避免当前日期下无可购场次。
5. JWT 拦截器兼容 `Bearer` token、`OPTIONS` 预检请求，并返回真实 HTTP `401/403`。
6. JWT 拦截器校验用户存在和启用状态，并补充管理员/普通用户基础权限隔离。
7. 用户密码字段响应脱敏，登录响应不再回传密码。
8. 订单支付和取消补充归属校验，普通用户不能操作他人订单。
9. 前端端口改为 `3060`，代理目标改为 `http://localhost:8060`，请求头补齐 `Bearer`。
10. 新增后端冒烟测试，覆盖登录脱敏、权限拦截、电影、影院、排片、收藏、下单、订单、评论和看板主链路。
