# 034 零食铺子仓储管理系统检查报告

## 1. 检查结论

- 项目编号：`034`
- 项目名称：`零食铺子仓储管理系统`
- 检查日期：`2026-04-22`
- 当前状态：`已完成`
- 综合结论：`本轮修复了 Spring Security 鉴权装配链中的循环依赖，补齐后端启动/登录烟雾测试；当前项目可在默认 H2 环境下通过测试并启动，管理员登录、SKU、库存、入库、出库、调拨、盘点链路与静态前端默认 API 联调均已验证通过。`

## 2. 项目形态

- 后端目录：`034-backend`
- 前端目录：`034-frontend`
- PRD/说明文档：`034-backend/PRD/034-零食铺子仓储管理系统-PRD.md`
- 后端技术栈：`Spring Boot 3.2 + MyBatis-Plus + Spring Security + JWT + H2/MySQL`
- 前端技术栈：`单文件 HTML + Vue 3 CDN + Element Plus CDN`

## 3. 本轮修复

### 3.1 启动问题修复

1. 复现 `mvn spring-boot:run` 默认启动失败，根因是 `SecurityConfig -> JwtAuthenticationFilter -> UserDetailsService -> UserServiceImpl -> PasswordEncoder -> SecurityConfig` 形成循环依赖。
2. 将 `UserDetailsService` 从 `SecurityConfig` 中拆出，新增独立的 `WmsUserDetailsService`。
3. 将 `PasswordEncoder` Bean 从 `SecurityConfig` 中拆到 `SecuritySupportConfig`，消除过滤器链对安全配置类的回指。
4. 保留当前默认 `H2` 自举与 `application-mysql.yml` 兼容入口，不改动现有运行口径。

### 3.2 自动化测试补齐

1. 新增后端集成测试，覆盖：
   - 应用上下文启动
   - 默认账号 `admin / admin` 登录
   - 携带 JWT 访问受保护的 `GET /sku`

## 4. 当前实现与目标能力对照

### 4.1 已具备能力

1. 管理员登录与 JWT 鉴权
2. SKU 列表、库存列表、库存日志查询
3. 入库、出库、调拨、盘点四类单据处理
4. H2 演示数据自举与静态前端直接联调

### 4.2 仍有差距

1. PRD 中的采购、补货建议、预警报表、通知、导入导出、细粒度仓库权限等能力仍未完整落地。
2. 当前安全实现仍存在明显基线缺口：默认账号为明文口令、`PasswordEncoder` 仍为 `NoOpPasswordEncoder`、JWT 密钥硬编码在配置中。
3. 前端只是单页演示台，不是完整的工程化管理端；依赖外部 CDN，离线或内网环境下可用性有限。

## 5. 验证结果

### 5.1 后端测试

- 执行命令：`034-backend/mvn test`
- 结果：`通过`
- 关键结果：`Tests run: 1, Failures: 0, Errors: 0`

### 5.2 后端启动与接口抽测

- 执行命令：
  - `034-backend/mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8034`
  - `034-backend/mvn spring-boot:run`
- 结果：`通过（默认端口 8080）`
- 抽测链路：
  1. `POST /auth/login`
  2. `GET /sku`
  3. `GET /inventory?skuId=1`
  4. `POST /inbound`
  5. `POST /outbound`
  6. `POST /transfer`
  7. `POST /stock-check`
  8. `GET /inventory/logs?skuId=1`
- 关键业务结果：
  - 管理员 `admin / admin` 登录成功
  - SKU 列表成功返回 `2` 条演示数据
  - 仓库 `1` / 库位 `1` / 批次 `1` 的可用库存从 `100` 变为 `92`
  - 调拨后仓库 `2` / 库位 `2` / 批次 `1` 的可用库存为 `2`
  - 新入库批次成功创建，新增库存行为 `batch=3, avail=12`
  - 库存日志成功记录 `INBOUND`、`OUTBOUND`、`TRANSFER_OUT`、`TRANSFER_IN`、`STOCK_CHECK`

### 5.3 前端验证

- 执行命令：`python -m http.server 5134 --bind 127.0.0.1`
- 结果：`通过`
- 关键结果：
  1. `GET http://127.0.0.1:5134` 返回 `200`
  2. 页面标题“零食铺子仓储管理前端”正常渲染
  3. 使用默认 API 地址 `http://localhost:8080` 即可登录成功并拿到令牌
  4. 点击“刷新 SKU”后页面可见演示数据“番茄味薯片”

## 6. 默认账号与运行说明

- 默认运行模式：`H2`
- MySQL 兼容配置：`034-backend/src/main/resources/application-mysql.yml`
- 默认账号：
  - `admin / admin`
  - `warehouse / warehouse`

## 7. 备注

1. 本轮修复聚焦于“默认环境真实可启动 + 主链路可验证”，未对当前明文密码与硬编码 JWT 密钥做破坏性改造，后续若面向答辩演示之外的场景，建议优先加固。
2. 前端为静态演示页，没有 `npm run build` / `npm run dev` 流程；当前采用临时静态文件服务 + Playwright 登录联调作为验证口径。
3. `mvn` 输出中的 `rdc` profile 警告来自外部 Maven 环境，不影响本项目测试与启动结论。
