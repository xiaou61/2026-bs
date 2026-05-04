# 公考学习平台

## 项目说明

本项目是面向公务员考试备考场景的学习平台，包含 Spring Boot 后端和 Vue 管理端。核心流程包括用户登录注册、学科课程、题库试题、模拟试卷、考试记录、学习计划、公告和统计看板。

## 技术栈

- 后端：Spring Boot 2.7.14、MyBatis-Plus 3.5.3、JWT、H2、MySQL、Redis 依赖保留
- 前端：Vue 3、Vite 5、Element Plus、Pinia、Axios、ECharts
- JDK：17
- 默认后端端口：`8082`
- 前端开发端口：`3082`

## 数据库模式

默认模式使用 H2 内存库，方便毕业设计验收时直接运行和自动化测试。

正式 MySQL 配置保留在 `src/main/resources/application-mysql.yml`：

- 数据库：`gongkao_db`
- 账号：`root`
- 密码：`1234`

MySQL 非破坏验证配置在 `src/main/resources/application-mysql-verify.yml`，使用临时库 `gongkao_082_verify`。原始 `sql/init.sql` 包含 `DROP DATABASE IF EXISTS gongkao_db`，不要在自动化巡检中直接反复执行正式库脚本。

## 默认账号

| 角色 | 用户名 | 密码 |
| --- | --- | --- |
| 管理员 | `admin` | `123456` |
| 讲师 | `teacher` | `123456` |
| 学员 | `user` | `123456` |

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

当前已通过 `GongkaoApplicationSmokeTest`，覆盖登录脱敏、管理员统计、学员越权、学习计划归属、讲师课程归属和登出失效。

## 前端启动

在 `082-frontend` 目录执行：

```bash
npm install
npm run dev -- --host 127.0.0.1
```

前端默认运行在 `http://127.0.0.1:3082/`，代理 `/api` 到 `http://localhost:8082`。如需覆盖后端地址，可设置 `VITE_API_TARGET`。

## 前端构建

```bash
npm run build
```

当前构建已通过，仅保留 Vite chunk 体积提示，不影响运行。
