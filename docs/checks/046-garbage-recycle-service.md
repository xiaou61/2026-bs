# 046 垃圾回收服务系统检查报告

## 1. 检查结论

- 项目编号：`046`
- 项目名称：`垃圾回收服务系统`
- 检查日期：`2026-04-26`
- 当前状态：`已完成`
- 综合结论：`本轮已修复默认环境强依赖 MySQL/Redis、默认端口未项目化、H2 自举缺失、MyBatis-Plus 分页方言固定 MySQL、H2 控制台未放行、JWT 角色命名仍沿用上一项目语义、后端缺少可执行冒烟测试、前端 package 名称仍为 045 以及前端代理仍指向旧端口等问题。当前后端可在默认 H2 环境下通过冒烟测试并真实启动，居民预约、回收员接单完成、积分增加、积分商城兑换、管理员发放兑换、公告和环保知识链路可用；前端可构建，并可通过 Vite 代理完成登录、公告和分类接口联调。`

## 2. 项目形态

- 后端目录：`046-backend`
- 前端目录：`046-frontend`
- 后端技术栈：`Spring Boot 3.2.1 + MyBatis-Plus 3.5.5 + Spring Security + JWT + H2/MySQL`
- 前端技术栈：`Vue 3 + Vite + TypeScript + Pinia + Element Plus + Axios`
- 默认后端端口：`8046`
- 默认前端端口：`3046`

## 3. 本轮修复

### 3.1 默认运行环境修复

1. 新增 H2 依赖，并将默认 `application.yml` 改为 H2 内存库自举，避免巡检和演示必须预先安装 MySQL/Redis。
2. 新增 `application-mysql.yml`，保留 MySQL/Redis 部署配置，真实数据库模式通过 `-Dspring-boot.run.profiles=mysql` 启动。
3. 新增 `schema-h2.sql` 与 `data-h2.sql`，提供用户、小区、垃圾分类、订单、积分记录、积分商品、兑换记录、环保知识和公告等核心表结构与演示数据。
4. 将默认后端端口调整为 `8046`，前端开发端口调整为 `3046`，并将 Vite 代理目标改为 `http://localhost:8046`。
5. 将 MyBatis-Plus 分页方言改为通过 `app.mybatis.db-type` 配置控制，默认 H2，MySQL profile 中切换为 MySQL。
6. 放行 H2 控制台并设置 frame same-origin，方便默认演示环境查看内存库数据。

### 3.2 后端认证与测试修复

1. 将 JWT 过滤器中角色权限名称从上一项目的 `ROLE_NURSE` / `ROLE_FAMILY` 调整为当前项目语义的 `ROLE_COLLECTOR` / `ROLE_USER`。
2. 在 JWT 过滤器中补充 `request` attribute：`userId` 和 `role`，方便后续接口扩展时读取当前登录上下文。
3. 新增 Spring Boot 测试依赖，补齐可执行的后端冒烟测试。
4. 新增冒烟测试覆盖管理员登录、公告列表、管理员统计、分类列表、居民登录、居民创建订单、回收员查看待接单订单、回收员接单、回收员完成订单、积分增加、积分商城兑换、管理员发放兑换和环保知识列表。
5. 删除无业务意义的 `Main.java`，避免项目入口存在 `Hello world` 杂散类。

### 3.3 前端联调修复

1. 将 `package.json` 和 `package-lock.json` 的项目名从 `045-frontend` 修正为 `046-frontend`。
2. 将 Vite 开发端口调整为 `3046`，避免与其他项目端口冲突。
3. 将 `/api` 代理到 `http://localhost:8046`，使前端默认开发环境可以直接联调当前后端。

### 3.4 文档修复

1. 更新 `046-backend/README.md`，补齐默认 H2 启动、H2 控制台、MySQL profile、前端端口和默认账号说明。
2. 新增 `046-backend/启动说明.txt`，便于答辩或本地演示时快速启动。
3. 新增本巡检报告，并同步更新总台账。

## 4. 当前实现与目标能力对照

### 4.1 已具备能力

1. 后端默认 H2 自举，可直接执行 `mvn spring-boot:run` 启动。
2. 管理员账号 `admin / admin123`、回收员账号 `collector1 / admin123`、居民账号 `user1 / admin123` 可用于演示。
3. 演示数据覆盖 3 个小区、10 个垃圾分类、5 个积分商品、3 条环保知识和 2 条公告。
4. 居民可创建垃圾回收预约订单并查看个人订单。
5. 回收员可查看同小区待接单订单、接单并完成回收。
6. 完成回收后系统会计算回收金额、环保积分，并更新居民积分余额。
7. 居民可使用积分兑换商品，管理员可发放兑换记录。
8. 前端可通过 `npm run build` 构建，并可在 `3046` 端口通过 Vite 代理访问后端 `/api`。

### 4.2 仍有差距

1. 回收订单缺少真实地图定位、路线派单和图片凭证上传。
2. 积分兑换仍为简化发放流程，未接真实物流或核销码。
3. 回收员接单权限只按小区过滤，缺少更细粒度的回收员资质、排班和容量控制。
4. 环保知识、公告、积分商品的图片字段仍以 URL/字符串字段为主，缺少完整文件存储策略。
5. 前端构建存在主包体积超过 500KB 的 Vite 告警。

## 5. 验证结果

### 5.1 后端测试

- 执行命令：`046-backend/mvn test`
- 结果：`通过`
- 关键结果：`Tests run: 1, Failures: 0, Errors: 0`
- 覆盖内容：
  1. 默认 H2 上下文可启动
  2. `POST /api/auth/login` 使用 `admin / admin123` 返回 `code=200`，角色为 `2`
  3. `GET /api/notice/list` 返回公告分页数据
  4. `GET /api/admin/statistics` 返回居民数量和回收员数量
  5. `GET /api/admin/categories` 返回 10 条垃圾分类
  6. `POST /api/auth/login` 使用 `user1 / admin123` 返回 token 和初始积分 `500`
  7. 居民携带 token 创建预约订单并查询个人订单成功
  8. `POST /api/auth/login` 使用 `collector1 / admin123` 返回 token
  9. 回收员查询待接单订单、接单并完成订单成功
  10. 完成订单后订单状态为 `3`，金额为 `4.50`，获得积分 `35`
  11. 居民积分从 `500` 增加到 `535`
  12. 居民兑换 2 个环保购物袋后积分减少到 `435`
  13. 管理员发放兑换记录成功
  14. 环保知识列表返回 3 条演示数据

### 5.2 前端构建

- 执行命令：`046-frontend/npm run build`
- 结果：`通过`
- 关键结果：`vue-tsc -b && vite build` 成功，生成 `dist`
- 构建告警：
  1. `Some chunks are larger than 500 kB after minification`

### 5.3 启动与联调抽测

- 后端启动命令：`046-backend/mvn spring-boot:run`
- 前端启动命令：`046-frontend/npm run dev -- --host 127.0.0.1`
- 结果：`通过`
- 抽测地址：
  1. 后端公告列表：`http://127.0.0.1:8046/api/notice/list`
  2. 后端登录：`http://127.0.0.1:8046/api/auth/login`
  3. 后端管理员统计：`http://127.0.0.1:8046/api/admin/statistics`
  4. 后端分类列表：`http://127.0.0.1:8046/api/category/list`
  5. 后端创建订单：`http://127.0.0.1:8046/api/order`
  6. 后端回收员接单/完成：`http://127.0.0.1:8046/api/collector/accept/{id}`、`http://127.0.0.1:8046/api/collector/complete/{id}`
  7. 后端积分兑换：`http://127.0.0.1:8046/api/points/exchange`
  8. 后端管理员发放兑换：`http://127.0.0.1:8046/api/admin/exchange/{id}/deliver`
  9. 前端登录页：`http://127.0.0.1:3046/login`
  10. 前端代理登录：`http://127.0.0.1:3046/api/auth/login`
  11. 前端代理公告与分类：`http://127.0.0.1:3046/api/notice/list`、`http://127.0.0.1:3046/api/category/list`
- 关键业务结果：
  1. 后端公告列表接口返回 `HTTP 200`，业务码 `200`，包含 2 条演示公告
  2. 管理员、居民、回收员登录均返回 `HTTP 200`，业务码 `200`
  3. 管理员统计返回居民数量 `2`、回收员数量 `2`
  4. 分类列表返回 10 条演示分类
  5. 居民创建订单返回业务码 `200`
  6. 回收员待接单列表可看到订单，接单与完成订单均返回业务码 `200`
  7. 完成订单后订单状态为 `3`，金额为 `4.5`，积分为 `35`
  8. 居民积分从 `500` 增加到 `535`，兑换后为 `435`
  9. 管理员发放兑换记录返回业务码 `200`
  10. 前端登录页返回 `HTTP 200`
  11. 经 Vite 代理调用登录、公告和分类接口均返回 `HTTP 200`，业务码 `200`

## 6. 默认账号与运行说明

- 默认运行模式：`H2`
- MySQL 兼容配置：`046-backend/src/main/resources/application-mysql.yml`
- MySQL 初始化脚本：`046-backend/sql/init.sql`
- 默认账号：
  - 管理员：`admin / admin123`
  - 回收员：`collector1 / admin123`
  - 居民：`user1 / admin123`
- 说明：
  1. 直接执行 `046-backend/mvn spring-boot:run` 即可使用默认 H2 环境启动后端。
  2. 后端接口默认不带额外 context-path，例如 `POST http://localhost:8046/api/auth/login`。
  3. H2 控制台地址为 `http://localhost:8046/h2-console`，JDBC URL 为 `jdbc:h2:mem:garbage_recycle`。
  4. 如需切换 MySQL，可先导入 `046-backend/sql/init.sql`，再运行 `mvn spring-boot:run -Dspring-boot.run.profiles=mysql`。
  5. 前端执行 `npm install` 后使用 `npm run dev` 启动，默认访问 `http://localhost:3046`。

## 7. 备注

1. 本轮后端真实启动使用端口 `8046`，前端真实启动使用端口 `3046`。
2. `mvn` 输出中的 `rdc` profile 警告来自外部 Maven 环境，不影响本项目测试与启动结论。
3. 前端 `dist/`、`node_modules/`、后端 `target/`、临时日志目录均不纳入提交。
