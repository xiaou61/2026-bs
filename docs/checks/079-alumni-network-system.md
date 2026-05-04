# 079 计算机学院校友网检查报告

## 1. 检查结论

- 项目编号：`079`
- 项目名称：`计算机学院校友网`
- 检查日期：`2026-05-02`
- 当前状态：`已完成`
- 综合结论：`本轮已修复默认环境强依赖 MySQL/Redis/8080、H2 自举缺失、MySQL 驱动旧坐标、后端无自动化测试、登录响应 password 字段泄露、JWT Bearer 不兼容、未登录/越权 HTTP 状态不准确、登出后 token 不失效、公开 GET 通配符误放行写操作、后台管理接口缺少服务端权限、校友资料/企业/岗位/活动/捐赠/论坛等归属校验不足、前端代理仍指向 8080、前端请求头缺少 Bearer token 等问题。当前后端可在默认 H2 环境下通过自动化冒烟测试，并已使用本机 MySQL root/1234 的 mysql-verify profile 完成真实 HTTP 验证；前端可完成生产构建和代理登录联调。`

## 2. 项目形态

- 后端目录：`079-backend`
- 前端目录：`079-frontend`
- 后端技术栈：`Spring Boot 2.7.18 + MyBatis-Plus 3.5.2 + JWT + H2/MySQL + Redis 依赖保留`
- 前端技术栈：`Vue 3 + Vite + Element Plus + Pinia + Axios + ECharts`
- 默认后端端口：`8079`
- 前端开发端口：`3079`
- MySQL 账号密码：`root / 1234`

## 3. 本轮修复

1. 后端默认端口从 `8080` 调整为 `8079`。
2. 默认 `application.yml` 改为 H2 内存库自举，新增 `db/schema-h2.sql` 与 `db/data-h2.sql`。
3. 新增 `application-mysql.yml`，保留正式 MySQL 部署入口，默认使用 `root / 1234`。
4. 新增 `application-mysql-verify.yml`，用于 `alumni_079_verify` 临时库的非破坏 MySQL 验证。
5. 修复 MySQL 驱动坐标为 `com.mysql:mysql-connector-j`，项目编译目标改为 JDK 17。
6. MyBatis-Plus 分页方言改为配置化，默认 `H2`，MySQL profile 使用 `MYSQL`。
7. `User.password` 改为 Jackson `WRITE_ONLY`，登录响应和代理登录均不回传密码字段。
8. JWT 拦截器兼容 `Authorization: Bearer <token>`，并新增运行态 token 失效表实现登出失效。
9. 全局异常处理改为真实 HTTP 状态：未登录/失效 `401`，越权 `403`，参数错误 `400`。
10. 公开接口改为拦截器中的 GET 可选登录，避免 `/api/forum/post/*` 等通配符误放行 DELETE/POST/PUT。
11. 用户、届次、班级、轮播图、新闻、日志、捐赠记录、统计等后台接口补充管理员校验。
12. 校友资料更新改为以 JWT 当前用户为准，阻止请求体篡改 `id/userId`。
13. 活动管理、报名清单、相册删除补充组织者或管理员校验。
14. 校友企业和招聘岗位补充本人企业归属或管理员校验。
15. 捐赠项目和捐赠记录确认补充管理员校验，捐赠金额增加正数校验。
16. 论坛板块、帖子、回复补充管理员或资源归属校验。
17. 前端 Vite 开发端口改为 `3079`，代理目标改为 `http://localhost:8079`。
18. 前端请求拦截器自动补齐 `Bearer` token，并在 HTTP `401` 时清理登录态。
19. 新增后端冒烟测试 `AlumniApplicationSmokeTest`。

## 4. 已具备能力

1. 后端默认 H2 自举，可直接执行 `mvn spring-boot:run` 启动。
2. MySQL `root / 1234` 已验证可连接，本轮 `mysql-verify` profile 真实启动通过。
3. 管理员和正常校友账号可登录，待审核校友登录返回 HTTP `400`。
4. 登录响应不回传 `password` 字段。
5. 未登录访问受保护接口返回 HTTP `401`。
6. 普通校友访问后台统计、捐赠记录等管理接口返回 HTTP `403`。
7. 游客可访问新闻、活动、轮播图、论坛、岗位、企业和捐赠项目公开列表。
8. 校友可维护本人校友资料，无法通过请求体篡改到他人档案。
9. 活动报名、重复报名、取消报名链路可用。
10. 捐赠记录提交和管理员确认链路可用。
11. 企业登记、管理员审核、岗位发布和归属校验可用。
12. 论坛发帖、点赞、回复、删除归属校验可用。
13. 登出后旧 token 失效，重新访问用户信息返回 HTTP `401`。
14. 前端构建和 `3079 -> 8079` 代理登录已验证通过。

## 5. 验证结果

### 5.1 后端测试

- 执行命令：`079-backend/mvn test`
- 结果：`通过`
- 关键结果：`Tests run: 1, Failures: 0, Errors: 0, Skipped: 0`
- 覆盖内容：登录脱敏、未登录 `401`、待审核账号、普通校友统计越权 `403`、管理员统计、公开列表、校友资料防篡改、活动报名/重复报名/取消、捐赠与管理员确认、企业/岗位归属、论坛归属和登出失效。

### 5.2 前端构建

- 执行命令：`079-frontend/npm run build`
- 结果：`通过`
- 备注：Vite 输出 CJS API 过期提示和 chunk 体积提示，不影响构建产物生成。

### 5.3 MySQL 启动与接口抽测

- MySQL 连接：`localhost:3306`，账号 `root / 1234`
- MySQL 版本：`8.0.26`
- 后端启动命令：`079-backend/mvn spring-boot:run -Dspring-boot.run.profiles=mysql-verify`
- 验证库：`alumni_079_verify`
- 后端端口：`8079`
- 真实 HTTP 抽测结果：
  1. `GET /api/auth/info` 未登录返回 HTTP `401`
  2. `admin`、`user`、`user3`、`user4` 登录返回 HTTP `200`，且无 `password` 字段
  3. `user2` 待审核账号登录返回 HTTP `400`
  4. 普通校友访问 `GET /api/stats/overview` 返回 HTTP `403`
  5. 管理员访问 `GET /api/stats/overview` 返回 HTTP `200`
  6. 新闻、活动、轮播图、论坛、岗位、企业和捐赠项目公开列表返回 HTTP `200`
  7. 非活动组织者查看报名清单返回 HTTP `403`，管理员查看返回 HTTP `200`
  8. 活动报名、重复报名拦截和取消报名分别返回 HTTP `200/400/200`
  9. 普通校友捐赠返回 HTTP `200`，普通校友访问捐赠记录管理返回 HTTP `403`
  10. 管理员查询并确认捐赠记录返回 HTTP `200`
  11. 企业登记、审核、错误用户编辑 `403`、所属用户编辑 `200`
  12. 岗位发布、错误用户编辑 `403`、所属用户编辑 `200`
  13. 论坛发帖、错误用户删除 `403`、本人删除 `200`
  14. 管理员登出后旧 token 访问用户信息返回 HTTP `401`

### 5.4 前端代理联调

- 后端启动端口：`8079`
- 前端启动命令：`079-frontend/npm run dev -- --host 127.0.0.1`
- 前端端口：`3079`
- 结果：`通过`
- 关键结果：
  1. `GET http://127.0.0.1:3079/` 返回 HTTP `200`
  2. `POST http://127.0.0.1:3079/api/auth/login` 返回 HTTP `200`，业务码 `200`，角色为 `admin`，token 存在，且无 `password` 字段

## 6. 默认账号与运行说明

- 默认运行模式：`H2`
- 正式 MySQL 配置：`079-backend/src/main/resources/application-mysql.yml`
- MySQL 验证配置：`079-backend/src/main/resources/application-mysql-verify.yml`
- MySQL 初始化脚本：`079-backend/sql/init.sql`
- 默认管理员：`admin / 123456`
- 正常校友：`user / 123456`
- 待审核校友：`user2 / 123456`
- 正常校友：`user3 / 123456`
- 正常校友：`user4 / 123456`

## 7. 仍有差距

1. 密码仍为明文存储，生产环境建议改为 BCrypt。
2. 默认演示模式使用 H2 内存库，正式 MySQL profile 需要按目标机器导入 `sql/init.sql`。
3. Redis 依赖保留但当前默认演示链路未使用分布式会话。
4. 捐赠为模拟确认流程，未接入真实支付或财务对账。
5. 活动报名、岗位发布和论坛互动仍缺少更细粒度的内容审核和通知机制。
6. 图片上传使用本地 `uploads/`，未接入对象存储。
7. 前端构建存在 chunk 体积提示，当前不影响构建产物生成。
