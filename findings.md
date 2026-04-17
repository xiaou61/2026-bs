# Findings & Decisions

## Requirements
- 按项目编号逐个检查毕设项目。
- 当前进入 `030` 居民智能健康管理平台巡检。
- 检查范围包括 PRD、配置文件、实体、前后端实现、JDK 17 兼容性、测试、构建、启动和核心链路。
- 发现明确问题后不能只记录，要直接修复并复测。
- 需要持续更新总台账、单项目检查文档和会话文件。

## Research Findings
- `030` 项目目录已确认存在：
  - `030-backend`
  - `030-frontend`
- 项目 PRD 已确认：
  - `030-backend/PRD/030-智能健康管理平台-PRD.md`
- 项目形态初步确认：
  - 后端：`Spring Boot 3.2 + Spring Data JPA + JWT`
  - 前端：`React 18 + Vite + Ant Design`
- 文档层面已确认的高风险点：
  - PRD 覆盖健康评估、预警、知识库、报表、系统配置等大范围模块
  - 实际工程当前只落地了用户、患者档案、健康数据、医生资料、在线咨询和少量后台页
- 真实工程问题已确认并修复：
  - 默认配置强依赖本地 PostgreSQL，导致默认环境无法启动
  - `schema.sql` 含无效建库语句
  - `health_data.value` 与 H2 保留字冲突，导致建表失败
  - 后端没有任何真实测试
  - 用户密码哈希通过接口返回前端
  - 注册接口可任意指定角色
  - 患者、医生、管理员接口缺少角色边界
  - 前端根路由固定跳患者首页，且缺少角色路由守卫
- PRD 核心模块当前可对应到的落地点主要是：
  - 用户注册登录与资料管理
  - 患者健康档案
  - 健康数据录入与查询
  - 医生资料维护与审核
  - 在线咨询问答

## Technical Decisions
| Decision | Rationale |
|----------|-----------|
| 默认环境改为 H2 文件库，自保留 PostgreSQL profile | 满足本机“开箱即起”目标，同时保留真库切换能力 |
| 增加默认账号初始化器 | 便于患者 / 医生 / 管理员链路抽测 |
| 新增后端冒烟测试并覆盖越权场景 | 让 `mvn test` 真正覆盖启动、登录、健康数据和权限边界 |
| 前端补齐角色路由守卫 | 避免页面层面的跨角色误访问 |

## Issues Encountered
| Issue | Resolution |
|-------|------------|
| 默认 PostgreSQL 凭据导致启动失败 | 已改为默认 H2，自保留 `application-postgresql.yml` |
| H2 建表时 `value` 命中保留字 | 已将列名改为 `metric_value` |
| 注册/个人信息接口返回密码哈希 | 已将密码字段改为 `WRITE_ONLY` |
| 管理端接口只有登录鉴权，没有角色鉴权 | 已补齐患者 / 医生 / 管理员角色边界 |

## Resources
- `readme_simple.md`
- `docs/project-check-tracker.md`
- `docs/checks/030-smart-health-management-platform.md`
- `docs/plans/2026-04-17-030-health-management-check-plan.md`
- `030-backend/PRD/030-智能健康管理平台-PRD.md`
- `030-backend/pom.xml`
- `030-backend/src/main/resources/application.yml`
- `030-backend/src/main/resources/application-postgresql.yml`
- `030-backend/src/main/java/com/xiaou/health/config/DataInitializer.java`
- `030-backend/src/test/java/com/xiaou/health/HealthManagementApplicationTests.java`
- `030-frontend/package.json`
- `030-frontend/src/App.jsx`
- `030-frontend/src/components/ProtectedRoute.jsx`
- `task_plan.md`
- `progress.md`
