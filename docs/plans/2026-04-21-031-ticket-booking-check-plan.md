# 031 球赛订票系统巡检计划

## 目标

将 `031` 项目修到满足以下基线：

1. `JDK 17` 下可构建、可测试、可启动
2. 默认环境不依赖本地手工准备的 `MySQL / Redis`
3. 登录、赛事列表、选座下单、支付、订单查询、个人资料至少形成最小可演示闭环
4. 检查结论与修复过程同步回写总台账和单项目报告

## 已知风险

1. `pom.xml` 中 `jooq-codegen-maven` 写死本地 `MySQL`
2. `application.yml` 写死本地 `MySQL` 与 `Redis`
3. `schema.sql` / `data.sql` 带 MySQL 专属语法
4. 下单/支付/出票/退单流程存在明显逻辑缺口
5. 前端选座页未接真实后端接口

## 执行顺序

### 1. 先复现

- 跑后端 `mvn test` 或 `mvn -DskipTests compile`
- 记录真实失败点和日志

### 2. 先保底能起

- 调整默认数据源为内嵌库
- 保留 `MySQL` profile 供后续切换
- 处理 `schema.sql` / `data.sql` 兼容问题
- 避免 `Redis` 在默认环境阻塞启动
- 处理 `jooq-codegen` 对默认构建的影响

### 3. 再补主链路

- 补齐赛事价位 / 座位查询接口
- 修复下单金额、库存、座位锁定逻辑
- 修复支付后出票与订单状态回写
- 修复取消订单时的退款与座位释放
- 让前端选座页使用真实接口而不是硬编码数据

### 4. 最后复测

- 后端测试 / 编译
- 后端启动
- 前端页面访问
- 登录、赛事列表、下单、支付、订单、个人中心抽测

## 交付物

- `docs/checks/031-football-ticket-booking-system.md`
- `docs/project-check-tracker.md`
- `task_plan.md`
- `findings.md`
- `progress.md`
