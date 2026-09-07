# 基于SpringBoot+Vue+uniapp的电器维修系统小程序

## 项目说明

本项目是家电售后维修系统，包含 Spring Boot 后端、Vue 管理端和 uni-app 小程序端。核心流程包括用户报修、管理员派单、技师处理、维修进度、用户支付评价、公告和统计看板。

## 技术栈

- 后端：Spring Boot 2.7.14、MyBatis-Plus 3.5.3、JWT、H2、MySQL、Redis 依赖保留
- 管理端：Vue 3、Vite 5、Element Plus、Pinia、Axios、ECharts
- 小程序端：uni-app、Vue 3、Pinia
- JDK：17
- 默认后端端口：`8080`
- 管理端开发端口：`3081`

## 数据库模式

默认模式使用 H2 内存库，方便毕业设计验收时直接运行和自动化测试。

正式 MySQL 配置保留在 `src/main/resources/application-mysql.yml`：

- 数据库：`repair_db`
- 账号：`root`
- 密码：`1234`

MySQL 非破坏验证配置在 `src/main/resources/application-mysql-verify.yml`，使用临时库 `repair_081_verify`。

## 默认账号

| 角色 | 用户名 | 密码 |
| --- | --- | --- |
| 管理员 | `admin` | `123456` |
| 技师 | `tech1` | `123456` |
| 普通用户 | `user1` | `123456` |
| 普通用户 | `user2` | `123456` |

## 后端启动

默认 H2 演示模式：

```bash
mvn spring-boot:run
```

正式 MySQL 模式：

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=mysql
```

MySQL 临时验证模式：

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=mysql-verify
```

## 后端测试

```bash
mvn test
```

当前已通过 `RepairSmokeTest`，覆盖登录脱敏、权限、报修、派单、技师处理、支付评价和登出失效。

## 管理端启动

在 `081-frontend` 目录执行：

```bash
npm install
npm run dev -- --host 127.0.0.1
```

管理端默认运行在 `http://127.0.0.1:3081/`，代理 `/api` 到 `http://localhost:8080`。如需覆盖后端地址，可设置 `VITE_API_TARGET`。

## 管理端构建

```bash
npm run build
```

当前构建已通过，仅保留 Vite chunk 体积提示，不影响运行。
