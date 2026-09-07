# 056 短文写作竞赛管理小程序后端

## 项目说明

本项目是短文写作竞赛管理小程序的后端服务，配套 `056-frontend` 管理端和 `056-miniapp` 小程序端使用。系统包含竞赛分类、竞赛发布、公告、参赛作品投稿、作品审核、评委分配、评分标准、评委评分、获奖名单与统计概览等能力。

## 技术栈

- Spring Boot 2.7.18
- MyBatis-Plus 3.5.4
- JDK 17
- H2 / MySQL
- JWT
- Hutool

## 默认运行模式

当前默认配置使用 H2 内存数据库，便于在毕业设计合集里直接启动演示，不需要提前安装或配置 MySQL。

- 默认后端端口：`8056`
- H2 控制台：`http://localhost:8056/h2-console`
- H2 JDBC URL：`jdbc:h2:mem:writing_competition`
- H2 用户名：`sa`
- H2 密码：空
- 默认上传目录：`./.tmp/056-uploads/`

启动命令：

```powershell
mvn spring-boot:run
```

## MySQL 模式

如需使用 MySQL，请先创建并初始化 `writing_competition` 数据库，再启用 `mysql` profile。

```powershell
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

默认密码均为 `123456`。

| 角色 | 用户名 | 密码 |
| --- | --- | --- |
| 管理员 | `admin` | `123456` |
| 评委 | `judge` | `123456` |
| 评委 | `judge2` | `123456` |
| 参赛者 | `user` | `123456` |
| 参赛者 | `user2` | `123456` |
| 参赛者 | `user3` | `123456` |

## 配套前端

管理端目录：

```text
../056-frontend
```

管理端默认开发端口为 `3056`，代理目标为 `http://localhost:8056`。

```powershell
npm install --no-audit --no-fund
npm run dev
```

小程序目录：

```text
../056-miniapp
```

小程序请求地址已调整为 `http://localhost:8056`，可使用 HBuilderX 或 uni-app 工具链打开运行。

## 验证命令

后端自动化测试：

```powershell
mvn test
```

管理端生产构建：

```powershell
cd ..\056-frontend
npm run build
```

## 本轮巡检修复摘要

1. 默认环境从 MySQL 强依赖改为 H2 自举，并保留 MySQL profile。
2. 后端端口调整为 `8056`，管理端端口调整为 `3056`，避免合集项目端口冲突。
3. 修复 MyBatis-Plus 分页方言固定 MySQL 导致 H2 环境不兼容的问题。
4. 修复游客访问分类接口被 JWT 拦截的问题。
5. 修复登录响应中密码字段泄露风险。
6. 补充基础管理员、评委和参赛者接口权限拦截。
7. 修复评分标准保存时 `List<Map>` 强转实体列表导致的运行时错误。
8. 修复小程序端口配置和缺失静态图片资源引用问题。
9. 新增后端冒烟测试，覆盖公开接口、登录、权限、投稿、审核和评分主链路。
