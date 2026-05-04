# 073 人事管理系统后端

## 默认启动

默认使用 H2 内存库自举，不依赖本机 MySQL / Redis。

```bash
mvn spring-boot:run
```

- 后端端口：`8073`
- H2 控制台：`http://localhost:8073/h2-console`
- JDBC URL：`jdbc:h2:mem:hrm_system_073`
- 用户名：`sa`
- 密码：空

## 默认账号

| 角色 | 用户名 | 密码 |
| --- | --- | --- |
| 管理员 | `admin` | `123456` |
| HR | `hr` | `123456` |
| 员工 | `zhangsan` | `123456` |

## 前端联调

```bash
cd ../073-frontend
npm install
npm run dev
```

- 前端端口：`3073`
- 默认代理目标：`http://localhost:8073`
- 可通过环境变量覆盖：`VITE_API_TARGET=http://localhost:8073`

## MySQL 模式

MySQL 配置保留在 `src/main/resources/application-mysql.yml`。原始初始化脚本位于 `sql/init.sql`，其中包含 `DROP DATABASE`，执行前请确认本机数据可被覆盖。

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=mysql
```

## 验证命令

```bash
mvn test
```

当前冒烟测试覆盖默认 H2 启动、登录脱敏、真实 HTTP `401/403`、HR 管理权限、员工考勤打卡、员工请假归属、防越权和登出失效。
