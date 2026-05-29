## Session: 2026-05-11 097-098 项目巡检续查

### 已完成：097 大模型提示词资产管理与效果评测平台
- **Status:** completed_with_findings
- Actions taken:
  - 重新核对 `docs/project-check-tracker.md`、`docs/checks/` 和 `docs/topic-candidates-097-146.md`，确认真实巡检需要从 `097` 继续，而不是从 `101` 跳号
  - 完成 `097-backend` / `097-frontend` 静态核对，确认后端默认强依赖 MySQL/Redis，前端仅做菜单显隐
  - 执行 `097-backend/mvn.cmd test`，结果为通过，但确认当前无自动化测试
  - 执行 `097-frontend/npm.cmd install`、`npm.cmd run build`，构建通过，保留 chunk 体积告警
  - 在 `18097` 启动后端并复现默认登录失败，定位到 `Unknown database 'prompt_asset_097'`
  - 使用本机 MySQL `root / 1234` 导入 `097-backend/sql/init.sql`，确认导库后登录恢复 `200`
  - 实测确认工程师/评审员可读取管理员侧团队/模型/评分规则分页数据
  - 实测确认工程师可更新他人反馈内容并成功落库
  - 实测确认评审员可对同一条评测结果重复复核并覆盖先前结论
  - 新增 `docs/checks/097-prompt-asset-evaluation-platform.md`

### 已完成：098 企业知识库智能问答与文档权限管理系统
- **Status:** completed_with_findings
- Actions taken:
  - 完成 `098-backend` / `098-frontend` 静态核对，确认后端默认强依赖 MySQL/Redis，路由层无角色守卫
  - 执行 `098-backend/mvn.cmd test`，结果为通过，但确认 `No tests to run`
  - 执行 `098-frontend/npm.cmd install`、`npm.cmd run build`，构建通过，主包与 Dashboard chunk 偏大
  - 在 `18098` 启动后端并复现默认登录失败，定位到 `Unknown database 'knowledge_qa_098'`
  - 使用本机 MySQL `root / 1234` 导入 `098-backend/sql/init.sql`，确认导库后管理员/编辑员/员工均可登录
  - 实测确认员工可读取 `/api/group/page`、`/api/permission/page`，编辑员可读取 `/api/member/page`
  - 实测确认编辑员可在仅 `READ` 授权的制度库空间创建会话并成功发起问答，说明 `document_permission` / `permission_level` 未参与真实问答鉴权
  - 实测确认编辑员可关闭员工会话并改写员工反馈内容，数据库状态与内容均被真实更新
  - 新增 `docs/checks/098-knowledge-qa-permission-platform.md`

### 当前状态
- `097`：已完成巡检，状态为 `待修复`
- `098`：已完成巡检，状态为 `待修复`
- `099`：已完成巡检，状态为 `待修复`
- `100`：已完成巡检，状态为 `待修复`
- 下一项目：`101`

### 已完成：099 AIGC 图片内容审核与版权存证平台
- **Status:** completed_with_findings
- Actions taken:
  - 完成 `099-backend` / `099-frontend` 静态核对，确认后端默认强依赖 MySQL/Redis，路由层无角色守卫
  - 执行 `099-backend/mvn.cmd test`，结果为通过，但确认 `No tests to run`
  - 执行 `099-frontend/npm.cmd install`、`npm.cmd run build`，构建通过，主包与 Dashboard chunk 偏大
  - 在 `18099` 启动后端并复现默认登录失败，定位到 `Unknown database 'aigc_copyright_099'`
  - 使用本机 MySQL `root / 1234` 导入 `099-backend/sql/init.sql`，确认导库后管理员/审核员/创作者均可登录
  - 复核认证返回，确认登录和 `/api/auth/info` 已通过实体 `@JsonIgnore` 隐藏 `password`
  - 实测确认创作者可读取 `/api/rule/page`、`/api/tag/page`、`/api/result/page`
  - 实测确认 `assertCreator` 实际包含审核员，审核员可通过 `/api/asset` 越权修改创作者作品并成功落库
  - 实测确认审核员可对同一条审核结果重复复核并覆盖先前意见
  - 新增 `docs/checks/099-aigc-image-copyright-platform.md`

### 已完成：100 AI 生成文本检测与学术诚信预警系统
- **Status:** completed_with_findings
- Actions taken:
  - 完成 `100-backend` / `100-frontend` 静态核对，确认后端默认强依赖 MySQL/Redis，路由层无角色守卫
  - 执行 `100-backend/mvn.cmd test`，结果为通过，但确认 `No tests to run`
  - 执行 `100-frontend/npm.cmd install`、`npm.cmd run build`，构建通过，主包与 Dashboard chunk 偏大
  - 在 `18100` 启动后端，确认应用可启动；随后导入 `100-backend/sql/init.sql` 供真实联调
  - 复核认证返回，确认登录和 `/api/auth/info` 已隐藏 `password`
  - 实测确认学生可读取 `/api/rule/page`、`/api/result/page`、`/api/warning/page`
  - 实测确认教师被视为复核角色，可新增检测任务
  - 实测确认复核员被视为学生角色，可新增申诉，且新记录 `student_id` 被写为复核员自身 ID
  - 实测确认复核员可修改他人整改记录并成功落库
  - 实测确认复核员可对同一条检测结果重复复核并覆盖先前意见
  - 新增 `docs/checks/100-academic-integrity-warning-platform.md`

## Session: 2026-05-06 101-118 项目精修

### 进行中：101-150 项目精修
- **Status:** in_progress
- Actions taken:
  - GitHub 已完成首次上传，远端仓库为 `origin https://github.com/xiaou61/2026-bs.git`
  - 从 `101` 开始启动精修，当前已完成 `101-118` 的首轮静态修正
  - 修复 `101/102` 认证接口返回用户对象未脱敏的问题，为 `login/info` 增加 `setPassword(null)`
  - 修复 `102-frontend/src/views/Layout.vue` 中错误的 Vue 模板插值 `{ ... }`，恢复为 `{{ ... }}`
  - 扩展修复 `103-116` 多个后端 `AuthService`，统一补齐登录返回与用户信息接口的密码脱敏
  - 复核 `117/118`，确认认证脱敏已存在，仅保留文档层精修
  - 重写 `101-118` 的 `PLAN.md` 表述，移除“创建工程目录”“README 标记最新”“核对 root/1234”这类生成态措辞
  - 将 `PLAN.md` 统一调整为精修阶段口径，补充“当前状态”“完成标准”“文档与实现一致性”等交付描述
  - 全程遵循仓库规则，仅做静态修正与扫描，不执行编译构建
  - 在 `101` 中继续按 PRD 深修角色边界：候选人/简历/证书/面试排期/面试反馈已切到按当前登录人归属控制
  - 收紧 `101` 后端统计看板权限，`/api/statistics/dashboard` 改为仅 `ADMIN` 可访问
  - 收紧 `101` 前端路由与落地页：管理员默认进入 `/dashboard`，HR 默认进入 `/candidate`，候选人默认进入 `/resume`，面试官默认进入 `/interview`
  - 隐藏 `101` 非管理员侧边栏中的“首页看板”，避免候选人和面试官通过前端入口误入管理员看板
  - 修复 `101` 候选人档案状态字段越权问题：候选人前端不再可编辑 `status`，后端保存时强制保留原状态/默认启用
  - 同步更新 `101-backend/PRD.md` 与 `101-backend/PLAN.md`，明确管理员/HR/候选人/面试官的责任边界
  - 收紧 `101` 面试排期、解析任务、匹配任务和岗位管理的状态流转：普通新增/编辑不再允许直接改 `status`，统一通过发布/关闭、启动/完成/驳回、确认/取消/完成等动作接口流转
  - 优化 `101` 候选人/面试官前端视角，隐藏候选人简历/证书中的冗余 `candidateId` 列，以及面试官视角中的冗余 `interviewerId` 列
  - 补齐 `101` 解析任务、匹配任务的 `updateTime` 写入逻辑，并为动作按钮增加按状态显隐，避免非法状态流转和重复点击
  - 收紧 `101` 面试反馈唯一性：同一面试计划下，同一面试官只能保留一条反馈记录
  - 补齐 `101` 操作日志模块筛选项，与系统真实业务模块保持一致

## Session: 2026-04-24

## Session: 2026-05-04 新增 117 项目生成

### 已完成：118 智能仓储 AGV 任务调度与库位优化系统
- **Status:** completed
- Actions taken:
  - 确认候选清单中 `118` 为“智能仓储 AGV 任务调度与库位优化系统”
  - 确认 `117` 使用 MyBatis-Plus，`118` 按交替节奏使用 MyBatis + PageHelper
  - 生成 `118-backend`：Spring Boot 2.7.18、MyBatis 2.3.1 注解 SQL、PageHelper 1.4.7、JWT、Redis、MySQL `smart_warehouse_agv_118`
  - 生成 `118-frontend`：Vue3、Vite 5、Element Plus、Pinia、Axios、ECharts，端口 `3118`，代理后端 `8118`
  - 补齐 `118-backend/PRD.md`、`118-backend/PLAN.md` 和 `118-backend/sql/init.sql`
  - 静态扫描确认：14 个实体、14 个 Mapper、16 个 Controller、17 个前端视图、14 张 SQL 表
  - 修复 MyBatis 注解 SQL 空字符串判断转义，修复 `selectByUsername` 参数绑定，并补充登录与用户信息 password 脱敏
  - 静态扫描确认：`root / 1234`、`8118/3118`、`smart_warehouse_agv_118`、Redis `database: 21`、`smartwarehouse:token:`、MyBatis + PageHelper 依赖和智能仓储 AGV 主题词均存在
  - 残留关键词和注释扫描未发现异常
  - 更新 `readme.md`、`readme_simple.md`、`docs/topic-candidates-097-146.md`，下一项目为 `119`

### 已完成：117 本地生活服务券核销与商户结算系统
- **Status:** completed
- Actions taken:
  - 确认候选清单中 `117` 为“本地生活服务券核销与商户结算系统”
  - 确认 `115` 使用 MyBatis-Plus、`116` 使用 MyBatis + PageHelper，`117` 按交替节奏使用 MyBatis-Plus
  - 确认当前规则：不跑编译构建、不创建单项目 README、MySQL 使用 `root / 1234`、缓存使用 Redis、前端 Vue、后端 Spring Boot
  - 生成 `117-backend`：Spring Boot 2.7.18、MyBatis-Plus 3.5.5、JWT、Redis、MySQL `local_voucher_117`
  - 生成 `117-frontend`：Vue3、Vite 5、Element Plus、Pinia、Axios、ECharts，端口 `3117`，代理后端 `8117`
  - 补齐 `117-backend/PRD.md`、`117-backend/PLAN.md` 和 `117-backend/sql/init.sql`
  - 静态扫描确认：14 个实体、14 个 Mapper、16 个 Controller、17 个前端视图、14 张 SQL 表
  - 静态扫描确认：`root / 1234`、`8117/3117`、`local_voucher_117`、Redis `database: 20`、`localvoucher:token:`、MyBatis-Plus 依赖和本地生活主题词均存在
  - 残留关键词和注释扫描未发现异常
  - 更新 `readme.md`、`readme_simple.md`、`docs/topic-candidates-097-146.md`，下一项目为 `118`

### 已完成：001 项目预览样板
- **Status:** completed
- Actions taken:
  - 创建 `docs/previews/` 预览目录结构
  - 新增项目预览设计文档和实施计划
  - 实现 `scripts/project_preview/run_preview.py`
  - 实现真实项目扫描、README 项目名解析、账号提取和路由提取
  - 实现 MySQL 初始化 SQL 的自动重建导入
  - 实现前后端启动、运行清单输出和项目页/分组页/README 渲染
  - 完成 `001` 前后端真实启动
  - 通过浏览器工具完成 `001` 的 23 张截图
  - 生成 `docs/previews/projects/001.md`
  - 生成 `docs/previews/groups/001-010.md`
  - 在 `readme.md` 中新增“项目预览”入口

### 当前状态
- `001`：预览已完成
- `002`：预览已完成
- `003`：预览已完成
- `004`：预览已完成
- `005`：预览已完成
- `006`：预览已完成
- `007`：待开始
- 项目预览入口：`readme.md`
- 单项目详览入口：`docs/previews/projects/001.md`、`docs/previews/projects/002.md`、`docs/previews/projects/003.md`、`docs/previews/projects/004.md`、`docs/previews/projects/005.md`

### 已完成：002 项目预览
- **Status:** completed
- Actions taken:
  - 重新核对 `002` 的 README、SQL、路由与账号口径
  - 补充脚本对 Markdown 账号表格的自动识别
  - 修复 `002-frontend` 代理目标可通过 `PREVIEW_API_TARGET` 覆盖
  - 在 `18080` 启动 `002-backend`，在 `3002` 启动 `002-frontend`
  - 通过浏览器工具完成 `002` 的 `20` 张截图
  - 生成 `docs/previews/projects/002.md`
  - 更新 `docs/previews/groups/001-010.md`
  - 更新 `readme.md` 中的项目预览入口

### 已完成：003 项目预览
- **Status:** completed
- Actions taken:
  - 核对 `003` 的 README、`TEST_ACCOUNTS.md`、SQL、模板页面与默认账号
  - 确认 `003` 为 Thymeleaf 一体化后端项目，无独立前端工程
  - 修复 `JwtInterceptor` 未写入 `userRole` 导致订单接口报错的问题
  - 修复公告管理接口被错误排除鉴权导致 `403` 的问题
  - 补强预览脚本对 `TEST_ACCOUNTS.md` 的识别与重复实例清理
  - 通过浏览器工具完成 `003` 的 `24` 张截图
  - 生成 `docs/previews/projects/003.md`
  - 更新 `docs/previews/groups/001-010.md`
  - 更新 `readme.md` 中的项目预览入口
  - 清理 `003` 在 `8080-8083` 上遗留的 Java 进程

### 已完成：004 项目预览
- **Status:** completed
- Actions taken:
  - 核对 `004` 的 `ACCOUNTS.md`、前后端配置、SQL、路由和 WebSocket 口径
  - 启动 `004-backend(8004)` 与 `004-frontend(5004)` 并完成真实联调
  - 使用双隔离浏览器会话跑通 `zhangsan` 与 `lisi` 的实时聊天与历史记录
  - 通过好友搜索完成 `zhangsan -> admin` 的好友添加，并验证 `admin` 侧列表同步
  - 创建分组并保存个人资料，覆盖设置相关页面
  - 通过数据库插入真实未读通知，覆盖通知列表、未读页和全部已读状态
  - 通过浏览器工具完成 `004` 的 `20` 张截图
  - 生成 `docs/previews/projects/004.md`
  - 更新 `docs/previews/groups/001-010.md`
  - 更新 `readme.md` 中的项目预览入口
  - 停止 `004` 的前后端进程
  - 补强预览脚本的账号识别和进程回收逻辑

### 已完成：005 项目预览
- **Status:** completed
- Actions taken:
  - 核对 `005` 的 README、使用说明、账号文档、SQL、模板页面与权限口径
  - 启动 `005-backend(8085)` 并完成真实单体站点联调
  - 使用管理员账号创建“毕业设计项目预览满意度调查”问卷
  - 通过真实接口补齐单选、多选、问答、评分、下拉五种题型
  - 发布问卷并验证分享链接 `/survey/fill?id=1`
  - 使用游客视角完成问卷填写和提交
  - 验证统计页实时显示答卷数据
  - 验证普通用户登录后 dashboard 为空，但直达统计页仍可访问
  - 通过浏览器工具完成 `005` 的 `15` 张截图
  - 生成 `docs/previews/projects/005.md`
  - 更新 `docs/previews/groups/001-010.md`
  - 更新 `readme.md` 中的项目预览入口
  - 停止 `005` 后端进程
  - 补强预览脚本对 `ACCOUNTS.md` 多行账号块的识别逻辑

### 进行中：006 项目预览
- **Status:** in_progress
- Actions taken:
  - 核对 `006` 在 `readme.md`、`ACCOUNTS.md`、`application.yml`、`init.sql`、`PRD.md` 中的项目口径
  - 确认 `006` 为 Spring Boot + Thymeleaf 单体站点，无独立前端工程
  - 确认默认端口为 `8086`，数据库为 `lost_found_db`
  - 确认账号为 `admin/admin`、`user1/123456`、`user2/123456`
  - 确认主要页面和业务链路已覆盖失物、招领、认领、收藏、通知、个人中心与管理员后台
  - 确认文档与实现存在技术栈差异，后续以真实代码运行为准

### 已完成：006 项目预览
- **Status:** completed
- Actions taken:
  - 启动 `006-backend(8086)` 并完成真实单体站点联调
  - 补强预览脚本，避免把数据库账号误识别为测试账号，并让单体后端项目自动读取真实端口
  - 修复 `publish-lost.html` 与 `publish-found.html` 的时间格式提交问题，打通发布接口
  - 使用浏览器工具完成游客、管理员、`user1`、`user2` 四类视角的 `23` 张截图
  - 使用 `user1` 真实发布一条失物信息“在图书馆遗失银色保温杯”
  - 使用 `user1` 对 `user2` 的“捡到高数教材”提交认领申请
  - 使用 `user2` 在“收到的认领”页面真实审核通过该申请
  - 验证双方通知、收藏、发出的认领与审核结果链路
  - 生成 `docs/previews/projects/006.md`
  - 更新 `docs/previews/groups/001-010.md`
  - 更新 `readme.md` 中的项目预览入口

# Progress Log

## Session: 2026-04-21

### 已完成：031 基于SpringBoot的球赛订票系统
- **Status:** completed
- Actions taken:
  - 修复默认 MySQL/Redis 依赖，切到 H2 自举
  - 修复 `jOOQ codegen` 默认阻塞
  - 修复前端登录/注册提交方式
  - 打通真实票价、真实座位、下单、支付、出票、取消退款闭环
  - 补充后端自动化测试并通过
  - 完成真实启动与接口抽测

### 已完成：032 社区老年人健康管理系统
- **Status:** completed
- Actions taken:
  - 复现默认启动失败并定位到 MySQL/JWT/列映射问题
  - 改为 H2 默认自举，保留 MySQL 配置
  - 修复 JWT 密钥与解析逻辑
  - 修复 Spring Data JDBC 列映射
  - 补默认账号并隐藏密码字段
  - 新增后端自动化测试并通过
  - 完成后端真实启动与接口抽测
  - 完成前端依赖安装、打包与开发服务启动验证

## Session: 2026-04-22

### 已完成：033 婚纱写真馆管理系统
- **Status:** completed
- Actions taken:
  - 复现默认环境下首个查询即 MySQL 认证失败的问题
  - 改为 H2 默认自举，保留 `application-mysql.yml`
  - 新增 H2 初始化脚本，兼容 `user/order/package` 等保留字
  - 修复异常处理吞错，只返回 `System error: null` 的问题
  - 明确并统一默认账号口径到 H2/MySQL 初始化脚本
  - 新增后端自动化测试并通过
  - 完成后端真实启动与接口抽测
  - 完成前端构建、开发服务与代理登录验证

### 进行中：034 项目巡检恢复
- **Status:** in_progress
- Actions taken:
  - 读取 `task_plan.md`、`progress.md`、`findings.md` 恢复上一轮上下文
  - 读取 `docs/project-check-tracker.md` 确认总台账最新完成到 `033`
  - 确认下一项目为 `034`
  - 检查 `docs/checks` 目录，确认尚无 `034` 单项目检查文档
  - 确认 `034` 项目名为“零食铺子仓储管理系统”
  - 确认 `034-backend` 为 Spring Boot 3.2 + MyBatis-Plus 后端，默认 H2 自举
  - 确认 `034-frontend` 为单文件静态前端，使用 CDN 版 Vue3 / Element Plus

### 已完成：034 零食铺子仓储管理系统
- **Status:** completed
- Actions taken:
  - 复现 `mvn spring-boot:run` 默认启动失败，并定位到 Spring Security Bean 循环依赖
  - 拆出 `WmsUserDetailsService` 与 `SecuritySupportConfig`，修复鉴权装配链回环
  - 新增后端烟雾测试，覆盖应用启动、默认账号登录与 `GET /sku`
  - 完成后端 `mvn test`
  - 完成后端真实启动验证，确认默认端口 `8080` 可用
  - 完成登录、SKU、库存、入库、出库、调拨、盘点与库存日志接口抽测
  - 通过静态文件服务与 Playwright 完成前端默认 API 地址登录联调
  - 新增 `034` 单项目检查文档并更新总台账
  - 清理本轮后端 `8080` 与静态前端 `5134` 验证进程

### 进行中：035 项目巡检恢复
- **Status:** in_progress
- Actions taken:
  - 读取 `task_plan.md`、`progress.md`、`findings.md` 恢复上一轮上下文
  - 读取 `docs/project-check-tracker.md` 确认总台账最新完成到 `034`
  - 确认下一项目为 `035`
  - 确认 `035` 项目名为“乡村振兴水稻收割预约系统”
  - 确认 `035-backend` 与 `035-frontend` 目录存在
  - 检查 `docs/checks` 目录，确认尚无 `035` 单项目检查文档

### 已完成：035 乡村振兴水稻收割预约系统
- **Status:** completed
- Actions taken:
  - 修复 `mybatis-plus-boot-starter` 与 Boot 3.2 / Spring 6.1 不兼容导致的默认启动失败
  - 修复默认 JWT 密钥过短导致的 `WeakKeyException`
  - 将默认环境切换为 `H2` 自举，并新增 `application-mysql.yml`
  - 新增 `schema-h2.sql` / `data-h2.sql`，统一默认账号为 `admin`、`farmer_demo`、`driver_demo`
  - 修复原始种子哈希与文档口令不一致的问题，统一默认密码为 `123456`
  - 修复 `/api/auth/me` 密码字段泄露
  - 为注册、地块、设备、预约流转补齐角色与归属校验
  - 修复预约状态枚举在 H2 下落库失败的问题
  - 新增后端集成测试并通过
  - 完成后端真实接口抽测：登录、地块、设备、预约、指派、开工、完工
  - 为前端 Vite 代理新增 `VITE_API_TARGET` 覆盖能力
  - 完成前端构建与浏览器登录联调验证
  - 新增 `035` 单项目检查文档并更新总台账

## Current State

- `031`：已完成并回填文档
- `032`：已完成并回填文档
- `033`：已完成并回填文档
- `034`：已完成并回填文档
- `035`：已完成并回填文档
- `036`：已完成并回填文档
- `037`：已完成并回填文档
- 下一项目：`038`

### 已完成：036 小梦想全球公益捐赠平台
- **Status:** completed
- Actions taken:
  - 读取 `task_plan.md`、`progress.md`、`findings.md` 恢复上一轮上下文
  - 读取 `docs/project-check-tracker.md` 确认总台账最新完成到 `035`
  - 确认下一项目为 `036`
  - 确认 `036-backend` 与 `036-frontend` 目录存在
  - 检查 `docs/checks` 目录，确认尚无 `036` 单项目检查文档
  - 确认后端存在 `启动说明.txt`、`036-项目总结.txt`、`schema.sql`、`data.sql`
  - 确认前端为 Vite 工程，存在 `package.json` 与 `vite.config.js`
  - 执行 `036-backend/mvn test`，复现源码 HTML 实体转义导致的 100 个 Java 编译错误
  - 执行 `036-frontend/npm run build`，确认当前前端依赖未安装，`vite` 命令不可用
  - 限定范围解码 `036` 后端 Java 与前端 Vue/HTML/JS 源码中的 HTML 实体
  - 将默认后端环境改为 H2 自举，新增 `schema-h2.sql`、`data-h2.sql` 与 `application-postgresql.yml`
  - 修复默认账号 bcrypt 哈希，统一 `admin / 123456`、`user1 / 123456`、`org1 / 123456`
  - 修复 JWT 当前用户识别、请求头默认 `userId=1` 冒充、接口响应/日志密码字段泄露和普通用户创建项目越权
  - 为项目状态/删除、项目进度、捐赠创建补齐登录、角色、归属和金额校验
  - 修复 JPA 懒加载代理序列化、前端项目创建日期格式、Vite 代理目标覆盖与本地占位资源
  - 新增后端自动化测试，`036-backend/mvn test` 通过
  - 完成后端 `8036` 真实启动、登录、当前用户、捐赠、项目创建权限接口抽测
  - 完成前端 `npm install`、`npm run build` 与浏览器联调验证
  - 新增 `036` 单项目检查文档并更新总台账
  - 清理本轮 `8036`、`3036` 验证进程

### 已完成：037 基于SpringBoot的编程学习交流平台
- **Status:** completed
- Actions taken:
  - 读取 `task_plan.md`、`progress.md`、`findings.md` 恢复上一轮上下文
  - 读取 `docs/project-check-tracker.md` 确认总台账最新完成到 `036`
  - 确认下一项目为 `037`
  - 确认 `037-backend` 为 Spring Boot 3.2 + MyBatis XML + Spring Security + JWT 后端
  - 确认 `037-frontend` 为原生微信小程序项目
  - 复核默认配置，确认原始后端强依赖 MySQL / Redis，真实微信 appId/appSecret 未配置
  - 将后端默认环境改为 H2 自举，新增 `application-mysql.yml`、`schema-h2.sql`、`data-h2.sql`
  - 修复 Spring Security 在 `/api` context-path 下公开路由放行错误
  - 增加 `mock_*` / `demo_*` 微信登录 code，保证本地演示可走通
  - 新增课程公开接口，覆盖分页、分类、热门与详情查询，并修复分类分页总数统计
  - 补充后端集成测试，`037-backend/mvn test` 通过
  - 完成后端 `8037` 真实启动，抽测模拟微信登录、当前用户、课程列表、分类、热门与详情接口
  - 补齐小程序 `app.json` 声明的 16 个页面骨架，移除缺失图标和图片引用，新增 `sitemap.json`
  - 完成小程序页面文件与资源引用静态核查
  - 新增 `037` 单项目检查文档并更新总台账
  - 清理本轮 `8037` 验证进程

## Session: 2026-05-01

### 已完成：071 基于SpringBoot和Vue的共享单车系统
- **Status:** completed
- Actions taken:
  - 读取 `docs/project-check-tracker.md`、`progress.md`、`findings.md` 和 `docs/checks` 恢复巡检上下文
  - 确认总台账已完成到 `070`，下一项目为 `071`
  - 接续 `071` 既有未提交改动，未回退任何已有修改
  - 确认 `071-backend` 为 Spring Boot 2.7.18 + MyBatis XML + PageHelper + JWT 后端
  - 确认 `071-frontend` 为 Vue 3 + Vite + Element Plus 前端
  - 将默认环境完善为 H2 自举，保留 MySQL profile
  - 修复 JWT Bearer 兼容、Redis 不可用阻断登录、密码响应泄露、未登录/越权 HTTP 状态和登出失效问题
  - 修复普通用户可越权查看他人骑行订单详情的问题
  - 为骑行开始/结束补充用户、站点、车辆所在站点和还车站点校验
  - 为钱包充值、故障上报和故障处理补充基础输入校验
  - 修复统计趋势 SQL 使用 MySQL `DATE_SUB` 导致默认 H2 看板接口 500 的问题
  - 修复前端代理端口和请求头 Bearer token
  - 新增后端冒烟测试，覆盖登录、权限、站点车辆、骑行、钱包、故障、信用和登出
  - `071-backend/mvn test` 通过，`Tests run: 1, Failures: 0, Errors: 0`
  - `071-frontend/npm run build` 通过，仅保留 Vite CJS API 和 chunk 体积提示
  - 后端 `8071` 真实启动抽测通过，覆盖匿名 401、登录、看板、站点、骑行、还车和登出
  - 前端 `3071` 开发服务代理登录验证通过
  - 新增 `docs/checks/071-bike-sharing-system.md`
  - 新增 `071-backend/README.md` 与 `071-backend/启动说明.txt`
  - 更新 `docs/project-check-tracker.md`
  - 清理本轮 `8071`、`3071` 验证进程

### 已完成：072 基于SpringBoot和Vue的哈尔滨文旅系统
- **Status:** completed
- Actions taken:
  - 读取 `docs/project-check-tracker.md`、`progress.md`、`findings.md` 和 `docs/checks` 恢复巡检上下文
  - 确认总台账已完成到 `071`，下一项目为 `072`
  - 确认 `072-backend` 为 Spring Boot 2.7.18 + MyBatis-Plus + JWT 后端
  - 确认 `072-frontend` 为 Vue 3 + Vite + Element Plus + Pinia 前端
  - 复核原始默认配置，确认后端强依赖 MySQL `harbin_tourism`、Redis 和默认 `8080`
  - 复核前端配置，确认 Vite 端口与代理仍指向 `5173/8080`，路由引用不存在的子目录，API 导出名与页面不一致
  - 将后端默认环境改为 H2 自举，保留 `application-mysql.yml`
  - 将后端编译目标调整为 JDK 17，修复 MySQL 驱动坐标并增加 H2 / Spring Boot Test 依赖
  - 修复 JWT Bearer 兼容、登录响应密码泄露、真实 HTTP `401/403`、登出失效和后台服务端权限缺失
  - 修复订单详情/退款、游记编辑/删除、评价删除等归属校验
  - 为用户资料更新、充值、购票、票种、游记、评价和订单状态补充基础校验
  - 补充票务列表接口，支撑前端门票页查询
  - 修复前端端口、代理、Bearer token、路由导入路径、API 兼容导出和购票参数名
  - 新增后端冒烟测试，覆盖登录、权限、公开列表、购票/退款、收藏、评价、活动报名和登出
  - `072-backend/mvn test` 通过，`Tests run: 1, Failures: 0, Errors: 0`
  - `072-frontend/npm run build` 通过，仅保留 chunk 体积提示
  - 后端 `8072` 真实启动抽测通过，覆盖匿名 401、三账号登录、公开列表、后台权限、购票、订单归属、退款和登出
  - 前端 `3072` 开发服务代理登录验证通过
  - 新增 `docs/checks/072-harbin-tourism-system.md`
  - 新增 `072-backend/README.md` 与 `072-backend/启动说明.txt`
  - 更新 `docs/project-check-tracker.md`
  - 清理本轮 `8072`、`3072` 验证进程

### 已完成：073 基于SpringBoot和Vue的人事管理系统
- **Status:** completed
- Actions taken:
  - 读取 `073-backend/PRD.md`、后端配置、控制器、服务、MyBatis XML 和前端配置恢复项目口径
  - 确认 `073-backend` 为 Spring Boot 2.7.18 + MyBatis XML + PageHelper + JWT 后端
  - 确认 `073-frontend` 为 Vue 3 + Vite + Element Plus + Pinia + Axios + ECharts 前端
  - 复核原始默认配置，确认后端强依赖 MySQL `hrm_system`、Redis 和默认 `8080`
  - 复核前端配置，确认 Vite 默认代理仍指向 `8080`
  - 修复 `UserService` 静态调用 `JwtUtils.generateToken` 导致的编译失败
  - 将后端默认环境改为 H2 自举，保留 `application-mysql.yml`
  - 修复 MySQL 驱动旧坐标、JDK 17 配置、PageHelper 默认方言和 H2 schema/data
  - 修复登录放行路径、JWT Bearer 兼容、密码响应泄露、真实 HTTP `401/403` 和登出失效
  - 补充用户、员工、部门、职位、招聘、简历、培训、公告管理端服务端权限
  - 补充考勤、请假、薪资、合同接口的员工本人归属校验
  - 修复前端端口、代理和请求头 Bearer token
  - 新增后端冒烟测试，覆盖登录、权限、考勤、请假归属和登出
  - `073-backend/mvn test` 通过，`Tests run: 1, Failures: 0, Errors: 0`
  - `073-frontend/npm run build` 通过，仅保留 chunk 体积提示
  - 后端 `8073` 真实启动抽测通过，覆盖匿名 401、三账号登录、员工越权 403、HR 管理接口、员工考勤、请假 employeeId 防篡改、合同越权和登出失效
  - 前端 `3073` 开发服务页面响应和代理登录验证通过
  - 新增 `docs/checks/073-hrm-system.md`
  - 新增 `073-backend/README.md` 与 `073-backend/启动说明.txt`
  - 更新 `docs/project-check-tracker.md`

### 已完成：074 手工艺品销售系统
- **Status:** completed
- Actions taken:
  - 接续 `074` 已有修复现场，保留前序未提交改动，未回退任何已有修改
  - 确认 `074-backend` 为 Spring Boot 2.7.18 + MyBatis-Plus + JWT 后端
  - 确认 `074-frontend` 为 Vue 3 + Vite + Element Plus + Pinia + Axios + ECharts 前端
  - 将后端默认环境改为 H2 自举，保留 `application-mysql.yml`
  - 修复 MySQL 驱动旧坐标、JDK 17 配置、MyBatis-Plus 分页方言和 H2 schema/data
  - 修复 JWT Bearer 兼容、登录响应 password 字段泄露、真实 HTTP `401/403` 和登出失效
  - 补充用户、分类、商品、订单、评价、申诉、公告和看板管理接口的服务端管理员校验
  - 补充商品编辑/删除、订单支付/发货/完成/取消、评价和申诉的本人归属校验
  - 修复前端端口、代理和请求头 Bearer token
  - 新增后端冒烟测试，覆盖登录、权限、下单、支付、发货、完成、评价、收藏、申诉和登出
  - `074-backend/mvn test` 通过，`Tests run: 1, Failures: 0, Errors: 0`
  - `074-frontend/npm install` 通过并生成 `package-lock.json`
  - `074-frontend/npm run build` 通过，仅保留 Vite CJS API 和 chunk 体积提示
  - 后端 `8074` 真实启动抽测通过，覆盖匿名 401、四账号登录、买家看板越权 403、商品分类公告、下单支付、错误卖家发货 403、正确卖家发货、评价收藏申诉和登出失效
  - 前端 `3074` 开发服务页面响应和代理登录验证通过
  - 新增 `docs/checks/074-craft-sales-system.md`
  - 新增 `074-backend/README.md` 与 `074-backend/启动说明.txt`
  - 更新 `docs/project-check-tracker.md`

### 已完成：075 汽车维修预约服务系统
- **Status:** completed
- Actions taken:
  - 确认 `075-backend` 为 Spring Boot 2.7.18 + MyBatis-Plus + JWT 后端
  - 确认 `075-frontend` 为 Vue 3 + Vite + Element Plus + Pinia + Axios + ECharts 前端
  - 复核原始默认配置，确认后端强依赖 MySQL `auto_repair_service`、Redis 和默认 `8080`
  - 复核前端配置，确认 Vite 默认代理仍指向 `8080`
  - 将后端默认环境改为 H2 自举，保留 `application-mysql.yml`
  - 修复 MySQL 驱动旧坐标、JDK 17 配置、MyBatis-Plus 分页方言和 H2 schema/data
  - 修复 JWT Bearer 兼容、登录响应 password 字段泄露、真实 HTTP `401/403` 和登出失效
  - 补充用户、分类、维修项目、预约单、评价、申诉、公告和看板管理端服务端权限
  - 补充维修项目编辑/删除、预约支付/确认/完成/取消、评价和申诉的本人归属校验
  - 修复前端端口、代理和请求头 Bearer token
  - 新增后端冒烟测试，覆盖登录、权限、预约、支付、确认、完成、评价、收藏、申诉和登出
  - `075-backend/mvn test` 通过，`Tests run: 1, Failures: 0, Errors: 0`
  - `075-frontend/npm install` 通过并生成 `package-lock.json`
  - `075-frontend/npm run build` 通过，仅保留 Vite CJS API 和 chunk 体积提示
  - 后端 `8075` 真实启动抽测通过，覆盖匿名 401、四账号登录、车主看板越权 403、维修项目分类公告、预约支付、错误服务方确认 403、正确服务方确认、评价收藏申诉和登出失效
  - 前端 `3075` 开发服务页面响应和代理登录验证通过
  - 新增 `docs/checks/075-auto-repair-appointment-system.md`
  - 新增 `075-backend/README.md` 与 `075-backend/启动说明.txt`
  - 更新 `docs/project-check-tracker.md`

### 已完成：076 企业信息管理系统
- **Status:** completed
- Actions taken:
  - 接续 `076` 已有修复现场，保留前序未提交改动，未回退任何已有修改
  - 确认 `076-backend` 为 Spring Boot 2.7.18 + MyBatis-Plus + JWT 后端
  - 确认 `076-frontend` 为 Vue 3 + Vite + Element Plus + Pinia + Axios + ECharts 前端
  - 将后端默认环境改为 H2 自举，保留 `application-mysql.yml`
  - 修复 MySQL 驱动旧坐标、JDK 17 配置、MyBatis-Plus 分页方言和 H2 schema/data
  - 修复 JWT Bearer 兼容、登录响应 password 字段泄露、真实 HTTP `401/403` 和登出失效
  - 补充用户、分类、企业信息、处理单、评价、申诉、公告和看板管理端服务端权限
  - 补充企业信息编辑/删除、处理单支付/确认/完成/取消、评价和申诉的本人归属校验
  - 修复前端端口、代理和请求头 Bearer token
  - 新增后端冒烟测试，覆盖登录、权限、处理单、支付、确认、完成、评价、收藏、申诉和登出
  - `076-backend/mvn test` 通过，`Tests run: 1, Failures: 0, Errors: 0`
  - `076-frontend/npm install` 通过并生成 `package-lock.json`
  - `076-frontend/npm run build` 通过，仅保留 Vite CJS API 和 chunk 体积提示
  - 后端 `8076` 真实启动抽测通过，覆盖匿名 401、四账号登录、企业用户看板越权 403、企业信息分类公告、处理单支付、错误服务方确认 403、正确服务方确认、评价收藏申诉和登出失效
  - 前端 `3076` 开发服务页面响应和代理登录验证通过
  - 新增 `docs/checks/076-enterprise-info-management-system.md`
  - 新增 `076-backend/README.md` 与 `076-backend/启动说明.txt`
  - 更新 `docs/project-check-tracker.md`

### 已完成：077 基于Vue的MES生产制造执行系统
- **Status:** completed
- Actions taken:
  - 确认 `077-backend` 为 Spring Boot 2.7.18 + MyBatis-Plus + JWT 后端
  - 确认 `077-frontend` 为 Vue 3 + Vite + Element Plus + Pinia + Axios + ECharts 前端
  - 复核 PRD 和 SQL，确认项目口径为 MES 生产制造执行系统
  - 复核原始默认配置，确认后端强依赖 MySQL `mes_execution_system`、Redis 和默认 `8080`
  - 复核前端配置，确认 Vite 默认代理仍指向 `8080`
  - 将后端默认环境改为 H2 自举，保留 `application-mysql.yml`
  - 修复 MySQL 驱动旧坐标、JDK 17 配置、MyBatis-Plus 分页方言和 H2 schema/data
  - 修复 JWT Bearer 兼容、登录响应 password 字段泄露、真实 HTTP `401/403` 和登出失效
  - 补充用户、分类、生产任务、执行工单、评价、申诉、公告和看板管理端服务端权限
  - 补充生产任务编辑/删除、执行工单支付/确认/完成/取消、评价和申诉的本人归属校验
  - 修复前端端口、代理和请求头 Bearer token
  - 新增后端冒烟测试，覆盖登录、权限、执行工单、支付、确认、完成、评价、收藏、申诉和登出
  - `077-backend/mvn test` 通过，`Tests run: 1, Failures: 0, Errors: 0`
  - `077-frontend/npm install` 通过并生成 `package-lock.json`
  - `077-frontend/npm run build` 通过，仅保留 Vite CJS API 和 chunk 体积提示
  - 后端 `8077` 真实启动抽测通过，覆盖匿名 401、四账号登录、计划员看板越权 403、生产任务分类公告、执行工单支付、错误生产主管确认 403、正确生产主管确认、评价收藏申诉和登出失效
  - 前端 `3077` 开发服务页面响应和代理登录验证通过
  - 新增 `docs/checks/077-mes-execution-system.md`
  - 新增 `077-backend/README.md` 与 `077-backend/启动说明.txt`
  - 更新 `docs/project-check-tracker.md`

### 已完成：078 网上团购系统
- **Status:** completed
- Actions taken:
  - 确认 `078-backend` 为 Spring Boot 2.7.18 + MyBatis-Plus + JWT 后端
  - 确认 `078-frontend` 为 Vue 3 + Vite + Element Plus + Pinia + Axios + ECharts 前端
  - 复核 PRD 和 SQL，确认项目口径为网上团购系统
  - 复核原始默认配置，确认后端强依赖 MySQL `group_buy`、Redis 和默认 `8080`
  - 复核前端配置，确认 Vite 默认代理仍指向 `8080`
  - 将后端默认环境改为 H2 自举，保留 `application-mysql.yml`
  - 修复 MySQL 驱动旧坐标、JDK 17 配置、MyBatis-Plus 分页方言和 H2 schema/data
  - 修复 JWT Bearer 兼容、登录响应 password 字段泄露、真实 HTTP `401/403` 和登出失效
  - 补充用户、商家、分类、公告、评价和统计管理端服务端权限
  - 补充商品、团购活动、地址、购物车、订单和拼团订单的本人/商家归属校验
  - 补齐评价删除、公告状态切换、首页公告别名和个人中心 API 断链
  - 修复首页公开团购列表调用，避免游客首页请求受保护接口
  - 修复前端端口、代理和请求头 Bearer token
  - 新增后端冒烟测试，覆盖登录、权限、购物车、订单、支付、发货、收货、评价和登出
  - `078-backend/mvn test` 通过，`Tests run: 1, Failures: 0, Errors: 0`
  - `078-frontend/npm install` 通过并生成 `package-lock.json`
  - `078-frontend/npm run build` 通过，仅保留 Vite CJS API 和 chunk 体积提示
  - 后端 `8078` 真实启动抽测通过，覆盖匿名 401、五账号登录、普通用户统计越权 403、公开分类/团购/商品/公告、购物车下单、错误用户支付 403、本人支付、错误商家发货 403、所属商家发货、错误用户收货 403、本人收货、评价和登出失效
  - 前端 `3078` 开发服务页面响应和代理登录验证通过
  - 新增 `docs/checks/078-groupbuy-system.md`
  - 新增 `078-backend/README.md` 与 `078-backend/启动说明.txt`
  - 更新 `docs/project-check-tracker.md`

## Current State

- `083`：已完成并回填文档
- 下一项目：`084`

### 已完成：083 基于B/S的老年人体检管理系统
- **Status:** completed
- Actions taken:
  - 确认 `083-backend` 为 Spring Boot 2.7.14 + MyBatis-Plus + JWT 后端，`083-frontend` 为 Vue 3 + Vite + Element Plus + Pinia 前端
  - 将后端默认端口调整为 `8083`，默认环境改为 H2 自举，保留正式 `application-mysql.yml`
  - 根据用户确认的 MySQL `root / 1234` 新增 `application-mysql-verify.yml`，使用 `eldercare_083_verify` 临时库做非破坏性 MySQL 验证
  - 修复 MySQL 驱动坐标、JDK 17 和 JJWT 缺 JAXB 的运行问题
  - 修复 JWT Bearer 兼容、登录响应 password 泄露、真实 HTTP `401/403` 和登出失效问题
  - 补充管理员、医生、护士、前台角色边界
  - 修复医生录入体检结果时 `elderId` / `doctorId` 可伪造的问题，改为以预约单和当前登录医生绑定
  - 修复前台新增预约时 `createBy` 可伪造的问题，改为以当前登录前台绑定
  - 修复前端端口、代理和请求头 Bearer token
  - 新增后端冒烟测试，覆盖登录、权限、结果录入和登出
  - `083-backend/mvn test` 通过，`Tests run: 1, Failures: 0, Errors: 0`
  - `083-frontend/npm run build` 通过，仅保留 Vite chunk 体积提示
  - 后端 `18084` 使用 MySQL `root / 1234` 的 `mysql-verify` profile 真实启动抽测通过，覆盖匿名 `401`、公开公告、四角色登录、统计看板、前台录入结果越权、医生结果归属、预警处理权限、预约 `createBy` 防伪造和登出失效
  - MySQL 临时库 `eldercare_083_verify` 已在验证后删除
  - 前端 `3083` 开发服务页面响应和代理登录验证通过
  - 新增 `docs/checks/083-eldercare-checkup-system.md`
  - 新增 `083-backend/README.md` 与 `083-backend/启动说明.txt`
  - 更新 `docs/project-check-tracker.md`

### 已完成：084 教学资料管理系统
- **Status:** completed
- Actions taken:
  - 确认 `084-backend` 为 Spring Boot 2.7.18 + MyBatis + PageHelper + JWT 后端，`084-frontend` 为 Vue 3 + Vite + Element Plus + Pinia 前端
  - 将后端默认端口调整为 `8084`，默认环境改为 H2 自举，保留正式 `application-mysql.yml`
  - 根据用户确认的 MySQL `root / 1234` 新增 `application-mysql-verify.yml`，使用 `teachres_084_verify` 临时库做非破坏性 MySQL 验证
  - 修复 MySQL 驱动坐标、JDK 17 和 JJWT 缺 JAXB 的运行问题
  - 修复 JWT Bearer 兼容、登录响应 password 泄露、真实 HTTP `401/403` 和登出失效问题
  - 补充管理员、教师、学生角色边界，修复统计、审核、分类、公告和资料管理接口权限
  - 修复教师新增资料 `uploaderId` 可伪造、教师资料列表越权枚举和资料更新/删除/发布归属校验不足
  - 修复收藏学习清单删除缺少本人校验的问题
  - 修复统计 SQL 中 `month`、`value` 保留字别名导致 H2 看板接口 500 的问题
  - 修复前端端口、代理和请求头 Bearer token
  - 新增后端冒烟测试，覆盖登录、权限、资料上传归属、审核、下载收藏和登出
  - `084-backend/mvn test` 通过，`Tests run: 1, Failures: 0, Errors: 0`
  - `084-frontend/npm run build` 通过，仅保留 Vite chunk 体积提示
  - 后端 `18085` 使用 MySQL `root / 1234` 的 `mysql-verify` profile 真实启动抽测通过，覆盖匿名 `401`、公开资料/公告、三角色登录、管理员统计、学生统计越权、教师新增资料 uploaderId 防伪造、学生审核越权、管理员审核、下载收藏和登出失效
  - MySQL 临时库 `teachres_084_verify` 已在验证后删除
  - 前端 `3084` 开发服务页面响应和代理登录验证通过
  - 新增 `docs/checks/084-teachres-management-system.md`
  - 新增 `084-backend/README.md` 与 `084-backend/启动说明.txt`
  - 更新 `docs/project-check-tracker.md`

## Current State

- `084`：已完成并回填文档
- 下一项目：`085`

### 已完成：085 数学课程评价系统
- **Status:** completed
- Actions taken:
  - 确认 `085-backend` 为 Spring Boot 2.7.18 + MyBatis XML + PageHelper + JWT 后端，`085-frontend` 为 Vue 3 + Vite + Element Plus + Pinia 前端
  - 复核默认配置，确认默认端口为 `8085`，默认环境已切到 H2，自带 `application-mysql.yml`、`application-mysql-verify.yml`、`schema-h2.sql` 和 `data-h2.sql`
  - 确认并复测 JWT Bearer、登录响应脱敏、真实 HTTP `401/403`、登出失效和运行态 token 校验
  - 确认管理员、教师、学生角色边界已生效
  - 复测教师新增课程 `teacherId` 防伪造、学生访问统计越权、学生访问他人评价详情/任务记录越权、教师查看授课任务汇总和学生评价提交流程
  - `085-backend/mvn test` 通过，`Tests run: 1, Failures: 0, Errors: 0`
  - `085-frontend/npm run build` 通过，仅保留 Vite chunk 体积提示
  - 后端 `18086` 使用默认 H2 环境真实启动抽测通过，覆盖匿名 `401`、公开公告、登录脱敏、统计权限、课程归属绑定、学生提交评价和登出失效
  - 后端 `18087` 使用 MySQL `root / 1234` 的 `mysql-verify` profile 真实启动抽测通过，覆盖匿名 `401`、公开公告、三角色登录、管理员统计、学生统计越权、任务 `creatorId` 自动绑定、评价详情/任务记录越权、教师任务汇总、学生提交评价和登出失效
  - 前端 `3085` 开发服务页面响应和代理登录验证通过
  - 新增 `docs/checks/085-math-course-evaluation-system.md`
  - 更新 `docs/project-check-tracker.md`

## Current State

- `085`：已完成并回填文档
- 下一项目：`086`

### 已完成：082 公考学习平台
- **Status:** completed
- Actions taken:
  - 确认 `082-backend` 为 Spring Boot 2.7.14 + MyBatis-Plus + JWT 后端，`082-frontend` 为 Vue 3 + Vite + Element Plus + Pinia 前端
  - 将后端默认端口调整为 `8082`，默认环境改为 H2 自举，保留正式 `application-mysql.yml`
  - 根据既有巡检口径新增 `application-mysql-verify.yml`，使用 `gongkao_082_verify` 临时库做非破坏性 MySQL 验证
  - 修复 MySQL 驱动坐标、JDK 17 和 JJWT 缺 JAXB 的运行问题
  - 修复 JWT Bearer 兼容、登录响应 password 泄露、真实 HTTP `401/403` 和登出失效问题
  - 补充管理员、讲师、学员角色边界，修复课程 teacherId 和学习计划/考试记录 userId 可伪造问题
  - 为学科、公告公开接口补充 Redis 不可用时的数据库回退，避免默认演示环境被 Redis 阻断
  - 修复前端端口、代理、请求头 Bearer token 和登出 API
  - 新增后端冒烟测试，覆盖登录、权限、学习计划归属、课程归属和登出
  - `082-backend/mvn test` 通过，`Tests run: 1, Failures: 0, Errors: 0`
  - `082-frontend/npm install` 通过并生成 `package-lock.json`
  - `082-frontend/npm run build` 通过，仅保留 Vite chunk 体积提示
  - 后端 `18082` 使用默认 H2 环境真实启动抽测通过，覆盖匿名 `401`、管理员登录、学员统计越权、讲师新增课程和登出失效
  - 后端 `18083` 使用 MySQL `root / 1234` 的 `mysql-verify` profile 真实启动抽测通过，覆盖匿名 `401`、公开公告、管理员登录脱敏、学员越权 `403`、学习计划 `userId` 防伪造、讲师课程 `teacherId` 防伪造、管理员统计和登出失效
  - MySQL 临时库 `gongkao_082_verify` 已在验证后删除
  - 前端 `3082` 开发服务页面响应和代理登录验证通过
  - 新增 `docs/checks/082-gongkao-learning-platform.md`
  - 新增 `082-backend/README.md` 与 `082-backend/启动说明.txt`
  - 更新 `docs/project-check-tracker.md`

### 已完成：079 计算机学院校友网
- **Status:** completed
- Actions taken:
  - 确认 `079-backend` 为 Spring Boot 2.7.18 + MyBatis-Plus + JWT 后端，`079-frontend` 为 Vue 3 + Vite + Element Plus + Pinia 前端
  - 将后端默认端口调整为 `8079`，默认环境改为 H2 自举，保留正式 `application-mysql.yml`
  - 根据用户确认的 MySQL `root / 1234` 新增 `application-mysql-verify.yml`，使用 `alumni_079_verify` 临时库做非破坏性 MySQL 验证
  - 修复 MySQL 驱动坐标、JDK 17、MyBatis-Plus 分页方言和 H2 `YEAR` 保留字兼容
  - 修复 JWT Bearer 兼容、登录响应 password 泄露、真实 HTTP `401/403`、登出失效和公开 GET 通配符误放行写操作问题
  - 补充用户、届次、班级、轮播图、新闻、日志、捐赠、统计等后台接口服务端管理员校验
  - 补充校友资料、活动、企业、岗位、论坛帖子/回复等资源的本人/组织者/管理员归属校验
  - 修复前端端口、代理和请求头 Bearer token
  - 新增后端冒烟测试，覆盖登录、权限、公开列表、活动报名、捐赠、企业岗位、论坛和登出
  - `079-backend/mvn test` 通过，`Tests run: 1, Failures: 0, Errors: 0`
  - `079-frontend/npm run build` 通过，仅保留 Vite CJS API 和 chunk 体积提示
  - 后端 `8079` 使用 MySQL `mysql-verify` profile 真实启动抽测通过，覆盖匿名 `401`、四账号登录、待审核账号 `400`、普通校友后台越权 `403`、管理员统计、公开列表、活动报名、捐赠确认、企业岗位归属、论坛归属和登出失效
  - 前端 `3079` 开发服务页面响应和代理登录验证通过
  - 新增 `docs/checks/079-alumni-network-system.md`
  - 新增 `079-backend/README.md` 与 `079-backend/启动说明.txt`
  - 更新 `docs/project-check-tracker.md`

### 已完成：080 基于SpringBoot和Vue的贫困地区儿童资助网站
- **Status:** completed
- Actions taken:
  - 确认 `080-backend` 为 Spring Boot 2.7.14 + MyBatis-Plus + JWT 后端，`080-frontend` 为 Vue 3 + Vite + Element Plus + Pinia 前端
  - 将后端默认环境改为 H2 自举，保留正式 `application-mysql.yml`
  - 根据用户确认的 MySQL `root / 1234` 新增 `application-mysql-verify.yml`，使用 `charity_080_verify` 临时库做非破坏性 MySQL 验证
  - 修复 MySQL 驱动坐标、JDK 17 和 JJWT 缺 JAXB 的运行问题
  - 修复 JWT Bearer 兼容、登录响应 password 泄露、真实 HTTP `401/403` 和登出失效问题
  - 补充用户、统计、资金、项目、公告、申请、捐赠确认等管理型接口的管理员校验
  - 补充捐赠人、志愿者、儿童、申请、捐赠、反馈、成长记录和资助关系权限边界
  - 修复捐赠创建可伪造 `donorId` 的问题，改为以当前登录捐赠人绑定
  - 修复前端端口、代理、请求头 Bearer token 和登出 API
  - 新增后端冒烟测试，覆盖登录、权限、儿童新增、捐赠、申请和登出
  - `080-backend/mvn test` 通过，`Tests run: 1, Failures: 0, Errors: 0`
  - `080-frontend/npm install` 通过并生成 `package-lock.json`
  - `080-frontend/npm run build` 通过，仅保留 Vite chunk 体积提示
  - 后端 `8080` 使用 MySQL `mysql-verify` profile 真实启动抽测通过，覆盖匿名 `401`、管理员登录、捐赠人统计越权、管理员统计、志愿者新增儿童、捐赠 donorId 防伪造、捐赠确认权限和登出失效
  - 前端 `3080` 开发服务页面响应和代理登录验证通过
  - 新增 `docs/checks/080-charity-child-sponsorship-system.md`
  - 新增 `080-backend/README.md` 与 `080-backend/启动说明.txt`
  - 更新 `docs/project-check-tracker.md`

### 已完成：081 基于SpringBoot+Vue+uniapp的电器维修系统小程序
- **Status:** completed
- Actions taken:
  - 确认 `081-backend` 为 Spring Boot 2.7.14 + MyBatis-Plus + JWT 后端，`081-frontend` 为 Vue 3 管理端，`081-miniapp` 为 uni-app 小程序端
  - 将后端默认环境改为 H2 自举，保留正式 `application-mysql.yml`
  - 根据用户确认的 MySQL `root / 1234` 新增 `application-mysql-verify.yml`，使用 `repair_081_verify` 临时库做非破坏性 MySQL 验证
  - 修复 MySQL 驱动坐标、JDK 17 和 JJWT 缺 JAXB 的运行问题
  - 修复 JWT Bearer 兼容、登录响应 password 泄露、真实 HTTP `401/403` 和登出失效问题
  - 补充用户、分类、技师、工单、备件、公告、评价、统计等管理型接口管理员校验
  - 补充客户工单、技师工单、工单进度和评价的本人/分配技师归属校验
  - 修复客户创建工单可伪造 `userId` 的问题，改为以当前登录用户绑定
  - 修复管理端端口、代理、请求头 Bearer token、上传预览 URL
  - 修复小程序请求头 Bearer token 和图片 URL 口径
  - 新增后端冒烟测试，覆盖登录、权限、报修、派单、技师处理、支付评价和登出
  - `081-backend/mvn test` 通过，`Tests run: 1, Failures: 0, Errors: 0`
  - `081-frontend/npm install` 通过并生成 `package-lock.json`
  - `081-frontend/npm run build` 通过，仅保留 Vite chunk 体积提示
  - 后端 `8080` 使用 MySQL `mysql-verify` profile 真实启动抽测通过，覆盖匿名 `401`、公开分类、管理员登录、客户统计越权、管理员统计、客户报修、管理员派单、技师处理、支付评价和登出失效
  - 管理端 `3081` 开发服务页面响应和代理登录验证通过
  - 新增 `docs/checks/081-appliance-repair-system.md`
  - 新增 `081-backend/README.md` 与 `081-backend/启动说明.txt`
  - 更新 `docs/project-check-tracker.md`

### 已完成：086 高清壁纸社区网站
- **Status:** completed
- Actions taken:
  - 确认 `086-backend` 为 Spring Boot 2.7.18 + MyBatis-Plus + JWT 后端，`086-frontend` 为 Vue 3 + Vite + Element Plus + Pinia 前端
  - 将后端默认环境改为 H2 自举，新增 `application-mysql.yml`、`application-mysql-verify.yml`、`schema-h2.sql`、`data-h2.sql`
  - 修复 MySQL 驱动坐标、JDK 17、H2 和 JAXB 运行兼容
  - 新增 `RuntimeStoreService`，去掉默认演示链路对 Redis token 的强依赖
  - 修复 JWT Bearer 兼容、登录响应 password 泄露、真实 HTTP `401/403/404` 和登出失效问题
  - 修复壁纸越权删除状态码，确认壁纸新增时 `uploaderId` 强制绑定当前登录用户
  - 修复前端端口、代理和 Bearer token 请求头
  - `086-backend/mvn test` 通过，当前为 `No tests to run`
  - `086-frontend/npm.cmd install` 通过并生成 `package-lock.json`
  - `086-frontend/npm.cmd run build` 通过，仅保留 Vite chunk 体积提示
  - 后端 `18088` 默认 H2 环境真实启动抽测通过，覆盖匿名 `401`、公开公告/壁纸 `200`、管理员/用户登录脱敏、普通用户统计越权 `403`、壁纸 `uploaderId` 防伪造、他人删除越权 `403`、管理员审核上架、收藏详情 `hasFavorite=true`、下载和登出失效
  - 后端 `18089` 使用 MySQL `root / 1234` 的 `mysql-verify` profile 真实启动抽测通过，覆盖匿名 `401`、公开公告、管理员统计 `200`、壁纸 `uploaderId` 防伪造、越权删除 `403`、审核上架、收藏详情与登出失效；验证库为 `wallpaper_086_verify`
  - 前端 `3086` 页面响应和代理登录验证通过：首页 `200`、代理登录 `200`、角色 `admin`、无 `password` 字段
  - 新增 `docs/checks/086-wallpaper-community-system.md`
  - 更新 `docs/project-check-tracker.md`
  - 下一项目为 `087`

### 已完成：087 课程管理系统
- **Status:** completed
- Actions taken:
  - 确认 `087-backend` 为 Spring Boot 2.7.18 + MyBatis + PageHelper + JWT 后端，`087-frontend` 为 Vue 3 + Vite + Element Plus + Pinia 前端
  - 将后端默认环境改为 H2 自举，新增 `application-mysql.yml`、`application-mysql-verify.yml`、`schema-h2.sql`、`data-h2.sql`
  - 修复 MySQL 驱动坐标、JDK 17、H2 和 JAXB 运行兼容
  - 新增 `RuntimeStoreService`，去掉默认演示链路对 Redis token 的强依赖
  - 修复 JWT Bearer 兼容、登录响应 password 泄露、真实 HTTP `401/403` 和登出失效问题
  - 修复课程资源 `teacherId` 防伪造、学生更新教师资源越权、成绩录入权限和选课/评教归属链路
  - 修复前端端口、代理和 Bearer token 请求头
  - 新增 `CourseManagementApplicationSmokeTest`，`087-backend/mvn test` 通过
  - `087-frontend/npm.cmd install` 通过并生成 `package-lock.json`
  - `087-frontend/npm.cmd run build` 通过，仅保留 Vite chunk 体积提示
  - 后端 `18090` 默认 H2 环境真实启动抽测通过，覆盖匿名 `401`、公开公告 `200`、教师统计 `200`、学生统计越权 `403`、课程资源 `teacherId` 防伪造、选课、成绩、评教和登出失效
  - 后端 `18091` 使用 MySQL `root / 1234` 的 `mysql-verify` profile 真实启动抽测通过，覆盖匿名 `401`、公开公告 `200`、四账号登录脱敏、教师统计 `200`、学生统计越权 `403`、课程资源 `teacherId` 防伪造、学生更新资源越权 `403`、选课、成绩、评教和登出失效；验证库为 `course_manage_087_verify`
  - 前端 `3087` 页面响应和代理登录验证通过：首页 `200`、代理登录 `200`、角色 `admin`、无 `password` 字段
  - 新增 `docs/checks/087-course-management-system.md`
  - 更新 `docs/project-check-tracker.md`
  - 下一项目为 `088`

### 已完成：088 孩童收养信息管理系统
- **Status:** completed
- Actions taken:
  - 确认 `088-backend` 为 Spring Boot 2.7.18 + MyBatis-Plus + JWT 后端，`088-frontend` 为 Vue 3 + Vite + Element Plus + Pinia 前端
  - 将后端默认环境改为 H2 自举，新增 `application-mysql.yml`、`application-mysql-verify.yml`、`schema-h2.sql`、`data-h2.sql`
  - 修复 MySQL 驱动坐标、JDK 17、H2 和 JAXB 运行兼容
  - 修复 `AgreementService` 缺失 `Wrappers` 导入导致的编译失败
  - 新增 `RuntimeStoreService`，去掉默认演示链路对 Redis token 的强依赖
  - 修复 JWT Bearer 兼容、登录响应 password 泄露、真实 HTTP `401/403/404` 和登出失效问题
  - 修复申请人/审核员角色边界，禁止审核员伪装申请人发起收养申请
  - 修复申请材料越权追加问题，材料列表与新增均按申请归属校验
  - 修复前端端口、代理和 Bearer token 请求头
  - 新增 `ChildAdoptionApplicationSmokeTest`，`088-backend/mvn test` 通过
  - `088-frontend/npm.cmd install` 通过并生成 `package-lock.json`
  - `088-frontend/npm.cmd run build` 通过，仅保留 Vite chunk 体积提示
  - 后端 `18093` 默认 H2 环境真实启动抽测通过，覆盖匿名 `401`、公开儿童列表 `200`、登录脱敏、管理员统计 `200`、审核员越权申请 `403` 和跨申请人材料追加越权 `403`
  - 后端 `18094` 使用 MySQL `root / 1234` 的 `mysql-verify` profile 真实启动抽测通过，覆盖匿名 `401`、公开儿童列表 `200`、新申请人注册、登录脱敏、资料更新、申请创建、审核、跨申请人材料越权 `403`、本人材料新增 `200` 和登出失效；验证库为 `adoption_088_verify`
  - 前端 `3088` 页面响应和代理登录验证通过：首页 `200`、代理登录 `200`、角色 `admin`、无 `password` 字段
  - 新增 `docs/checks/088-child-adoption-management-system.md`
  - 更新 `docs/project-check-tracker.md`
  - 下一项目为 `089`

### 已完成：089 铁路购票平台
- **Status:** completed
- Actions taken:
  - 确认 `089-backend` 为 Spring Boot 2.7.18 + MyBatis-Plus + JWT 后端，`089-frontend` 为 Vue 3 + Vite + Element Plus + Pinia 前端
  - 将后端默认环境改为 H2 自举，新增 `application-mysql.yml`、`application-mysql-verify.yml`、`schema-h2.sql`、`data-h2.sql`
  - 修复 MySQL 驱动坐标、JDK 17、H2 保留字和测试依赖兼容
  - 新增 `RuntimeStoreService`，去掉默认演示链路对 Redis token 和 Redis 座位锁的强依赖
  - 修复 JWT Bearer 兼容、登录响应 password 泄露、真实 HTTP `401/403/404` 和登出失效问题
  - 修复公开班次、公告和座位查询路由误拦截问题
  - 修复普通用户访问统计接口、他人订单详情和车票核销接口的越权边界
  - 修复前端端口、代理和 Bearer token 请求头
  - 新增 `RailwayTicketApplicationSmokeTest`，`089-backend/mvn test` 通过
  - `089-frontend/npm.cmd run build` 通过，仅保留 Vite chunk 体积提示
  - 后端 `18095` 默认 H2 环境真实启动抽测通过，覆盖匿名 `401`、公开班次/公告、登录脱敏、统计越权 `403`、锁座下单支付、跨用户订单越权 `403`、普通用户核销越权 `403` 和登出失效
  - 后端 `18096` 使用 MySQL `root / 1234` 的 `mysql-verify` profile 真实启动抽测通过，覆盖匿名 `401`、公开班次/公告、新乘客注册、登录脱敏、统计越权 `403`、锁座下单支付、跨用户订单越权 `403`、普通用户核销越权 `403` 和登出失效；验证库为 `railway_ticket_089_verify`
  - 前端 `3089` 页面响应和代理登录验证通过：首页 `200`、代理登录 `200`、角色 `ADMIN`、无 `password` 字段
  - 新增 `docs/checks/089-railway-ticket-platform.md`
  - 更新 `docs/project-check-tracker.md`
  - 下一项目为 `090`

### 已完成：090 戏曲文化苑系统
- **Status:** completed
- Actions taken:
  - 确认 `090-backend` 为 Spring Boot 2.7.18 + MyBatis + PageHelper + JWT 后端，`090-frontend` 为 Vue 3 + Vite + Element Plus + Pinia 前端
  - 将后端默认环境改为 H2 自举，新增 `application-mysql.yml`、`application-mysql-verify.yml`、`schema-h2.sql`、`data-h2.sql`
  - 修复 MySQL 驱动坐标、JDK 17、H2 和 JAXB 运行兼容
  - 新增 `RuntimeStoreService`，去掉默认演示链路对 Redis token 的强依赖
  - 修复 JWT Bearer 兼容、登录响应 password 泄露、真实 HTTP `401/403/404` 和登出失效问题
  - 修复公开剧目/排期分页误拦截问题
  - 修复会员预约权限、资源更新删除归属、艺术家评分归属和赏析互动预约前置约束
  - 修复前端端口、代理和 Bearer token 请求头
  - 新增 `OperaCultureApplicationSmokeTest`，`090-backend/mvn test` 通过
  - `090-frontend/npm.cmd install` 通过并生成 `package-lock.json`
  - `090-frontend/npm.cmd run build` 通过，仅保留 Vite chunk 体积提示
  - 后端 `18097` 默认 H2 环境真实启动抽测通过，覆盖匿名 `401`、公开剧目/公告、登录脱敏、统计越权 `403`、艺术家预约越权 `403`、会员预约、资源管理员更新、会员删除越权 `403`、评分权限、赏析互动权限和登出失效
  - 后端 `18098` 使用 MySQL `root / 1234` 的 `mysql-verify` profile 真实启动抽测通过，覆盖匿名 `401`、公开剧目/公告、登录脱敏、统计越权 `403`、艺术家预约越权 `403`、会员预约、资源管理员更新、会员删除越权 `403`、评分权限、赏析互动权限和登出失效；验证库为 `opera_culture_090_verify`
  - 前端 `3090` 页面响应和代理登录验证通过：首页 `200`、代理登录 `200`、角色 `admin`、无 `password` 字段
  - 新增 `docs/checks/090-opera-culture-platform.md`
  - 更新 `docs/project-check-tracker.md`
  - 下一项目为 `091`

### 已完成：091 电影院会员管理系统
- **Status:** completed
- Actions taken:
  - 确认 `091-backend` 为 Spring Boot 2.7.18 + MyBatis-Plus + JWT 后端，`091-frontend` 为 Vue 3 + Vite + Element Plus + Pinia 前端
  - 将后端默认环境改为 H2 自举，新增 `application-mysql.yml`、`application-mysql-verify.yml`、`schema-h2.sql`、`data-h2.sql`
  - 修复 MySQL 驱动坐标、JDK 17、H2 保留字和测试依赖兼容
  - 新增 `RuntimeStoreService`，去掉默认演示链路对 Redis token 和座位锁的强依赖
  - 修复 JWT Bearer 兼容、登录响应 password 泄露、真实 HTTP `401/403` 和登出失效问题
  - 修复会员/员工/管理员角色边界，限制下单、支付、充值、领券、评论和我的票券仅会员可操作
  - 修复评论必须绑定本人已购订单且影片匹配的前置约束
  - 修复前端端口、代理和 Bearer token 请求头
  - 新增 `CinemaMemberApplicationSmokeTest`，`091-backend/mvn test` 通过
  - `091-frontend/npm.cmd install` 通过并生成 `package-lock.json`
  - `091-frontend/npm.cmd run build` 通过，仅保留 Vite chunk 体积提示
  - 后端 `18099` 默认 H2 环境真实启动抽测通过，覆盖匿名 `401`、公开影片/场次、登录脱敏、统计越权 `403`、会员锁座下单支付、员工下单越权 `403`、跨用户订单/票券越权 `403`、评论前置 `403`、普通会员核销越权 `403`、员工核销 `200` 和登出失效
  - 后端 `18100` 使用 MySQL `root / 1234` 的 `mysql-verify` profile 真实启动抽测通过，覆盖匿名 `401`、公开影片/场次、登录脱敏、统计越权 `403`、会员锁座下单支付、员工下单越权 `403`、跨用户订单/票券越权 `403`、评论前置 `403`、普通会员核销越权 `403`、员工核销 `200` 和登出失效；验证库为 `cinema_member_system_091_verify`
  - 前端 `3091` 页面响应和代理登录验证通过：首页 `200`、代理登录 `200`、角色 `ADMIN`、无 `password` 字段
  - 新增 `docs/checks/091-cinema-member-system.md`
  - 更新 `docs/project-check-tracker.md`
  - 下一项目为 `092`

### 已完成：092 蓝天幼儿园管理系统
- **Status:** completed
- Actions taken:
  - 确认 `092-backend` 为 Spring Boot 2.7.18 + MyBatis + PageHelper + JWT 后端，`092-frontend` 为 Vue 3 + Vite + Element Plus + Axios 前端
  - 将后端默认环境改为 H2 自举，新增 `application-mysql.yml`、`application-mysql-verify.yml`、`schema-h2.sql`、`data-h2.sql`
  - 修复 MySQL 驱动坐标、JDK 17、H2 和 JAXB 运行兼容
  - 新增 `RuntimeStoreService`，去掉默认演示链路对 Redis token 的强依赖
  - 修复 JWT Bearer 兼容、登录响应 password 泄露、真实 HTTP `401/403/500` 和登出失效问题
  - 修复家长只能查看本人孩子及其课表，限制家长伪造他人反馈
  - 修复教师跨班修改考勤、晨检、反馈回复和食谱删除的越权问题
  - 修复前端端口、代理和 Bearer token 请求头
  - 新增 `KindergartenApplicationSmokeTest`，`092-backend/mvn.cmd test` 通过
  - `092-frontend/npm.cmd install` 通过并生成 `package-lock.json`
  - `092-frontend/npm.cmd run build` 通过，仅保留 Vite chunk 体积提示
  - 后端 `18101` 默认 H2 环境真实启动抽测通过，覆盖匿名 `401`、公开公告 `200`、登录脱敏、家长统计越权 `403`、家长本人幼儿档案/课表收口、教师跨班考勤/晨检越权 `403`、本人反馈与回复 `200`、食谱删除越权 `403` 和登出失效
  - 后端 `18102` 使用 MySQL `root / 1234` 的 `mysql-verify` profile 真实启动抽测通过，覆盖匿名 `401`、公开公告 `200`、登录脱敏、家长统计越权 `403`、家长本人幼儿档案/课表收口、教师跨班考勤/晨检越权 `403`、本人反馈与回复 `200`、食谱删除越权 `403` 和登出失效；验证库为 `kindergarten_092_verify`
  - 前端 `3092` 页面响应和代理登录验证通过：首页 `200`、代理登录 `200`、角色 `admin`、无 `password` 字段
  - 新增 `docs/checks/092-kindergarten-management-system.md`
  - 更新 `docs/project-check-tracker.md`
  - 下一项目为 `093`

### 已完成：093 自助售货管理系统
- **Status:** completed
- Actions taken:
  - 确认 `093-backend` 为 Spring Boot 2.7.18 + MyBatis-Plus + JWT 后端，`093-frontend` 为 Vue 3 + Vite + Element Plus 前端
  - 将后端默认环境改为 H2 自举，新增 `application-mysql.yml`、`application-mysql-verify.yml`、`schema-h2.sql`、`data-h2.sql`、`schema-mysql.sql`、`data-mysql.sql`
  - 修复 MySQL 驱动坐标、JDK 17、H2 和 JAXB 运行兼容
  - 在启动类补充 `@MapperScan`，修复 Mapper 未扫描导致的默认启动失败
  - 新增 `RuntimeStoreService`，去掉默认演示链路对 Redis token 的强依赖
  - 修复 JWT Bearer 兼容、登录响应 password 泄露、真实 HTTP `401/403/404/500` 和登出失效问题
  - 修复顾客/补货员/管理员角色边界，限制下单、支付、充值、补货处理与统计访问
  - 修复订单支付归属和故障归属校验
  - 修复前端端口、代理和 Bearer token 请求头
  - 新增 `VendingManagementApplicationSmokeTest`，`093-backend/mvn.cmd test` 通过
  - `093-frontend/npm.cmd install` 通过并生成 `package-lock.json`
  - `093-frontend/npm.cmd run build` 通过，仅保留 Vite chunk 体积提示
  - 后端 `18103` 默认 H2 环境真实启动抽测通过，覆盖匿名 `401`、公开公告、登录脱敏、统计越权、顾客下单支付和故障归属
  - 后端 `18104` 使用 MySQL `root / 1234` 的 `mysql-verify` profile 真实启动抽测通过，覆盖匿名 `401`、公开公告、登录脱敏、统计越权、补货员下单越权 `403`、顾客下单支付和故障归属；验证库为 `vending_093_verify`
  - 前端 `3093` 页面响应和代理登录验证通过：首页 `200`、代理登录 `200`、角色 `ADMIN`、无 `password` 字段
  - 新增 `docs/checks/093-vending-management-system.md`
  - 更新 `docs/project-check-tracker.md`
  - 清理 `18103`、`18104`、`3093` 验证进程
  - 下一项目为 `094`

### 进行中：094 宠物咖啡馆平台
- **Status:** in_progress
- Actions taken:
  - 读取 `094-backend/PRD.md`、`094-backend/PLAN.md`、`pom.xml`、`application.yml`
  - 读取 `094-frontend/package.json`、`vite.config.js`、`src/api/request.js`
  - 确认原始后端强依赖 MySQL、Redis 和 `8080`
  - 确认原始前端写死 `3000 -> 8080` 代理，且 `Authorization` 头未补 Bearer

### 已完成：094 宠物咖啡馆平台
- **Status:** completed
- Actions taken:
  - 清理 `094-backend` 46 个 Java 源文件中的 UTF-8 BOM，恢复 `mvn.cmd test` 编译
  - 升级后端到 JDK 17，切换新版 MySQL 驱动，补齐 H2 与测试依赖
  - 将后端默认环境改为 H2 自举，新增 `application-mysql.yml`、`application-mysql-verify.yml`、`schema-h2.sql`、`data-h2.sql`、`schema-mysql.sql`、`data-mysql.sql`
  - 在启动类补充 `@MapperScan`，修复 Mapper 未扫描问题
  - 新增 `RuntimeStoreService`，去掉默认演示链路对 Redis token 的强依赖
  - 修复 JWT Bearer 兼容、登录响应 password 泄露、真实 HTTP `401/403/500` 和登出失效问题
  - 放行 `/api/pet/public/**`，修复店宠公开列表被误拦截
  - 收紧顾客/店长/管理员权限边界，限制下单、支付、预约、评价、公告和统计访问
  - 修复前端端口、代理和 Bearer token 请求头
  - 新增 `PetCafePlatformApplicationSmokeTest`，`094-backend/mvn.cmd test` 通过
  - `094-frontend/npm.cmd install` 通过
  - `094-frontend/npm.cmd run build` 通过，仅保留 Vite chunk 体积提示
  - 后端 `18105` 默认 H2 环境真实启动抽测通过，覆盖匿名 `401`、公开店宠/菜单/公告、登录脱敏、统计越权和店长公告越权
  - 后端 `18106` 使用 MySQL `root / 1234` 的 `mysql-verify` profile 真实启动抽测通过，覆盖匿名 `401`、公开店宠、登录脱敏、统计越权和店长下单越权；验证库为 `pet_cafe_platform_094_verify`
  - 前端 `3094` 页面响应和代理登录验证通过：首页 `200`、代理登录 `200`、角色 `ADMIN`、无 `password` 字段
  - 新增 `docs/checks/094-pet-cafe-platform.md`
  - 更新 `docs/project-check-tracker.md`
  - 下一项目为 `095`

### 进行中：095 足球联赛管理系统
- **Status:** in_progress
- Actions taken:
  - 已确认下一项目目录存在：`095-backend`、`095-frontend`
  - 已定位到 `docs/plans/2026-03-18-095-football-league.md` 作为下一轮规划入口

### 已完成：095 足球联赛管理系统
- **Status:** completed
- Actions taken:
  - 确认 `095-backend` 为 Spring Boot 2.7.18 + MyBatis-Plus + JWT 后端，`095-frontend` 为 Vue 3 + Vite + Element Plus + Pinia 前端
  - 确认默认环境已为 H2 自举，正式 MySQL profile 使用 `football_league_management_095`、`root / 1234`
  - 确认 `application-mysql-verify.yml` 使用 `football_league_management_095_verify` 临时库做非破坏性 MySQL 验证
  - `095-backend/mvn.cmd test` 通过，`Tests run: 1, Failures: 0, Errors: 0`
  - `095-frontend/npm.cmd run build` 通过，仅保留 Vite CJS API 和 chunk 体积提示
  - 后端 `18107` 默认 H2 环境真实启动抽测通过，覆盖匿名 `401`、公开赛程、三角色登录脱敏、球迷统计越权、经理统计、关注球队和登出失效
  - 后端 `18108` 使用 MySQL `root / 1234` 的 `mysql-verify` profile 真实启动抽测通过，覆盖匿名 `401`、公开赛程/资讯/积分榜、三角色登录脱敏、球迷统计越权、经理访问用户管理越权、经理统计、关注球队和登出失效；验证库为 `football_league_management_095_verify`
  - 前端 `3095` 页面响应和代理登录验证通过：首页 `200`、代理登录 `200`、角色 `ADMIN`、无 `password` 字段
  - MySQL 临时库 `football_league_management_095_verify` 已在验证后删除
  - 新增 `docs/checks/095-football-league-system.md`
  - 更新 `docs/project-check-tracker.md`
  - 清理 `18107`、`18108`、`3095` 验证进程
  - 下一项目为 `096`

### 已完成：096 线上医院挂号系统
- **Status:** completed
- Actions taken:
  - 确认 `096-backend` 为 Spring Boot 2.7.18 + MyBatis + PageHelper + JWT 后端，`096-frontend` 为 Vue 3 + Vite + Element Plus + Pinia 前端
  - 将后端默认环境改为 H2 自举，新增 `application-mysql.yml`、`application-mysql-verify.yml`、`schema-h2.sql`、`data-h2.sql`、`schema-mysql.sql`、`data-mysql.sql`
  - 将 MySQL 正式 profile 调整为 `online_hospital_registration_db`、`root / 1234`
  - 新增 `RuntimeStoreService`，去掉默认演示链路对 Redis token 的强依赖
  - 修复 JWT Bearer 兼容、登录响应 password 泄露、真实 HTTP `401/403/500` 和登出失效问题
  - 放行公开科室、医生、评价、排班、公告、轮播和热门医生接口
  - 修复统计 SQL 的 H2/MySQL 兼容问题：`value` 别名冲突、`date_format/date_sub/curdate` MySQL 专属函数
  - 收紧他人订单支付、他人预约取消、非本人预约评价和医生完成非本人预约的权限语义
  - 修复前端端口、代理和 Bearer token 请求头
  - 新增 `OnlineHospitalRegistrationApplicationSmokeTest`，覆盖公开接口、三角色登录、登录脱敏、权限边界、挂号支付、就诊完成、评价和登出失效
  - `096-backend` 下执行 `mvn.cmd test` 通过，`Tests run: 1, Failures: 0, Errors: 0`
  - `096-frontend/npm.cmd run build` 通过，仅保留 Vite CJS API 和 chunk 体积提示
  - 后端 `18109` 默认 H2 环境真实启动抽测通过，覆盖匿名 `401`、公开公告/轮播/医生/排班/热门医生、登录脱敏、患者用户管理越权、统计、挂号支付、医生完成、患者评价和登出失效
  - 后端 `18110` 使用 MySQL `root / 1234` 的 `mysql-verify` profile 真实启动抽测通过；验证库为 `online_hospital_registration_096_verify`
  - 前端 `3096` 页面响应和代理登录验证通过：首页 `200`、代理登录 `200`、角色 `ADMIN`、有 token、无 `password` 字段
  - MySQL 临时库 `online_hospital_registration_096_verify` 已在验证后删除
  - 新增 `docs/checks/096-online-hospital-registration-system.md`
  - 更新 `docs/project-check-tracker.md`
  - 清理 `18109`、`18110`、`3096` 验证进程
  - `001` 到 `096` 全部项目验证完成

### 已完成：119 设备备件寿命预测与维保决策系统 / 120 数字孪生园区设备巡检管理系统
- **Status:** completed
- Actions taken:
  - 生成 `119-backend`、`119-frontend`、`120-backend`、`120-frontend`
  - `119` 使用 Spring Boot 2.7.18 + MyBatis-Plus 3.5.5 + MySQL + Redis + JWT，Vue3 + Element Plus + ECharts
  - `120` 使用 Spring Boot 2.7.18 + MyBatis 注解 SQL + PageHelper + MySQL + Redis + JWT，Vue3 + Element Plus + ECharts
  - `119` 配置后端端口 `8119`、前端端口 `3119`、数据库 `spare_life_119`、MySQL `root / 1234`、Redis DB `22`
  - `120` 配置后端端口 `8120`、前端端口 `3120`、数据库 `digital_twin_park_120`、MySQL `root / 1234`、Redis DB `23`
  - 静态数量验证通过：`119/120` 均为 14 个实体、14 个 Mapper、16 个 Controller、17 个前端页面、14 张 SQL 表
  - `120` MyBatis 注解 SQL 的空字符串条件转义已确认正常
  - `119/120` 登录响应密码脱敏已确认存在 `setPassword(null)`
  - 残留旧项目关键词扫描和注释扫描均通过
  - 修正初始化 SQL 中演示手机号位数过长的问题
  - 更新 `readme.md`、`readme_simple.md`、`docs/topic-candidates-097-146.md`
  - 按 `rule.md` 要求未执行编译构建，仅做静态验证
  - 下一项目为 `121`

### 已完成：121 无人机巡检任务调度与缺陷上报平台 / 122 智慧工地安全巡检与隐患整改系统
- **Status:** completed
- Actions taken:
  - 生成 `121-backend`、`121-frontend`、`122-backend`、`122-frontend`
  - `121` 使用 Spring Boot 2.7.18 + MyBatis-Plus 3.5.5 + MySQL + Redis + JWT，Vue3 + Element Plus + ECharts
  - `122` 使用 Spring Boot 2.7.18 + MyBatis 注解 SQL + PageHelper + MySQL + Redis + JWT，Vue3 + Element Plus + ECharts
  - `121` 配置后端端口 `8121`、前端端口 `3121`、数据库 `drone_inspection_121`、MySQL `root / 1234`、Redis DB `24`
  - `122` 配置后端端口 `8122`、前端端口 `3122`、数据库 `smart_worksite_safety_122`、MySQL `root / 1234`、Redis DB `25`
  - 静态数量验证通过：`121/122` 均为 14 个实体、14 个 Mapper、16 个 Controller、17 个前端页面、14 张 SQL 表
  - `122` MyBatis 注解 SQL 的空字符串条件转义已确认正常
  - 残留旧项目关键词扫描和注释扫描均通过
  - 修正首页统计计数来源，使看板指标对应设备/任务/缺陷/整改与项目/任务/隐患/整改
  - 更新 `readme.md`、`readme_simple.md`、`docs/topic-candidates-097-146.md`
  - 按 `rule.md` 要求未执行编译构建，仅做静态验证
  - 下一项目为 `123`

### 已完成：123 城市内涝监测与应急调度平台 / 124 电动车充电桩预约与运维管理系统
- **Status:** completed
- Actions taken:
  - 生成 `123-backend`、`123-frontend`、`124-backend`、`124-frontend`
  - `123` 使用 Spring Boot 2.7.18 + MyBatis-Plus 3.5.5 + MySQL + Redis + JWT，Vue3 + Element Plus + ECharts
  - `124` 使用 Spring Boot 2.7.18 + MyBatis 注解 SQL + PageHelper + MySQL + Redis + JWT，Vue3 + Element Plus + ECharts
  - `123` 配置后端端口 `8123`、前端端口 `3123`、数据库 `urban_flood_dispatch_123`、MySQL `root / 1234`、Redis DB `26`
  - `124` 配置后端端口 `8124`、前端端口 `3124`、数据库 `ev_charging_124`、MySQL `root / 1234`、Redis DB `27`
  - 静态数量验证通过：`123/124` 均为 14 个实体、14 个 Mapper、16 个 Controller、17 个前端页面、14 张 SQL 表
  - `124` MyBatis 注解 SQL 的空字符串条件转义已确认正常
  - 残留旧项目关键词扫描和注释扫描均通过
  - 更新 `readme.md`、`readme_simple.md`、`docs/topic-candidates-097-146.md`
  - 按 `rule.md` 要求未执行编译构建，仅做静态验证
  - 下一项目为 `125`

### 已完成：125 智慧停车诱导与空位预测平台 / 126 家庭能源用电分析与节能建议平台
- **Status:** completed
- Actions taken:
  - 生成 `125-backend`、`125-frontend`、`126-backend`、`126-frontend`
  - `125` 使用 Spring Boot 2.7.18 + MyBatis-Plus 3.5.5 + MySQL + Redis + JWT，Vue3 + Element Plus + ECharts
  - `126` 使用 Spring Boot 2.7.18 + MyBatis 注解 SQL + PageHelper + MySQL + Redis + JWT，Vue3 + Element Plus + ECharts
  - `125` 配置后端端口 `8125`、前端端口 `3125`、数据库 `smart_parking_125`、MySQL `root / 1234`、Redis DB `28`
  - `126` 配置后端端口 `8126`、前端端口 `3126`、数据库 `home_energy_126`、MySQL `root / 1234`、Redis DB `29`
  - 静态数量验证通过：`125/126` 均为 14 个实体、14 个 Mapper、16 个 Controller、17 个前端页面、14 张 SQL 表
  - `126` MyBatis 注解 SQL 的空字符串条件转义已确认正常
  - `125/126` 登录响应密码脱敏已确认存在 `setPassword(null)`
  - 残留旧项目关键词扫描和注释扫描均通过
  - 更新 `readme.md`、`readme_simple.md`、`docs/topic-candidates-097-146.md`
  - 按 `rule.md` 要求未执行编译构建，仅做静态验证
  - 下一项目为 `127`

### 已完成：127 企业碳排放核算与减排任务管理系统 / 128 ESG 数据填报与可视化报告系统
- **Status:** completed
- Actions taken:
  - 生成 `127-backend`、`127-frontend`、`128-backend`、`128-frontend`
  - `127` 使用 Spring Boot 2.7.18 + MyBatis-Plus 3.5.5 + MySQL + Redis + JWT，Vue3 + Element Plus + ECharts
  - `128` 使用 Spring Boot 2.7.18 + MyBatis 注解 SQL + PageHelper + MySQL + Redis + JWT，Vue3 + Element Plus + ECharts
  - `127` 配置后端端口 `8127`、前端端口 `3127`、数据库 `carbon_accounting_127`、MySQL `root / 1234`、Redis DB `30`
  - `128` 配置后端端口 `8128`、前端端口 `3128`、数据库 `esg_report_128`、MySQL `root / 1234`、Redis DB `31`
  - 静态数量验证通过：`127/128` 均为 14 个实体、14 个 Mapper、16 个 Controller、17 个前端页面、14 张 SQL 表
  - `128` MyBatis 注解 SQL 的空字符串条件转义已确认正常
  - 残留旧项目关键词扫描和注释扫描均通过
  - 更新 `readme.md`、`readme_simple.md`、`docs/topic-candidates-097-146.md`
  - 按 `rule.md` 要求未执行编译构建，仅做静态验证
  - 下一项目为 `129`

### 已完成：129 水产养殖环境监测与投喂预警系统 / 130 温室大棚物联网控制与病害预警系统
- **Status:** completed
- Actions taken:
  - 生成 `129-backend`、`129-frontend`、`130-backend`、`130-frontend`
  - `129` 使用 Spring Boot 2.7.18 + MyBatis-Plus 3.5.5 + MySQL + Redis + JWT，Vue3 + Element Plus + ECharts
  - `130` 使用 Spring Boot 2.7.18 + MyBatis 注解 SQL + PageHelper + MySQL + Redis + JWT，Vue3 + Element Plus + ECharts
  - `129` 配置后端端口 `8129`、前端端口 `3129`、数据库 `aquaculture_monitor_129`、MySQL `root / 1234`、Redis DB `32`
  - `130` 配置后端端口 `8130`、前端端口 `3130`、数据库 `greenhouse_iot_130`、MySQL `root / 1234`、Redis DB `33`
  - 静态数量验证通过：`129/130` 均为 14 个实体、14 个 Mapper、16 个 Controller、17 个前端页面、14 张 SQL 表
  - `130` MyBatis 注解 SQL 的空字符串条件转义已确认正常
  - `129/130` 登录响应密码脱敏已确认存在 `setPassword(null)`
  - 残留旧项目关键词扫描和注释扫描均通过
  - 修正 `127/128/129/130` JWT secret 与 artifactId 口径残留
  - 更新 `readme.md`、`readme_simple.md`、`docs/topic-candidates-097-146.md`
  - 按 `rule.md` 要求未执行编译构建，仅做静态验证
  - 下一项目为 `131`

### 已完成：131 药品不良反应上报与随访管理系统 / 132 医疗器械借用消毒与追踪管理系统
- **Status:** completed
- Actions taken:
  - 生成 `131-backend`、`131-frontend`、`132-backend`、`132-frontend`
  - `131` 使用 Spring Boot 2.7.18 + MyBatis-Plus 3.5.5 + MySQL + Redis + JWT，Vue3 + Element Plus + ECharts
  - `132` 使用 Spring Boot 2.7.18 + MyBatis 注解 SQL + PageHelper + MySQL + Redis + JWT，Vue3 + Element Plus + ECharts
  - `131` 配置后端端口 `8131`、前端端口 `3131`、数据库 `drug_reaction_131`、MySQL `root / 1234`、Redis DB `34`
  - `132` 配置后端端口 `8132`、前端端口 `3132`、数据库 `medical_device_132`、MySQL `root / 1234`、Redis DB `35`
  - 静态数量验证通过：`131/132` 均为 14 个实体、14 个 Mapper、16 个 Controller、17 个前端页面、14 张 SQL 表
  - `132` MyBatis 注解 SQL 的空字符串条件转义已确认正常
  - `131/132` 登录响应密码脱敏已确认存在 `setPassword(null)`
  - 残留旧项目关键词扫描和注释扫描均通过
  - 修正 `131/132` 数据库名、artifactId 与 JWT secret 替换顺序残留
  - 更新 `readme.md`、`readme_simple.md`、`docs/topic-candidates-097-146.md`
  - 按 `rule.md` 要求未执行编译构建，仅做静态验证
  - 下一项目为 `133`


### 已完成：133 实验室耗材采购审批与库存预警系统 / 134 科研项目经费报销与成果管理系统
- 本轮新增：
  - 生成 `133-backend`、`133-frontend`、`134-backend`、`134-frontend`
  - `133` 使用 Spring Boot 2.7.18 + MyBatis-Plus 3.5.5 + MySQL + Redis + JWT，Vue3 + Element Plus + ECharts
  - `134` 使用 Spring Boot 2.7.18 + MyBatis 注解 SQL + PageHelper + MySQL + Redis + JWT，Vue3 + Element Plus + ECharts
  - `133` 配置后端端口 `8133`、前端端口 `3133`、数据库 `lab_consumable_133`、MySQL `root / 1234`、Redis DB `36`
  - `134` 配置后端端口 `8134`、前端端口 `3134`、数据库 `research_fund_134`、MySQL `root / 1234`、Redis DB `37`
  - 静态数量验证通过：`133/134` 均为 14 个实体、14 个 Mapper、16 个 Controller、17 个前端页面、14 张 SQL 表
  - 下一项目为 `135`

### 已完成：135 学术会议投稿评审与日程管理系统 / 136 导师课题双选与开题过程管理系统
- 本轮新增：
  - 生成 `135-backend`、`135-frontend`、`136-backend`、`136-frontend`
  - `135` 使用 Spring Boot 2.7.18 + MyBatis-Plus 3.5.5 + MySQL + Redis + JWT，Vue3 + Element Plus + ECharts
  - `136` 使用 Spring Boot 2.7.18 + MyBatis 注解 SQL + PageHelper + MySQL + Redis + JWT，Vue3 + Element Plus + ECharts
  - `135` 配置后端端口 `8135`、前端端口 `3135`、数据库 `conference_review_135`、MySQL `root / 1234`、Redis DB `38`
  - `136` 配置后端端口 `8136`、前端端口 `3136`、数据库 `topic_selection_136`、MySQL `root / 1234`、Redis DB `39`
  - 静态数量验证通过：`135/136` 均为 14 个实体、14 个 Mapper、16 个 Controller、17 个前端页面、14 张 SQL 表
  - `136` MyBatis 注解 SQL 的空字符串条件转义已确认正常
  - `135/136` 登录响应密码脱敏已确认存在 `setPassword(null)`
  - 残留旧项目关键词扫描和注释扫描均通过
  - 更新 `readme.md`、`readme_simple.md`、`docs/topic-candidates-097-146.md`
  - 按 `rule.md` 要求未执行编译构建，仅做静态验证
  - 下一项目为 `137`

### 已完成：137 大学生创新创业项目孵化管理平台 / 138 在线考试反作弊行为分析与证据管理系统
- 本轮新增：
  - 生成 `137-backend`、`137-frontend`、`138-backend`、`138-frontend`
  - `137` 使用 Spring Boot 2.7.18 + MyBatis-Plus 3.5.5 + MySQL + Redis + JWT，Vue3 + Element Plus + ECharts
  - `138` 使用 Spring Boot 2.7.18 + MyBatis 注解 SQL + PageHelper + MySQL + Redis + JWT，Vue3 + Element Plus + ECharts
  - `137` 配置后端端口 `8137`、前端端口 `3137`、数据库 `innovation_incubator_137`、MySQL `root / 1234`、Redis DB `40`
  - `138` 配置后端端口 `8138`、前端端口 `3138`、数据库 `exam_guard_138`、MySQL `root / 1234`、Redis DB `41`
  - 静态数量验证通过：`137/138` 均为 14 个实体、14 个 Mapper、16 个 Controller、17 个前端页面、14 张 SQL 表
  - `138` MyBatis 注解 SQL 的空字符串条件转义已确认正常
  - `137/138` 登录响应密码脱敏已确认存在 `setPassword(null)`
  - 残留旧项目关键词扫描和注释扫描均通过
  - 更新 `readme.md`、`readme_simple.md`、`docs/topic-candidates-097-146.md`
  - 按 `rule.md` 要求未执行编译构建，仅做静态验证
  - 下一项目为 `139`

### 已完成：139 企业培训学习路径与能力画像系统 / 140 电子合同签署与印章审批管理系统
- 本轮新增：
  - 生成 `139-backend`、`139-frontend`、`140-backend`、`140-frontend`
  - `139` 使用 Spring Boot 2.7.18 + MyBatis-Plus 3.5.5 + MySQL + Redis + JWT，Vue3 + Element Plus + ECharts
  - `140` 使用 Spring Boot 2.7.18 + MyBatis 注解 SQL + PageHelper + MySQL + Redis + JWT，Vue3 + Element Plus + ECharts
  - `139` 配置后端端口 `8139`、前端端口 `3139`、数据库 `learning_path_139`、MySQL `root / 1234`、Redis DB `42`
  - `140` 配置后端端口 `8140`、前端端口 `3140`、数据库 `electronic_contract_140`、MySQL `root / 1234`、Redis DB `43`
  - 静态数量验证通过：`139/140` 均为 14 个实体、14 个 Mapper、16 个 Controller、17 个前端页面、14 张 SQL 表
  - `140` MyBatis 注解 SQL 的空字符串条件转义已确认正常
  - `139/140` 登录响应密码脱敏已确认存在 `setPassword(null)`
  - 残留旧项目关键词扫描和注释扫描均通过
  - 修正 `139/140` 前端菜单路由索引残留
  - 更新 `readme.md`、`readme_simple.md`、`docs/topic-candidates-097-146.md`
  - 按 `rule.md` 要求未执行编译构建，仅做静态验证
  - 下一项目为 `141`

### 已完成：141 固定资产 RFID 盘点与借用归还系统 / 142 车辆保险理赔材料审核与进度跟踪系统
- 本轮新增：
  - 生成 `141-backend`、`141-frontend`、`142-backend`、`142-frontend`
  - `141` 使用 Spring Boot 2.7.18 + MyBatis-Plus 3.5.5 + MySQL + Redis + JWT，Vue3 + Element Plus + ECharts
  - `142` 使用 Spring Boot 2.7.18 + MyBatis 注解 SQL + PageHelper + MySQL + Redis + JWT，Vue3 + Element Plus + ECharts
  - `141` 配置后端端口 `8141`、前端端口 `3141`、数据库 `asset_rfid_141`、MySQL `root / 1234`、Redis DB `44`
  - `142` 配置后端端口 `8142`、前端端口 `3142`、数据库 `vehicle_claim_142`、MySQL `root / 1234`、Redis DB `45`
  - 静态数量验证通过：`141/142` 均为 14 个实体、14 个 Mapper、16 个 Controller、17 个前端页面、14 张 SQL 表
  - `142` MyBatis 注解 SQL 的空字符串条件转义已确认正常
  - `141/142` 登录响应密码脱敏已确认存在 `setPassword(null)`
  - 残留旧项目关键词扫描和注释扫描均通过
  - 修正 `141/142` 统计看板与文档文案残留
  - 更新 `readme.md`、`readme_simple.md`、`docs/topic-candidates-097-146.md`
  - 按 `rule.md` 要求未执行编译构建，仅做静态验证
  - 下一项目为 `143`

### 已完成：143 社区公益时间银行互助服务平台 / 144 无障碍出行路线规划与志愿协助平台
- 本轮新增：
  - 生成 `143-backend`、`143-frontend`、`144-backend`、`144-frontend`
  - `143` 使用 Spring Boot 2.7.18 + MyBatis-Plus 3.5.5 + MySQL + Redis + JWT，Vue3 + Element Plus + ECharts
  - `144` 使用 Spring Boot 2.7.18 + MyBatis 注解 SQL + PageHelper + MySQL + Redis + JWT，Vue3 + Element Plus + ECharts
  - `143` 配置后端端口 `8143`、前端端口 `3143`、数据库 `time_bank_143`、MySQL `root / 1234`、Redis DB `46`
  - `144` 配置后端端口 `8144`、前端端口 `3144`、数据库 `accessible_travel_144`、MySQL `root / 1234`、Redis DB `47`
  - 静态数量验证通过：`143/144` 均为 14 个实体、14 个 Mapper、16 个 Controller、17 个前端页面、14 张 SQL 表
  - `144` MyBatis 注解 SQL 的空字符串条件转义已确认正常
  - `143/144` 登录响应密码脱敏已确认存在 `setPassword(null)`
  - 残留旧项目关键词扫描和注释扫描均通过
  - 修正 `143/144` 看板指标与项目文档文案残留
  - 更新 `readme.md`、`readme_simple.md`、`docs/topic-candidates-097-146.md`
  - 按 `rule.md` 要求未执行编译构建，仅做静态验证
  - 下一项目为 `145`

### 已完成：145 城市噪声投诉监测与执法协同平台 / 146 食品安全抽检任务与结果公示平台
- 本轮新增：
  - 生成 `145-backend`、`145-frontend`、`146-backend`、`146-frontend`
  - `145` 使用 Spring Boot 2.7.18 + MyBatis-Plus 3.5.5 + MySQL + Redis + JWT，Vue3 + Element Plus + ECharts
  - `146` 使用 Spring Boot 2.7.18 + MyBatis 注解 SQL + PageHelper + MySQL + Redis + JWT，Vue3 + Element Plus + ECharts
  - `145` 配置后端端口 `8145`、前端端口 `3145`、数据库 `noise_monitor_145`、MySQL `root / 1234`、Redis DB `48`
  - `146` 配置后端端口 `8146`、前端端口 `3146`、数据库 `food_inspection_146`、MySQL `root / 1234`、Redis DB `49`
  - 静态数量验证通过：`145/146` 均为 14 个实体、14 个 Mapper、16 个 Controller、17 个前端页面、14 张 SQL 表
  - `146` MyBatis 注解 SQL 的空字符串条件转义已确认正常
  - `145/146` 登录响应密码脱敏已确认存在 `setPassword(null)`
  - 残留旧项目关键词扫描和注释扫描均通过
  - 修正 `145/146` 路由索引、统计看板与项目文档文案残留
  - 更新 `readme.md`、`readme_simple.md`、`docs/topic-candidates-097-146.md`
  - 按 `rule.md` 要求未执行编译构建，仅做静态验证
  - `097-146` 候选清单全部实现完成

### 已完成：147 校园心理咨询预约与危机干预管理系统 / 148 社区养老服务派单与健康随访管理系统
- 本轮新增：
  - 生成 `147-backend`、`147-frontend`、`148-backend`、`148-frontend`
  - `147` 使用 Spring Boot 2.7.18 + MyBatis-Plus 3.5.5 + MySQL + Redis + JWT，Vue3 + Element Plus + ECharts
  - `148` 使用 Spring Boot 2.7.18 + MyBatis 注解 SQL + PageHelper + MySQL + Redis + JWT，Vue3 + Element Plus + ECharts
  - `147` 配置后端端口 `8147`、前端端口 `3147`、数据库 `campus_psychology_147`、MySQL `root / 1234`、Redis DB `50`
  - `148` 配置后端端口 `8148`、前端端口 `3148`、数据库 `elder_service_148`、MySQL `root / 1234`、Redis DB `51`
  - 静态数量验证通过：`147/148` 均为 14 个实体、14 个 Mapper、16 个 Controller、17 个前端页面、14 张 SQL 表
  - `148` MyBatis 注解 SQL 的空字符串条件转义已确认正常
  - `147/148` 登录响应密码脱敏已确认存在 `setPassword(null)`
  - 残留旧项目关键词扫描和注释扫描均通过
  - 修正 `147/148` 看板与项目文档文案残留
  - 新增 `docs/topic-candidates-147-196.md`
  - 更新 `readme.md`、`readme_simple.md`
  - 按 `rule.md` 要求未执行编译构建，仅做静态验证
  - 下一项目为 `149`

### 已完成：149 高校实验设备共享预约与违规使用追踪系统 / 150 医院门诊检查预约与报告回传管理系统
- 本轮新增：
  - 生成 `149-backend`、`149-frontend`、`150-backend`、`150-frontend`
  - `149` 使用 Spring Boot 2.7.18 + MyBatis-Plus 3.5.5 + MySQL + Redis + JWT，Vue3 + Element Plus + ECharts
  - `150` 使用 Spring Boot 2.7.18 + MyBatis 注解 SQL + PageHelper + MySQL + Redis + JWT，Vue3 + Element Plus + ECharts
  - `149` 配置后端端口 `8149`、前端端口 `3149`、数据库 `equipment_share_149`、MySQL `root / 1234`、Redis DB `52`
  - `150` 配置后端端口 `8150`、前端端口 `3150`、数据库 `outpatient_exam_150`、MySQL `root / 1234`、Redis DB `53`
  - 静态数量验证通过：`149/150` 均为 14 个实体、14 个 Mapper、16 个 Controller、17 个前端页面、14 张 SQL 表
  - `150` MyBatis 注解 SQL 的空字符串条件转义已确认正常
  - `149/150` 登录响应密码脱敏已确认存在 `setPassword(null)`
  - 残留旧项目关键词扫描和注释扫描均通过
  - 修正 `149/150` 路由索引、统计看板与项目文档文案残留
  - 更新 `docs/topic-candidates-147-196.md`、`readme.md`、`readme_simple.md`
  - 按 `rule.md` 要求未执行编译构建，仅做静态验证
  - 下一项目为 `151`

### 已检查：101 多模态招聘材料解析与岗位匹配系统 / 102 法律咨询案件进度与智能文书管理系统
- 本轮新增：
  - 复核 `101`、新增 `102` 真实巡检文档：`docs/checks/101-multimodal-recruit-match-platform.md`、`docs/checks/102-legal-case-document-platform.md`
  - `101` 后端 `mvn.cmd test` 通过但无自动化测试，前端 `npm.cmd run build` 通过
  - `101` 默认环境登录先后受阻于缺少数据库 `recruit_match_101` 和本地 Redis 依赖，手工导库并启动 Redis 后可继续验收
  - `102` 后端 `mvn.cmd test` 编译失败，`DocumentTemplateService` 与 `FeeRecordService` 缺少控制器调用的 `saveEntity(...)`
  - `102` 前端首次 `npm.cmd install` 后 `vite` 不可用，补执行 `npm.cmd install --include=dev` 后 `npm.cmd run build` 通过
  - 静态复核发现 `102` 的 `/api/document/page`、`/api/version/page`、`/api/fee/page` 缺少后端角色断言，且咨询/文书/版本等高敏写接口对全部 staff 放权过宽
  - 更新 `docs/project-check-tracker.md`
  - 下一项目为 `103`

### 已检查：103 智能客服工单质检与知识推荐系统
- 本轮新增：
  - 生成巡检文档 `docs/checks/103-customer-service-quality-platform.md`
  - `103` 后端 `mvn.cmd test` 通过，但无自动化测试，日志为 `No tests to run`
  - `103` 前端 `npm.cmd install --include=dev` 后首次 `npm.cmd run build` 报 `vite` 不存在，重试后构建通过
  - 静态复核确认前端路由与侧边栏完全未按角色收口，所有登录用户均可见全部业务页面
  - 静态复核确认后端 `/api/statistics/dashboard`、`/api/log/page`、`/api/performance/page`、`/api/order/page`、`/api/quality-result/page` 等查询接口缺少角色断言
  - 发现 `QualityTaskService.run()` 可重复插入质检结果，缺少状态机与幂等控制
  - 更新 `docs/project-check-tracker.md`
  - 下一项目为 `104`

### 已修复：104 开源许可证合规扫描与项目台账系统
- 本轮新增：
  - 后端 `mvn.cmd test` 通过，但无自动化测试，日志为 `No tests to run`
  - 前端 `npm.cmd run build` 通过
  - 为 `JwtInterceptor` 补充 `userId/username/role` 请求属性透传
  - 为 `AuthService` 增加统一角色断言方法，并为账号、日志、看板、项目台账、许可证策略、扫描、风险、整改、报告、审批等控制器补齐权限收口
  - 为前端路由补充 `meta.roles`，并按角色动态收口侧边栏菜单与默认跳转
  - 当前尚未做真实登录验收；默认环境仍依赖本地 MySQL `license_compliance_104` 与 Redis
  - 下一项目为 `105`

### 已修复：105 API 接口文档生成与测试用例管理平台
- 本轮新增：
  - 后端 `mvn.cmd test` 通过，但无自动化测试，日志为 `No tests to run`
  - 前端 `npm.cmd run build` 通过
  - 为 `JwtInterceptor` 补充 `userId/username/role` 请求属性透传
  - 为 `AuthService` 增加统一角色断言方法，并为账号、日志、接口项目、分组、接口定义、参数字段、Mock 规则、测试用例、执行记录、结果明细、文档快照等控制器补齐权限收口
  - 为前端路由补充 `meta.roles`，并按角色动态收口侧边栏菜单与默认跳转
  - 当前尚未做真实登录验收；默认环境仍依赖本地 MySQL `api_testcase_105` 与 Redis
  - 下一项目为 `106`

### 已修复：106 DevOps 发布审批与回滚管理系统
- 本轮新增：
  - 后端 `mvn.cmd test` 通过，但无自动化测试，日志为 `No tests to run`
  - 前端 `npm.cmd install` 与 `npm.cmd run build` 通过
  - 为 `JwtInterceptor` 补充 `userId/username/role` 请求属性透传
  - 为 `AuthService` 增加统一角色断言方法，并为账号、环境、应用服务、流水线、发布计划、发布单、审批流、审批记录、制品、部署、回滚、检查、日志、看板等控制器补齐权限收口
  - 为前端路由补充 `meta.roles`，并按角色动态收口侧边栏菜单与默认跳转
  - 当前尚未做真实登录验收；默认环境仍依赖本地 MySQL `devops_release_106` 与 Redis
  - 下一项目为 `107`

### 已修复：107 云服务器资产监控与告警平台
- 本轮新增：
  - 后端 `mvn.cmd test` 通过，但无自动化测试，日志为 `No tests to run`
  - 前端 `npm.cmd install` 与 `npm.cmd run build` 通过
  - 为 `JwtInterceptor` 补充 `userId/username/role` 请求属性透传
  - 为 `AuthService` 增加统一角色断言方法，并为账号、区域、资产、资源分组、指标、告警、通知、工单、维护窗口、容量规划、看板组件、日志、看板等控制器补齐权限收口
  - 为前端路由补充 `meta.roles`，并按角色动态收口侧边栏菜单与默认跳转
  - 当前尚未做真实登录验收；默认环境仍依赖本地 MySQL `cloud_monitor_107` 与 Redis
  - 下一项目为 `108`

### 已修复：108 云原生成本分析与资源优化平台
- 本轮新增：
  - 后端 `mvn.cmd test` 通过，但无自动化测试，日志为 `No tests to run`
  - 前端 `npm.cmd install` 与 `npm.cmd run build` 通过
  - 为 `JwtInterceptor` 补充 `userId/username/role` 请求属性透传
  - 为 `AuthService` 增加统一角色断言方法，并按 `ADMIN / FINOPS / DEVOPS / MANAGER` 口径，为账号、云账号、命名空间、账单、明细、预算、分摊、闲置资源、优化规则、优化建议、节省计划、异常事件、报告快照、日志、看板等控制器补齐权限收口
  - 为前端路由补充 `meta.roles`，并按角色动态收口侧边栏菜单与默认跳转
  - 当前尚未做真实登录验收；默认环境仍依赖本地 MySQL `cloud_cost_108` 与 Redis
  - 下一项目为 `109`

### 已修复：109 数据脱敏与敏感信息识别平台
- 本轮新增：
  - 后端 `mvn.cmd test` 通过，但无自动化测试，日志为 `No tests to run`
  - 前端 `npm.cmd install` 与 `npm.cmd run build` 通过
  - 为 `JwtInterceptor` 补充 `userId/username/role` 请求属性透传
  - 为 `AuthService` 增加统一角色断言方法，并按 `ADMIN / SECURITY / OWNER / AUDITOR` 口径，为账号、数据源、数据集、敏感规则、识别任务、识别结果、脱敏策略、脱敏任务、脱敏记录、字段血缘、访问申请、导出审批、风险告警、日志、看板等控制器补齐权限收口
  - 为前端路由补充 `meta.roles`，并按角色动态收口侧边栏菜单与默认跳转
  - 当前尚未做真实登录验收；默认环境仍依赖本地 MySQL `data_masking_109` 与 Redis
  - 下一项目为 `110`

### 已修复：110 个人数据隐私授权与访问审计平台
- 本轮新增：
  - 后端 `mvn.cmd test` 通过，但无自动化测试，日志为 `No tests to run`
  - 前端 `npm.cmd install` 与 `npm.cmd run build` 通过
  - 为 `JwtInterceptor` 补充 `userId/username/role` 请求属性透传
  - 为 `AuthService` 增加统一角色断言方法，并按 `ADMIN / PRIVACY / DATAUSER / AUDITOR` 口径，为账号、数据主体、个人数据项、授权目的、授权策略、授权记录、授权范围、访问申请、访问授权、访问日志、撤销申请、风险预警、审计报告、日志、看板等控制器补齐权限收口
  - 为前端路由补充 `meta.roles`，并按角色动态收口侧边栏菜单与默认跳转
  - 当前尚未做真实登录验收；默认环境仍依赖本地 MySQL `privacy_auth_110` 与 Redis
  - 下一项目为 `111`

### 已修复：111 网络钓鱼邮件演练与安全意识培训平台
- 本轮新增：
  - 后端 `mvn.cmd test` 通过，但无自动化测试，日志为 `No tests to run`
  - 前端 `npm.cmd install` 与 `npm.cmd run build` 通过
  - 为 `JwtInterceptor` 补充 `userId/username/role` 请求属性透传
  - 为 `AuthService` 增加统一角色断言方法，并按 `ADMIN / SECURITY / TRAINER / AUDITOR` 口径，为账号、员工、部门、邮件模板、演练活动、目标名单、发送记录、点击追踪、培训课程、培训考试、考试题目、考试记录、风险评分、日志、看板等控制器补齐权限收口
  - 为前端路由补充 `meta.roles`，并按角色动态收口侧边栏菜单与默认跳转
  - 当前尚未做真实登录验收；默认环境仍依赖本地 MySQL `phishing_training_111` 与 Redis
  - 下一项目为 `112`

### 已修复：112 零信任设备准入与访问控制管理系统
- 本轮新增：
  - 后端 `mvn.cmd test` 通过，但无自动化测试，日志为 `No tests to run`
  - 前端 `npm.cmd install` 与 `npm.cmd run build` 通过
  - 为 `JwtInterceptor` 补充 `userId/username/role` 请求属性透传
  - 为 `AuthService` 增加统一角色断言方法，并按 `ADMIN / SECURITY / NETWORK / AUDITOR` 口径，为账号、设备资产、员工账号、身份源、风险模型、风险评估、访问策略、策略规则、准入申请、访问会话、网络分区、设备证书、审计事件、日志、看板等控制器补齐权限收口
  - 为前端路由补充 `meta.roles`，并按角色动态收口侧边栏菜单与默认跳转
  - 当前尚未做真实登录验收；默认环境仍依赖本地 MySQL `zero_trust_112` 与 Redis

### 已修复：113 区块链农产品质量溯源与监管平台
- 本轮新增：
  - 后端 `mvn.cmd test` 通过，但无自动化测试，日志为 `No tests to run`
  - 前端 `npm.cmd install` 与 `npm.cmd run build` 通过
  - 为 `JwtInterceptor` 补充 `userId/username/role` 请求属性透传
  - 为 `AuthService` 增加统一角色断言方法，并按 `ADMIN / REGULATOR / FARMER / INSPECTOR` 口径，为账号权限、种植基地、农户档案、产品分类、产品批次、种植记录、农资投入、质检报告、区块存证、流通节点、物流记录、召回事件、监管检查、操作日志、看板等控制器补齐权限收口
  - 为前端路由补充 `meta.roles`，并按角色动态收口侧边栏菜单与默认跳转
  - 当前尚未做真实登录验收；默认环境仍依赖本地 MySQL `agri_trace_113` 与 Redis

### 已修复：114 供应链冷链温控追踪与异常预警平台
- 本轮新增：
  - 后端 `mvn.cmd test` 通过，但无自动化测试，日志为 `No tests to run`
  - 前端 `npm.cmd install` 与 `npm.cmd run build` 通过
  - 为 `JwtInterceptor` 补充 `userId/username/role` 请求属性透传
  - 为 `AuthService` 增加统一角色断言方法，并按 `ADMIN / DISPATCHER / CARRIER / SUPERVISOR` 口径，为账号权限、冷链仓点、承运企业、温控设备、冷链货品、运输订单、温控记录、运输轨迹、告警规则、异常告警、处置任务、责任追溯、设备维护、操作日志、看板等控制器补齐权限收口
  - 为前端路由补充 `meta.roles`，并按角色动态收口侧边栏菜单与默认跳转
  - 当前尚未做真实登录验收；默认环境仍依赖本地 MySQL `cold_chain_114` 与 Redis

### 已修复：115 跨境电商清关订单与汇率结算平台
- 本轮新增：
  - 后端 `mvn.cmd test` 通过，但无自动化测试，日志为 `No tests to run`
  - 前端 `npm.cmd install` 与 `npm.cmd run build` 通过
  - 为 `JwtInterceptor` 补充 `userId/username/role` 请求属性透传
  - 为 `AuthService` 增加统一角色断言方法，并按 `ADMIN / CUSTOMS / FINANCE / OPERATOR` 口径，为账号权限、商家店铺、客户档案、商品SKU、跨境订单、清关申报、清关单证、税费记录、汇率牌价、结算账单、支付记录、物流跟踪、订单对账、操作日志、看板等控制器补齐权限收口
  - 为前端路由补充 `meta.roles`，并按角色动态收口侧边栏菜单与默认跳转
  - 当前尚未做真实登录验收；默认环境仍依赖本地 MySQL `cross_border_115` 与 Redis

### 已修复：116 直播电商选品排期与售后工单系统
- 本轮新增：
  - 后端 `mvn.cmd test` 通过，但无自动化测试，日志为 `No tests to run`
  - 前端 `npm.cmd install` 与 `npm.cmd run build` 通过
  - 为 `JwtInterceptor` 补充 `userId/username/role` 请求属性透传
  - 为 `AuthService` 增加统一角色断言方法，并按 `ADMIN / OPERATOR / SERVICE / MERCHANT` 口径，为账号权限、直播渠道、主播档案、供应商品牌、选品池、直播场次、排期计划、样品申请、话术脚本、直播订单、售后工单、退款记录、主播绩效、操作日志、看板等控制器补齐权限收口
  - 为前端路由补充 `meta.roles`，并按角色动态收口侧边栏菜单与默认跳转
  - 当前尚未做真实登录验收；默认环境仍依赖本地 MySQL `live_commerce_116` 与 Redis

### 已修复：117 本地生活服务券核销与商户结算系统
- 本轮新增：
  - 后端 `mvn.cmd test` 通过，但无自动化测试，日志为 `No tests to run`
  - 前端 `npm.cmd install` 与 `npm.cmd run build` 通过
  - 为 `JwtInterceptor` 补充 `userId/username/role` 请求属性透传
  - 为 `AuthService` 增加统一角色断言方法，并按 `ADMIN / MERCHANT / CASHIER / FINANCE` 口径，为账号权限、商户档案、门店网点、用户档案、券模板、营销活动、用户领券、核销记录、商户结算、结算明细、打款记录、申诉工单、活动统计、操作日志、看板等控制器补齐权限收口
  - 为前端路由补充 `meta.roles`，并按角色动态收口侧边栏菜单与默认跳转
  - 当前尚未做真实登录验收；默认环境仍依赖本地 MySQL `local_voucher_117` 与 Redis

### 已修复：118 智能仓储 AGV 任务调度与库位优化系统
- 本轮新增：
  - 后端 `mvn.cmd test` 通过，但无自动化测试，日志为 `No tests to run`
  - 前端 `npm.cmd install` 与 `npm.cmd run build` 通过
  - 为 `JwtInterceptor` 补充 `userId/username/role` 请求属性透传
  - 为 `AuthService` 增加统一角色断言方法，并按 `ADMIN / DISPATCHER / KEEPER / MAINTAINER` 口径，为账号权限、仓库区域、库位档案、AGV车辆、充电站点、库存物料、入库订单、出库订单、AGV任务、路径规划、库位推荐、设备维保、异常告警、操作日志、看板等控制器补齐权限收口
  - 为前端路由补充 `meta.roles`，并按角色动态收口侧边栏菜单与默认跳转
  - 当前尚未做真实登录验收；默认环境仍依赖本地 MySQL `smart_warehouse_agv_118` 与 Redis

### 已修复：119 设备备件寿命预测与维保决策系统
- 本轮新增：
  - 后端 `mvn.cmd test` 通过，但无自动化测试，日志为 `No tests to run`
  - 前端 `npm.cmd install` 与 `npm.cmd run build` 通过
  - 为 `JwtInterceptor` 补充 `userId/username/role` 请求属性透传
  - 为 `AuthService` 增加统一角色断言方法，并按 `ADMIN / DEVICE_ADMIN / MAINTAINER / ANALYST` 口径，为账号权限、设备资产、备件目录、备件库存、入库记录、出库领用、使用记录、运行指标、故障记录、寿命预测、维保计划、采购申请、风险预警、操作日志、看板等控制器补齐权限收口
  - 为前端路由补充 `meta.roles`，并按角色动态收口侧边栏菜单与默认跳转
  - 当前尚未做真实登录验收；默认环境仍依赖本地 MySQL `spare_life_119` 与 Redis

### 已修复：120 数字孪生园区设备巡检管理系统
- 本轮新增：
  - 后端 `mvn.cmd test` 通过，但无自动化测试，日志为 `No tests to run`
  - 前端 `npm.cmd install` 与 `npm.cmd run build` 通过
  - 为 `JwtInterceptor` 补充 `userId/username/role` 请求属性透传
  - 为 `AuthService` 增加统一角色断言方法，并按 `ADMIN / INSPECTOR / ENGINEER / MANAGER` 口径，为账号权限、园区楼宇、孪生设备、巡检路线、巡检点位、巡检任务、巡检记录、缺陷报告、维修工单、传感数据、孪生模型、能耗监测、保养计划、操作日志、看板等控制器补齐权限收口
  - 为前端路由补充 `meta.roles`，并按角色动态收口侧边栏菜单与默认跳转
  - 当前尚未做真实登录验收；默认环境仍依赖本地 MySQL `digital_twin_park_120` 与 Redis

### 已修复：121 无人机巡检任务调度与缺陷上报平台
- 本轮新增：
  - 后端 `mvn.cmd test` 通过，但无自动化测试，日志为 `No tests to run`
  - 前端 `npm.cmd install` 与 `npm.cmd run build` 通过
  - 为 `JwtInterceptor` 补充 `userId/username/role` 请求属性透传
  - 为 `AuthService` 增加统一角色断言方法，并按 `ADMIN / PILOT / ENGINEER / MANAGER` 口径，为账号权限、无人机设备、飞手档案、巡检区域、航线规划、巡检任务、飞行记录、缺陷报告、缺陷图片、整改工单、电池站点、维保记录、风险预警、操作日志、看板等控制器补齐权限收口
  - 为前端路由补充 `meta.roles`，并按角色动态收口侧边栏菜单与默认跳转
  - 当前尚未做真实登录验收；默认环境仍依赖本地 MySQL `drone_inspection_121` 与 Redis

### 已修复：122 智慧工地安全巡检与隐患整改系统
- 本轮新增：
  - 后端 `mvn.cmd test` 通过，但无自动化测试，日志为 `No tests to run`
  - 前端 `npm.cmd install` 与 `npm.cmd run build` 通过
  - 为 `JwtInterceptor` 补充 `userId/username/role` 请求属性透传
  - 为 `AuthService` 增加统一角色断言方法，并按 `ADMIN / INSPECTOR / TEAM_LEADER / SUPERVISOR` 口径，为账号权限、工地项目、施工班组、安全员档案、巡检计划、巡检任务、隐患上报、整改工单、验收记录、安全培训、设备检查、防护用品、风险预警、操作日志、看板等控制器补齐权限收口
  - 为前端路由补充 `meta.roles`，并按角色动态收口侧边栏菜单与默认跳转
  - 当前尚未做真实登录验收；默认环境仍依赖本地 MySQL `smart_worksite_safety_122` 与 Redis

### 已修复：123 城市内涝监测与应急调度平台
- 本轮新增：
  - 后端 `mvn.cmd test` 通过，但无自动化测试，日志为 `No tests to run`
  - 前端 `npm.cmd install` 与 `npm.cmd run build` 通过
  - 为 `JwtInterceptor` 补充 `userId/username/role` 请求属性透传
  - 为 `AuthService` 增加统一角色断言方法，并按 `ADMIN / MONITOR / DISPATCHER / MANAGER` 口径，为账号权限、水位点位、雨量站点、排涝泵站、水位数据、雨量数据、预警规则、内涝预警、应急预案、调度任务、救援队伍、物资储备、避险点位、操作日志、看板等控制器补齐权限收口
  - 为前端路由补充 `meta.roles`，并按角色动态收口侧边栏菜单与默认跳转
  - 当前尚未做真实登录验收；默认环境仍依赖本地 MySQL `urban_flood_dispatch_123` 与 Redis

### 已修复：124 电动车充电桩预约与运维管理系统
- 本轮新增：
  - 后端 `mvn.cmd test` 通过，但无自动化测试，日志为 `No tests to run`
  - 前端 `npm.cmd install` 与 `npm.cmd run build` 通过
  - 为 `JwtInterceptor` 补充 `userId/username/role` 请求属性透传
  - 为 `AuthService` 增加统一角色断言方法，并按 `ADMIN / OPERATOR / MAINTAINER / OWNER` 口径，为账号权限、充电站点、充电桩位、用户车辆、预约订单、充电记录、故障报修、维修工单、保养计划、电价策略、支付记录、收益统计、能耗监测、操作日志、看板等控制器补齐权限收口
  - 为前端路由补充 `meta.roles`，并按角色动态收口侧边栏菜单与默认跳转
  - 当前尚未做真实登录验收；默认环境仍依赖本地 MySQL `smart_chargepile_safety_122` 与 Redis

### 已修复：125 智慧停车诱导与空位预测平台
- 本轮新增：
  - 后端 `mvn.cmd test` 通过，但无自动化测试，日志为 `No tests to run`
  - 前端 `npm.cmd install` 与 `npm.cmd run build` 通过
  - 为 `JwtInterceptor` 补充 `userId/username/role` 请求属性透传
  - 为 `AuthService` 增加统一角色断言方法，并按 `ADMIN / OPERATOR / GUARD / ANALYST` 口径，为账号权限、停车场、停车区域、车位档案、车位传感器、车主车辆、预约订单、停车记录、支付记录、空位预测、诱导屏幕、导航路线、故障报修、操作日志、看板等控制器补齐权限收口
  - 为前端路由补充 `meta.roles`，并按角色动态收口侧边栏菜单与默认跳转
  - 当前尚未做真实登录验收；默认环境仍依赖本地 MySQL `smart_parking_125` 与 Redis

### 已修复：126 家庭能源用电分析与节能建议平台
- 本轮新增：
  - 后端 `mvn.cmd test` 通过，但无自动化测试，日志为 `No tests to run`
  - 前端 `npm.cmd install` 与 `npm.cmd run build` 通过
  - 为 `JwtInterceptor` 补充 `userId/username/role` 请求属性透传
  - 为 `AuthService` 增加统一角色断言方法，并按 `ADMIN / RESIDENT / ANALYST / MAINTAINER` 口径，为账号权限、家庭档案、家庭成员、智能电表、用电设备、用电读数、电费账单、设备用电、能耗预算、节能建议、异常预警、碳排统计、维修工单、操作日志、看板等控制器补齐权限收口
  - 为前端路由补充 `meta.roles`，并按角色动态收口侧边栏菜单与默认跳转
  - 当前尚未做真实登录验收；默认环境仍依赖本地 MySQL `home_energy_126` 与 Redis

### 已修复：127 企业碳排放核算与减排任务管理系统
- 本轮新增：
  - 后端 `mvn.cmd test` 通过，但无自动化测试，日志为 `No tests to run`
  - 前端 `npm.cmd install` 与 `npm.cmd run build` 通过
  - 为 `JwtInterceptor` 补充 `userId/username/role` 请求属性透传
  - 为 `AuthService` 增加统一角色断言方法，并按 `ADMIN / ACCOUNTANT / AUDITOR / MANAGER` 口径，为账号权限、企业档案、排放因子、核算周期、能源消耗、排放记录、减排任务、减排措施、碳配额、核查报告、佐证附件、预警规则、碳排预警、操作日志、看板等控制器补齐权限收口
  - 为前端路由补充 `meta.roles`，并按角色动态收口侧边栏菜单与默认跳转
  - 当前尚未做真实登录验收；默认环境仍依赖本地 MySQL `carbon_accounting_127` 与 Redis

### 已修复：128 ESG 数据填报与可视化报告系统
- 本轮新增：
  - 后端 `mvn.cmd test` 通过，但无自动化测试，日志为 `No tests to run`
  - 前端 `npm.cmd install` 与 `npm.cmd run build` 通过
  - 确认 `JwtInterceptor` 已透传 `userId/username/role`
  - 确认 `AuthService` 已具备统一角色断言方法，并按 `ADMIN / EDITOR / REVIEWER / ESG_MANAGER` 口径，为账号权限、指标库、披露模板、报告周期、企业填报、指标数据、佐证材料、审核任务、评分模型、ESG评分、改进任务、利益相关方反馈、报告导出、操作日志、看板等控制器补齐权限收口
  - 确认前端路由已补充 `meta.roles`，并按角色动态收口侧边栏菜单与默认跳转
  - 当前尚未做真实登录验收；默认环境仍依赖本地 MySQL `esg_report_128` 与 Redis
  - 下一项目为 `129`

### 已修复：129 水产养殖环境监测与投喂预警系统
- 本轮新增：
  - 后端 `mvn.cmd test` 通过，但无自动化测试，日志为 `No tests to run`
  - 前端 `npm.cmd install` 与 `npm.cmd run build` 通过
  - 为 `JwtInterceptor` 补充 `userId/username/role` 请求属性透传
  - 为 `AuthService` 增加统一角色断言方法，并按 `ADMIN / BREEDER / TECHNICIAN / MANAGER` 口径，为账号权限、养殖池塘、传感设备、水质读数、投喂计划、投喂记录、鱼苗批次、生长采样、病害预警、用药记录、养殖设备、水质规则、产量统计、操作日志、看板等控制器补齐权限收口
  - 为前端路由补充 `meta.roles`，并按角色动态收口侧边栏菜单与默认跳转
  - 为 `SysUser.password` 增加 `@JsonIgnore`，避免登录与用户查询链路泄露密码字段
  - 当前尚未做真实登录验收；默认环境仍依赖本地 MySQL `aquaculture_monitor_129` 与 Redis
  - 下一项目为 `130`

### 已修复：130 温室大棚物联网控制与病害预警系统
- 本轮新增：
  - 后端 `mvn.cmd test` 通过，但无自动化测试，日志为 `No tests to run`
  - 前端 `npm.cmd install` 与 `npm.cmd run build` 通过
  - 为 `JwtInterceptor` 补充 `userId/username/role` 请求属性透传
  - 为 `AuthService` 增加统一角色断言方法，并按 `ADMIN / GROWER / TECHNICIAN / MANAGER` 口径，为账号权限、温室档案、作物批次、环境传感器、环境读数、灌溉任务、施肥计划、虫害预警、病害诊断、控制设备、远程指令、采收记录、维护工单、操作日志、看板等控制器补齐权限收口
  - 为前端路由补充 `meta.roles`，并按角色动态收口侧边栏菜单与默认跳转
  - 为 `SysUser.password` 增加 `@JsonIgnore`，避免登录与用户查询链路泄露密码字段
  - 当前尚未做真实登录验收；默认环境仍依赖本地 MySQL `greenhouse_iot_130` 与 Redis
  - 下一项目为 `131`

### 已修复：131 药品不良反应上报与随访管理系统
- 本轮新增：
  - 后端 `mvn.cmd test` 通过，但无自动化测试，日志为 `No tests to run`
  - 前端 `npm.cmd install` 与 `npm.cmd run build` 通过
  - 为 `JwtInterceptor` 补充 `userId/username/role` 请求属性透传
  - 为 `AuthService` 增加统一角色断言方法，并按 `ADMIN / REPORTER / REVIEWER / DOCTOR` 口径，为账号权限、患者档案、药品目录、上报人档案、不良反应上报、反应症状、风险评估、随访计划、随访记录、病例复核、处置建议、科室信息、统计报表、操作日志、看板等控制器补齐权限收口
  - 为前端路由补充 `meta.roles`，并按角色动态收口侧边栏菜单与默认跳转
  - 为 `SysUser.password` 增加 `@JsonIgnore`，避免登录与用户查询链路泄露密码字段
  - 当前尚未做真实登录验收；默认环境仍依赖本地 MySQL `drug_reaction_131` 与 Redis
  - 下一项目为 `132`

### 已修复：132 医疗器械借用消毒与追踪管理系统
- 本轮新增：
  - 后端 `mvn.cmd test` 通过，但无自动化测试，日志为 `No tests to run`
  - 前端 `npm.cmd install` 与 `npm.cmd run build` 通过
  - 为 `JwtInterceptor` 补充 `userId/username/role` 请求属性透传
  - 为 `AuthService` 增加统一角色断言方法，并按 `ADMIN / NURSE / STERILIZER / MANAGER` 口径，为账号权限、器械档案、器械分类、科室信息、借用申请、借用记录、归还记录、消毒批次、消毒记录、二维码追踪、维护记录、巡检任务、风险预警、操作日志、看板等控制器补齐权限收口
  - 为前端路由补充 `meta.roles`，并按角色动态收口侧边栏菜单与默认跳转
  - 为 `SysUser.password` 增加 `@JsonIgnore`，避免登录与用户查询链路泄露密码字段
  - 当前尚未做真实登录验收；默认环境仍依赖本地 MySQL `medical_device_132` 与 Redis
  - 下一项目为 `133`

### 已修复：133 实验室耗材采购审批与库存预警系统
- 本轮新增：
  - 后端 `mvn.cmd test` 通过，但无自动化测试，日志为 `No tests to run`
  - 前端 `npm.cmd install` 与 `npm.cmd run build` 通过
  - 为 `JwtInterceptor` 补充 `userId/username/role` 请求属性透传
  - 为 `AuthService` 增加统一角色断言方法，并按 `ADMIN / KEEPER / TEACHER / APPROVER` 口径，为账号权限、耗材目录、供应商档案、实验室、库存台账、采购申请、采购审批、采购订单、入库记录、领用记录、库存盘点、预警规则、库存预警、操作日志、看板等控制器补齐权限收口
  - 为前端路由补充 `meta.roles`，并按角色动态收口侧边栏菜单与默认跳转
  - 为 `SysUser.password` 增加 `@JsonIgnore`，避免登录与用户查询链路泄露密码字段
  - 补回项目缺失的 `133-backend/src/main/java/com/labconsumable/service/BaseCrudService.java`，修复后端原生编译失败
  - 当前尚未做真实登录验收；默认环境仍依赖本地 MySQL `lab_consumable_133` 与 Redis
  - 下一项目为 `134`

### 已修复：134 科研项目经费报销与成果管理系统
- 本轮新增：
  - 后端 `mvn.cmd test` 通过，但无自动化测试，日志为 `No tests to run`
  - 前端 `npm.cmd install` 与 `npm.cmd run build` 通过
  - 为 `JwtInterceptor` 补充 `userId/username/role` 请求属性透传
  - 为 `AuthService` 增加统一角色断言方法，并按 `ADMIN / RESEARCHER / FINANCE / LEADER` 口径，为账号权限、科研项目、预算科目、经费预算、报销申请、发票记录、审批任务、支付记录、科研成果、论文成果、专利成果、绩效统计、风险预警、操作日志、看板等控制器补齐权限收口
  - 为前端路由补充 `meta.roles`，并按角色动态收口侧边栏菜单与默认跳转
  - 为 `SysUser.password` 增加 `@JsonIgnore`，避免登录与用户查询链路泄露密码字段
  - 当前尚未做真实登录验收；默认环境仍依赖本地 MySQL `research_fund_134` 与 Redis
  - 下一项目为 `135`

### 已修复：135 学术会议投稿评审与日程管理系统
- 本轮新增：
  - 后端 `mvn.cmd test` 通过，但无自动化测试，日志为 `No tests to run`
  - 前端 `npm.cmd install` 与 `npm.cmd run build` 通过
  - 为 `JwtInterceptor` 补充 `userId/username/role` 请求属性透传
  - 为 `AuthService` 增加统一角色断言方法，并按 `ADMIN / MANAGER / REVIEWER / SECRETARY` 口径，为账号权限、会议信息、征稿通知、作者档案、论文投稿、审稿专家、审稿分配、盲审记录、录用通知、参会报名、会场安排、会议日程、签到记录、操作日志、看板等控制器补齐权限收口
  - 为前端路由补充 `meta.roles`，并按角色动态收口侧边栏菜单与默认跳转
  - 为 `SysUser.password` 增加 `@JsonIgnore`，避免登录与用户查询链路泄露密码字段
  - 补回项目缺失的 `135-backend/src/main/java/com/conferencereview/service/BaseCrudService.java`，修复后端原生编译失败
  - 当前尚未做真实登录验收；默认环境仍依赖本地 MySQL `conference_review_135` 与 Redis
  - 下一项目为 `136`

### 已修复：136 导师课题双选与开题过程管理系统
- 本轮新增：
  - 后端 `mvn.cmd test` 通过，但无自动化测试，日志为 `No tests to run`
  - 前端 `npm.cmd install` 与 `npm.cmd run build` 通过
  - 为 `JwtInterceptor` 补充 `userId/username/role` 请求属性透传
  - 为 `AuthService` 增加统一角色断言方法，并按 `ADMIN / TEACHER / STUDENT / AFFAIRS` 口径，为账号权限、课题发布、导师档案、学生档案、课题申请、双选审核、双选确认、任务书下达、开题材料、开题答辩、中期检查、指导记录、节点通知、操作日志、统计看板补齐权限收口
  - 为前端路由补充 `meta.roles`，并按角色动态收口侧边栏菜单与默认跳转
  - 为 `SysUser.password` 增加 `@JsonIgnore`，避免登录与用户查询链路泄露密码字段
  - 当前尚未做真实登录验收；默认环境仍依赖本地 MySQL `topic_selection_136` 与 Redis
  - 下一项目为 `137`

### 已修复：137 大学生创新创业项目孵化管理平台
- 本轮新增：
  - 后端 `mvn.cmd test` 通过，但无自动化测试，日志为 `No tests to run`
  - 前端 `npm.cmd install` 与 `npm.cmd run build` 通过
  - 为 `JwtInterceptor` 补充 `userId/username/role` 请求属性透传
  - 为 `AuthService` 增加统一角色断言方法，并按 `ADMIN / MENTOR / STUDENT / JUDGE` 口径，为账号权限、孵化项目、导师档案、团队档案、项目申报、孵化计划、导师辅导、路演活动、路演评分、经费记录、里程碑任务、成果展示、孵化通知、操作日志、统计看板补齐权限收口
  - 为前端路由补充 `meta.roles`，并按角色动态收口侧边栏菜单与默认跳转，同时修正“路演活动”菜单索引误指向 `/review` 的残留
  - 补齐前端 store 的 `setAuth` 兼容动作，并让登录成功后按角色首页跳转，修复登录态未持久化的运行时问题
  - 为 `SysUser.password` 增加 `@JsonIgnore`，避免登录与用户查询链路泄露密码字段
  - 补回项目缺失的 `137-backend/src/main/java/com/innovationhub/service/BaseCrudService.java`，修复后端原生编译失败
  - 当前尚未做真实登录验收；默认环境仍依赖本地 MySQL `innovation_incubator_137` 与 Redis
  - 下一项目为 `138`

### 已修复：138 在线考试反作弊行为分析与证据管理系统
- 本轮新增：
  - 后端 `mvn.cmd test` 通过，但无自动化测试，日志为 `No tests to run`
  - 前端 `npm.cmd install` 与 `npm.cmd run build` 通过
  - 为 `JwtInterceptor` 补充 `userId/username/role` 请求属性透传
  - 为 `AuthService` 增加统一角色断言方法，并按 `ADMIN / INVIGILATOR / CANDIDATE / REVIEWER` 口径，为账号权限、考试计划、监考档案、考生档案、考试场次、异常行为、证据记录、复核任务、复核结论、预警规则、设备监测、违规申诉、告警通知、操作日志、统计看板补齐权限收口
  - 为前端路由补充 `meta.roles`，并按角色动态收口侧边栏菜单与默认跳转，同时修正“告警通知”菜单索引与登录页 `setAuth` 调用不匹配的运行时问题
  - 为 `SysUser.password` 增加 `@JsonIgnore`，避免登录与用户查询链路泄露密码字段
  - 为 `GlobalExceptionHandler` 拆分业务异常与系统异常返回，避免将未处理异常明文透传给前端
  - 补充记录：`138-backend/sql/init.sql` 的业务表仍带有旧项目模板残留，本轮未重建，若要 fresh DB 初始化还需单独补脚本
  - 当前尚未做真实登录验收；默认环境仍依赖本地 MySQL `exam_guard_138` 与 Redis
  - 下一项目为 `139`

### 已修复：139 企业培训学习路径与能力认证管理系统
- 本轮新增：
  - 后端 `mvn.cmd test` 通过，但无自动化测试，日志为 `No tests to run`
  - 前端 `npm.cmd run build` 通过；仍有大包 warning，但不阻塞当前巡检结论
  - 为 `JwtInterceptor` 补充 `userId/username/role` 请求属性透传
  - 为 `AuthService` 增加统一角色断言方法，并按 `ADMIN / TRAINER / EMPLOYEE / MANAGER` 口径，为账号权限、培训项目、课程目录、学员档案、学习路径、学习任务、选课报名、考核考试、成绩记录、技能标签、胜任力画像、认证记录、学习提醒、操作日志、统计看板补齐权限收口
  - 为 `SysUser.password` 增加 `@JsonIgnore`，避免登录返回和用户列表链路泄露密码字段
  - 为 `GlobalExceptionHandler` 拆分业务异常与系统异常返回，避免将未处理异常明文透传给前端
  - 补齐前端 store 的 `setAuth` 兼容动作，并让登录成功后按角色首页跳转，修复登录态持久化与角色首页跳转问题
  - 为前端路由补充 `meta.roles`，并让侧边栏菜单与业务页按钮按角色动态收口，避免无权限角色继续看到新增、编辑、删除和审批类动作
  - 补回项目缺失的 `139-backend/src/main/java/com/learningpath/service/BaseCrudService.java`，恢复后端原生编译能力
  - 重写 `139-backend/sql/init.sql`、业务实体字段、服务层关键字列和前端 12 个业务页，清除 `133` 实验室耗材项目残留，恢复“企业培训学习路径与能力认证”主题语义
  - 轻量残留扫描未发现 `labconsumable / consumable / supplier / purchase_*` 等旧模板核心关键词；未发现 `System.out.print*`、`printStackTrace` 或通配符 CORS 残留
  - 当前尚未做真实登录验收；默认环境仍依赖本地 MySQL `learning_path_139` 与 Redis
  - 下一项目为 `140`

### 已修复：140 电子合同签署与印章审批管理系统
- 本轮新增：
  - 后端 `mvn.cmd test` 通过，但无自动化测试，日志为 `No tests to run`
  - 前端补齐 `npm.cmd install` 后，`npm.cmd run build` 通过；仍有大包 warning，但不阻塞当前巡检结论
  - 为 `JwtInterceptor` 补充 `userId/username/role` 请求属性透传
  - 为 `AuthService` 增加统一角色断言方法，并按 `ADMIN / LEGAL / APPLICANT / APPROVER` 口径，为账号权限、合同模板、相对方档案、签署方档案、合同草稿、用印申请、审批流程、合同签署、用印记录、归档记录、到期提醒、风险条款、合同通知、操作日志、统计看板补齐权限收口
  - 为 `SysUser.password` 增加 `@JsonIgnore`，避免登录返回和用户列表链路泄露密码字段；同时修复用户编辑时密码留空会被更新为 `null` 的问题
  - 为 `GlobalExceptionHandler` 拆分业务异常与系统异常返回，避免将未处理异常明文透传给前端
  - 补齐前端 store 的 `setAuth` 兼容动作，并让登录成功后按角色首页跳转，修复登录态持久化与角色首页跳转问题
  - 为前端路由补充 `meta.roles`，并让侧边栏菜单、通用 `DataPage` 和 12 个业务页按钮按角色动态收口，避免无权限角色继续看到新增、编辑、删除和审批类动作
  - 重写 `140-backend/sql/init.sql`、业务实体对应表结构与前端 12 个业务页字段，清除 `134` 科研经费与 `136` 导师双选模板残留，恢复“电子合同签署与印章审批管理”主题语义
  - 轻量残留扫描未发现 `research_* / budget_* / payment_* / approval_task / topic / mutual / 课题 / 导师 / 学院` 等旧模板核心关键词；未发现 `System.out.print*`、`printStackTrace` 或通配符 CORS 残留
  - 当前尚未做真实登录验收；默认环境仍依赖本地 MySQL `electronic_contract_140` 与 Redis
  - 下一项目为 `141`

### 已修复：141 固定资产 RFID 盘点与借用归还系统
- 本轮新增：
  - 后端 `mvn.cmd test` 通过，但无自动化测试，日志为 `No tests to run`
  - 前端补齐 `npm.cmd install` 后，`npm.cmd run build` 通过；仍有大包 warning，但不阻塞当前巡检结论
  - 为 `pom.xml`、`JwtUtils` 和 `TokenService` 修正 `learning-path-139` / `labconsumable` 模板残留，使 artifactId、JWT secret 和 Redis token 前缀回到 `asset-rfid-141` 主题
  - 为 `JwtInterceptor` 补充 `userId/username/role` 请求属性透传
  - 为 `AuthService` 增加统一角色断言方法，并按 `ADMIN / ASSET_ADMIN / BORROWER / AUDITOR` 口径，为账号权限、资产档案、资产分类、RFID 标签、存放位置、盘点任务、盘点明细、借用申请、归还记录、维修记录、折旧记录、闲置处置、预警通知、操作日志、统计看板补齐权限收口
  - 为 `SysUser.password` 增加 `@JsonIgnore`，避免登录返回和用户列表链路泄露密码字段；同时修复用户编辑时密码留空会被更新为 `null` 的问题
  - 为 `GlobalExceptionHandler` 拆分业务异常与系统异常返回，避免将未处理异常明文透传给前端
  - 补回项目原生缺失的 `141-backend/src/main/java/com/assetrfid/service/BaseCrudService.java`，恢复后端原生编译能力
  - 补齐前端 store 的 `setAuth` 兼容动作，并让登录成功后按角色首页跳转，修复登录态持久化与角色首页跳转问题
  - 为前端路由补充 `meta.roles`，并让侧边栏菜单、通用 `DataPage` 和 12 个业务页按钮按角色动态收口
  - 重写 `141-backend/sql/init.sql`、业务实体字段、服务层关键字列与前端 12 个业务页字段，清除 `133` 实验室耗材与 `135` 学术会议模板残留，恢复“固定资产 RFID 盘点与借用归还”主题语义
  - 轻量残留扫描未发现 `consumable / supplier / purchase_* / lab_room / stock_warning / 投稿 / 审稿 / 签到 / 会务` 等旧模板核心关键词；未发现 `System.out.print*`、`printStackTrace` 或通配符 CORS 残留
  - 当前尚未做真实登录验收；默认环境仍依赖本地 MySQL `asset_rfid_141` 与 Redis
  - 下一项目为 `142`

### 已修复：142 车辆保险理赔材料审核与进度跟踪系统
- 本轮新增：
  - 后端 `mvn.cmd test` 通过，但无自动化测试，日志为 `No tests to run`
  - 前端补齐 `npm.cmd install` 后，`npm.cmd run build` 通过；仍有大包 warning，但不阻塞当前巡检结论
  - 为 `pom.xml`、`JwtUtils` 和 `TokenService` 修正 `electronic-contract-140` 模板残留，使 artifactId、JWT secret 和 Redis token 前缀回到 `vehicle-claim-142` 主题
  - 为 `JwtInterceptor` 补充 `userId/username/role` 请求属性透传
  - 为 `AuthService` 增加统一角色断言方法，并按 `ADMIN / LEGAL / APPLICANT / APPROVER` 口径，为账号权限、保险保单、车辆档案、客户档案、理赔申请、事故报案、材料清单、材料审核、定损记录、赔付记录、进度跟踪、回访记录、消息通知、操作日志、统计看板补齐权限收口
  - 为 `SysUser.password` 增加 `@JsonIgnore`，避免登录返回和用户列表链路泄露密码字段；同时修复用户编辑时密码留空会被更新为 `null` 的问题
  - 为 `GlobalExceptionHandler` 拆分业务异常与系统异常返回，避免将未处理异常明文透传给前端
  - 补齐前端 store 的 `setAuth` 兼容动作，并让登录成功后按角色首页跳转，修复登录态持久化与角色首页跳转问题
  - 为前端路由补充 `meta.roles`，并让侧边栏菜单、通用 `DataPage` 和 12 个业务页按钮按角色动态收口，避免无权限角色继续看到新增、编辑、删除和审批类动作
  - 重写 `142-backend/sql/init.sql`、12 个业务实体字段、注解 SQL Mapper 与前端 12 个业务页字段，清除 `134` 科研经费与 `136` 选题过程模板残留，恢复“车辆保险理赔材料审核与进度跟踪”主题语义
  - 轻量残留扫描未发现 `research_* / budget_* / expense_* / invoice_* / approval_task / payment_record / paper_record / patent_record / performance_statistic / risk_warning / 课题 / 导师 / 开题 / 中期检查` 等旧模板核心关键词；未发现 `System.out.print*`、`printStackTrace` 或通配符 CORS 残留
  - 当前尚未做真实登录验收；默认环境仍依赖本地 MySQL `vehicle_claim_142` 与 Redis
  - 下一项目为 `143`

### 已修复：143 社区公益时间银行互助服务平台
- 本轮新增：
  - 后端 `mvn.cmd test` 通过，但无自动化测试，日志为 `No tests to run`
  - 前端补齐 `npm.cmd install` 后，`npm.cmd run build` 通过；仍有大包 warning，但不阻塞当前巡检结论
  - 为 `pom.xml`、`JwtUtils` 和 `TokenService` 修正旧模板残留，使 artifactId、JWT secret 和 Redis token 前缀回到 `time-bank-143` / `timebank` 主题
  - 为 `JwtInterceptor` 补充 `userId/username/role` 请求属性透传
  - 为 `AuthService` 增加统一角色断言方法，并按 `ADMIN / RESIDENT / VOLUNTEER / MANAGER` 口径，为账号权限、服务项目、服务分类、居民档案、志愿者档案、服务预约、服务签到、时长账户、互助兑换、评价反馈、公益活动、积分规则、站内通知、操作日志、统计看板补齐权限收口
  - 为 `SysUser.password` 增加 `@JsonIgnore`，避免登录返回和用户列表链路泄露密码字段；同时修复用户编辑资料时密码留空会被更新为 `null`、状态会被误覆盖的数据问题
  - 为 `GlobalExceptionHandler` 拆分业务异常与系统异常返回，避免把未处理异常消息明文透传给前端
  - 补回项目原生缺失的 `143-backend/src/main/java/com/timebank/service/BaseCrudService.java`，恢复后端原生编译能力
  - 重写 `143-backend/sql/init.sql`、12 个业务实体字段、服务层关键字列与前端 12 个业务页字段，清除 `133` 实验室耗材与 `135` 学术会议模板残留，恢复“社区公益时间银行互助服务平台”主题语义
  - 补齐前端 store 的 `setAuth` 兼容动作、登录成功后的角色首页跳转、路由 `meta.roles`、侧边栏菜单收口、通用 `DataPage` 权限开关，以及 `SysUser / OperationLog` 和 12 个业务页的按钮权限显示逻辑
  - 轻量残留扫描未发现 `consumable / supplier / lab_room / trainer / employee / 会议信息 / 审稿 / 盲审 / 论文投稿` 等旧模板核心关键词；未发现 `System.out.print*`、`printStackTrace` 或通配符 CORS 残留
  - 当前尚未做真实登录验收；默认环境仍依赖本地 MySQL `time_bank_143` 与 Redis
  - 下一项目为 `144`

### 已修复：144 无障碍出行路线规划与志愿协助平台
- 本轮新增：
  - 后端 `mvn.cmd test` 通过，但无自动化测试，日志为 `No tests to run`
  - 前端补齐 `npm.cmd install` 后，`npm.cmd run build` 通过；仍有大包 warning，但不阻塞当前巡检结论
  - 为 `pom.xml`、启动类 `AccessibleTravelApplication`、`JwtUtils` 和 `TokenService` 修正旧模板残留，使 artifactId、启动入口、JWT secret 和 Redis token 前缀回到 `accessible-travel-144` / `accessibletravel` 主题
  - 为 `JwtInterceptor` 补充 `userId/username/role` 请求属性透传
  - 为 `AuthService` 增加统一角色断言方法，并按 `ADMIN / TRAVELER / VOLUNTEER / DISPATCHER` 口径，为账号权限、无障碍路线、设施点位、出行用户、协助预约、志愿者档案、路线方案、协助任务、服务签到、评价反馈、应急联系人、行程记录、消息通知、操作日志、统计看板补齐权限收口
  - 为 `SysUser.password` 增加 `@JsonIgnore`，避免登录返回和用户列表链路泄露密码字段；同时保留用户编辑时密码留空不误覆盖的修复
  - 为 `GlobalExceptionHandler` 拆分业务异常与系统异常返回，避免把未处理异常消息明文透传给前端
  - 重写 `144-backend/sql/init.sql`、12 个业务实体字段、注解 SQL Mapper 与服务层变量命名，清除 `134/136/140/142` 等旧模板残留，恢复“无障碍出行路线规划与志愿协助平台”主题语义
  - 补齐前端 store 的 `setAuth` 兼容动作、登录成功后的角色首页跳转、路由 `meta.roles`、侧边栏菜单收口、通用 `DataPage` 权限开关，以及 12 个业务页的字段、文案与动作按钮权限逻辑
  - 轻量残留扫描未发现 `research_* / budget_* / expense_claim / invoice_record / approval_task / payment_record / paper_record / patent_record / performance_statistic / risk_warning / 课题 / 导师 / 开题 / 中期检查 / 答辩` 等旧模板核心关键词；未发现 `System.out.print*`、`printStackTrace` 或通配符 CORS 残留
  - 当前尚未做真实登录验收；默认环境仍依赖本地 MySQL `accessible_travel_144` 与 Redis
  - 下一项目为 `145`

### 已修复：145 城市噪声投诉监测与执法协同平台
- 本轮新增：
  - 后端 `mvn.cmd test` 通过，日志为 `No tests to run`，仅有不阻塞的 `rdc` profile warning
  - 前端 `npm.cmd install`、`npm.cmd run build` 通过；构建仍有大包 warning，但不阻塞当前巡检结论
  - 为 `JwtInterceptor` 已透传的 `role` 请求属性补齐控制器侧使用，并通过 `AuthService` 为 15 个控制器统一补上服务端角色权限校验，角色口径固定为 `ADMIN / CITIZEN / OFFICER / SUPERVISOR`
  - 重写前端 `router/index.js`、`Layout.vue`、`Login.vue`、`store/user.js`、`components/DataPage.vue`，补齐角色首页跳转、动态菜单、登录演示账号文案与通用按钮权限收口
  - 重写 `SysUser / ComplaintTicket / MonitoringSite / NoiseSource / OfficerProfile / HandlingTask / PatrolRecord / RectificationNotice / RetestRecord / PenaltyDecision / PublicFeedback / WarningRule / PublicNotice / OperationLog` 等业务页字段、文案与动作可见性，清除旧模板残留
  - 修复 `145-backend/src/main/java/com/noisemonitor/entity/PenaltyDecision.java` 缺少 `BigDecimal` 导入导致的原生编译失败
  - 将 `145-backend/src/main/java/com/noisemonitor/config/WebMvcConfig.java` 的通配符 CORS 收紧为本地前端地址 `localhost/127.0.0.1:3145` 与 `4173`
  - 生成并纳入前端锁定文件 `145-frontend/package-lock.json`
  - 轻量残留扫描未发现 `trainer / employee / conference / review / consumable / supplier / labconsumable / research_fund / topic_selection / electronic_contract / vehicle_claim / time_bank / accessible_travel` 等旧模板核心关键词；未发现 `System.out.print*`、`printStackTrace`
  - 当前尚未做真实登录验收；默认环境仍依赖本地 MySQL `noise_monitor_145` 与 Redis
  - 下一项目为 `146`

### 已修复：146 食品安全抽检任务与结果公示平台
- 本轮新增：
  - 后端 `mvn.cmd test` 通过，日志为 `No tests to run`，仅有不阻塞的 `rdc` profile warning
  - 前端 `npm.cmd install`、`npm.cmd run build` 通过；构建仍有大包 warning，但不阻塞当前巡检结论
  - 修复 `146-backend/src/main/java/com/foodinspect/FoodInspectApplication.java` 中启动类仍命名为 `VehicleSamplingTask` 的原生编译问题，并将 `pom.xml` 的 artifactId、`JwtUtils` 的 secret、`TokenService` 的 Redis token 前缀统一切回 `food-inspection-146` / `foodinspect` 主题
  - 为 `JwtInterceptor` 补充 `userId/username/role` 请求属性透传，为 `AuthService` 增加统一角色断言，并按 `ADMIN / INSPECTOR / MERCHANT / REVIEWER` 口径为 15 个控制器补齐服务端角色权限校验
  - 为 `GlobalExceptionHandler` 拆分业务异常与系统异常返回，并将 `WebMvcConfig` 的通配符 CORS 收紧为本地前端地址 `localhost/127.0.0.1:3146` 与 `4173`
  - 为 `SysUser.password` 增加 `@JsonIgnore`，同时修复用户新增默认密码与编辑时密码留空不被覆盖的问题
  - 重写 `146-backend/sql/init.sql`、12 个业务实体字段、12 个注解 SQL Mapper 与对应服务层变量命名，彻底清除 `134` 科研经费与 `140` 电子合同模板残留，恢复“食品安全抽检任务与结果公示”主题语义
  - 重写前端 `api/index.js`、`router/index.js`、`Layout.vue`、`Login.vue`、`store/user.js`、`components/DataPage.vue`，补齐角色首页跳转、动态菜单、接口路径和通用按钮权限收口
  - 重写 `SysUser / InspectionPlan / FoodItem / MerchantProfile / SamplingTask / AgencyProfile / SampleRecord / TestResult / RecheckApplication / DisposalRecord / PublicReport / RiskWarning / InspectionNotice / OperationLog` 等页面字段、文案与动作可见性，清除旧模板残留
  - 生成并纳入前端锁定文件 `146-frontend/package-lock.json`
  - 轻量残留扫描未发现 `research_* / budget_* / expense_* / invoice_* / approval_task / payment_record / paper_record / patent_record / performance_statistic / electronic-contract-140 / LEGAL / APPLICANT / APPROVER` 等旧模板核心关键词；未发现 `System.out.print*`、`printStackTrace` 或通配符 CORS 残留
  - 当前尚未做真实登录验收；默认环境仍依赖本地 MySQL `food_inspection_146` 与 Redis
  - 下一项目为 `147`

### 已修复：147 校园心理咨询预约与危机干预管理系统
- 本轮新增：
  - 后端 `mvn.cmd test` 通过，日志为 `No tests to run`，仅有不阻塞的 `rdc` profile warning
  - 前端 `npm.cmd run build` 通过；构建仍有大包 warning，但不阻塞当前巡检结论
  - 为 `pom.xml`、`JwtUtils` 与 `TokenService` 修正 `learning-path-139` 模板残留，使 artifactId、JWT secret 和 Redis token 前缀回到 `campus-psychology-147` / `psychologycare` 主题
  - 为 `JwtInterceptor` 补充 `userId/username/role` 请求属性透传，为 `AuthService` 增加统一角色断言，并按 `ADMIN / TEACHER / STUDENT / COUNSELOR` 口径为 15 个控制器补齐服务端角色权限校验
  - 为 `GlobalExceptionHandler` 拆分业务异常与系统异常返回，并将 `WebMvcConfig` 的通配符 CORS 收紧为 `http://localhost:3147`、`http://127.0.0.1:3147`、`http://localhost:4173`、`http://127.0.0.1:4173`
  - 为 `SysUser.password` 增加 `@JsonIgnore`，避免登录返回和用户列表链路泄露密码字段；同时修复用户新增默认密码与编辑资料时密码留空不被误覆盖的问题
  - 补回缺失的 `147-backend/src/main/java/com/psychologycare/service/BaseCrudService.java` 与 `147-backend/src/main/java/com/psychologycare/entity/CounselRoom.java`，并移除错误的 `ServiceCategory.java`
  - 重写 `147-backend/sql/init.sql`、12 个业务实体字段与服务层关键字列，清除旧模板残留，恢复“校园心理咨询预约与危机干预管理系统”主题语义
  - 重写前端 `router/index.js`、`Layout.vue`、`Login.vue`、`store/user.js` 与 `components/DataPage.vue`，补齐角色首页跳转、动态菜单、登录示例账号文案与通用按钮权限收口
  - 重写 `SysUser / CounselCase / CounselRoom / StudentProfile / DutySchedule / AppointmentRequest / CounselRecord / AssessmentQuestionnaire / RiskAssessment / CrisisIntervention / FamilyCommunication / FollowUpPlan / SystemNotice / OperationLog` 等页面字段、文案与动作可见性，清除旧模板残留
  - 精确残留扫描未发现 `consumable / supplier / conference / review / paper / labconsumable / learning-path-139 / 投稿 / 审稿 / 会场 / 征稿 / 盲审` 等旧模板核心关键词；未发现 `System.out.print*`、`printStackTrace` 或通配符 CORS 残留
  - 当前尚未做真实登录验收；默认环境仍依赖本地 MySQL `campus_psychology_147` 与 Redis
  - 下一项目为 `148`

### 已修复：148 社区养老服务派单与健康随访管理系统
- 本轮新增：
  - 后端 `mvn.cmd test` 通过，日志为 `No tests to run`，仅有不阻塞的 `rdc` profile warning
  - 前端 `npm.cmd install`、`npm.cmd run build` 通过；构建仍有大包 warning，但不阻塞当前巡检结论
  - 修复 `148-backend/src/main/java/com/eldercare/ElderCareApplication.java` 中启动类仍命名为 `VehicleServiceOrder` 的原生编译问题，并为 `pom.xml` 补齐 Lombok 注解处理配置，恢复项目原生编译能力
  - 为 `pom.xml`、`JwtUtils` 与 `TokenService` 修正 `electronic-contract-140` / `researchfund` 模板残留，使 artifactId、JWT secret 和 Redis token 前缀回到 `elder-service-148` / `eldercare` 主题
  - 为 `JwtInterceptor` 补充 `userId/username/role` 请求属性透传，为 `AuthService` 增加统一角色断言，并按 `ADMIN / CONSULTANT / CAREGIVER / FAMILY` 口径为 15 个控制器补齐服务端角色权限校验
  - 为 `GlobalExceptionHandler` 拆分业务异常与系统异常返回，并将 `WebMvcConfig` 的通配符 CORS 收紧为 `http://localhost:3148`、`http://127.0.0.1:3148`、`http://localhost:4173`、`http://127.0.0.1:4173`
  - 为 `SysUser.password` 增加 `@JsonIgnore`，同时修复用户新增默认密码与编辑资料时密码留空不被覆盖的问题
  - 重写 `148-backend/sql/init.sql`、12 个业务实体字段、12 个注解 SQL Mapper 与对应服务层变量命名，彻底清除科研经费与电子合同模板残留，恢复“社区养老服务派单与健康随访管理系统”主题语义
  - 重写前端 `api/index.js`、`router/index.js`、`Layout.vue`、`Login.vue`、`store/user.js`、`components/DataPage.vue`，补齐角色首页跳转、动态菜单、接口路径和通用按钮权限收口
  - 重写 `SysUser / ServicePackage / ElderProfile / CaregiverProfile / ServiceOrder / CareTeam / VisitCheckin / ServiceRecord / HealthAssessment / MedicationReminder / FamilyVisit / AlertEvent / CareNotice / OperationLog` 等页面字段、文案与动作可见性，清除旧模板残留
  - 生成并纳入前端锁定文件 `148-frontend/package-lock.json`
  - 精确残留扫描未发现 `legal / applicant / approver / research_project / budget_category / budget_allocation / expense_claim / paper_record / patent_record / approval_task / payment_record / performance_statistic / electronic-contract-140 / VehicleServiceOrder` 等旧模板核心关键词；未发现 `System.out.print*`、`printStackTrace` 或通配符 CORS 残留
  - 当前尚未做真实登录验收；默认环境仍依赖本地 MySQL `elder_service_148` 与 Redis
  - 下一项目为 `149`

### 已修复：149 高校实验设备共享预约与违规使用追踪系统
- 本轮新增：
  - 后端 `mvn.cmd test` 通过，日志为 `No tests to run`，仅有不阻塞的 `rdc` profile warning
  - 前端 `npm.cmd install`、`npm.cmd run build` 通过，并生成 `149-frontend/package-lock.json`；构建仍有大包 warning，但不阻塞当前巡检结论
  - 为 `pom.xml` 补齐 Lombok 注解处理配置，并修正 `artifactId`、`JwtUtils` 的 secret、`TokenService` 的 Redis token 前缀，统一回到 `equipment-share-149` / `equipmentshare` 主题
  - 为 `JwtInterceptor` 补齐 `userId/username/role` 请求属性透传，为 `AuthService` 增加统一角色断言，并按 `ADMIN / TEACHER / STUDENT / MANAGER` 口径为 15 个控制器补齐服务端角色权限校验
  - 为 `GlobalExceptionHandler` 拆分业务异常与系统异常返回，并将 `WebMvcConfig` 的通配符跨域收紧为 `http://localhost:3149`、`http://127.0.0.1:3149`、`http://localhost:4173`、`http://127.0.0.1:4173`
  - 为 `SysUser.password` 增加 `@JsonProperty(access = WRITE_ONLY)`，避免登录返回和用户列表链路泄露密码字段；同时保留登录、信息查询和列表链路的密码脱敏一致性
  - 补回缺失的 `149-backend/src/main/java/com/equipmentshare/service/BaseCrudService.java`，并修正错误的 `ServiceCategory.java` 为真实的 `LaboratoryRoom.java`，恢复项目原生编译能力
  - 重写 `149-backend/sql/init.sql`、12 个业务实体字段与对应服务层关键字列，使后端数据模型完整切回“高校实验设备共享预约与违规使用追踪系统”主题
  - 重写前端 `router/index.js`、`Layout.vue`、`Login.vue`、`store/user.js`、`components/DataPage.vue` 与 14 个业务页字段、文案、菜单和按钮权限逻辑，清除会议、论文、征稿、耗材等旧模板残留
  - 精确残留扫描未发现 `learning-path-139`、`labconsumable`、`trainer / employee`、`会议 / 论文 / 征稿 / 耗材 / 供应商 / 投稿 / 会场 / 评审 / 稿件` 等旧模板核心关键词，且未发现 `printStackTrace` 或通配符 CORS 残留
  - 当前尚未做真实登录验收；默认环境仍依赖本地 MySQL `equipment_share_149` 与 Redis
  - 下一项目为 `150`

### 已修复：150 医院门诊检查预约与报告回传管理系统
- 本轮新增：
  - 后端 `mvn.cmd test` 通过，日志为 `No tests to run`，仅有不阻塞的 `rdc` profile warning
  - 前端 `npm.cmd install`、`npm.cmd run build` 通过，并生成 `150-frontend/package-lock.json`；构建仍有大包 warning，但不阻塞当前巡检结论
  - 修复 `150-backend/src/main/java/com/outpatientexam/OutpatientExamApplication.java` 中启动类仍命名为 `VehicleExamAppointment` 的原生编译问题，并为 `pom.xml` 补齐 Lombok 注解处理配置，恢复项目原生编译能力
  - 为 `pom.xml`、`JwtUtils` 与 `TokenService` 修正 `electronic-contract-140` / `researchfund` 模板残留，使 artifactId、JWT secret 和 Redis token 前缀回到 `outpatient-exam-150` / `outpatientexam` 主题
  - 为 `JwtInterceptor` 补充 `userId/username/role` 请求属性透传，为 `AuthService` 增加统一角色断言，并按 `ADMIN / DOCTOR / TECHNICIAN / PATIENT` 口径为 15 个控制器补齐服务端角色权限校验
  - 为 `GlobalExceptionHandler` 拆分业务异常与系统异常返回，并将 `WebMvcConfig` 的通配符 CORS 收紧为 `http://localhost:3150`、`http://127.0.0.1:3150`、`http://localhost:4173`、`http://127.0.0.1:4173`
  - 为 `SysUser.password` 增加 `@JsonProperty(access = WRITE_ONLY)`，同时修复用户新增默认密码与编辑资料时密码留空不被误覆盖的问题
  - 重写 `150-backend/sql/init.sql`、12 个业务实体字段、12 个注解 SQL Mapper 与对应服务层变量命名，彻底清除科研经费与电子合同模板残留，恢复“医院门诊检查预约与报告回传管理系统”主题语义
  - 重写前端 `api/index.js`、`router/index.js`、`Layout.vue`、`Login.vue`、`store/user.js`、`components/DataPage.vue`，补齐角色首页跳转、动态菜单、接口路径和通用按钮权限收口
  - 重写 `SysUser / ExamItem / PatientProfile / DoctorProfile / ExamAppointment / ExamDepartment / CheckinRecord / ExamReport / AbnormalAlert / ReportDelivery / RevisitAdvice / QueueCall / HospitalNotice / OperationLog` 等页面字段、文案与动作可见性，清除旧模板残留
  - 精确残留扫描未发现 `research_project / budget_category / budget_allocation / expense_claim / invoice_record / approval_task / payment_record / research_achievement / paper_record / patent_record / performance_statistic / risk_warning / electronic-contract-140 / LEGAL / APPLICANT / APPROVER / VehicleExamAppointment` 等旧模板核心关键词，且未发现 `printStackTrace` 或通配符 CORS 残留
  - 当前尚未做真实登录验收；默认环境仍依赖本地 MySQL `outpatient_exam_150` 与 Redis
  - 下一项目为 `151`

### 已批量生成：151-200 新增毕设项目
- 本轮新增：
  - 已联网参考 2026 计算机毕业设计热门方向，并结合本地 `001-150` 标题做重复扫描，确认 `151-196` 候选题目未与既有项目 exact duplicate
  - 将原脚本中较易与 `136`、`196` 贴近的 `198/199` 方向替换为“城市共享充电宝投放巡检与收益结算系统”“运动康复训练计划与体测评估管理系统”，并补齐 `197` 家政服务、`200` 非遗工坊方向
  - 修正 `scripts/generate_151_200.py`：兼容当前 Python 写文件接口，前端依赖切到 `@vitejs/plugin-vue 5.0.4` + `vite 5.0.0`，后端补齐 Maven 编译插件和 Lombok 注解处理配置
  - 生成 `151-backend` 到 `200-backend` 与 `151-frontend` 到 `200-frontend` 共 100 个目录，每个项目包含 `PRD.md`、`PLAN.md`、`pom.xml`、`application.yml`、`sql/init.sql`、后端通用鉴权/CRUD/统计模块与 Vue3 前端页面
  - 统一补强模板级安全点：`SysUser.password` 使用 `@JsonProperty(access = WRITE_ONLY)`，`GlobalExceptionHandler` 不再向前端透出系统异常明文，`JwtInterceptor` 透传 `userId/username/role`，CORS 收紧为对应本地前端端口与 `4173`
  - 已为 `151-200` 前端生成 `package-lock.json`，并更新 `readme_simple.md`、`readme.md` 的项目总数与 `151-200` 条目，其中 `200` 标记为最新
  - 已更新 `docs/topic-candidates-147-196.md` 的标题和表格覆盖范围到 `147-200`，并标记 `151-200` 已实现
  - 静态检查确认 `151-200` 核心文件齐备，`readme_simple.md` 覆盖 `151-200`，未发现通配符 CORS、`printStackTrace`、`System.out.print*` 残留
  - 抽样验证通过：`151-backend`、`200-backend` 的 `mvn.cmd -q test` 均通过；`151-frontend`、`200-frontend` 的 `npm.cmd install` 与 `npm.cmd run build` 均通过，仅有 Vite CJS deprecation 与大包 warning，不阻塞当前结论
  - 当前 `151-200` 为批量开发版本，尚未做逐项目真实登录验收；后续正式巡检和主题字段深化仍建议从 `151` 逐项推进

### 已正式开发：151 文旅场馆讲解预约与票务核销管理平台
- 本轮新增：
  - 已按 `$pua` skill 要求执行“先做后问、端到端验证、同类排查”，从 `151` 开始逐项正式开发
  - 将批量版 `com.p151 / BizRecord` 泛化模板完整替换为 `com.culturevenue` 文旅场馆主题项目，后端 artifactId 改为 `culture-venue-ticket-151`，数据库改为 `culture_venue_151`，Redis token 前缀改为 `culturevenue:token:`
  - 后端保留 Spring Boot 2.7.18、MyBatis 注解 SQL、PageHelper、MySQL、Redis、JWT 技术栈，并补齐 Lombok 注解处理、系统异常兜底、密码写入可见脱敏、JWT 请求属性透传和本地端口 CORS 收口
  - 新增五类角色与默认账号：`ADMIN/admin`、`MANAGER/manager`、`GUIDE/guide`、`CHECKER/checker`、`VISITOR/visitor`，统一密码 `123456`
  - 重建 12 个业务模块：场馆档案、票种产品、票务预约、讲解员档案、讲解排期、讲解预约、扫码核销、客流统计、游客评价、文旅活动、场馆公告、操作日志
  - 前端重写路由、角色首页跳转、动态菜单、登录页、通用数据页、统计看板和全部业务页字段，操作按钮按 `ADMIN / MANAGER / GUIDE / CHECKER / VISITOR` 收口
  - `readme.md` 的 `151` 详情已从批量泛化模块改为正式文旅场馆模块说明
  - 残留扫描未发现 `com.p151`、`BizRecord`、`project_151`、`AUDITOR / OPERATOR / MEMBER`、`record01 / record02 / record03`、通配符 CORS、`printStackTrace` 或 `System.out.print*`
  - 后端 `mvn.cmd -q clean test` 通过；前端 `npm.cmd install` 与 `npm.cmd run build` 通过，仅有 Vite CJS deprecation 与大包 warning，不阻塞当前结论
  - 当前尚未做真实数据库登录联调；默认环境仍依赖本地 MySQL `culture_venue_151` 与 Redis
  - 下一项目为 `152`

### 已正式开发：152 工厂危险作业审批与监护巡检管理系统
- 本轮新增：
  - 继续按 `$pua` skill 的端到端闭环推进，未停在批量生成层面
  - 检查发现 `152` 批量版仍为 `com.p152 / BizRecord / project_152` 泛化模板，并带有通用角色和通用业务字段
  - 通过 `scripts/make_152_from_151.py` 与 `scripts/develop_152.py` 复用 `151` 已验证的正式化流水线，并在落盘前主动检查脚本内容，修正一次角色/模块替换未生效的问题
  - 将后端改为 `com.hazardwork` 包，启动类为 `HazardWorkApplication`，artifactId 改为 `hazard-work-permit-152`，数据库改为 `hazard_work_152`，Redis token 前缀改为 `hazardwork:token:`
  - 新增五类角色与默认账号：`ADMIN/admin`、`SAFETY/safety`、`APPROVER/approver`、`GUARDIAN/guardian`、`WORKER/worker`，统一密码 `123456`
  - 重建 12 个业务模块：作业区域、风险源台账、作业人员档案、作业票申请、审批链路、安全交底、监护安排、监护记录、隐患闭环、气体检测、应急预案、操作日志
  - 前端重写路由、角色首页跳转、动态菜单、登录页、通用数据页、统计看板和全部业务页字段，操作按钮按 `ADMIN / SAFETY / APPROVER / GUARDIAN / WORKER` 收口
  - `readme.md` 的 `152` 详情已从批量泛化模块改为正式危险作业模块说明
  - 残留扫描未发现 `com.p152`、`BizRecord`、`project_152`、旧角色名、旧文旅模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace` 或 `System.out.print*`
  - 后端 `mvn.cmd -q clean test` 通过；前端 `npm.cmd install` 与 `npm.cmd run build` 通过，仅有 Vite CJS deprecation 与大包 warning，不阻塞当前结论
  - 当前尚未做真实数据库登录联调；默认环境仍依赖本地 MySQL `hazard_work_152` 与 Redis
  - 下一项目为 `153`

### 已正式开发：153 校园二手物品寄卖与信用评价系统
- 本轮新增：
  - 继续按 `$pua` skill 的“先做后问、端到端验证、同类排查”方式推进，并联网参考校园二手交易、信用评价、担保交易类题目方向后，确定 153 不做普通商品发布平台，而突出寄卖、担保、信用和仲裁闭环
  - 检查发现 `153` 批量版仍为 `com.p153 / BizRecord / project_153` 泛化模板，前端仍使用 `record01-12` 路由和通用业务页
  - 基于 `151` 已验证的正式化流水线生成 `scripts/develop_153.py`；执行前主动检查并修复统计饼图、角色首页跳转、账号文案中的旧文旅残留
  - 将后端改为 `com.campusresale` 包，启动类为 `CampusResaleApplication`，artifactId 改为 `campus-resale-credit-153`，数据库改为 `campus_resale_153`，Redis token 前缀改为 `campusresale:token:`
  - 新增五类角色与默认账号：`ADMIN/admin`、`OPERATOR/operator`、`SELLER/seller`、`BUYER/buyer`、`ARBITER/arbiter`，统一密码 `123456`
  - 重建 12 个业务模块：物品分类、学生档案、寄卖物品、上架审核、担保订单、担保支付、交接确认、信用评价、违约记录、纠纷申诉、平台公告、操作日志
  - 前端重写路由、角色首页跳转、动态菜单、登录页、通用数据页、统计看板和全部业务页字段，操作按钮按 `ADMIN / OPERATOR / SELLER / BUYER / ARBITER` 收口
  - `readme.md` 的 `153` 详情已从批量泛化模块改为正式校园寄卖模块说明
  - 残留扫描未发现 `com.p153`、`BizRecord`、`project_153`、旧角色名、旧文旅模块名、旧工业安全模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace` 或 `System.out.print*`
  - 后端 `mvn.cmd -q clean test` 通过；前端 `npm.cmd install` 与 `npm.cmd run build` 通过，仅有 Vite CJS deprecation 与大包 warning，不阻塞当前结论
  - 当前尚未做真实数据库登录联调；默认环境仍依赖本地 MySQL `campus_resale_153` 与 Redis
  - 下一项目为 `154`

### 已正式开发：154 宠物医院接诊排班与疫苗随访管理系统
- 本轮新增：
  - 继续从 `154` 批量模板接力开发，结合宠物医院常见门诊运营能力，将正式化重点收敛到宠主建档、宠物档案、医生排班、接诊预约、诊疗记录、疫苗计划、疫苗接种、随访回访、药品库存和费用结算
  - 检查发现 `154` 批量版仍为 `com.p154 / BizRecord / project_154` 泛化模板，前端仍使用 `record01-12` 路由和通用业务页
  - 通过 `scripts/make_154_from_151.py` 与 `scripts/develop_154.py` 复用已验证的正式化流水线，并额外修正菜单/路由角色数组，使侧边栏可见性按模块实际角色收口
  - 将后端改为 `com.pethospital` 包，启动类为 `PetHospitalApplication`，artifactId 改为 `pet-hospital-vaccine-154`，数据库改为 `pet_hospital_154`，Redis token 前缀改为 `pethospital:token:`
  - 新增五类角色与默认账号：`ADMIN/admin`、`RECEPTION/reception`、`DOCTOR/doctor`、`NURSE/nurse`、`OWNER/owner`，统一密码 `123456`
  - 重建 12 个业务模块：宠主档案、宠物档案、医生档案、接诊排班、接诊预约、接诊记录、疫苗计划、疫苗接种、随访记录、药品库存、费用结算、操作日志
  - 前端重写路由、角色首页跳转、动态菜单、登录页、通用数据页、统计看板和全部业务页字段，操作按钮与侧边栏菜单按 `ADMIN / RECEPTION / DOCTOR / NURSE / OWNER` 收口
  - `readme.md` 与 `readme_simple.md` 的 `154` 详情已从批量泛化模块改为正式宠物医院模块说明
  - 残留扫描未发现 `com.p154`、`BizRecord`、`project_154`、旧角色名、旧文旅/校园寄卖/工业安全模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace` 或 `System.out.print*`
  - 后端 `mvn.cmd -q clean test` 通过；前端 `npm.cmd install` 与 `npm.cmd run build` 通过，仅有 Vite CJS deprecation 与大包 warning，不阻塞当前结论
  - 当前尚未做真实数据库登录联调；默认环境仍依赖本地 MySQL `pet_hospital_154` 与 Redis
  - 下一项目为 `155`

### 已正式开发：155 社区党建活动报名与积分激励平台
- 本轮新增：
  - 继续按 `$pua` skill 的“先做后问、端到端验证、同类排查”方式推进，先补跑 `154` 前端构建并修复 Vite 在沙箱路径下的 root 解析问题，再进入 `155`
  - 检查发现 `155` 批量版仍为 `com.p155 / BizRecord / project_155` 泛化模板，前端仍使用 `record01-12` 路由和通用业务页
  - 基于 `151` 已验证的正式化流水线生成 `scripts/develop_155.py`，并在落盘前清理统计饼图、默认账号、状态文案中的旧文旅/宠物医院/校园寄卖残留
  - 将后端切换为 `com.communityparty` 包，启动类为 `CommunityPartyApplication`，artifactId 为 `community-party-points-155`，数据库为 `community_party_155`，Redis token 前缀为 `communityparty:token:`
  - 新增五类角色与默认账号：`ADMIN/admin`、`SECRETARY/secretary`、`ORGANIZER/organizer`、`VOLUNTEER/volunteer`、`RESIDENT/resident`，统一密码 `123456`
  - 重建 12 个业务模块：党组织维护、党员档案、党建活动、活动报名、签到记录、志愿任务、积分记录、积分兑换、组织关系、榜单统计、通知公告、操作日志
  - 前端重写路由、角色首页跳转、动态菜单、登录页、通用数据页、统计看板和全部业务页字段，操作按钮与侧边栏菜单按 `ADMIN / SECRETARY / ORGANIZER / VOLUNTEER / RESIDENT` 收口
  - `readme.md` 与 `readme_simple.md` 的 `155` 详情已从批量泛化模块改为正式社区党建模块说明
  - 残留扫描未发现 `com.p155`、`BizRecord`、`project_155`、旧角色名、旧文旅/宠物医院/校园寄卖/工业安全模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace` 或 `System.out.print*`
  - 后端 `mvn.cmd -q clean test` 通过；前端 `npm.cmd install` 与 `npm.cmd run build` 通过，仅有 Vite CJS deprecation 与大包 warning，不阻塞当前结论
  - 当前尚未做真实数据库登录联调；默认环境仍依赖本地 MySQL `community_party_155` 与 Redis
  - 下一项目为 `156`

### 已正式开发：156 校园宿舍能耗监测与节能排名系统
- 本轮新增：
  - 按用户“尽量每个都多元化一点”的要求，将 `156` 从批量通用的宿舍能耗方向深化为“楼栋房间 + 表计读数 + 账单核算 + 预警策略 + 异常预警 + 节能任务 + 节能排行 + 后勤巡查”的能源治理闭环
  - 检查发现 `156` 批量版仍为 `com.p156 / BizRecord / project_156` 泛化模板，前端仍使用 `record01-12` 路由和通用业务页
  - 基于 `155` 已验证并带有 Vite `root: process.cwd()` 修复的流水线生成 `scripts/develop_156.py`，执行前扫描旧党建、宠物医院、校园寄卖、工业安全与文旅关键词均未命中
  - 将后端切换为 `com.dormenergy` 包，启动类为 `DormEnergyApplication`，artifactId 为 `dorm-energy-ranking-156`，数据库为 `dorm_energy_156`，Redis token 前缀为 `dormenergy:token:`
  - 新增五类角色与默认账号：`ADMIN/admin`、`LOGISTICS/logistics`、`COUNSELOR/counselor`、`ENERGY/energy`、`STUDENT/student`，统一密码 `123456`
  - 重建 12 个业务模块：宿舍楼栋、宿舍房间、能耗表计、用电读数、能耗账单、预警策略、异常预警、节能任务、节能排行、巡查记录、通知公告、操作日志
  - 前端重写路由、角色首页跳转、动态菜单、登录页、通用数据页、统计看板和全部业务页字段，操作按钮与侧边栏菜单按 `ADMIN / LOGISTICS / COUNSELOR / ENERGY / STUDENT` 收口
  - `readme.md` 与 `readme_simple.md` 的 `156` 详情已从批量泛化模块改为正式宿舍能耗模块说明
  - 残留扫描未发现 `com.p156`、`BizRecord`、`project_156`、旧角色名、旧党建/宠物医院/校园寄卖/工业安全/文旅模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace` 或 `System.out.print*`
  - 后端 `mvn.cmd -q clean test` 通过；前端 `npm.cmd install` 与 `npm.cmd run build` 通过，仅有 Vite CJS deprecation 与大包 warning，不阻塞当前结论
  - 当前尚未做真实数据库登录联调；默认环境仍依赖本地 MySQL `dorm_energy_156` 与 Redis
  - 下一项目为 `157`

### 已正式开发：157 物流园区车辆入场预约与道口调度平台
- 本轮新增：
  - 按多元化要求将 `157` 聚焦智慧物流园区调度，参考 YMS/车辆预约/门岗核验/道口月台分配等常见能力，形成“预约入场 + 门岗核验 + 排队叫号 + 道口分配 + 装卸任务 + 周转统计”闭环
  - 检查发现 `157` 批量版仍为 `com.p157 / BizRecord / project_157` 泛化模板，前端仍使用 `record01-12` 路由和通用业务页
  - 基于 `156` 已验证的正式化流水线生成 `scripts/develop_157.py`，执行前扫描旧宿舍能耗、党建、宠物医院、校园寄卖、工业安全与文旅关键词均未命中
  - 将后端切换为 `com.logisticspark` 包，启动类为 `LogisticsParkApplication`，artifactId 为 `logistics-yard-gate-157`，数据库为 `logistics_park_157`，Redis token 前缀为 `logisticspark:token:`
  - 新增五类角色与默认账号：`ADMIN/admin`、`DISPATCHER/dispatcher`、`GATEKEEPER/gatekeeper`、`YARDMASTER/yardmaster`、`CARRIER/carrier`，统一密码 `123456`
  - 重建 12 个业务模块：承运商档案、车辆档案、司机档案、入场预约、时段计划、门岗核验、排队叫号、道口资源、道口分配、装卸任务、周转统计、操作日志
  - 前端重写路由、角色首页跳转、动态菜单、登录页、通用数据页、统计看板和全部业务页字段，操作按钮与侧边栏菜单按 `ADMIN / DISPATCHER / GATEKEEPER / YARDMASTER / CARRIER` 收口
  - `readme.md` 与 `readme_simple.md` 的 `157` 详情已从批量泛化模块改为正式物流园区模块说明
  - 残留扫描未发现 `com.p157`、`BizRecord`、`project_157`、旧角色名、旧宿舍能耗/党建/宠物医院/校园寄卖/工业安全/文旅模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace` 或 `System.out.print*`
  - 后端 `mvn.cmd -q clean test` 通过；前端 `npm.cmd install` 与 `npm.cmd run build` 通过，仅有 Vite CJS deprecation 与大包 warning，不阻塞当前结论
  - 当前尚未做真实数据库登录联调；默认环境仍依赖本地 MySQL `logistics_park_157` 与 Redis
  - 下一项目为 `158`

### 已正式开发：158 校外培训机构课消统计与退费审批系统
- 本轮新增：
  - 按多元化要求将 `158` 聚焦校外培训机构经营合规场景，形成“课程产品 + 排课签到 + 课消确认 + 家长退费 + 财务流水”的教培闭环
  - 检查发现 `158` 批量版仍为 `com.p158 / BizRecord / project_158` 泛化模板，前端仍使用 `record01-12` 路由和通用业务页
  - 基于 `157` 已验证的正式化流水线生成 `scripts/develop_158.py`，修正统计饼图状态文案，避免物流园区“门岗/道口/装卸”等旧语义残留
  - 将后端切换为 `com.trainingrefund` 包，启动类为 `TrainingRefundApplication`，artifactId 为 `training-consumption-refund-158`，数据库为 `training_refund_158`，Redis token 前缀为 `trainingrefund:token:`
  - 新增六类角色与默认账号：`ADMIN/admin`、`PRINCIPAL/principal`、`ACADEMIC/academic`、`TEACHER/teacher`、`FINANCE/finance`、`PARENT/parent`，统一密码 `123456`
  - 重建 12 个业务模块：校区档案、课程产品、学员档案、教师档案、班级台账、排课计划、上课考勤、课消记录、退费申请、退费审批、财务流水、操作日志
  - 前端重写路由、角色首页跳转、动态菜单、登录页、通用数据页、统计看板和全部业务页字段，操作按钮与侧边栏菜单按 `ADMIN / PRINCIPAL / ACADEMIC / TEACHER / FINANCE / PARENT` 收口
  - `readme.md` 与 `readme_simple.md` 的 `158` 详情已从批量泛化模块改为正式教培课消退费模块说明
  - 残留扫描未发现 `com.p158`、`BizRecord`、`project_158`、旧角色名、旧物流园区/宿舍能耗/党建/宠物医院/校园寄卖/工业安全/文旅模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace` 或 `System.out.print*`
  - 后端 `mvn.cmd -q clean test` 通过；前端 `npm.cmd install` 与 `npm.cmd run build` 通过，仅有 Vite CJS deprecation 与大包 warning，不阻塞当前结论
  - 当前尚未做真实数据库登录联调；默认环境仍依赖本地 MySQL `training_refund_158` 与 Redis
  - 下一项目为 `159`

### 已正式开发：159 医疗废弃物收运联单与闭环监管系统
- 本轮新增：
  - 按多元化要求将 `159` 聚焦医疗废弃物闭环监管，结合公开资料中的分类收集、暂存交接、转运联单、处置确认和监管追溯要求，形成“包装赋码 + 称重交接 + 转运联单 + 处置确认 + 异常追溯 + 监管抽查”闭环
  - 检查发现 `159` 批量版仍为 `com.p159 / BizRecord / project_159` 泛化模板，前端仍使用 `record01-12` 路由和通用业务页
  - 基于 `158` 已验证的正式化流水线生成 `scripts/develop_159.py`，执行前扫描并修正旧教培统计饼图文案，避免“待上课/退费审批/财务结清”等残留
  - 将后端切换为 `com.medicalwaste` 包，启动类为 `MedicalWasteApplication`，artifactId 为 `medical-waste-manifest-159`，数据库为 `medical_waste_159`，Redis token 前缀为 `medicalwaste:token:`
  - 新增六类角色与默认账号：`ADMIN/admin`、`HOSPITAL/hospital`、`COLLECTOR/collector`、`TRANSPORTER/transporter`、`DISPOSAL/disposal`、`REGULATOR/regulator`，统一密码 `123456`
  - 重建 12 个业务模块：医废来源、废物类别、包装赋码、收集预约、称重记录、暂存交接、转运联单、运输轨迹、处置确认、异常追溯、监管抽查、操作日志
  - 前端重写路由、角色首页跳转、动态菜单、登录页、通用数据页、统计看板和全部业务页字段，操作按钮与侧边栏菜单按 `ADMIN / HOSPITAL / COLLECTOR / TRANSPORTER / DISPOSAL / REGULATOR` 收口
  - `readme.md` 与 `readme_simple.md` 的 `159` 详情已从批量泛化模块改为正式医疗废弃物联单监管模块说明
  - 残留扫描未发现 `com.p159`、`BizRecord`、`project_159`、旧角色名、旧教培/物流园区/宿舍能耗/党建/宠物医院/校园寄卖/工业安全/文旅模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace` 或 `System.out.print*`
  - 后端 `mvn.cmd -q clean test` 通过；前端 `npm.cmd install` 与 `npm.cmd run build` 通过，仅有 Vite CJS deprecation 与大包 warning，不阻塞当前结论
  - 当前尚未做真实数据库登录联调；默认环境仍依赖本地 MySQL `medical_waste_159` 与 Redis
  - 下一项目为 `160`

### 已正式开发：160 校园社团活动预算报销与物资借用系统
- 本轮新增：
  - 按多元化要求将 `160` 聚焦高校社团财务与物资协同场景，形成“活动立项 + 预算申请 + 预算明细 + 报销申请 + 票据归档 + 物资借用 + 归还验收”的闭环
  - 检查发现 `160` 批量版仍为 `com.p160 / BizRecord / project_160` 泛化模板，前端仍使用 `record01-12` 路由和通用业务页
  - 基于 `159` 已验证的正式化流水线生成 `scripts/develop_160.py`，执行前扫描旧医疗废弃物联单、教培课消、物流园区等关键词均未命中
  - 将后端切换为 `com.clubfinance` 包，启动类为 `ClubFinanceApplication`，artifactId 为 `campus-club-budget-160`，数据库为 `club_finance_160`，Redis token 前缀为 `clubfinance:token:`
  - 新增六类角色与默认账号：`ADMIN/admin`、`UNION/union`、`CLUB/club`、`TREASURER/treasurer`、`WAREHOUSE/warehouse`、`MEMBER/member`，统一密码 `123456`
  - 重建 12 个业务模块：社团档案、成员档案、活动立项、预算申请、预算明细、审批记录、报销申请、票据归档、物资台账、物资借用、归还验收、操作日志
  - 前端重写路由、角色首页跳转、动态菜单、登录页、通用数据页、统计看板和全部业务页字段，操作按钮与侧边栏菜单按 `ADMIN / UNION / CLUB / TREASURER / WAREHOUSE / MEMBER` 收口
  - `readme.md` 与 `readme_simple.md` 的 `160` 详情已从批量泛化模块改为正式校园社团财资模块说明
  - 残留扫描未发现 `com.p160`、`BizRecord`、`project_160`、旧角色名、旧医废/教培/物流园区/宿舍能耗/党建/宠物医院/工业安全/文旅模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace` 或 `System.out.print*`
  - 后端 `mvn.cmd -q clean test` 通过；前端 `npm.cmd install` 与 `npm.cmd run build` 通过，仅有 Vite CJS deprecation 与大包 warning，不阻塞当前结论
  - 当前尚未做真实数据库登录联调；默认环境仍依赖本地 MySQL `club_finance_160` 与 Redis
  - 下一项目为 `161`

### 已正式开发：161 景区失物招领与游客寻回协同平台
- 本轮新增：
  - 按多元化要求将 `161` 聚焦景区游客服务，形成“失物登记 + 拾物上报 + 身份核验 + 暂存保管 + 通知协同 + 归还交接 + 回访评价”的寻回闭环
  - 检查发现 `161` 批量版仍为 `com.p161 / BizRecord / project_161` 泛化模板，前端仍使用 `record01-12` 路由和通用业务页
  - 基于 `160` 已验证的正式化流水线生成 `scripts/develop_161.py`，执行前扫描并修正旧社团财资统计饼图文案，避免“预算通过/报销处理中/物资归还”等残留
  - 将后端切换为 `com.lostfound` 包，启动类为 `ScenicLostFoundApplication`，artifactId 为 `scenic-lost-found-161`，数据库为 `scenic_lost_found_161`，Redis token 前缀为 `lostfound:token:`
  - 新增六类角色与默认账号：`ADMIN/admin`、`SERVICE/service`、`SECURITY/security`、`SCENIC/scenic`、`BROADCAST/broadcast`、`VISITOR/visitor`，统一密码 `123456`
  - 重建 12 个业务模块：景区区域、失物登记、拾物上报、游客认领、身份核验、位置追踪、暂存保管、通知协同、归还交接、寻回任务、回访评价、操作日志
  - 前端重写路由、角色首页跳转、动态菜单、登录页、通用数据页、统计看板和全部业务页字段，操作按钮与侧边栏菜单按 `ADMIN / SERVICE / SECURITY / SCENIC / BROADCAST / VISITOR` 收口
  - `readme.md` 与 `readme_simple.md` 的 `161` 详情已从批量泛化模块改为正式景区失物寻回模块说明
  - 残留扫描未发现 `com.p161`、`BizRecord`、`project_161`、旧角色名、旧社团财资/医废/教培/物流园区模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace` 或 `System.out.print*`
  - 后端 `mvn.cmd -q clean test` 通过；前端 `npm.cmd install` 与 `npm.cmd run build` 通过，仅有 Vite CJS deprecation 与大包 warning，不阻塞当前结论
  - 当前尚未做真实数据库登录联调；默认环境仍依赖本地 MySQL `scenic_lost_found_161` 与 Redis
  - 下一项目为 `162`

### 已正式开发：162 生鲜门店临期商品预警与促销处置系统
- 本轮新增：
  - 检查发现 `162` 批量版仍为 `com.p162 / BizRecord / project_162` 泛化模板，前端仍使用 `record01-12` 路由和通用业务页
  - 修正 `scripts/develop_162.py` 中从 `161` 继承来的景区失物 PRD/PLAN、端口、初始化编号与账号文案残留，并将本项目端口切换为后端 `8162`、前端 `3162`
  - 将后端切换为 `com.freshretail` 包，启动类为 `FreshRetailApplication`，artifactId 为 `fresh-expiry-promotion-162`，数据库为 `fresh_expiry_162`，Redis token 前缀为 `freshretail:token:`
  - 新增六类角色与默认账号：`ADMIN/admin`、`MANAGER/manager`、`CLERK/clerk`、`STOCK/stock`、`MARKETING/marketing`、`SUPPLIER/supplier`，统一密码 `123456`
  - 重建 12 个业务模块：门店档案、供应商档案、生鲜品类、商品批次、保质期规则、临期预警、促销策略、折扣执行、报损记录、库存周转、门店分析、操作日志
  - 前端重写路由、角色首页跳转、动态菜单、登录页、通用数据页、统计看板和全部业务页字段，操作按钮与侧边栏菜单按 `ADMIN / MANAGER / CLERK / STOCK / MARKETING / SUPPLIER` 收口
  - `readme.md` 与 `readme_simple.md` 的 `162` 详情已从批量泛化模块改为正式生鲜临期商品处置模块说明
  - 残留扫描未发现 `com.p162`、`BizRecord`、`project_162`、旧景区失物角色/模块、旧 `record01` 路径、通配符 CORS、`printStackTrace` 或 `System.out.print*`
  - 后端 `mvn.cmd -q clean test` 通过；前端 `npm.cmd install` 与 `npm.cmd run build` 通过，仅有 Vite CJS deprecation 与大包 warning，不阻塞当前结论
  - 当前尚未做真实数据库登录联调；默认环境仍依赖本地 MySQL `fresh_expiry_162` 与 Redis
  - 下一项目为 `163`

### 已正式开发：163 医学实习轮转考核与病例学习管理系统
- 本轮新增：
  - 检查发现 `163` 批量版仍为 `com.p163 / BizRecord / project_163` 泛化模板，前端仍使用 `record01-12` 路由和通用业务页
  - 基于 `162` 已验证的正式化流水线生成 `scripts/develop_163.py`，将题目深化为医学实习轮转、病例学习、教学查房、技能训练、带教评分、出科考核和反馈整改闭环
  - 将后端切换为 `com.clinicalrotation` 包，启动类为 `ClinicalRotationApplication`，artifactId 为 `clinical-rotation-case-163`，数据库为 `clinical_rotation_163`，Redis token 前缀为 `clinicalrotation:token:`
  - 新增六类角色与默认账号：`ADMIN/admin`、`SECRETARY/secretary`、`TEACHER/teacher`、`STUDENT/student`、`EXAMINER/examiner`、`DIRECTOR/director`，统一密码 `123456`
  - 重建 12 个业务模块：轮转科室、实习学生、带教老师、轮转安排、病例学习、学习记录、教学查房、技能训练、带教评分、出科考核、反馈整改、操作日志
  - 前端重写路由、角色首页跳转、动态菜单、登录页、通用数据页、统计看板和全部业务页字段，操作按钮与侧边栏菜单按 `ADMIN / SECRETARY / TEACHER / STUDENT / EXAMINER / DIRECTOR` 收口
  - `readme.md` 与 `readme_simple.md` 的 `163` 详情已从批量泛化模块改为正式医学实习轮转考核模块说明
  - 残留扫描未发现 `com.p163`、`BizRecord`、`project_163`、旧生鲜临期角色/模块、旧 `record01` 路径、通配符 CORS、`printStackTrace` 或 `System.out.print*`
  - 后端 `mvn.cmd -q clean test` 通过；前端 `npm.cmd install` 与 `npm.cmd run build` 通过，仅有 Vite CJS deprecation 与大包 warning，不阻塞当前结论
  - 当前尚未做真实数据库登录联调；默认环境仍依赖本地 MySQL `clinical_rotation_163` 与 Redis
  - 下一项目为 `164`

### 已正式开发：164 校园体育赛事报名编排与裁判评分系统
- 本轮新增：
  - 检查发现 `164` 批量版仍为 `com.p164 / BizRecord / project_164` 泛化模板，前端仍使用 `record01-12` 路由和通用业务页
  - 基于 `163` 已验证的正式化流水线生成并执行 `scripts/develop_164.py`；执行前清理医学轮转、病例、带教、出科等旧主题词，端口切换为后端 `8164`、前端 `3164`
  - 将后端切换为 `com.sportevent` 包，启动类为 `SportEventApplication`，artifactId 为 `campus-sport-event-164`，数据库为 `campus_sport_event_164`，Redis token 前缀为 `sportevent:token:`
  - 新增六类角色与默认账号：`ADMIN/admin`、`ORGANIZER/organizer`、`COACH/coach`、`ATHLETE/athlete`、`REFEREE/referee`、`COMMITTEE/committee`，统一密码 `123456`
  - 重建 12 个业务模块：体育赛事、参赛队伍、运动员档案、赛事报名、报名分组、赛程编排、场地资源、裁判指派、裁判评分、成绩公示、申诉复核、操作日志
  - 前端重写路由、角色首页跳转、动态菜单、登录页、通用数据页、统计看板和全部业务页字段，操作按钮与侧边栏菜单按 `ADMIN / ORGANIZER / COACH / ATHLETE / REFEREE / COMMITTEE` 收口
  - `readme.md` 与 `readme_simple.md` 的 `164` 详情已从批量泛化模块改为正式校园体育赛事模块说明
  - 残留扫描未发现 `com.p164`、`BizRecord`、`project_164`、旧医学轮转角色/模块、旧 `record01` 路径、通配符 CORS、`printStackTrace` 或 `System.out.print*`
  - 后端 `mvn.cmd -q clean test` 通过；前端 `npm.cmd install` 与 `npm.cmd run build` 通过，仅有 Vite CJS deprecation 与大包 warning，不阻塞当前结论
  - 当前尚未做真实数据库登录联调；默认环境仍依赖本地 MySQL `campus_sport_event_164` 与 Redis
  - 下一项目为 `165`

### 已正式开发：165 企业访客预约登记与门禁联动管理系统
- 本轮新增：
  - 检查发现 `165` 批量版仍为 `com.p165 / BizRecord / project_165` 泛化模板，前端仍使用 `record01-12` 路由和通用业务页
  - 基于 `164` 已验证的正式化流水线生成并执行 `scripts/develop_165.py`；执行前清理体育赛事、参赛、裁判、成绩、申诉等旧主题词，端口切换为后端 `8165`、前端 `3165`
  - 将后端切换为 `com.visitoraccess` 包，启动类为 `VisitorAccessApplication`，artifactId 为 `enterprise-visitor-access-165`，数据库为 `visitor_access_165`，Redis token 前缀为 `visitoraccess:token:`
  - 新增六类角色与默认账号：`ADMIN/admin`、`RECEPTION/reception`、`EMPLOYEE/employee`、`SECURITY/security`、`VISITOR/visitor`、`MANAGER/manager`，统一密码 `123456`
  - 重建 12 个业务模块：楼宇区域、被访员工、访客档案、访客预约、访问审批、二维码通行、门禁设备、门禁联动、入园登记、轨迹留痕、异常告警、操作日志
  - 前端重写路由、角色首页跳转、动态菜单、登录页、通用数据页、统计看板和全部业务页字段，操作按钮与侧边栏菜单按 `ADMIN / RECEPTION / EMPLOYEE / SECURITY / VISITOR / MANAGER` 收口
  - `readme.md` 与 `readme_simple.md` 的 `165` 详情已从批量泛化模块改为正式企业访客门禁模块说明
  - 残留扫描未发现 `com.p165`、`BizRecord`、`project_165`、旧体育赛事角色/模块、旧 `record01` 路径、通配符 CORS、`printStackTrace` 或 `System.out.print*`
  - 后端 `mvn.cmd -q clean test` 通过；前端 `npm.cmd install` 与 `npm.cmd run build` 通过，仅有 Vite CJS deprecation 与大包 warning，不阻塞当前结论
  - 当前尚未做真实数据库登录联调；默认环境仍依赖本地 MySQL `visitor_access_165` 与 Redis
  - 下一项目为 `166`

### 已正式开发：166 农贸市场摊位巡检与食品追溯台账系统
- 本轮新增：
  - 检查发现 `166` 批量版仍为 `com.p166 / BizRecord / project_166` 泛化模板，前端仍使用 `record01-12` 路由和通用业务页
  - 基于 `165` 已验证的正式化流水线生成并执行 `scripts/develop_166.py`；执行前清理访客、门禁、二维码、入园、轨迹留痕等旧主题词，端口切换为后端 `8166`、前端 `3166`
  - 将后端切换为 `com.markettrace` 包，启动类为 `MarketTraceApplication`，artifactId 为 `farm-market-trace-166`，数据库为 `market_trace_166`，Redis token 前缀为 `markettrace:token:`
  - 新增六类角色与默认账号：`ADMIN/admin`、`MARKET/market`、`INSPECTOR/inspector`、`VENDOR/vendor`、`SAMPLER/sampler`、`REGULATOR/regulator`，统一密码 `123456`
  - 重建 12 个业务模块：市场区域、摊位档案、商户档案、商品溯源、摊位巡检、问题整改、抽检记录、检测结果、进货台账、处置记录、风险预警、操作日志
  - 前端重写路由、角色首页跳转、动态菜单、登录页、通用数据页、统计看板和全部业务页字段，操作按钮与侧边栏菜单按 `ADMIN / MARKET / INSPECTOR / VENDOR / SAMPLER / REGULATOR` 收口
  - `readme.md` 与 `readme_simple.md` 的 `166` 详情已从批量泛化模块改为正式农贸市场巡检追溯模块说明
  - 残留扫描未发现 `com.p166`、`BizRecord`、`project_166`、旧访客门禁角色/模块、旧 `record01` 路径、通配符 CORS、`printStackTrace` 或 `System.out.print*`
  - 后端 `mvn.cmd -q clean test` 通过；前端 `npm.cmd install` 与 `npm.cmd run build` 通过，仅有 Vite CJS deprecation 与大包 warning，不阻塞当前结论
  - 当前尚未做真实数据库登录联调；默认环境仍依赖本地 MySQL `market_trace_166` 与 Redis
  - 下一项目为 `167`

### 已正式开发：167 社区垃圾分类督导与积分兑换平台
- 本轮新增：
  - 检查发现 `167` 批量版仍为 `com.p167 / BizRecord / project_167` 泛化模板，前端仍使用 `record01-12` 路由和通用业务页
  - 基于 `166` 已验证的正式化流水线生成并执行 `scripts/develop_167.py`；执行前清理农贸、摊位、商户、抽检、检测、风险预警等旧主题词，端口切换为后端 `8167`、前端 `3167`
  - 将后端切换为 `com.wastesorting` 包，启动类为 `WasteSortingApplication`，artifactId 为 `community-waste-points-167`，数据库为 `waste_sorting_167`，Redis token 前缀为 `wastesorting:token:`
  - 新增六类角色与默认账号：`ADMIN/admin`、`COMMUNITY/community`、`SUPERVISOR/supervisor`、`RESIDENT/resident`、`VOLUNTEER/volunteer`、`EXCHANGE/exchange`，统一密码 `123456`
  - 重建 12 个业务模块：社区区域、楼栋档案、居民档案、分类规则、分类打卡、督导记录、整改任务、积分记录、兑换商品、积分兑换、楼栋排名、操作日志
  - 前端重写路由、角色首页跳转、动态菜单、登录页、通用数据页、统计看板和全部业务页字段，操作按钮与侧边栏菜单按 `ADMIN / COMMUNITY / SUPERVISOR / RESIDENT / VOLUNTEER / EXCHANGE` 收口
  - `readme.md` 与 `readme_simple.md` 的 `167` 详情已从批量泛化模块改为正式社区垃圾分类积分模块说明
  - 残留扫描未发现 `com.p167`、`BizRecord`、`project_167`、旧农贸市场角色/模块、旧 `record01` 路径、通配符 CORS、`printStackTrace` 或 `System.out.print*`
  - 后端 `mvn.cmd -q clean test` 通过；前端 `npm.cmd install` 与 `npm.cmd run build` 通过，仅有 Vite CJS deprecation 与大包 warning，不阻塞当前结论
  - 当前尚未做真实数据库登录联调；默认环境仍依赖本地 MySQL `waste_sorting_167` 与 Redis
  - 下一项目为 `168`

### 已正式开发：168 在线职业培训证书考试与续证提醒系统
- 本轮新增：
  - 检查发现 `168` 批量版仍为 `com.p168 / BizRecord / project_168` 泛化模板，前端仍使用 `record01-12` 路由和通用业务页
  - 基于 `167` 已验证的正式化流水线生成并执行 `scripts/develop_168.py`；执行前清理社区垃圾分类、楼栋、居民、积分兑换、督导整改等旧主题词，端口切换为后端 `8168`、前端 `3168`
  - 将后端切换为 `com.certtraining` 包，启动类为 `CertTrainingApplication`，artifactId 为 `online-cert-training-168`，数据库为 `cert_training_168`，Redis token 前缀为 `certtraining:token:`
  - 新增六类角色与默认账号：`ADMIN/admin`、`TRAINING/training`、`INSTRUCTOR/instructor`、`TRAINEE/trainee`、`EXAMINER/examiner`、`CERTIFIER/certifier`，统一密码 `123456`
  - 重建 12 个业务模块：培训课程、学员档案、讲师档案、课程报名、学习进度、考试安排、考试成绩、证书发放、证书台账、续证申请、提醒通知、操作日志
  - 前端重写路由、角色首页跳转、动态菜单、登录页、通用数据页、统计看板和全部业务页字段，操作按钮与侧边栏菜单按 `ADMIN / TRAINING / INSTRUCTOR / TRAINEE / EXAMINER / CERTIFIER` 收口
  - `readme.md` 与 `readme_simple.md` 的 `168` 详情已从批量泛化模块改为正式在线职业培训证书考试模块说明
  - 残留扫描未发现 `com.p168`、`BizRecord`、`project_168`、旧社区垃圾分类角色/模块、旧 `record01` 路径、通配符 CORS、`printStackTrace` 或 `System.out.print*`
  - 后端 `mvn.cmd -q clean test` 通过；前端 `npm.cmd install` 与 `npm.cmd run build` 通过，仅有 Vite CJS deprecation 与大包 warning，不阻塞当前结论
  - 当前尚未做真实数据库登录联调；默认环境仍依赖本地 MySQL `cert_training_168` 与 Redis
  - 下一项目为 `169`

### 已正式开发：169 校车乘车预约与学生上下车核验系统
- 本轮新增：
  - 检查发现 `169` 批量版仍为 `com.p169 / BizRecord / project_169` 泛化模板，前端仍使用 `record01-12` 路由和通用业务页
  - 基于 `168` 已验证的正式化流水线生成并执行 `scripts/develop_169.py`；执行前清理在线职业培训、证书发放、考试成绩、续证申请、提醒通知等旧主题词，端口切换为后端 `8169`、前端 `3169`
  - 将后端切换为 `com.schoolbus` 包，启动类为 `SchoolBusApplication`，artifactId 为 `school-bus-checkin-169`，数据库为 `school_bus_169`，Redis token 前缀为 `schoolbus:token:`
  - 新增六类角色与默认账号：`ADMIN/admin`、`SCHOOL/school`、`DISPATCH/dispatch`、`DRIVER/driver`、`TEACHER/teacher`、`GUARDIAN/guardian`，统一密码 `123456`
  - 重建 12 个业务模块：校车线路、站点档案、车辆档案、司机档案、学生档案、家长档案、乘车预约、上车核验、下车核验、异常告警、家长通知、操作日志
  - 前端重写路由、角色首页跳转、动态菜单、登录页、通用数据页、统计看板和全部业务页字段，操作按钮与侧边栏菜单按 `ADMIN / SCHOOL / DISPATCH / DRIVER / TEACHER / GUARDIAN` 收口
  - `readme.md` 与 `readme_simple.md` 的 `169` 详情已从批量泛化模块改为正式校车乘车核验模块说明
  - 残留扫描未发现 `com.p169`、`BizRecord`、`project_169`、旧职业培训证书角色/模块、旧 `record01` 路径、通配符 CORS、`printStackTrace` 或 `System.out.print*`
  - 后端 `mvn.cmd -q clean test` 通过；前端 `npm.cmd install` 与 `npm.cmd run build` 通过，仅有 Vite CJS deprecation 与大包 warning，不阻塞当前结论
  - 当前尚未做真实数据库登录联调；默认环境仍依赖本地 MySQL `school_bus_169` 与 Redis
  - 下一项目为 `170`

### 已正式开发：170 养老机构床位分配与护理记录管理系统
- 本轮新增：
  - 检查发现 `170` 批量版仍为 `com.p170 / BizRecord / project_170` 泛化模板，前端仍使用 `record01-12` 路由和通用业务页
  - 基于 `169` 已验证的正式化流水线生成并执行 `scripts/develop_170.py`；执行前清理校车线路、站点、车辆、司机、学生、乘车预约、上下车核验等旧主题词，端口切换为后端 `8170`、前端 `3170`
  - 将后端切换为 `com.eldercare` 包，启动类为 `ElderCareApplication`，artifactId 为 `elder-care-bed-170`，数据库为 `elder_care_170`，Redis token 前缀为 `eldercare:token:`
  - 新增六类角色与默认账号：`ADMIN/admin`、`ADMISSION/admission`、`NURSING/nursing`、`CAREGIVER/caregiver`、`FAMILY/family`、`DIRECTOR/director`，统一密码 `123456`
  - 重建 12 个业务模块：护理区域、房间档案、床位档案、老人档案、家属档案、入住评估、床位分配、护理计划、护理记录、家属查询、异常上报、操作日志
  - 前端重写路由、角色首页跳转、动态菜单、登录页、通用数据页、统计看板和全部业务页字段，操作按钮与侧边栏菜单按 `ADMIN / ADMISSION / NURSING / CAREGIVER / FAMILY / DIRECTOR` 收口
  - `readme.md` 与 `readme_simple.md` 的 `170` 详情已从批量泛化模块改为正式养老机构床位护理模块说明
  - 残留扫描未发现 `com.p170`、`BizRecord`、`project_170`、旧校车乘车核验角色/模块、旧 `record01` 路径、通配符 CORS、`printStackTrace` 或 `System.out.print*`
  - 后端 `mvn.cmd -q clean test` 通过；前端 `npm.cmd install` 与 `npm.cmd run build` 通过，仅有 Vite CJS deprecation 与大包 warning，不阻塞当前结论
  - 当前尚未做真实数据库登录联调；默认环境仍依赖本地 MySQL `elder_care_170` 与 Redis
  - 下一项目为 `171`

### 已正式开发：171 应急物资储备盘点与调拨审批平台
- 本轮新增：
  - 检查发现 `171` 批量版仍为 `com.p171 / BizRecord / project_171` 泛化模板，前端仍使用 `record01-12` 路由和通用业务页
  - 基于 `170` 已验证的正式化流水线生成并执行 `scripts/develop_171.py`；执行前清理养老机构、床位、护理、老人、家属、入住等旧主题词，端口切换为后端 `8171`、前端 `3171`
  - 将后端切换为 `com.emergencysupply` 包，启动类为 `EmergencySupplyApplication`，artifactId 为 `emergency-supply-dispatch-171`，数据库为 `emergency_supply_171`，Redis token 前缀为 `emergencysupply:token:`
  - 新增六类角色与默认账号：`ADMIN/admin`、`WAREHOUSE/warehouse`、`CHECKER/checker`、`APPLICANT/applicant`、`DISPATCH/dispatch`、`AUDITOR/auditor`，统一密码 `123456`
  - 重建 12 个业务模块：储备仓库、物资分类、物资台账、库存批次、库存盘点、盘点差异、申领工单、调拨审批、调度任务、出库记录、库存预警、操作日志
  - 前端重写路由、角色首页跳转、动态菜单、登录页、通用数据页、统计看板和全部业务页字段，操作按钮与侧边栏菜单按 `ADMIN / WAREHOUSE / CHECKER / APPLICANT / DISPATCH / AUDITOR` 收口
  - `readme.md` 与 `readme_simple.md` 的 `171` 详情已从批量泛化模块改为正式应急物资储备盘点调拨模块说明
  - 残留扫描未发现 `com.p171`、`BizRecord`、`project_171`、旧养老机构角色/模块、旧 `record01` 路径、通配符 CORS、`printStackTrace` 或 `System.out.print*`
  - 后端 `mvn.cmd -q clean test` 通过；前端 `npm.cmd install` 与 `npm.cmd run build` 通过，仅有 Vite CJS deprecation 与大包 warning，不阻塞当前结论
  - 当前尚未做真实数据库登录联调；默认环境仍依赖本地 MySQL `emergency_supply_171` 与 Redis
  - 下一项目为 `172`

### 已正式开发：172 口腔门诊治疗预约与耗材计费管理系统
- 本轮新增：
  - 检查发现 `172` 批量版仍为 `com.p172 / BizRecord / project_172` 泛化模板，前端仍使用 `record01-12` 路由和通用业务页
  - 基于 `171` 已验证的正式化流水线生成并执行 `scripts/develop_172.py`；执行前清理应急物资、储备、盘点、调拨、申领等旧主题词，端口切换为后端 `8172`、前端 `3172`
  - 将后端切换为 `com.dentalclinic` 包，启动类为 `DentalClinicApplication`，artifactId 为 `dental-clinic-treatment-172`，数据库为 `dental_clinic_172`，Redis token 前缀为 `dentalclinic:token:`
  - 新增六类角色与默认账号：`ADMIN/admin`、`RECEPTION/reception`、`DENTIST/dentist`、`NURSE/nurse`、`CASHIER/cashier`、`PATIENT/patient`，统一密码 `123456`
  - 重建 12 个业务模块：诊室档案、医生档案、患者档案、治疗项目、预约诊疗、签到分诊、治疗记录、耗材目录、耗材库存、耗材使用、费用结算、操作日志
  - 前端重写路由、角色首页跳转、动态菜单、登录页、通用数据页、统计看板和全部业务页字段，操作按钮与侧边栏菜单按 `ADMIN / RECEPTION / DENTIST / NURSE / CASHIER / PATIENT` 收口
  - `readme.md` 与 `readme_simple.md` 的 `172` 详情已从批量泛化模块改为正式口腔门诊治疗预约耗材计费模块说明
  - 残留扫描未发现 `com.p172`、`BizRecord`、`project_172`、旧应急物资角色/模块、旧 `record01` 路径、通配符 CORS、`printStackTrace` 或 `System.out.print*`
  - 后端 `mvn.cmd -q clean test` 通过；前端 `npm.cmd install` 与 `npm.cmd run build` 通过，仅有 Vite CJS deprecation 与大包 warning，不阻塞当前结论
  - 当前尚未做真实数据库登录联调；默认环境仍依赖本地 MySQL `dental_clinic_172` 与 Redis
  - 下一项目为 `173`

### 已正式开发：173 高校毕业去向填报与就业帮扶跟踪系统
- 本轮新增：
  - 检查发现 `173` 批量版仍为 `com.p173 / BizRecord / project_173` 泛化模板，前端仍使用 `record01-12` 路由和通用业务页
  - 基于 `172` 已验证的正式化流水线生成并执行 `scripts/develop_173.py`；执行前清理口腔门诊、诊室、医生、患者、治疗、耗材和费用结算等旧主题词，端口切换为后端 `8173`、前端 `3173`
  - 将后端切换为 `com.grademployment` 包，启动类为 `GraduateEmploymentApplication`，artifactId 为 `graduate-employment-help-173`，数据库为 `graduate_employment_173`，Redis token 前缀为 `grademployment:token:`
  - 新增六类角色与默认账号：`ADMIN/admin`、`CAREER/career`、`COLLEGE/college`、`COUNSELOR/counselor`、`STUDENT/student`、`EMPLOYER/employer`，统一密码 `123456`
  - 重建 12 个业务模块：学院专业、毕业生档案、用人单位、招聘岗位、去向填报、协议归档、材料审核、帮扶记录、岗位推荐、跟踪回访、就业统计、操作日志
  - 前端重写路由、角色首页跳转、动态菜单、登录页、通用数据页、统计看板和全部业务页字段，操作按钮与侧边栏菜单按 `ADMIN / CAREER / COLLEGE / COUNSELOR / STUDENT / EMPLOYER` 收口
  - `readme.md`、`readme_simple.md` 与候选清单的 `173` 详情已从批量泛化模块改为正式高校就业帮扶模块说明
  - 残留扫描未发现 `com.p173`、`BizRecord`、`project_173`、旧口腔门诊角色/模块、旧 `record01` 路径、通配符 CORS、`printStackTrace` 或 `System.out.print*`
  - 后端 `mvn.cmd -q clean test` 通过；前端 `npm.cmd install` 与 `npm.cmd run build` 通过，仅有 Vite CJS deprecation 与大包 warning，不阻塞当前结论
  - 当前尚未做真实数据库登录联调；默认环境仍依赖本地 MySQL `graduate_employment_173` 与 Redis
  - 下一项目为 `174`

### 已正式开发：174 社区慢病档案随访与服药依从性管理系统
- 本轮新增：
  - 检查发现 `174` 批量版仍为 `com.p174 / BizRecord / project_174` 泛化模板，前端仍使用 `record01-12` 路由和通用业务页
  - 基于 `173` 已验证的正式化流水线生成并执行 `scripts/develop_174.py`；执行前清理高校毕业、就业中心、学院就业、去向填报、协议归档、材料审核、帮扶记录、岗位推荐和跟踪回访等旧主题词，端口切换为后端 `8174`、前端 `3174`
  - 将后端切换为 `com.chroniccare` 包，启动类为 `ChronicCareApplication`，artifactId 为 `chronic-care-followup-174`，数据库为 `chronic_care_174`，Redis token 前缀为 `chroniccare:token:`
  - 新增六类角色与默认账号：`ADMIN/admin`、`CENTER/center`、`DOCTOR/doctor`、`NURSE/nurse`、`PHARMACIST/pharmacist`、`RESIDENT/resident`，统一密码 `123456`
  - 重建 12 个业务模块：社区站点、患者档案、医护团队、慢病档案、随访计划、随访记录、用药方案、服药打卡、健康指标、风险分层、提醒通知、操作日志
  - 前端重写路由、角色首页跳转、动态菜单、登录页、通用数据页、统计看板和全部业务页字段，操作按钮与侧边栏菜单按 `ADMIN / CENTER / DOCTOR / NURSE / PHARMACIST / RESIDENT` 收口
  - `readme.md`、`readme_simple.md` 与候选清单的 `174` 详情已从批量泛化模块改为正式社区慢病随访与服药依从性模块说明
  - 残留扫描未发现 `com.p174`、`BizRecord`、`project_174`、旧高校就业角色/模块、旧 `record01` 路径、通配符 CORS、`printStackTrace` 或 `System.out.print*`
  - 后端 `mvn.cmd -q clean test` 通过；前端 `npm.cmd install` 与 `npm.cmd run build` 通过，仅有 Vite CJS deprecation 与大包 warning，不阻塞当前结论
  - 当前尚未做真实数据库登录联调；默认环境仍依赖本地 MySQL `chronic_care_174` 与 Redis
  - 下一项目为 `175`

### 已正式开发：175 校园图书漂流借阅与读书打卡平台
- 本轮新增：
  - 检查发现 `175` 批量版仍为 `com.p175 / BizRecord / project_175` 泛化模板，前端仍使用 `record01-12` 路由和通用业务页
  - 基于 `174` 已验证的正式化流水线生成并执行 `scripts/develop_175.py`；执行前清理社区慢病、患者档案、医护团队、随访计划、用药方案、服药打卡、健康指标、风险分层等旧主题词，端口切换为后端 `8175`、前端 `3175`
  - 将后端切换为 `com.bookdrift` 包，启动类为 `CampusBookDriftApplication`，artifactId 为 `campus-book-drift-175`，数据库为 `campus_book_drift_175`，Redis token 前缀为 `bookdrift:token:`
  - 新增六类角色与默认账号：`ADMIN/admin`、`LIBRARY/library`、`CURATOR/curator`、`STUDENT/student`、`CLUB/club`、`TEACHER/teacher`，统一密码 `123456`
  - 重建 12 个业务模块：漂流书架、漂流图书、读者档案、图书捐赠、借阅记录、归还流转、读书打卡、读书笔记、积分勋章、阅读活动、消息通知、操作日志
  - 前端重写路由、角色首页跳转、动态菜单、登录页、通用数据页、统计看板和全部业务页字段，操作按钮与侧边栏菜单按 `ADMIN / LIBRARY / CURATOR / STUDENT / CLUB / TEACHER` 收口
  - `readme.md`、`readme_simple.md` 与候选清单的 `175` 详情已从批量泛化模块改为正式校园图书漂流借阅与读书打卡模块说明
  - 残留扫描未发现 `com.p175`、`BizRecord`、`project_175`、旧社区慢病角色/模块、旧 `record01` 路径、通配符 CORS、`printStackTrace` 或 `System.out.print*`
  - 后端 `mvn.cmd -q clean test` 通过；前端 `npm.cmd install` 与 `npm.cmd run build` 通过，仅有 Vite CJS deprecation 与大包 warning，不阻塞当前结论
  - 当前尚未做真实数据库登录联调；默认环境仍依赖本地 MySQL `campus_book_drift_175` 与 Redis
  - 下一项目为 `176`

### 已正式开发：176 水务巡线工单与阀门检修协同管理系统
- 本轮新增：
  - 检查发现 `176` 批量版仍为 `com.p176 / BizRecord / project_176` 泛化模板，前端仍使用 `record01-12` 路由和通用业务页
  - 基于 `175` 已验证的正式化流水线生成并执行 `scripts/develop_176.py`；执行前清理校园图书漂流、借阅记录、读书打卡、积分勋章、阅读活动、消息通知等旧主题词，端口切换为后端 `8176`、前端 `3176`
  - 将后端切换为 `com.waterpatrol` 包，启动类为 `WaterPatrolApplication`，artifactId 为 `water-patrol-valve-176`，数据库为 `water_patrol_176`，Redis token 前缀为 `waterpatrol:token:`
  - 新增六类角色与默认账号：`ADMIN/admin`、`DISPATCH/dispatch`、`PATROL/patrol`、`REPAIR/repair`、`WAREHOUSE/warehouse`、`SUPERVISOR/supervisor`，统一密码 `123456`
  - 重建 12 个业务模块：水务站点、管线区段、巡线路线、阀门台账、巡线任务、巡线记录、故障上报、故障派工、检修回执、备件台账、风险预警、操作日志
  - 前端重写路由、角色首页跳转、动态菜单、登录页、通用数据页、统计看板和全部业务页字段，操作按钮与侧边栏菜单按 `ADMIN / DISPATCH / PATROL / REPAIR / WAREHOUSE / SUPERVISOR` 收口
  - `readme.md`、`readme_simple.md` 与候选清单的 `176` 详情已从批量泛化模块改为正式水务巡线工单与阀门检修协同模块说明
  - 残留扫描未发现 `com.p176`、`BizRecord`、`project_176`、旧校园图书角色/模块、旧 `record01` 路径、通配符 CORS、`printStackTrace` 或 `System.out.print*`
  - 后端 `mvn.cmd -q clean test` 通过；前端 `npm.cmd install` 与 `npm.cmd run build` 通过，仅有 Vite CJS deprecation 与大包 warning，不阻塞当前结论
  - 当前尚未做真实数据库登录联调；默认环境仍依赖本地 MySQL `water_patrol_176` 与 Redis
  - 下一项目为 `177`

### 已正式开发：177 直播基地主播排班与选品样品管理系统
- 本轮新增：
  - 检查发现 `177` 批量版仍为 `com.p177 / BizRecord / project_177` 泛化模板，前端仍使用 `record01-12` 路由和通用业务页
  - 基于 `176` 已验证的正式化流水线生成并执行 `scripts/develop_177.py`；执行前清理水务巡线、管线区段、巡线路线、阀门台账、巡线任务、故障派工、检修回执、风险预警等旧主题词，端口切换为后端 `8177`、前端 `3177`
  - 将后端切换为 `com.livebase` 包，启动类为 `LiveBaseApplication`，artifactId 为 `live-base-anchor-177`，数据库为 `live_base_177`，Redis token 前缀为 `livebase:token:`
  - 新增六类角色与默认账号：`ADMIN/admin`、`BASE/base`、`ANCHOR/anchor`、`SELECTOR/selector`、`SAMPLE/sample`、`MERCHANT/merchant`，统一密码 `123456`
  - 重建 12 个业务模块：直播间档案、主播档案、商家档案、商品选品、样品台账、样品借还、主播排班、选品评测、直播计划、直播场次、直播复盘、操作日志
  - 前端重写路由、角色首页跳转、动态菜单、登录页、通用数据页、统计看板和全部业务页字段，操作按钮与侧边栏菜单按 `ADMIN / BASE / ANCHOR / SELECTOR / SAMPLE / MERCHANT` 收口
  - `readme.md` 与 `readme_simple.md` 的 `177` 详情已从批量泛化模块改为正式直播基地运营模块说明
  - 残留扫描未发现 `com.p177`、`BizRecord`、`project_177`、旧水务巡线角色/模块、旧 `record01` 路径、通配符 CORS、`printStackTrace` 或 `System.out.print*`
  - 后端 `mvn.cmd -q clean test` 通过；前端 `npm.cmd install` 与 `npm.cmd run build` 通过，仅有 Vite CJS deprecation 和大 chunk warning，不阻塞当前结论
  - 当前尚未做真实数据库登录联调；默认环境仍依赖本地 MySQL `live_base_177` 与 Redis
  - 下一项目为 `178`

### 已正式开发：178 医院手术室器械包追踪与灭菌放行系统
- 本轮新增：
  - 检查发现 `178` 批量版仍为 `com.p178 / BizRecord / project_178` 泛化模板，前端仍使用 `record01-12` 路由和通用业务页
  - 基于 `177` 已验证的正式化流水线生成并执行 `scripts/develop_178.py`；执行前清理直播基地、主播排班、选品评测、样品借还、直播复盘等旧主题词，端口切换为后端 `8178`、前端 `3178`
  - 将后端切换为 `com.orsterile` 包，启动类为 `SterileReleaseApplication`，artifactId 为 `or-pack-sterilization-178`，数据库为 `or_sterile_pack_178`，Redis token 前缀为 `orsterile:token:`
  - 新增六类角色与默认账号：`ADMIN/admin`、`ORNURSE/ornurse`、`CSSD/cssd`、`STERILE/sterile`、`QA/qa`、`SURGEON/surgeon`，统一密码 `123456`
  - 重建 12 个业务模块：手术室档案、器械包档案、器械明细、器械包追踪、灭菌批次、灭菌记录、放行审核、手术使用、回收清点、缺损上报、异常召回、操作日志
  - 前端重写路由、角色首页跳转、动态菜单、登录页、通用数据页、统计看板和全部业务页字段，操作按钮与侧边栏菜单按 `ADMIN / ORNURSE / CSSD / STERILE / QA / SURGEON` 收口
  - `readme.md` 与 `readme_simple.md` 的 `178` 详情已从批量泛化模块改为正式手术室器械包追踪与灭菌放行模块说明
  - 残留扫描未发现 `com.p178`、`BizRecord`、`project_178`、旧直播基地角色/模块、旧 `record01` 路径、通配符 CORS、`printStackTrace` 或 `System.out.print*`
  - 后端 `mvn.cmd -q clean test` 通过；前端 `npm.cmd install` 与 `npm.cmd run build` 通过，仅有 Vite CJS deprecation 和大 chunk warning，不阻塞当前结论
  - 已按 `skills.md` 约定删除 `178-frontend/node_modules`
  - 当前尚未做真实数据库登录联调；默认环境仍依赖本地 MySQL `or_sterile_pack_178` 与 Redis
  - 下一项目为 `179`

### 已正式开发：179 高校考勤异常申诉与课堂巡查管理系统
- 本轮新增：
  - 检查发现 `179` 批量版仍为 `com.p179 / BizRecord / project_179` 泛化模板，前端仍使用 `record01-12` 路由和通用业务页
  - 基于 `178` 已验证的正式化流水线生成并执行 `scripts/develop_179.py`；执行前清理手术室器械、器械包追踪、灭菌记录、放行审核、异常召回等旧主题词，端口切换为后端 `8179`、前端 `3179`
  - 将后端切换为 `com.classattendance` 包，启动类为 `ClassAttendanceApplication`，artifactId 为 `class-attendance-appeal-179`，数据库为 `class_attendance_179`，Redis token 前缀为 `classattendance:token:`
  - 新增六类角色与默认账号：`ADMIN/admin`、`ACADEMIC/academic`、`TEACHER/teacher`、`INSPECTOR/inspector`、`COUNSELOR/counselor`、`STUDENT/student`，统一密码 `123456`
  - 重建 12 个业务模块：教学班级、学生档案、教师档案、课程排课、考勤记录、异常申诉、申诉审核、课堂巡查、巡查问题、整改任务、整改反馈、操作日志
  - 前端重写路由、角色首页跳转、动态菜单、登录页、通用数据页、统计看板和全部业务页字段，操作按钮与侧边栏菜单按 `ADMIN / ACADEMIC / TEACHER / INSPECTOR / COUNSELOR / STUDENT` 收口
  - `readme.md` 与 `readme_simple.md` 的 `179` 详情已从批量泛化模块改为正式高校课堂考勤与巡查整改模块说明
  - 残留扫描未发现 `com.p179`、`BizRecord`、`project_179`、旧手术室器械角色/模块、旧 `record01` 路径、通配符 CORS、`printStackTrace` 或 `System.out.print*`
  - 后端 `mvn.cmd -q clean test` 通过；前端 `npm.cmd install` 与 `npm.cmd run build` 通过，仅有 Vite CJS deprecation 和大 chunk warning，不阻塞当前结论
  - 已按 `skills.md` 约定删除 `179-frontend/node_modules`
  - 当前尚未做真实数据库登录联调；默认环境仍依赖本地 MySQL `class_attendance_179` 与 Redis
  - 下一项目为 `180`

### 已正式开发：180 物业报修派单与服务满意度评价平台
- 本轮新增：
  - 检查发现 `180` 批量版仍为 `com.p180 / BizRecord / project_180` 泛化模板，前端仍使用 `record01-12` 路由和通用业务页
  - 基于 `179` 已验证的正式化流水线生成并执行 `scripts/develop_180.py`；执行前清理高校考勤、教学班级、课程排课、考勤记录、异常申诉、课堂巡查、整改任务和整改反馈等旧主题词，端口切换为后端 `8180`、前端 `3180`
  - 将后端切换为 `com.propertyrepair` 包，启动类为 `PropertyRepairApplication`，artifactId 为 `property-repair-service-180`，数据库为 `property_repair_180`，Redis token 前缀为 `propertyrepair:token:`
  - 新增六类角色与默认账号：`ADMIN/admin`、`PROPERTY/property`、`OWNER/owner`、`DISPATCH/dispatch`、`WORKER/worker`、`FINANCE/finance`，统一密码 `123456`
  - 重建 12 个业务模块：小区区域、业主档案、报修分类、报修工单、派单分配、上门处理、物料使用、费用登记、支付记录、满意评价、投诉回访、操作日志
  - 前端重写路由、角色首页跳转、动态菜单、登录页、通用数据页、统计看板和全部业务页字段，操作按钮与侧边栏菜单按 `ADMIN / PROPERTY / OWNER / DISPATCH / WORKER / FINANCE` 收口
  - `readme.md` 与 `readme_simple.md` 的 `180` 详情已从批量泛化模块改为正式物业报修派单与满意评价闭环模块说明
  - 残留扫描未发现 `com.p180`、`BizRecord`、`project_180`、旧高校课堂考勤角色/模块、旧 `record01` 路径、通配符 CORS、`printStackTrace` 或 `System.out.print*`
  - 后端 `mvn.cmd -q clean test` 通过；前端 `npm.cmd install` 与 `npm.cmd run build` 通过，仅有 Vite CJS deprecation 和大 chunk warning，不阻塞当前结论
  - 已按 `skills.md` 约定删除 `180-frontend/node_modules`
  - 当前尚未做真实数据库登录联调；默认环境仍依赖本地 MySQL `property_repair_180` 与 Redis
  - 下一项目为 `181`

### 已正式开发：181 社区儿童托管签到接送与安全告警系统
- 本轮新增：
  - 检查发现 `181` 批量版仍为 `com.p181 / BizRecord / project_181` 泛化模板，前端仍使用 `record01-12` 路由和通用业务页
  - 基于 `180` 已验证的正式化流水线生成并执行 `scripts/develop_181.py`；执行前清理物业报修、小区区域、业主档案、报修工单、派单分配、上门处理、费用登记、满意评价和投诉回访等旧主题词，端口切换为后端 `8181`、前端 `3181`
  - 将后端切换为 `com.childcare` 包，启动类为 `CommunityChildcareApplication`，artifactId 为 `community-childcare-safety-181`，数据库为 `childcare_safety_181`，Redis token 前缀为 `childcare:token:`
  - 新增六类角色与默认账号：`ADMIN/admin`、`CENTER/center`、`TEACHER/teacher`、`PARENT/parent`、`SECURITY/security`、`NURSE/nurse`，统一密码 `123456`
  - 重建 12 个业务模块：托管班级、儿童档案、监护人档案、签到记录、接送授权、接送记录、安全告警、健康晨检、托管活动、家长通知、事件跟进、操作日志
  - 前端重写路由、角色首页跳转、动态菜单、登录页、通用数据页、统计看板和全部业务页字段，操作按钮与侧边栏菜单按 `ADMIN / CENTER / TEACHER / PARENT / SECURITY / NURSE` 收口
  - `readme.md` 与 `readme_simple.md` 的 `181` 详情已从批量泛化模块改为正式社区儿童托管签到接送与安全告警闭环模块说明
  - 残留扫描未发现 `com.p181`、`BizRecord`、`project_181`、旧物业报修角色/模块、旧 `record01` 路径、通配符 CORS、`printStackTrace` 或 `System.out.print*`
  - 后端 `mvn.cmd -q clean test` 通过；前端 `npm.cmd install` 与 `npm.cmd run build` 通过，仅有 Vite CJS deprecation 和大 chunk warning，不阻塞当前结论
  - 已按 `skills.md` 约定删除 `181-frontend/node_modules`
  - 当前尚未做真实数据库登录联调；默认环境仍依赖本地 MySQL `childcare_safety_181` 与 Redis
  - 下一项目为 `182`

### 已正式开发：182 养老助餐配送排线与营养套餐管理系统
- 本轮新增：
  - 检查发现 `182` 批量版仍为 `com.p182 / BizRecord / project_182` 泛化模板，前端仍使用 `record01-12` 路由和通用业务页
  - 基于 `181` 已验证的正式化流水线生成并执行 `scripts/develop_182.py`；执行前清理社区儿童托管、托管班级、儿童档案、监护人档案、签到记录、接送授权、接送记录、安全告警、健康晨检、家长通知和事件跟进等旧主题词，端口切换为后端 `8182`、前端 `3182`
  - 将后端切换为 `com.eldermeal` 包，启动类为 `ElderMealApplication`，artifactId 为 `elder-meal-delivery-182`，数据库为 `elder_meal_182`，Redis token 前缀为 `eldermeal:token:`
  - 新增六类角色与默认账号：`ADMIN/admin`、`CENTER/center`、`DIETITIAN/dietitian`、`DISPATCH/dispatch`、`COURIER/courier`、`ELDER/elder`，统一密码 `123456`
  - 重建 12 个业务模块：助餐站点、老人档案、营养套餐、助餐订单、配送排线、配送任务、签收回执、饮食禁忌、营养分析、补贴记录、回访关怀、操作日志
  - 前端重写路由、角色首页跳转、动态菜单、登录页、通用数据页、统计看板和全部业务页字段，操作按钮与侧边栏菜单按 `ADMIN / CENTER / DIETITIAN / DISPATCH / COURIER / ELDER` 收口
  - `readme.md` 与 `readme_simple.md` 的 `182` 详情已从批量泛化模块改为正式养老助餐配送与营养套餐闭环模块说明
  - 残留扫描未发现 `com.p182`、`BizRecord`、`project_182`、旧社区儿童托管角色/模块、旧 `record01` 路径、通配符 CORS、`printStackTrace` 或 `System.out.print*`
  - 后端 `mvn.cmd -q clean test` 通过；前端 `npm.cmd install` 与 `npm.cmd run build` 通过，仅有 Vite CJS deprecation 和大 chunk warning，不阻塞当前结论
  - 已按 `skills.md` 约定删除 `182-frontend/node_modules`
  - 当前尚未做真实数据库登录联调；默认环境仍依赖本地 MySQL `elder_meal_182` 与 Redis
  - 下一项目为 `183`

### 已正式开发：183 实验动物饲养排班与伦理审批管理系统
- 本轮新增：
  - 检查发现 `183` 批量版仍为 `com.p183 / BizRecord / project_183` 泛化模板，前端仍使用 `record01-12` 路由和通用业务页
  - 基于 `182` 已验证的正式化流水线生成并执行 `scripts/develop_183.py`；执行前清理养老助餐、助餐站点、老人档案、营养套餐、助餐订单、配送排线、配送任务、签收回执、饮食禁忌、营养分析、补贴记录和回访关怀等旧主题词，端口切换为后端 `8183`、前端 `3183`
  - 将后端切换为 `com.labanimal` 包，启动类为 `LabAnimalApplication`，artifactId 为 `lab-animal-ethics-183`，数据库为 `lab_animal_183`，Redis token 前缀为 `labanimal:token:`
  - 新增六类角色与默认账号：`ADMIN/admin`、`LAB/lab`、`BREEDER/breeder`、`RESEARCHER/researcher`、`ETHICS/ethics`、`VET/vet`，统一密码 `123456`
  - 重建 12 个业务模块：动物房间、实验动物档案、饲养排班、饲养记录、伦理申请、伦理审批、实验登记、健康巡检、异常告警、兽医处置、耗材使用、操作日志
  - 前端重写路由、角色首页跳转、动态菜单、登录页、通用数据页、统计看板和全部业务页字段，操作按钮与侧边栏菜单按 `ADMIN / LAB / BREEDER / RESEARCHER / ETHICS / VET` 收口
  - `readme.md` 与 `readme_simple.md` 的 `183` 详情已从批量泛化模块改为正式实验动物饲养与伦理审批闭环模块说明
  - 残留扫描未发现 `com.p183`、`BizRecord`、`project_183`、旧养老助餐角色/模块、旧 `record01` 路径、通配符 CORS、`printStackTrace` 或 `System.out.print*`
  - 后端 `mvn.cmd -q clean test` 通过；前端 `npm.cmd install` 与 `npm.cmd run build` 通过，仅有 Vite CJS deprecation 和大 chunk warning，不阻塞当前结论
  - 已按 `skills.md` 约定删除 `183-frontend/node_modules`
  - 当前尚未做真实数据库登录联调；默认环境仍依赖本地 MySQL `lab_animal_183` 与 Redis
  - 下一项目为 `184`

### 已正式开发：184 停车场月租合同续费与异常占位管理系统
- 本轮新增：
  - 检查发现 `184` 批量版仍为 `com.p184 / BizRecord / project_184` 泛化模板，前端仍使用 `record01-12` 路由和通用业务页
  - 基于 `183` 已验证的正式化流水线生成并执行 `scripts/develop_184.py`；执行前清理实验动物、动物房间、实验动物档案、饲养排班、饲养记录、伦理申请、伦理审批、实验登记、健康巡检、异常告警、兽医处置和耗材使用等旧主题词，端口切换为后端 `8184`、前端 `3184`
  - 将后端切换为 `com.parkinglease` 包，启动类为 `ParkingLeaseApplication`，artifactId 为 `parking-lease-occupancy-184`，数据库为 `parking_lease_184`，Redis token 前缀为 `parkinglease:token:`
  - 新增六类角色与默认账号：`ADMIN/admin`、`PARKING/parking`、`FINANCE/finance`、`PATROL/patrol`、`TENANT/tenant`、`SERVICE/service`，统一密码 `123456`
  - 重建 12 个业务模块：停车场档案、车位档案、月租车主、月租合同、续费提醒、续费缴费、车辆绑定、占位巡检、异常占位、违规处理、客服工单、操作日志
  - 前端重写路由、角色首页跳转、动态菜单、登录页、通用数据页、统计看板和全部业务页字段，操作按钮与侧边栏菜单按 `ADMIN / PARKING / FINANCE / PATROL / TENANT / SERVICE` 收口
  - `readme.md` 与 `readme_simple.md` 的 `184` 详情已从批量泛化模块改为正式停车场月租合同续费与异常占位闭环模块说明
  - 残留扫描未发现 `com.p184`、`BizRecord`、`project_184`、旧实验动物角色/模块、旧 `record01` 路径、通配符 CORS、`printStackTrace` 或 `System.out.print*`
  - 后端 `mvn.cmd -q clean test` 通过；前端 `npm.cmd install` 与 `npm.cmd run build` 通过，仅有 Vite CJS deprecation 和大 chunk warning，不阻塞当前结论
  - 已按 `skills.md` 约定删除 `184-frontend/node_modules`
  - 当前尚未做真实数据库登录联调；默认环境仍依赖本地 MySQL `parking_lease_184` 与 Redis
  - 下一项目为 `185`

### 已正式开发：185 城市公厕巡检保洁与耗材补给调度系统
- 本轮新增：
  - 检查发现 `185` 批量版仍为 `com.p185 / BizRecord / project_185` 泛化模板，前端仍使用 `record01-12` 路由和通用业务页
  - 基于 `184` 已验证的正式化流水线生成并执行 `scripts/develop_185.py`；执行前清理停车场月租、停车场档案、车位档案、月租车主、月租合同、续费提醒、续费缴费、车辆绑定、占位巡检、异常占位、违规处理和客服工单等旧主题词，端口切换为后端 `8185`、前端 `3185`
  - 将后端切换为 `com.citytoilet` 包，启动类为 `CityToiletApplication`，artifactId 为 `city-toilet-cleaning-185`，数据库为 `city_toilet_185`，Redis token 前缀为 `citytoilet:token:`
  - 新增六类角色与默认账号：`ADMIN/admin`、`SANITATION/sanitation`、`CLEANER/cleaner`、`SUPPLY/supply`、`INSPECTOR/inspector`、`CITIZEN/citizen`，统一密码 `123456`
  - 重建 12 个业务模块：公厕点位、保洁路线、保洁任务、保洁记录、巡检计划、巡检记录、耗材库存、补给申请、补给调度、投诉反馈、问题整改、操作日志
  - 前端重写路由、角色首页跳转、动态菜单、登录页、通用数据页、统计看板和全部业务页字段，操作按钮与侧边栏菜单按 `ADMIN / SANITATION / CLEANER / SUPPLY / INSPECTOR / CITIZEN` 收口
  - `readme.md` 与 `readme_simple.md` 的 `185` 详情已从批量泛化模块改为正式城市公厕巡检保洁与耗材补给闭环模块说明
  - 残留扫描未发现 `com.p185`、`BizRecord`、`project_185`、旧停车月租角色/模块、旧 `record01` 路径、通配符 CORS、`printStackTrace` 或 `System.out.print*`
  - 后端 `mvn.cmd -q clean test` 通过；前端 `npm.cmd install` 与 `npm.cmd run build` 通过，仅有 Vite CJS deprecation 和大 chunk warning，不阻塞当前结论
  - 已按 `skills.md` 约定删除 `185-frontend/node_modules`
  - 当前尚未做真实数据库登录联调；默认环境仍依赖本地 MySQL `city_toilet_185` 与 Redis
  - 下一项目为 `186`

### 已正式开发：186 校园餐厅后厨留样与卫生巡检台账系统
- 本轮新增：
  - 检查发现 `186` 批量版仍为 `com.p186 / BizRecord / project_186` 泛化模板，前端仍使用 `record01-12` 路由和通用业务页
  - 基于 `185` 已验证的正式化流水线生成并执行 `scripts/develop_186.py`；执行前清理城市公厕、公厕点位、保洁路线、保洁任务、保洁记录、巡检计划、巡检记录、耗材库存、补给申请、补给调度、投诉反馈和问题整改等旧主题词，端口切换为后端 `8186`、前端 `3186`
  - 将后端切换为 `com.canteenhygiene` 包，启动类为 `CampusCanteenApplication`，artifactId 为 `campus-canteen-hygiene-186`，数据库为 `campus_canteen_186`，Redis token 前缀为 `canteenhygiene:token:`
  - 新增六类角色与默认账号：`ADMIN/admin`、`CANTEEN/canteen`、`CHEF/chef`、`INSPECTOR/inspector`、`WAREHOUSE/warehouse`、`SCHOOL/school`，统一密码 `123456`
  - 重建 12 个业务模块：餐厅档案、后厨区域、菜品档案、留样登记、留样存储、食材验收、消毒记录、卫生巡检、问题整改、风险提醒、厨余处置、操作日志
  - 前端重写路由、角色首页跳转、动态菜单、登录页、通用数据页、统计看板和全部业务页字段，操作按钮与侧边栏菜单按 `ADMIN / CANTEEN / CHEF / INSPECTOR / WAREHOUSE / SCHOOL` 收口
  - `readme.md` 与 `readme_simple.md` 的 `186` 详情已从批量泛化模块改为正式校园餐厅后厨留样与卫生巡检闭环模块说明
  - 残留扫描未发现 `com.p186`、`BizRecord`、`project_186`、旧城市公厕角色/模块、旧 `record01` 路径、通配符 CORS、`printStackTrace` 或 `System.out.print*`
  - 后端 `mvn.cmd -q clean test` 通过；前端 `npm.cmd install` 与 `npm.cmd run build` 通过，仅有 Vite CJS deprecation 和大 chunk warning，不阻塞当前结论
  - 已按 `skills.md` 约定删除 `186-frontend/node_modules`
  - 当前尚未做真实数据库登录联调；默认环境仍依赖本地 MySQL `campus_canteen_186` 与 Redis
  - 下一项目为 `187`

### 已正式开发：187 旅行社团建行程报名与费用分摊管理平台
- 本轮新增：
  - 检查发现 `187` 批量版仍为 `com.p187 / BizRecord / project_187` 泛化模板，前端仍使用 `record01-12` 路由和通用业务页
  - 基于 `186` 已验证的正式化流水线生成并执行 `scripts/develop_187.py`；执行前清理校园餐厅、后厨区域、菜品档案、留样登记、留样存储、食材验收、消毒记录、卫生巡检、问题整改、风险提醒和厨余处置等旧主题词，端口切换为后端 `8187`、前端 `3187`
  - 将后端切换为 `com.teambuilding` 包，启动类为 `TeamBuildingApplication`，artifactId 为 `travel-team-building-187`，数据库为 `team_building_187`，Redis token 前缀为 `teambuilding:token:`
  - 新增六类角色与默认账号：`ADMIN/admin`、`AGENCY/agency`、`PLANNER/planner`、`FINANCE/finance`、`LEADER/leader`、`PARTICIPANT/participant`，统一密码 `123456`
  - 重建 12 个业务模块：旅行社档案、团队档案、团建行程、行程报名、成员确认、费用预算、费用分摊、缴费记录、通知同步、出行反馈、发票记录、操作日志
  - 前端重写路由、角色首页跳转、动态菜单、登录页、通用数据页、统计看板和全部业务页字段，操作按钮与侧边栏菜单按 `ADMIN / AGENCY / PLANNER / FINANCE / LEADER / PARTICIPANT` 收口
  - `readme.md` 与 `readme_simple.md` 的 `187` 详情已从批量泛化模块改为正式旅行社团建行程报名与费用分摊闭环模块说明
  - 残留扫描未发现 `com.p187`、`BizRecord`、`project_187`、旧校园餐厅角色/模块、旧 `record01` 路径、通配符 CORS、`printStackTrace` 或 `System.out.print*`
  - 后端 `mvn.cmd -q clean test` 通过；前端 `npm.cmd install` 与 `npm.cmd run build` 通过，仅有 Vite CJS deprecation 和大 chunk warning，不阻塞当前结论
  - 已按 `skills.md` 约定删除 `187-frontend/node_modules`，并完成全仓库 `node_modules` 扫描，未发现残留
  - 当前尚未做真实数据库登录联调；默认环境仍依赖本地 MySQL `team_building_187` 与 Redis
  - 下一项目为 `188`

### 已正式开发：188 医疗护理培训签到考核与技能档案系统
- 本轮新增：
  - 检查发现 `188` 批量版仍为 `com.p188 / BizRecord / project_188` 泛化模板，前端仍使用 `record01-12` 路由和通用业务页
  - 基于 `187` 已验证的正式化流水线生成并执行 `scripts/develop_188.py`；执行前清理旅行社团建、团队档案、团建行程、行程报名、成员确认、费用分摊、缴费记录和通知同步等旧主题词，端口切换为后端 `8188`、前端 `3188`
  - 将后端切换为 `com.nursetraining` 包，启动类为 `NurseTrainingApplication`，artifactId 为 `nursing-training-188`，数据库为 `nurse_training_188`，Redis token 前缀为 `nursetraining:token:`
  - 新增六类角色与默认账号：`ADMIN/admin`、`EDU/edu`、`INSTRUCTOR/instructor`、`ASSESSOR/assessor`、`NURSE/nurse`、`HR/hr`，统一密码 `123456`
  - 重建 12 个业务模块：培训项目、护理人员档案、培训班次、培训签到、技能项目、技能考核、证书档案、续训提醒、实操记录、考核申诉、培训通知、操作日志
  - 前端重写路由、角色首页跳转、动态菜单、登录页、通用数据页、统计看板和全部业务页字段，操作按钮与侧边栏菜单按 `ADMIN / EDU / INSTRUCTOR / ASSESSOR / NURSE / HR` 收口
  - `readme.md` 与 `readme_simple.md` 的 `188` 详情已从批量泛化模块改为正式护理培训签到考核闭环模块说明
  - 残留扫描未发现 `com.p188`、`BizRecord`、`project_188`、旧旅行社团建角色/模块、旧 `record01` 路径、通配符 CORS、`printStackTrace` 或 `System.out.print*`
  - 静态结构确认：13 张 SQL 表、13 个实体、13 个 Mapper、15 个 Controller、16 个前端视图
  - 后端 `mvn.cmd -q clean test` 通过；前端 `npm.cmd install` 与 `npm.cmd run build` 通过，仅有 Vite CJS deprecation 和大 chunk warning，不阻塞当前结论
  - 已按 `skills.md` 约定删除 `188-frontend/node_modules`，并完成全仓库 `node_modules` 扫描，未发现残留
  - 当前尚未做真实数据库登录联调；默认环境仍依赖本地 MySQL `nurse_training_188` 与 Redis
  - 下一项目为 `189`

### 已正式开发：189 乡镇农机作业预约与维修保养跟踪平台
- 本轮新增：
  - 检查发现 `189` 批量版仍为 `com.p189 / BizRecord / project_189` 泛化模板，前端仍使用 `record01-12` 路由和通用业务页
  - 基于 `188` 正式化流水线生成并执行 `scripts/develop_189.py`；执行前清理护理培训、培训项目、护理人员档案、培训班次、培训签到、技能项目、技能考核、证书档案、续训提醒、实操记录、考核申诉和培训通知等旧主题词，端口切换为后端 `8189`、前端 `3189`
  - 将后端切换为 `com.farmmachinery` 包，启动类为 `FarmMachineryApplication`，artifactId 为 `township-farm-machinery-189`，数据库为 `farm_machinery_189`，Redis token 前缀为 `farmmachinery:token:`
  - 新增六类角色与默认账号：`ADMIN/admin`、`STATION/station`、`DISPATCH/dispatch`、`DRIVER/driver`、`MECHANIC/mechanic`、`FARMER/farmer`，统一密码 `123456`
  - 重建 12 个业务模块：农机站点、农机档案、农户档案、机手档案、作业预约、机手调度、作业派单、作业回执、维修保养、维修跟踪、季节统计、操作日志
  - 前端重写路由、角色首页跳转、动态菜单、登录页、通用数据页、统计看板和全部业务页字段，操作按钮与侧边栏菜单按 `ADMIN / STATION / DISPATCH / DRIVER / MECHANIC / FARMER` 收口
  - `readme.md` 与 `readme_simple.md` 的 `189` 详情已从批量泛化模块改为正式乡镇农机作业预约与维修保养闭环模块说明
  - 残留扫描未发现 `com.p189`、`BizRecord`、`project_189`、旧护理培训角色/模块、旧 `record01` 路径、通配符 CORS、`printStackTrace` 或 `System.out.print*`
  - 静态结构确认：13 张 SQL 表、13 个实体、13 个 Mapper、15 个 Controller、16 个前端视图
  - 后端 `mvn.cmd -q clean test` 通过；前端 `npm.cmd install` 与 `npm.cmd run build` 通过，仅有 Vite CJS deprecation 和大 chunk warning，不阻塞当前结论
  - 已按 `skills.md` 约定删除 `189-frontend/node_modules`，并完成全仓库 `node_modules` 扫描，未发现残留
  - 当前尚未做真实数据库登录联调；默认环境仍依赖本地 MySQL `farm_machinery_189` 与 Redis
  - 下一项目为 `190`

### 已正式开发：190 智慧楼宇访修协同与设备保养提醒系统
- 本轮新增：
  - 检查发现 `190` 批量版仍为 `com.p190 / BizRecord / project_190` 泛化模板，前端仍使用 `record01-12` 路由和通用业务页
  - 基于 `189` 正式化流水线生成并执行 `scripts/develop_190.py`；端口切换为后端 `8190`、前端 `3190`
  - 将后端切换为 `com.smartbuilding` 包，启动类为 `SmartBuildingApplication`，artifactId 为 `smart-building-maintenance-190`，数据库为 `smart_building_190`，Redis token 前缀为 `smartbuilding:token:`
  - 新增六类角色与默认账号：`ADMIN/admin`、`PROPERTY/property`、`DISPATCH/dispatch`、`TECHNICIAN/technician`、`INSPECTOR/inspector`、`TENANT/tenant`，统一密码 `123456`
  - 重建 12 个业务模块：楼宇档案、设备档案、入驻档案、访修工单、维修派工、保养计划、保养任务、故障预警、巡检记录、备件库存、服务评价、操作日志
  - 前端重写路由、角色首页跳转、动态菜单、登录页、通用数据页、统计看板和全部业务页字段，操作按钮与侧边栏菜单按 `ADMIN / PROPERTY / DISPATCH / TECHNICIAN / INSPECTOR / TENANT` 收口
  - `readme.md` 与 `readme_simple.md` 的 `190` 详情已从批量泛化模块改为正式智慧楼宇访修协同与设备保养闭环模块说明
  - 残留扫描未发现 `com.p190`、`BizRecord`、`project_190`、旧农机角色/模块、旧 `record01` 路径、通配符 CORS、`printStackTrace` 或 `System.out.print*`
  - 静态结构确认：13 张 SQL 表、13 个实体、13 个 Mapper、15 个 Controller、16 个前端视图
  - 按 `rule.md` 约定，本轮未执行 Maven/NPM 编译验证；如需正式交付再单独运行，并在前端构建后清理 `node_modules`
  - 已清理 `190-frontend/node_modules`，并完成全仓库 `node_modules` 扫描，未发现残留
  - 当前尚未做真实数据库登录联调；默认环境仍依赖本地 MySQL `smart_building_190` 与 Redis
  - 下一项目为 `191`

### 已正式开发：191 社区助残器具借用与康复随访平台
- 本轮新增：
  - 检查发现 `191` 批量版仍为 `com.p191 / BizRecord / project_191` 泛化模板，前端仍使用 `record01-12` 路由和通用业务页
  - 基于 `190` 正式化流水线生成并执行 `scripts/develop_191.py`；端口切换为后端 `8191`、前端 `3191`
  - 将后端切换为 `com.assistivecare` 包，启动类为 `AssistiveCareApplication`，artifactId 为 `assistive-device-care-191`，数据库为 `assistive_care_191`，Redis token 前缀为 `assistivecare:token:`
  - 新增六类角色与默认账号：`ADMIN/admin`、`COMMUNITY/community`、`AIDSTAFF/aidstaff`、`THERAPIST/therapist`、`VOLUNTEER/volunteer`、`RESIDENT/resident`，统一密码 `123456`
  - 重建 12 个业务模块：服务站点、居民档案、助残器具、器具借用、借用审核、器具交付、康复计划、康复训练、随访记录、回收提醒、器具维护、操作日志
  - 前端重写路由、角色首页跳转、动态菜单、登录页、通用数据页、统计看板和全部业务页字段，操作按钮与侧边栏菜单按 `ADMIN / COMMUNITY / AIDSTAFF / THERAPIST / VOLUNTEER / RESIDENT` 收口
  - `readme.md` 与 `readme_simple.md` 的 `191` 详情已从批量泛化模块改为正式社区助残器具借用与康复随访闭环模块说明
  - 残留扫描未发现 `com.p191`、`BizRecord`、`project_191`、旧楼宇角色/模块、旧 `record01` 路径、通配符 CORS、`printStackTrace` 或 `System.out.print*`
  - 静态结构确认：13 张 SQL 表、13 个实体、13 个 Mapper、15 个 Controller、16 个前端视图
  - 按 `rule.md` 约定，本轮未执行 Maven/NPM 编译验证；如需正式交付再单独运行，并在前端构建后清理 `node_modules`
  - 完成全仓库 `node_modules` 扫描，未发现残留
  - 当前尚未做真实数据库登录联调；默认环境仍依赖本地 MySQL `assistive_care_191` 与 Redis
  - 下一项目为 `192`

### 已正式开发：192 医院陪护服务预约与护工评价管理系统
- 本轮新增：
  - 检查发现 `192` 批量版仍为 `com.p192 / BizRecord / project_192` 泛化模板，前端仍使用 `record01-12` 路由和通用业务页
  - 基于 `191` 正式化流水线生成并执行 `scripts/develop_192.py`；端口切换为后端 `8192`、前端 `3192`
  - 将后端切换为 `com.hospitalcare` 包，启动类为 `HospitalCareApplication`，artifactId 为 `hospital-care-service-192`，数据库为 `hospital_care_192`，Redis token 前缀为 `hospitalcare:token:`
  - 新增六类角色与默认账号：`ADMIN/admin`、`HOSPITAL/hospital`、`COORDINATOR/coordinator`、`CAREGIVER/caregiver`、`FINANCE/finance`、`FAMILY/family`，统一密码 `123456`
  - 重建 12 个业务模块：病区档案、患者档案、护工档案、陪护预约、预约审核、护工排班、服务派单、服务记录、家属沟通、护工评价、评价结算、操作日志
  - 前端重写路由、角色首页跳转、动态菜单、登录页、通用数据页、统计看板和全部业务页字段，操作按钮与侧边栏菜单按 `ADMIN / HOSPITAL / COORDINATOR / CAREGIVER / FINANCE / FAMILY` 收口
  - `readme.md` 与 `readme_simple.md` 的 `192` 详情已从批量泛化模块改为正式医院陪护服务预约与护工评价闭环模块说明
  - 残留扫描未发现 `com.p192`、`BizRecord`、`project_192`、旧助残角色/模块、旧 `record01` 路径、通配符 CORS、`printStackTrace` 或 `System.out.print*`
  - 静态结构确认：13 张 SQL 表、13 个实体、13 个 Mapper、15 个 Controller、16 个前端视图
  - 按 `rule.md` 约定，本轮未执行 Maven/NPM 编译验证；如需正式交付再单独运行，并在前端构建后清理 `node_modules`
  - 完成全仓库 `node_modules` 扫描，未发现残留
  - 当前尚未做真实数据库登录联调；默认环境仍依赖本地 MySQL `hospital_care_192` 与 Redis
  - 下一项目为 `193`

### 已正式开发：193 校园创新实验班选拔与导师跟踪管理系统
- 本轮新增：
  - 检查发现 `193` 批量版仍为 `com.p193 / BizRecord / project_193` 泛化模板，前端仍使用 `record01-12` 路由和通用业务页
  - 基于 `192` 正式化流水线生成并执行 `scripts/develop_193.py`；端口切换为后端 `8193`、前端 `3193`
  - 将后端切换为 `com.innovationclass` 包，启动类为 `InnovationClassApplication`，artifactId 为 `campus-innovation-class-193`，数据库为 `innovation_class_193`，Redis token 前缀为 `innovationclass:token:`
  - 新增六类角色与默认账号：`ADMIN/admin`、`ACADEMIC/academic`、`REVIEWER/reviewer`、`MENTOR/mentor`、`COUNSELOR/counselor`、`STUDENT/student`，统一密码 `123456`
  - 重建 12 个业务模块：实验班项目、学生档案、导师档案、选拔公告、报名选拔、资格评审、面试考核、导师匹配、培养计划、过程跟踪、成果档案、操作日志
  - 前端重写路由、角色首页跳转、动态菜单、登录页、通用数据页、统计看板和全部业务页字段，操作按钮与侧边栏菜单按 `ADMIN / ACADEMIC / REVIEWER / MENTOR / COUNSELOR / STUDENT` 收口
  - `readme.md` 与 `readme_simple.md` 的 `193` 详情已从批量泛化模块改为正式校园创新实验班选拔与导师跟踪闭环模块说明
  - 残留扫描未发现 `com.p193`、`BizRecord`、`project_193`、旧医院陪护角色/模块、旧 `record01` 路径、通配符 CORS、`printStackTrace` 或 `System.out.print*`
  - 静态结构确认：13 张 SQL 表、13 个实体、13 个 Mapper、15 个 Controller、16 个前端视图
  - 按 `rule.md` 约定，本轮未执行 Maven/NPM 编译验证；如需正式交付再单独运行，并在前端构建后清理 `node_modules`
  - 完成全仓库 `node_modules` 扫描，未发现残留
  - 当前尚未做真实数据库登录联调；默认环境仍依赖本地 MySQL `innovation_class_193` 与 Redis
  - 下一项目为 `194`

### 已正式开发：194 工业园区危废暂存与转运联动监管平台
- 本轮新增：
  - 检查发现 `194` 批量版仍为 `com.p194 / BizRecord / project_194` 泛化模板，前端仍使用 `record01-12` 路由和通用业务页
  - 基于 `193` 正式化流水线生成并执行 `scripts/develop_194.py`；端口切换为后端 `8194`、前端 `3194`
  - 将后端切换为 `com.hazardwaste` 包，启动类为 `HazardWasteApplication`，artifactId 为 `industrial-hazard-waste-194`，数据库为 `hazard_waste_194`，Redis token 前缀为 `hazardwaste:token:`
  - 新增六类角色与默认账号：`ADMIN/admin`、`PARK/park`、`WAREHOUSE/warehouse`、`TRANSPORT/transport`、`INSPECTOR/inspector`、`ENTERPRISE/enterprise`，统一密码 `123456`
  - 重建 12 个业务模块：园区企业、危废类别、暂存设施、入库登记、暂存巡检、转运联单、车辆调度、称重记录、处置交接、风险预警、台账审计、操作日志
  - 前端重写路由、角色首页跳转、动态菜单、登录页、通用数据页、统计看板和全部业务页字段，操作按钮与侧边栏菜单按 `ADMIN / PARK / WAREHOUSE / TRANSPORT / INSPECTOR / ENTERPRISE` 收口
  - `readme.md` 与 `readme_simple.md` 的 `194` 详情已从批量泛化模块改为正式工业园区危废暂存与转运联动监管闭环模块说明
  - 残留扫描未发现 `com.p194`、`BizRecord`、`project_194`、旧创新实验班角色/模块、旧 `record01` 路径、通配符 CORS、`printStackTrace` 或 `System.out.print*`
  - 静态结构确认：13 张 SQL 表、13 个实体、13 个 Mapper、15 个 Controller、16 个前端视图
  - 按 `rule.md` 约定，本轮未执行 Maven/NPM 编译验证；如需正式交付再单独运行，并在前端构建后清理 `node_modules`
  - 完成全仓库 `node_modules` 扫描，未发现残留
  - 当前尚未做真实数据库登录联调；默认环境仍依赖本地 MySQL `hazard_waste_194` 与 Redis
  - 下一项目为 `195`

### 已正式开发：195 便民服务中心事项预约与窗口评价平台
- 本轮新增：
  - 检查发现 `195` 批量版仍为 `com.p195 / BizRecord / project_195` 泛化模板，前端仍使用 `record01-12` 路由和通用业务页
  - 基于 `194` 正式化流水线生成并执行 `scripts/develop_195.py`；端口切换为后端 `8195`、前端 `3195`
  - 将后端切换为 `com.publicservice` 包，启动类为 `PublicServiceApplication`，artifactId 为 `public-service-center-195`，数据库为 `public_service_195`，Redis token 前缀为 `publicservice:token:`
  - 新增六类角色与默认账号：`ADMIN/admin`、`CENTER/center`、`WINDOW/window`、`REVIEW/review`、`SUPERVISE/supervise`、`CITIZEN/citizen`，统一密码 `123456`
  - 重建 12 个业务模块：事项档案、窗口档案、人员排班、事项预约、窗口叫号、材料审核、办理进度、通知提醒、满意评价、投诉处理、绩效统计、操作日志
  - 前端重写路由、角色首页跳转、动态菜单、登录页、通用数据页、统计看板和全部业务页字段，操作按钮与侧边栏菜单按 `ADMIN / CENTER / WINDOW / REVIEW / SUPERVISE / CITIZEN` 收口
  - `readme.md` 与 `readme_simple.md` 的 `195` 详情已从批量泛化模块改为正式便民服务中心事项预约与窗口评价闭环模块说明
  - 残留扫描未发现 `com.p195`、`BizRecord`、`project_195`、旧危废监管角色/模块、旧 `record01` 路径、通配符 CORS、`printStackTrace` 或 `System.out.print*`
  - 静态结构确认：13 张 SQL 表、13 个实体、13 个 Mapper、15 个 Controller、16 个前端视图
  - 按 `rule.md` 约定，本轮未执行 Maven/NPM 编译验证；如需正式交付再单独运行，并在前端构建后清理 `node_modules`
  - 完成全仓库 `node_modules` 扫描，未发现残留
  - 当前尚未做真实数据库登录联调；默认环境仍依赖本地 MySQL `public_service_195` 与 Redis
  - 下一项目为 `196`

### 已正式开发：196 药店处方审核与慢病续方提醒管理系统
- 本轮新增：
  - 检查发现 `196` 批量版仍为 `com.p196 / BizRecord / project_196` 泛化模板，前端仍使用 `record01-12` 路由和通用业务页
  - 基于 `195` 正式化流水线生成并执行 `scripts/develop_196.py`；端口切换为后端 `8196`、前端 `3196`
  - 将后端切换为 `com.pharmacycare` 包，启动类为 `PharmacyCareApplication`，artifactId 为 `pharmacy-prescription-care-196`，数据库为 `pharmacy_care_196`，Redis token 前缀为 `pharmacycare:token:`
  - 新增六类角色与默认账号：`ADMIN/admin`、`PHARMACY/pharmacy`、`PHARMACIST/pharmacist`、`CLERK/clerk`、`FOLLOWUP/followup`、`CUSTOMER/customer`，统一密码 `123456`
  - 重建 12 个业务模块：药店门店、顾客档案、药品目录、处方登记、处方审核、风险复核、购药记录、用药指导、慢病方案、续方提醒、随访记录、操作日志
  - 前端重写路由、角色首页跳转、动态菜单、登录页、通用数据页、统计看板和全部业务页字段，操作按钮与侧边栏菜单按 `ADMIN / PHARMACY / PHARMACIST / CLERK / FOLLOWUP / CUSTOMER` 收口
  - `readme.md` 与 `readme_simple.md` 的 `196` 详情已从批量泛化模块改为正式药店处方审核与慢病续方提醒闭环模块说明
  - 残留扫描未发现 `com.p196`、`BizRecord`、`project_196`、旧便民服务角色/模块、旧 `record01` 路径、通配符 CORS、`printStackTrace` 或 `System.out.print*`
  - 静态结构确认：13 张 SQL 表、13 个实体、13 个 Mapper、15 个 Controller、16 个前端视图
  - 按 `rule.md` 约定，本轮未执行 Maven/NPM 编译验证；如需正式交付再单独运行，并在前端构建后清理 `node_modules`
  - 完成全仓库 `node_modules` 扫描，未发现残留
  - 当前尚未做真实数据库登录联调；默认环境仍依赖本地 MySQL `pharmacy_care_196` 与 Redis
  - 下一项目为 `197`

### 已正式开发：197 社区家政服务预约与人员信用评价系统
- 本轮新增：
  - 检查发现 `197` 批量版仍为 `com.p197 / BizRecord / project_197` 泛化模板，前端仍使用 `record01-12` 路由和通用业务页
  - 基于 `196` 正式化流水线生成并执行 `scripts/develop_197.py`；端口切换为后端 `8197`、前端 `3197`
  - 将后端切换为 `com.housekeeping` 包，启动类为 `HousekeepingApplication`，artifactId 为 `community-housekeeping-197`，数据库为 `housekeeping_197`，Redis token 前缀为 `housekeeping:token:`
  - 新增六类角色与默认账号：`ADMIN/admin`、`AGENCY/agency`、`DISPATCH/dispatch`、`WORKER/worker`、`QUALITY/quality`、`RESIDENT/resident`，统一密码 `123456`
  - 重建 12 个业务模块：服务站点、居民档案、人员档案、服务项目、服务预约、预约审核、人员排班、上门记录、信用评价、投诉处理、费用结算、操作日志
  - 前端重写路由、角色首页跳转、动态菜单、登录页、通用数据页、统计看板和全部业务页字段，操作按钮与侧边栏菜单按 `ADMIN / AGENCY / DISPATCH / WORKER / QUALITY / RESIDENT` 收口
  - `readme.md` 与 `readme_simple.md` 的 `197` 详情已从批量泛化模块改为正式社区家政服务预约与人员信用评价闭环模块说明
  - 残留扫描未发现 `com.p197`、`BizRecord`、`project_197`、旧药店处方角色/模块、旧 `record01` 路径、通配符 CORS、`printStackTrace` 或 `System.out.print*`
  - 静态结构确认：13 张 SQL 表、13 个实体、13 个 Mapper、15 个 Controller、16 个前端视图
  - 按 `rule.md` 约定，本轮未执行 Maven/NPM 编译验证；如需正式交付再单独运行，并在前端构建后清理 `node_modules`
  - 完成全仓库 `node_modules` 扫描，未发现残留
  - 当前尚未做真实数据库登录联调；默认环境仍依赖本地 MySQL `housekeeping_197` 与 Redis
  - 下一项目为 `198`

### 已正式开发：198 城市共享充电宝投放巡检与收益结算系统
- 本轮新增：
  - 检查发现 `198` 批量版仍为 `com.p198 / BizRecord / project_198` 泛化模板，前端仍使用 `record01-12` 路由和通用业务页
  - 基于 `197` 正式化流水线生成并执行 `scripts/develop_198.py`；端口切换为后端 `8198`、前端 `3198`
  - 将后端切换为 `com.powerbank` 包，启动类为 `PowerBankApplication`，artifactId 为 `urban-powerbank-198`，数据库为 `powerbank_198`，Redis token 前缀为 `powerbank:token:`
  - 新增六类角色与默认账号：`ADMIN/admin`、`OPERATOR/operator`、`SITE/site`、`INSPECTOR/inspector`、`FINANCE/finance`、`MERCHANT/merchant`，统一密码 `123456`
  - 重建 12 个业务模块：投放点位、设备柜档案、充电宝档案、点位投放、设备巡检、故障维修、异常回收、租借订单、商户收益、收益结算、库存调拨、操作日志
  - 前端重写路由、角色首页跳转、动态菜单、登录页、通用数据页、统计看板和全部业务页字段，操作按钮与侧边栏菜单按 `ADMIN / OPERATOR / SITE / INSPECTOR / FINANCE / MERCHANT` 收口
  - `readme.md` 与 `readme_simple.md` 的 `198` 详情已从批量泛化模块改为正式城市共享充电宝投放巡检与收益结算闭环模块说明
  - 残留扫描未发现 `com.p198`、`BizRecord`、`project_198`、旧家政服务角色/模块、旧 `record01` 路径、通配符 CORS、`printStackTrace` 或 `System.out.print*`
  - 静态结构确认：13 张 SQL 表、13 个实体、13 个 Mapper、15 个 Controller、16 个前端视图
  - 按 `rule.md` 约定，本轮未执行 Maven/NPM 编译验证；如需正式交付再单独运行，并在前端构建后清理 `node_modules`
  - 完成全仓库 `node_modules` 扫描，未发现残留
  - 当前尚未做真实数据库登录联调；默认环境仍依赖本地 MySQL `powerbank_198` 与 Redis
  - 下一项目为 `199`

### 已正式开发：199 运动康复训练计划与体测评估管理系统
- 本轮新增：
  - 检查发现 `199` 批量版仍为 `com.p199 / BizRecord / project_199` 泛化模板，前端仍使用 `record01-12` 路由和通用业务页
  - 基于 `198` 正式化流水线生成并执行 `scripts/develop_199.py`；端口切换为后端 `8199`、前端 `3199`
  - 将后端切换为 `com.sportrehab` 包，启动类为 `SportRehabApplication`，artifactId 为 `sport-rehab-199`，数据库为 `sport_rehab_199`，Redis token 前缀为 `sportrehab:token:`
  - 新增六类角色与默认账号：`ADMIN/admin`、`CENTER/center`、`ASSESSOR/assessor`、`COACH/coach`、`THERAPIST/therapist`、`MEMBER/member`，统一密码 `123456`
  - 重建 12 个业务模块：康复中心、会员档案、教练档案、体测项目、体测评估、风险提醒、训练计划、训练安排、训练打卡、康复反馈、复评记录、操作日志
  - 前端重写路由、角色首页跳转、动态菜单、登录页、通用数据页、统计看板和全部业务页字段，操作按钮与侧边栏菜单按 `ADMIN / CENTER / ASSESSOR / COACH / THERAPIST / MEMBER` 收口
  - `readme.md` 与 `readme_simple.md` 的 `199` 详情已从批量泛化模块改为正式运动康复训练计划与体测评估闭环模块说明
  - 残留扫描未发现 `com.p199`、`BizRecord`、`project_199`、旧共享充电宝角色/模块、旧 `record01` 路径、通配符 CORS、`printStackTrace` 或 `System.out.print*`
  - 静态结构确认：13 张 SQL 表、13 个实体、13 个 Mapper、15 个 Controller、16 个前端视图
  - 按 `rule.md` 约定，本轮未执行 Maven/NPM 编译验证；如需正式交付再单独运行，并在前端构建后清理 `node_modules`
  - 完成全仓库 `node_modules` 扫描，未发现残留
  - 当前尚未做真实数据库登录联调；默认环境仍依赖本地 MySQL `sport_rehab_199` 与 Redis
  - 下一项目为 `200`

### 已正式开发：200 非遗工坊课程预约与作品展销平台
- 本轮新增：
  - 检查发现 `200` 批量版仍为 `com.p200 / BizRecord / project_200` 泛化模板，前端仍使用 `record01-12` 路由和通用业务页
  - 基于 `199` 正式化流水线生成并执行 `scripts/develop_200.py`；端口切换为后端 `8200`、前端 `3200`
  - 将后端切换为 `com.heritageworkshop` 包，启动类为 `HeritageWorkshopApplication`，artifactId 为 `heritage-workshop-200`，数据库为 `heritage_workshop_200`，Redis token 前缀为 `heritageworkshop:token:`
  - 新增六类角色与默认账号：`ADMIN/admin`、`WORKSHOP/workshop`、`TEACHER/teacher`、`CURATOR/curator`、`SALES/sales`、`VISITOR/visitor`，统一密码 `123456`
  - 重建 12 个业务模块：工坊档案、传承人档案、课程目录、工坊排期、课程预约、预约审核、课程签到、作品档案、作品展销、展销订单、展销结算、操作日志
  - 前端重写路由、角色首页跳转、动态菜单、登录页、通用数据页、统计看板和全部业务页字段，操作按钮与侧边栏菜单按 `ADMIN / WORKSHOP / TEACHER / CURATOR / SALES / VISITOR` 收口
  - `readme.md` 与 `readme_simple.md` 的 `200` 详情已从批量泛化模块改为正式非遗工坊课程预约与作品展销闭环模块说明
  - 残留扫描未发现 `com.p200`、`BizRecord`、`project_200`、旧运动康复角色/模块、旧 `record01` 路径、通配符 CORS、`printStackTrace` 或 `System.out.print*`
  - 静态结构确认：13 张 SQL 表、13 个实体、13 个 Mapper、15 个 Controller、16 个前端视图
  - 按 `rule.md` 约定，本轮未执行 Maven/NPM 编译验证；如需正式交付再单独运行，并在前端构建后清理 `node_modules`
  - 完成全仓库 `node_modules` 扫描，未发现残留
  - 当前尚未做真实数据库登录联调；默认环境仍依赖本地 MySQL `heritage_workshop_200` 与 Redis
- 当前清单已完成到 `200`

## Session: 2026-05-20 127-200 权限模板巡检

### 已完成范围
- 完成 `127-150` 的 PRD / 路由 / 菜单 / 配置 / 控制器首轮静态巡检。
- 完成 `151-200` 的批量权限分布巡检，重点统计 controller 中 `assertAuthenticated(role)` 与 `assertAnyRole(role, ...)` 的复用模式。

### 本轮结论
- `124` 存在明确配置串项：数据库仍指向 `smart_chargepile_safety_122`，与项目 `124` 题目“电动车充电桩预约与运维管理系统”不一致。
- `127-145` 并非全部失真，但已出现一批真实 PRD 偏差：
  - 前端按角色显隐
  - 后端 `page/dashboard` 仍只校验“已登录”
  - 服务层 `page()` 普遍未做角色或本人过滤
- `149-150` 偏差更重：
  - `149` 预约申请查/增/改/删/提交都只要求已登录
  - `150` 患者可以读检查报告分页，但服务层未按患者本人过滤
- `151-200` 已确认形成明显的批量模板问题，不再是单个项目偶发：
  - `151-160` 多数项目统计结果为 `generic=12 / anyRole=48`
  - `161-170` 多数项目统计结果同样为 `generic=12 / anyRole=48`
  - `171-200` 多数项目统计结果继续保持 `generic=12 / anyRole=48`
  - 说明这段项目大概率复用了同一类“分页接口先放登录、写接口再广泛放角色”的权限模板

### 当前判断
- `127-128`：首轮更像默认 MySQL/Redis 强依赖问题，未优先发现明显角色越界。
- `129-150`：属于“已出现较多真实越界”的高风险区段，值得逐项深挖或返修。
- `151-200`：属于“批量角色模板宽放段”，后续应按模板级问题处理，而不是每个项目重新从零证明。

### 下一步建议
- 从 `149-150` 选 1-2 个样板项目做深修验证，确认如何把“本人过滤 + 角色收口”补到控制器和 service 层。
- 并行把 `151-200` 视作一整段高风险模板族，按相同修复策略批量处理。

## Session: 2026-05-20 高风险项目正式建档

### 已完成
- 已把 `124`、`131`、`133`、`135`、`136`、`137`、`143`、`145`、`149`、`150` 的静态巡检结论落成正式报告，共新增 10 份 `docs/checks/*.md`。
- 已将这 10 个项目同步登记到 `docs/project-check-tracker.md`，状态统一标记为 `待修复`，并把总项目数口径修正到真实目录数 `200`。
- 已明确三类高风险模式：
  - 配置串项：`124`
  - 前后端权限脱节：`131`、`133`、`145`
  - 模板串项或本人数据越权：`135`、`136`、`137`、`143`、`149`、`150`

### 当前优先级
- `149`、`150` 已具备“可直接作为返修样板”的条件，优先级最高。
- `135`、`136`、`137` 不只是权限问题，还涉及实体/mapper/service 领域串项，返修时不能只补控制器断言。

### 下一步
- 继续把 `127-130`、`132`、`134`、`138-144`、`146-200` 的首轮静态巡检结论逐步补成正式报告。
- 在返修阶段优先验证 `149`、`150` 如何补“本人过滤 + 角色收口”，再把同类修复策略推广到后续模板族项目。

## Session: 2026-05-20 104 静态对照建档

### 已完成
- 已完成 `104` 的 `PRD.md / PLAN.md / sql/init.sql / application.yml / router / menu / controller` 静态对照。
- 已新增正式报告 `docs/checks/104-license-compliance-platform.md`。
- 已将 `104` 同步登记到 `docs/project-check-tracker.md`，当前状态标记为 `检查中`。

### 当前判断
- `104` 暂未发现类似 `124`、`149`、`150` 的明显 PRD 失真或高风险越权问题。
- 当前主要缺口是 `PLAN.md` 仍以“不执行编译验证”为完成标准，本轮也尚未做 Maven / NPM / 启动复验。

### 下一步
- 先补 `104` 的编译、构建和基础登录验证。
- 继续按顺序推进 `105` 的 PRD 巡检与正式建档。

## Session: 2026-05-20 105 静态巡检建档

### 已完成
- 已完成 `105` 的 `PRD.md / PLAN.md / sql/init.sql / application.yml / router / menu / api / controller` 静态对照。
- 已新增正式报告 `docs/checks/105-api-testcase-platform.md`。
- 已将 `105` 同步登记到 `docs/project-check-tracker.md`，当前状态标记为 `待修复`。

### 当前判断
- `105` 的数据库、端口、默认账号和主体模块命名与 PRD 基本一致，不属于主题串项项目。
- 当前明确缺两块闭环：
  - `Mock规则启停` 未落地
  - `复制` 动作未见实现
- 同时本轮尚未做 Maven / NPM / 启动复验。

### 下一步
- 继续按顺序推进 `106` 的 PRD 巡检与正式建档。
- 后续返修时把 `105` 归入“主体结构对齐，但动作闭环缺口”的一类项目处理。

## Session: 2026-05-20 106 静态对照建档

### 已完成
- 已完成 `106` 的 `PRD.md / PLAN.md / sql/init.sql / application.yml / router / menu / api / controller` 静态对照。
- 已新增正式报告 `docs/checks/106-devops-release-platform.md`。
- 已将 `106` 同步登记到 `docs/project-check-tracker.md`，当前状态标记为 `检查中`。

### 当前判断
- `106` 的数据库、端口、默认账号和主体模块命名与 PRD 基本一致。
- 发布单、审批记录、部署任务、回滚记录、发布计划和回滚预案的关键状态动作，当前都能在前后端静态结构中找到对应实现。
- 当前主要缺口仍是本轮尚未做 Maven / NPM / 启动复验。

### 下一步
- 继续按顺序推进 `107` 的 PRD 巡检与正式建档。
- 后续若要把 `106` 转成 `已完成`，优先补编译、构建和基础登录/页面联调验证。

## Session: 2026-05-20 107 静态对照建档

### 已完成
- 已完成 `107` 的 `PRD.md / PLAN.md / sql/init.sql / application.yml / router / menu / api / controller` 静态对照。
- 已新增正式报告 `docs/checks/107-cloud-monitor-platform.md`。
- 已将 `107` 同步登记到 `docs/project-check-tracker.md`，当前状态标记为 `检查中`。

### 当前判断
- `107` 的数据库、端口、默认账号和主体模块命名与 PRD 基本一致。
- 告警事件、处置工单、维护窗口、容量规划和看板趋势这几条关键闭环，在前后端静态结构中都能找到对应实现。
- 当前主要缺口仍是本轮尚未做 Maven / NPM / 启动复验。

### 下一步
- 继续按顺序推进 `108` 的 PRD 巡检与正式建档。
- 后续若要把 `107` 转成 `已完成`，优先补编译、构建和基础登录/页面联调验证。

## Session: 2026-05-20 108 静态对照建档

### 已完成
- 已完成 `108` 的 `PRD.md / PLAN.md / sql/init.sql / application.yml / router / menu / 关键业务页 / controller` 静态对照。
- 已新增正式报告 `docs/checks/108-cloud-cost-platform.md`。
- 已将 `108` 同步登记到 `docs/project-check-tracker.md`，当前状态标记为 `检查中`。

### 当前判断
- `108` 的数据库、端口、默认账号和主体模块命名与 PRD 基本一致。
- 云账号、预算策略、成本分摊、账单、闲置资源、优化建议、优化规则、节省计划、异常事件和报告快照等关键闭环，在前后端静态结构中都能找到对应实现。
- 当前主要缺口仍是本轮尚未做 Maven / NPM / 启动复验。

### 下一步
- 继续按顺序推进 `109` 的 PRD 巡检与正式建档。
- 后续若要把 `108` 转成 `已完成`，优先补编译、构建和基础登录/页面联调验证。

## Session: 2026-05-20 109 静态对照建档

### 已完成
- 已完成 `109` 的 `PRD.md / PLAN.md / sql/init.sql / application.yml / router / menu / store / login / 关键业务页 / controller` 静态对照。
- 已新增正式报告 `docs/checks/109-data-masking-platform.md`。
- 已将 `109` 同步登记到 `docs/project-check-tracker.md`，当前状态标记为 `待修复`。

### 当前判断
- `109` 的数据库、端口、主体模块命名与 PRD 基本一致，识别任务、脱敏任务、访问申请、导出审批和风险告警等关键动作在前后端静态结构中都能找到对应实现。
- 当前最大问题不是模块缺页，而是“数据负责人”角色编码前后不一致：SQL / 表单枚举使用 `DATA_OWNER`，路由守卫、菜单和后端权限断言使用 `OWNER`。
- 当前主要缺口仍是本轮尚未做 Maven / NPM / 启动复验。

### 下一步
- 继续按顺序推进 `110` 的 PRD 巡检与正式建档。
- 后续返修时优先统一 `OWNER / DATA_OWNER` 角色口径，再补数据负责人链路联调验证。

## Session: 2026-05-21 110 静态对照建档

### 已完成
- 已完成 `110` 的 `PRD.md / PLAN.md / sql/init.sql / application.yml / router / menu / store / login / 关键业务页 / controller` 静态对照。
- 已新增正式报告 `docs/checks/110-privacy-auth-platform.md`。
- 已将 `110` 同步登记到 `docs/project-check-tracker.md`，当前状态标记为 `待修复`。

### 当前判断
- `110` 的数据库、端口、主体模块命名与 PRD 基本一致，授权目的、授权策略、授权记录、访问申请、访问授权、撤销申请、风险预警和审计报告等关键动作在前后端静态结构中都能找到对应实现。
- 当前最大问题不是模块缺页，而是 `隐私官 / 数据使用人` 角色编码前后不一致：SQL / 表单枚举使用 `PRIVACY_OFFICER / DATA_USER`，路由守卫、菜单和后端权限断言使用 `PRIVACY / DATAUSER`。
- 当前主要缺口仍是本轮尚未做 Maven / NPM / 启动复验。

### 下一步
- 继续按顺序推进 `111` 的 PRD 巡检与正式建档。
- 后续返修时优先统一 `PRIVACY_OFFICER / DATA_USER` 与 `PRIVACY / DATAUSER` 角色口径，再补隐私官和数据使用人链路联调验证。

## Session: 2026-05-21 111 静态对照建档

### 已完成
- 已完成 `111` 的 `PRD.md / PLAN.md / sql/init.sql / application.yml / router / menu / store / login / 关键业务页 / controller` 静态对照。
- 已新增正式报告 `docs/checks/111-phishing-training-platform.md`。
- 已将 `111` 同步登记到 `docs/project-check-tracker.md`，当前状态标记为 `检查中`。

### 当前判断
- `111` 的数据库、端口、默认账号和主体模块命名与 PRD 基本一致。
- 邮件模板、演练活动、发送记录、点击追踪、培训课程、培训考试、考试记录和风险评分等关键闭环，在前后端静态结构中都能找到对应实现。
- 当前主要缺口仍是本轮尚未做 Maven / NPM / 启动复验。

### 下一步
- 继续按顺序推进 `112` 的 PRD 巡检与正式建档。
- 后续若要把 `111` 转成 `已完成`，优先补编译、构建和基础登录/页面联调验证。

## Session: 2026-05-21 112 静态对照建档

### 已完成
- 已完成 `112` 的 `PRD.md / PLAN.md / sql/init.sql / application.yml / router / menu / store / login / 关键业务页 / controller` 静态对照。
- 已新增正式报告 `docs/checks/112-zero-trust-platform.md`。
- 已将 `112` 同步登记到 `docs/project-check-tracker.md`，当前状态标记为 `检查中`。

### 当前判断
- `112` 的数据库、端口、默认账号和主体模块命名与 PRD 基本一致。
- 设备资产、员工账号、身份源、风险模型、风险评估、访问策略、准入申请、访问会话、设备证书和审计事件等关键闭环，在前后端静态结构中都能找到对应实现。
- 当前主要缺口仍是本轮尚未做 Maven / NPM / 启动复验。

### 下一步
- 继续按顺序推进 `113` 的 PRD 巡检与正式建档。
- 后续若要把 `112` 转成 `已完成`，优先补编译、构建和基础登录/页面联调验证。

## Session: 2026-05-21 113 静态对照建档

### 已完成
- 已完成 `113` 的 `PRD.md / PLAN.md / sql/init.sql / application.yml / router / menu / store / login / 关键业务页 / controller` 静态对照。
- 已新增正式报告 `docs/checks/113-agri-trace-platform.md`。
- 已将 `113` 同步登记到 `docs/project-check-tracker.md`，当前状态标记为 `检查中`。

### 当前判断
- `113` 的数据库、端口、默认账号和主体模块命名与 PRD 基本一致。
- 种植基地、农户档案、产品批次、种植记录、农资投入、质检报告、区块存证、物流记录、召回事件和监管检查等关键闭环，在前后端静态结构中都能找到对应实现。
- 当前主要缺口仍是本轮尚未做 Maven / NPM / 启动复验。

### 下一步
- 继续按顺序推进 `114` 的 PRD 巡检与正式建档。
- 后续若要把 `113` 转成 `已完成`，优先补编译、构建和基础登录/页面联调验证。
