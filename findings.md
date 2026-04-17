# Findings & Decisions

## Requirements
- 按项目编号逐个检查毕设项目。
- 当前进入 `029` 中药食疗推荐交流平台巡检。
- 检查范围包括 PRD、配置文件、SQL、前后端实现、JDK 17 兼容性、测试、构建、启动和核心链路。
- 发现明确问题后不能只记录，要直接修复并复测。
- 需要持续更新总台账、单项目检查文档和会话文件。

## Research Findings
- `029` 项目目录已确认存在：
  - `029-backend`
  - `029-frontend`
- 项目名称存在文档口径差异：
  - `readme_simple.md` 为“中药食疗推荐交流平台”
  - `029-backend/README.md` 与 `029-backend/PRD/029-中药餐饮推荐交流平台-PRD.md` 更接近“中药餐饮推荐交流平台”
- 项目形态初步确认：
  - 后端：`Spring Boot 3.2.0`
  - 前端：`Vue 3 + Vite`
- 后端 `pom.xml` 当前已确认：
  - 已切换到 `mybatis-plus-spring-boot3-starter`
  - 默认运行环境已切换为 H2，自保留 MySQL profile
  - 已声明 `Java 17`
- 文档层面已确认的高风险点：
  - README 未给默认管理员账号
  - 管理端接口命名在 README 与 PRD 间不一致
  - 详情接口、评论接口命名在 README 与 PRD 间不一致
  - PRD 功能范围明显大于 README 暴露接口
- 真实工程问题已确认并修复：
  - `Result<T>` 泛型静态工厂方法导致 JDK 17 编译失败
  - `JwtUtil` 与 `jjwt 0.12.3` API 不兼容
  - 旧 `MyBatis-Plus` starter 导致 Spring Boot 3 初始化失败
  - 默认 MySQL 凭据导致启动失败
  - `Collection` 实体类型别名与 `java.util.Collection` 冲突
  - JWT 密钥长度不足导致登录失败
  - 用户密码哈希通过接口返回前端
  - 前端缺页、响应字段映射错误、登录态保存错误、注册字段缺失、接口路径不一致
- PRD 核心模块初步提炼为：
  - 用户认证与健康档案
  - 食谱浏览 / 搜索 / 推荐
  - 食材库与禁忌搭配
  - 社区交流与评论
  - 创作者内容发布与管理
  - 管理端审核、管理与统计

## Technical Decisions
| Decision | Rationale |
|----------|-----------|
| 默认环境改为 H2 文件库，自保留 MySQL profile | 满足本机“开箱即起”目标，同时保留真库切换能力 |
| 增加默认种子数据 | 便于登录、前端展示和后台接口抽测 |
| 新增后端冒烟测试 | 让 `mvn test` 真正覆盖启动与登录链路 |
| 前端采用“补齐页面 + 修接口对接 + 修状态管理”一次性收口 | 先解决构建失败，再保证真实交互可用 |

## Issues Encountered
| Issue | Resolution |
|-------|------------|
| 项目名称“食疗 / 餐饮”口径不一致 | 台账沿用“中药食疗推荐交流平台”，检查报告中显式标注工程口径冲突 |
| 默认环境强依赖本地 MySQL | 已改为默认 H2，自保留 `application-mysql.yml` |
| 前端无足够页面导致构建失败 | 已补齐全部路由页面并通过构建与启动验证 |
| 后端虽可编译但原本没有真实测试 | 已新增 MockMvc 冒烟测试并验证通过 |

## Resources
- `readme_simple.md`
- `readme.md`
- `docs/project-check-tracker.md`
- `docs/checks/029-herbal-diet-recommendation-community-platform.md`
- `029-backend/README.md`
- `029-backend/PRD/029-中药餐饮推荐交流平台-PRD.md`
- `029-backend/pom.xml`
- `029-frontend/package.json`
- `029-backend/src/test/java/com/xiaou/herbal/HerbalFoodApplicationTests.java`
- `task_plan.md`
- `findings.md`
- `progress.md`
