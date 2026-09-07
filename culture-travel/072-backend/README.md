# 072 哈尔滨文旅系统后端

## 默认启动

默认使用 H2 内存库自举，不依赖本机 MySQL / Redis。

```bash
mvn spring-boot:run
```

- 后端端口：`8072`
- H2 控制台：`http://localhost:8072/h2-console`
- JDBC URL：`jdbc:h2:mem:harbin_tourism_072`
- 用户名：`sa`
- 密码：空

## 默认账号

| 角色 | 用户名 | 密码 |
| --- | --- | --- |
| 管理员 | `admin` | `123456` |
| 普通用户 | `user` | `123456` |
| 普通用户 | `tourist` | `123456` |

## 前端联调

```bash
cd ../072-frontend
npm install
npm run dev
```

- 前端端口：`3072`
- 默认代理目标：`http://localhost:8072`
- 可通过环境变量覆盖：`VITE_API_TARGET=http://localhost:8072`

## MySQL 模式

MySQL 配置保留在 `src/main/resources/application-mysql.yml`。原始初始化脚本位于 `sql/init.sql`，其中包含 `DROP DATABASE`，执行前请确认本机数据可被覆盖。

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=mysql
```

## 验证命令

```bash
mvn test
```

当前冒烟测试覆盖默认 H2 启动、登录脱敏、真实 HTTP `401/403`、公开文旅列表、后台权限、购票/退款、收藏、评价、活动报名和登出失效。
