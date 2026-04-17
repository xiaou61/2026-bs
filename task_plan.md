# Task Plan: 030 项目巡检与修复

## Goal
完成 `030` 居民智能健康管理平台的文档一致性、JDK 17 兼容性、测试、构建、启动与核心链路巡检；发现明确问题后直接修复并复测，同时持续同步总台账与检查报告。

## Current Phase
Phase 5

## Phases

### Phase 1: 建档与上下文同步
- [x] 确认 `030` 项目名称、目录结构与资料范围
- [x] 建立 `030` 巡检文档骨架并更新总台账
- [x] 切换本轮会话计划文件
- **Status:** complete

### Phase 2: 文档与源码核对
- [x] 核对 PRD、后端配置、实体、控制器、前端路由与接口调用
- [x] 判断实现范围与 PRD 的偏差
- [x] 记录默认环境依赖、启动阻塞点与权限缺口
- **Status:** complete

### Phase 3: 构建、启动与抽测
- [x] 执行后端测试 / 编译
- [x] 执行前端依赖安装与构建
- [x] 启动前后端并抽测登录、医生列表、健康数据与权限链路
- **Status:** complete

### Phase 4: 问题修复与复测
- [x] 修复默认环境无法启动、建表失败与权限边界问题
- [x] 补齐后端冒烟测试与前端角色路由守卫
- [x] 重新执行相关测试、构建与启动验证
- **Status:** complete

### Phase 5: 回填与清理
- [x] 更新总台账与单项目检查报告
- [x] 更新 `findings.md` / `progress.md`
- [x] 清理本项目临时进程与运行状态
- **Status:** complete

## Key Questions
1. `030` 当前能否在 JDK 17 下直接通过后端测试与前端构建？
2. 默认 `PostgreSQL + Redis` 配置是否会成为启动阻塞点？
3. 患者、医生、管理员的接口边界是否已经真实隔离？
4. PRD 中的健康评估、预警、知识库和报表模块实际落地了多少？

## Decisions Made
| Decision | Rationale |
|----------|-----------|
| 默认运行环境切换到 H2，自保留 PostgreSQL profile | 满足“默认可测试、可启动”的巡检目标 |
| 通过 `DataInitializer` 提供默认账号与基础资料 | 降低启动后验证门槛，便于抽测患者 / 医生 / 管理员链路 |
| 新增 MockMvc 冒烟测试覆盖注册、登录、健康数据与越权场景 | 让 `mvn test` 真正对核心链路负责 |
| 前端补充角色路由守卫与按角色首页跳转 | 避免页面层面的跨角色误访问 |

## Errors Encountered
| Error | Attempt | Resolution |
|-------|---------|------------|
| 默认 `application.yml` 强依赖本地 PostgreSQL，启动失败 | 1 | 改为默认 H2 文件库，并拆出 `application-postgresql.yml` |
| `schema.sql` 中使用无效建库语句 | 1 | 改为说明文件，避免后续误执行 |
| `health_data.value` 在 H2 下命中保留字，建表报错 | 1 | 将数据库列名改为 `metric_value` |
| 后端虽可编译但没有任何真实测试 | 1 | 新增 `HealthManagementApplicationTests` |
| 患者可直接访问管理员审核接口 | 1 | 补齐控制器角色校验，并将越权场景加入测试 |

## Notes
- `030-backend` 已通过 `mvn test`，并新增默认配置冒烟测试。
- `030-backend` 已通过 `mvn spring-boot:run` 启动验证与接口抽测。
- `030-frontend` 已完成 `npm install` 与 `npm run build`，并验证 `npm run dev -- --host 127.0.0.1 --port 5173` 可启动。
- 默认账号：
  - `admin / admin123`
  - `doctor / doctor123`
  - `patient / patient123`
