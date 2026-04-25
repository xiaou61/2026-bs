# 035 乡村振兴水稻收割预约系统检查报告

## 1. 检查结论

- 项目编号：`035`
- 项目名称：`乡村振兴水稻收割预约系统`
- 检查日期：`2026-04-25`
- 当前状态：`已完成`
- 综合结论：`本轮已修复 Spring Boot 3.2 与旧 MyBatis-Plus starter 不兼容、默认 JWT 密钥过短、默认环境强依赖本机 MySQL/Redis、初始化口令口径失真、预约状态持久化不兼容 H2、当前用户接口泄露密码、注册角色缺少约束以及地块/设备/预约流转缺少角色校验等问题。当前项目可在默认 H2 环境下通过测试并启动，登录、地块、设备、预约、指派、开工、完工链路与前端代理登录联调均已验证通过。`

## 2. 项目形态

- 后端目录：`035-backend`
- 前端目录：`035-frontend`
- 说明文档：`035-backend/README.md`、`035-backend/README_SIMPLE.md`
- 后端技术栈：`Spring Boot 3.2 + MyBatis-Plus + Spring Security + JWT + H2/MySQL`
- 前端技术栈：`Vite + Vue 3 + Element Plus + Pinia + axios`

## 3. 本轮修复

### 3.1 默认运行环境修复

1. 将 `mybatis-plus-boot-starter` 切换为 `mybatis-plus-spring-boot3-starter`，修复 Boot 3.2 / Spring 6.1 下的启动报错：`Invalid value type for attribute 'factoryBeanObjectType'`。
2. 将默认 JWT 密钥改为满足 `HS256` 长度要求的安全字符串，并在 `JwtUtil` 中显式使用 `UTF-8` 字节序，修复 `WeakKeyException`。
3. 将默认 `application.yml` 从本机 `MySQL + Redis` 强依赖切到 `H2` 内存库自举。
4. 新增 `application-mysql.yml`，保留原始 MySQL 兼容入口。
5. 新增 `schema-h2.sql` 与 `data-h2.sql`，补齐默认演示数据。
6. 统一 H2/MySQL 初始化账号口径，修正原始哈希与文档不一致的问题，当前默认账号均可真实登录。

### 3.2 安全与权限修复

1. 将 `/api/auth/me` 的返回值改为脱敏 DTO，移除密码字段泄露。
2. 为 `RegisterRequest.role` 增加服务端校验，仅允许 `1=农户`、`2=机手` 自注册。
3. 为地块接口增加农户角色校验。
4. 为设备接口增加机手角色校验。
5. 为预约接口补齐角色与归属约束：
   - 农户才能查看/创建/取消预约
   - 管理员才能指派设备
   - 仅被指派机手或管理员可开工、完工
   - 创建预约时校验地块归属
6. 改造全局异常兜底逻辑，优先返回最内层根因，避免再次出现 `msg=null`。

### 3.3 兼容性与测试补齐

1. 将预约状态字段改为字符串持久化，修复 H2 下 `JAVA_OBJECT to CHARACTER VARYING` 的枚举写库失败问题。
2. 新增后端集成测试，覆盖：
   - 健康检查
   - 管理员登录
   - 非法注册角色校验
   - `/api/auth/me` 不返回密码
   - 地块/设备角色隔离
   - 预约创建、指派、开工、完工主链路
   - 非法角色与非归属机手的拒绝逻辑
3. 为前端 Vite dev 代理增加 `VITE_API_TARGET` 环境变量覆盖能力，便于在默认 `8080` 被占用时仍能联调后端。

## 4. 当前实现与目标能力对照

### 4.1 已具备能力

1. JWT 登录注册与当前用户查询
2. 农户地块管理
3. 机手设备管理
4. 农户预约创建、取消
5. 管理员指派设备
6. 机手/管理员推进开工、完工
7. 默认 H2 演示环境与前端代理联调

### 4.2 仍有差距

1. `dispatch_task`、`work_record`、`review` 仅停留在实体/表结构层，当前没有完整控制器或页面闭环，README 提到的“评价完善”尚未真正落地。
2. 前端目前只实现了登录、首页、健康检查，尚未覆盖地块、设备、预约与调度界面，演示能力明显弱于后端功能。
3. 前端登录态仍保存在 `localStorage`，仅适合演示环境，不适合作为更高安全等级方案。
4. 前端打包主包仍约 `1.06 MB`，存在明显大包告警。

## 5. 验证结果

### 5.1 后端测试

- 执行命令：`035-backend/mvn test`
- 结果：`通过`
- 关键结果：`Tests run: 1, Failures: 0, Errors: 0`

### 5.2 后端启动与接口抽测

- 执行命令：`035-backend/mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8035`
- 结果：`通过`
- 抽测链路：
  1. `GET /api/health`
  2. `POST /api/auth/login`
  3. `POST /api/auth/register`
  4. `GET /api/auth/me`
  5. `POST /api/plots`
  6. `POST /api/machines`
  7. `POST /api/bookings`
  8. `PATCH /api/bookings/{id}/assign`
  9. `PATCH /api/bookings/{id}/start`
  10. `PATCH /api/bookings/{id}/finish`
  11. `GET /api/bookings`
- 关键业务结果：
  - 管理员 `admin / 123456` 登录成功
  - `/api/auth/me` 不再返回密码字段
  - 农户创建地块成功
  - 机手创建设备成功
  - 农户创建预约成功
  - 农户直调 `assign` 被正确拒绝，返回 `code=403`
  - 管理员指派设备成功
  - 机手开工、完工成功
  - 农户查询预约列表可见最终状态 `SETTLED`

### 5.3 前端验证

- 执行命令：
  - `035-frontend/npm run build`
  - `VITE_API_TARGET=http://127.0.0.1:8035 npm run dev -- --host 127.0.0.1 --port 5135`
- 结果：`通过`
- 关键结果：
  1. `npm run build` 成功完成，仅保留大包告警
  2. 浏览器访问 `http://127.0.0.1:5135/login` 返回 `200`
  3. 使用管理员账号 `admin / 123456` 可从登录页成功跳转到首页
  4. 前端“后端健康检查”页可通过代理成功调用 `/api/health`
  5. 浏览器控制台未出现错误日志

## 6. 默认账号与运行说明

- 默认运行模式：`H2`
- MySQL 兼容配置：`035-backend/src/main/resources/application-mysql.yml`
- 默认账号：
  - `admin / 123456`
  - `farmer_demo / 123456`
  - `driver_demo / 123456`
- 说明：
  1. 直接执行 `mvn spring-boot:run` 即可使用默认 H2 环境启动后端。
  2. 如需切换 MySQL，可先执行 `mysql -u root -p < sql/init.sql`，再运行 `mvn spring-boot:run -Dspring-boot.run.profiles=mysql`。
  3. 前端 dev 默认代理到 `http://localhost:8080`；若本机 `8080` 被其他服务占用，可通过 `VITE_API_TARGET` 临时覆盖。

## 7. 备注

1. 本机 `8080` 在巡检时被外部进程占用，因此后端实机抽测使用 `8035`，前端 dev 通过新增的代理覆盖变量完成联调；这不是项目本身的启动故障。
2. `mvn` 输出中的 `rdc` profile 警告来自外部 Maven 环境，不影响本项目测试与启动结论。
3. 本轮修复聚焦于“默认环境可启动 + 权限与安全基线可用 + 主链路可验证”，未继续扩展缺失的派工记录、作业记录、评价前后端闭环。
