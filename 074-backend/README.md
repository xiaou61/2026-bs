# 074 手工艺品销售系统后端

## 项目说明

本项目为 `074 - 手工艺品销售系统` 后端，技术栈为 Spring Boot 2.7.18、MyBatis-Plus、JWT、H2/MySQL。当前默认运行模式已调整为 H2 内存库自举，便于毕业设计答辩、本机演示和自动化验证；原 MySQL 部署入口保留在 `application-mysql.yml`。

## 默认端口

- 后端端口：`8074`
- H2 控制台：`http://localhost:8074/h2-console`
- H2 JDBC URL：`jdbc:h2:mem:craft_sales_074`

## 默认账号

| 角色 | 用户名 | 密码 | 说明 |
| --- | --- | --- | --- |
| 管理员 | `admin` | `123456` | 平台管理、看板、用户/分类/商品/订单/申诉/公告管理 |
| 卖家 | `seller1` | `123456` | 发布和管理本人作品，处理本人售出订单 |
| 卖家 | `seller2` | `123456` | 发布和管理本人作品，处理本人售出订单 |
| 买家 | `buyer1` | `123456` | 浏览、下单、支付、收藏、评价和申诉 |

## 常用命令

```bash
mvn test
mvn spring-boot:run
```

默认会使用 H2 自举数据启动。若需使用 MySQL，请先确认 `sql/init.sql` 中的建库和删库语句可接受，再导入脚本并使用：

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=mysql
```

## 前端联调

前端目录为 `../074-frontend`，默认开发端口为 `3074`，代理到 `http://localhost:8074`。

```bash
cd ../074-frontend
npm install
npm run dev -- --host 127.0.0.1
```

如需覆盖代理目标，可设置：

```bash
set VITE_API_TARGET=http://localhost:8074
```

## 本轮验证结果

- `mvn test`：通过，覆盖登录脱敏、未登录 `401`、越权 `403`、下单/支付/发货/完成、评价、收藏、申诉和登出失效。
- `npm run build`：通过，仅保留 Vite CJS API 和 chunk 体积提示。
- 后端 `8074` 真实 HTTP 抽测：通过。
- 前端 `3074` 页面响应和代理登录：通过。

