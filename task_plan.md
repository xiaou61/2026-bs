# Task Plan: 029 项目巡检与修复

## Goal
完成 `029` 中药食疗推荐交流平台的文档一致性、JDK 17 兼容性、测试、构建、启动与核心链路巡检；发现明确问题后直接修复并复测，同时持续同步总台账与检查报告。

## Current Phase
Phase 5

## Phases

### Phase 1: 建档与上下文同步
- [x] 确认 `029` 项目名称、目录结构与资料范围
- [x] 建立 `029` 巡检文档骨架并更新总台账
- [x] 切换本轮会话计划文件
- **Status:** complete

### Phase 2: 文档与源码核对
- [x] 核对 PRD、README、后端配置、SQL、控制器、服务层与前端页面
- [x] 判断实现范围与 PRD 的偏差
- [x] 记录构建风险、依赖缺口和已知断链点
- **Status:** complete

### Phase 3: 构建、启动与抽测
- [x] 执行后端测试 / 编译
- [x] 执行前端构建
- [x] 启动前后端并抽测登录、食谱、食材、社区、健康档案与后台接口
- **Status:** complete

### Phase 4: 问题修复与复测
- [x] 对能明确闭环的问题直接修复
- [x] 重新执行相关测试、构建与启动验证
- [x] 更新检查结论和剩余风险
- **Status:** complete

### Phase 5: 回填与清理
- [x] 更新总台账与单项目检查报告
- [x] 更新 `findings.md` / `progress.md`
- [x] 清理本项目临时日志与运行状态
- **Status:** complete

## Key Questions
1. `029` 当前能否在 JDK 17 下直接通过后端测试与前端构建？
2. 默认 `MySQL + Redis` 是否会成为启动或登录阻塞点？
3. 文档中的“中药食疗 / 中药餐饮”命名差异，最终应以哪套工程口径为准？
4. 食谱、食材、社区、健康档案与管理端是否具备真实闭环？

## Decisions Made
| Decision | Rationale |
|----------|-----------|
| 放弃多 agent，改为单线修复 `029` | 用户已明确要求不再多开 agent |
| 默认运行环境切换到 H2，自保留 MySQL profile | 满足“默认可测试、可启动”的巡检目标 |
| 前端优先补齐缺页，再统一修请求 / 状态逻辑 | 先打通构建，再打通真实页面链路 |

## Errors Encountered
| Error | Attempt | Resolution |
|-------|---------|------------|
| `Result<T>` 泛型工厂方法在 JDK 17 下编译失败 | 1 | 改为显式构造返回，消除类型推断错误 |
| `JwtUtil` 使用 `parserBuilder()` 无法适配 `jjwt 0.12.3` | 1 | 改为 `Jwts.parser().verifyWith(key).build().parseSignedClaims(...)` |
| `mybatis-plus-boot-starter` 与 Spring Boot 3 初始化冲突 | 1 | 切换到 `mybatis-plus-spring-boot3-starter` |
| 默认 MySQL `root/root` 无法启动 | 1 | 改为默认 H2 自举，新增 `application-mysql.yml` 保留真库配置 |
| `Collection` 实体与 MyBatis 别名冲突 | 1 | 移除 `type-aliases-package` |
| JWT 登录时报 HS512 密钥长度不足 | 1 | 延长密钥并保留 HS512 |
| 前端路由引用大量不存在页面，Vite 构建失败 | 1 | 补齐缺失视图并修正现有页面逻辑 |

## Notes
- `029-backend` 已通过 `mvn test -DskipTests=false`，并新增 2 个冒烟测试。
- `029-frontend` 已通过 `npm run build`，并验证 `npm run dev -- --host 127.0.0.1 --port 5173` 可启动。
- 默认账号：`admin029 / creator029 / user029 / applicant029`，统一密码 `123456`。
