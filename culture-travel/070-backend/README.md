# 070 基于SpringBoot和Vue的最优网络购票系统

## 项目说明

本项目是一个电影/演出票务购票系统，包含用户端购票流程和管理端票务管理能力。核心链路包括用户登录、影片浏览、在线选座、座位锁定、订单创建、优惠券抵扣、余额支付、出票核销、评论评分和后台统计。

本轮巡检已将默认运行模式调整为 H2 内存库自举，保留 MySQL + Redis 的生产 profile，方便毕业设计合集在本机逐项启动和演示。

## 技术栈

- 后端：Spring Boot 2.7.18、MyBatis-Plus 3.5.5、JWT、H2、MySQL、Redis 可选、Hutool
- 前端：Vue 3、Vite 5、Element Plus、Pinia、Axios、ECharts
- 默认后端端口：`8070`
- 默认前端端口：`3070`

## 默认账号

| 角色 | 账号 | 密码 |
| --- | --- | --- |
| 管理员 | `admin` | `123456` |
| 普通用户 | `user` | `123456` |
| 测试用户 | `test` | `123456` |

## 默认 H2 启动

后端默认使用 H2 内存库，直接启动即可自动加载演示数据：

```bash
cd 070-backend
mvn spring-boot:run
```

启动后访问：

- 后端接口：`http://localhost:8070`
- H2 控制台：`http://localhost:8070/h2-console`
- H2 JDBC URL：`jdbc:h2:mem:ticket_system_070`
- H2 用户名：`sa`
- H2 密码：空

## MySQL 模式

如需连接本机 MySQL，可先导入原始脚本：

```bash
mysql -uroot -p1234 < sql/init.sql
```

再使用 MySQL profile 启动：

```bash
cd 070-backend
mvn spring-boot:run -Dspring-boot.run.profiles=mysql
```

MySQL profile 配置文件为 `src/main/resources/application-mysql.yml`，默认连接：

- 地址：`jdbc:mysql://localhost:3306/ticket_system_070`
- 用户名：`root`
- 密码：`1234`
- Redis：默认启用，地址 `localhost:6379`

## 前端启动

首次运行先安装依赖：

```bash
cd 070-frontend
npm install --no-audit --no-fund
```

开发启动：

```bash
npm run dev
```

生产构建：

```bash
npm run build
```

前端开发代理已指向 `http://localhost:8070`，请求拦截器会自动携带 `Bearer` token。

## 验证命令

后端测试：

```bash
cd 070-backend
mvn test
```

前端构建：

```bash
cd 070-frontend
npm run build
```

本轮已通过真实 HTTP 抽测，覆盖未登录 `401`、普通用户越权 `403`、管理员管理接口、座位锁定、下单、创建支付、余额支付、出票、核销、完成订单、评论、管理员审核评论和登出后 token 失效。

## 已知说明

1. 默认演示模式不依赖 Redis，token 和座位锁由本地运行态缓存兜底。
2. MySQL profile 保留 Redis，用于后续生产化扩展。
3. 演示密码仍为明文，生产环境建议改造为 BCrypt。
4. 前端构建会出现 Vite CJS API 过时提示和 chunk 体积提示，不影响构建产物生成。
