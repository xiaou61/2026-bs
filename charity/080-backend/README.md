# 基于SpringBoot和Vue的贫困地区儿童资助网站

## 项目说明

本项目是贫困地区儿童资助管理平台，后端位于 `080-backend`，前端位于 `080-frontend`。系统包含用户登录、儿童档案、资助申请、捐赠管理、资金记录、项目管理、公告、成长记录、反馈和统计看板等模块。

## 技术栈

- 后端：Spring Boot 2.7.14、MyBatis-Plus 3.5.3、JWT、H2、MySQL、Redis 依赖保留
- 前端：Vue 3、Vite 5、Element Plus、Pinia、Axios、ECharts
- JDK：17
- 默认后端端口：`8080`
- 前端开发端口：`3080`

## 数据库模式

默认模式使用 H2 内存库，方便毕业设计验收时直接运行和自动化测试。

正式 MySQL 配置保留在 `src/main/resources/application-mysql.yml`：

- 数据库：`charity_db`
- 账号：`root`
- 密码：`1234`

MySQL 非破坏验证配置在 `src/main/resources/application-mysql-verify.yml`，使用临时库 `charity_080_verify`，用于巡检时验证 MySQL 兼容性。

> 默认不直接拿 `charity_db` 做自动化重建，是因为原始 `sql/init.sql` 包含 `DROP DATABASE IF EXISTS charity_db`。H2 用于安全回归，MySQL profile 用于真实部署和兼容验证。

## 默认账号

| 角色 | 用户名 | 密码 |
| --- | --- | --- |
| 管理员 | `admin` | `123456` |
| 捐赠人 | `donor` | `123456` |
| 志愿者 | `volunteer` | `123456` |
| 企业捐赠人 | `donor2` | `123456` |

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

当前已通过 `CharitySmokeTest`，覆盖登录脱敏、未登录拦截、越权拦截、捐赠 donorId 防伪造、管理员确认捐赠和登出失效。

## 前端启动

在 `080-frontend` 目录执行：

```bash
npm install
npm run dev -- --host 127.0.0.1
```

前端默认运行在 `http://127.0.0.1:3080/`，代理 `/api` 到 `http://localhost:8080`。如需覆盖后端地址，可设置：

```bash
set VITE_API_TARGET=http://127.0.0.1:8080
npm run dev -- --host 127.0.0.1
```

## 前端构建

```bash
npm run build
```

当前构建已通过，仅保留 Vite chunk 体积提示，不影响运行。
