# 080 基于SpringBoot和Vue的贫困地区儿童资助网站检查报告

## 1. 检查结论

- 项目编号：`080`
- 项目名称：`基于SpringBoot和Vue的贫困地区儿童资助网站`
- 检查日期：`2026-05-02`
- 当前状态：`已完成`
- 综合结论：`本轮已修复默认环境强依赖 MySQL/Redis/8080、H2 自举缺失、MySQL 驱动旧坐标、JDK 17 下 JJWT 缺 JAXB、后端无自动化测试、登录响应 password 字段泄露、JWT Bearer 不兼容、未登录/越权 HTTP 状态不准确、登出后 token 不失效、管理员/捐赠人/志愿者角色边界不足、捐赠 donorId 可伪造、儿童/申请/捐赠/资金/项目/公告/反馈/成长记录/资助关系权限不足、前端代理仍指向默认端口且请求头缺少 Bearer token 等问题。当前后端可在默认 H2 环境下通过自动化冒烟测试，并已使用本机 MySQL root/1234 的 mysql-verify profile 完成真实 HTTP 验证；前端可完成生产构建和代理登录联调。`

## 2. 项目形态

- 后端目录：`080-backend`
- 前端目录：`080-frontend`
- 后端技术栈：`Spring Boot 2.7.14 + MyBatis-Plus 3.5.3 + JWT + H2/MySQL + Redis 依赖保留`
- 前端技术栈：`Vue 3 + Vite + Element Plus + Pinia + Axios + ECharts`
- 默认后端端口：`8080`
- 前端开发端口：`3080`
- MySQL 账号密码：`root / 1234`

## 3. 本轮修复

1. 默认 `application.yml` 改为 H2 内存库自举，新增 `schema-h2.sql` 与 `data-h2.sql`。
2. 新增 `application-mysql.yml`，正式 MySQL 部署入口使用 `charity_db`、`root / 1234`。
3. 新增 `application-mysql-verify.yml`，用于 `charity_080_verify` 临时库的非破坏 MySQL 验证。
4. 修复 MySQL 驱动坐标为 `com.mysql:mysql-connector-j`，项目编译目标改为 JDK 17。
5. 增加 JAXB 依赖，修复 JDK 17 下 JJWT 0.9.1 运行缺 `javax/xml/bind/DatatypeConverter` 的问题。
6. JWT 拦截器兼容 `Authorization: Bearer <token>`，并新增运行态 token 失效表实现登出失效。
7. 登录响应和当前用户接口不再输出 `password` 字段。
8. 全局异常处理改为真实 HTTP 状态：未登录/失效 `401`，越权 `403`，参数错误 `400`。
9. 补充用户、统计、资金、项目、公告、申请、捐赠确认等管理型接口的管理员校验。
10. 补充捐赠人和志愿者角色边界，限制捐赠人访问管理统计、儿童新增、申请列表等接口。
11. 捐赠创建以 JWT 当前用户绑定的 donor 记录为准，阻止请求体伪造 `donorId`。
12. 儿童、申请、资金、反馈、成长记录、资助关系等模块补充基础输入校验和权限判断。
13. 前端 Vite 开发端口改为 `3080`，代理目标支持 `VITE_API_TARGET` 覆盖。
14. 前端请求拦截器自动补齐 `Bearer` token，并新增登出 API。
15. 新增后端冒烟测试 `CharitySmokeTest`。

## 4. 已具备能力

1. 后端默认 H2 自举，可直接执行 `mvn spring-boot:run` 启动演示。
2. MySQL `root / 1234` 已验证可连接，本轮 `mysql-verify` profile 真实启动通过。
3. 管理员、捐赠人、志愿者默认账号可登录。
4. 登录响应和当前用户接口均不回传 `password` 字段。
5. 未登录访问受保护接口返回 HTTP `401`。
6. 捐赠人访问后台统计、用户列表等管理接口返回 HTTP `403`。
7. 志愿者可新增儿童档案，捐赠人新增儿童会被拒绝。
8. 捐赠人查询捐赠人列表时只能看到本人 donor 记录。
9. 捐赠创建会自动绑定当前登录捐赠人，无法通过请求体把捐赠记到其他 donor。
10. 捐赠确认仅管理员可执行，捐赠人确认返回 HTTP `403`。
11. 志愿者可提交资助申请，捐赠人访问申请管理接口会被拒绝。
12. 登出后旧 token 失效，重新访问用户信息返回 HTTP `401`。
13. 前端构建和 `3080 -> 8080` 代理登录已验证通过。

## 5. 验证结果

### 5.1 后端测试

- 执行命令：`080-backend/mvn test`
- 结果：`通过`
- 关键结果：`Tests run: 1, Failures: 0, Errors: 0`
- 覆盖内容：登录脱敏、未登录 `401`、捐赠人统计越权 `403`、用户列表越权 `403`、志愿者新增儿童、捐赠人新增儿童越权、捐赠人 donor 列表隔离、捐赠 donorId 防伪造、捐赠确认权限、申请提交权限、登出失效。

### 5.2 前端构建

- 执行命令：`080-frontend/npm run build`
- 结果：`通过`
- 备注：Vite 输出 chunk 体积提示，不影响构建产物生成。

### 5.3 MySQL 启动与接口抽测

- MySQL 连接：`localhost:3306`，账号 `root / 1234`
- MySQL 版本：`8.0.26`
- 后端启动命令：`080-backend/mvn spring-boot:run -Dspring-boot.run.profiles=mysql-verify`
- 验证库：`charity_080_verify`
- 后端端口：`8080`
- 真实 HTTP 抽测结果：
  1. `GET /api/child/list` 未登录返回 HTTP `401`
  2. `admin` 登录返回 HTTP `200`，且无 `password` 字段
  3. 捐赠人访问 `GET /api/statistics/dashboard` 返回 HTTP `403`
  4. 管理员访问 `GET /api/statistics/dashboard` 返回 HTTP `200`
  5. 志愿者新增儿童返回 HTTP `200`
  6. 捐赠人新增捐赠返回 HTTP `200`，且创建记录的 `donorId` 被强制绑定为本人 donor
  7. 捐赠人确认捐赠返回 HTTP `403`
  8. 管理员确认捐赠返回 HTTP `200`
  9. 登出后旧 token 访问用户信息返回 HTTP `401`

### 5.4 前端代理联调

- 后端启动端口：`8080`
- 前端启动命令：`080-frontend/npm run dev -- --host 127.0.0.1`
- 前端端口：`3080`
- 结果：`通过`
- 关键结果：
  1. `GET http://127.0.0.1:3080/` 返回 HTTP `200`
  2. `POST http://127.0.0.1:3080/api/user/login` 返回 HTTP `200`，业务码 `200`，角色为 `admin`，token 存在，且无 `password` 字段

## 6. H2 与 MySQL 口径说明

默认使用 H2 不是替代 MySQL，而是为了让自动化测试和默认演示不会破坏本机正式库。原始 `080-backend/sql/init.sql` 开头包含 `DROP DATABASE IF EXISTS charity_db`，如果默认测试直接使用 `charity_db`，反复巡检有误删正式演示数据的风险。因此本轮采用：

1. 默认 H2：用于 `mvn test` 和开箱演示，保证项目随时可跑。
2. 正式 MySQL profile：`application-mysql.yml`，连接 `charity_db`，账号 `root / 1234`。
3. MySQL 验证 profile：`application-mysql-verify.yml`，连接临时库 `charity_080_verify`，用同一套 HTTP 链路验证 MySQL 兼容性，验证后可删除临时库。

## 7. 默认账号与运行说明

- 默认运行模式：`H2`
- 正式 MySQL 配置：`080-backend/src/main/resources/application-mysql.yml`
- MySQL 验证配置：`080-backend/src/main/resources/application-mysql-verify.yml`
- MySQL 初始化脚本：`080-backend/sql/init.sql`
- 管理员：`admin / 123456`
- 捐赠人：`donor / 123456`
- 志愿者：`volunteer / 123456`
- 企业捐赠人：`donor2 / 123456`

## 8. 仍有差距

1. 密码仍为明文存储，生产环境建议改为 BCrypt。
2. 默认演示模式使用 H2 内存库，正式 MySQL profile 需要按目标机器导入 `sql/init.sql`。
3. Redis 依赖保留但当前默认演示链路未使用分布式会话。
4. 捐赠为模拟确认流程，未接入真实支付、证书生成或财务对账。
5. 资助申请、成长记录和反馈仍缺少更完整的消息通知与内容审核。
6. 图片上传使用本地路径字段，未接入对象存储。
7. 前端构建存在 chunk 体积提示，当前不影响构建产物生成。
