# 055 企业 OA 管理系统后端

本项目是企业 OA 管理系统的 Spring Boot 后端，当前已调整为默认 H2 内存库自举，便于毕业设计合集在本机直接启动、测试和演示；如需连接 MySQL，可切换 `mysql` profile。

## 技术栈

- Spring Boot 3.2.0
- MyBatis-Plus 3.5.5
- JWT
- H2 / MySQL
- JDK 17

## 默认端口

- 后端服务：`8055`
- H2 控制台：`http://localhost:8055/h2-console`
- 前端开发端口：`3055`

## 默认账号

| 角色 | 用户名 | 密码 |
| --- | --- | --- |
| 管理员 | `admin` | `admin123` |
| 部门经理 | `manager` | `manager123` |
| 普通员工 | `employee` | `employee123` |
| HR 主管 | `hr` | `hr123456` |
| 后端工程师 | `zhangsan` | `123456` |
| 前端工程师 | `lisi` | `123456` |
| 财务专员 | `wangwu` | `123456` |

## 快速启动

```bash
mvn spring-boot:run
```

默认配置使用 H2 内存库，启动时自动执行：

- `src/main/resources/db/schema-h2.sql`
- `src/main/resources/db/data-h2.sql`

H2 控制台连接信息：

- JDBC URL：`jdbc:h2:mem:oa_system`
- 用户名：`sa`
- 密码：留空

## MySQL 模式

如需使用 MySQL，请先导入原始脚本：

```bash
mysql -uroot -p < sql/init.sql
```

再使用 MySQL profile 启动：

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=mysql
```

MySQL 连接配置位于：

- `src/main/resources/application-mysql.yml`

## 前端联调

前端目录：`../055-frontend`

```bash
npm install
npm run dev
```

前端开发服务默认运行在 `http://localhost:3055`，Vite 代理会把 `/api` 转发到 `http://localhost:8055`。

## 验证命令

```bash
mvn test
```

当前已新增 `OaApplicationSmokeTest`，覆盖默认 H2 自举、管理员/员工登录、未登录拦截、员工越权访问、统计、部门、公告、请假、审批和考勤打卡等核心链路。

前端构建验证：

```bash
cd ../055-frontend
npm run build
```

## 已修复要点

1. 后端默认端口从 `8080` 调整为 `8055`，避免合集项目端口冲突。
2. 默认运行环境改为 H2 自举，保留 MySQL profile。
3. 修复 JDK 17 下旧版 JJWT 依赖风险，升级到 JJWT 0.12 API。
4. 修复多个 Controller 与 Service 方法签名不一致导致的 Maven 编译失败。
5. 修复前后端接口路径不一致：考勤打卡、会议室、工作日志、请假审批。
6. 登录响应和用户信息响应不再回传密码字段。
7. JWT 拦截器兼容 `Bearer` token 与 `OPTIONS` 预检请求。
8. JWT 拦截器补充用户存在/启用校验和基础管理员权限校验。
9. 前端开发端口改为 `3055`，代理目标改为 `8055`。

## 仍需注意

1. 当前密码仍为明文存储，生产环境建议改为 BCrypt。
2. Redis 配置保留为扩展项，默认演示链路不依赖 Redis。
3. 当前权限模型按毕业设计演示场景做了基础管理员拦截，复杂岗位审批流仍可继续细化。
4. 文档上传使用本地 `.tmp/055-uploads/`，生产部署应替换为持久化文件存储。
