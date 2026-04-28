# 054 农业生产技术管理系统后端

## 项目说明

本项目是 `054 基于 SpringBoot 的农业生产技术管理系统` 的后端，提供用户登录、作物管理、农业知识库、生产计划与任务、病虫害预警、农资库存、技术咨询、专家预约、公告和统计接口。

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

- 后端接口：`http://localhost:8054`
- H2 控制台：`http://localhost:8054/h2-console`
- H2 JDBC URL：`jdbc:h2:mem:agriculture_tech`
- H2 用户名：`sa`
- H2 密码：空

## 默认账号

| 角色 | 用户名 | 密码 | 说明 |
| --- | --- | --- | --- |
| 管理员 | `admin` | `123456` | 可访问统计、用户管理和公告管理 |
| 专家 | `expert` | `123456` | 演示专家咨询角色 |
| 技术员 | `tech` | `123456` | 演示农技服务角色 |
| 农户 | `farmer` | `123456` | 演示生产管理和咨询角色 |

## 前端启动

前端目录：`../054-frontend`

```bash
cd ../054-frontend
npm install --no-audit --no-fund
npm run dev
```

默认前端端口：`http://localhost:3054`

Vite 代理目标：`http://localhost:8054`

## MySQL 模式

默认演示推荐使用 H2。如需切换到 MySQL：

1. 创建数据库 `agriculture_tech`。
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
cd ../054-frontend
npm run build
```

## 本轮巡检修复摘要

- 修复 `ConsultationService` 使用查询 wrapper 执行更新导致的 Maven 编译失败。
- 默认端口调整为 `8054`，避免和其他项目抢占 `8080`。
- 默认数据库改为 H2 自举，新增 `schema-h2.sql` 和 `data-h2.sql`。
- 新增 `application-mysql.yml`，保留 MySQL 部署方式。
- 新增后端冒烟测试，覆盖登录、权限、用户脱敏、分类与统计接口。
- MyBatis-Plus 分页插件改为自动识别数据库方言。
- JWT 拦截器兼容 `Bearer` token 和 `OPTIONS` 预检请求。
- 管理员接口补充服务端角色校验，普通农户访问统计接口返回 `403`。
- 用户密码改为请求可写、响应不回传，并修复修改密码接口假成功问题。
- 前端端口改为 `3054`，代理目标改为 `http://localhost:8054`。
- 修复前端登录提示、`Bearer` 请求头、角色枚举大小写和管理员菜单显示问题。
- 新增前端 `package-lock.json`，保证依赖安装可复现。
