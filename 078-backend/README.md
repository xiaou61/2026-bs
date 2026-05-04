# 网上团购系统后端

## 项目说明

本项目为 `078 网上团购系统` 后端，提供用户、商家、分类、商品、团购活动、购物车、订单、评价、公告和统计接口。

## 技术栈

- Spring Boot 2.7.18
- MyBatis-Plus 3.5.2
- JWT
- 默认 H2 内存数据库
- MySQL profile 保留
- JDK 17

## 默认运行

```bash
mvn spring-boot:run
```

- 默认端口：`8078`
- H2 控制台：`http://localhost:8078/h2-console`
- H2 JDBC URL：`jdbc:h2:mem:group_buy_078`
- 默认初始化脚本：`src/main/resources/db/schema-h2.sql`、`src/main/resources/db/data-h2.sql`

## MySQL 运行

如需使用原 MySQL 数据库：

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=mysql
```

MySQL 配置文件为 `src/main/resources/application-mysql.yml`，初始化脚本为 `sql/init.sql`。

## 默认账号

| 角色 | 用户名 | 密码 |
| --- | --- | --- |
| 管理员 | `admin` | `123456` |
| 商家一 | `merchant` | `123456` |
| 商家二 | `merchant2` | `123456` |
| 普通用户 | `user` | `123456` |
| 普通用户二 | `user2` | `123456` |
| 普通用户三 | `user3` | `123456` |

## 验证命令

```bash
mvn test
```

当前已通过 `GroupBuyApplicationSmokeTest`，覆盖登录脱敏、未登录 `401`、越权 `403`、购物车下单、订单支付/发货/收货、评价、管理员评价分页和登出失效。

