# 124 电动车充电桩预约与运维管理系统检查报告

## 检查范围

- 项目编号：`124`
- 项目名称：电动车充电桩预约与运维管理系统
- 检查时间：`2026-05-20`
- 目录：
  - 后端：`124-backend`
  - 前端：`124-frontend`
- 主要对照文档：
  - `124-backend/PRD.md`
  - `124-backend/PLAN.md`
  - `124-backend/sql/init.sql`

## 结论摘要

`124` 当前应标记为 `待修复`。本轮静态核对已确认项目的核心运行配置仍串到 `122`，导致 PRD、PLAN、SQL 初始化脚本与 `application.yml` 出现直接冲突：

1. `PLAN.md` 和 `sql/init.sql` 明确要求使用 `ev_charging_124`。
2. 实际运行配置却仍连到 `smart_chargepile_safety_122`。
3. 这类串项会直接导致默认环境和文档口径不一致，属于明显的项目主题错配。

## 主要问题

### 124-1 数据库配置仍指向 `122` 项目

- `application.yml` 的数据源仍写死为 `smart_chargepile_safety_122`：`124-backend/src/main/resources/application.yml:6`
- `PLAN.md` 写明应创建 `ev_charging_124`：`124-backend/PLAN.md:15`
- 初始化 SQL 也明确创建并使用 `ev_charging_124`：`124-backend/sql/init.sql:1-3`

影响：

- 即使按 `init.sql` 正常导库，应用默认也不会连接到该库。
- 当前实现与项目题目“电动车充电桩预约与运维管理系统”不一致，属于硬性配置串项，不是普通文档疏漏。

### 124-2 完成标准仍保留“不执行编译验证”

- `PLAN.md` 完成标准仍写为“不执行编译验证”：`124-backend/PLAN.md:40`

影响：

- 当前项目即便存在核心配置串项，也可能在纯静态交付流程中被遗漏。
- 这和实际巡检要求的“至少确认配置、启动口径和 PRD 一致”不一致。

## 静态核对记录

- 本轮以静态巡检为主，已核对：
  - `PRD.md`
  - `PLAN.md`
  - `application.yml`
  - `sql/init.sql`
- 本轮未执行 Maven / NPM 编译与启动验证。

## 当前判断

`124` 当前状态应标记为：`待修复`

- 文档与配置一致性：`不通过`
- 默认数据库口径：`不通过`
- 编译与启动验证：`本轮未执行`

## 建议下一步

1. 先将 `application.yml` 的数据库名从 `smart_chargepile_safety_122` 修正为 `ev_charging_124`。
2. 复核是否还残留 `122` 项目的表名、包名、模块字段或统计口径。
3. 修正后至少补一次后端启动和登录冒烟验证，确认默认配置与 `init.sql` 已一致。
