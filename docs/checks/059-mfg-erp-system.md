# 059 制造装备物联及生产管理 ERP 系统检查报告

## 1. 检查结论

- 项目编号：`059`
- 项目名称：`制造装备物联及生产管理 ERP 系统`
- 检查日期：`2026-04-29`
- 当前状态：`已完成`
- 综合结论：`本轮已修复默认环境强依赖 MySQL/Redis/8080、H2 自举缺失、分页方言固定 MySQL、JWT 不兼容 Bearer token、未登录/越权仍返回 HTTP 200、Redis 不可用会阻断默认演示登录、用户密码响应泄露风险、前端代理仍指向 8080 等问题。当前后端可在默认 H2 环境下启动并通过自动化冒烟测试，前端可完成生产构建，真实 HTTP 核心链路抽测通过。MySQL profile 已按本机账号 `root / 1234` 保留。`

## 2. 项目形态

- 后端目录：`059-backend`
- 前端目录：`059-frontend`
- 后端技术栈：`Spring Boot 2.7.18 + MyBatis-Plus 3.5.4 + JWT + H2/MySQL + Redis 可选`
- 前端技术栈：`Vue 3 + Vite 5 + Element Plus + Pinia + ECharts`
- 默认后端端口：`8059`
- 前端开发端口：`3059`

## 3. 本轮修复

### 3.1 默认运行环境修复

1. 将后端默认端口从 `8080` 调整为 `8059`，避免合集内项目端口冲突。
2. 默认 `application.yml` 改为 H2 内存库自举，新增 `schema-h2.sql` 与 `data-h2.sql`。
3. 新增 `application-mysql.yml`，保留 MySQL 部署模式，通过 `-Dspring-boot.run.profiles=mysql` 切换。
4. MySQL profile 默认密码按当前机器约定调整为 `root / 1234`。
5. 更新 MySQL 驱动坐标为 `com.mysql:mysql-connector-j:8.0.33`。
6. 增加 H2 与 Spring Boot Test 依赖。
7. MyBatis-Plus 分页插件改为自动识别数据库方言，兼容默认 H2 与 MySQL profile。
8. Redis 不可用时不阻断登录和退出主流程，默认演示链路只依赖 JWT。

### 3.2 登录、鉴权与权限修复

1. `User.password` 改为 `WRITE_ONLY`，支持登录/新增写入但响应不再回传密码字段。
2. JWT 拦截器兼容 `Bearer` token。
3. JWT 拦截器兼容 `OPTIONS` 预检请求。
4. JWT 拦截器补充 token 对应用户存在和启用状态校验。
5. 未登录、token 无效和账号禁用场景直接返回真实 HTTP `401`。
6. 设备操作员访问用户管理接口返回真实 HTTP `403`。
7. 管理员、生产经理、设备操作员、质检员补充基础角色访问边界。
8. 新增用户若密码为空，默认补齐为 `123456`。

### 3.3 前端修复

1. 前端 Vite 开发端口改为 `3059`。
2. 前端代理目标改为 `http://localhost:8059`。
3. 请求拦截器自动补齐 `Bearer` token。
4. 新增前端 `package-lock.json`，保证依赖安装可复现。

### 3.4 文档与测试修复

1. 新增后端冒烟测试 `MfgApplicationSmokeTest`。
2. 新增 `059-backend/README.md`，补齐默认 H2 启动、MySQL profile、默认账号、前端端口和验证命令。
3. 新增 `059-backend/启动说明.txt`，便于答辩和本地演示。
4. 新增本巡检报告，并同步更新总台账。

## 4. 当前实现与目标能力对照

### 4.1 已具备能力

1. 后端默认 H2 自举，可直接执行 `mvn spring-boot:run` 启动。
2. 默认管理员、生产经理、设备操作员和质检员账号可登录。
3. 未登录访问受保护接口返回 HTTP `401`。
4. 设备操作员访问用户管理接口返回 HTTP `403`。
5. 管理员可访问用户、设备、传感器、告警、产品、生产工单、物料、质检、维保和看板接口。
6. 设备操作员可更新设备状态、添加传感器数据、处理告警和更新维保记录状态。
7. 生产经理可完成物料入库/出库和生产工单状态流转。
8. 质检员可查看并新增质量检测记录。
9. 前端登录、代理和鉴权请求头已与后端主链路对齐。

### 4.2 仍有差距

1. 密码仍为明文存储，生产环境建议改为 BCrypt。
2. Redis 配置仍保留为生产扩展项，默认演示链路不依赖 Redis。
3. 当前权限拦截为基础角色模型，更细粒度的菜单和操作权限仍可继续增强。
4. 传感器数据仍为模拟采集，尚未对接真实物联网网关。
5. 告警规则引擎、排产优化、库存预警推送和质检报表仍可继续增强。
6. 前端构建存在 Vite chunk 体积提示，当前不影响构建产物生成。

## 5. 验证结果

### 5.1 后端测试

- 执行命令：`059-backend/mvn test`
- 结果：`通过`
- 关键结果：`Tests run: 1, Failures: 0, Errors: 0, Skipped: 0`
- 覆盖内容：
  1. 默认 H2 上下文可启动
  2. 未登录访问用户信息返回 HTTP `401`
  3. 管理员、生产经理、设备操作员和质检员登录成功且密码字段不回传
  4. 设备操作员访问用户管理接口返回 HTTP `403`
  5. 管理员访问看板、用户、设备、传感器、告警、产品、生产、物料、质检和维保接口成功
  6. 设备操作员更新设备状态、添加传感器数据、处理告警和更新维保记录状态成功
  7. 生产经理完成物料入库/出库和生产工单状态流转成功
  8. 质检员查看并新增质量检测记录成功
  9. 管理员退出登录成功

### 5.2 前端构建

- 执行命令：`059-frontend/npm run build`
- 结果：`通过`
- 备注：Vite 输出 CJS API 过时提示和 chunk 体积提示，不影响构建产物生成。

### 5.3 启动与接口抽测

- 后端启动命令：`059-backend/mvn spring-boot:run`
- 结果：`通过`
- 抽测地址：
  1. 未登录用户信息：`GET http://localhost:8059/api/user/info`
  2. 管理员、生产经理、设备操作员和质检员登录：`POST http://localhost:8059/api/login`
  3. 操作员越权用户管理：`GET http://localhost:8059/api/user/page`
  4. 看板接口：`/api/dashboard/**`
  5. 用户、设备分类、设备、传感器、告警、产品、生产工单、物料、库存、质检、维保接口
  6. 设备状态更新、传感器新增、告警处理、库存入库/出库、工单状态、质检新增、维保状态接口
- 关键业务结果：
  1. 未登录访问用户信息返回 HTTP `401`
  2. 四类默认账号登录均返回业务码 `200`，响应不包含密码字段
  3. 设备操作员访问用户管理返回 HTTP `403`
  4. 管理员、生产经理、设备操作员和质检员核心链路均返回业务码 `200`
  5. 联调结束后 `8059` 无 LISTENING 残留端口

## 6. 默认账号与运行说明

- 默认运行模式：`H2`
- MySQL 兼容配置：`059-backend/src/main/resources/application-mysql.yml`
- MySQL 初始化脚本：`059-backend/sql/init.sql`
- 默认管理员：`admin / 123456`
- 默认生产经理：`manager / 123456`
- 默认设备操作员：`operator / 123456`
- 默认质检员：`inspector / 123456`
- 说明：
  1. 直接执行 `059-backend/mvn spring-boot:run` 即可使用默认 H2 环境启动后端。
  2. H2 控制台地址为 `http://localhost:8059/h2-console`，JDBC URL 为 `jdbc:h2:mem:mfg_erp`。
  3. 如需切换 MySQL，可先导入 `059-backend/sql/init.sql`，再运行 `mvn spring-boot:run -Dspring-boot.run.profiles=mysql`。
  4. 当前 MySQL profile 默认使用 `root / 1234`。
  5. 前端执行 `npm run dev` 后默认访问 `http://localhost:3059`。

## 7. 备注

1. 本轮后端真实启动使用端口 `8059`。
2. 本机 `3306` 存在 MySQL 监听，但 `mysql` CLI 未在 PATH 中；为避免执行 `DROP DATABASE` 类破坏性脚本，默认验证采用 H2 自举链路。
3. `mvn` 输出中的 `rdc` profile 警告来自外部 Maven 环境，不影响本项目测试和启动结论。
4. `target/`、`node_modules/`、`dist/` 和 `.tmp/` 临时日志等生成物不纳入提交。
