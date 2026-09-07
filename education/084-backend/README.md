# 教学资料管理系统

## 项目说明

本项目面向高校和培训机构的教学资源管理场景，提供教学资料上传、分类、审核、检索、下载、收藏、学习清单、公告和统计看板能力。

## 技术栈

- 后端：Spring Boot 2.7.18、MyBatis、PageHelper、JWT、H2、MySQL、Redis 依赖保留
- 前端：Vue 3、Vite 5、Element Plus、Pinia、Axios、ECharts
- JDK：17
- 默认后端端口：`8084`
- 前端开发端口：`3084`

## 数据库模式

默认模式使用 H2 内存库，方便毕业设计验收时直接运行和自动化测试。

正式 MySQL 配置保留在 `src/main/resources/application-mysql.yml`：

- 数据库：`teachres_db`
- 账号：`root`
- 密码：`1234`

MySQL 非破坏验证配置在 `src/main/resources/application-mysql-verify.yml`，使用临时库 `teachres_084_verify`。原始 `sql/init.sql` 包含 `DROP DATABASE IF EXISTS teachres_db`，不要在自动化巡检中直接反复执行正式库脚本。

## 默认账号

| 角色 | 用户名 | 密码 |
| --- | --- | --- |
| 管理员 | `admin` | `123456` |
| 教师 | `teacher` | `123456` |
| 学生 | `student` | `123456` |

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

当前已通过 `TeachResApplicationSmokeTest`，覆盖登录脱敏、统计权限、教师资料上传归属、审核权限、下载收藏和登出失效。

## 前端启动

在 `084-frontend` 目录执行：

```bash
npm install
npm run dev -- --host 127.0.0.1
```

前端默认运行在 `http://127.0.0.1:3084/`，代理 `/api` 到 `http://127.0.0.1:8084`。如需覆盖后端地址，可设置 `VITE_API_TARGET`。

## 前端构建

```bash
npm run build
```

当前构建已通过，仅保留 Vite chunk 体积提示，不影响运行。
