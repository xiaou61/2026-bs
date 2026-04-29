# 064 经方药食两用服务平台后端

## 项目说明

本项目是经方药食两用服务平台后端服务，配套 `064-frontend` 使用。系统覆盖登录注册、用户管理、食材分类、药食同源食材、经方信息、药膳方案、调理服务预约、体质记录、收藏、公告和数据看板等流程。

## 技术栈

- Spring Boot 2.7.18
- MyBatis-Plus 3.5.5
- JDK 17
- H2 / MySQL
- JWT
- Redis 可选
- Hutool

## 默认运行模式

当前默认配置使用 H2 内存数据库，便于在毕业设计合集里直接启动演示，不需要提前安装 MySQL 或 Redis。

- 默认后端端口：`8064`
- H2 控制台：`http://localhost:8064/h2-console`
- H2 JDBC URL：`jdbc:h2:mem:classic_diet_service`
- H2 用户名：`sa`
- H2 密码：空

启动命令：

```powershell
mvn spring-boot:run
```

## MySQL 模式

如需使用 MySQL，请先确认脚本中的建库/删库语句符合预期，再导入 `classic_diet_service` 初始化脚本并启用 `mysql` profile。

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
| 平台管理员 | `admin` | `123456` |
| 调理用户 | `user` | `123456` |

## 配套前端

前端目录：

```text
../064-frontend
```

前端默认开发端口为 `3064`，代理目标为 `http://localhost:8064`。

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
cd ..\064-frontend
npm run build
```

## 本轮巡检修复摘要

1. 默认环境从 MySQL/Redis/8080 强依赖改为 H2/8064 自举，并保留 MySQL profile。
2. MySQL profile 按本机约定使用 `root / 1234`，可配合 `sql/init.sql` 切换真实 MySQL。
3. 修复 MySQL 驱动坐标，升级项目编译目标到 JDK 17，增加 H2 与 Spring Boot Test 依赖。
4. 新增 H2 schema/data 初始化脚本，并显式指定 SQL 初始化编码为 UTF-8。
5. 默认演示环境使用本地 token 缓存兜底，MySQL profile 保留 Redis 扩展能力。
6. JWT 拦截器兼容 `Bearer` token，并在鉴权时校验用户状态和本地/Redis token。
7. 未登录返回真实 HTTP `401`，无权限返回真实 HTTP `403`。
8. 用户密码字段响应脱敏，登录响应不再回传密码。
9. 修复用户分页筛选参数为空时的 NPE 风险。
10. 修复 H2 下服务单趋势统计别名命中保留字导致的查询失败。
11. 前端端口改为 `3064`，代理目标改为 `http://localhost:8064`，请求头补齐 `Bearer`。
12. 新增后端冒烟测试，覆盖登录、权限、目录查询、预约、体质记录、收藏、看板和登出失效主链路。
