# Progress Log

## Session: 2026-04-15

### Phase 1: 建档与上下文同步
- **Status:** complete
- Actions taken:
  - 已确认 `029` 对应“中药食疗推荐交流平台”
  - 已确认 `029-backend`、`029-frontend`、PRD 和 README 路径
  - 已建立单项目检查文档、计划文件并更新总台账状态

### Phase 2: 文档与源码核对
- **Status:** complete
- Actions taken:
  - 已核对 PRD、README、后端配置、SQL、控制器与前端目录结构
  - 已确认项目名存在“食疗 / 餐饮”命名差异
  - 已确认后端原始工程存在 JDK 17 编译、Boot 3 starter、MySQL 强依赖和 JWT 兼容问题
  - 已确认前端原始工程存在缺页、登录态映射和接口路径不一致问题

### Phase 3: 构建、启动与抽测
- **Status:** complete
- Actions taken:
  - 后端已通过 `mvn test -DskipTests=false`
  - 后端已通过 `mvn spring-boot:run` 启动验证
  - 已抽测 `recipe/list`、`user/login`、`admin/users`、`user/info`
  - 前端已通过 `npm run build`
  - 前端已通过 `npm run dev -- --host 127.0.0.1 --port 5173` 启动验证
  - 已验证前端首页 `HTTP 200`

### Phase 4: 问题修复与复测
- **Status:** complete
- Actions taken:
  - 已修复后端 `Result` 泛型、JJWT 解析、MyBatis-Plus starter、H2 自举、别名冲突、JWT 密钥长度与密码哈希泄露
  - 已修复前端缺页、响应字段不匹配、登录态保存错误、注册字段缺失、首页字段映射错误和接口路径不一致
  - 已新增后端冒烟测试并复测通过

### Phase 5: 回填与清理
- **Status:** complete
- Actions taken:
  - 已更新单项目检查报告和总台账
  - 已更新 `task_plan.md`、`findings.md`、`progress.md`
  - 已停止本轮用于验证的 `8080` 和 `5173` 进程，保留 `logs/` 日志

## Current State

- `029` 已达到“可测试、可构建、可启动、核心链路可用”的通过标准
- 默认运行环境已切换为 H2，自保留 MySQL profile
- 可直接进入下一个项目巡检
