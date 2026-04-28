# 053 救灾物资调度与救援系统后端

## 项目说明

本项目是 `053 基于 SpringBoot 的救灾物资调度与救援系统` 的后端，提供用户登录、灾情上报、物资分类、物资库存、仓库管理、物资调度、救援任务、公告和统计接口。

## 技术栈

- Spring Boot 3.2.0
- MyBatis-Plus 3.5.5
- JWT
- H2 默认演示数据库
- MySQL 生产兼容配置

## 默认启动

默认配置使用 H2 内存数据库，可直接启动，不需要提前安装 MySQL 或 Redis。

```bash
mvn spring-boot:run
```

默认访问地址：

- 后端接口：`http://localhost:8053`
- H2 控制台：`http://localhost:8053/h2-console`
- H2 JDBC URL：`jdbc:h2:mem:disaster_rescue`
- H2 用户名：`sa`
- H2 密码：空

## 默认账号

| 角色 | 用户名 | 密码 | 说明 |
| --- | --- | --- | --- |
| 管理员 | `admin` | `123456` | 可访问统计和用户管理 |
| 仓库管理员 | `warehouse` | `123456` | 演示仓库角色 |
| 救援人员 | `rescuer` | `123456` | 演示救援角色 |
| 信息员 | `reporter` | `123456` | 可上报灾情 |

## 前端启动

前端目录：`../053-frontend`

```bash
cd ../053-frontend
npm install --no-audit --no-fund
npm run dev
```

默认前端端口：`http://localhost:3053`

Vite 代理目标：`http://localhost:8053`

## MySQL 模式

默认演示推荐使用 H2。如需切换到 MySQL：

1. 创建数据库 `disaster_rescue`。
2. 导入 `sql/init.sql`。
3. 按需修改 `src/main/resources/application-mysql.yml` 中的数据库账号密码。
4. 使用 MySQL profile 启动。

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=mysql
```

## 验证命令

后端：

```bash
mvn clean test
```

前端：

```bash
cd ../053-frontend
npm run build
```

## 本轮巡检修复摘要

- 默认端口调整为 `8053`，避免和其他项目抢占 `8080`。
- 默认数据库改为 H2 自举，新增 `schema-h2.sql` 和 `data-h2.sql`。
- 新增 `application-mysql.yml`，保留 MySQL 部署方式。
- 新增后端冒烟测试，覆盖登录、权限、统计、灾情、库存、调度、任务和公告。
- MyBatis-Plus 分页插件改为自动识别数据库方言。
- 用户响应脱敏，登录和用户信息接口不再返回密码字段。
- 管理员接口补充角色校验，普通信息员访问统计接口返回 `403`。
- 补齐前端实际调用的用户管理和任务状态接口。
- 前端端口改为 `3053`，代理目标改为 `http://localhost:8053`。
- 修复前端登录 token 存储、`Bearer` 请求头、接口路径和灾情字段适配问题。
