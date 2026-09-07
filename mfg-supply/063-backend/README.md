# 063 进销存管理系统后端

## 项目说明

本项目是进销存管理系统后端服务，配套 `063-frontend` 使用。系统覆盖登录注册、用户管理、供应商、客户、商品分类、商品档案、采购单审核入库、销售单审核出库、库存流水、公告和经营看板等流程。

## 技术栈

- Spring Boot 2.7.18
- MyBatis 2.3.1
- PageHelper 1.4.7
- JDK 17
- H2 / MySQL
- JWT
- Redis 可选

## 默认运行模式

当前默认配置使用 H2 内存数据库，便于在毕业设计合集里直接启动演示，不需要提前安装 MySQL 或 Redis。

- 默认后端端口：`8063`
- H2 控制台：`http://localhost:8063/h2-console`
- H2 JDBC URL：`jdbc:h2:mem:inventory_system`
- H2 用户名：`sa`
- H2 密码：空

启动命令：

```powershell
mvn spring-boot:run
```

## MySQL 模式

如需使用 MySQL，请先确认脚本中的 `DROP DATABASE` 符合预期，再导入 `inventory_system` 初始化脚本并启用 `mysql` profile。

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
| 业务员 | `staff` | `123456` |

## 配套前端

前端目录：

```text
../063-frontend
```

前端默认开发端口为 `3063`，代理目标为 `http://localhost:8063`。

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
cd ..\063-frontend
npm run build
```

## 本轮巡检修复摘要

1. 默认环境从 MySQL/Redis/8080 强依赖改为 H2/8063 自举，并保留 MySQL profile。
2. MySQL profile 按本机约定使用 `root / 1234`，可配合 `sql/init.sql` 切换真实 MySQL。
3. 修复 MySQL 驱动坐标，升级项目编译目标到 JDK 17，增加 H2 与 Spring Boot Test 依赖。
4. 新增 H2 schema/data 初始化脚本，并显式指定 SQL 初始化编码为 UTF-8。
5. PageHelper 默认方言改为 H2，MySQL profile 单独使用 MySQL 方言。
6. JWT 拦截器兼容 `Bearer` token 与 `OPTIONS` 预检请求。
7. 默认演示环境使用本地 token 缓存兜底，MySQL profile 保留 Redis 扩展能力。
8. 未登录返回真实 HTTP `401`，无权限返回真实 HTTP `403`。
9. 用户密码字段响应脱敏，登录响应不再回传密码。
10. 前端端口改为 `3063`，代理目标改为 `http://localhost:8063`，请求头补齐 `Bearer`。
11. 新增后端冒烟测试，覆盖登录、权限、采购入库、销售出库、库存不足和登出失效主链路。
