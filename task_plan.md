## Session: 2026-04-24 项目预览自动化

# Task Plan: 119-120 新增毕设项目生成与推送

## Goal
生成 `119-backend`、`119-frontend`、`120-backend` 与 `120-frontend`，补齐 PRD、PLAN、SQL 初始化脚本、前后端核心模块、合集 README 和候选清单状态；仅做静态验证，不执行编译构建，并推送到 GitHub。

## Current Phase
Completed: `119/120` 已生成后端、前端、SQL、PRD、PLAN，并完成静态数量、配置、残留关键词、注释扫描、关键 Mapper 转义检查、文档登记、临时脚本清理、提交与推送。

## Phases
- [x] 确认候选题目、规则和技术栈
- [x] 生成 `119` 后端、前端、SQL、PRD、PLAN
- [x] 生成 `120` 后端、前端、SQL、PRD、PLAN
- [x] 静态扫描数量、配置、残留关键词和注释
- [x] 检查 MyBatis 注解 SQL 空字符串转义和账号响应密码脱敏
- [x] 更新 `readme.md`、`readme_simple.md`、`docs/topic-candidates-097-146.md`
- [x] 清理临时脚本、最终扫描
- [x] 提交并推送

## Decisions Made
| Decision | Rationale |
|----------|-----------|
| `119` 使用 MyBatis-Plus | `118` 已使用 MyBatis + PageHelper，继续保持交替 |
| `120` 使用 MyBatis + PageHelper | `119` 已使用 MyBatis-Plus，继续保持交替 |
| `119` 端口使用 `8119/3119`，Redis DB `22` | 延续新增项目编号递增规则 |
| `120` 端口使用 `8120/3120`，Redis DB `23` | 延续新增项目编号递增规则 |
| 数据库分别使用 `spare_life_119`、`digital_twin_park_120` | 与设备备件寿命预测、数字孪生园区巡检主题绑定 |

# Task Plan: 121-122 新增毕设项目生成

## Goal
生成 `121-backend`、`121-frontend`、`122-backend` 与 `122-frontend`，补齐 PRD、PLAN、SQL 初始化脚本、前后端核心模块、合集 README 和候选清单状态；仅做静态验证，不执行编译构建。

## Current Phase
Completed: `121/122` 已生成后端、前端、SQL、PRD、PLAN，并完成静态数量、配置、残留关键词、注释扫描、关键 Mapper 转义检查、文档登记、提交与推送。

## Phases
- [x] 确认候选题目、规则和技术栈
- [x] 生成 `121` 后端、前端、SQL、PRD、PLAN
- [x] 生成 `122` 后端、前端、SQL、PRD、PLAN
- [x] 静态扫描数量、配置、残留关键词和注释
- [x] 检查 MyBatis 注解 SQL 空字符串转义和账号响应密码脱敏
- [x] 更新 `readme.md`、`readme_simple.md`、`docs/topic-candidates-097-146.md`
- [x] 清理临时脚本、最终扫描、提交并推送

## Decisions Made
| Decision | Rationale |
|----------|-----------|
| `121` 使用 MyBatis-Plus | `120` 已使用 MyBatis + PageHelper，继续保持交替 |
| `122` 使用 MyBatis + PageHelper | `121` 已使用 MyBatis-Plus，继续保持交替 |
| `121` 端口使用 `8121/3121`，Redis DB `24` | 延续新增项目编号递增规则 |
| `122` 端口使用 `8122/3122`，Redis DB `25` | 延续新增项目编号递增规则 |
| 数据库分别使用 `drone_inspection_121`、`smart_worksite_safety_122` | 与无人机巡检、智慧工地安全巡检主题绑定 |

# Task Plan: 123-124 新增毕设项目生成

## Goal
生成 `123-backend`、`123-frontend`、`124-backend` 与 `124-frontend`，补齐 PRD、PLAN、SQL 初始化脚本、前后端核心模块、合集 README 和候选清单状态；仅做静态验证，不执行编译构建。

## Current Phase
Completed: `123/124` 已生成后端、前端、SQL、PRD、PLAN，并完成静态数量、配置、残留关键词、注释扫描、关键 Mapper 转义检查、文档登记、提交与推送。

## Phases
- [x] 确认候选题目、规则和技术栈
- [x] 生成 `123` 后端、前端、SQL、PRD、PLAN
- [x] 生成 `124` 后端、前端、SQL、PRD、PLAN
- [x] 静态扫描数量、配置、残留关键词和注释
- [x] 检查 MyBatis 注解 SQL 空字符串转义和账号响应密码脱敏
- [x] 更新 `readme.md`、`readme_simple.md`、`docs/topic-candidates-097-146.md`
- [x] 清理临时脚本、最终扫描、提交并推送

## Decisions Made
| Decision | Rationale |
|----------|-----------|
| `123` 使用 MyBatis-Plus | `122` 已使用 MyBatis + PageHelper，继续保持交替 |
| `124` 使用 MyBatis + PageHelper | `123` 已使用 MyBatis-Plus，继续保持交替 |
| `123` 端口使用 `8123/3123`，Redis DB `26` | 延续新增项目编号递增规则 |
| `124` 端口使用 `8124/3124`，Redis DB `27` | 延续新增项目编号递增规则 |
| 数据库分别使用 `urban_flood_dispatch_123`、`ev_charging_124` | 与城市内涝应急调度、电动车充电桩预约运维主题绑定 |

# Task Plan: 118 智能仓储 AGV 任务调度与库位优化系统生成

## Goal
生成 `118-backend` 与 `118-frontend`，补齐 PRD、PLAN、SQL 初始化脚本、前后端核心模块、合集 README 和候选清单状态；仅做静态验证，不执行编译构建。

## Current Phase
Completed: `118` 已生成后端、前端、SQL、PRD、PLAN，并完成静态数量、配置、残留关键词、注释扫描和关键 Mapper 转义检查；下一项目为 `119`。

## Phases
- [x] 确认候选题目、规则和技术栈
- [x] 生成后端、前端、SQL、PRD、PLAN
- [x] 静态扫描数量、配置、残留关键词和注释
- [x] 修复 MyBatis 注解 SQL 空字符串转义和账号响应密码脱敏
- [x] 更新 `readme.md`、`readme_simple.md`、`docs/topic-candidates-097-146.md`
- [x] 清理临时脚本并汇总结果

## Decisions Made
| Decision | Rationale |
|----------|-----------|
| `118` 使用 MyBatis + PageHelper | `117` 已使用 MyBatis-Plus，继续保持交替 |
| 端口使用 `8118/3118`，Redis DB `21` | 延续新增项目编号递增规则 |
| 数据库使用 `smart_warehouse_agv_118` | 与智能仓储 AGV 调度主题绑定 |

# Task Plan: 117 本地生活服务券核销与商户结算系统生成

## Goal
生成 `117-backend` 与 `117-frontend`，补齐 PRD、PLAN、SQL 初始化脚本、前后端核心模块、合集 README 和候选清单状态；仅做静态验证，不执行编译构建。

## Current Phase
Completed: `117` 已生成后端、前端、SQL、PRD、PLAN，并完成静态数量、配置、残留关键词和注释扫描；下一项目为 `118`。

## Phases
- [x] 确认候选题目、规则和技术栈
- [x] 生成后端、前端、SQL、PRD、PLAN
- [x] 静态扫描数量、配置、残留关键词和注释
- [x] 更新 `readme.md`、`readme_simple.md`、`docs/topic-candidates-097-146.md`
- [x] 清理临时脚本并汇总结果

## Decisions Made
| Decision | Rationale |
|----------|-----------|
| `117` 使用 MyBatis-Plus | `116` 已使用 MyBatis + PageHelper，继续保持交替 |
| 端口使用 `8117/3117`，Redis DB `20` | 延续 `115/116` 编号递增规则 |
| 数据库使用 `local_voucher_117` | 与本地生活服务券主题绑定 |


### Goal
- 为真实存在的毕业设计项目建立“运行 + 多图截图 + 项目详览页 + 分组索引页 + README 入口”的预览体系。
- 当前先完成 `001` 作为首个样板项目，再按编号继续推进。

### Current Phase
- `001` 已完成首轮运行、截图和文档生成。
- `002` 已完成运行、截图和文档生成。
- `003` 已完成运行、截图和文档生成。
- `004` 已完成运行、截图和文档生成。
- `005` 已完成运行、截图和文档生成。
- `006` 已完成运行、截图和文档生成。
- 下一步进入 `007` 的预览推进。

### Current Task Breakdown
- [x] 设计预览目录结构和文档组织方式
- [x] 建立 `docs/previews/` 目录骨架
- [x] 实现项目扫描、账号提取、SQL 导入、服务启动、Markdown 渲染脚本
- [x] 完成 `001` 的数据库初始化与前后端启动
- [x] 完成 `001` 的多角色多页面截图
- [x] 生成 `001` 项目详览页
- [x] 生成 `001-010` 分组索引页
- [x] 在 `readme.md` 中新增项目预览入口
- [x] 推进 `002`
- [x] 推进 `003`
- [x] 推进 `004`
- [x] 推进 `005`
- [x] 推进 `006`

### Notes
- 当前截图采用“浏览器工具操作页面，脚本负责运行和文档生成”的方式。
- `001` 前端默认尝试占用 `3000`，但由于本机已有服务占用，实际运行在 `3002`。
- 预览流水线已修复为优先读取前端真实启动地址，而不是盲信默认端口。
- `002` 已补齐 `20` 张截图，覆盖管理员、教师、学生三类主要页面。
- `002` 预览环境实际使用 `frontend:3002`、`backend:18080`。
- `003` 已补齐 `24` 张截图，覆盖游客、消费者、农户、管理员四类视角。
- `003` 是后端一体化的 Thymeleaf 项目，核心页面来自服务端模板，不需要额外前端工程。
- `003` 预览过程中修复了两个真实后端问题：
  - `JwtInterceptor` 仅写入 `role`，未写入控制器实际依赖的 `userRole`
  - `WebConfig` 中 `/api/notices/{id}` 的公开排除规则误伤了 `/api/notices/all`
- 预览脚本已补强：
  - `prepare` 前会先停止同项目上一轮实例
  - 支持从 `TEST_ACCOUNTS.md` 自动识别默认账号
- `004` 已补齐 `20` 张截图，覆盖游客、管理员、张三、李四四类视角。
- `004` 已真实覆盖的功能包括：
  - 登录 / 注册
  - 双用户实时聊天
  - 聊天历史加载
  - 好友搜索与添加
  - 分组创建
  - 通知列表 / 未读通知 / 全部已读
  - 个人资料编辑
- `004` 预览脚本已继续补强：
  - 支持从“统一密码 + 用户名列表”的 `ACCOUNTS.md` 中提取账号
  - 只在密码说明之后读取用户名列表，避免把技术栈条目误识别为账号
  - `prepare/stop` 现直接启动和停止 `mvn.cmd` / `npm.cmd`，避免只杀掉短命壳进程
- `005` 已补齐 `15` 张截图，覆盖游客、管理员、普通用户三类视角。
- `005` 已真实覆盖的功能包括：
  - 首页 / 登录 / 注册
  - 管理员创建问卷
  - 五种题型的问卷编辑
  - 问卷发布与分享链接展示
  - 游客填写并提交问卷
  - 统计页面查看题目分布与文本答案
- `005` 预览过程中确认了一个真实实现差异：
  - 文档声称普通用户可以在列表中看到已发布问卷，但 `user1` 登录后的 `/dashboard` 仍显示“暂无可用问卷”
  - 普通用户如果直接访问统计页 URL，仍可正常查看统计数据
- 预览脚本已继续补强：
  - 支持从 `ACCOUNTS.md` 的“用户名/密码/角色”多行块中提取默认账号
- `006` 基线已确认：
  - 项目名为“校园失物招领信息管理平台”
  - 仅存在 `006-backend`，页面由 Spring Boot + Thymeleaf 模板直接输出
  - 系统默认端口为 `8086`，数据库为 `lost_found_db`
  - 认证方式是 `HttpSession`，并非 JWT
  - 关键页面包括首页、失物/招领列表、详情、发布、我的发布、认领记录、收藏、通知、个人中心、管理员用户/分类管理
- `006` 的文档与实现存在口径差异：
  - `readme.md` 与 `PRD.md` 写了“Vue 3 / JWT”
  - 实际代码是 Bootstrap + jQuery + 服务端模板，登录后写入 Session
- `006` 已补齐 `23` 张截图，覆盖游客、管理员、普通用户张三、普通用户李四四类视角。
- `006` 已真实覆盖的功能包括：
  - 登录 / 注册
  - 首页聚合数据
  - 失物列表 / 详情 / 发布 / 我的失物
  - 招领详情 / 收藏 / 认领申请
  - 发出的认领 / 收到的认领 / 审核通过
  - 通知列表 / 审核结果通知
  - 个人资料
  - 管理员用户管理 / 分类管理
- `006` 预览过程中确认并修复了一个真实前端联调问题：
  - 发布失物 / 发布招领页面直接提交 `datetime-local` 原始值 `yyyy-MM-ddTHH:mm`
  - 后端 `LocalDateTime` 无法按该格式反序列化，接口返回 `400`
  - 现已在发布页前端提交前统一转成 `yyyy-MM-dd HH:mm:ss`
- `006` 预览过程中确认了一个静态资源问题：
  - 初始化 SQL 引用了 `/images/sample1.jpg`、`/images/sample2.jpg`
  - 项目实际未提供这些示例图片，详情页图片请求返回 `404`

# Task Plan: 036 项目巡检与修复

## Goal
完成 `036` 圆梦捐赠公益项目的文档一致性、JDK 17 兼容性、测试、构建、启动与核心业务链路巡检；发现问题后直接修复并复测，同时同步总台账和单项目检查文档。

## Current Phase
Completed: `036` 巡检、修复、测试、启动验证与文档回填已完成；下一项目为 `037`。

## Phases

### Phase 1: 建档与上下文同步
- [x] 读取续检记录，确认上一项目停在 `035`
- [x] 读取总台账，确认下一项目为 `036`
- [x] 确认 `036` 目录结构与资料范围
- [x] 确认是否已存在单项目检查文档
- **Status:** completed

### Phase 2: 基线核对
- [x] 读取后端关键配置、依赖、启动入口与数据库脚本
- [x] 读取前端关键配置、入口、代理与构建脚本
- [x] 识别默认环境高风险点
- **Status:** completed

### Phase 3: 运行验证与根因定位
- [x] 执行后端测试或构建
- [x] 尝试真实启动后端并抽测核心接口
- [x] 执行前端构建或启动验证
- [x] 如存在故障，定位根因
- **Status:** completed

### Phase 4: 问题修复与复测
- [x] 修复巡检中发现的问题
- [x] 补充必要测试或脚本
- [x] 完成后端复测
- [x] 完成前端复测
- **Status:** completed

### Phase 5: 回填与清理
- [x] 新增或更新 `036` 单项目检查文档
- [x] 更新总台账
- [x] 更新 `findings.md` / `progress.md`
- [x] 清理本轮后台进程
- **Status:** completed

## Key Questions
1. `036` 的真实项目名以哪个文档口径为准？
2. `036` 是否能在 JDK 17 下通过测试并直接启动？
3. 默认数据库、鉴权与前后端联调链路是否可用？
4. 当前实现与 PRD / 总结文档之间的主要差距在哪里？

## Decisions Made
| Decision | Rationale |
|----------|-----------|
| 先从总台账和规划文件恢复上下文，再进入 `036` | 避免重复检查 `035` 或漏掉续检状态 |
| 优先确认 `036` 单项目文档是否存在 | 便于判断是续做还是从零建档 |
| 默认切换到 H2 自举，保留 PostgreSQL profile | 兼顾本机开箱验证与后续真实数据库部署 |
| JWT 当前用户作为权限判定来源 | 避免请求头默认 `userId=1` 冒充导致越权 |

## Errors Encountered
| Error | Attempt | Resolution |
|-------|---------|------------|
| `docs/checks` 中未找到 `036` 单项目检查文档 | 列出目录并按编号检索 | 记为本轮需新建文档，待完成巡检后回填 |
| `036-backend/mvn test` 出现 100 个 Java 编译错误 | 读取报错文件并用 `rg` 扫描实体字符 | 根因是源码被 HTML 实体转义，已对 `036` Java/Vue/HTML/JS 源码做限定范围解码 |
| `036-frontend/npm run build` 提示 `vite` 未识别 | 检查 `package.json` | 已执行 `npm install` 并完成前端构建 |
| 默认后端强依赖 PostgreSQL | 读取 `application.yml` 和 SQL 脚本 | 默认改为 H2 自举，新增 `application-postgresql.yml` 保留原部署入口 |
| 接口可通过默认 `userId=1` 请求头冒充 | 抽查控制器与服务参数 | 改为统一解析 JWT 当前用户并补权限测试 |

## Notes
- 当前已确认：
  - 总台账最新完成到 `036`
  - 下一项目为 `037`
  - `036` 目录为 `036-backend` + `036-frontend`
  - 后端默认 H2 自举，PostgreSQL 通过 `postgresql` profile 保留
  - 后端 `mvn test` 通过，覆盖鉴权、脱敏、项目权限与捐赠校验
  - 前端 `npm run build` 通过，浏览器联调覆盖登录、详情捐赠和个人中心记录
  - `docs/checks/036-dream-donation-platform.md` 已创建并回填验证结论

# Task Plan: 079 项目巡检与修复

## Goal
完成 `079` 计算机学院校友网的文档一致性、JDK 17 兼容性、测试、H2 默认启动、MySQL `root / 1234` 验证、前端构建与代理联调；发现问题后直接修复并复测，同时同步总台账和单项目检查文档。

## Current Phase
Completed: `079` 巡检、修复、测试、MySQL 真实 HTTP 验证、前端代理验证与文档回填已完成；下一项目为 `080`。

## Phases
- [x] 读取续检记录，确认上一项目停在 `078`
- [x] 确认 `079` 后端、前端、PRD、SQL 与默认账号
- [x] 修复默认 H2 自举、保留正式 MySQL profile，并新增 `mysql-verify` 验证 profile
- [x] 修复 JWT、HTTP 状态、登出失效、密码脱敏、后台权限和资源归属校验
- [x] 补充后端冒烟测试并通过 `mvn test`
- [x] 修复前端端口、代理和 Bearer token，并通过 `npm run build`
- [x] 使用 MySQL `root / 1234` 真实启动 `8079` 并抽测核心链路
- [x] 启动前端 `3079` 并验证代理登录
- [x] 新增 `079` 检查报告、README、启动说明并更新总台账

## Errors Encountered
| Error | Attempt | Resolution |
|-------|---------|------------|
| H2 初始化 `grade.year` 失败 | 首次 `mvn test` | `NON_KEYWORDS` 补充 `YEAR` |
| 公开 GET 通配符误放行 DELETE | 冒烟测试删除他人论坛帖返回 `401` | 将公开路由改为拦截器 GET 可选登录，写操作强制鉴权 |
| 本机无 `mysql` 命令 | MySQL CLI 验证失败 | 使用 JDK + MySQL Connector/J 通过 JShell 验证连接 |
| PowerShell 读取错误响应体失败 | 真实 HTTP 脚本首轮失败 | 改用 `-SkipHttpErrorCheck` |

# Task Plan: 080 项目巡检与修复

## Goal
完成 `080` 基于SpringBoot和Vue的贫困地区儿童资助网站的文档一致性、JDK 17 兼容性、测试、H2 默认启动、MySQL `root / 1234` 验证、前端构建与代理联调；发现问题后直接修复并复测，同时同步总台账和单项目检查文档。

## Current Phase
Completed: `080` 巡检、修复、测试、MySQL 真实 HTTP 验证、前端代理验证与文档回填已完成；下一项目为 `081`。

## Phases
- [x] 确认 `080` 后端、前端、PRD、SQL 与默认账号
- [x] 修复默认 H2 自举、保留正式 MySQL profile，并新增 `mysql-verify` 验证 profile
- [x] 修复 JWT、HTTP 状态、登出失效、密码脱敏、后台权限和资源归属校验
- [x] 补充后端冒烟测试并通过 `mvn test`
- [x] 修复前端端口、代理、Bearer token 和登出 API，并通过 `npm run build`
- [x] 使用 MySQL `root / 1234` 真实启动 `8080` 并抽测核心链路
- [x] 启动前端 `3080` 并验证代理登录
- [x] 新增 `080` 检查报告、README、启动说明并更新总台账

## Errors Encountered
| Error | Attempt | Resolution |
|-------|---------|------------|
| 原始默认配置强依赖 MySQL/Redis，且 SQL 含 `DROP DATABASE` | 读取 `application.yml` 与 `sql/init.sql` | 默认改为 H2 自举，正式 MySQL profile 保留，MySQL 验证走临时库 |
| JDK 17 下 JJWT 0.9.1 缺 JAXB | 启动/测试暴露运行时兼容问题 | 增加 `jaxb-api` 与 `jaxb-runtime` |
| 登录响应会包含 password 字段 | 自动化测试检查登录响应 | 改为响应脱敏，复测通过 |
| 普通捐赠人可访问管理型接口或伪造捐赠 donorId | 通读控制器和服务并补权限测试 | 增加管理员校验、角色边界和 JWT 当前用户绑定 |
| 前端请求头不带 `Bearer` | 读取 `src/api/request.js` | 请求拦截器改为 `Authorization: Bearer <token>` |

# Task Plan: 081 项目巡检与修复

## Goal
完成 `081` 基于SpringBoot+Vue+uniapp的电器维修系统小程序的文档一致性、JDK 17 兼容性、测试、H2 默认启动、MySQL `root / 1234` 验证、管理端构建与代理联调、小程序接口口径核查；发现问题后直接修复并复测，同时同步总台账和单项目检查文档。

## Current Phase
Completed: `081` 巡检、修复、测试、MySQL 真实 HTTP 验证、管理端代理验证与文档回填已完成；下一项目为 `082`。

## Phases
- [x] 确认 `081` 后端、管理端、小程序端、PRD、SQL 与默认账号
- [x] 修复默认 H2 自举、保留正式 MySQL profile，并新增 `mysql-verify` 验证 profile
- [x] 修复 JWT、HTTP 状态、登出失效、密码脱敏、后台权限和工单归属校验
- [x] 补充后端冒烟测试并通过 `mvn test`
- [x] 修复管理端端口、代理、Bearer token 和上传预览 URL，并通过 `npm run build`
- [x] 修复小程序 Bearer token 和图片 URL 口径
- [x] 使用 MySQL `root / 1234` 真实启动 `8080` 并抽测核心链路
- [x] 启动管理端 `3081` 并验证代理登录
- [x] 新增 `081` 检查报告、README、启动说明并更新总台账

## Errors Encountered
| Error | Attempt | Resolution |
|-------|---------|------------|
| 原始默认配置强依赖 MySQL `root/root`、Redis 和默认 `8080` | 读取 `application.yml` 与 `sql/init.sql` | 默认改为 H2 自举，正式 MySQL profile 改为 `root / 1234` |
| 原始 SQL 含 `DROP DATABASE IF EXISTS repair_db` | 核查 SQL 初始化脚本 | MySQL 验证走 `repair_081_verify` 临时库，避免误删正式库 |
| `081-backend/mvn test` 原始结果为 `No tests to run` | 增加 SpringBootTest + TestRestTemplate 冒烟测试 | 当前 `mvn test` 通过，覆盖报修/派单/技师处理/支付评价/权限/登出 |
| JDK 17 下 JJWT 0.9.1 缺 JAXB | 对照 080 的 JDK 17 修复经验 | 增加 `jaxb-api` 与 `jaxb-runtime` |
| 前端原始构建因缺少依赖失败 | 执行 `npm install` | 已生成 `package-lock.json`，构建通过 |
| 客户可伪造 `userId` 或访问他人工单数据 | 通读工单控制器和服务 | 改为 JWT 当前用户绑定，并补本人/技师/管理员校验 |

# Task Plan: 082 项目巡检与修复

## Goal
完成 `082` 公考学习平台的文档一致性、JDK 17 兼容性、默认启动、测试、MySQL `root / 1234` 真实验证、前端构建与代理联调；发现问题后直接修复并复测，同时同步总台账和单项目检查文档。

## Current Phase
Completed: `082` 巡检、修复、测试、MySQL 真实 HTTP 验证、前端代理验证与文档回填已完成；下一项目为 `083`。

## Phases
- [x] 确认 `082` 后端、前端、PRD、SQL、默认账号与原始配置风险
- [x] 修复默认 H2 自举、保留正式 MySQL profile，并新增 `mysql-verify` 验证 profile
- [x] 修复 JDK 17/JJWT、MySQL 驱动坐标、JWT Bearer、HTTP 状态、登出失效和密码脱敏
- [x] 补充管理员、讲师、学员权限边界，修复学习计划/考试记录用户归属和课程讲师归属
- [x] 补充 Redis 不可用时公开学科/公告接口数据库回退
- [x] 修复前端端口、代理、Bearer token 和登出 API
- [x] 新增后端冒烟测试并通过 `mvn test`
- [x] 完成前端 `npm install` 与 `npm run build`
- [x] 使用 MySQL `root / 1234` 的 `gongkao_082_verify` 临时库真实启动并抽测核心链路
- [x] 启动前端 `3082` 并验证代理登录
- [x] 新增 `082` 检查报告、README、启动说明并更新总台账
- [x] 清理 `8082/18083/3082` 验证进程，删除 MySQL 临时库

## Errors Encountered
| Error | Attempt | Resolution |
|-------|---------|------------|
| 原始默认配置强依赖 MySQL `root/root`、Redis 和默认 `8080` | 读取 `application.yml` 与 `sql/init.sql` | 默认改为 H2 自举，正式 MySQL profile 改为 `root / 1234` |
| 原始 SQL 含 `DROP DATABASE IF EXISTS gongkao_db` | 核查 SQL 初始化脚本 | MySQL 验证走 `gongkao_082_verify` 临时库，避免误删正式库 |
| `082-backend/mvn test` 原始缺少有效业务测试 | 增加 SpringBootTest + TestRestTemplate 冒烟测试 | 当前 `mvn test` 通过，覆盖登录、权限、学习计划、课程和登出 |
| JDK 17 下 JJWT 0.9.1 缺 JAXB | 对照前序项目兼容修复 | 增加 `jaxb-api` 与 `jaxb-runtime` |
| PowerShell 读取 401/403 状态时旧 catch 逻辑不兼容当前响应对象 | 使用 `-SkipHttpErrorCheck` 重新抽测 | 精确得到 `401/403` 状态码 |
| 中文绝对路径在 JShell 交互输入中被转码 | 改用相对路径读取 SQL | MySQL 临时库成功导入并验证 |

# Task Plan: 083 项目巡检与修复

## Goal
完成 `083` 基于B/S的老年人体检管理系统的文档一致性、JDK 17 兼容性、默认启动、测试、MySQL `root / 1234` 真实验证、前端构建与代理联调；发现问题后直接修复并复测，同时同步总台账和单项目检查文档。

## Current Phase
Completed: `083` 巡检、修复、测试、MySQL 真实 HTTP 验证、前端代理验证与文档回填已完成；下一项目为 `084`。

## Phases
- [x] 确认 `083` 后端、前端、PRD、SQL、默认账号与原始配置风险
- [x] 修复默认 H2 自举、保留正式 MySQL profile，并新增 `mysql-verify` 验证 profile
- [x] 修复 JDK 17/JJWT、MySQL 驱动坐标、JWT Bearer、HTTP 状态、登出失效和密码脱敏
- [x] 补充管理员、医生、护士、前台权限边界
- [x] 修复体检结果 `elderId` / `doctorId` 伪造与预约 `createBy` 伪造
- [x] 修复前端端口、代理和 Bearer token
- [x] 新增后端冒烟测试并通过 `mvn test`
- [x] 完成前端 `npm run build`
- [x] 使用 MySQL `root / 1234` 的 `eldercare_083_verify` 临时库真实启动并抽测核心链路
- [x] 启动前端 `3083` 并验证代理登录
- [x] 新增 `083` 检查报告、README、启动说明并更新总台账
- [x] 清理 `18084/3083` 验证进程，删除 MySQL 临时库

## Errors Encountered
| Error | Attempt | Resolution |
|-------|---------|------------|
| 原始默认配置强依赖 MySQL `root/root`、Redis 和默认 `8080` | 读取 `application.yml` 与 `sql/init.sql` | 默认改为 H2 自举，正式 MySQL profile 改为 `root / 1234` |
| 原始 SQL 含 `DROP DATABASE IF EXISTS eldercare_db` | 核查 SQL 初始化脚本 | MySQL 验证走 `eldercare_083_verify` 临时库，避免误删正式库 |
| `083-backend/mvn test` 原始缺少有效业务测试 | 增加 SpringBootTest + TestRestTemplate 冒烟测试 | 当前 `mvn test` 通过，覆盖登录、权限、结果录入和登出 |
| 体检结果可由请求体伪造老人或医生归属 | 通读 `ResultService` 和控制器 | 按预约单绑定 `elderId`，按当前 JWT 绑定 `doctorId` |
| 预约可由请求体伪造创建人 | 通读 `AppointmentService` | 新增预约按当前 JWT 绑定 `createBy` |
| MySQL 临时库第一次复测统计接口命中过期进程 | 重启后端并重建临时库 | 干净复测统计接口返回 `200` |

# Task Plan: 037 项目巡检与修复

## Goal
完成 `037` 编程学习交流平台的文档一致性、JDK 17 兼容性、测试、启动与微信小程序前端配置巡检；发现问题后直接修复并复测，同时同步总台账和单项目检查文档。

## Current Phase
Completed: `037` 巡检、修复、测试、启动验证与文档回填已完成；下一项目为 `038`。

## Phases

### Phase 1: 建档与上下文同步
- [x] 读取续检记录，确认上一项目停在 `036`
- [x] 读取总台账，确认下一项目为 `037`
- [x] 确认 `037` 目录结构与资料范围
- [x] 确认是否已存在单项目检查文档
- **Status:** completed

### Phase 2: 基线核对
- [x] 读取后端关键配置、依赖、启动入口与数据库脚本
- [x] 读取小程序入口、页面声明与请求封装
- [x] 识别默认环境高风险点
- **Status:** completed

### Phase 3: 运行验证与根因定位
- [x] 执行后端测试或构建
- [x] 尝试真实启动后端并抽测核心接口
- [x] 核查小程序页面配置与资源引用
- [x] 如存在故障，定位根因
- **Status:** completed

### Phase 4: 问题修复与复测
- [x] 修复巡检中发现的问题
- [x] 补充必要测试或脚本
- [x] 完成后端复测
- [x] 完成小程序前端静态核查
- **Status:** completed

### Phase 5: 回填与清理
- [x] 新增或更新 `037` 单项目检查文档
- [x] 更新总台账
- [x] 更新 `findings.md` / `progress.md`
- [x] 清理本轮后台进程
- **Status:** completed

## Key Questions
1. `037` 的真实项目名和实现范围以 README、启动说明、PRD 还是源码为准？
2. `037` 是否能在 JDK 17 下通过测试并直接启动？
3. 默认数据库、Redis、微信登录配置是否会阻塞本机验证？
4. 小程序 `app.json` 声明页面与实际文件是否一致？

## Decisions Made
| Decision | Rationale |
|----------|-----------|
| 将 `037` 视为 Spring Boot 后端 + 微信小程序前端项目 | 目录结构为 `037-backend` + 原生小程序文件，而非 Vite/React/Vue Web 前端 |
| 小程序前端先做配置和页面文件完整性核查 | 当前没有 npm 构建链路，微信开发者工具依赖页面文件存在性 |
| 默认切换到 H2 自举，MySQL 用 profile 保留 | 保证本机可测试可启动，同时不丢失原部署数据库入口 |
| 使用 `mock_*` / `demo_*` 微信 code 支持本地演示 | 真实 appId/appSecret 未配置时，仍可完成毕业设计登录链路验证 |

## Errors Encountered
| Error | Attempt | Resolution |
|-------|---------|------------|
| `docs/checks` 中未找到 `037` 单项目检查文档 | 列出目录并按编号检索 | 记为本轮需新建文档，待完成巡检后回填 |
| `037-frontend/app.json` 声明 16 个页面但实际只有 `pages/login` | 对比小程序文件树与页面配置 | 已补齐声明页面的 `.js/.wxml/.wxss` 最小骨架，避免微信开发者工具编译失败 |
| 默认后端强依赖 MySQL / Redis，且真实微信配置不可用 | 执行配置核查和后端测试 | 默认改为 H2 自举，保留 MySQL profile，并增加微信模拟 code |
| `/api` context-path 下 Security 放行路径误写为 `/api/auth/**` | 真实启动与 HTTP 抽测验证 | 改为应用内路径 `/auth/**`、`/courses/**` 等，真实 `/api/auth/wxlogin` 抽测通过 |

# Task Plan: 071 项目巡检与修复

## Goal
完成 `071` 共享单车系统的文档一致性、JDK 17 兼容性、测试、启动、前后端代理与核心业务链路巡检；发现问题后直接修复并复测，同时同步总台账和单项目检查文档。

## Current Phase
Completed: `071` 巡检、修复、测试、启动验证、前端代理验证与文档回填已完成；下一项目为 `072`。

## Phases

### Phase 1: 建档与上下文同步
- [x] 读取续检记录，确认总台账已完成到 `070`
- [x] 确认下一项目为 `071`
- [x] 确认 `071` 目录结构与资料范围
- [x] 确认尚无 `071` 单项目检查文档
- **Status:** completed

### Phase 2: 基线核对
- [x] 读取后端关键配置、依赖、启动入口与数据库脚本
- [x] 读取前端关键配置、请求封装、代理与构建脚本
- [x] 识别默认环境高风险点
- **Status:** completed

### Phase 3: 运行验证与根因定位
- [x] 执行后端测试，确认原始状态没有自动化测试
- [x] 执行前端构建
- [x] 尝试真实启动后端并抽测核心接口
- [x] 定位 H2 趋势统计 SQL 兼容问题
- **Status:** completed

### Phase 4: 问题修复与复测
- [x] 修复巡检中发现的问题
- [x] 补充后端冒烟测试
- [x] 完成后端复测
- [x] 完成前端构建与代理联调验证
- **Status:** completed

### Phase 5: 回填与清理
- [x] 新增 `071` 单项目检查文档
- [x] 新增后端 README 与启动说明
- [x] 更新总台账
- [x] 更新 `findings.md` / `progress.md`
- [x] 清理本轮后台进程
- **Status:** completed

## Key Questions
1. `071` 是否能在 JDK 17 下通过测试并直接启动？
2. 默认 MySQL / Redis 依赖是否会阻塞本机验证？
3. JWT、用户角色和骑行订单归属是否能正确防越权？
4. 前端代理是否能连到默认后端端口？

## Decisions Made
| Decision | Rationale |
|----------|-----------|
| 将 `071` 视为 Spring Boot 后端 + Vue 前端项目 | 目录结构为 `071-backend` + `071-frontend`，且前端有 Vite 构建链路 |
| 默认切换到 H2 自举，MySQL 用 profile 保留 | 保证本机可测试可启动，同时不丢失原部署数据库入口 |
| Redis 改为默认本地运行态缓存兜底 | Redis 不应阻塞毕业设计默认演示和自动化测试 |
| 骑行订单详情增加角色与归属校验 | 避免普通用户枚举订单 ID 查看他人骑行记录 |

## Errors Encountered
| Error | Attempt | Resolution |
|-------|---------|------------|
| `071-backend/mvn test` 原始结果为 `No tests to run` | 增加 SpringBootTest + TestRestTemplate 冒烟测试 | 当前 `mvn test` 通过，覆盖骑行/钱包/故障/权限/登出 |
| `071` 统计趋势接口在 H2 下因 `DATE_SUB` 返回 500 | 真实测试访问 `/api/statistics/ride-trend` | 调整趋势 SQL，移除 H2 不支持的 MySQL 专有函数 |
| 前端代理仍指向 `8080` | 读取 `vite.config.js` 并启动前端代理验证 | 改为 `8071` 并支持 `VITE_API_TARGET` |
| 前端请求头缺少 `Bearer` | 读取 `src/api/request.js` | 请求拦截器改为 `Authorization: Bearer <token>` |

## Notes
- 当前已确认：
  - `071` 后端项目名为“基于SpringBoot和Vue的共享单车系统”
  - 后端默认配置已改为 H2 自举，MySQL 配置保留在 `application-mysql.yml`
  - 前端默认端口为 `3071`，代理到后端 `8071`
  - 后端 `mvn test` 通过，`Tests run: 1, Failures: 0, Errors: 0`
  - 前端 `npm run build` 通过
  - 后端 `8071` 真实启动并完成登录、看板、站点、骑行、还车、登出抽测
  - 前端 `3071` 代理登录验证通过
  - `docs/checks/071-bike-sharing-system.md` 已创建并回填验证结论

## Notes
- 当前已确认：
  - `037` 后端项目名为“基于SpringBoot的编程学习交流平台”
  - 后端技术栈为 Spring Boot 3.2.0 + MyBatis XML + Spring Security + JWT + H2/MySQL
  - 后端默认配置已改为 H2 自举，MySQL 配置保留在 `application-mysql.yml`
  - 前端为微信小程序原生项目，`app.json` 声明 16 个页面
  - 小程序目录已补齐 `app.json` 声明的所有页面文件
  - 后端 `mvn test` 通过，`Tests run: 3, Failures: 0, Errors: 0`
  - 后端 `8037` 真实启动并完成模拟微信登录、当前用户、课程公开接口抽测
  - `docs/checks/037-programming-learning-platform.md` 已创建并回填验证结论

## Session: 2026-05-01 项目巡检 072

### Phases

### Phase 1: 建档与上下文同步
- [x] 读取续检记录，确认总台账已完成到 `071`
- [x] 确认下一项目为 `072`
- [x] 确认 `072` 目录结构与资料范围
- [x] 确认尚无 `072` 单项目检查文档
- **Status:** completed

### Phase 2: 基线核对
- [x] 读取后端关键配置、依赖、启动入口与数据库脚本
- [x] 读取后端鉴权、异常处理、用户、票务、游记、评价、活动和后台接口
- [x] 读取前端关键配置、路由、请求封装、API 导出和核心页面引用
- [x] 识别默认环境、权限边界和前端构建高风险点
- **Status:** completed

### Phase 3: 问题修复与复测
- [x] 修复后端默认 H2 自举、JDK 17、MySQL profile 和分页方言
- [x] 修复 JWT Bearer、密码脱敏、HTTP `401/403` 和登出失效
- [x] 补充后台服务端权限、订单/游记/评价归属校验和关键输入校验
- [x] 修复前端端口、代理、路由导入、API 兼容导出和购票参数
- [x] 新增后端冒烟测试
- [x] 完成后端 `mvn test`
- [x] 完成前端 `npm run build`
- [x] 完成后端 `8072` 真实 HTTP 抽测
- [x] 完成前端 `3072` 代理登录验证
- **Status:** completed

### Phase 4: 回填与清理
- [x] 新增 `072` 单项目检查文档
- [x] 新增后端 README 与启动说明
- [x] 更新总台账
- [x] 更新 `findings.md` / `progress.md`
- [x] 清理本轮后台进程
- **Status:** completed

### Key Questions
1. `072` 是否能在 JDK 17 下通过测试并直接启动？
2. 默认 MySQL / Redis 依赖是否会阻塞本机验证？
3. JWT、用户角色、订单/游记/评价归属是否能正确防越权？
4. 前端路由、API 导出和代理是否能完成构建与联调？

### Decisions Made
| Decision | Rationale |
|----------|-----------|
| 将 `072` 视为 Spring Boot 后端 + Vue 前端项目 | 目录结构为 `072-backend` + `072-frontend`，且前端有 Vite 构建链路 |
| 默认切换到 H2 自举，MySQL 用 profile 保留 | 保证本机可测试可启动，同时不丢失原部署数据库入口 |
| 后台接口全部加服务端管理员校验 | 前端菜单隐藏不能替代授权 |
| 前端 API 层补兼容导出 | 低风险修复页面与接口命名不一致导致的构建/运行问题 |

### Errors Encountered
| Error | Attempt | Resolution |
|-------|---------|------------|
| `072-backend/mvn test` 原始结果为 `No tests to run` | 增加 SpringBootTest + TestRestTemplate 冒烟测试 | 当前 `mvn test` 通过，覆盖文旅/票务/权限/登出 |
| 前端原始 `npm run build` 因未安装依赖无法执行 | 执行 `npm install` | 已生成 `package-lock.json`，构建通过 |
| 前端路由引用不存在目录 | 读取 `src/views` 实际文件 | 路由改为实际存在的扁平页面 |
| 前端页面引用不存在 API 导出 | 逐项搜索视图 import | 在 API 层补兼容导出并映射真实接口 |
| PowerShell 首次读取非 2xx HTTP 状态脚本不兼容 | 重写为 `SkipHttpErrorCheck` | 固定端口 HTTP 401/403 证据重跑通过 |

### Notes
- 当前已确认：
  - `072` 后端项目名为“基于SpringBoot和Vue的哈尔滨文旅系统”
  - 后端默认配置已改为 H2 自举，MySQL 配置保留在 `application-mysql.yml`
  - 前端默认端口为 `3072`，代理到后端 `8072`
  - 后端 `mvn test` 通过，`Tests run: 1, Failures: 0, Errors: 0`
  - 前端 `npm run build` 通过
  - 后端 `8072` 真实启动并完成登录、公开列表、后台权限、购票、退款、登出抽测
  - 前端 `3072` 代理登录验证通过
  - `docs/checks/072-harbin-tourism-system.md` 已创建并回填验证结论
  - 下一项目为 `073`

## Session: 2026-05-01 项目巡检 073

### Phases

### Phase 1: 建档与上下文同步
- [x] 读取 `073` PRD、配置、控制器和前端工程信息
- [x] 确认 `073` 目录结构与资料范围
- [x] 确认尚无 `073` 单项目检查文档
- **Status:** completed

### Phase 2: 基线核对
- [x] 读取后端关键配置、依赖、启动入口与数据库脚本
- [x] 读取后端鉴权、异常处理、用户、员工、考勤、请假、薪资和合同接口
- [x] 读取前端关键配置、请求封装、代理与构建脚本
- [x] 识别默认环境、权限边界和前端构建高风险点
- **Status:** completed

### Phase 3: 问题修复与复测
- [x] 修复后端默认 H2 自举、JDK 17、MySQL profile 和分页方言
- [x] 修复 JWT 生成调用、Bearer、登录放行路径、密码脱敏、HTTP `401/403` 和登出失效
- [x] 补充管理型接口服务端 admin/hr 权限
- [x] 补充考勤、请假、薪资、合同本人归属校验
- [x] 修复前端端口、代理和 Bearer token
- [x] 新增后端冒烟测试
- [x] 完成后端 `mvn test`
- [x] 完成前端 `npm run build`
- [x] 完成后端 `8073` 真实 HTTP 抽测
- [x] 完成前端 `3073` 页面响应和代理登录验证
- **Status:** completed

### Phase 4: 回填与清理
- [x] 新增 `073` 单项目检查文档
- [x] 新增后端 README 与启动说明
- [x] 更新总台账
- [x] 更新 `findings.md` / `progress.md`
- [x] 清理本轮后台进程
- **Status:** completed

### Key Questions
1. `073` 是否能在 JDK 17 下通过测试并直接启动？
2. 默认 MySQL / Redis 依赖是否会阻塞本机验证？
3. JWT、用户角色、考勤/请假/薪资/合同归属是否能正确防越权？
4. 前端代理是否能连到默认后端端口？

### Decisions Made
| Decision | Rationale |
|----------|-----------|
| 将 `073` 视为 Spring Boot 后端 + Vue 前端项目 | 目录结构为 `073-backend` + `073-frontend`，且前端有 Vite 构建链路 |
| 默认切换到 H2 自举，MySQL 用 profile 保留 | 保证本机可测试可启动，同时不丢失原部署数据库入口 |
| 管理型接口统一加 admin/hr 服务端校验 | 前端菜单隐藏不能替代授权 |
| 员工数据按本人 `employeeId` 约束 | 避免员工枚举 ID 访问或修改他人数据 |

### Errors Encountered
| Error | Attempt | Resolution |
|-------|---------|------------|
| `073-backend/mvn test` 原始编译失败 | 读取错误上下文，定位到静态调用 `JwtUtils.generateToken` | 改为注入 `JwtUtils` 实例并补冒烟测试 |
| 默认后端强依赖 MySQL/Redis，且 SQL 含 `DROP DATABASE` | 读取 `application.yml` 与 `sql/init.sql` | 默认改为 H2 自举，MySQL profile 保留 |
| 登录放行路径与真实接口不一致 | 搜索控制器映射和前端 API | 改为排除 `/api/user/login` |
| 普通员工可访问管理型接口或他人数据 | 通读同类控制器并补权限 | 增加 admin/hr 校验与本人归属校验 |
| PowerShell 非 2xx 响应读取脚本与当前版本对象模型不兼容 | 修正为兼容 `HttpResponseMessage` 的读取方式 | 干净重跑真实 HTTP 401/403 验证通过 |
| 浏览器 DevTools MCP 被已有 Chrome profile 锁定 | 尝试新开和枚举页面均失败 | 改用真实 HTTP 页面响应、生产构建和代理登录作为前端验收证据 |

### Notes
- 当前已确认：
  - `073` 后端项目名为“基于SpringBoot和Vue的人事管理系统”
  - 后端默认配置已改为 H2 自举，MySQL 配置保留在 `application-mysql.yml`
  - 前端默认端口为 `3073`，代理到后端 `8073`
  - 后端 `mvn test` 通过，`Tests run: 1, Failures: 0, Errors: 0`
  - 前端 `npm run build` 通过
  - 后端 `8073` 真实启动并完成登录、HR 权限、员工越权、考勤、请假归属、合同越权、登出失效抽测
  - 前端 `3073` 页面响应和代理登录验证通过
  - `docs/checks/073-hrm-system.md` 已创建并回填验证结论
  - 下一项目为 `074`

## Session: 2026-05-02 项目巡检 074

### Phases

### Phase 1: 建档与上下文同步
- [x] 读取 `074` PRD、配置、控制器、服务和前端工程信息
- [x] 确认 `074` 目录结构与资料范围
- [x] 确认尚无 `074` 单项目检查文档
- **Status:** completed

### Phase 2: 基线核对
- [x] 读取后端关键配置、依赖、启动入口与数据库脚本
- [x] 读取后端鉴权、异常处理、用户、分类、商品、订单、评价、收藏、申诉、公告和看板接口
- [x] 读取前端关键配置、请求封装、代理与构建脚本
- [x] 识别默认环境、权限边界和前端构建高风险点
- **Status:** completed

### Phase 3: 问题修复与复测
- [x] 修复后端默认 H2 自举、JDK 17、MySQL profile 和分页方言
- [x] 修复 Bearer、密码脱敏、HTTP `401/403` 和登出失效
- [x] 补充管理型接口服务端管理员权限
- [x] 补充商品、订单、评价和申诉本人归属校验
- [x] 修复前端端口、代理和 Bearer token
- [x] 新增后端冒烟测试
- [x] 完成后端 `mvn test`
- [x] 完成前端 `npm install`
- [x] 完成前端 `npm run build`
- [x] 完成后端 `8074` 真实 HTTP 抽测
- [x] 完成前端 `3074` 页面响应和代理登录验证
- **Status:** completed

### Phase 4: 回填与清理
- [x] 新增 `074` 单项目检查文档
- [x] 新增后端 README 与启动说明
- [x] 更新总台账
- [x] 更新 `findings.md` / `progress.md`
- [x] 清理本轮后台进程
- **Status:** completed

### Key Questions
1. `074` 是否能在 JDK 17 下通过测试并直接启动？
2. 默认 MySQL / Redis 依赖是否会阻塞本机验证？
3. JWT、用户角色、商品/订单/评价/申诉归属是否能正确防越权？
4. 前端代理是否能连到默认后端端口？

### Decisions Made
| Decision | Rationale |
|----------|-----------|
| 将 `074` 视为 Spring Boot 后端 + Vue 前端项目 | 目录结构为 `074-backend` + `074-frontend`，且前端有 Vite 构建链路 |
| 默认切换到 H2 自举，MySQL 用 profile 保留 | 保证本机可测试可启动，同时不丢失原部署数据库入口 |
| 管理型接口统一加管理员服务端校验 | 前端菜单隐藏不能替代授权 |
| 交易数据按买家/卖家归属约束 | 避免用户枚举 ID 操作他人商品、订单、评价或申诉 |

### Errors Encountered
| Error | Attempt | Resolution |
|-------|---------|------------|
| `074-backend/mvn test` 原始结果为 `No tests to run` | 增加 SpringBootTest + TestRestTemplate 冒烟测试 | 当前 `mvn test` 通过，覆盖交易/评价/申诉/权限/登出 |
| 默认后端强依赖 MySQL/Redis，且 SQL 含 `DROP DATABASE` | 读取 `application.yml` 与 `sql/init.sql` | 默认改为 H2 自举，MySQL profile 保留 |
| 登录响应仍包含 `password: null` 字段 | 复跑测试定位到 Jackson 序列化字段未隐藏 | `User.password` 改为 `WRITE_ONLY` 后复测通过 |
| 普通用户可访问管理型接口或他人交易数据 | 通读同类控制器和服务并补权限 | 增加管理员校验与买家/卖家归属校验 |
| PowerShell 前端验证脚本误用只读 `$HOME` 变量 | 读取报错并改变量名 | 重跑页面响应和代理登录验证通过 |

### Notes
- 当前已确认：
  - `074` 后端项目名为“手工艺品销售系统”
  - 后端默认配置已改为 H2 自举，MySQL 配置保留在 `application-mysql.yml`
  - 前端默认端口为 `3074`，代理到后端 `8074`
  - 后端 `mvn test` 通过，`Tests run: 1, Failures: 0, Errors: 0`
  - 前端 `npm run build` 通过
  - 后端 `8074` 真实启动并完成登录、权限、商品分类公告、下单支付、发货归属、评价收藏申诉、登出失效抽测
  - 前端 `3074` 页面响应和代理登录验证通过
  - `docs/checks/074-craft-sales-system.md` 已创建并回填验证结论
  - 下一项目为 `075`

## Session: 2026-05-02 项目巡检 075

### Phases

### Phase 1: 建档与上下文同步
- [x] 读取 `075` PRD、配置、控制器、服务和前端工程信息
- [x] 确认 `075` 目录结构与资料范围
- [x] 确认尚无 `075` 单项目检查文档
- **Status:** completed

### Phase 2: 基线核对
- [x] 读取后端关键配置、依赖、启动入口与数据库脚本
- [x] 读取后端鉴权、异常处理、用户、分类、维修项目、预约单、评价、收藏、申诉、公告和看板接口
- [x] 读取前端关键配置、请求封装、代理与构建脚本
- [x] 识别默认环境、权限边界和前端构建高风险点
- **Status:** completed

### Phase 3: 问题修复与复测
- [x] 修复后端默认 H2 自举、JDK 17、MySQL profile 和分页方言
- [x] 修复 Bearer、密码脱敏、HTTP `401/403` 和登出失效
- [x] 补充管理型接口服务端管理员权限
- [x] 补充维修项目、预约单、评价和申诉本人归属校验
- [x] 修复前端端口、代理和 Bearer token
- [x] 新增后端冒烟测试
- [x] 完成后端 `mvn test`
- [x] 完成前端 `npm install`
- [x] 完成前端 `npm run build`
- [x] 完成后端 `8075` 真实 HTTP 抽测
- [x] 完成前端 `3075` 页面响应和代理登录验证
- **Status:** completed

### Phase 4: 回填与清理
- [x] 新增 `075` 单项目检查文档
- [x] 新增后端 README 与启动说明
- [x] 更新总台账
- [x] 更新 `findings.md` / `progress.md`
- [x] 清理本轮后台进程
- **Status:** completed

### Key Questions
1. `075` 是否能在 JDK 17 下通过测试并直接启动？
2. 默认 MySQL / Redis 依赖是否会阻塞本机验证？
3. JWT、用户角色、维修项目/预约/评价/申诉归属是否能正确防越权？
4. 前端代理是否能连到默认后端端口？

### Decisions Made
| Decision | Rationale |
|----------|-----------|
| 将 `075` 视为 Spring Boot 后端 + Vue 前端项目 | 目录结构为 `075-backend` + `075-frontend`，且前端有 Vite 构建链路 |
| 默认切换到 H2 自举，MySQL 用 profile 保留 | 保证本机可测试可启动，同时不丢失原部署数据库入口 |
| 管理型接口统一加管理员服务端校验 | 前端菜单隐藏不能替代授权 |
| 预约数据按车主/服务方归属约束 | 避免用户枚举 ID 操作他人的维修项目、预约、评价或申诉 |

### Errors Encountered
| Error | Attempt | Resolution |
|-------|---------|------------|
| `075-backend/mvn test` 原始结果为 `No tests to run` | 增加 SpringBootTest + TestRestTemplate 冒烟测试 | 当前 `mvn test` 通过，覆盖预约/评价/申诉/权限/登出 |
| 默认后端强依赖 MySQL/Redis，且 SQL 含 `DROP DATABASE` | 读取 `application.yml` 与 `sql/init.sql` | 默认改为 H2 自举，MySQL profile 保留 |
| 登录响应会包含密码字段 | 对照 `074` 已验证修复方式 | `User.password` 改为 `WRITE_ONLY` 后复测通过 |
| 普通用户可访问管理型接口或他人预约数据 | 通读同类控制器和服务并补权限 | 增加管理员校验与车主/服务方归属校验 |
| 前端原始 `npm run build` 因未安装依赖失败 | 执行 `npm install` | 已生成 `package-lock.json`，构建通过 |

### Notes
- 当前已确认：
  - `075` 后端项目名为“汽车维修预约服务系统”
  - 后端默认配置已改为 H2 自举，MySQL 配置保留在 `application-mysql.yml`
  - 前端默认端口为 `3075`，代理到后端 `8075`
  - 后端 `mvn test` 通过，`Tests run: 1, Failures: 0, Errors: 0`
  - 前端 `npm run build` 通过
  - 后端 `8075` 真实启动并完成登录、权限、维修项目分类公告、预约支付、服务方确认归属、评价收藏申诉、登出失效抽测
  - 前端 `3075` 页面响应和代理登录验证通过
  - `docs/checks/075-auto-repair-appointment-system.md` 已创建并回填验证结论
  - 下一项目为 `076`

## Session: 2026-05-02 项目巡检 076

### Phases

### Phase 1: 建档与上下文同步
- [x] 接续 `076` 已有修复现场
- [x] 确认 `076` 目录结构与资料范围
- [x] 确认尚无 `076` 单项目检查文档
- **Status:** completed

### Phase 2: 基线核对
- [x] 读取后端关键配置、依赖、启动入口与数据库脚本
- [x] 读取后端鉴权、异常处理、用户、分类、企业信息、处理单、评价、收藏、申诉、公告和看板接口
- [x] 读取前端关键配置、请求封装、代理与构建脚本
- [x] 识别默认环境、权限边界和前端构建高风险点
- **Status:** completed

### Phase 3: 问题修复与复测
- [x] 修复后端默认 H2 自举、JDK 17、MySQL profile 和分页方言
- [x] 修复 Bearer、密码脱敏、HTTP `401/403` 和登出失效
- [x] 补充管理型接口服务端管理员权限
- [x] 补充企业信息、处理单、评价和申诉本人归属校验
- [x] 修复前端端口、代理和 Bearer token
- [x] 新增后端冒烟测试
- [x] 完成后端 `mvn test`
- [x] 完成前端 `npm install`
- [x] 完成前端 `npm run build`
- [x] 完成后端 `8076` 真实 HTTP 抽测
- [x] 完成前端 `3076` 页面响应和代理登录验证
- **Status:** completed

### Phase 4: 回填与清理
- [x] 新增 `076` 单项目检查文档
- [x] 新增后端 README 与启动说明
- [x] 更新总台账
- [x] 更新 `findings.md` / `progress.md`
- [x] 清理本轮后台进程
- **Status:** completed

### Key Questions
1. `076` 是否能在 JDK 17 下通过测试并直接启动？
2. 默认 MySQL / Redis 依赖是否会阻塞本机验证？
3. JWT、用户角色、企业信息/处理单/评价/申诉归属是否能正确防越权？
4. 前端代理是否能连到默认后端端口？

### Decisions Made
| Decision | Rationale |
|----------|-----------|
| 将 `076` 视为 Spring Boot 后端 + Vue 前端项目 | 目录结构为 `076-backend` + `076-frontend`，且前端有 Vite 构建链路 |
| 默认切换到 H2 自举，MySQL 用 profile 保留 | 保证本机可测试可启动，同时不丢失原部署数据库入口 |
| 管理型接口统一加管理员服务端校验 | 前端菜单隐藏不能替代授权 |
| 企业信息处理数据按企业用户/服务方归属约束 | 避免用户枚举 ID 操作他人的企业信息、处理单、评价或申诉 |

### Errors Encountered
| Error | Attempt | Resolution |
|-------|---------|------------|
| `076-backend/mvn test` 原始结果为 `No tests to run` | 增加 SpringBootTest + TestRestTemplate 冒烟测试 | 当前 `mvn test` 通过，覆盖处理单/评价/申诉/权限/登出 |
| 默认后端强依赖 MySQL/Redis，且 SQL 含 `DROP DATABASE` | 读取 `application.yml` 与 `sql/init.sql` | 默认改为 H2 自举，MySQL profile 保留 |
| 登录响应会包含密码字段 | 对照已验证修复方式 | `User.password` 改为 `WRITE_ONLY` 后复测通过 |
| 普通用户可访问管理型接口或他人处理数据 | 通读同类控制器和服务并补权限 | 增加管理员校验与企业用户/服务方归属校验 |
| 前端原始 `npm run build` 因未安装依赖失败 | 执行 `npm install` | 已生成 `package-lock.json`，构建通过 |
| PowerShell 前端验证脚本误用只读 `$HOME` 变量 | 读取完整错误并更换变量名 | 重跑前端页面响应和代理登录验证通过 |

### Notes
- 当前已确认：
  - `076` 后端项目名为“企业信息管理系统”
  - 后端默认配置已改为 H2 自举，MySQL 配置保留在 `application-mysql.yml`
  - 前端默认端口为 `3076`，代理到后端 `8076`
  - 后端 `mvn test` 通过，`Tests run: 1, Failures: 0, Errors: 0`
  - 前端 `npm run build` 通过
  - 后端 `8076` 真实启动并完成登录、权限、企业信息分类公告、处理单支付、服务方确认归属、评价收藏申诉、登出失效抽测
  - 前端 `3076` 页面响应和代理登录验证通过
  - `docs/checks/076-enterprise-info-management-system.md` 已创建并回填验证结论
  - 下一项目为 `077`

## Session: 2026-05-02 项目巡检 077

### Phases

### Phase 1: 建档与上下文同步
- [x] 读取 `077` PRD、配置、控制器、服务和前端工程信息
- [x] 确认 `077` 目录结构与资料范围
- [x] 确认尚无 `077` 单项目检查文档
- **Status:** completed

### Phase 2: 基线核对
- [x] 读取后端关键配置、依赖、启动入口与数据库脚本
- [x] 读取后端鉴权、异常处理、用户、分类、生产任务、执行工单、评价、收藏、申诉、公告和看板接口
- [x] 读取前端关键配置、请求封装、代理与构建脚本
- [x] 识别默认环境、权限边界和前端构建高风险点
- **Status:** completed

### Phase 3: 问题修复与复测
- [x] 修复后端默认 H2 自举、JDK 17、MySQL profile 和分页方言
- [x] 修复 Bearer、密码脱敏、HTTP `401/403` 和登出失效
- [x] 补充管理型接口服务端管理员权限
- [x] 补充生产任务、执行工单、评价和申诉本人归属校验
- [x] 修复前端端口、代理和 Bearer token
- [x] 新增后端冒烟测试
- [x] 完成后端 `mvn test`
- [x] 完成前端 `npm install`
- [x] 完成前端 `npm run build`
- [x] 完成后端 `8077` 真实 HTTP 抽测
- [x] 完成前端 `3077` 页面响应和代理登录验证
- **Status:** completed

### Phase 4: 回填与清理
- [x] 新增 `077` 单项目检查文档
- [x] 新增后端 README 与启动说明
- [x] 更新总台账
- [x] 更新 `findings.md` / `progress.md`
- [x] 清理本轮后台进程
- **Status:** completed

### Key Questions
1. `077` 是否能在 JDK 17 下通过测试并直接启动？
2. 默认 MySQL / Redis 依赖是否会阻塞本机验证？
3. JWT、用户角色、生产任务/执行工单/评价/申诉归属是否能正确防越权？
4. 前端代理是否能连到默认后端端口？

### Decisions Made
| Decision | Rationale |
|----------|-----------|
| 将 `077` 视为 Spring Boot 后端 + Vue 前端项目 | 目录结构为 `077-backend` + `077-frontend`，且前端有 Vite 构建链路 |
| 默认切换到 H2 自举，MySQL 用 profile 保留 | 保证本机可测试可启动，同时不丢失原部署数据库入口 |
| 管理型接口统一加管理员服务端校验 | 前端菜单隐藏不能替代授权 |
| MES 工单数据按计划员/生产主管归属约束 | 避免用户枚举 ID 操作他人的生产任务、执行工单、评价或申诉 |

### Errors Encountered
| Error | Attempt | Resolution |
|-------|---------|------------|
| `077-backend/mvn test` 原始结果为 `No tests to run` | 增加 SpringBootTest + TestRestTemplate 冒烟测试 | 当前 `mvn test` 通过，覆盖工单/评价/申诉/权限/登出 |
| 默认后端强依赖 MySQL/Redis，且 SQL 含 `DROP DATABASE` | 读取 `application.yml` 与 `sql/init.sql` | 默认改为 H2 自举，MySQL profile 保留 |
| 登录响应会包含密码字段 | 对照已验证修复方式 | `User.password` 改为 `WRITE_ONLY` 后复测通过 |
| 普通用户可访问管理型接口或他人工单数据 | 通读同类控制器和服务并补权限 | 增加管理员校验与计划员/生产主管归属校验 |
| 前端原始 `npm run build` 因未安装依赖失败 | 执行 `npm install` | 已生成 `package-lock.json`，构建通过 |
| 权限异常原本只返回业务码，HTTP 仍为 200/400 | 改造全局异常处理与 `BusinessException(403, ...)` | 自动化测试和真实 HTTP 均验证越权返回 `403` |

### Notes
- 当前已确认：
  - `077` 后端项目名为“基于Vue的MES生产制造执行系统”
  - 后端默认配置已改为 H2 自举，MySQL 配置保留在 `application-mysql.yml`
  - 前端默认端口为 `3077`，代理到后端 `8077`
  - 后端 `mvn test` 通过，`Tests run: 1, Failures: 0, Errors: 0`
  - 前端 `npm run build` 通过
  - 后端 `8077` 真实启动并完成登录、权限、生产任务分类公告、执行工单支付、生产主管确认归属、评价收藏申诉、登出失效抽测
  - 前端 `3077` 页面响应和代理登录验证通过
  - `docs/checks/077-mes-execution-system.md` 已创建并回填验证结论
  - 下一项目为 `078`

## Session: 2026-05-02 项目巡检 078

### Phases

### Phase 1: 建档与上下文同步
- [x] 读取 `078` PRD、配置、控制器、服务和前端工程信息
- [x] 确认 `078` 目录结构与资料范围
- [x] 确认尚无 `078` 单项目检查文档
- **Status:** completed

### Phase 2: 基线核对
- [x] 读取后端关键配置、依赖、启动入口与数据库脚本
- [x] 读取后端鉴权、异常处理、用户、商家、分类、商品、团购活动、拼团、购物车、地址、订单、评价、公告和统计接口
- [x] 读取前端关键配置、请求封装、代理与构建脚本
- [x] 识别默认环境、权限边界和前端构建高风险点
- **Status:** completed

### Phase 3: 问题修复与复测
- [x] 修复后端默认 H2 自举、JDK 17、MySQL profile 和分页方言
- [x] 修复 Bearer、密码脱敏、HTTP `401/403` 和登出失效
- [x] 补充管理型接口服务端管理员权限
- [x] 补充商品、团购活动、地址、购物车、订单和拼团订单本人/商家归属校验
- [x] 修复前端端口、代理和 Bearer token
- [x] 修复前端 API 导出断链与首页公开团购调用
- [x] 新增后端冒烟测试
- [x] 完成后端 `mvn test`
- [x] 完成前端 `npm install`
- [x] 完成前端 `npm run build`
- [x] 完成后端 `8078` 真实 HTTP 抽测
- [x] 完成前端 `3078` 页面响应和代理登录验证
- **Status:** completed

### Phase 4: 回填与清理
- [x] 新增 `078` 单项目检查文档
- [x] 新增后端 README 与启动说明
- [x] 更新总台账
- [x] 更新 `findings.md` / `progress.md`
- [x] 清理本轮后台进程
- **Status:** completed

### Key Questions
1. `078` 是否能在 JDK 17 下通过测试并直接启动？
2. 默认 MySQL / Redis 依赖是否会阻塞本机验证？
3. JWT、用户角色、商家/普通用户交易归属是否能正确防越权？
4. 前端代理和首页公开数据是否能连到默认后端端口？

### Decisions Made
| Decision | Rationale |
|----------|-----------|
| 将 `078` 视为 Spring Boot 后端 + Vue 前端项目 | 目录结构为 `078-backend` + `078-frontend`，且前端有 Vite 构建链路 |
| 默认切换到 H2 自举，MySQL 用 profile 保留 | 保证本机可测试可启动，同时不丢失原部署数据库入口 |
| 管理型接口统一加管理员服务端校验 | 前端菜单隐藏不能替代授权 |
| 团购交易数据按普通用户/商家归属约束 | 避免用户枚举 ID 操作他人的地址、购物车、订单、商品或活动 |

### Errors Encountered
| Error | Attempt | Resolution |
|-------|---------|------------|
| `078-backend/mvn test` 原始结果为 `No tests to run` | 增加 SpringBootTest + TestRestTemplate 冒烟测试 | 当前 `mvn test` 通过，覆盖购物车/订单/评价/权限/登出 |
| 默认后端强依赖 MySQL/Redis，且 SQL 含 `DROP DATABASE` | 读取 `application.yml` 与 `sql/init.sql` | 默认改为 H2 自举，MySQL profile 保留 |
| 登录响应会包含密码字段 | 对照已验证修复方式 | `User.password` 改为 `WRITE_ONLY` 后复测通过 |
| 普通用户可访问管理型接口或他人交易数据 | 通读同类控制器和服务并补权限 | 增加管理员校验与普通用户/商家归属校验 |
| 前端原始 `npm run build` 因未安装依赖失败 | 执行 `npm install` | 已生成 `package-lock.json` |
| 前端构建连续暴露 `deleteReview`、`updateNoticeStatus`、`getNoticeList` 缺失 | 读取对应页面和 API 层 | 补齐接口与导出，最终构建通过 |
| 首页调用受保护的活动分页接口 | 读取首页源码 | 改为调用公开首页团购接口 |

### Notes
- 当前已确认：
  - `078` 后端项目名为“网上团购系统”
  - 后端默认配置已改为 H2 自举，MySQL 配置保留在 `application-mysql.yml`
  - 前端默认端口为 `3078`，代理到后端 `8078`
  - 后端 `mvn test` 通过，`Tests run: 1, Failures: 0, Errors: 0`
  - 前端 `npm run build` 通过
  - 后端 `8078` 真实启动并完成登录、权限、公开分类/团购/商品/公告、购物车下单、订单支付/发货/收货归属、评价和登出失效抽测
  - 前端 `3078` 页面响应和代理登录验证通过
  - `docs/checks/078-groupbuy-system.md` 已创建并回填验证结论
  - 下一项目为 `079`

# Task Plan: 084 项目巡检与修复

## Goal
完成 `084` 教学资料管理系统的文档一致性、JDK 17 兼容性、默认启动、测试、MySQL `root / 1234` 真实验证、前端构建与代理联调；发现问题后直接修复并复测，同时同步总台账和单项目检查文档。

## Current Phase
Completed: `084` 巡检、修复、测试、MySQL 真实 HTTP 验证、前端代理验证与文档回填已完成；下一项目为 `085`。

## Phases
- [x] 确认 `084` 后端、前端、PRD、SQL、默认账号与原始配置风险
- [x] 修复默认 H2 自举、保留正式 MySQL profile，并新增 `mysql-verify` 验证 profile
- [x] 修复 JDK 17/JJWT、MySQL 驱动坐标、JWT Bearer、HTTP 状态、登出失效和密码脱敏
- [x] 补充管理员、教师、学生权限边界，修复统计、审核、分类、公告和资料管理端权限
- [x] 修复教师资料 `uploaderId` 防伪造、列表本人约束、资料更新/删除/发布归属和收藏学习清单归属
- [x] 修复统计 SQL 中 H2 保留字别名冲突
- [x] 修复前端端口、代理和 Bearer token
- [x] 新增后端冒烟测试并通过 `mvn test`
- [x] 完成前端 `npm run build`
- [x] 使用 MySQL `root / 1234` 的 `teachres_084_verify` 临时库真实启动并抽测核心链路
- [x] 启动前端 `3084` 并验证代理登录
- [x] 新增 `084` 检查报告、README、启动说明并更新总台账
- [x] 清理 `18085/3084` 验证进程，删除 MySQL 临时库

## Errors Encountered
| Error | Attempt | Resolution |
|-------|---------|------------|
| 原始默认配置强依赖 MySQL `root/root`、Redis 和默认 `8080` | 读取 `application.yml` 与 `sql/init.sql` | 默认改为 H2 自举，正式 MySQL profile 改为 `root / 1234` |
| 原始 SQL 含 `DROP DATABASE IF EXISTS teachres_db` | 核查 SQL 初始化脚本 | MySQL 验证走 `teachres_084_verify` 临时库，避免误删正式库 |
| `084-backend/mvn test` 原始结果为 `No tests to run` | 增加 SpringBootTest + TestRestTemplate 冒烟测试 | 当前 `mvn test` 通过，覆盖资料/审核/下载收藏/权限/登出 |
| H2 统计接口因 `AS month` 报错 | 读取 mapper 和服务上下文 | 改为 `month_value` 并兼容旧 key |
| H2 热门排行接口因 `AS value` 报错 | 继续读取失败信号和同类别名 | SQL 改为 `download_count`，服务层转换回响应 `value` |
| 教师可伪造资料上传者 | 通读 `MaterialService` 和控制器 | 新增资料按当前 JWT 教师绑定，MySQL 验证确认 `uploaderId=2` |
| PowerShell 验证脚本对 401/403 响应读取不兼容 | 读取报错并改 HTTP 工具参数 | 使用 `-SkipHttpErrorCheck` 后完成真实状态码验证 |

## Notes
- 当前已确认：
  - `084` 后端项目名为“教学资料管理系统”
  - 后端默认配置已改为 H2 自举，正式 MySQL 配置保留在 `application-mysql.yml`
  - MySQL 验证使用 `root / 1234` 和临时库 `teachres_084_verify`
  - 后端 `mvn test` 通过，`Tests run: 1, Failures: 0, Errors: 0`
  - 前端 `npm run build` 通过
  - 后端 `18085` 真实启动并完成登录脱敏、统计权限、教师资料归属、审核权限、下载收藏和登出失效抽测
  - 前端 `3084` 页面响应和代理登录验证通过
  - `docs/checks/084-teachres-management-system.md` 已创建并回填验证结论
  - 下一项目为 `085`

# Task Plan: 086 项目巡检与修复

## Goal
完成 `086` 高清壁纸社区网站的默认启动、H2/MySQL 双环境验证、JWT/权限修复、前端构建与代理联调，并同步单项目报告与总台账。

## Current Phase
Completed: `086` 巡检、修复、H2/MySQL 真实验证、前端代理验证与文档回填已完成；下一项目为 `087`。

## Phases
- [x] 确认 `086` 后端、前端、PRD、SQL、默认账号与原始配置风险
- [x] 修复默认 H2 自举、保留正式 MySQL profile，并新增 `mysql-verify` 验证 profile
- [x] 修复 JDK 17、MySQL 驱动、H2 与 JAXB 运行兼容
- [x] 补充运行态 token 存储，去除默认演示链路对 Redis token 的强依赖
- [x] 修复 JWT Bearer、真实 HTTP 状态、密码脱敏和登出失效
- [x] 修复壁纸 `uploaderId` 归属、防越权删除和审核上架链路
- [x] 修复前端端口、代理和 Bearer token
- [x] 完成后端 `mvn test`
- [x] 完成前端 `npm.cmd install` 与 `npm.cmd run build`
- [x] 使用 `18088` 完成默认 H2 环境真实启动抽测
- [x] 使用 MySQL `root / 1234` 的 `wallpaper_086_verify` 临时库完成 `18089` 真实启动抽测
- [x] 启动前端 `3086` 并验证代理登录
- [x] 新增 `086` 检查报告并更新总台账/进度/发现日志

# Task Plan: 087 项目巡检与修复

## Goal
完成 `087` 课程管理系统的默认启动、H2/MySQL 双环境验证、JWT/权限修复、前端构建与代理联调，并同步单项目报告与总台账。

## Current Phase
Completed: `087` 巡检、修复、H2/MySQL 真实验证、前端代理验证与文档回填已完成；下一项目为 `088`。

## Phases
- [x] 确认 `087` 后端、前端、PRD、SQL、默认账号与原始配置风险
- [x] 修复默认 H2 自举、保留正式 MySQL profile，并新增 `mysql-verify` 验证 profile
- [x] 修复 JDK 17、MySQL 驱动、H2 与 JAXB 运行兼容
- [x] 补充运行态 token 存储，去除默认演示链路对 Redis token 的强依赖
- [x] 修复 JWT Bearer、真实 HTTP 状态、密码脱敏和登出失效
- [x] 修复课程资源 `teacherId` 归属、防越权更新、成绩录入权限和选课/评教链路
- [x] 修复前端端口、代理和 Bearer token
- [x] 完成后端 `mvn test`
- [x] 完成前端 `npm.cmd install` 与 `npm.cmd run build`
- [x] 使用 `18090` 完成默认 H2 环境真实启动抽测
- [x] 使用 MySQL `root / 1234` 的 `course_manage_087_verify` 临时库完成 `18091` 真实启动抽测
- [x] 启动前端 `3087` 并验证代理登录
- [x] 新增 `087` 检查报告并更新总台账/进度/发现日志

# Task Plan: 088 项目巡检与修复

## Goal
完成 `088` 孩童收养信息管理系统的默认启动、H2/MySQL 双环境验证、JWT/权限修复、前端构建与代理联调，并同步单项目报告与总台账。

## Current Phase
Completed: `088` 巡检、修复、H2/MySQL 真实验证、前端代理验证与文档回填已完成；下一项目为 `089`。

## Phases
- [x] 确认 `088` 后端、前端、PRD、SQL、默认账号与原始配置风险
- [x] 修复默认 H2 自举、保留正式 MySQL profile，并新增 `mysql-verify` 验证 profile
- [x] 修复 JDK 17、MySQL 驱动、H2 与 JAXB 运行兼容
- [x] 补充运行态 token 存储，去除默认演示链路对 Redis token 的强依赖
- [x] 修复 JWT Bearer、真实 HTTP 状态、密码脱敏和登出失效
- [x] 修复申请人与审核员角色边界、申请材料归属和后端编译问题
- [x] 修复前端端口、代理和 Bearer token
- [x] 完成后端 `mvn test`
- [x] 完成前端 `npm.cmd install` 与 `npm.cmd run build`
- [x] 使用 `18093` 完成默认 H2 环境真实启动抽测
- [x] 使用 MySQL `root / 1234` 的 `adoption_088_verify` 临时库完成 `18094` 真实启动抽测
- [x] 启动前端 `3088` 并验证代理登录
- [x] 新增 `088` 检查报告并更新总台账/进度/发现日志

# Task Plan: 089 项目巡检与修复

## Goal
完成 `089` 铁路购票平台的默认启动、H2/MySQL 双环境验证、JWT/权限修复、前端构建与代理联调，并同步单项目报告与总台账。

## Current Phase
Completed: `089` 巡检、修复、H2/MySQL 真实验证、前端代理验证与文档回填已完成；下一项目为 `090`。

## Phases
- [x] 确认 `089` 后端、前端、PRD、SQL、默认账号与原始配置风险
- [x] 修复默认 H2 自举、保留正式 MySQL profile，并新增 `mysql-verify` 验证 profile
- [x] 修复 JDK 17、MySQL 驱动、H2 保留字和测试依赖兼容
- [x] 补充运行态 token 与座位锁存储，去除默认演示链路对 Redis 的强依赖
- [x] 修复 JWT Bearer、真实 HTTP 状态、密码脱敏和登出失效
- [x] 修复公开班次放行、订单/车票归属和核销权限边界
- [x] 修复前端端口、代理和 Bearer token
- [x] 完成后端 `mvn test`
- [x] 完成前端 `npm.cmd run build`
- [x] 使用 `18095` 完成默认 H2 环境真实启动抽测
- [x] 使用 MySQL `root / 1234` 的 `railway_ticket_089_verify` 临时库完成 `18096` 真实启动抽测
- [x] 启动前端 `3089` 并验证代理登录
- [x] 新增 `089` 检查报告并更新总台账/进度/发现日志

# Task Plan: 090 项目巡检与修复

## Goal
完成 `090` 戏曲文化苑系统的默认启动、H2/MySQL 双环境验证、JWT/权限修复、前端构建与代理联调，并同步单项目报告与总台账。

## Current Phase
Completed: `090` 巡检、修复、H2/MySQL 真实验证、前端代理验证与文档回填已完成；下一项目为 `091`。

## Phases
- [x] 确认 `090` 后端、前端、PRD、SQL、默认账号与原始配置风险
- [x] 修复默认 H2 自举、保留正式 MySQL profile，并新增 `mysql-verify` 验证 profile
- [x] 修复 JDK 17、MySQL 驱动、H2 和 JAXB 运行兼容
- [x] 补充运行态 token 存储，去除默认演示链路对 Redis 的强依赖
- [x] 修复 JWT Bearer、真实 HTTP 状态、密码脱敏和登出失效
- [x] 修复公开剧目放行、会员预约权限、资源归属、评分权限和赏析互动预约前置约束
- [x] 修复前端端口、代理和 Bearer token
- [x] 完成后端 `mvn test`
- [x] 完成前端 `npm.cmd install` 与 `npm.cmd run build`
- [x] 使用 `18097` 完成默认 H2 环境真实启动抽测
- [x] 使用 MySQL `root / 1234` 的 `opera_culture_090_verify` 临时库完成 `18098` 真实启动抽测
- [x] 启动前端 `3090` 并验证代理登录
- [x] 新增 `090` 检查报告并更新总台账/进度/发现日志

# Task Plan: 091 项目巡检与修复

## Goal
完成 `091` 电影院会员管理系统的默认启动、H2/MySQL 双环境验证、JWT/权限修复、前端构建与代理联调，并同步单项目报告与总台账。

## Current Phase
Completed: `091` 巡检、修复、H2/MySQL 真实验证、前端代理验证与文档回填已完成；下一项目为 `092`。

## Phases
- [x] 确认 `091` 后端、前端、PRD、SQL、默认账号与原始配置风险
- [x] 修复默认 H2 自举、保留正式 MySQL profile，并新增 `mysql-verify` 验证 profile
- [x] 修复 JDK 17、MySQL 驱动、H2 保留字和测试依赖兼容
- [x] 补充运行态 token 与座位锁存储，去除默认演示链路对 Redis 的强依赖
- [x] 修复 JWT Bearer、真实 HTTP 状态、密码脱敏和登出失效
- [x] 修复会员/员工/管理员角色边界、评论购票前置约束和订单/票券归属校验
- [x] 修复前端端口、代理和 Bearer token
- [x] 完成后端 `mvn test`
- [x] 完成前端 `npm.cmd install` 与 `npm.cmd run build`
- [x] 使用 `18099` 完成默认 H2 环境真实启动抽测
- [x] 使用 MySQL `root / 1234` 的 `cinema_member_system_091_verify` 临时库完成 `18100` 真实启动抽测
- [x] 启动前端 `3091` 并验证代理登录
- [x] 新增 `091` 检查报告并更新总台账/进度/发现日志

# Task Plan: 092 项目巡检与修复

## Goal
完成 `092` 蓝天幼儿园管理系统的默认启动、H2/MySQL 双环境验证、JWT/权限修复、前端构建与代理联调，并同步单项目报告与总台账。

## Current Phase
Completed: `092` 巡检、修复、H2/MySQL 真实验证、前端代理验证与文档回填已完成；下一项目为 `093`。

## Phases
- [x] 确认 `092` 后端、前端、PRD、SQL、默认账号与原始配置风险
- [x] 修复默认 H2 自举、保留正式 MySQL profile，并新增 `mysql-verify` 验证 profile
- [x] 修复 JDK 17、MySQL 驱动、H2 和 JAXB 运行兼容
- [x] 补充运行态 token 存储，去除默认演示链路对 Redis token 的强依赖
- [x] 修复 JWT Bearer、真实 HTTP 状态、密码脱敏和登出失效
- [x] 修复家长课表/幼儿档案收口、教师跨班考勤/晨检/反馈/食谱越权和家长反馈归属
- [x] 修复前端端口、代理和 Bearer token
- [x] 完成后端 `mvn.cmd test`
- [x] 完成前端 `npm.cmd install` 与 `npm.cmd run build`
- [x] 使用 `18101` 完成默认 H2 环境真实启动抽测
- [x] 使用 MySQL `root / 1234` 的 `kindergarten_092_verify` 临时库完成 `18102` 真实启动抽测
- [x] 启动前端 `3092` 并验证代理登录
- [x] 新增 `092` 检查报告并更新总台账/进度/发现日志

# Task Plan: 093 项目巡检与修复

## Goal
完成 `093` 自助售货管理系统的默认启动、H2/MySQL 双环境验证、JWT/权限修复、前端构建与代理联调，并同步单项目报告与总台账。

## Current Phase
Completed: `093` 巡检、修复、H2/MySQL 真实验证、前端代理验证与文档回填已完成；下一项目为 `094`。

## Phases
- [x] 确认 `093` 后端、前端、PRD、SQL、默认账号与原始配置风险
- [x] 修复默认 H2 自举、保留正式 MySQL profile，并新增 `mysql-verify` 验证 profile
- [x] 修复 JDK 17、MySQL 驱动、H2 与 JAXB 运行兼容
- [x] 补充 `@MapperScan` 与运行态 token 存储，去除默认演示链路对 Redis token 的强依赖
- [x] 修复 JWT Bearer、真实 HTTP 状态、密码脱敏和登出失效
- [x] 修复顾客/补货员/管理员角色边界、订单支付归属和故障归属校验
- [x] 修复前端端口、代理和 Bearer token
- [x] 完成后端 `mvn.cmd test`
- [x] 完成前端 `npm.cmd install` 与 `npm.cmd run build`
- [x] 使用 `18103` 完成默认 H2 环境真实启动抽测
- [x] 使用 MySQL `root / 1234` 的 `vending_093_verify` 临时库完成 `18104` 真实启动抽测
- [x] 启动前端 `3093` 并验证代理登录
- [x] 新增 `093` 检查报告并更新总台账/进度/发现日志

# Task Plan: 094 项目巡检与修复

## Goal
完成 `094` 宠物咖啡馆平台的默认启动、H2/MySQL 双环境验证、JWT/权限修复、前端构建与代理联调，并同步单项目报告与总台账。

## Current Phase
Completed: `094` 巡检、修复、H2/MySQL 真实验证、前端代理验证与文档回填已完成；下一项目为 `095`。

## Phases
- [x] 读取总台账、`093` 报告与规划文件，确认下一项目为 `094`
- [x] 清理 `093` 验证进程（`18103`、`18104`、`3093`）
- [x] 确认 `094` 后端、前端、PRD、PLAN、SQL、默认账号与原始配置风险
- [x] 执行后端测试/构建并定位默认启动阻塞点
- [x] 修复默认 H2 自举、保留正式 MySQL profile，并新增 `mysql-verify` 验证 profile
- [x] 修复 JDK 17、MySQL 驱动、JWT/Redis 演示依赖与真实 HTTP 状态
- [x] 修复顾客/店长/管理员角色边界、订单支付/预约/评价归属和密码脱敏
- [x] 修复前端端口、代理和 Bearer token
- [x] 完成后端 `mvn.cmd test`
- [x] 完成前端 `npm.cmd install` 与 `npm.cmd run build`
- [x] 完成默认 H2 环境真实启动抽测
- [x] 完成 MySQL `root / 1234` 的 `mysql-verify` 真实启动抽测
- [x] 启动前端并验证代理登录
- [x] 新增 `094` 检查报告并更新总台账/进度/发现日志

# Task Plan: 095 项目巡检与修复

## Goal
完成 `095` 足球联赛管理系统的默认启动、H2/MySQL 双环境验证、JWT/权限修复、前端构建与代理联调，并同步单项目报告与总台账。

## Current Phase
Completed: `095` 巡检、H2/MySQL 真实验证、前端代理验证与文档回填已完成；下一项目为 `096`。

## Phases
- [x] 读取总台账并确认下一项目为 `095`
- [x] 确认 `095` 后端、前端、PRD、SQL、默认账号与原始配置风险
- [x] 执行后端测试/构建并定位默认启动阻塞点
- [x] 确认默认 H2 自举、正式 MySQL profile 与 `mysql-verify` 验证 profile
- [x] 确认 JDK 17、MySQL 驱动、JWT/Redis 演示依赖与真实 HTTP 状态
- [x] 验证角色边界、归属校验和密码脱敏
- [x] 验证前端端口、代理和 Bearer token
- [x] 完成后端 `mvn.cmd test`
- [x] 完成前端 `npm.cmd run build`
- [x] 完成默认 H2 环境真实启动抽测
- [x] 完成 MySQL `root / 1234` 的 `mysql-verify` 真实启动抽测
- [x] 启动前端并验证代理登录
- [x] 新增 `095` 检查报告并更新总台账/进度/发现日志

# Task Plan: 096 项目巡检与修复

## Goal
完成 `096` 最终项目的默认启动、H2/MySQL 双环境验证、JWT/权限修复、前端构建与代理联调，并同步单项目报告与总台账，完成本批全部项目闭环。

## Current Phase
Completed: `096` 巡检、修复、H2/MySQL 真实验证、前端代理验证与文档回填已完成；本批 `001`-`096` 全部完成。

## Phases
- [x] 读取总台账并确认下一项目为 `096`
- [x] 确认 `096` 后端、前端、PRD、SQL、默认账号与原始配置风险
- [x] 执行后端测试和前端构建
- [x] 修复或确认默认 H2 自举、正式 MySQL profile 与 `mysql-verify` 验证 profile
- [x] 验证 JWT Bearer、真实 HTTP 状态、密码脱敏和登出失效
- [x] 验证角色边界、业务归属和核心链路
- [x] 完成默认 H2 环境真实启动抽测
- [x] 完成 MySQL `root / 1234` 的 `mysql-verify` 真实启动抽测
- [x] 启动前端并验证代理登录
- [x] 新增 `096` 检查报告并更新总台账/进度/发现日志

# Task Plan: 125/126 新增毕设项目生成

## Goal
完成 `125` 智慧停车诱导与空位预测平台、`126` 家庭能源用电分析与节能建议平台的 PRD、PLAN、Spring Boot 后端、Vue 前端、SQL 初始化脚本、合集 README 和候选清单回填。

## Current Phase
Completed: `125/126` 已按新增项目规则生成并完成静态结构验证；下一项目为 `127`。

## Phases
- [x] 确认下一批题目为 `125` 智慧停车诱导与空位预测平台、`126` 家庭能源用电分析与节能建议平台
- [x] 按 `123/124` 结构模板生成 `125-backend`、`125-frontend`、`126-backend`、`126-frontend`
- [x] `125` 使用 Spring Boot 2.7.18 + MyBatis-Plus + MySQL + Redis + JWT，前端使用 Vue3 + Element Plus + ECharts
- [x] `126` 使用 Spring Boot 2.7.18 + MyBatis 注解 SQL + PageHelper + MySQL + Redis + JWT，前端使用 Vue3 + Element Plus + ECharts
- [x] 配置 `125` 后端端口 `8125`、前端端口 `3125`、数据库 `smart_parking_125`、Redis DB `28`
- [x] 配置 `126` 后端端口 `8126`、前端端口 `3126`、数据库 `home_energy_126`、Redis DB `29`
- [x] 确认两个项目均使用 MySQL `root / 1234`
- [x] 完成静态数量验证：两个项目均为 14 个实体、14 个 Mapper、16 个 Controller、17 个前端页面、14 张 SQL 表
- [x] 完成旧项目关键词残留扫描、注释扫描、MyBatis 注解 SQL 条件转义检查和登录脱敏检查
- [x] 更新 `readme.md`、`readme_simple.md`、`docs/topic-candidates-097-146.md`
- [x] 按 `rule.md` 要求未执行编译构建，仅做静态验证
- [x] 下一项目为 `127`

# Task Plan: 127/128 新增毕设项目生成

## Goal
完成 `127` 企业碳排放核算与减排任务管理系统、`128` ESG 数据填报与可视化报告系统的 PRD、PLAN、Spring Boot 后端、Vue 前端、SQL 初始化脚本、合集 README 和候选清单回填。

## Current Phase
Completed: `127/128` 已按新增项目规则生成并完成静态结构验证；下一项目为 `129`。

## Phases
- [x] 确认下一批题目为 `127` 企业碳排放核算与减排任务管理系统、`128` ESG 数据填报与可视化报告系统
- [x] 按 `125/126` 结构模板生成 `127-backend`、`127-frontend`、`128-backend`、`128-frontend`
- [x] `127` 使用 Spring Boot 2.7.18 + MyBatis-Plus + MySQL + Redis + JWT，前端使用 Vue3 + Element Plus + ECharts
- [x] `128` 使用 Spring Boot 2.7.18 + MyBatis 注解 SQL + PageHelper + MySQL + Redis + JWT，前端使用 Vue3 + Element Plus + ECharts
- [x] 配置 `127` 后端端口 `8127`、前端端口 `3127`、数据库 `carbon_accounting_127`、Redis DB `30`
- [x] 配置 `128` 后端端口 `8128`、前端端口 `3128`、数据库 `esg_report_128`、Redis DB `31`
- [x] 确认两个项目均使用 MySQL `root / 1234`
- [x] 完成静态数量验证：两个项目均为 14 个实体、14 个 Mapper、16 个 Controller、17 个前端页面、14 张 SQL 表
- [x] 完成旧项目关键词残留扫描、注释扫描、MyBatis 注解 SQL 条件转义检查和运行配置检查
- [x] 更新 `readme.md`、`readme_simple.md`、`docs/topic-candidates-097-146.md`
- [x] 按 `rule.md` 要求未执行编译构建，仅做静态验证
- [x] 下一项目为 `129`

# Task Plan: 129/130 新增毕设项目生成

## Goal
完成 `129` 水产养殖环境监测与投喂预警系统、`130` 温室大棚物联网控制与病害预警系统的 PRD、PLAN、Spring Boot 后端、Vue 前端、SQL 初始化脚本、合集 README 和候选清单回填。

## Current Phase
Completed: `129/130` 已按新增项目规则生成并完成静态结构验证；下一项目为 `131`。

## Phases
- [x] 确认下一批题目为 `129` 水产养殖环境监测与投喂预警系统、`130` 温室大棚物联网控制与病害预警系统
- [x] 按 `127/128` 结构模板生成 `129-backend`、`129-frontend`、`130-backend`、`130-frontend`
- [x] `129` 使用 Spring Boot 2.7.18 + MyBatis-Plus + MySQL + Redis + JWT，前端使用 Vue3 + Element Plus + ECharts
- [x] `130` 使用 Spring Boot 2.7.18 + MyBatis 注解 SQL + PageHelper + MySQL + Redis + JWT，前端使用 Vue3 + Element Plus + ECharts
- [x] 配置 `129` 后端端口 `8129`、前端端口 `3129`、数据库 `aquaculture_monitor_129`、Redis DB `32`
- [x] 配置 `130` 后端端口 `8130`、前端端口 `3130`、数据库 `greenhouse_iot_130`、Redis DB `33`
- [x] 确认两个项目均使用 MySQL `root / 1234`
- [x] 完成静态数量验证：两个项目均为 14 个实体、14 个 Mapper、16 个 Controller、17 个前端页面、14 张 SQL 表
- [x] 完成旧项目关键词残留扫描、注释扫描、MyBatis 注解 SQL 条件转义检查和登录脱敏检查
- [x] 修正 `127/128/129/130` JWT secret 与 artifactId 口径残留
- [x] 更新 `readme.md`、`readme_simple.md`、`docs/topic-candidates-097-146.md`
- [x] 按 `rule.md` 要求未执行编译构建，仅做静态验证
- [x] 下一项目为 `131`

# Task Plan: 131/132 新增毕设项目生成

## Goal
完成 `131` 药品不良反应上报与随访管理系统、`132` 医疗器械借用消毒与追踪管理系统的 PRD、PLAN、Spring Boot 后端、Vue 前端、SQL 初始化脚本、合集 README 和候选清单回填。

## Current Phase
Completed: `131/132` 已按新增项目规则生成并完成静态结构验证；下一项目为 `133`。

## Phases
- [x] 确认下一批题目为 `131` 药品不良反应上报与随访管理系统、`132` 医疗器械借用消毒与追踪管理系统
- [x] 按 `129/130` 结构模板生成 `131-backend`、`131-frontend`、`132-backend`、`132-frontend`
- [x] `131` 使用 Spring Boot 2.7.18 + MyBatis-Plus + MySQL + Redis + JWT，前端使用 Vue3 + Element Plus + ECharts
- [x] `132` 使用 Spring Boot 2.7.18 + MyBatis 注解 SQL + PageHelper + MySQL + Redis + JWT，前端使用 Vue3 + Element Plus + ECharts
- [x] 配置 `131` 后端端口 `8131`、前端端口 `3131`、数据库 `drug_reaction_131`、Redis DB `34`
- [x] 配置 `132` 后端端口 `8132`、前端端口 `3132`、数据库 `medical_device_132`、Redis DB `35`
- [x] 确认两个项目均使用 MySQL `root / 1234`
- [x] 完成静态数量验证：两个项目均为 14 个实体、14 个 Mapper、16 个 Controller、17 个前端页面、14 张 SQL 表
- [x] 完成旧项目关键词残留扫描、注释扫描、MyBatis 注解 SQL 条件转义检查和登录脱敏检查
- [x] 修正 `131/132` 数据库名、artifactId 与 JWT secret 替换顺序残留
- [x] 更新 `readme.md`、`readme_simple.md`、`docs/topic-candidates-097-146.md`
- [x] 按 `rule.md` 要求未执行编译构建，仅做静态验证
- [x] 下一项目为 `133`


# Task Plan: 133/134 新增毕设项目生成

完成 `133` 实验室耗材采购审批与库存预警系统、`134` 科研项目经费报销与成果管理系统的 PRD、PLAN、Spring Boot 后端、Vue 前端、SQL 初始化脚本、合集 README 和候选清单回填。

Status: Completed

- [x] 确认下一批题目为 `133` 实验室耗材采购审批与库存预警系统、`134` 科研项目经费报销与成果管理系统
- [x] `133` 使用 Spring Boot 2.7.18 + MyBatis-Plus + MySQL + Redis + JWT，前端使用 Vue3 + Element Plus + ECharts
- [x] `134` 使用 Spring Boot 2.7.18 + MyBatis 注解 SQL + PageHelper + MySQL + Redis + JWT，前端使用 Vue3 + Element Plus + ECharts
- [x] 配置 `133` 后端端口 `8133`、前端端口 `3133`、数据库 `lab_consumable_133`、Redis DB `36`
- [x] 配置 `134` 后端端口 `8134`、前端端口 `3134`、数据库 `research_fund_134`、Redis DB `37`
- [x] 完成 `133/134` 静态数量、残留关键词、注释、MyBatis 条件和登录脱敏验证
- [x] 更新 `readme.md`、`readme_simple.md` 和 `docs/topic-candidates-097-146.md`
- [x] 下一项目为 `135`
