# 069 科任教师考评系统后端

## 项目说明

本项目是科任教师考评系统后端服务，配套 `069-frontend` 使用。系统覆盖统一登录、用户权限、科目/班级/教师档案、评价指标、考评任务、学生评教、教师申诉、公告和统计看板等流程。

## 技术栈

- Spring Boot 2.7.18
- MyBatis-Plus 3.5.5
- JDK 17
- H2 / MySQL
- JWT
- Redis 可选
- Hutool

## 默认运行模式

当前默认配置使用 H2 内存数据库，便于在毕业设计合集里直接启动演示，不需要提前安装 MySQL 或 Redis。

- 默认后端端口：`8069`
- H2 控制台：`http://localhost:8069/h2-console`
- H2 JDBC URL：`jdbc:h2:mem:teacher_eval_069`
- H2 用户名：`sa`
- H2 密码：空

启动命令：

```powershell
mvn spring-boot:run
```

## MySQL 模式

如需使用 MySQL，请先确认脚本中的建库/删库语句符合预期，再导入原始初始化脚本并启用 `mysql` profile。

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
| 教师 | `teacher1` | `123456` |
| 学生 | `student1` | `123456` |

## 配套前端

前端目录：

```text
../069-frontend
```

前端默认开发端口为 `3069`，代理目标为 `http://localhost:8069`。

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
cd ..\069-frontend
npm run build
```

## 本轮巡检修复摘要

1. 将非法包名 `com.teacher.new` 迁移为 `com.teacher.eval`，修复 Java 关键字导致的编译失败。
2. 删除混入的 068 周边游平台源码副本，保留 069 科任教师考评系统业务模块。
3. 默认环境从 MySQL/Redis/8080 强依赖改为 H2/8069 自举，并保留 MySQL profile。
4. MySQL profile 按本机约定使用 `root / 1234`，可配合 `sql/init.sql` 切换真实 MySQL。
5. 修复 MySQL 驱动坐标，升级项目编译目标到 JDK 17，增加 H2 与 Spring Boot Test 依赖。
6. 新增 H2 schema/data 初始化脚本，并显式指定 SQL 初始化编码为 UTF-8。
7. MyBatis-Plus 分页方言默认使用 H2，MySQL profile 单独使用 MySQL 方言。
8. 默认演示环境使用本地 token 缓存兜底，MySQL profile 保留 Redis 扩展能力。
9. JWT 拦截器兼容 `Bearer` token，并在鉴权时校验用户状态和本地/Redis token。
10. 未登录返回真实 HTTP `401`，无权限返回真实 HTTP `403`。
11. 用户密码字段响应脱敏，登录响应不再回传密码。
12. 前端端口改为 `3069`，代理目标改为 `http://localhost:8069`，请求头补齐 `Bearer`。
13. 新增后端冒烟测试，覆盖登录、权限、基础档案、任务、评教、申诉、公告、看板和登出失效主链路。
