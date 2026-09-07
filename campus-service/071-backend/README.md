# 071 基于SpringBoot和Vue的共享单车系统

## 默认启动

后端默认使用 H2 内存库，可直接启动：

```bash
mvn spring-boot:run
```

- 后端地址：`http://localhost:8071`
- H2 控制台：`http://localhost:8071/h2-console`
- JDBC URL：`jdbc:h2:mem:bike_system_071`

## 默认账号

| 角色 | 用户名 | 密码 |
| --- | --- | --- |
| 管理员 | `admin` | `123456` |
| 运维人员 | `operator` | `123456` |
| 普通用户 | `user` | `123456` |
| 测试用户 | `test` | `123456` |

## 前端启动

```bash
cd ../071-frontend
npm install
npm run dev
```

- 前端地址：`http://localhost:3071`
- 默认代理：`http://localhost:8071`
- 如需覆盖代理目标：`VITE_API_TARGET=http://127.0.0.1:8071 npm run dev`

## 验证命令

```bash
mvn test
cd ../071-frontend
npm run build
```

本轮已通过后端自动化冒烟测试、前端构建、后端真实启动抽测和前端代理登录联调。

## MySQL 模式

默认不依赖 MySQL / Redis。需要使用 MySQL 时：

1. 确认 `sql/init.sql` 中的 `DROP DATABASE` 可接受。
2. 导入初始化脚本：`mysql -uroot -p1234 < sql/init.sql`
3. 使用 MySQL profile 启动：`mvn spring-boot:run -Dspring-boot.run.profiles=mysql`

MySQL profile 默认使用 `root / 1234`，并启用 Redis token 存储。
