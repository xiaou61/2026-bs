# 计算机学院校友网后端

## 项目说明

本项目为 `079 计算机学院校友网` 后端，提供校友信息、新闻公告、活动报名、捐赠、校友企业、招聘、论坛、轮播图、日志和统计接口。

## 技术栈

- Spring Boot 2.7.18
- MyBatis-Plus 3.5.2
- JWT
- 默认 H2 内存数据库
- MySQL profile 保留，默认账号 `root / 1234`
- JDK 17

## 默认运行

```bash
mvn spring-boot:run
```

- 默认端口：`8079`
- H2 控制台：`http://localhost:8079/h2-console`
- H2 JDBC URL：`jdbc:h2:mem:alumni_079`
- 默认初始化脚本：`src/main/resources/db/schema-h2.sql`、`src/main/resources/db/data-h2.sql`

## MySQL 运行

如需使用正式 MySQL 数据库：

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=mysql
```

MySQL 配置文件为 `src/main/resources/application-mysql.yml`，默认连接 `alumni_db`，账号密码为 `root / 1234`。首次使用前请导入 `sql/init.sql`。

本轮另提供非破坏性验证 profile：

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=mysql-verify
```

该 profile 使用 `alumni_079_verify` 临时库并自动初始化表，用于本机 MySQL 联调验证，不会触碰正式 `alumni_db`。

## 默认账号

| 角色 | 用户名 | 密码 | 说明 |
| --- | --- | --- | --- |
| 管理员 | `admin` | `123456` | 正常 |
| 校友 | `user` | `123456` | 正常 |
| 校友 | `user2` | `123456` | 待审核 |
| 校友 | `user3` | `123456` | 正常 |
| 校友 | `user4` | `123456` | 正常 |

## 验证命令

```bash
mvn test
```

当前已通过 `AlumniApplicationSmokeTest`，覆盖登录脱敏、未登录 `401`、越权 `403`、公开列表、校友资料防篡改、活动报名、捐赠确认、企业/岗位归属、论坛归属和登出失效。
