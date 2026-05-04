# 072 基于SpringBoot和Vue的哈尔滨文旅系统检查报告

## 1. 检查结论

- 项目编号：`072`
- 项目名称：`基于SpringBoot和Vue的哈尔滨文旅系统`
- 检查日期：`2026-05-01`
- 当前状态：`已完成`
- 综合结论：`本轮已修复默认环境强依赖 MySQL/Redis/8080、H2 自举缺失、MySQL 驱动旧坐标、JDK 17 配置不足、前端路由指向不存在目录、前端 API 导出名与页面不匹配、前端代理仍指向 8080、JWT 不兼容 Bearer token、未登录/越权 HTTP 状态不准确、登录响应密码泄露、后台接口缺少服务端角色校验、订单/游记/评价归属校验不足等问题。当前后端可在默认 H2 环境下启动并通过自动化冒烟测试，前端可完成生产构建，真实 HTTP 文旅主链路和前端代理登录复测通过。`

## 2. 项目形态

- 后端目录：`072-backend`
- 前端目录：`072-frontend`
- 后端技术栈：`Spring Boot 2.7.18 + MyBatis-Plus 3.5.5 + JWT + H2/MySQL + Redis 依赖保留`
- 前端技术栈：`Vue 3 + Vite 5 + Element Plus + Pinia + Axios + ECharts`
- 默认后端端口：`8072`
- 前端开发端口：`3072`

## 3. 本轮修复

### 3.1 默认运行环境修复

1. 将后端默认端口从 `8080` 调整为 `8072`。
2. 默认 `application.yml` 改为 H2 内存库自举，新增 `db/schema-h2.sql` 与 `db/data-h2.sql`。
3. 新增 `application-mysql.yml`，保留原 MySQL 部署入口。
4. 修复 MySQL 驱动坐标为 `com.mysql:mysql-connector-j`。
5. 项目编译目标改为 JDK 17，并增加 H2 与 Spring Boot Test 依赖。
6. MyBatis-Plus 分页方言支持按配置切换，默认使用 H2，MySQL profile 使用 MySQL。
7. 默认演示数据覆盖管理员、普通用户、景点、路线、票种、订单、酒店、餐厅、活动、游记、评价、公告和收藏。

### 3.2 登录、鉴权与权限修复

1. `User.password` 改为 `WRITE_ONLY`，登录响应和用户信息响应不再回传密码。
2. JWT 拦截器兼容 `Authorization: Bearer <token>`。
3. 新增本地运行态 token 失效表，登出后旧 token 返回 HTTP `401`。
4. 全局异常处理改为真实 HTTP 状态：未登录/失效 `401`，越权 `403`，参数错误 `400`，资源不存在 `404`。
5. 管理后台统计、用户管理、景点/酒店/餐厅/公告/活动管理、票种管理、订单管理、评价审核和游记审核均增加服务端管理员校验。
6. 订单详情和退款增加归属校验，普通用户不能枚举他人订单。
7. 游记编辑/删除、评价删除增加归属校验，非本人且非管理员返回 HTTP `403`。
8. 用户资料更新只允许修改昵称、头像、电话、邮箱，避免通过资料接口覆盖角色、余额、状态或密码。

### 3.3 文旅业务修复

1. 补充票务列表接口，支撑前端门票页按景点名称查询可售票种。
2. 购票时校验数量、游玩日期、票种存在、票种与景点匹配、库存、限购和用户余额。
3. 退款时回补用户余额和票种库存。
4. 充值金额必须大于 0。
5. 活动报名校验活动状态、报名截止时间、人数上限和重复报名。
6. 游记发布校验标题和内容，并默认进入待审核状态。
7. 评价发布校验评价对象和评分范围，并同步更新景点/酒店/餐厅评分。

### 3.4 前端修复

1. 前端 Vite 开发端口改为 `3072`。
2. 前端代理目标改为 `http://localhost:8072`，并支持 `VITE_API_TARGET` 覆盖。
3. 请求拦截器自动补齐 `Bearer` token，HTTP `401` 会清理登录态并跳转登录页。
4. 修复路由导入路径，改为引用实际存在的 `src/views/*.vue` 页面。
5. 补齐前端 API 兼容导出，解决页面引用的 `getTicketList`、`getTicketsBySpot`、`getMyFavorites`、`updatePassword`、`rechargeWallet`、`createReview` 等函数缺失问题。
6. 修复购票参数名，前端统一向后端提交 `spotId`、`ticketTypeId`、`ticketDate` 和 `quantity`。
7. 新增 `package-lock.json`，保证依赖安装可复现。

### 3.5 文档与测试修复

1. 新增后端冒烟测试 `TourismApplicationSmokeTest`。
2. 新增 `072-backend/README.md`，补齐默认启动、MySQL profile、默认账号、前端联调和验证命令。
3. 新增 `072-backend/启动说明.txt`，便于答辩和本地演示。
4. 新增本巡检报告，并同步更新总台账。

## 4. 当前实现与目标能力对照

### 4.1 已具备能力

1. 后端默认 H2 自举，可直接执行 `mvn spring-boot:run` 启动。
2. 管理员、普通用户和测试游客账号可登录。
3. 登录响应不回传 `password` 字段。
4. 未登录访问受保护接口返回 HTTP `401`。
5. 普通用户访问后台统计和管理接口返回 HTTP `403`。
6. 普通用户访问他人订单返回 HTTP `403`。
7. 管理员可访问统计、用户管理、景点/票种/订单/公告等管理接口。
8. 普通用户可浏览景点、路线、酒店、餐厅、活动、公告和游记。
9. 普通用户可购票、查看本人订单、退款、收藏、评价和报名活动。
10. 登出后旧 token 失效，重新访问用户信息返回 HTTP `401`。
11. 前端构建、路由导入和代理登录已与后端主链路对齐。

### 4.2 仍有差距

1. 密码仍为明文存储，生产环境建议改为 BCrypt。
2. 默认演示模式使用 H2 内存库，MySQL profile 需要按目标机器单独导入 `sql/init.sql`。
3. Redis 依赖保留但当前默认演示链路未使用分布式会话。
4. 票务支付和退款为本地余额模拟，真实支付、退款和对账仍未接入。
5. 游记评论当前复用评价接口做兼容，完整评论模型仍可继续拆分。
6. 前端后台页面仍是简化管理台，复杂运营配置和富文本/图片上传能力未完整实现。
7. 前端构建存在 chunk 体积提示，当前不影响构建产物生成。

## 5. 验证结果

### 5.1 后端测试

- 执行命令：`072-backend/mvn test`
- 结果：`通过`
- 关键结果：`Tests run: 1, Failures: 0, Errors: 0, Skipped: 0`
- 覆盖内容：
  1. 默认 H2 上下文可启动
  2. 未登录访问用户信息返回 HTTP `401`
  3. 管理员、普通用户和游客登录成功且密码字段不回传
  4. 公开文旅列表可访问
  5. 普通用户访问后台统计返回 HTTP `403`
  6. 管理员访问后台统计成功
  7. 普通用户购票成功
  8. 游客访问他人订单返回 HTTP `403`
  9. 普通用户退款成功
  10. 普通用户收藏、评价和活动报名成功
  11. 普通用户登出后旧 token 返回 HTTP `401`

### 5.2 前端构建

- 执行命令：`072-frontend/npm run build`
- 结果：`通过`
- 备注：Vite 输出 chunk 体积提示，不影响构建产物生成。

### 5.3 启动与接口抽测

- 后端启动命令：`072-backend/mvn spring-boot:run`
- 结果：`通过`
- 后端端口：`8072`
- 真实 HTTP 抽测结果：
  1. `GET /api/user/info` 未登录返回 HTTP `401`
  2. `POST /api/auth/login` 管理员、普通用户、游客登录均返回 HTTP `200`
  3. 公开列表返回：`spots=2, routes=1, hotels=1, restaurants=1, activities=1`
  4. 普通用户访问 `GET /api/admin/statistics` 返回 HTTP `403`
  5. 管理员访问 `GET /api/admin/statistics` 返回 HTTP `200`
  6. 普通用户 `POST /api/tickets/order` 返回 HTTP `200`
  7. 游客访问普通用户订单详情返回 HTTP `403`
  8. 普通用户退款返回 HTTP `200`
  9. 普通用户登出返回 HTTP `200`
  10. 登出后访问 `GET /api/user/info` 返回 HTTP `401`

### 5.4 前端代理联调

- 后端启动端口：`8072`
- 前端启动命令：`072-frontend/npm run dev -- --host 127.0.0.1`
- 前端端口：`3072`
- 结果：`通过`
- 关键结果：`POST http://localhost:3072/api/auth/login` 返回 HTTP `200`，业务码 `200`，角色为 `admin`，token 存在。

## 6. 默认账号与运行说明

- 默认运行模式：`H2`
- MySQL 兼容配置：`072-backend/src/main/resources/application-mysql.yml`
- MySQL 初始化脚本：`072-backend/sql/init.sql`
- 默认管理员：`admin / 123456`
- 默认普通用户：`user / 123456`
- 默认游客用户：`tourist / 123456`
- 说明：
  1. 直接执行 `072-backend/mvn spring-boot:run` 即可使用默认 H2 环境启动后端。
  2. H2 控制台地址为 `http://localhost:8072/h2-console`，JDBC URL 为 `jdbc:h2:mem:harbin_tourism_072`。
  3. 如需切换 MySQL，请先确认 `sql/init.sql` 中的建库/删库语句可接受，再导入脚本并使用 `mysql` profile 启动。
  4. 前端执行 `npm run dev` 后默认访问 `http://localhost:3072`。

## 7. 备注

1. 本轮后端真实启动使用端口 `8072`，前端真实启动使用端口 `3072`。
2. 原始 SQL 脚本包含 `DROP DATABASE`，为避免破坏本机数据，默认验证采用 H2 自举链路。
3. `mvn` 输出中的 `rdc` profile 警告来自外部 Maven 环境，不影响本项目测试和启动结论。
4. `target/`、`node_modules/`、`dist/` 和临时日志等生成物不纳入提交。
