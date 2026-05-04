# 081 基于SpringBoot+Vue+uniapp的电器维修系统小程序检查报告

## 1. 检查结论

- 项目编号：`081`
- 项目名称：`基于SpringBoot+Vue+uniapp的电器维修系统小程序`
- 检查日期：`2026-05-02`
- 当前状态：`已完成`
- 综合结论：`本轮已修复默认环境强依赖 MySQL root/root、Redis/8080、H2 自举缺失、MySQL 驱动旧坐标、JDK 17 下 JJWT 缺 JAXB、后端无自动化测试、登录响应 password 字段泄露、JWT Bearer 不兼容、未登录/越权 HTTP 状态不准确、登出后 token 不失效、管理员/客户/技师角色边界不足、客户可伪造工单 userId、他人工单/进度可被枚举、评价缺少工单归属校验、前端依赖缺失、前端和小程序 token 头不规范、代理/静态资源地址写死等问题。当前后端默认 H2 环境可通过冒烟测试，MySQL root/1234 的 mysql-verify 临时库真实 HTTP 验证通过；管理端可完成生产构建和 3081 代理登录联调。`

## 2. 项目形态

- 后端目录：`081-backend`
- 管理端目录：`081-frontend`
- 小程序目录：`081-miniapp`
- 后端技术栈：`Spring Boot 2.7.14 + MyBatis-Plus 3.5.3 + JWT + H2/MySQL + Redis 依赖保留`
- 管理端技术栈：`Vue 3 + Vite + Element Plus + Pinia + Axios + ECharts`
- 小程序技术栈：`uni-app + Vue 3 + Pinia`
- 默认后端端口：`8080`
- 管理端开发端口：`3081`
- MySQL 账号密码：`root / 1234`

## 3. 本轮修复

1. 默认后端配置改为 H2 内存库自举，新增 `schema-h2.sql` 与 `data-h2.sql`。
2. 新增 `application-mysql.yml`，正式 MySQL 部署入口使用 `repair_db`、`root / 1234`。
3. 新增 `application-mysql-verify.yml`，用于 `repair_081_verify` 临时库的非破坏 MySQL 验证。
4. 修复 MySQL 驱动坐标为 `com.mysql:mysql-connector-j`，项目编译目标改为 JDK 17。
5. 增加 JAXB 依赖，修复 JDK 17 下 JJWT 0.9.1 运行兼容问题。
6. JWT 拦截器兼容 `Authorization: Bearer <token>`，新增运行态 token 失效表实现登出失效。
7. 登录响应和用户信息接口不再输出 `password` 字段。
8. 全局异常处理返回真实 HTTP 状态：未登录/失效 `401`，越权 `403`，参数错误 `400`。
9. 用户、分类、技师、工单、备件、公告、评价、统计等管理型接口补充管理员校验。
10. 客户创建工单以 JWT 当前用户为准，阻止请求体伪造 `userId`。
11. 客户工单详情、取消、支付和评价补充本人归属校验。
12. 技师工单列表、状态更新、进度上报补充分配技师归属校验。
13. 工单进度列表仅允许管理员、工单本人或被分配技师查看。
14. 管理端 Vite 端口改为 `3081`，代理目标支持 `VITE_API_TARGET` 覆盖。
15. 管理端和小程序请求头统一补齐 `Bearer` token。
16. 管理端上传图片预览、小程序图片 URL 不再写死 `localhost:8080`。
17. 新增后端冒烟测试 `RepairSmokeTest`。

## 4. 验证结果

### 4.1 后端测试

- 执行命令：`081-backend/mvn test`
- 结果：`通过`
- 关键结果：`Tests run: 1, Failures: 0, Errors: 0`
- 覆盖内容：未登录 `401`、公开分类、登录脱敏、客户统计越权 `403`、管理员统计、客户用户列表越权、客户创建工单、他人工单详情越权、管理员派单、技师工单、他人工单进度越权、技师更新状态、客户支付评价、登出失效。

### 4.2 管理端构建

- 执行命令：`081-frontend/npm install`
- 结果：`通过`
- 执行命令：`081-frontend/npm run build`
- 结果：`通过`
- 备注：Vite 输出 chunk 体积提示，不影响构建产物生成。

### 4.3 MySQL 启动与接口抽测

- MySQL 连接：`localhost:3306`，账号 `root / 1234`
- MySQL 版本：`8.0.26`
- 后端启动命令：`081-backend/mvn spring-boot:run -Dspring-boot.run.profiles=mysql-verify`
- 验证库：`repair_081_verify`
- 后端端口：`8080`
- 真实 HTTP 抽测结果：
  1. `GET /api/user/info` 未登录返回 HTTP `401`
  2. `GET /api/category/public/list` 返回 HTTP `200`
  3. 管理员登录返回 HTTP `200`，且无 `password` 字段
  4. 普通用户访问统计返回 HTTP `403`，管理员访问统计返回 HTTP `200`
  5. 普通用户访问用户列表返回 HTTP `403`
  6. 普通用户创建工单返回 HTTP `200`
  7. 其他用户访问该工单详情返回 HTTP `403`
  8. 管理员派单返回 HTTP `200`
  9. 技师查看本人派单和更新状态返回 HTTP `200`
  10. 其他用户查看工单进度返回 HTTP `403`
  11. 工单支付和评价返回 HTTP `200`
  12. 登出后旧 token 访问用户信息返回 HTTP `401`

### 4.4 管理端代理联调

- 后端启动端口：`8080`
- 管理端启动命令：`081-frontend/npm run dev -- --host 127.0.0.1`
- 管理端端口：`3081`
- 结果：`通过`
- 关键结果：
  1. `GET http://127.0.0.1:3081/` 返回 HTTP `200`
  2. `POST http://127.0.0.1:3081/api/user/login` 返回 HTTP `200`，业务码 `200`，角色为 `admin`，token 存在，且无 `password` 字段

## 5. H2 与 MySQL 口径说明

默认使用 H2 是为了自动化测试和默认演示不破坏正式库。原始 `081-backend/sql/init.sql` 包含 `DROP DATABASE IF EXISTS repair_db`，因此巡检默认使用 H2，MySQL 真实兼容性通过 `repair_081_verify` 临时库验证；正式部署仍使用 `application-mysql.yml`，账号密码为 `root / 1234`。

## 6. 默认账号

- 管理员：`admin / 123456`
- 技师：`tech1 / 123456`
- 普通用户：`user1 / 123456`
- 额外普通用户：`user2 / 123456`

## 7. 仍有差距

1. 密码仍为明文存储，生产环境建议改为 BCrypt。
2. 默认演示模式使用 H2 内存库，正式 MySQL profile 需要按目标机器导入 `sql/init.sql`。
3. Redis 依赖保留但当前默认演示链路未使用分布式会话。
4. 支付为模拟完成，未接入真实支付和售后财务对账。
5. 备件库存未和工单结算做强一致扣减。
6. 小程序目录仍残留未在 `pages.json` 声明的旧页面文件，当前不会参与页面编译，但后续可清理。
