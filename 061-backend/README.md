# 061 游戏交易系统后端

## 项目说明

本项目是游戏交易系统后端服务，配套 `061-frontend` 使用。系统覆盖登录注册、游戏分类、交易商品、下单支付、卖家发货、买家确认、收藏评价、交易申诉、公告和运营看板等流程。

## 技术栈

- Spring Boot 2.7.18
- MyBatis-Plus 3.5.5
- JDK 17
- H2 / MySQL
- JWT
- Redis 可选

## 默认运行模式

当前默认配置使用 H2 内存数据库，便于在毕业设计合集里直接启动演示，不需要提前安装 MySQL 或 Redis。

- 默认后端端口：`8061`
- H2 控制台：`http://localhost:8061/h2-console`
- H2 JDBC URL：`jdbc:h2:mem:game_trade`
- H2 用户名：`sa`
- H2 密码：空

启动命令：

```powershell
mvn spring-boot:run
```

## MySQL 模式

如需使用 MySQL，请先确认脚本中的 `DROP DATABASE` 符合预期，再导入 `game_trade` 初始化脚本并启用 `mysql` profile。

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
| 普通用户 | `user3` | `123456` |

## 配套前端

前端目录：

```text
../061-frontend
```

前端默认开发端口为 `3061`，代理目标为 `http://localhost:8061`。

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
cd ..\061-frontend
npm run build
```

## 本轮巡检修复摘要

1. 默认环境从 MySQL/Redis/8080 强依赖改为 H2/8061 自举，并保留 MySQL profile。
2. MySQL profile 按本机约定使用 `root / 1234`，可配合 `sql/init.sql` 切换真实 MySQL。
3. 修复 MySQL 驱动坐标，增加 H2 与 Spring Boot Test 依赖。
4. 新增 H2 schema/data 初始化脚本，并将演示订单和公告日期更新到 2026 年 4 月/5 月。
5. MyBatis-Plus 分页插件改为自动识别数据库方言，兼容默认 H2 与 MySQL profile。
6. JWT 拦截器兼容 `Bearer` token、`OPTIONS` 预检请求，并返回真实 HTTP `401/403`。
7. JWT 拦截器校验用户存在和启用状态，Redis 不可用时不阻断默认演示登录和鉴权。
8. 用户密码字段响应脱敏，登录响应不再回传密码。
9. 权限异常统一返回 HTTP `403`，覆盖管理员接口和订单/商品/评价归属校验。
10. 前端端口改为 `3061`，代理目标改为 `http://localhost:8061`，请求头补齐 `Bearer`。
11. 新增后端冒烟测试，覆盖登录脱敏、权限拦截、商品、收藏、下单支付、发货完成、评价、申诉、公告和看板主链路。
