# 059 制造装备物联及生产管理 ERP 系统后端

## 项目说明

本项目是制造装备物联及生产管理 ERP 系统后端服务，配套 `059-frontend` 管理端使用。系统覆盖用户权限、设备台账、传感器数据、告警处理、产品档案、生产工单、物料库存、质量检测、维保计划、维保记录和数据看板等流程。

## 技术栈

- Spring Boot 2.7.18
- MyBatis-Plus 3.5.4
- JDK 17
- H2 / MySQL
- JWT
- Redis 可选

## 默认运行模式

当前默认配置使用 H2 内存数据库，便于在毕业设计合集里直接启动演示，不需要提前安装 MySQL 或 Redis。

- 默认后端端口：`8059`
- H2 控制台：`http://localhost:8059/h2-console`
- H2 JDBC URL：`jdbc:h2:mem:mfg_erp`
- H2 用户名：`sa`
- H2 密码：空

启动命令：

```powershell
mvn spring-boot:run
```

## MySQL 模式

如需使用 MySQL，请先导入原始脚本创建并初始化 `mfg_erp` 数据库，再启用 `mysql` profile。

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
| 生产经理 | `manager` | `123456` |
| 设备操作员 | `operator` | `123456` |
| 质检员 | `inspector` | `123456` |

## 配套前端

前端目录：

```text
../059-frontend
```

前端默认开发端口为 `3059`，代理目标为 `http://localhost:8059`。

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
cd ..\059-frontend
npm run build
```

## 本轮巡检修复摘要

1. 默认环境从 MySQL/Redis/8080 强依赖改为 H2/8059 自举，并保留 MySQL profile。
2. MySQL profile 按本机约定使用 `root / 1234`，可直接配合 `sql/init.sql` 切换真实 MySQL。
3. 增加 H2 与 Spring Boot Test 依赖，新增 H2 schema/data 初始化脚本。
4. MyBatis-Plus 分页插件改为自动识别数据库方言，兼容 H2 与 MySQL。
5. JWT 拦截器兼容 `Bearer` token、`OPTIONS` 预检请求，并返回真实 HTTP `401/403`。
6. JWT 拦截器校验用户存在和启用状态，并补充管理员、生产经理、设备操作员、质检员基础权限隔离。
7. 用户密码字段响应脱敏，新增用户默认密码补齐为 `123456`。
8. Redis 不可用时不阻断默认演示登录和退出流程。
9. 前端端口改为 `3059`，代理目标改为 `http://localhost:8059`，请求头补齐 `Bearer`。
10. 新增后端冒烟测试，覆盖登录脱敏、权限拦截、设备、传感器、告警、生产、物料、质检、维保和看板主链路。
