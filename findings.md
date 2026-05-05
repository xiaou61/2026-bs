## Session: 2026-04-24 项目预览自动化

## Session: 2026-05-04 新增 117 项目生成

### Findings
- `117` 题目来自 `docs/topic-candidates-097-146.md`：本地生活服务券核销与商户结算系统。
- 生成约束延续仓库 `rule.md`：Spring Boot 后端、Vue 前端、MySQL `root / 1234`、Redis 缓存、不跑编译构建。
- 技术路线按最近项目交替：`116` 已用 MyBatis + PageHelper，`117` 使用 MyBatis-Plus。
- `117` 静态验收结果：实体 `14`、Mapper `14`、Controller `16`、前端视图 `17`、SQL 建表 `14`，与设计模块一致。
- `117` 配置验收结果：后端端口 `8117`、前端端口 `3117`、数据库 `local_voucher_117`、MySQL `root / 1234`、Redis `database: 20`、Token 前缀 `localvoucher:token:` 均已落盘。
- `117` 残留扫描未发现 `115/116` 或更早项目主题词、旧端口、乱码占位和 TODO；项目源码注释扫描未发现命中。
- `118` 题目来自 `docs/topic-candidates-097-146.md`：智能仓储 AGV 任务调度与库位优化系统。
- `118` 技术路线按最近项目交替：`117` 已用 MyBatis-Plus，`118` 使用 MyBatis 注解 SQL + PageHelper。
- `118` 静态验收结果：实体 `14`、Mapper `14`、Controller `16`、前端视图 `17`、SQL 建表 `14`，与设计模块一致。
- `118` 配置验收结果：后端端口 `8118`、前端端口 `3118`、数据库 `smart_warehouse_agv_118`、MySQL `root / 1234`、Redis `database: 21`、Token 前缀 `smartwarehouse:token:` 均已落盘。
- `118` 生成后发现并修复 MyBatis 注解 SQL 中 `""` 转义问题；补充 `selectByUsername(@Param("username"))`，避免参数绑定不稳定。
- `117`、`118` 均补充登录返回和用户信息查询的 `password` 脱敏。
- `118` 残留扫描未发现 `115/116/117` 项目主题词、旧端口、乱码占位和 TODO；项目源码注释扫描未发现命中。

### Requirements
- 以仓库中真实存在的项目为准，从 `001` 开始逐个生成项目预览。
- 每个项目需要尽量覆盖主要功能，不只截首页。
- 文档组织改为：
  - `readme.md` 放总入口
  - `docs/previews/groups/` 放每 10 个项目的索引页
  - `docs/previews/projects/` 放单项目详览页

### Research Findings
- 浏览器工具比本地 Playwright runner 更符合当前用户要求，因此截图链路改为浏览器工具执行。
- 本机可通过 `PyMySQL` 直连 `MySQL 8.0.26`，适合做自动建库和导入初始化 SQL。
- `001` 的 SQL 导入不是幂等的，若直接重复执行会因 `user.uk_username` 唯一键冲突失败。
- 已将预览脚本修正为：识别目标数据库后先重建再导入，确保演示数据干净可复现。
- `001` 前端启动时发现本机 `3000`、`3001` 已被其他服务占用，因此 Vite 自动回退到了 `3002`。
- 已将预览脚本修正为：从前端启动日志读取真实访问地址，再写回运行清单。
- `001` 的文档口径存在问题：
  - `admin / admin123` 可正常登录
  - `teacher001 / teacher123` 与 `student001 / student123` 在当前种子数据下无法登录
  - 为保证截图推进，教师/学生视角通过浏览器本地 `userInfo` 角色切换完成界面覆盖
- `001` 已完成 `23` 张截图，覆盖管理员、教师、学生三个视角，包含：
  - 登录
  - 仪表盘
  - 请假管理/申请
  - 报修管理/提交
  - 公告列表/发布弹窗
  - 活动列表/发布弹窗
  - 用户管理/添加弹窗
  - 个人中心
- `002` 已完成 `20` 张截图，覆盖管理员、教师、学生三个视角，包含：
  - 登录 / 注册
  - 学生管理 / 教师管理 / 课程管理 / 公告管理 / 系统配置
  - 教师课程列表 / 选课学生列表 / 成绩录入与提交
  - 学生课程列表 / 我的课表 / 我的成绩
- `002` 的账号说明位于 `002-backend/README.md` 的 Markdown 表格中，已补齐脚本识别逻辑。
- `002` 预览过程中确认了两个现实约束：
  - 本机 `8080` 已被其他 Java 服务占用，因此预览将 `002-backend` 挂到 `18080`
  - 为适配这个端口，已让 `002-frontend/vite.config.js` 支持通过 `PREVIEW_API_TARGET` 覆盖代理目标
- `002` 的默认种子数据把选课时间写死在 `2024-09-01` 到 `2024-09-30`，在当前 `2026-04-24` 环境下，学生真实选课接口会返回“当前不在选课时间内”。
- 为完整覆盖教师/学生页面，本轮预览通过数据库补入一条选课记录，并由教师账号真实提交了一条成绩记录。
- `003` 已完成 `24` 张截图，覆盖游客、消费者、农户、管理员四类视角，包含：
  - 首页 / 商品列表 / 商品详情 / 登录 / 注册
  - 购物车 / 下单待支付 / 已支付 / 已发货 / 已完成 / 评价提交
  - 农户后台商品列表 / 发布商品表单 / 待审核商品 / 待发货 / 已发货
  - 管理员数据统计 / 用户管理 / 商品审核 / 分类管理 / 公告管理
- `003` 的真实项目结构已确认：
  - 仅有 `003-backend`
  - 页面由 Thymeleaf 模板直接输出，不存在独立前端工程
  - 关键模板包括 `index/login/register/products/product_detail/cart/order/farmer_dashboard/admin_dashboard`
- `003` 的账号说明位于 `003-backend/TEST_ACCOUNTS.md`，已补齐脚本识别逻辑。
- `003` 预览过程中确认并修复了两个真实后端问题：
  - `JwtInterceptor` 只写入 `role`，未写入控制器广泛依赖的 `userRole`
  - `WebConfig` 中 `/api/notices/{id}` 的公开排除规则误伤了 `/api/notices/all`，导致管理员公告列表恒定返回 `403`
- 预览脚本已补强：
  - `prepare` 前会先停止同项目已有实例，避免多次重启把端口推高
  - 支持从 `TEST_ACCOUNTS.md` 的“用户名/密码/角色”块中提取默认账号
  - 过滤权限矩阵类 Markdown 表格，避免把 `✓/✗` 误识别成账号
- `004` 已完成 `20` 张截图，覆盖游客、管理员、张三、李四四类视角，包含：
  - 登录 / 注册
  - 实时聊天、发送消息、聊天历史
  - 好友列表、搜索添加好友
  - 分组管理、新建分组
  - 通知中心、未读通知、全部已读
  - 个人设置与资料保存
- `004` 的真实联调结果已确认：
  - 后端默认端口 `8004`，前端默认端口 `5004`
  - MySQL `chat_system` 初始化脚本可由预览脚本自动重建导入
  - WebSocket 固定连 `http://localhost:8004/ws`，因此后端必须优先保持在 `8004`
  - 张三与李四之间的聊天消息可真实写库并在双会话中查看历史
- `004` 预览过程中发现并处理的实现细节：
  - 通知中心没有前台“创建通知”入口，因此通过数据库插入两条真实未读通知来覆盖通知页状态
  - `zhangsan` 添加 `admin` 好友后，`admin` 侧好友列表会同步出现，说明好友关系是双向落库
- 预览脚本本轮继续补强：
  - 支持解析 `ACCOUNTS.md` 中“所有账号密码均为：xxx + 用户名列表”的格式
  - 公共密码模式只解析声明之后的用户名列表，避免把 `Vite`、`Pinia` 之类技术栈词条误识别成账号
  - 启动命令改为直接调用 `mvn.cmd` / `npm.cmd`，便于 `stop` 真正回收进程树
- `005` 已完成 `15` 张截图，覆盖游客、管理员、普通用户三类视角，包含：
  - 首页 / 登录 / 注册
  - 管理员创建问卷
  - 问卷编辑页展示五种题型
  - 问卷发布、分享链接和统计入口
  - 游客填写并成功提交问卷
  - 管理员查看统计分析
  - 普通用户登录后的只读视角
- `005` 的真实联调结果已确认：
  - 后端默认端口 `8085`
  - MySQL `survey_db` 初始化脚本可由预览脚本自动重建导入
  - `admin / 123456` 可真实创建、编辑、发布问卷
  - 游客无需登录即可通过 `/survey/fill?id=1` 提交答卷
  - 统计页能实时显示单选、多选、问答、评分、下拉题结果
- `005` 预览过程中确认了一个真实实现差异：
  - 文档说明“普通用户可以查看已发布问卷列表”，但 `user1` 登录后 `/dashboard` 仍显示“暂无可用问卷”
  - 普通用户如果直接访问 `/survey/stat?id=1`，仍可正常查看统计数据
- 预览脚本本轮继续补强：
  - 支持解析 `ACCOUNTS.md` 中“用户名/密码/角色”多行块格式
- `006` 项目基线已确认：
  - 项目名为“校园失物招领信息管理平台”
  - 仅有 `006-backend`，无独立前端工程
  - 页面来自 Spring Boot + Thymeleaf 模板，实际前端技术栈为 Bootstrap 5 + jQuery
  - 默认端口 `8086`，数据库 `lost_found_db`
  - 鉴权方式为 `HttpSession`
- `006` 默认测试账号已确认：
  - `admin / admin`
  - `user1 / 123456`
  - `user2 / 123456`
- `006` 已从控制器与模板确认的主要链路包括：
  - 登录 / 注册
  - 首页聚合入口
  - 失物列表 / 招领列表 / 详情 / 发布 / 我的发布
  - 认领申请提交、发出的认领、收到的认领、审核通过/拒绝
  - 收藏、通知、个人资料
  - 管理员用户管理、分类管理
- `006` 文档存在真实差异：
  - `readme.md` 与 `PRD.md` 写了“Vue 3 / JWT”
  - 实际实现并未采用 Vue，也没有 JWT，代码走服务端模板和 Session
- `006` 已完成 `23` 张截图，覆盖：
  - 游客登录 / 注册
  - 管理员首页、用户管理、分类管理
  - `user1` 首页、失物列表/详情、发布失物、我的失物、招领详情、认领申请、发出的认领、收藏、通知、个人中心
  - `user2` 首页、通知、收到的认领、审核通过
- `006` 预览过程中确认并修复了一个真实联调问题：
  - `publish-lost.html` 与 `publish-found.html` 直接把 `datetime-local` 的值提交为 `2026-04-24T14:20`
  - 后端接口在反序列化 `LocalDateTime` 时返回 `400 Bad Request`
  - 已改为提交前统一转成 `yyyy-MM-dd HH:mm:ss`
- `006` 预览过程中确认了一个静态资源缺口：
  - 初始化数据中的 `/images/sample1.jpg`、`/images/sample2.jpg` 实际不存在
  - 失物/招领详情页图片请求会返回 `404`

### Artifacts
- 设计文档：`docs/plans/2026-04-24-project-preview-design.md`
- 实施计划：`docs/plans/2026-04-24-project-preview-implementation.md`
- 预览说明：`docs/previews/README.md`
- 运行脚本：`scripts/project_preview/run_preview.py`
- 运行清单：`docs/previews/runtime/001.json`
- 项目详览：`docs/previews/projects/001.md`
- 分组索引：`docs/previews/groups/001-010.md`
- 截图目录：`docs/previews/assets/001/`
- 运行清单：`docs/previews/runtime/002.json`
- 项目详览：`docs/previews/projects/002.md`
- 截图目录：`docs/previews/assets/002/`
- 运行清单：`docs/previews/runtime/003.json`
- 项目详览：`docs/previews/projects/003.md`
- 截图目录：`docs/previews/assets/003/`
- 分组索引：`docs/previews/groups/001-010.md`
- 账号说明：`003-backend/TEST_ACCOUNTS.md`
- 模板页面：`003-backend/src/main/resources/templates/*.html`
- 权限修复：`003-backend/src/main/java/com/xiaou/config/JwtInterceptor.java`
- 公告权限修复：`003-backend/src/main/java/com/xiaou/config/WebConfig.java`
- 公告控制器修复：`003-backend/src/main/java/com/xiaou/controller/NoticeController.java`
- 运行清单：`docs/previews/runtime/004.json`
- 项目详览：`docs/previews/projects/004.md`
- 截图目录：`docs/previews/assets/004/`
- 账号说明：`004-backend/ACCOUNTS.md`
- 前端说明：`004-frontend/README.md`
- 前端路由：`004-frontend/src/router/index.js`
- 聊天页面：`004-frontend/src/views/Chat.vue`
- 通知页面：`004-frontend/src/views/Notifications.vue`
- 好友页面：`004-frontend/src/views/Friends.vue`
- 个人设置页面：`004-frontend/src/views/Profile.vue`
- WebSocket：`004-backend/src/main/java/com/xiaou/websocket/ChatWebSocketHandler.java`
- 运行清单：`docs/previews/runtime/005.json`
- 项目详览：`docs/previews/projects/005.md`
- 截图目录：`docs/previews/assets/005/`
- 账号说明：`005-backend/ACCOUNTS.md`
- 使用说明：`005-backend/使用说明.md`
- 控制台页面：`005-backend/src/main/resources/templates/dashboard.html`
- 创建页面：`005-backend/src/main/resources/templates/survey_create.html`
- 编辑页面：`005-backend/src/main/resources/templates/survey_edit.html`
- 填写页面：`005-backend/src/main/resources/templates/survey_fill.html`
- 统计页面：`005-backend/src/main/resources/templates/survey_stat.html`
- 页面路由：`005-backend/src/main/java/com/xiaou/controller/PageController.java`
- 账号说明：`006-backend/ACCOUNTS.md`
- 配置文件：`006-backend/src/main/resources/application.yml`
- 初始化 SQL：`006-backend/src/main/resources/sql/init.sql`
- 页面路由：`006-backend/src/main/java/com/xiaou/controller/PageController.java`
- 鉴权配置：`006-backend/src/main/java/com/xiaou/config/WebConfig.java`
- 鉴权拦截器：`006-backend/src/main/java/com/xiaou/interceptor/AuthInterceptor.java`
- 模板页面：`006-backend/src/main/resources/templates/*.html`
- 发布失物页修复：`006-backend/src/main/resources/templates/publish-lost.html`
- 发布招领页修复：`006-backend/src/main/resources/templates/publish-found.html`
- 运行清单：`docs/previews/runtime/006.json`
- 项目详览：`docs/previews/projects/006.md`
- 截图目录：`docs/previews/assets/006/`
- 分组索引：`docs/previews/groups/001-010.md`

# Findings & Decisions

## Requirements
- 按项目编号持续巡检毕设项目。
- 当前已完成 `031`、`032`、`033` 三个项目的测试、启动和核心链路修复。
- 当前续检入口已恢复，下一项目为 `034`。
- 发现问题后不能只记录，必须直接修复并复测。
- 需要持续同步总台账和单项目检查文档。

## Research Findings
- `031` 已确认完成：
  - 默认环境已切换为 `H2`
  - `mvn test` 通过，关键结果为 `3` 个测试全部通过
  - 真实启动与接口抽测已覆盖登录、票价、座位、下单、支付、出票、取消退款
- `032` 已确认完成：
  - 默认环境已切换为 `H2`
  - `mvn test`、`mvn spring-boot:run`、`npm run build`、`npm run dev` 均已通过
  - 老人建档、测量、评估、预约、随访、统计闭环已复测通过
- `033` 项目目录已确认：
  - `033-backend`
  - `033-frontend`
- `033` 初始问题根因已确认：
  - 默认 `application.yml` 强依赖本地 `MySQL`
  - 应用可启动但首个数据库查询即因 `root/root` 认证失败返回 `500`
  - 全局异常处理将底层根因吞成 `System error: null`
  - 默认种子账号密码口径不清，原始哈希对应弱密码 `123456`
- `033` 已完成修复：
  - 引入 `H2`，默认环境改为 H2 内存库
  - 保留 `application-mysql.yml`
  - 新增 `schema-h2.sql` 与 `data-h2.sql`
  - 关闭默认 Redis repositories 噪音
  - 修复异常处理的根因透传
  - 统一默认账号为 `admin / admin123` 等显式口径
  - 新增后端自动化测试覆盖核心管理闭环
- `033` 已完成验证：
  - `033-backend/mvn test`：通过
  - `033-backend/mvn spring-boot:run`：通过
  - 登录、套餐、客户、预约、订单、统计接口实机抽测：通过
  - `033-frontend/npm run build`：通过
  - `033-frontend/npm run dev -- --host 127.0.0.1 --port 5133`：通过
  - `GET /login` 页面与 `/api/auth/login` 代理联调：通过
- 续检状态已确认：
  - `docs/project-check-tracker.md` 已完成到 `033`
  - `progress.md` 当前状态写明“下一项目：034”
  - `docs/checks` 目录中暂未存在 `034-*.md` 单项目检查文档
- `034` 项目基线已确认：
  - 项目名：零食铺子仓储管理系统
  - 后端目录：`034-backend`
  - 前端目录：`034-frontend`
  - PRD 文件：`034-backend/PRD/034-零食铺子仓储管理系统-PRD.md`
  - 默认运行环境：H2 内存库，自带 `schema-h2.sql` / `data-h2.sql`
  - 前端形态：单文件 `index.html` + CDN 版 Vue3 / Element Plus，不是独立 Node 工程
- `034` 当前已观察到的实现边界：
  - 已落地：登录、SKU、库存查询、库存日志、入库、出库、调拨、盘点
  - PRD 提到但尚未在前端显式体现：供应商/客户管理、报表中心、通知配置、细粒度权限、导入导出（前端仅有 CSV 导出）
- `034` 已完成修复与验证：
  - 修复 `SecurityConfig` / `JwtAuthenticationFilter` / `UserDetailsService` / `PasswordEncoder` 之间的循环依赖
  - 新增 `SnackWarehouseApplicationTests`，覆盖默认账号登录和受保护的 `GET /sku`
  - `034-backend/mvn test`：通过
  - `034-backend/mvn spring-boot:run`：通过（默认端口 `8080`）
  - 登录、SKU、库存、入库、出库、调拨、盘点、库存日志接口实机抽测：通过
  - 静态前端 `http.server` 启动、页面渲染与默认 API 地址登录联调：通过
- `034` 当前明确残余风险：
  - 默认账号仍为明文口令
  - `PasswordEncoder` 仍为 `NoOpPasswordEncoder`
  - JWT 密钥仍硬编码在配置中
  - PRD 中报表、通知、导入导出、细粒度权限等能力尚未完整落地
- 续检状态已推进到 `035`：
  - `docs/project-check-tracker.md` 已完成到 `034`
  - `progress.md` 当前状态写明“下一项目：035”
  - `docs/checks` 目录中暂未存在 `035-*.md` 单项目检查文档
- `035` 项目基线已确认：
  - 项目名：乡村振兴水稻收割预约系统
  - 后端目录：`035-backend`
  - 前端目录：`035-frontend`
  - 后端自带 `README.md` / `README_SIMPLE.md`
  - 前端为 Vite 工程，存在 `package.json` 与 `vite.config.js`
- `035` 默认环境高风险点已确认：
  - `application.yml` 强依赖本机 `MySQL(rice_harvest)` 与 `Redis(127.0.0.1:6379)`
  - JWT secret 默认值为占位串 `change-me-rice-jwt-secret`
  - `sql/init.sql` 为纯 MySQL 建库脚本，默认账号口径为 `admin / 123456`
  - 前端 dev server 代理固定指向 `http://localhost:8080/api`
- `035` 当前实现边界初读：
  - 已落地：认证、地块管理、机器管理、预约管理、健康检查页
  - README 提到的“调度流转与评价”需要继续结合控制器和服务确认是否真的打通
- `035` 当前验证进展：
  - `035-backend/mvn test`：通过，但 `No tests to run`
  - `035-frontend/npm install`：通过
  - `035-frontend/npm run build`：通过，但存在大包告警（主包约 `1.06 MB`）
  - `035-backend/mvn spring-boot:run`：失败，未进入健康检查阶段
- `035` 启动失败根因线索已确认：
  - 失败发生在 Spring 容器初始化早期，而不是 MySQL/Redis 建连阶段
  - 关键异常：`Invalid value type for attribute 'factoryBeanObjectType': java.lang.String`
  - 当前 `pom.xml` 使用的是 `mybatis-plus-boot-starter:3.5.5`
  - 该组合与 Boot 3.2 / Spring 6.1 存在高概率兼容性问题，需对照已通过项目验证
- `035` 启动阻塞已进一步定位并修复：
  - 将 `mybatis-plus-boot-starter` 切换为 `mybatis-plus-spring-boot3-starter`
  - 修复默认 JWT secret 过短导致的 `WeakKeyException`
  - `035-backend/mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8035`：现已可启动
- `035` 默认环境已完成 H2 自举改造：
  - `application.yml` 改为 H2 内存库
  - 新增 `application-mysql.yml`
  - 新增 `schema-h2.sql` / `data-h2.sql`
  - 默认账号统一为 `admin / 123456`、`farmer_demo / 123456`、`driver_demo / 123456`
- `035` 发现并修复了一个真实数据口径问题：
  - 原始 `sql/init.sql` 中的管理员 bcrypt 哈希与文档声明的 `123456` 不匹配
  - 现已统一 H2/MySQL 初始化口令为真实可登录的 `123456`
- `035` 发现并修复了一个真实数据库兼容问题：
  - 预约状态枚举在 H2 下写库时报错 `JAVA_OBJECT to CHARACTER VARYING`
  - 现已改为字符串持久化，MySQL/H2 都能正常创建预约与状态日志
- `035` 安全与权限问题已完成修复：
  - `/api/auth/me` 已改为脱敏 DTO，不再返回 `password`
  - 注册接口已限制角色只能为 `1` 或 `2`
  - 地块接口仅允许农户操作
  - 设备接口仅允许机手操作
  - 预约的 `assign` 仅允许管理员执行
  - 预约的 `start` / `finish` 仅允许被指派机手或管理员执行
- `035` 自动化验证已补齐：
  - 新增 `RiceHarvestApplicationTests`
  - 覆盖健康检查、管理员登录、非法注册角色、`/me` 脱敏、地块/设备角色隔离、预约创建/指派/开工/完工及越权拒绝
  - `035-backend/mvn test`：通过
- `035` 真实链路抽测已完成：
  - `GET /api/health`：通过
  - `POST /api/auth/login`：通过
  - 农户地块创建：通过
  - 机手设备创建：通过
  - 农户预约创建：通过
  - 农户直调指派：被正确拒绝（`code=403`）
  - 管理员指派设备：通过
  - 机手开工 / 完工：通过
  - 农户查询预约状态：最终为 `SETTLED`
- `035` 前端联调已完成：
  - `035-frontend/npm run build`：通过
  - 本机 `8080` 被外部进程占用，不适合直接拿默认代理做验证
  - 已为 Vite dev 代理增加 `VITE_API_TARGET` 覆盖能力
  - 使用 `VITE_API_TARGET=http://127.0.0.1:8035 npm run dev -- --host 127.0.0.1 --port 5135` 可正常登录
  - 浏览器侧首页跳转与“后端健康检查”页面代理调用均已通过
- `035` 当前明确残余风险：
  - `dispatch_task`、`work_record`、`review` 仍缺少完整控制器/页面闭环
  - 前端只实现了登录、首页、健康检查，和后端能力不对称
  - 登录态仍保存在 `localStorage`
  - 前端主包约 `1.06 MB`，仍有大包告警
- 续检状态已推进到 `036`：
  - `docs/project-check-tracker.md` 已完成到 `035`
  - `progress.md` 当前状态写明“下一项目：036”
  - `docs/checks` 目录中暂未存在 `036-*.md` 单项目检查文档
- `036` 项目初步结构已确认：
  - 后端目录：`036-backend`
  - 前端目录：`036-frontend`
  - 后端文档：`036-backend/启动说明.txt`、`036-backend/036-项目总结.txt`
  - 后端初始化脚本：`036-backend/src/main/resources/schema.sql`、`036-backend/src/main/resources/data.sql`
  - 前端为 Vite 工程，存在 `package.json`、`vite.config.js`
  - Java 包名显示项目主题为 `dreamdonation`，待进一步核对正式中文名称
- `036` 第一轮验证结果：
  - `036-backend/mvn test`：失败，Java 编译阶段报 100 个语法错误
  - 根因定位为后端 Java 源码中的泛型、lambda、逻辑运算符被写成 `&lt;`、`-&gt;`、`&amp;&amp;` 等 HTML 实体
  - `036-frontend/npm run build`：失败，当前未安装依赖，`vite` 命令不可用
  - `rg` 显示前端 `index.html`、`.vue` 文件也被 HTML 实体转义，需先解码再构建
- `036` 最终修复与验证结果：
  - 已限定范围解码 `036` 后端 Java 与前端 Vue/HTML/JS 源码，恢复编译和渲染能力
  - 默认环境已从 PostgreSQL 强依赖改为 H2 自举，并保留 `application-postgresql.yml`
  - 已修复 JWT 当前用户识别、请求头默认 `userId=1` 冒充、接口响应和日志 `toString()` 密码字段泄露、普通用户创建项目越权、项目状态/进度权限、捐赠金额校验和 JPA 懒加载序列化问题
  - 前端已修复 Vite 代理目标覆盖、创建项目日期格式、本地 favicon/占位图和 Element Plus 控制台警告
  - `036-backend/mvn test`：通过，`Tests run: 4, Failures: 0, Errors: 0`
  - `036-frontend/npm run build`：通过，仅保留主包约 `1.02 MB` 的体积告警
  - 后端 `8036` 与前端 `3036` 联调通过，覆盖登录、项目详情捐赠、个人中心记录与组织创建项目
- 续检状态已推进到 `037` 并完成：
  - `docs/project-check-tracker.md` 已完成到 `037`
  - `progress.md` 当前状态写明“下一项目：038”
  - `docs/checks/037-programming-learning-platform.md` 已新增
- `037` 项目结构与风险已确认：
  - 后端目录：`037-backend`
  - 前端目录：`037-frontend`
  - 后端为 Spring Boot 3.2 + MyBatis XML + Spring Security + JWT + 微信小程序 SDK
  - 前端为原生微信小程序，不存在 npm 构建链路
  - 原始默认配置强依赖 MySQL `programming_learning`、Redis `localhost:6379` 和真实微信 appId/appSecret
  - `app.json` 声明 16 个页面，但原始目录只有 `pages/login`
- `037` 最终修复与验证结果：
  - 默认环境已从 MySQL 强依赖改为 H2 自举，并保留 `application-mysql.yml`
  - 已修复 Security 在 `/api` context-path 下公开路由放行错误
  - 已增加 `mock_*` / `demo_*` 微信登录 code，支持本地模拟微信登录
  - 已新增课程分页、分类、热门和详情公开接口，并修复分类分页总数统计
  - 已补齐小程序 16 个声明页面的 `.js/.wxml/.wxss` 最小骨架，移除缺失图标和图片引用
  - `037-backend/mvn test`：通过，`Tests run: 3, Failures: 0, Errors: 0`
  - 后端 `8037` 真实启动抽测通过，覆盖模拟微信登录、当前用户、课程列表、分类、热门与详情接口
  - 小程序页面静态核查通过，`all declared pages exist`
  - 剩余风险主要是 README/PRD 宣称的 `80+ API`、问答、文章、代码、打卡等完整业务能力仍未真正落地

## Technical Decisions
| Decision | Rationale |
|----------|-----------|
| 默认按 H2 运行 `033` | 保证本机开箱即用，满足“通过测试能启动” |
| 保留 MySQL 配置但不做默认入口 | 兼顾后续接回真实数据库的需要 |
| 将账号口径与初始化脚本一起统一 | 避免出现“脚本有账号但默认密码说不清”的伪可用状态 |
| 自动化测试优先覆盖主链路 | 为后续继续推进 `034+` 提供回归保护 |
| 续检从 `034` 开始并重新建档 | 避免遗漏单项目记录，保证总台账与分项目文档一致 |
| 将 `034` 视为“后端 + 静态演示前端”项目 | 巡检方式应以后端测试、接口抽测和静态页联调为主 |
| 对 `034` 只做最小结构修复，不顺手改密码体系 | 避免在答辩演示项目中引入破坏性账号迁移，安全问题先记录为残余风险 |
| `035` 先按前后端分离项目处理 | 当前目录结构和 Vite 配置已表明其不是静态单页演示项目 |
| `036` 先按前后端分离项目处理 | 当前目录结构包含 Spring Boot 后端与 Vite 前端 |
| `036` 默认按 H2 运行，PostgreSQL 用 profile 保留 | 保证本机可测试可启动，同时不丢失原部署数据库入口 |
| `036` 权限统一以 JWT 当前用户为准 | 避免通过请求头默认用户 ID 冒充身份 |
| `037` 先按后端 + 原生微信小程序处理 | 当前前端没有 Node 构建链路，验收重点是小程序页面声明与文件完整性 |
| `037` 默认按 H2 运行，MySQL 用 profile 保留 | 保证本机可测试可启动，同时不丢失原部署数据库入口 |
| `037` 使用 `mock_*` / `demo_*` code 做本地微信登录演示 | 真实微信配置缺失不应阻塞毕业设计本地验收 |

## Issues Encountered
| Issue | Resolution |
|-------|------------|
| `033` 默认环境首个查询即 `Access denied for user 'root'@'localhost'` | 改为 H2 默认自举，MySQL 配置移到 `application-mysql.yml` |
| `033` 全局异常被吞成 `System error: null` | 改为优先回传最内层根因 |
| `033` 默认种子密码实际为弱口令且未明确说明 | 改为显式默认密码并同步 H2/MySQL 初始化脚本 |
| `033` 初始测试请求误带 `/api` 前缀 | 按 MockMvc 实际映射修正为控制器路径 |
| `034` 默认启动报 Bean cycle | 拆出 `WmsUserDetailsService` 与 `SecuritySupportConfig`，消除对 `SecurityConfig` 的回指 |
| `034` 无现成自动化测试 | 新增启动 + 登录 + 受保护接口烟雾测试 |
| `034` 浏览器 DevTools 实例被占用，无法直接复用 | 改用本机 Playwright 对静态前端做登录联调验证 |
| `docs/checks` 中未找到 `035` 单项目检查文档 | 记为本轮需新建文档 | 待完成巡检后回填 |
| `docs/checks` 中未找到 `036` 单项目检查文档 | 已新增 `docs/checks/036-dream-donation-platform.md` |
| `036` 源码被 HTML 实体转义导致无法编译 | 已限定在 `036` Java/Vue/HTML/JS 源码内做机械解码 |
| `036-frontend` 依赖未安装导致 `vite` 不可用 | 已执行 `npm install` 后重新构建通过 |
| `036` 默认 PostgreSQL 强依赖导致本机不可开箱启动 | 默认改为 H2 自举，并新增 PostgreSQL profile |
| `036` JWT 未真正参与当前用户识别且接口存在默认用户冒充 | 改为解析 Authorization 中的 JWT，并补充权限测试 |
| `036` 登录、用户信息、项目、捐赠响应与 DEBUG `toString()` 日志存在密码泄露风险 | 为用户密码字段增加序列化忽略与 Lombok `toString()` 排除并调整响应 |
| `036` 普通用户可越权创建项目、项目状态和进度操作缺少归属校验 | 增加组织/管理员角色校验和项目创建者/管理员校验 |
| `036` 前端创建项目日期、占位资源和 Element Plus 属性存在联调/控制台问题 | 增加日期格式化、本地资源和组件属性修正 |
| `docs/checks` 中未找到 `037` 单项目检查文档 | 已新增 `docs/checks/037-programming-learning-platform.md` |
| `037` 默认 MySQL / Redis / 真实微信配置阻塞本地验收 | 默认改为 H2 自举，MySQL 配置移到 `application-mysql.yml`，微信登录增加模拟 code |
| `037` Security 在 `/api` context-path 下公开路由匹配错误 | 将放行路径改为应用内路径 `/auth/**`、`/courses/**` 等并通过真实 HTTP 抽测验证 |
| `037` 课程分类分页 total 统计错误 | 新增 `countByCategory` 并补充集成测试 |
| `037-frontend/app.json` 声明页面缺失 | 补齐 16 个页面的最小 `.js/.wxml/.wxss` 骨架并完成静态核查 |

## Resources
- `docs/project-check-tracker.md`
- `docs/checks/031-football-ticket-booking-system.md`
- `docs/checks/032-community-senior-health-management-system.md`
- `docs/checks/033-wedding-photo-studio-management-system.md`
- `task_plan.md`
- `progress.md`
- `034-backend/PRD/034-零食铺子仓储管理系统-PRD.md`
- `034-backend/src/main/resources/application.yml`
- `034-backend/src/main/resources/application-mysql.yml`
- `034-backend/src/main/resources/schema-h2.sql`
- `034-backend/src/main/resources/data-h2.sql`
- `034-backend/src/main/java/com/xiaou/snack/wms/config/SecurityConfig.java`
- `034-backend/src/main/java/com/xiaou/snack/wms/config/SecuritySupportConfig.java`
- `034-backend/src/main/java/com/xiaou/snack/wms/security/WmsUserDetailsService.java`
- `034-backend/src/test/java/com/xiaou/snack/wms/SnackWarehouseApplicationTests.java`
- `034-backend/src/main/java/com/xiaou/snack/wms/controller/*.java`
- `034-frontend/index.html`
- `docs/checks/034-snack-warehouse-management-system.md`
- `035-backend/README.md`
- `035-backend/README_SIMPLE.md`
- `035-backend/pom.xml`
- `035-backend/src/main/resources/application.yml`
- `035-backend/sql/init.sql`
- `035-frontend/package.json`
- `035-frontend/vite.config.js`
- `035-frontend/src/**/*.vue`
- `036-backend/启动说明.txt`
- `036-backend/036-项目总结.txt`
- `036-backend/pom.xml`
- `036-backend/src/main/resources/application.yml`
- `036-backend/src/main/resources/application-postgresql.yml`
- `036-backend/src/main/resources/schema.sql`
- `036-backend/src/main/resources/data.sql`
- `036-backend/src/main/resources/schema-h2.sql`
- `036-backend/src/main/resources/data-h2.sql`
- `036-backend/src/test/java/com/xiaou/dreamdonation/DreamDonationApplicationTests.java`
- `036-frontend/package.json`
- `036-frontend/package-lock.json`
- `036-frontend/vite.config.js`
- `036-frontend/src/**/*.vue`
- `036-frontend/public/favicon.svg`
- `036-frontend/public/cover-placeholder.svg`
- `docs/checks/036-dream-donation-platform.md`
- `037-backend/pom.xml`
- `037-backend/src/main/resources/application.yml`
- `037-backend/src/main/resources/application-mysql.yml`
- `037-backend/src/main/resources/sql/schema-h2.sql`
- `037-backend/src/main/resources/sql/data-h2.sql`
- `037-backend/src/main/java/com/programming/learning/config/SecurityConfig.java`
- `037-backend/src/main/java/com/programming/learning/controller/AuthController.java`
- `037-backend/src/main/java/com/programming/learning/controller/CourseController.java`
- `037-backend/src/main/java/com/programming/learning/service/CourseService.java`
- `037-backend/src/main/java/com/programming/learning/mapper/CourseMapper.java`
- `037-backend/src/main/resources/mapper/CourseMapper.xml`
- `037-backend/src/main/java/com/programming/learning/util/WeChatUtil.java`
- `037-backend/src/test/java/com/programming/learning/ProgrammingLearningApplicationTests.java`
- `037-frontend/app.json`
- `037-frontend/sitemap.json`
- `037-frontend/pages/**/*.js`
- `037-frontend/pages/**/*.wxml`
- `037-frontend/pages/**/*.wxss`
- `docs/checks/037-programming-learning-platform.md`
- `033-backend/pom.xml`
- `033-backend/sql/init_data.sql`
- `033-backend/src/main/resources/application.yml`
- `033-backend/src/main/resources/application-mysql.yml`
- `033-backend/src/main/resources/schema-h2.sql`
- `033-backend/src/main/resources/data-h2.sql`
- `033-backend/src/main/java/com/xiaou/wedding/exception/GlobalExceptionHandler.java`
- `033-backend/src/test/java/com/xiaou/wedding/WeddingPhotoStudioApplicationTests.java`
- `033-frontend/package.json`
- `033-frontend/vite.config.ts`
- `033-frontend/src/api/*.ts`
- `033-frontend/src/pages/*.tsx`

## Session: 2026-05-01 项目巡检续推

### Findings
- 续检状态已推进到 `071` 并完成：
  - `docs/project-check-tracker.md` 已完成到 `071`
  - `progress.md` 当前状态写明“下一项目：072”
  - `docs/checks/071-bike-sharing-system.md` 已新增
- `071` 项目结构与风险已确认：
  - 后端目录：`071-backend`
  - 前端目录：`071-frontend`
  - 后端为 Spring Boot 2.7.18 + MyBatis XML + PageHelper + JWT
  - 前端为 Vue 3 + Vite + Element Plus + Pinia + Axios + ECharts
  - 原始默认配置强依赖 MySQL `bike_system`、Redis `localhost:6379` 和默认 `8080`
  - 前端原始代理仍指向 `8080`
- `071` 最终修复与验证结果：
  - 默认环境已从 MySQL/Redis 强依赖改为 H2 自举 + 本地运行态缓存，MySQL 配置保留在 `application-mysql.yml`
  - 已修复 JWT Bearer 兼容、Redis 不可用阻断登录、密码响应泄露、未登录/越权 HTTP 状态、登出失效和普通用户越权查看他人骑行订单问题
  - 已补充骑行开始/结束的车辆站点一致性、站点可用性、用户存在性校验
  - 已补充钱包充值正数校验、故障车辆存在校验和故障处理记录存在校验
  - 已修复统计趋势 SQL 使用 MySQL `DATE_SUB` 导致默认 H2 看板接口 500 的问题
  - 已修复前端代理端口和请求头 Bearer token
  - `071-backend/mvn test`：通过，`Tests run: 1, Failures: 0, Errors: 0`
  - `071-frontend/npm run build`：通过，仅保留 Vite CJS API 和 chunk 体积提示
  - 后端 `8071` 真实启动抽测通过：`anonymousStatus=401`、登录 `200`、看板 `200`、骑行结束状态 `3`、扣费 `1.5`
  - 前端 `3071` 代理登录验证通过：`proxyLoginCode=200`、`proxyRole=admin`
  - 剩余风险主要是明文密码、真实支付退款、真实二维码/定位防作弊、Redis 分布式 token 与前端主包体积仍需后续优化

### Technical Decisions
| Decision | Rationale |
|----------|-----------|
| `071` 默认按 H2 运行，MySQL 用 profile 保留 | 保证本机可测试可启动，同时不丢失原部署数据库入口 |
| `071` 默认 Redis 改为本地运行态缓存兜底 | Redis 不应阻塞毕业设计默认演示和自动化验证 |
| 骑行订单详情按“管理员/运维可查，普通用户只能查本人”处理 | 避免普通用户枚举订单 ID 查看他人骑行记录 |
| 趋势统计默认移除 MySQL 专有日期函数 | 默认 H2 验收优先保证看板接口可用 |

### Issues Encountered
| Issue | Resolution |
|-------|------------|
| `071` 已有未提交改动，疑似上一轮半成品 | 保留并接续现状，不做回退 |
| `071-backend/mvn test` 原始结果为 `No tests to run` | 新增 `BikeSystemApplicationSmokeTest` 覆盖核心链路 |
| `071` 统计趋势接口在 H2 下因 `DATE_SUB` 返回 500 | 调整 `RideOrderMapper.xml` 的趋势 SQL，移除 H2 不支持的函数 |
| `071` 普通用户可访问他人骑行订单详情 | `RideService#getById` 增加角色与归属校验 |
| `071` 前端请求头不带 `Bearer` 且代理指向 `8080` | 修正请求拦截器与 Vite 代理目标，并完成代理登录复测 |

### Resources
- `docs/project-check-tracker.md`
- `docs/checks/071-bike-sharing-system.md`
- `071-backend/pom.xml`
- `071-backend/README.md`
- `071-backend/启动说明.txt`
- `071-backend/src/main/resources/application.yml`
- `071-backend/src/main/resources/application-mysql.yml`
- `071-backend/src/main/resources/db/schema-h2.sql`
- `071-backend/src/main/resources/db/data-h2.sql`
- `071-backend/src/main/resources/mapper/RideOrderMapper.xml`
- `071-backend/src/main/java/com/bike/config/JwtInterceptor.java`
- `071-backend/src/main/java/com/bike/service/RuntimeStoreService.java`
- `071-backend/src/main/java/com/bike/service/RideService.java`
- `071-backend/src/main/java/com/bike/service/WalletService.java`
- `071-backend/src/main/java/com/bike/service/FaultService.java`
- `071-backend/src/test/java/com/bike/BikeSystemApplicationSmokeTest.java`
- `071-frontend/package.json`
- `071-frontend/package-lock.json`
- `071-frontend/vite.config.js`
- `071-frontend/src/api/request.js`

## Session: 2026-05-01 项目巡检续推至 072

### Findings
- `072` 项目已完成巡检、修复和复测：
  - `docs/project-check-tracker.md` 已完成到 `072`
  - `docs/checks/072-harbin-tourism-system.md` 已新增
  - `progress.md` 当前状态写明“下一项目：073”
- `072` 项目结构与风险已确认：
  - 后端目录：`072-backend`
  - 前端目录：`072-frontend`
  - 后端为 Spring Boot 2.7.18 + MyBatis-Plus + JWT
  - 前端为 Vue 3 + Vite + Element Plus + Pinia + Axios + ECharts
  - 原始默认配置强依赖 MySQL `harbin_tourism`、Redis `localhost:6379` 和默认 `8080`
  - 前端原始端口为 `5173`，代理仍指向 `8080`
  - 前端路由原始引用 `views/Spot/*`、`views/Admin/*` 等不存在目录，构建会失败
  - 前端多个页面引用的 API 导出名不存在，如 `getTicketList`、`getMyFavorites`、`updatePassword`、`rechargeWallet`
- `072` 最终修复与验证结果：
  - 默认环境已从 MySQL/Redis 强依赖改为 H2 自举，MySQL 配置保留在 `application-mysql.yml`
  - 已修复 JDK 17、MySQL 驱动旧坐标、H2 schema/data、MyBatis-Plus 分页方言配置
  - 已修复 JWT Bearer 兼容、密码响应泄露、未登录/越权 HTTP 状态、登出失效和后台服务端权限缺失
  - 已补充订单详情/退款、游记编辑/删除、评价删除等归属校验
  - 已补充用户资料更新字段限制、充值正数、购票参数、票种价格、游记内容、评价评分和订单状态校验
  - 已补充票务列表接口并修复前端购票参数
  - 已修复前端路由、端口、代理、Bearer token 和 API 兼容导出
  - `072-backend/mvn test`：通过，`Tests run: 1, Failures: 0, Errors: 0`
  - `072-frontend/npm run build`：通过，仅保留 chunk 体积提示
  - 后端 `8072` 真实启动抽测通过：匿名用户信息 `401`、三账号登录 `200`、公开列表可访问、普通用户后台 `403`、管理员后台 `200`、购票 `200`、他人订单 `403`、退款 `200`、登出后旧 token `401`
  - 前端 `3072` 代理登录验证通过：HTTP `200`、业务码 `200`、角色 `admin`、token 存在
  - 剩余风险主要是明文密码、真实支付退款、Redis 分布式 token、完整评论模型、图片/富文本上传和前端后台深度管理能力仍需后续优化

### Technical Decisions
| Decision | Rationale |
|----------|-----------|
| `072` 默认按 H2 运行，MySQL 用 profile 保留 | 保证本机可测试可启动，同时不丢失原部署数据库入口 |
| 使用本地运行态 token 失效表实现默认登出失效 | 默认环境不应依赖 Redis 才能验证登录闭环 |
| 后台接口全部做服务端 `admin` 校验 | 前端菜单隐藏不能替代后端授权 |
| 订单/游记/评价按“本人或管理员”校验 | 避免用户枚举 ID 访问或修改他人资源 |
| 前端 API 层补兼容导出而不重写页面 | 尽量保留原页面结构，低风险修复构建和联调链路 |

### Issues Encountered
| Issue | Resolution |
|-------|------------|
| `072-backend/mvn test` 原始结果为 `No tests to run` | 新增 `TourismApplicationSmokeTest` 覆盖核心链路 |
| 默认后端强依赖 MySQL/Redis，且 SQL 含 `DROP DATABASE` | 默认改为 H2 自举，MySQL profile 保留，避免破坏本机库 |
| 登录响应包含用户密码字段 | `User.password` 改为 Jackson `WRITE_ONLY` |
| 普通用户可访问后台统计/管理类接口 | 控制器增加 `AuthUtils.requireAdmin` 服务端校验 |
| 普通用户可按 ID 访问他人订单 | 订单详情和退款增加归属校验 |
| 前端构建缺依赖且路由导入不存在目录 | 执行 `npm install` 生成 lockfile，修正路由到实际页面 |
| 前端页面引用旧 API 函数名 | 在 `src/api/index.js` 补齐兼容导出并映射到真实后端接口 |

### Resources
- `docs/project-check-tracker.md`
- `docs/checks/072-harbin-tourism-system.md`
- `072-backend/pom.xml`
- `072-backend/README.md`
- `072-backend/启动说明.txt`
- `072-backend/src/main/resources/application.yml`
- `072-backend/src/main/resources/application-mysql.yml`
- `072-backend/src/main/resources/db/schema-h2.sql`
- `072-backend/src/main/resources/db/data-h2.sql`
- `072-backend/src/main/java/com/harbin/tourism/common/GlobalExceptionHandler.java`
- `072-backend/src/main/java/com/harbin/tourism/config/JwtInterceptor.java`
- `072-backend/src/main/java/com/harbin/tourism/service/RuntimeStoreService.java`
- `072-backend/src/main/java/com/harbin/tourism/utils/AuthUtils.java`
- `072-backend/src/main/java/com/harbin/tourism/service/TicketService.java`
- `072-backend/src/main/java/com/harbin/tourism/service/UserService.java`
- `072-backend/src/main/java/com/harbin/tourism/service/NoteService.java`
- `072-backend/src/main/java/com/harbin/tourism/service/ReviewService.java`
- `072-backend/src/test/java/com/harbin/tourism/TourismApplicationSmokeTest.java`
- `072-frontend/package-lock.json`
- `072-frontend/vite.config.js`
- `072-frontend/src/api/request.js`
- `072-frontend/src/api/index.js`
- `072-frontend/src/router/index.js`

## Session: 2026-05-01 项目巡检续推至 073

### Findings
- `073` 项目已完成巡检、修复和复测：
  - `docs/project-check-tracker.md` 已完成到 `073`
  - `docs/checks/073-hrm-system.md` 已新增
  - `progress.md` 当前状态写明“下一项目：074”
- `073` 项目结构与风险已确认：
  - 后端目录：`073-backend`
  - 前端目录：`073-frontend`
  - 后端为 Spring Boot 2.7.18 + MyBatis XML + PageHelper + JWT
  - 前端为 Vue 3 + Vite + Element Plus + Pinia + Axios + ECharts
  - 原始默认配置强依赖 MySQL `hrm_system`、Redis `localhost:6379` 和默认 `8080`
  - 原始后端 `mvn test` 编译失败，根因是 `UserService` 静态调用非静态 `JwtUtils.generateToken`
  - 前端原始代理仍指向 `8080`
- `073` 最终修复与验证结果：
  - 默认环境已从 MySQL/Redis 强依赖改为 H2 自举，MySQL 配置保留在 `application-mysql.yml`
  - 已修复 MySQL 驱动旧坐标、H2 schema/data、PageHelper 默认方言和 Spring Boot Test 依赖
  - 已修复 JWT Bearer 兼容、登录放行路径、登录响应密码泄露、未登录/越权 HTTP 状态和登出失效
  - 已补充用户、员工、部门、职位、招聘、简历、培训、公告管理接口的服务端 admin/hr 校验
  - 已补充考勤、请假、薪资、合同接口的员工本人归属校验，并防止请假请求体篡改 `employeeId`
  - 已修复前端代理端口和请求头 Bearer token
  - `073-backend/mvn test`：通过，`Tests run: 1, Failures: 0, Errors: 0`
  - `073-frontend/npm run build`：通过，仅保留 chunk 体积提示
  - 后端 `8073` 真实启动抽测通过：匿名用户信息 `401`、三账号登录 `200`、员工管理接口越权 `403`、HR 管理接口 `200`、员工考勤 `200`、请假归属强制本人、合同越权 `403`、登出后旧 token `401`
  - 前端 `3073` 页面响应和代理登录验证通过：HTTP `200`、业务码 `200`、角色 `admin`、token 存在
  - 剩余风险主要是 MD5 密码、Redis 分布式 token、复杂审批/通知/导入导出、员工端个性化首页和前端主包体积仍需后续优化

### Technical Decisions
| Decision | Rationale |
|----------|-----------|
| `073` 默认按 H2 运行，MySQL 用 profile 保留 | 保证本机可测试可启动，同时不丢失原部署数据库入口 |
| 使用本地运行态 token 失效表实现默认登出失效 | 默认环境不应依赖 Redis 才能验证登录闭环 |
| 管理型接口统一按 admin/hr 校验 | 前端菜单隐藏不能替代服务端授权 |
| 员工数据接口按本人 `employeeId` 约束 | 避免员工枚举或操作他人的考勤、请假、薪资、合同信息 |

### Issues Encountered
| Issue | Resolution |
|-------|------------|
| `073-backend/mvn test` 原始编译失败，提示静态上下文引用非静态 `generateToken` | 改为注入 `JwtUtils` 实例并调用实例方法 |
| 默认后端强依赖 MySQL/Redis，且 SQL 含 `DROP DATABASE` | 默认改为 H2 自举，MySQL profile 保留，避免破坏本机库 |
| 登录放行路径误写为 `/api/auth/login` | 改为真实的 `/api/user/login` |
| 登录响应包含用户密码字段 | `User.password` 改为 Jackson `WRITE_ONLY` |
| 普通员工可访问管理型接口或他人数据 | 控制器增加 admin/hr 校验和本人归属校验 |
| 前端构建缺依赖导致 `vite` 不可用 | 执行 `npm install` 生成 lockfile，构建通过 |
| 前端请求头不带 `Bearer` 且代理指向 `8080` | 修正请求拦截器与 Vite 代理目标，并完成代理登录复测 |
| 浏览器 DevTools MCP 被已有 Chrome profile 锁定 | 使用真实 HTTP 页面响应、生产构建和代理登录完成前端验收 |

### Resources
- `docs/project-check-tracker.md`
- `docs/checks/073-hrm-system.md`
- `073-backend/pom.xml`
- `073-backend/README.md`
- `073-backend/启动说明.txt`
- `073-backend/src/main/resources/application.yml`
- `073-backend/src/main/resources/application-mysql.yml`
- `073-backend/src/main/resources/db/schema-h2.sql`
- `073-backend/src/main/resources/db/data-h2.sql`
- `073-backend/src/main/java/com/hrm/common/GlobalExceptionHandler.java`
- `073-backend/src/main/java/com/hrm/config/JwtInterceptor.java`
- `073-backend/src/main/java/com/hrm/config/WebMvcConfig.java`
- `073-backend/src/main/java/com/hrm/service/RuntimeStoreService.java`
- `073-backend/src/main/java/com/hrm/utils/AuthUtils.java`
- `073-backend/src/main/java/com/hrm/controller/*.java`
- `073-backend/src/test/java/com/hrm/HrmApplicationSmokeTest.java`
- `073-frontend/package-lock.json`
- `073-frontend/vite.config.js`
- `073-frontend/src/api/request.js`

## Session: 2026-05-02 项目巡检续推至 074

### Findings
- `074` 项目已完成巡检、修复和复测：
  - `docs/project-check-tracker.md` 已完成到 `074`
  - `docs/checks/074-craft-sales-system.md` 已新增
  - `progress.md` 当前状态写明“下一项目：075”
- `074` 项目结构与风险已确认：
  - 后端目录：`074-backend`
  - 前端目录：`074-frontend`
  - 后端为 Spring Boot 2.7.18 + MyBatis-Plus + JWT
  - 前端为 Vue 3 + Vite + Element Plus + Pinia + Axios + ECharts
  - 原始默认配置强依赖 MySQL `craft_sales`、Redis `localhost:6379` 和默认 `8080`
  - 原始 SQL 脚本包含 `DROP DATABASE IF EXISTS craft_sales`
  - 原始后端 `mvn test` 可构建但 `No tests to run`
  - 原始前端因未安装依赖无法执行 `vite build`
- `074` 最终修复与验证结果：
  - 默认环境已从 MySQL/Redis 强依赖改为 H2 自举，MySQL 配置保留在 `application-mysql.yml`
  - 已修复 JDK 17、MySQL 驱动旧坐标、H2 schema/data、MyBatis-Plus 分页方言配置
  - 已修复 JWT Bearer 兼容、登录响应 password 字段泄露、未登录/越权 HTTP 状态和登出失效
  - 已补充后台管理接口服务端管理员校验
  - 已补充商品、订单、评价、申诉等交易资源的本人归属校验
  - 已修复前端端口、代理和 Bearer token
  - `074-backend/mvn test`：通过，`Tests run: 1, Failures: 0, Errors: 0`
  - `074-frontend/npm install`：通过
  - `074-frontend/npm run build`：通过，仅保留 Vite CJS API 和 chunk 体积提示
  - 后端 `8074` 真实启动抽测通过：匿名用户信息 `401`、四账号登录 `200`、买家后台 `403`、管理员后台 `200`、商品/分类/公告 `200`、下单支付 `200`、错误卖家发货 `403`、正确卖家发货 `200`、评价收藏申诉 `200`、登出后旧 token `401`
  - 前端 `3074` 页面响应和代理登录验证通过：HTTP `200`、业务码 `200`、角色 `ADMIN`、token 存在且无 `password` 字段
  - 剩余风险主要是明文密码、真实支付/退款/物流、Redis 分布式 token、对象存储、库存并发和前端主包体积仍需后续优化

### Technical Decisions
| Decision | Rationale |
|----------|-----------|
| `074` 默认按 H2 运行，MySQL 用 profile 保留 | 保证本机可测试可启动，同时不丢失原部署数据库入口 |
| 使用本地运行态 token 失效表实现默认登出失效 | 默认环境不应依赖 Redis 才能验证登录闭环 |
| 后台接口统一按 `ADMIN` 校验 | 前端菜单隐藏不能替代服务端授权 |
| 商品和订单按卖家/买家归属校验 | 避免用户枚举 ID 操作他人商品、订单、评价或申诉 |
| `User.password` 使用 Jackson `WRITE_ONLY` | 保持注册/改密可接收密码，同时响应不输出 password 字段 |

### Issues Encountered
| Issue | Resolution |
|-------|------------|
| `074-backend/mvn test` 原始结果为 `No tests to run` | 新增 `CraftSalesApplicationSmokeTest` 覆盖核心链路 |
| 默认后端强依赖 MySQL/Redis，且 SQL 含 `DROP DATABASE` | 默认改为 H2 自举，MySQL profile 保留，避免破坏本机库 |
| 登录响应仍包含 `password: null` 字段 | `User.password` 改为 Jackson `WRITE_ONLY`，复测确认字段不输出 |
| 普通用户可访问后台看板/管理类接口 | 控制器增加管理员服务端校验 |
| 非订单所属卖家可尝试发货 | 订单发货增加 sellerId 归属校验，真实 HTTP 返回 `403` |
| 前端构建缺依赖导致 `vite` 不可用 | 执行 `npm install` 生成 lockfile，构建通过 |
| 前端请求头不带 `Bearer` 且代理指向 `8080` | 修正请求拦截器与 Vite 代理目标，并完成代理登录复测 |
| PowerShell 首次前端验证使用 `$home` 变量失败 | 改用 `$homeResp` 后重跑，页面响应与代理登录通过 |

### Resources
- `docs/project-check-tracker.md`
- `docs/checks/074-craft-sales-system.md`
- `074-backend/pom.xml`
- `074-backend/README.md`
- `074-backend/启动说明.txt`
- `074-backend/src/main/resources/application.yml`
- `074-backend/src/main/resources/application-mysql.yml`
- `074-backend/src/main/resources/db/schema-h2.sql`
- `074-backend/src/main/resources/db/data-h2.sql`
- `074-backend/src/main/java/com/craft/common/BusinessException.java`
- `074-backend/src/main/java/com/craft/common/GlobalExceptionHandler.java`
- `074-backend/src/main/java/com/craft/config/JwtInterceptor.java`
- `074-backend/src/main/java/com/craft/config/MybatisPlusConfig.java`
- `074-backend/src/main/java/com/craft/service/RuntimeStoreService.java`
- `074-backend/src/main/java/com/craft/service/CraftItemService.java`
- `074-backend/src/main/java/com/craft/service/CraftOrderService.java`
- `074-backend/src/main/java/com/craft/service/ReviewService.java`
- `074-backend/src/main/java/com/craft/service/ComplaintService.java`
- `074-backend/src/main/java/com/craft/entity/User.java`
- `074-backend/src/test/java/com/craft/CraftSalesApplicationSmokeTest.java`
- `074-frontend/package-lock.json`
- `074-frontend/vite.config.js`
- `074-frontend/src/api/request.js`

## Session: 2026-05-02 项目巡检续推至 075

### Findings
- `075` 项目已完成巡检、修复和复测：
  - `docs/project-check-tracker.md` 已完成到 `075`
  - `docs/checks/075-auto-repair-appointment-system.md` 已新增
  - `progress.md` 当前状态写明“下一项目：076”
- `075` 项目结构与风险已确认：
  - 后端目录：`075-backend`
  - 前端目录：`075-frontend`
  - 后端为 Spring Boot 2.7.18 + MyBatis-Plus + JWT
  - 前端为 Vue 3 + Vite + Element Plus + Pinia + Axios + ECharts
  - 原始默认配置强依赖 MySQL `auto_repair_service`、Redis `localhost:6379` 和默认 `8080`
  - 原始 SQL 脚本包含 `DROP DATABASE IF EXISTS auto_repair_service`
  - 原始后端 `mvn test` 可构建但 `No tests to run`
  - 原始前端因未安装依赖无法执行 `vite build`
- `075` 最终修复与验证结果：
  - 默认环境已从 MySQL/Redis 强依赖改为 H2 自举，MySQL 配置保留在 `application-mysql.yml`
  - 已修复 JDK 17、MySQL 驱动旧坐标、H2 schema/data、MyBatis-Plus 分页方言配置
  - 已修复 JWT Bearer 兼容、登录响应 password 字段泄露、未登录/越权 HTTP 状态和登出失效
  - 已补充后台管理接口服务端管理员校验
  - 已补充维修项目、预约单、评价、申诉等资源的本人归属校验
  - 已修复前端端口、代理和 Bearer token
  - `075-backend/mvn test`：通过，`Tests run: 1, Failures: 0, Errors: 0`
  - `075-frontend/npm install`：通过
  - `075-frontend/npm run build`：通过，仅保留 Vite CJS API 和 chunk 体积提示
  - 后端 `8075` 真实启动抽测通过：匿名用户信息 `401`、四账号登录 `200`、车主后台 `403`、管理员后台 `200`、维修项目/分类/公告 `200`、预约支付 `200`、错误服务方确认 `403`、正确服务方确认 `200`、评价收藏申诉 `200`、登出后旧 token `401`
  - 前端 `3075` 页面响应和代理登录验证通过：HTTP `200`、业务码 `200`、角色 `ADMIN`、token 存在且无 `password` 字段
  - 剩余风险主要是明文密码、真实支付/退款/门店排班、Redis 分布式 token、对象存储、预约名额并发和前端主包体积仍需后续优化

### Technical Decisions
| Decision | Rationale |
|----------|-----------|
| `075` 默认按 H2 运行，MySQL 用 profile 保留 | 保证本机可测试可启动，同时不丢失原部署数据库入口 |
| 使用本地运行态 token 失效表实现默认登出失效 | 默认环境不应依赖 Redis 才能验证登录闭环 |
| 后台接口统一按 `ADMIN` 校验 | 前端菜单隐藏不能替代服务端授权 |
| 维修项目和预约按服务方/车主归属校验 | 避免用户枚举 ID 操作他人维修项目、预约、评价或申诉 |
| `User.password` 使用 Jackson `WRITE_ONLY` | 保持注册/改密可接收密码，同时响应不输出 password 字段 |

### Issues Encountered
| Issue | Resolution |
|-------|------------|
| `075-backend/mvn test` 原始结果为 `No tests to run` | 新增 `AutoRepairAppointmentApplicationSmokeTest` 覆盖核心链路 |
| 默认后端强依赖 MySQL/Redis，且 SQL 含 `DROP DATABASE` | 默认改为 H2 自举，MySQL profile 保留，避免破坏本机库 |
| 登录响应会包含密码字段 | `User.password` 改为 Jackson `WRITE_ONLY`，复测确认字段不输出 |
| 普通用户可访问后台看板/管理类接口 | 控制器增加管理员服务端校验 |
| 非预约所属服务方可尝试确认预约 | 预约确认增加 sellerId 归属校验，真实 HTTP 返回 `403` |
| 前端构建缺依赖导致 `vite` 不可用 | 执行 `npm install` 生成 lockfile，构建通过 |
| 前端请求头不带 `Bearer` 且代理指向 `8080` | 修正请求拦截器与 Vite 代理目标，并完成代理登录复测 |

### Resources
- `docs/project-check-tracker.md`
- `docs/checks/075-auto-repair-appointment-system.md`
- `075-backend/pom.xml`
- `075-backend/README.md`
- `075-backend/启动说明.txt`
- `075-backend/src/main/resources/application.yml`
- `075-backend/src/main/resources/application-mysql.yml`
- `075-backend/src/main/resources/db/schema-h2.sql`
- `075-backend/src/main/resources/db/data-h2.sql`
- `075-backend/src/main/java/com/autorepair/common/BusinessException.java`
- `075-backend/src/main/java/com/autorepair/common/GlobalExceptionHandler.java`
- `075-backend/src/main/java/com/autorepair/config/JwtInterceptor.java`
- `075-backend/src/main/java/com/autorepair/config/MybatisPlusConfig.java`
- `075-backend/src/main/java/com/autorepair/service/RuntimeStoreService.java`
- `075-backend/src/main/java/com/autorepair/service/RepairServiceService.java`
- `075-backend/src/main/java/com/autorepair/service/RepairAppointmentService.java`
- `075-backend/src/main/java/com/autorepair/service/ReviewService.java`
- `075-backend/src/main/java/com/autorepair/service/ComplaintService.java`
- `075-backend/src/main/java/com/autorepair/entity/User.java`
- `075-backend/src/test/java/com/autorepair/AutoRepairAppointmentApplicationSmokeTest.java`
- `075-frontend/package-lock.json`
- `075-frontend/vite.config.js`
- `075-frontend/src/api/request.js`

## Session: 2026-05-02 项目巡检续推至 076

### Findings
- `076` 项目已完成巡检、修复和复测：
  - `docs/project-check-tracker.md` 已完成到 `076`
  - `docs/checks/076-enterprise-info-management-system.md` 已新增
  - `progress.md` 当前状态写明“下一项目：077”
- `076` 项目结构与风险已确认：
  - 后端目录：`076-backend`
  - 前端目录：`076-frontend`
  - 后端为 Spring Boot 2.7.18 + MyBatis-Plus + JWT
  - 前端为 Vue 3 + Vite + Element Plus + Pinia + Axios + ECharts
  - 原始默认配置强依赖 MySQL `enterprise_info_system`、Redis 和默认 `8080`
  - 原始 SQL 脚本包含 `DROP DATABASE IF EXISTS enterprise_info_system`
  - 原始后端 `mvn test` 可构建但 `No tests to run`
  - 原始前端因未安装依赖无法执行 `vite build`
- `076` 最终修复与验证结果：
  - 默认环境已从 MySQL/Redis 强依赖改为 H2 自举，MySQL 配置保留在 `application-mysql.yml`
  - 已修复 JDK 17、MySQL 驱动旧坐标、H2 schema/data、MyBatis-Plus 分页方言配置
  - 已修复 JWT Bearer 兼容、登录响应 password 字段泄露、未登录/越权 HTTP 状态和登出失效
  - 已补充后台管理接口服务端管理员校验
  - 已补充企业信息、处理单、评价、申诉等资源的本人归属校验
  - 已修复前端端口、代理和 Bearer token
  - `076-backend/mvn test`：通过，`Tests run: 1, Failures: 0, Errors: 0`
  - `076-frontend/npm install`：通过
  - `076-frontend/npm run build`：通过，仅保留 Vite CJS API 和 chunk 体积提示
  - 后端 `8076` 真实启动抽测通过：匿名用户信息 `401`、四账号登录 `200`、企业用户后台 `403`、管理员后台 `200`、企业信息/分类/公告 `200`、处理单支付 `200`、错误服务方确认 `403`、正确服务方确认 `200`、评价收藏申诉 `200`、登出后旧 token `401`
  - 前端 `3076` 页面响应和代理登录验证通过：HTTP `200`、业务码 `200`、角色 `ADMIN`、token 存在且无 `password` 字段
  - 剩余风险主要是明文密码、真实支付/退款/服务排班、Redis 分布式 token、对象存储、处理容量并发和前端主包体积仍需后续优化

### Technical Decisions
| Decision | Rationale |
|----------|-----------|
| `076` 默认按 H2 运行，MySQL 用 profile 保留 | 保证本机可测试可启动，同时不丢失原部署数据库入口 |
| 使用本地运行态 token 失效表实现默认登出失效 | 默认环境不应依赖 Redis 才能验证登录闭环 |
| 后台接口统一按 `ADMIN` 校验 | 前端菜单隐藏不能替代服务端授权 |
| 企业信息和处理单按服务方/企业用户归属校验 | 避免用户枚举 ID 操作他人企业信息、处理单、评价或申诉 |
| `User.password` 使用 Jackson `WRITE_ONLY` | 保持注册/改密可接收密码，同时响应不输出 password 字段 |

### Issues Encountered
| Issue | Resolution |
|-------|------------|
| `076-backend/mvn test` 原始结果为 `No tests to run` | 新增 `EnterpriseInfoManagementApplicationSmokeTest` 覆盖核心链路 |
| 默认后端强依赖 MySQL/Redis，且 SQL 含 `DROP DATABASE` | 默认改为 H2 自举，MySQL profile 保留，避免破坏本机库 |
| 登录响应会包含密码字段 | `User.password` 改为 Jackson `WRITE_ONLY`，复测确认字段不输出 |
| 普通用户可访问后台看板/管理类接口 | 控制器增加管理员服务端校验 |
| 非处理单所属服务方可尝试确认处理 | 处理单确认增加 sellerId 归属校验，真实 HTTP 返回 `403` |
| 前端构建缺依赖导致 `vite` 不可用 | 执行 `npm install` 生成 lockfile，构建通过 |
| 前端请求头不带 `Bearer` 且代理指向 `8080` | 修正请求拦截器与 Vite 代理目标，并完成代理登录复测 |
| PowerShell 前端验证脚本误用只读 `$HOME` 变量 | 读取报错并改变量名，重跑页面响应和代理登录验证通过 |

### Resources
- `docs/project-check-tracker.md`
- `docs/checks/076-enterprise-info-management-system.md`
- `076-backend/pom.xml`
- `076-backend/README.md`
- `076-backend/启动说明.txt`
- `076-backend/src/main/resources/application.yml`
- `076-backend/src/main/resources/application-mysql.yml`
- `076-backend/src/main/resources/db/schema-h2.sql`
- `076-backend/src/main/resources/db/data-h2.sql`
- `076-backend/src/main/java/com/enterprise/common/BusinessException.java`
- `076-backend/src/main/java/com/enterprise/common/GlobalExceptionHandler.java`
- `076-backend/src/main/java/com/enterprise/config/JwtInterceptor.java`
- `076-backend/src/main/java/com/enterprise/config/MybatisPlusConfig.java`
- `076-backend/src/main/java/com/enterprise/service/RuntimeStoreService.java`
- `076-backend/src/main/java/com/enterprise/service/RepairServiceService.java`
- `076-backend/src/main/java/com/enterprise/service/RepairAppointmentService.java`
- `076-backend/src/main/java/com/enterprise/service/ReviewService.java`
- `076-backend/src/main/java/com/enterprise/service/ComplaintService.java`
- `076-backend/src/main/java/com/enterprise/entity/User.java`
- `076-backend/src/test/java/com/enterprise/EnterpriseInfoManagementApplicationSmokeTest.java`
- `076-frontend/package-lock.json`
- `076-frontend/vite.config.js`
- `076-frontend/src/api/request.js`

## Session: 2026-05-02 项目巡检续推至 077

### Findings
- `077` 项目已完成巡检、修复和复测：
  - `docs/project-check-tracker.md` 已完成到 `077`
  - `docs/checks/077-mes-execution-system.md` 已新增
  - `progress.md` 当前状态写明“下一项目：078”
- `077` 项目结构与风险已确认：
  - 后端目录：`077-backend`
  - 前端目录：`077-frontend`
  - 后端为 Spring Boot 2.7.18 + MyBatis-Plus + JWT
  - 前端为 Vue 3 + Vite + Element Plus + Pinia + Axios + ECharts
  - 原始默认配置强依赖 MySQL `mes_execution_system`、Redis 和默认 `8080`
  - 原始 SQL 脚本包含 `DROP DATABASE IF EXISTS mes_execution_system`
  - 原始后端 `mvn test` 可构建但 `No tests to run`
  - 原始前端因未安装依赖无法执行 `vite build`
- `077` 最终修复与验证结果：
  - 默认环境已从 MySQL/Redis 强依赖改为 H2 自举，MySQL 配置保留在 `application-mysql.yml`
  - 已修复 JDK 17、MySQL 驱动旧坐标、H2 schema/data、MyBatis-Plus 分页方言配置
  - 已修复 JWT Bearer 兼容、登录响应 password 字段泄露、未登录/越权 HTTP 状态和登出失效
  - 已补充后台管理接口服务端管理员校验
  - 已补充生产任务、执行工单、评价、申诉等资源的本人归属校验
  - 已修复前端端口、代理和 Bearer token
  - `077-backend/mvn test`：通过，`Tests run: 1, Failures: 0, Errors: 0`
  - `077-frontend/npm install`：通过
  - `077-frontend/npm run build`：通过，仅保留 Vite CJS API 和 chunk 体积提示
  - 后端 `8077` 真实启动抽测通过：匿名用户信息 `401`、四账号登录 `200`、计划员后台 `403`、管理员后台 `200`、生产任务/分类/公告 `200`、执行工单支付 `200`、错误生产主管确认 `403`、正确生产主管确认 `200`、评价收藏申诉 `200`、登出后旧 token `401`
  - 前端 `3077` 页面响应和代理登录验证通过：HTTP `200`、业务码 `200`、角色 `ADMIN`、token 存在且无 `password` 字段
  - 剩余风险主要是明文密码、真实支付/退款/排产设备联动、Redis 分布式 token、对象存储、产能并发和前端主包体积仍需后续优化

### Technical Decisions
| Decision | Rationale |
|----------|-----------|
| `077` 默认按 H2 运行，MySQL 用 profile 保留 | 保证本机可测试可启动，同时不丢失原部署数据库入口 |
| 使用本地运行态 token 失效表实现默认登出失效 | 默认环境不应依赖 Redis 才能验证登录闭环 |
| 后台接口统一按 `ADMIN` 校验 | 前端菜单隐藏不能替代服务端授权 |
| 生产任务和执行工单按生产主管/计划员归属校验 | 避免用户枚举 ID 操作他人生产任务、工单、评价或申诉 |
| `User.password` 使用 Jackson `WRITE_ONLY` | 保持注册/改密可接收密码，同时响应不输出 password 字段 |

### Issues Encountered
| Issue | Resolution |
|-------|------------|
| `077-backend/mvn test` 原始结果为 `No tests to run` | 新增 `MesExecutionApplicationSmokeTest` 覆盖核心链路 |
| 默认后端强依赖 MySQL/Redis，且 SQL 含 `DROP DATABASE` | 默认改为 H2 自举，MySQL profile 保留，避免破坏本机库 |
| 登录响应会包含密码字段 | `User.password` 改为 Jackson `WRITE_ONLY`，复测确认字段不输出 |
| 普通用户可访问后台看板/管理类接口 | 控制器增加管理员服务端校验 |
| 非工单所属生产主管可尝试确认工单 | 执行工单确认增加 sellerId 归属校验，真实 HTTP 返回 `403` |
| 前端构建缺依赖导致 `vite` 不可用 | 执行 `npm install` 生成 lockfile，构建通过 |
| 前端请求头不带 `Bearer` 且代理指向 `8080` | 修正请求拦截器与 Vite 代理目标，并完成代理登录复测 |

### Resources
- `docs/project-check-tracker.md`
- `docs/checks/077-mes-execution-system.md`
- `077-backend/pom.xml`
- `077-backend/README.md`
- `077-backend/启动说明.txt`
- `077-backend/src/main/resources/application.yml`
- `077-backend/src/main/resources/application-mysql.yml`
- `077-backend/src/main/resources/db/schema-h2.sql`
- `077-backend/src/main/resources/db/data-h2.sql`
- `077-backend/src/main/java/com/mes/common/BusinessException.java`
- `077-backend/src/main/java/com/mes/common/GlobalExceptionHandler.java`
- `077-backend/src/main/java/com/mes/config/JwtInterceptor.java`
- `077-backend/src/main/java/com/mes/config/MybatisPlusConfig.java`
- `077-backend/src/main/java/com/mes/service/RuntimeStoreService.java`
- `077-backend/src/main/java/com/mes/service/RepairServiceService.java`
- `077-backend/src/main/java/com/mes/service/RepairAppointmentService.java`
- `077-backend/src/main/java/com/mes/service/ReviewService.java`
- `077-backend/src/main/java/com/mes/service/ComplaintService.java`
- `077-backend/src/main/java/com/mes/entity/User.java`
- `077-backend/src/test/java/com/mes/MesExecutionApplicationSmokeTest.java`
- `077-frontend/package-lock.json`
- `077-frontend/vite.config.js`
- `077-frontend/src/api/request.js`

## Session: 2026-05-02 项目巡检续推至 078

### Findings
- `078` 项目已完成巡检、修复和复测：
  - `docs/project-check-tracker.md` 已完成到 `078`
  - `docs/checks/078-groupbuy-system.md` 已新增
  - `progress.md` 当前状态写明“下一项目：079”
- `078` 项目结构与风险已确认：
  - 后端目录：`078-backend`
  - 前端目录：`078-frontend`
  - 后端为 Spring Boot 2.7.18 + MyBatis-Plus + JWT
  - 前端为 Vue 3 + Vite + Element Plus + Pinia + Axios + ECharts
  - 原始默认配置强依赖 MySQL `group_buy`、Redis 和默认 `8080`
  - 原始 SQL 脚本包含 `DROP DATABASE IF EXISTS group_buy`
  - 原始后端 `mvn test` 可构建但 `No tests to run`
  - 原始前端因未安装依赖无法执行 `vite build`
- `078` 最终修复与验证结果：
  - 默认环境已从 MySQL/Redis 强依赖改为 H2 自举，MySQL 配置保留在 `application-mysql.yml`
  - 已修复 JDK 17、MySQL 驱动旧坐标、H2 schema/data、MyBatis-Plus 分页方言配置
  - 已修复 JWT Bearer 兼容、登录响应 password 字段泄露、未登录/越权 HTTP 状态和登出失效
  - 已补充后台管理接口服务端管理员校验
  - 已补充商品、团购活动、地址、购物车、订单、拼团订单、评价等资源的本人/商家归属校验
  - 已修复前端端口、代理、Bearer token、首页公开团购调用和多个 API 导出断链
  - `078-backend/mvn test`：通过，`Tests run: 1, Failures: 0, Errors: 0`
  - `078-frontend/npm install`：通过
  - `078-frontend/npm run build`：通过，仅保留 Vite CJS API 和 chunk 体积提示
  - 后端 `8078` 真实启动抽测通过：匿名用户信息 `401`、五账号登录 `200`、普通用户统计 `403`、管理员统计 `200`、公开分类/团购/商品/公告 `200`、购物车下单 `200`、订单支付/发货/收货归属校验、评价、管理员评价分页和登出后旧 token `401`
  - 前端 `3078` 页面响应和代理登录验证通过：HTTP `200`、业务码 `200`、角色 `0`、token 存在且无 `password` 字段
  - 剩余风险主要是明文密码、真实支付/退款、成团并发、库存锁定、Redis 分布式 token、对象存储和前端主包体积仍需后续优化

### Technical Decisions
| Decision | Rationale |
|----------|-----------|
| `078` 默认按 H2 运行，MySQL 用 profile 保留 | 保证本机可测试可启动，同时不丢失原部署数据库入口 |
| 使用本地运行态 token 失效表实现默认登出失效 | 默认环境不应依赖 Redis 才能验证登录闭环 |
| 后台接口统一按管理员校验 | 前端菜单隐藏不能替代服务端授权 |
| 团购交易链路按普通用户/商家归属约束 | 避免用户枚举 ID 操作他人的地址、购物车、订单或商品活动 |
| 首页活动列表改用公开接口 `/api/activity/home` | 首页应支持游客访问，避免调用受保护管理分页接口 |

### Issues Encountered
| Issue | Resolution |
|-------|------------|
| `078-backend/mvn test` 原始结果为 `No tests to run` | 新增 `GroupBuyApplicationSmokeTest` 覆盖核心链路 |
| 默认后端强依赖 MySQL/Redis，且 SQL 含 `DROP DATABASE` | 默认改为 H2 自举，MySQL profile 保留，避免破坏本机库 |
| 登录响应会包含密码字段 | `User.password` 改为 Jackson `WRITE_ONLY`，复测确认字段不输出 |
| 普通用户可访问管理型接口或他人交易数据 | 增加管理员校验与用户/商家归属校验 |
| 前端构建缺依赖导致 `vite` 不可用 | 执行 `npm install` 生成 lockfile，构建通过 |
| 前端 `deleteReview`、`updateNoticeStatus`、`getNoticeList`、个人中心 API 导出缺失 | 补齐后端接口与前端 API 导出，重跑构建通过 |
| 首页调用受保护的活动分页接口 | 改为调用公开的 `getActivityHome` |

### Resources
- `docs/project-check-tracker.md`
- `docs/checks/078-groupbuy-system.md`
- `078-backend/pom.xml`
- `078-backend/README.md`
- `078-backend/启动说明.txt`
- `078-backend/src/main/resources/application.yml`
- `078-backend/src/main/resources/application-mysql.yml`
- `078-backend/src/main/resources/db/schema-h2.sql`
- `078-backend/src/main/resources/db/data-h2.sql`
- `078-backend/src/main/java/com/groupbuy/common/BusinessException.java`
- `078-backend/src/main/java/com/groupbuy/common/GlobalExceptionHandler.java`
- `078-backend/src/main/java/com/groupbuy/config/JwtInterceptor.java`
- `078-backend/src/main/java/com/groupbuy/config/MybatisPlusConfig.java`
- `078-backend/src/main/java/com/groupbuy/service/RuntimeStoreService.java`
- `078-backend/src/main/java/com/groupbuy/utils/AuthUtils.java`
- `078-backend/src/main/java/com/groupbuy/service/OrderService.java`
- `078-backend/src/main/java/com/groupbuy/service/CartService.java`
- `078-backend/src/main/java/com/groupbuy/service/AddressService.java`
- `078-backend/src/main/java/com/groupbuy/service/ProductService.java`
- `078-backend/src/main/java/com/groupbuy/service/GroupActivityService.java`
- `078-backend/src/main/java/com/groupbuy/service/GroupOrderService.java`
- `078-backend/src/main/java/com/groupbuy/service/ReviewService.java`
- `078-backend/src/main/java/com/groupbuy/service/NoticeService.java`
- `078-backend/src/main/java/com/groupbuy/entity/User.java`
- `078-backend/src/test/java/com/groupbuy/GroupBuyApplicationSmokeTest.java`
- `078-frontend/package-lock.json`
- `078-frontend/vite.config.js`
- `078-frontend/src/api/request.js`
- `078-frontend/src/api/index.js`
- `078-frontend/src/views/home/Index.vue`

## Session: 2026-05-02 项目巡检续推至 079

### Findings
- `079` 项目已完成巡检、修复和复测：
  - `docs/project-check-tracker.md` 已完成到 `079`
  - `docs/checks/079-alumni-network-system.md` 已新增
  - `progress.md` 当前状态写明“下一项目：080”
- `079` 项目结构与风险已确认：
  - 后端目录：`079-backend`
  - 前端目录：`079-frontend`
  - 后端为 Spring Boot 2.7.18 + MyBatis-Plus + JWT
  - 前端为 Vue 3 + Vite + Element Plus + Pinia + Axios + ECharts
  - 原始默认配置强依赖 MySQL `alumni_db`、Redis 和默认 `8080`
  - 原始 SQL 脚本包含 `DROP DATABASE IF EXISTS alumni_db`
  - 本机 MySQL `root / 1234` 可连接，版本为 `8.0.26`
- `079` 最终修复与验证结果：
  - 默认环境已从 MySQL/Redis 强依赖改为 H2 自举，正式 MySQL 配置保留在 `application-mysql.yml`
  - 新增 `mysql-verify` profile，使用 `alumni_079_verify` 临时库完成非破坏性 MySQL 真实 HTTP 验证
  - 已修复 JDK 17、MySQL 驱动旧坐标、H2 schema/data、H2 `YEAR` 保留字、MyBatis-Plus 分页方言配置
  - 已修复 JWT Bearer 兼容、登录响应 password 字段泄露、未登录/越权 HTTP 状态、登出失效和公开 GET 通配符误放行写操作
  - 已补充后台管理接口服务端管理员校验
  - 已补充校友资料、活动、企业、岗位、捐赠、论坛等资源的本人/组织者/管理员归属校验
  - `079-backend/mvn test`：通过，`Tests run: 1, Failures: 0, Errors: 0`
  - `079-frontend/npm run build`：通过，仅保留 Vite CJS API 和 chunk 体积提示
  - 后端 `8079` 使用 MySQL `root / 1234` 的 `mysql-verify` profile 真实启动抽测通过：匿名用户信息 `401`、四账号登录 `200`、待审核账号 `400`、普通校友统计 `403`、管理员统计 `200`、公开列表 `200`、活动报名、捐赠确认、企业岗位归属、论坛归属和登出后旧 token `401`
  - 前端 `3079` 页面响应和代理登录验证通过：HTTP `200`、业务码 `200`、角色 `admin`、token 存在且无 `password` 字段
  - 剩余风险主要是明文密码、真实支付/财务对账、Redis 分布式 token、对象存储、内容审核/通知和前端主包体积仍需后续优化

### Issues Encountered
| Issue | Resolution |
|-------|------------|
| 用户质疑为什么只用 H2，而本机 MySQL 账号为 `root / 1234` | 保留 H2 作为无破坏自动回归，同时用 JDBC 验证 MySQL 可连，并新增 `mysql-verify` profile 真实启动验证 |
| `mysql` 命令不在 PATH | 改用 JDK + MySQL Connector/J 通过 JShell 直连验证 |
| H2 初始化 `grade.year` 保留字报错 | 在 H2 JDBC URL 的 `NON_KEYWORDS` 中补充 `YEAR` |
| 公开 GET 通配符 `/api/forum/post/*` 同时误放行 DELETE | 改为拦截器内 GET 可选登录，写操作仍强制鉴权 |
| PowerShell HTTP 脚本读取 401/403 异常体失败 | 改用 `Invoke-WebRequest -SkipHttpErrorCheck` 直接获取真实状态码与响应体 |

### Resources
- `docs/project-check-tracker.md`
- `docs/checks/079-alumni-network-system.md`
- `079-backend/pom.xml`
- `079-backend/README.md`
- `079-backend/启动说明.txt`
- `079-backend/src/main/resources/application.yml`
- `079-backend/src/main/resources/application-mysql.yml`
- `079-backend/src/main/resources/application-mysql-verify.yml`
- `079-backend/src/main/resources/db/schema-h2.sql`
- `079-backend/src/main/resources/db/data-h2.sql`
- `079-backend/src/main/java/com/alumni/common/BusinessException.java`
- `079-backend/src/main/java/com/alumni/common/GlobalExceptionHandler.java`
- `079-backend/src/main/java/com/alumni/config/JwtInterceptor.java`
- `079-backend/src/main/java/com/alumni/config/WebMvcConfig.java`
- `079-backend/src/main/java/com/alumni/service/RuntimeStoreService.java`
- `079-backend/src/main/java/com/alumni/utils/AuthUtils.java`
- `079-backend/src/test/java/com/alumni/AlumniApplicationSmokeTest.java`
- `079-frontend/vite.config.js`
- `079-frontend/src/api/request.js`

## Session: 2026-05-02 项目巡检续推至 080

### Findings
- `080` 项目已完成巡检、修复和复测：
  - `docs/project-check-tracker.md` 已完成到 `080`
  - `docs/checks/080-charity-child-sponsorship-system.md` 已新增
  - `progress.md` 当前状态写明“下一项目：081”
- `080` 项目结构与风险已确认：
  - 后端目录：`080-backend`
  - 前端目录：`080-frontend`
  - 后端为 Spring Boot 2.7.14 + MyBatis-Plus + JWT
  - 前端为 Vue 3 + Vite + Element Plus + Pinia + Axios + ECharts
  - 原始默认配置强依赖 MySQL `charity_db`、Redis 和默认 `8080`
  - 原始 SQL 脚本包含 `DROP DATABASE IF EXISTS charity_db`
  - 本机 MySQL `root / 1234` 可连接，版本为 `8.0.26`
- `080` 最终修复与验证结果：
  - 默认环境已从 MySQL/Redis 强依赖改为 H2 自举，正式 MySQL 配置保留在 `application-mysql.yml`
  - 新增 `mysql-verify` profile，使用 `charity_080_verify` 临时库完成非破坏性 MySQL 真实 HTTP 验证
  - 已修复 JDK 17、MySQL 驱动旧坐标、JJWT JAXB 兼容、H2 schema/data 和 MyBatis-Plus 配置
  - 已修复 JWT Bearer 兼容、登录响应 password 字段泄露、未登录/越权 HTTP 状态和登出失效
  - 已补充管理员、捐赠人、志愿者角色边界和管理型接口服务端权限
  - 已补充儿童、申请、捐赠、资金、项目、公告、反馈、成长记录、资助关系等资源权限与基础校验
  - `080-backend/mvn test`：通过，`Tests run: 1, Failures: 0, Errors: 0`
  - `080-frontend/npm run build`：通过，仅保留 Vite chunk 体积提示
  - 后端 `8080` 使用 MySQL `root / 1234` 的 `mysql-verify` profile 真实启动抽测通过：匿名儿童列表 `401`、管理员登录 `200`、捐赠人统计 `403`、管理员统计 `200`、志愿者新增儿童 `200`、捐赠人新增捐赠 `200` 且 donorId 防伪造、捐赠人确认 `403`、管理员确认 `200`、登出后旧 token `401`
  - 前端 `3080` 页面响应和代理登录验证通过：HTTP `200`、业务码 `200`、角色 `admin`、token 存在且无 `password` 字段
  - 剩余风险主要是明文密码、真实支付/证书/财务对账、Redis 分布式 token、对象存储、内容审核/通知和前端主包体积仍需后续优化

### Issues Encountered
| Issue | Resolution |
|-------|------------|
| 用户质疑为什么只用 H2，而本机 MySQL 账号为 `root / 1234` | 明确 H2 仅作为无破坏自动回归；同时新增 `mysql-verify` profile，使用 MySQL `root / 1234` 真实启动和 HTTP 抽测 |
| 原始 SQL 含 `DROP DATABASE IF EXISTS charity_db` | 不让默认测试直接重建正式库，使用 H2 和 `charity_080_verify` 临时库隔离验证 |
| JDK 17 下 JJWT 0.9.1 缺 JAXB | 增加 `jaxb-api` 与 `jaxb-runtime` 依赖 |
| 登录响应会包含密码字段 | `User.password` 改为响应脱敏，自动化测试和代理登录均确认不输出 |
| 捐赠人可通过请求体伪造 `donorId` | 捐赠创建改为按 JWT 当前用户绑定 donor 记录 |
| 前端请求头不带 `Bearer` 且代理指向默认不可控后端 | 修正请求拦截器与 Vite 代理目标，并完成前端代理登录复测 |

### Resources
- `docs/project-check-tracker.md`
- `docs/checks/080-charity-child-sponsorship-system.md`
- `080-backend/pom.xml`
- `080-backend/README.md`
- `080-backend/启动说明.txt`
- `080-backend/src/main/resources/application.yml`
- `080-backend/src/main/resources/application-mysql.yml`
- `080-backend/src/main/resources/application-mysql-verify.yml`
- `080-backend/src/main/resources/schema-h2.sql`
- `080-backend/src/main/resources/data-h2.sql`
- `080-backend/src/main/java/com/charity/common/GlobalExceptionHandler.java`
- `080-backend/src/main/java/com/charity/config/JwtInterceptor.java`
- `080-backend/src/main/java/com/charity/config/MybatisPlusConfig.java`
- `080-backend/src/main/java/com/charity/utils/JwtUtils.java`
- `080-backend/src/test/java/com/charity/CharitySmokeTest.java`
- `080-frontend/package-lock.json`
- `080-frontend/vite.config.js`
- `080-frontend/src/api/request.js`
- `080-frontend/src/api/index.js`

## Session: 2026-05-02 项目巡检续推至 081

### Findings
- `081` 项目已完成巡检、修复和复测：
  - `docs/project-check-tracker.md` 已完成到 `081`
  - `docs/checks/081-appliance-repair-system.md` 已新增
  - `progress.md` 当前状态写明“下一项目：082”
- `081` 项目结构与风险已确认：
  - 后端目录：`081-backend`
  - 管理端目录：`081-frontend`
  - 小程序目录：`081-miniapp`
  - 后端为 Spring Boot 2.7.14 + MyBatis-Plus + JWT
  - 管理端为 Vue 3 + Vite + Element Plus + Pinia + Axios + ECharts
  - 小程序端为 uni-app + Vue 3 + Pinia
  - 原始默认配置强依赖 MySQL `repair_db`、Redis 和默认 `8080`，且密码为 `root`
  - 原始 SQL 脚本包含 `DROP DATABASE IF EXISTS repair_db`
  - 本机 MySQL `root / 1234` 可连接，版本为 `8.0.26`
- `081` 最终修复与验证结果：
  - 默认环境已从 MySQL/Redis 强依赖改为 H2 自举，正式 MySQL 配置保留在 `application-mysql.yml`
  - 新增 `mysql-verify` profile，使用 `repair_081_verify` 临时库完成非破坏性 MySQL 真实 HTTP 验证
  - 已修复 JDK 17、MySQL 驱动旧坐标、JJWT JAXB 兼容、H2 schema/data 和 MyBatis-Plus 配置
  - 已修复 JWT Bearer 兼容、登录响应 password 字段泄露、未登录/越权 HTTP 状态和登出失效
  - 已补充管理员、客户、技师角色边界和管理型接口服务端权限
  - 已补充客户工单、技师工单、工单进度、评价等资源的本人/分配技师归属校验
  - `081-backend/mvn test`：通过，`Tests run: 1, Failures: 0, Errors: 0`
  - `081-frontend/npm run build`：通过，仅保留 Vite chunk 体积提示
  - 后端 `8080` 使用 MySQL `root / 1234` 的 `mysql-verify` profile 真实启动抽测通过：匿名用户信息 `401`、公开分类 `200`、管理员登录 `200`、客户统计 `403`、管理员统计 `200`、客户用户列表 `403`、客户创建工单 `200`、他人工单详情 `403`、管理员派单 `200`、技师处理 `200`、他人工单进度 `403`、支付评价 `200`、登出后旧 token `401`
  - 管理端 `3081` 页面响应和代理登录验证通过：HTTP `200`、业务码 `200`、角色 `admin`、token 存在且无 `password` 字段
  - 剩余风险主要是明文密码、真实支付/售后财务对账、备件库存强一致扣减、Redis 分布式 token、对象存储和小程序残留未声明旧页面清理

### Issues Encountered
| Issue | Resolution |
|-------|------------|
| 原始默认配置使用 MySQL `root/root`，与用户确认的 `root / 1234` 不一致 | 正式 MySQL profile 和验证 profile 均改为 `root / 1234` |
| 原始 SQL 含 `DROP DATABASE IF EXISTS repair_db` | 默认测试不直接重建正式库，使用 H2 和 `repair_081_verify` 临时库隔离验证 |
| `081-backend/mvn test` 原始结果为 `No tests to run` | 新增 `RepairSmokeTest` 覆盖核心链路 |
| 前端原始 `npm run build` 因未安装依赖失败 | 执行 `npm install` 生成 lockfile 后构建通过 |
| 登录响应会包含密码字段 | `User.password` 改为响应脱敏，自动化测试和代理登录均确认不输出 |
| 客户可尝试伪造 `userId` 或访问他人工单/进度 | 工单创建改为按 JWT 当前用户绑定，并增加本人/技师/管理员访问校验 |
| 前端和小程序请求头不带 `Bearer` 且多处写死 `localhost:8080` | 修正请求头、Vite 代理目标和图片 URL 口径 |

### Resources
- `docs/project-check-tracker.md`
- `docs/checks/081-appliance-repair-system.md`
- `081-backend/pom.xml`
- `081-backend/README.md`
- `081-backend/启动说明.txt`
- `081-backend/src/main/resources/application.yml`
- `081-backend/src/main/resources/application-mysql.yml`
- `081-backend/src/main/resources/application-mysql-verify.yml`
- `081-backend/src/main/resources/schema-h2.sql`
- `081-backend/src/main/resources/data-h2.sql`
- `081-backend/src/main/java/com/repair/common/GlobalExceptionHandler.java`
- `081-backend/src/main/java/com/repair/config/JwtInterceptor.java`
- `081-backend/src/main/java/com/repair/config/MybatisPlusConfig.java`
- `081-backend/src/main/java/com/repair/service/RuntimeStoreService.java`
- `081-backend/src/main/java/com/repair/service/UserService.java`
- `081-backend/src/main/java/com/repair/service/RepairOrderService.java`
- `081-backend/src/main/java/com/repair/service/EvaluateService.java`
- `081-backend/src/test/java/com/repair/RepairSmokeTest.java`
- `081-frontend/package-lock.json`
- `081-frontend/vite.config.js`
- `081-frontend/src/api/request.js`
- `081-frontend/src/api/index.js`
- `081-miniapp/utils/request.js`
- `081-miniapp/api/index.js`

## Session: 2026-05-02 项目巡检续推至 082

### Findings
- `082` 项目已完成巡检、修复和复测：
  - `docs/project-check-tracker.md` 已完成到 `082`
  - `docs/checks/082-gongkao-learning-platform.md` 已新增
  - `progress.md` 当前状态写明“下一项目：083”
- `082` 项目结构与风险已确认：
  - 后端目录：`082-backend`
  - 前端目录：`082-frontend`
  - 后端为 Spring Boot 2.7.14 + MyBatis-Plus + JWT
  - 前端为 Vue 3 + Vite + Element Plus + Pinia + Axios + ECharts
  - 原始默认配置强依赖 MySQL `gongkao_db`、Redis 和默认 `8080`
  - 原始 SQL 脚本包含 `DROP DATABASE IF EXISTS gongkao_db`
  - 原始后端 `mvn test` 可编译但 `No tests to run`
  - 原始前端请求头未携带 `Bearer`，Vite 代理固定写死 `localhost:8080`
- `082` 最终修复与验证结果：
  - 默认环境已从 MySQL/Redis 强依赖改为 H2 自举，正式 MySQL 配置保留在 `application-mysql.yml`
  - 新增 `mysql-verify` profile，使用 `gongkao_082_verify` 临时库完成非破坏性 MySQL 真实 HTTP 验证
  - 已修复 JDK 17、MySQL 驱动旧坐标、JJWT JAXB 兼容、H2 schema/data 和分页配置
  - 已修复 JWT Bearer 兼容、登录响应 password 字段泄露、未登录/越权 HTTP 状态和登出失效
  - 已补充管理员、讲师、学员角色边界和后台管理权限
  - 已修复讲师课程 `teacherId` 可伪造、学员学习计划/考试记录 `userId` 可伪造与越权枚举问题
  - 已为学科、公告公开接口补充 Redis 不可用时的数据库回退逻辑
  - `082-backend/mvn test`：通过，`Tests run: 1, Failures: 0, Errors: 0`
  - `082-frontend/npm install`：通过
  - `082-frontend/npm.cmd run build`：通过，仅保留 Vite chunk 体积提示
  - 后端 `18082` 默认 H2 环境真实启动抽测通过：匿名用户信息 `401`、管理员登录 `200` 且无 password、学员统计越权 `403`、讲师新增课程 `200`、登出后旧 token `401`
  - 后端 `18083` 使用 MySQL `root / 1234` 的 `mysql-verify` profile 真实启动抽测通过：匿名用户信息 `401`、公开公告 `200`、管理员登录 `200` 且无 password、学员统计越权 `403`、学员用户列表越权 `403`、学习计划 `userId` 自动绑定为 `3`、讲师课程 `teacherId` 自动绑定为 `2`、管理员统计 `200`、登出后旧 token `401`
  - 本轮 MySQL 复测结果：`{"anon":401,"publicNotice":200,"adminLogin":200,"loginHasPassword":false,"adminRole":"admin","studentStats":403,"studentUserList":403,"planAdd":200,"planList":200,"planUserIds":"3","courseAdd":200,"courseList":200,"courseTeacherIds":"2","adminStats":200,"logout":200,"afterLogout":401}`
  - MySQL 临时库 `gongkao_082_verify` 已在验证后删除
  - 前端 `3082` 页面响应和代理登录验证通过：HTTP `200`、业务码 `200`、角色 `admin`、token 存在且无 `password` 字段
  - 剩余风险主要是明文密码、课程章节与考试前台闭环不足、Redis 分布式 token、对象存储和前端大包体积仍需后续优化

### Issues Encountered
| Issue | Resolution |
|-------|------------|
| 原始默认配置强依赖 MySQL `root/root`、Redis 和 `8080` | 默认改为 H2 自举，正式 MySQL profile 改为 `root / 1234` |
| 原始 SQL 含 `DROP DATABASE IF EXISTS gongkao_db` | 默认测试不直接重建正式库，使用 H2 和 `gongkao_082_verify` 临时库隔离验证 |
| `082-backend/mvn test` 原始结果为 `No tests to run` | 新增 `GongkaoApplicationSmokeTest` 覆盖核心链路 |
| 登录响应会包含密码字段 | `User.password` 改为响应脱敏，自动化测试和代理登录均确认不输出 |
| 学员可通过 `userId` 查询或写入他人学习计划/考试记录 | 改为按 JWT 当前用户绑定，并增加本人归属校验 |
| 讲师可伪造课程 `teacherId` | 新增课程时强制绑定当前讲师，更新/删除时校验课程归属 |
| 前端 `npm run build` 在当前 shell 下找不到 `vite` | 改用 `npm.cmd run build` 完成 Windows 环境验证 |
| 默认端口 `8082` 被外部进程占用 | 真实启动抽测改用 `18082/18083`，不阻塞验证 |

### Resources
- `docs/project-check-tracker.md`
- `docs/checks/082-gongkao-learning-platform.md`
- `082-backend/pom.xml`
- `082-backend/README.md`
- `082-backend/启动说明.txt`
- `082-backend/PRD.md`
- `082-backend/sql/init.sql`
- `082-backend/src/main/resources/application.yml`
- `082-backend/src/main/resources/application-mysql.yml`
- `082-backend/src/main/resources/application-mysql-verify.yml`
- `082-backend/src/main/resources/schema-h2.sql`
- `082-backend/src/main/resources/data-h2.sql`
- `082-backend/src/main/java/com/gongkao/common/GlobalExceptionHandler.java`
- `082-backend/src/main/java/com/gongkao/config/JwtInterceptor.java`
- `082-backend/src/main/java/com/gongkao/service/RuntimeStoreService.java`
- `082-backend/src/main/java/com/gongkao/utils/AuthUtils.java`
- `082-backend/src/main/java/com/gongkao/service/UserService.java`
- `082-backend/src/main/java/com/gongkao/service/CourseService.java`
- `082-backend/src/main/java/com/gongkao/service/StudyPlanService.java`
- `082-backend/src/main/java/com/gongkao/service/ExamRecordService.java`
- `082-backend/src/main/java/com/gongkao/service/SubjectService.java`
- `082-backend/src/main/java/com/gongkao/service/NoticeService.java`
- `082-backend/src/test/java/com/gongkao/GongkaoApplicationSmokeTest.java`
- `082-frontend/vite.config.js`
- `082-frontend/src/api/request.js`
- `082-frontend/src/api/index.js`
- `082-frontend/src/store/user.js`

## Session: 2026-05-02 项目巡检续推至 083

### Findings
- `083` 项目已完成巡检、修复和复测：
  - `docs/project-check-tracker.md` 已完成到 `083`
  - `docs/checks/083-eldercare-checkup-system.md` 已新增
  - `progress.md` 当前状态写明“下一项目：084”
- `083` 项目结构与风险已确认：
  - 后端目录：`083-backend`
  - 前端目录：`083-frontend`
  - 后端为 Spring Boot 2.7.14 + MyBatis-Plus + JWT
  - 前端为 Vue 3 + Vite + Element Plus + Pinia + Axios + ECharts
  - 原始默认配置强依赖 MySQL `eldercare_db`、Redis 和默认 `8080`
  - 原始 SQL 脚本包含 `DROP DATABASE IF EXISTS eldercare_db`
- `083` 最终修复与验证结果：
  - 默认环境已从 MySQL/Redis 强依赖改为 H2 自举，正式 MySQL 配置保留在 `application-mysql.yml`
  - 新增 `mysql-verify` profile，使用 `eldercare_083_verify` 临时库完成非破坏性 MySQL 真实 HTTP 验证
  - 已修复 JDK 17、MySQL 驱动旧坐标、JJWT JAXB 兼容、H2 schema/data 和分页配置
  - 已修复 JWT Bearer 兼容、登录响应 password 字段泄露、未登录/越权 HTTP 状态和登出失效
  - 已补充管理员、医生、护士、前台角色边界
  - 已修复医生结果录入 `elderId` / `doctorId` 可伪造和前台预约 `createBy` 可伪造问题
  - `083-backend/mvn test`：通过，`Tests run: 1, Failures: 0, Errors: 0`
  - `083-frontend/npm run build`：通过，仅保留 Vite chunk 体积提示
  - 后端 `18084` 使用 MySQL `root / 1234` 的 `mysql-verify` profile 真实启动抽测通过：匿名用户信息 `401`、公开公告 `200`、管理员登录 `200` 且无 password、四角色登录 `200`、统计看板 `200`、前台录入结果越权 `403`、医生录入结果 `200` 且自动绑定 `elderId=3` / `doctorId=2`、护士处理预警越权 `403`、医生处理预警 `200`、预约自动绑定 `createBy=4`、登出后旧 token `401`
  - 本轮 MySQL 复测结果：`{"anon":401,"publicNotice":200,"adminLogin":200,"loginHasPassword":false,"adminRole":"admin","adminRealName":"系统管理员","doctorLogin":200,"nurseLogin":200,"receptionLogin":200,"adminStats":200,"receptionStats":200,"receptionResultAdd":403,"doctorResultAdd":200,"resultList":200,"resultElderId":3,"resultDoctorId":2,"nurseWarningStatus":403,"doctorWarningStatus":200,"appointmentAdd":200,"appointmentCreatedBy":4,"logout":200,"afterLogout":401}`
  - MySQL 临时库 `eldercare_083_verify` 已在验证后删除
  - 前端 `3083` 页面响应和代理登录验证通过：HTTP `200`、业务码 `200`、角色 `admin`、真实姓名 `系统管理员`、token 存在且无 `password` 字段
  - 剩余风险主要是明文密码、真实报告导出、批量导入、短信通知、医学参考值规则和前端大包体积仍需后续优化

### Issues Encountered
| Issue | Resolution |
|-------|------------|
| 原始默认配置强依赖 MySQL `root/root`、Redis 和 `8080` | 默认改为 H2 自举，正式 MySQL profile 改为 `root / 1234` |
| 原始 SQL 含 `DROP DATABASE IF EXISTS eldercare_db` | 默认测试不直接重建正式库，使用 H2 和 `eldercare_083_verify` 临时库隔离验证 |
| `083-backend/mvn test` 原始缺少有效业务测试 | 新增 `ElderCareApplicationSmokeTest` 覆盖核心链路 |
| 登录响应会包含密码字段 | `SysUser.password` 改为响应脱敏，自动化测试和代理登录均确认不输出 |
| 医生录入体检结果时可伪造 `elderId` / `doctorId` | 录入时按预约单绑定老人、按当前 JWT 绑定医生 |
| 前台新增预约时可伪造 `createBy` | 新增预约改为按当前登录用户绑定 |
| MySQL 临时库第一次复测统计接口命中过期进程 | 重启干净的 `mysql-verify` 后端并重建临时库，最终统计接口复测为 `200` |

### Resources
- `docs/project-check-tracker.md`
- `docs/checks/083-eldercare-checkup-system.md`
- `083-backend/pom.xml`
- `083-backend/README.md`
- `083-backend/启动说明.txt`
- `083-backend/PRD.md`
- `083-backend/sql/init.sql`
- `083-backend/src/main/resources/application.yml`
- `083-backend/src/main/resources/application-mysql.yml`
- `083-backend/src/main/resources/application-mysql-verify.yml`
- `083-backend/src/main/resources/schema-h2.sql`
- `083-backend/src/main/resources/data-h2.sql`
- `083-backend/src/main/java/com/eldercare/common/GlobalExceptionHandler.java`
- `083-backend/src/main/java/com/eldercare/config/JwtInterceptor.java`
- `083-backend/src/main/java/com/eldercare/service/RuntimeStoreService.java`
- `083-backend/src/main/java/com/eldercare/utils/AuthUtils.java`
- `083-backend/src/main/java/com/eldercare/service/AuthService.java`
- `083-backend/src/main/java/com/eldercare/service/AppointmentService.java`
- `083-backend/src/main/java/com/eldercare/service/ResultService.java`
- `083-backend/src/test/java/com/eldercare/ElderCareApplicationSmokeTest.java`
- `083-frontend/vite.config.js`
- `083-frontend/src/api/request.js`

## Session: 2026-05-02 项目巡检续推至 084

### Findings
- `084` 项目已完成巡检、修复和复测：
  - `docs/project-check-tracker.md` 已完成到 `084`
  - `docs/checks/084-teachres-management-system.md` 已新增
  - `progress.md` 当前状态写明“下一项目：085”
- `084` 项目结构与风险已确认：
  - 后端目录：`084-backend`
  - 前端目录：`084-frontend`
  - 后端为 Spring Boot 2.7.18 + MyBatis + PageHelper + JWT
  - 前端为 Vue 3 + Vite + Element Plus + Pinia + Axios + ECharts
  - 原始默认配置强依赖 MySQL `teachres_db`、Redis 和默认 `8080`
  - 原始 SQL 脚本包含 `DROP DATABASE IF EXISTS teachres_db`
- `084` 最终修复与验证结果：
  - 默认环境已从 MySQL/Redis 强依赖改为 H2 自举，正式 MySQL 配置保留在 `application-mysql.yml`
  - 新增 `mysql-verify` profile，使用 `teachres_084_verify` 临时库完成非破坏性 MySQL 真实 HTTP 验证
  - 已修复 JDK 17、MySQL 驱动旧坐标、JJWT JAXB 兼容、H2 schema/data 和分页配置
  - 已修复 JWT Bearer 兼容、登录响应 password 字段泄露、未登录/越权 HTTP 状态和登出失效
  - 已补充管理员、教师、学生角色边界和后台管理权限
  - 已修复教师资料 `uploaderId` 可伪造、教师列表越权枚举、资料更新/删除/发布归属校验不足和学生审核越权问题
  - 已修复统计 SQL 使用 H2 保留字 `month`、`value` 别名导致看板接口 500 的问题
  - `084-backend/mvn test`：通过，`Tests run: 1, Failures: 0, Errors: 0`
  - `084-frontend/npm run build`：通过，仅保留 Vite chunk 体积提示
  - 后端 `18085` 使用 MySQL `root / 1234` 的 `mysql-verify` profile 真实启动抽测通过：匿名用户信息 `401`、公开资料 `200`、公开公告 `200`、管理员/教师/学生登录 `200`、管理员统计 `200`、学生统计越权 `403`、教师新增资料 `200` 且自动绑定 `uploaderId=2`、学生审核越权 `403`、管理员审核 `200`、学生下载收藏 `200`、登出后旧 token `401`
  - 本轮 MySQL 复测结果：`{"anon":401,"publicMaterial":200,"publicNotice":200,"adminLogin":200,"teacherLogin":200,"studentLogin":200,"loginHasPassword":false,"adminRole":"admin","adminStats":200,"studentStats":403,"materialAdd":200,"teacherList":200,"teacherMaterialUploaderId":2,"studentAudit":403,"adminAudit":200,"studentDownload":200,"studentFavorite":200,"logout":200,"afterLogout":401}`
  - MySQL 临时库 `teachres_084_verify` 已在验证后删除
  - 前端 `3084` 页面响应和代理登录验证通过：HTTP `200`、代理登录 `200`、token 存在且无 `password` 字段
  - 剩余风险主要是明文密码、真实文件存储/预览/下载鉴权、Redis 分布式 token、文件安全扫描和前端大包体积仍需后续优化

### Issues Encountered
| Issue | Resolution |
|-------|------------|
| 原始默认配置强依赖 MySQL `root/root`、Redis 和 `8080` | 默认改为 H2 自举，正式 MySQL profile 改为 `root / 1234` |
| 原始 SQL 含 `DROP DATABASE IF EXISTS teachres_db` | 默认测试不直接重建正式库，使用 H2 和 `teachres_084_verify` 临时库隔离验证 |
| `084-backend/mvn test` 原始结果为 `No tests to run` | 新增 `TeachResApplicationSmokeTest` 覆盖核心链路 |
| 登录响应会包含密码字段 | `SysUser.password` 改为响应脱敏，自动化测试和代理登录均确认不输出 |
| 教师可通过请求体伪造 `uploaderId` | 新增资料改为按 JWT 当前教师绑定 |
| 学生可尝试审核资料，教师可枚举他人资料 | 增加角色权限和教师本人资料列表约束 |
| H2 看板统计接口因 `AS month`、`AS value` 报 SQL 语法错误 | SQL 原始别名改为非保留字，服务层再转换为前端需要的 `month/value` |
| PowerShell HTTP 脚本首轮 catch 不兼容当前响应对象，且误用只读 `$HOME` 变量 | 改用 `-SkipHttpErrorCheck`，并将变量名改为 `$homeResp` 后完成验证 |

### Resources
- `docs/project-check-tracker.md`
- `docs/checks/084-teachres-management-system.md`
- `084-backend/pom.xml`
- `084-backend/README.md`
- `084-backend/启动说明.txt`
- `084-backend/PRD.md`
- `084-backend/sql/init.sql`
- `084-backend/src/main/resources/application.yml`
- `084-backend/src/main/resources/application-mysql.yml`
- `084-backend/src/main/resources/application-mysql-verify.yml`
- `084-backend/src/main/resources/schema-h2.sql`
- `084-backend/src/main/resources/data-h2.sql`
- `084-backend/src/main/resources/mapper/MaterialDownloadMapper.xml`
- `084-backend/src/main/java/com/teachres/common/GlobalExceptionHandler.java`
- `084-backend/src/main/java/com/teachres/config/JwtInterceptor.java`
- `084-backend/src/main/java/com/teachres/service/RuntimeStoreService.java`
- `084-backend/src/main/java/com/teachres/utils/AuthUtils.java`
- `084-backend/src/main/java/com/teachres/service/AuthService.java`
- `084-backend/src/main/java/com/teachres/service/MaterialService.java`
- `084-backend/src/main/java/com/teachres/service/FavoriteService.java`
- `084-backend/src/main/java/com/teachres/service/StatisticsService.java`
- `084-backend/src/test/java/com/teachres/TeachResApplicationSmokeTest.java`
- `084-frontend/vite.config.js`
- `084-frontend/src/api/request.js`

## Session: 2026-05-02 项目巡检续推至 085

### Findings
- `085` 项目已完成巡检、复测和文档回填：
  - `docs/project-check-tracker.md` 已完成到 `085`
  - `docs/checks/085-math-course-evaluation-system.md` 已新增
  - `progress.md` 当前状态写明“下一项目：086”
- `085` 项目结构与风险已确认：
  - 后端目录：`085-backend`
  - 前端目录：`085-frontend`
  - 后端为 Spring Boot 2.7.18 + MyBatis XML + PageHelper + JWT
  - 前端为 Vue 3 + Vite + Element Plus + Pinia + Axios + ECharts
  - 原始 SQL 脚本包含 `DROP DATABASE IF EXISTS math_eval_db`
  - 默认演示环境已为 H2，自带 `application-mysql.yml`、`application-mysql-verify.yml`、`schema-h2.sql`、`data-h2.sql`
- `085` 最终复测结果：
  - `085-backend/mvn test`：通过，`Tests run: 1, Failures: 0, Errors: 0`
  - `085-frontend/npm run build`：通过，仅保留 Vite chunk 体积提示
  - 后端 `18086` 默认 H2 环境真实启动抽测通过：匿名用户信息 `401`、公开公告 `200`、管理员登录脱敏 `200`、教师统计 `200`、学生统计越权 `403`、教师新增课程 `200` 且自动绑定 `teacherId=2`、学生新增课程越权 `403`、学生评价提交 `200`、登出后旧 token `401`
  - 后端 `18087` 使用 MySQL `root / 1234` 的 `mysql-verify` profile 真实启动抽测通过：匿名用户信息 `401`、公开公告 `200`、管理员/教师/学生登录 `200`、登录响应无 `password`、管理员统计 `200`、学生统计越权 `403`、管理员新增任务 `200` 且自动绑定 `creatorId=1`、学生访问任务记录 `403`、学生查看他人评价详情 `403`、教师任务汇总 `200`、学生评价提交 `200`、登出后旧 token `401`
  - 前端 `3085` 页面响应和代理登录验证通过：首页 `200`、代理登录 `200`、角色 `admin`、无 `password` 字段
  - 剩余风险主要是明文密码、真实匿名评价保护、复杂指标模型、Redis 分布式 token 和前端大包体积仍需后续优化

### Issues Encountered
| Issue | Resolution |
|-------|------------|
| PowerShell 启动和编译临时 MySQL 准备类时首轮并行导致执行抢跑 | 改为顺序编译并执行 `MysqlPrepare085`，成功创建 `math_eval_085_verify` |
| PowerShell 使用 `$HOME` 变量名读取前端首页响应时触发只读变量错误 | 改用 `$homeResp` 变量后补齐首页 `200` 验证 |
| 原始 SQL 会直接 `DROP DATABASE math_eval_db` | 默认巡检仍使用 H2，自建 `math_eval_085_verify` 临时库做 MySQL 非破坏复测 |

### Resources
- `docs/project-check-tracker.md`
- `docs/checks/085-math-course-evaluation-system.md`
- `085-backend/PRD.md`
- `085-backend/pom.xml`
- `085-backend/sql/init.sql`
- `085-backend/src/main/resources/application.yml`
- `085-backend/src/main/resources/application-mysql.yml`
- `085-backend/src/main/resources/application-mysql-verify.yml`
- `085-backend/src/main/resources/schema-h2.sql`
- `085-backend/src/main/resources/data-h2.sql`
- `085-backend/src/main/java/com/teachres/common/GlobalExceptionHandler.java`
- `085-backend/src/main/java/com/teachres/config/JwtInterceptor.java`
- `085-backend/src/main/java/com/teachres/service/AuthService.java`
- `085-backend/src/main/java/com/teachres/service/CourseService.java`
- `085-backend/src/main/java/com/teachres/service/EvaluationService.java`
- `085-backend/src/main/java/com/teachres/service/TaskService.java`
- `085-backend/src/main/java/com/teachres/service/RuntimeStoreService.java`
- `085-backend/src/test/java/com/teachres/MathEvalApplicationSmokeTest.java`
- `085-frontend/vite.config.js`
- `085-frontend/src/api/request.js`

## Session: 2026-05-02 项目巡检续推至 086

### Findings
- `086` 项目已完成巡检、复测和文档回填：
  - `docs/project-check-tracker.md` 已推进到 `086`
  - `docs/checks/086-wallpaper-community-system.md` 已新增
  - `progress.md` 当前状态写明“下一项目：087”
- `086` 项目结构与风险已确认：
  - 后端目录：`086-backend`
  - 前端目录：`086-frontend`
  - 后端为 Spring Boot 2.7.18 + MyBatis-Plus + JWT
  - 前端为 Vue 3 + Vite + Element Plus + Pinia + Axios + ECharts
  - 原始 SQL 脚本包含 `DROP DATABASE IF EXISTS wallpaper_db`
  - 原始默认配置强依赖 MySQL `root/root`、Redis 和 `8080`
- `086` 最终复测结果：
  - `086-backend/mvn test`：通过，当前为 `No tests to run`
  - `086-frontend/npm.cmd install`：通过，并生成 `package-lock.json`
  - `086-frontend/npm.cmd run build`：通过，仅保留 Vite chunk 体积提示
  - 后端 `18088` 默认 H2 环境真实启动抽测通过：匿名用户信息 `401`、公开公告和公开壁纸 `200`、管理员/用户登录脱敏 `200`、普通用户统计越权 `403`、普通用户新增壁纸 `200` 且自动绑定 `uploaderId=2`、其他普通用户删除越权 `403`、管理员审核上架 `200`、第二普通用户收藏后详情 `hasFavorite=true`、下载 `200`、登出后旧 token `401`
  - 后端 `18089` 使用 MySQL `root / 1234` 的 `mysql-verify` profile 真实启动抽测通过：匿名用户信息 `401`、公开公告 `200`、管理员统计 `200`、普通用户新增壁纸 `200` 且自动绑定 `uploaderId=2`、其他普通用户删除越权 `403`、管理员审核上架 `200`、第二普通用户收藏后详情 `hasFavorite=true`、登出后旧 token `401`
  - 前端 `3086` 页面响应和代理登录验证通过：首页 `200`、代理登录 `200`、角色 `admin`、无 `password` 字段
  - 剩余风险主要是明文密码、运行态 token 仍为进程内存存储、Redis 分布式会话未验证、上传文件仍为本地目录存储以及前端大包体积仍需后续优化

### Issues Encountered
| Issue | Resolution |
|-------|------------|
| 原始默认配置强依赖 MySQL `root/root`、Redis 和 `8080` | 默认改为 H2 自举，补齐 `application-mysql.yml` 与 `application-mysql-verify.yml` |
| JWT 拦截器与可选登录态解析都硬绑定 Redis token | 新增 `RuntimeStoreService`，登录/登出/鉴权统一改为运行态 token 存储 |
| 越权删除壁纸时业务异常默认走 `500` | 为关键参数/资源/权限分支补齐 `400/403/404` 业务码并通过真实 HTTP 复测 |
| MySQL 临时 helper 首轮读取 `init.sql` 路径失败 | 将 `MysqlPrepare086.java` 改为绝对路径读取后成功创建 `wallpaper_086_verify` |
| MySQL 原始初始化脚本只有 `admin` 和 `user`，没有 `user2` | 在 MySQL 复测阶段先通过注册接口创建 `user2`，再继续跑越权、审核和收藏链路 |

### Resources
- `docs/project-check-tracker.md`
- `docs/checks/086-wallpaper-community-system.md`
- `086-backend/PRD.md`
- `086-backend/pom.xml`
- `086-backend/sql/init.sql`
- `086-backend/src/main/resources/application.yml`
- `086-backend/src/main/resources/application-mysql.yml`
- `086-backend/src/main/resources/application-mysql-verify.yml`
- `086-backend/src/main/resources/schema-h2.sql`
- `086-backend/src/main/resources/data-h2.sql`
- `086-backend/src/main/java/com/wallpaper/common/GlobalExceptionHandler.java`
- `086-backend/src/main/java/com/wallpaper/config/JwtInterceptor.java`
- `086-backend/src/main/java/com/wallpaper/entity/SysUser.java`
- `086-backend/src/main/java/com/wallpaper/service/AuthService.java`
- `086-backend/src/main/java/com/wallpaper/service/RuntimeStoreService.java`
- `086-backend/src/main/java/com/wallpaper/service/WallpaperService.java`
- `086-backend/src/main/java/com/wallpaper/service/AuditService.java`
- `086-backend/src/main/java/com/wallpaper/service/FavoriteService.java`
- `086-backend/src/main/java/com/wallpaper/utils/JwtUtils.java`
- `086-frontend/vite.config.js`
- `086-frontend/src/api/request.js`

## Session: 2026-05-02 项目巡检续推至 087

### Findings
- `087` 项目已完成巡检、复测和文档回填：
  - `docs/project-check-tracker.md` 已推进到 `087`
  - `docs/checks/087-course-management-system.md` 已新增
  - `progress.md` 当前状态写明“下一项目：088”
- `087` 项目结构与风险已确认：
  - 后端目录：`087-backend`
  - 前端目录：`087-frontend`
  - 后端为 Spring Boot 2.7.18 + MyBatis + PageHelper + JWT
  - 前端为 Vue 3 + Vite + Element Plus + Pinia + Axios + ECharts
  - 原始 SQL 脚本包含 `DROP DATABASE IF EXISTS course_manage_db`
  - 原始默认配置强依赖 MySQL、Redis 和 `8087`
- `087` 最终复测结果：
  - `087-backend/mvn test`：通过，`Tests run: 1, Failures: 0, Errors: 0`
  - `087-frontend/npm.cmd install`：通过，并生成 `package-lock.json`
  - `087-frontend/npm.cmd run build`：通过，仅保留 Vite chunk 体积提示
  - 后端 `18090` 默认 H2 环境真实启动抽测通过：匿名用户信息 `401`、公开公告 `200`、教师统计 `200`、学生统计越权 `403`、教师新增课程资源自动绑定 `teacherId=2`、学生更新资源越权 `403`、`student2` 选课成功、教师录入成绩成功、学生录入成绩越权 `403`、`student2` 提交评教成功、登出后旧 token `401`
  - 后端 `18091` 使用 MySQL `root / 1234` 的 `mysql-verify` profile 真实启动抽测通过：匿名用户信息 `401`、公开公告 `200`、四账号登录 `200` 且无 password、教师统计 `200`、学生统计越权 `403`、教师新增课程资源自动绑定 `teacherId=2`、学生更新资源越权 `403`、`student2` 选课成功、教师录入成绩成功、学生录入成绩越权 `403`、`student2` 提交评教成功、登出后旧 token `401`
  - 前端 `3087` 页面响应和代理登录验证通过：首页 `200`、代理登录 `200`、角色 `admin`、无 `password` 字段
  - 剩余风险主要是明文密码、运行态 token 仍为进程内存存储、Redis 分布式会话未验证、复杂排课冲突/容量并发以及文件资源真实上传仍需后续优化

### Issues Encountered
| Issue | Resolution |
|-------|------------|
| 原始 SQL 含 `DROP DATABASE IF EXISTS course_manage_db` | MySQL 复测使用 `course_manage_087_verify` 临时库，避免误删正式库 |
| Java helper 首轮使用绝对中文路径读取 `init.sql` 失败 | 改为相对路径读取 `087-backend/sql/init.sql` 后成功建库导入 |
| 探活首轮误打受保护的 `/api/notice/list` 返回 `401` | 改用真实公开接口 `/api/notice/public/list` 与完整业务链路继续验证 |
| 默认演示链路对 Redis token 有运行时依赖 | 新增 `RuntimeStoreService`，改为进程内存 token 存储 |
| 课程资源、成绩和评教存在归属/权限风险 | 在服务层补齐教师/学生角色边界，并通过 H2/MySQL 双环境复测 |

### Resources
- `docs/project-check-tracker.md`
- `docs/checks/087-course-management-system.md`
- `087-backend/PRD.md`
- `087-backend/pom.xml`
- `087-backend/sql/init.sql`
- `087-backend/src/main/resources/application.yml`
- `087-backend/src/main/resources/application-mysql.yml`
- `087-backend/src/main/resources/application-mysql-verify.yml`
- `087-backend/src/main/resources/schema-h2.sql`
- `087-backend/src/main/resources/data-h2.sql`
- `087-backend/src/main/java/com/course/common/GlobalExceptionHandler.java`
- `087-backend/src/main/java/com/course/config/JwtInterceptor.java`
- `087-backend/src/main/java/com/course/entity/SysUser.java`
- `087-backend/src/main/java/com/course/service/AuthService.java`
- `087-backend/src/main/java/com/course/service/RuntimeStoreService.java`
- `087-backend/src/main/java/com/course/service/ResourceService.java`
- `087-backend/src/main/java/com/course/service/SelectionService.java`
- `087-backend/src/main/java/com/course/service/ScoreService.java`
- `087-backend/src/main/java/com/course/service/EvaluationService.java`
- `087-backend/src/test/java/com/course/CourseManagementApplicationSmokeTest.java`
- `087-frontend/vite.config.js`
- `087-frontend/src/api/request.js`

## Session: 2026-05-02 项目巡检续推至 088

### Findings
- `088` 项目已完成巡检、复测和文档回填：
  - `docs/project-check-tracker.md` 已推进到 `088`
  - `docs/checks/088-child-adoption-management-system.md` 已新增
  - `progress.md` 当前状态写明“下一项目：089”
- `088` 项目结构与风险已确认：
  - 后端目录：`088-backend`
  - 前端目录：`088-frontend`
  - 后端为 Spring Boot 2.7.18 + MyBatis-Plus + JWT
  - 前端为 Vue 3 + Vite + Element Plus + Pinia + Axios + ECharts
  - 原始 SQL 脚本包含 `DROP DATABASE IF EXISTS 088_child_adoption`
  - 原始默认配置强依赖 MySQL `root/root`、Redis 和 `8080`
- `088` 最终复测结果：
  - `088-backend/mvn test`：通过，`Tests run: 3, Failures: 0, Errors: 0`
  - `088-frontend/npm.cmd install`：通过，并生成 `package-lock.json`
  - `088-frontend/npm.cmd run build`：通过，仅保留 Vite chunk 体积提示
  - 后端 `18093` 默认 H2 环境真实启动抽测通过：匿名用户信息 `401`、公开儿童列表 `200`、管理员/审核员/申请人登录 `200` 且无 password、管理员统计 `200`、审核员越权申请 `403`、跨申请人材料追加越权 `403`
  - 后端 `18094` 使用 MySQL `root / 1234` 的 `mysql-verify` profile 真实启动抽测通过：匿名用户信息 `401`、公开儿童列表 `200`、管理员/审核员/申请人登录 `200`、新申请人注册 `200`、资料更新 `200`、申请创建 `200`、审核 `200`、跨申请人材料追加越权 `403`、本人材料新增 `200`、登出后旧 token `401`
  - 前端 `3088` 页面响应和代理登录验证通过：首页 `200`、代理登录 `200`、角色 `admin`、无 `password` 字段
  - 剩余风险主要是明文密码、运行态 token 仍为进程内存存储、Redis 分布式会话未验证、流程状态流转约束仍偏松和文件材料真实存储仍需后续优化

### Issues Encountered
| Issue | Resolution |
|-------|------------|
| `AgreementService` 缺失 `Wrappers` 导入导致后端编译失败 | 补齐导入后恢复编译 |
| 原始默认配置强依赖 MySQL `root/root`、Redis 和 `8080` | 默认改为 H2 自举，补齐 `application-mysql.yml` 与 `application-mysql-verify.yml` |
| JWT 拦截器与登录态校验都硬绑定 Redis token | 新增 `RuntimeStoreService`，登录/登出/鉴权统一改为运行态 token 存储 |
| 申请材料接口允许申请人向他人申请越权追加材料 | 在服务层补齐申请归属校验，H2/MySQL 双环境复测均返回 `403` |
| 审核员可调用申请创建接口伪装申请人发起收养申请 | 在控制器层补齐 `assertApplicant` 角色校验，复测返回 `403` |
| 冒烟测试首轮命中种子数据中的既有申请 | 改为测试中注册新申请人，再走资料更新和申请闭环 |

### Resources
- `docs/project-check-tracker.md`
- `docs/checks/088-child-adoption-management-system.md`
- `088-backend/PRD.md`
- `088-backend/pom.xml`
- `088-backend/sql/init.sql`
- `088-backend/src/main/resources/application.yml`
- `088-backend/src/main/resources/application-mysql.yml`
- `088-backend/src/main/resources/application-mysql-verify.yml`
- `088-backend/src/main/resources/schema-h2.sql`
- `088-backend/src/main/resources/data-h2.sql`
- `088-backend/src/main/java/com/adoption/common/GlobalExceptionHandler.java`
- `088-backend/src/main/java/com/adoption/config/JwtInterceptor.java`
- `088-backend/src/main/java/com/adoption/entity/SysUser.java`
- `088-backend/src/main/java/com/adoption/service/AuthService.java`
- `088-backend/src/main/java/com/adoption/service/RuntimeStoreService.java`
- `088-backend/src/main/java/com/adoption/service/ApplicationService.java`
- `088-backend/src/main/java/com/adoption/service/AgreementService.java`
- `088-backend/src/test/java/com/adoption/ChildAdoptionApplicationSmokeTest.java`
- `088-frontend/vite.config.js`
- `088-frontend/src/api/request.js`

## Session: 2026-05-03 项目巡检推进至 089

### Findings
- `089` 项目已完成巡检、复测和文档回填：
  - `docs/project-check-tracker.md` 已推进到 `089`
  - `docs/checks/089-railway-ticket-platform.md` 已新增
  - `progress.md` 当前状态写明“下一项目：090”
- `089` 项目结构与风险已确认：
  - 后端目录：`089-backend`
  - 前端目录：`089-frontend`
  - 后端为 Spring Boot 2.7.18 + MyBatis-Plus + JWT
  - 前端为 Vue 3 + Vite + Element Plus + Pinia + Axios + ECharts
  - 原始 SQL 脚本包含 `DROP DATABASE IF EXISTS railway_ticket_089`
  - 原始默认配置强依赖 MySQL `railway_ticket_089`、Redis 和 `8080`
- `089` 最终复测结果：
  - `089-backend/mvn test`：通过，`Tests run: 1, Failures: 0, Errors: 0`
  - `089-frontend/npm.cmd run build`：通过，仅保留 Vite chunk 体积提示
  - 后端 `18095` 默认 H2 环境真实启动抽测通过：匿名用户信息 `401`、公开班次 `3` 条、公开公告 `2` 条、登录无 password、普通用户统计越权 `403`、锁座下单支付成功、跨用户订单访问 `403`、普通用户核销越权 `403`、登出后旧 token `401`
  - 后端 `18096` 使用 MySQL `root / 1234` 的 `mysql-verify` profile 真实启动抽测通过：匿名用户信息 `401`、公开班次 `3` 条、公开公告 `2` 条、新乘客注册 `200`、登录无 password、普通用户统计越权 `403`、锁座下单支付成功、跨用户订单访问 `403`、普通用户核销越权 `403`、登出后旧 token `401`
  - 前端 `3089` 页面响应和代理登录验证通过：首页 `200`、代理登录 `200`、角色 `ADMIN`、无 `password` 字段
  - 剩余风险主要是明文密码、运行态 token/座位锁仍为进程内存存储、普通用户车票分页总数统计不精确和改签流程仍未形成新订单闭环

### Issues Encountered
| Issue | Resolution |
|-------|------------|
| 默认环境强依赖 MySQL、Redis 和 `8080`，本机不可直接起服务 | 改为默认 H2 自举，并补齐 `application-mysql.yml` 与 `application-mysql-verify.yml` |
| JWT 鉴权与座位锁都硬绑定 Redis | 新增 `RuntimeStoreService`，登录/登出/鉴权/座位锁统一改为运行态存储 |
| `schedule/list`、`schedule/enabled`、`seat/schedule/**` 等公开接口被拦截器误拦截 | 在 `WebMvcConfig` 中补齐公开放行白名单 |
| H2 首轮建表命中 `user` 保留字导致启动失败 | 在 H2 连接串中加入 `NON_KEYWORDS=USER` 并复跑 |
| MySQL 验证库原始数据没有第二乘客，跨用户越权校验脚本第一次串掉 | 在 MySQL 真实复测中先走注册接口创建新乘客，再验证跨用户订单访问 `403` |

### Resources
- `docs/project-check-tracker.md`
- `docs/checks/089-railway-ticket-platform.md`
- `089-backend/PRD.md`
- `089-backend/pom.xml`
- `089-backend/sql/init.sql`
- `089-backend/src/main/resources/application.yml`
- `089-backend/src/main/resources/application-mysql.yml`
- `089-backend/src/main/resources/application-mysql-verify.yml`
- `089-backend/src/main/resources/schema-h2.sql`
- `089-backend/src/main/resources/data-h2.sql`
- `089-backend/src/main/java/com/railway/common/GlobalExceptionHandler.java`
- `089-backend/src/main/java/com/railway/config/JwtInterceptor.java`
- `089-backend/src/main/java/com/railway/config/WebMvcConfig.java`
- `089-backend/src/main/java/com/railway/entity/User.java`
- `089-backend/src/main/java/com/railway/service/RuntimeStoreService.java`
- `089-backend/src/main/java/com/railway/service/UserService.java`
- `089-backend/src/main/java/com/railway/service/SeatService.java`
- `089-backend/src/test/java/com/railway/RailwayTicketApplicationSmokeTest.java`
- `089-backend/target/MysqlPrepare089.java`
- `089-frontend/vite.config.js`
- `089-frontend/src/api/request.js`

## Session: 2026-05-03 项目巡检推进至 090

### Findings
- `090` 项目已完成巡检、复测和文档回填：
  - `docs/project-check-tracker.md` 已推进到 `090`
  - `docs/checks/090-opera-culture-platform.md` 已新增
  - `progress.md` 当前状态写明“下一项目：091”
- `090` 项目结构与风险已确认：
  - 后端目录：`090-backend`
  - 前端目录：`090-frontend`
  - 后端为 Spring Boot 2.7.18 + MyBatis + PageHelper + JWT
  - 前端为 Vue 3 + Vite + Element Plus + Pinia + Axios + ECharts
  - 原始 SQL 脚本包含 `DROP DATABASE IF EXISTS opera_culture_db`
  - 原始默认配置强依赖 MySQL `root/root`、Redis 和 `8080`
- `090` 最终复测结果：
  - `090-backend/mvn test`：通过，`Tests run: 1, Failures: 0, Errors: 0`
  - `090-frontend/npm.cmd install`：通过，并生成 `package-lock.json`
  - `090-frontend/npm.cmd run build`：通过，仅保留 Vite chunk 体积提示
  - 后端 `18097` 默认 H2 环境真实启动抽测通过：匿名用户信息 `401`、公开公告 `2` 条、公开剧目 `3` 条、登录无 password、会员统计越权 `403`、艺术家预约越权 `403`、会员预约成功、资源管理员更新 `200`、会员删除资源越权 `403`、艺术家评分 `200`、未预约会员评论越权 `403`、登出后旧 token `401`
  - 后端 `18098` 使用 MySQL `root / 1234` 的 `mysql-verify` profile 真实启动抽测通过：匿名用户信息 `401`、公开公告 `2` 条、公开剧目 `3` 条、登录无 password、会员统计越权 `403`、艺术家预约越权 `403`、会员预约成功、资源管理员更新 `200`、会员删除资源越权 `403`、艺术家评分 `200`、未预约会员评论越权 `403`、登出后旧 token `401`
  - 前端 `3090` 页面响应和代理登录验证通过：首页 `200`、代理登录 `200`、角色 `admin`、无 `password` 字段
  - 剩余风险主要是明文密码、运行态 token 仍为进程内存存储、业务命名保留课程系统痕迹和真实文件资源存储仍未落地

### Issues Encountered
| Issue | Resolution |
|-------|------------|
| 默认环境强依赖 MySQL `root/root`、Redis 和 `8080`，本机不可直接起服务 | 改为默认 H2 自举，并补齐 `application-mysql.yml` 与 `application-mysql-verify.yml` |
| JWT 鉴权硬绑定 Redis token 且未兼容 Bearer | 新增 `RuntimeStoreService`，登录/登出/鉴权改为运行态 token 存储，并兼容 `Authorization: Bearer ...` |
| 公开剧目/排期接口被拦截器误拦截 | 在 `WebMvcConfig` 中补齐公开放行白名单 |
| 艺术家与管理员可直接调用会员预约接口 | 在 `BookingService` 中补齐会员角色校验 |
| 未预约会员可以直接提交赏析互动，艺术家可修改他人资源 | 在评论、资源、评分和签到链路补齐归属校验与前置预约约束 |

### Resources
- `docs/project-check-tracker.md`
- `docs/checks/090-opera-culture-platform.md`
- `090-backend/PRD.md`
- `090-backend/pom.xml`
- `090-backend/sql/init.sql`
- `090-backend/src/main/resources/application.yml`
- `090-backend/src/main/resources/application-mysql.yml`
- `090-backend/src/main/resources/application-mysql-verify.yml`
- `090-backend/src/main/resources/schema-h2.sql`
- `090-backend/src/main/resources/data-h2.sql`
- `090-backend/src/main/java/com/opera/common/GlobalExceptionHandler.java`
- `090-backend/src/main/java/com/opera/config/JwtInterceptor.java`
- `090-backend/src/main/java/com/opera/config/WebMvcConfig.java`
- `090-backend/src/main/java/com/opera/entity/SysUser.java`
- `090-backend/src/main/java/com/opera/service/RuntimeStoreService.java`
- `090-backend/src/main/java/com/opera/service/AuthService.java`
- `090-backend/src/main/java/com/opera/service/BookingService.java`
- `090-backend/src/main/java/com/opera/service/MediaResourceService.java`
- `090-backend/src/main/java/com/opera/service/ReviewService.java`
- `090-backend/src/main/java/com/opera/service/CommentService.java`
- `090-backend/src/main/java/com/opera/service/CheckinService.java`
- `090-backend/src/test/java/com/opera/OperaCultureApplicationSmokeTest.java`
- `090-backend/target/MysqlPrepare090.java`
- `090-frontend/vite.config.js`
- `090-frontend/src/api/request.js`

## Session: 2026-05-03 项目巡检推进至 091

### Findings
- `091` 项目已完成巡检、复测和文档回填：
  - `docs/project-check-tracker.md` 已推进到 `091`
  - `docs/checks/091-cinema-member-system.md` 已新增
  - `progress.md` 当前状态写明“下一项目：092”
- `091` 项目结构与风险已确认：
  - 后端目录：`091-backend`
  - 前端目录：`091-frontend`
  - 后端为 Spring Boot 2.7.18 + MyBatis-Plus + JWT
  - 前端为 Vue 3 + Vite + Element Plus + Pinia + Axios + ECharts
  - 原始 SQL 脚本包含 `DROP DATABASE IF EXISTS cinema_member_system_091`
  - 原始默认配置强依赖 MySQL、Redis 和 `8080`
- `091` 最终复测结果：
  - `091-backend/mvn test`：通过，`Tests run: 1, Failures: 0, Errors: 0`
  - `091-frontend/npm.cmd install`：通过，并生成 `package-lock.json`
  - `091-frontend/npm.cmd run build`：通过，仅保留 Vite chunk 体积提示
  - 后端 `18099` 默认 H2 环境真实启动抽测通过：匿名用户信息 `401`、公开影片 `4` 条、公开场次 `5` 条、登录无 password、会员统计越权 `403`、员工下单越权 `403`、会员锁座下单支付成功、跨用户订单/票券访问 `403`、未购票评论越权 `403`、普通会员核销越权 `403`、员工核销 `200`、登出后旧 token `401`
  - 后端 `18100` 使用 MySQL `root / 1234` 的 `mysql-verify` profile 真实启动抽测通过：匿名用户信息 `401`、公开影片 `4` 条、公开场次 `5` 条、登录无 password、会员统计越权 `403`、员工下单越权 `403`、会员锁座下单支付成功、跨用户订单/票券访问 `403`、未购票评论越权 `403`、普通会员核销越权 `403`、员工核销 `200`、登出后旧 token `401`
  - 前端 `3091` 页面响应和代理登录验证通过：首页 `200`、代理登录 `200`、角色 `ADMIN`、无 `password` 字段
  - 剩余风险主要是明文密码、运行态 token/座位锁仍为进程内存存储、真实支付和通知链路未落地以及前端大包体积仍需后续优化

### Issues Encountered
| Issue | Resolution |
|-------|------------|
| 默认环境强依赖 MySQL、Redis 和 `8080`，本机不可直接起服务 | 改为默认 H2 自举，并补齐 `application-mysql.yml` 与 `application-mysql-verify.yml` |
| JWT 鉴权与座位锁都硬绑定 Redis | 新增 `RuntimeStoreService`，登录/登出/鉴权/座位锁统一改为运行态存储 |
| 前端请求头未带 `Bearer`，Vite 代理固定指向 `3000/8080` | 调整 `request.js` 和 `vite.config.js`，支持 Bearer 与环境变量覆盖 |
| 评论接口允许不带本人已购订单直接提交 | 在 `CommentService` 中补齐订单归属、订单状态和影片匹配校验 |
| 员工和管理员可以直接复用会员链路下单/支付/领券 | 在控制器层补齐 `MEMBER` 角色限制，并通过 H2/MySQL 双环境复测返回 `403` |

### Resources
- `docs/project-check-tracker.md`
- `docs/checks/091-cinema-member-system.md`
- `091-backend/PRD.md`
- `091-backend/pom.xml`
- `091-backend/sql/init.sql`
- `091-backend/src/main/resources/application.yml`
- `091-backend/src/main/resources/application-mysql.yml`
- `091-backend/src/main/resources/application-mysql-verify.yml`
- `091-backend/src/main/resources/schema-h2.sql`
- `091-backend/src/main/resources/data-h2.sql`
- `091-backend/src/main/java/com/cinema/common/GlobalExceptionHandler.java`
- `091-backend/src/main/java/com/cinema/config/JwtInterceptor.java`
- `091-backend/src/main/java/com/cinema/entity/User.java`
- `091-backend/src/main/java/com/cinema/service/RuntimeStoreService.java`
- `091-backend/src/main/java/com/cinema/service/UserService.java`
- `091-backend/src/main/java/com/cinema/service/SeatService.java`
- `091-backend/src/main/java/com/cinema/service/CommentService.java`
- `091-backend/src/test/java/com/cinema/CinemaMemberApplicationSmokeTest.java`
- `091-backend/target/MysqlPrepare091.java`
- `091-frontend/vite.config.js`
- `091-frontend/src/api/request.js`

## Session: 2026-05-03 项目巡检推进至 092

### Findings
- `092` 项目已完成巡检、复测和文档回填：
  - `docs/project-check-tracker.md` 已推进到 `092`
  - `docs/checks/092-kindergarten-management-system.md` 已新增
  - `progress.md` 当前状态写明“下一项目：093”
- `092` 项目结构与风险已确认：
  - 后端目录：`092-backend`
  - 前端目录：`092-frontend`
  - 后端为 Spring Boot 2.7.18 + MyBatis + PageHelper + JWT
  - 前端为 Vue 3 + Vite + Element Plus + Axios
  - 原始 SQL 脚本包含 `DROP DATABASE IF EXISTS blue_sky_kindergarten_db`
  - 原始默认配置强依赖 MySQL、Redis 和 `8080`
- `092` 最终复测结果：
  - `092-backend/mvn.cmd test`：通过，`Tests run: 1, Failures: 0, Errors: 0`
  - `092-frontend/npm.cmd install`：通过，并生成 `package-lock.json`
  - `092-frontend/npm.cmd run build`：通过，仅保留 Vite chunk 体积提示
  - 后端 `18101` 默认 H2 环境真实启动抽测通过：匿名用户信息 `401`、公开公告 `2` 条、登录无 password、家长统计越权 `403`、家长本人幼儿档案 `1` 条、家长本人课表 `1` 条、教师跨班考勤越权 `403`、教师本人考勤修改 `200`、教师跨班晨检越权 `403`、教师本人晨检修改 `200`、家长伪造他人反馈 `403`、本人反馈 `200`、教师跨班回复越权 `403`、本人回复 `200`、教师删除他人食谱越权 `403`、登出后旧 token `401`
  - 后端 `18102` 使用 MySQL `root / 1234` 的 `mysql-verify` profile 真实启动抽测通过：结果与 H2 口径一致；验证库为 `kindergarten_092_verify`
  - 前端 `3092` 页面响应和代理登录验证通过：首页 `200`、代理登录 `200`、角色 `admin`、无 `password` 字段
  - 剩余风险主要是明文密码、运行态 token 仍为进程内存存储、表结构命名串项明显、真实通知/上传/接送链路未落地以及前端大包体积仍需后续优化

### Issues Encountered
| Issue | Resolution |
|-------|------------|
| 默认环境强依赖 MySQL、Redis 和 `8080`，本机不可直接起服务 | 改为默认 H2 自举，并补齐 `application-mysql.yml` 与 `application-mysql-verify.yml` |
| JWT 鉴权硬绑定 Redis，前端请求头未带 Bearer | 新增 `RuntimeStoreService`，调整 `JwtInterceptor` 和前端 `request.js` |
| `GlobalExceptionHandler` 只返回业务体，不回真实 HTTP 状态 | 改为 `ResponseEntity<Result<?>>`，统一返回真实 `401/403/404/500` |
| 教师可跨班修改考勤、晨检、反馈回复和食谱 | 在 `ChildService/AttendanceService/HealthService/FeedbackService/RecipeService` 中补齐归属校验 |
| `schema-h2.sql` 初版用了 `CLOB`，MySQL verify 启动时报错 | 调整为 H2/MySQL 通吃的 `TEXT` 后重跑双环境验证 |

### Resources
- `docs/project-check-tracker.md`
- `docs/checks/092-kindergarten-management-system.md`
- `092-backend/PRD.md`
- `092-backend/pom.xml`
- `092-backend/sql/init.sql`
- `092-backend/src/main/resources/application.yml`
- `092-backend/src/main/resources/application-mysql.yml`
- `092-backend/src/main/resources/application-mysql-verify.yml`
- `092-backend/src/main/resources/schema-h2.sql`
- `092-backend/src/main/resources/data-h2.sql`
- `092-backend/src/main/java/com/kindergarten/common/GlobalExceptionHandler.java`
- `092-backend/src/main/java/com/kindergarten/config/JwtInterceptor.java`
- `092-backend/src/main/java/com/kindergarten/entity/SysUser.java`
- `092-backend/src/main/java/com/kindergarten/service/RuntimeStoreService.java`
- `092-backend/src/main/java/com/kindergarten/service/AuthService.java`
- `092-backend/src/main/java/com/kindergarten/service/ChildService.java`
- `092-backend/src/main/java/com/kindergarten/service/AttendanceService.java`
- `092-backend/src/main/java/com/kindergarten/service/HealthService.java`
- `092-backend/src/main/java/com/kindergarten/service/FeedbackService.java`
- `092-backend/src/main/java/com/kindergarten/service/RecipeService.java`
- `092-backend/src/main/java/com/kindergarten/service/ScheduleService.java`
- `092-backend/src/test/java/com/kindergarten/KindergartenApplicationSmokeTest.java`
- `092-backend/target/MysqlPrepare092.java`
- `092-frontend/vite.config.js`
- `092-frontend/src/api/request.js`

## Session: 2026-05-03 项目巡检推进至 093

### Findings
- `093` 项目已完成巡检、复测和文档回填：
  - `docs/project-check-tracker.md` 已推进到 `093`
  - `docs/checks/093-vending-management-system.md` 已新增
  - 下一项目已切换为 `094`
- `093` 项目结构与风险已确认：
  - 后端目录：`093-backend`
  - 前端目录：`093-frontend`
  - 后端为 Spring Boot 2.7.18 + MyBatis-Plus + JWT + Redis 依赖保留
  - 前端为 Vue 3 + Vite + Element Plus + Axios
  - 原始问题包括 JDK 1.8、默认 MySQL 强依赖、Mapper 未扫描、Redis token 强依赖、前端 Bearer 缺失
- `093` 最终复测结果：
  - `093-backend/mvn.cmd test`：通过
  - `093-frontend/npm.cmd install`：通过，并生成 `package-lock.json`
  - `093-frontend/npm.cmd run build`：通过，仅保留 Vite chunk 体积提示
  - 后端 `18103` 默认 H2 环境真实启动抽测通过：匿名设备详情 `401`、公开公告 `2` 条、登录无 password、管理员角色 `ADMIN`、顾客统计越权 `403`
  - 后端 `18104` 使用 MySQL `root / 1234` 的 `mysql-verify` profile 真实启动抽测通过：结果与 H2 口径一致，并额外确认补货员创建订单越权 `403`；验证库为 `vending_093_verify`
  - 前端 `3093` 页面响应和代理登录验证通过：首页 `200`、代理登录 `200`、角色 `ADMIN`、无 `password` 字段
  - 当前已清理 `18103`、`18104`、`3093` 验证进程，准备进入 `094`

### Issues Encountered
| Issue | Resolution |
|-------|------------|
| 默认环境强依赖 MySQL、Redis 和 JDK 8 配置，JDK 17 下默认不可直接验收 | 升级 `pom.xml` 到 JDK 17，改为默认 H2 自举，并补齐 `application-mysql.yml` 与 `application-mysql-verify.yml` |
| `Mapper` 未被扫描，应用启动后核心查询不可用 | 在启动类补充 `@MapperScan(\"com.vending.mapper\")` |
| JWT token 存储硬绑 Redis，前端请求头未带 Bearer | 新增 `RuntimeStoreService`，调整 `JwtInterceptor` 和前端 `request.js` |
| `GlobalExceptionHandler` 不回真实 HTTP 状态，权限边界不易验证 | 改为返回真实 `401/403/404/500`，并补充冒烟测试 |
| 顾客订单支付、故障上报和补货处理缺少角色/归属校验 | 在 `OrderService/FaultService` 与相关控制器中补齐角色与本人归属约束 |

### Resources
- `docs/project-check-tracker.md`
- `docs/checks/093-vending-management-system.md`
- `093-backend/pom.xml`
- `093-backend/src/main/resources/application.yml`
- `093-backend/src/main/resources/application-mysql.yml`
- `093-backend/src/main/resources/application-mysql-verify.yml`
- `093-backend/src/main/resources/schema-h2.sql`
- `093-backend/src/main/resources/data-h2.sql`
- `093-backend/src/main/resources/schema-mysql.sql`
- `093-backend/src/main/resources/data-mysql.sql`
- `093-backend/src/main/java/com/vending/VendingManagementApplication.java`
- `093-backend/src/main/java/com/vending/common/GlobalExceptionHandler.java`
- `093-backend/src/main/java/com/vending/config/JwtInterceptor.java`
- `093-backend/src/main/java/com/vending/service/RuntimeStoreService.java`
- `093-backend/src/main/java/com/vending/service/UserService.java`
- `093-backend/src/main/java/com/vending/service/OrderService.java`
- `093-backend/src/main/java/com/vending/service/FaultService.java`
- `093-backend/src/main/java/com/vending/utils/AuthUtils.java`
- `093-backend/src/main/java/com/vending/utils/JwtUtils.java`
- `093-backend/src/test/java/com/vending/VendingManagementApplicationSmokeTest.java`
- `093-backend/target/MysqlPrepare093.java`
- `093-frontend/vite.config.js`
- `093-frontend/src/api/request.js`

## Session: 2026-05-03 项目巡检切入 094

### Findings
- `094` 项目名已确认：`SpringBoot 宠物咖啡馆平台设计与实现`
- `094` 目录结构已确认：
  - 后端目录：`094-backend`
  - 前端目录：`094-frontend`
- `094` 原始后端风险已确认：
  - `pom.xml` 仍为 `java.version=1.8`
  - 默认数据库直连 `jdbc:mysql://localhost:3306/pet_cafe_platform_094`
  - 默认 Redis 直连 `localhost:6379`
  - 默认端口写死 `8080`
  - MySQL 密码在配置里仍是 `123456`，与本轮统一验证口径 `root / 1234` 不一致
- `094` 原始前端风险已确认：
  - Vite 默认端口写死 `3000`
  - 代理目标写死 `http://localhost:8080`
  - `request.js` 仍直接把 token 原样写入 `Authorization`，尚未补 Bearer
  - 需要后续通过构建和实际登录验证角色与脱敏返回

### Resources
- `094-backend/PRD.md`
- `094-backend/PLAN.md`
- `094-backend/pom.xml`
- `094-backend/src/main/resources/application.yml`
- `094-backend/sql/init.sql`
- `094-frontend/package.json`
- `094-frontend/vite.config.js`
- `094-frontend/src/api/request.js`
- `094-frontend/src/api/index.js`

## Session: 2026-05-03 项目巡检推进至 094

### Findings
- `094` 项目已完成巡检、复测和文档回填：
  - `docs/project-check-tracker.md` 已推进到 `094`
  - `docs/checks/094-pet-cafe-platform.md` 已新增
  - 下一项目已切换为 `095`
- `094` 项目结构与风险已确认：
  - 后端目录：`094-backend`
  - 前端目录：`094-frontend`
  - 后端为 Spring Boot 2.7.18 + MyBatis-Plus + JWT + Redis 依赖保留
  - 前端为 Vue 3 + Vite + Element Plus + Pinia + Axios + ECharts
  - 原始问题包括 Java 源文件 BOM、JDK 1.8、默认 MySQL/Redis 强依赖、Mapper 未扫描、前端 Bearer 缺失、宠物公开列表未放行、角色边界过宽
- `094` 最终复测结果：
  - `094-backend/mvn.cmd test`：通过，新增 `PetCafePlatformApplicationSmokeTest`
  - `094-frontend/npm.cmd install`：通过
  - `094-frontend/npm.cmd run build`：通过，仅保留 Vite chunk 体积提示
  - 后端 `18105` 默认 H2 环境真实启动抽测通过：匿名 `401`、公开店宠 `2` 条、公开菜单 `2` 条、公开公告 `2` 条、管理员登录无 password、顾客统计越权 `403`、店长公告列表越权 `403`
  - 后端 `18106` 使用 MySQL `root / 1234` 的 `mysql-verify` profile 真实启动抽测通过：匿名 `401`、公开店宠 `2` 条、管理员登录无 password、顾客统计越权 `403`、店长下单越权 `403`；验证库为 `pet_cafe_platform_094_verify`
  - 前端 `3094` 页面响应和代理登录验证通过：首页 `200`、代理登录 `200`、角色 `ADMIN`、无 `password` 字段

### Issues Encountered
| Issue | Resolution |
|-------|------------|
| 46 个 Java 源文件携带 UTF-8 BOM，`mvn test` 首轮直接报 `\ufeff` 非法字符 | 对 `094-backend/src/main/java` 执行机械清洗，去除 BOM 后恢复编译 |
| 默认环境强依赖 MySQL、Redis、`8080` 和 JDK 8 配置 | 升级到 JDK 17，改为默认 H2 自举，并补齐 `application-mysql.yml` 与 `application-mysql-verify.yml` |
| Mapper 接口未显式扫描 | 在启动类补充 `@MapperScan(\"com.petcafe.mapper\")` |
| JWT token 存储硬绑 Redis，前端请求头未带 Bearer | 新增 `RuntimeStoreService`，调整 `JwtInterceptor` 和前端 `request.js` |
| 宠物公开列表 `/api/pet/public/list` 与 PRD 口径不符，匿名访问返回 `401` | 在 `WebMvcConfig` 放行 `/api/pet/public/**`，并写入烟雾测试 |
| 顾客/店长/管理员角色边界过宽 | 新增 `AuthUtils.requireAdmin/requireManager/requireCustomer`，分别收紧订单、支付、预约、评价、公告和统计入口 |

### Resources
- `docs/project-check-tracker.md`
- `docs/checks/094-pet-cafe-platform.md`
- `094-backend/pom.xml`
- `094-backend/src/main/resources/application.yml`
- `094-backend/src/main/resources/application-mysql.yml`
- `094-backend/src/main/resources/application-mysql-verify.yml`
- `094-backend/src/main/resources/schema-h2.sql`
- `094-backend/src/main/resources/data-h2.sql`
- `094-backend/src/main/resources/schema-mysql.sql`
- `094-backend/src/main/resources/data-mysql.sql`
- `094-backend/src/main/java/com/petcafe/PetCafePlatformApplication.java`
- `094-backend/src/main/java/com/petcafe/common/GlobalExceptionHandler.java`
- `094-backend/src/main/java/com/petcafe/config/JwtInterceptor.java`
- `094-backend/src/main/java/com/petcafe/config/WebMvcConfig.java`
- `094-backend/src/main/java/com/petcafe/entity/User.java`
- `094-backend/src/main/java/com/petcafe/service/RuntimeStoreService.java`
- `094-backend/src/main/java/com/petcafe/service/UserService.java`
- `094-backend/src/main/java/com/petcafe/service/OrderService.java`
- `094-backend/src/main/java/com/petcafe/service/PaymentService.java`
- `094-backend/src/main/java/com/petcafe/service/ReservationService.java`
- `094-backend/src/main/java/com/petcafe/service/ReviewService.java`
- `094-backend/src/main/java/com/petcafe/utils/AuthUtils.java`
- `094-backend/src/test/java/com/petcafe/PetCafePlatformApplicationSmokeTest.java`
- `094-backend/target/MysqlPrepare094.java`
- `094-frontend/vite.config.js`
- `094-frontend/src/api/request.js`

## Session: 2026-05-03 项目巡检推进至 095

### Summary
- `095` 项目已完成巡检、复测和文档回填：
  - `docs/project-check-tracker.md` 已推进到 `095`
  - `docs/checks/095-football-league-system.md` 已新增
  - 下一项目已切换为 `096`
- `095` 项目结构与风险已确认：
  - 后端目录：`095-backend`
  - 前端目录：`095-frontend`
  - 默认后端端口：`8095`
  - 前端开发端口：`3095`
  - 正式 MySQL 数据库：`football_league_management_095`
  - MySQL 账号密码：`root / 1234`
- `095` 最终复测结果：
  - `095-backend/mvn.cmd test`：通过，`Tests run: 1, Failures: 0, Errors: 0`
  - `095-frontend/npm.cmd run build`：通过，仅保留 Vite CJS API 和 chunk 体积提示
  - 后端 `18107` 默认 H2 环境真实启动抽测通过：匿名 `401`、公开赛程 `200`、三角色登录脱敏、球迷统计越权 `403`、经理统计 `200`、关注球队 `200`、登出后旧 token `401`
  - 后端 `18108` 使用 MySQL `root / 1234` 的 `mysql-verify` profile 真实启动抽测通过：匿名 `401`、公开赛程/资讯/积分榜 `200`、三角色登录脱敏、球迷统计越权 `403`、经理访问用户管理越权 `403`、经理统计 `200`、关注球队 `200`、登出后旧 token `401`
  - 前端 `3095` 页面响应和代理登录验证通过：首页 `200`、代理登录 `200`、角色 `ADMIN`、无 `password` 字段
  - MySQL 临时库 `football_league_management_095_verify` 已在验证后删除
  - `18107`、`18108`、`3095` 验证进程已清理

### Key Evidence
| Area | Evidence |
|------|----------|
| 后端测试 | `095-backend/mvn.cmd test` 通过，新增 `FootballLeagueApplicationSmokeTest` |
| MySQL 真实链路 | `football_league_management_095_verify` 临时库，`root / 1234`，HTTP 验证结果包含 `fanStats=403`、`managerUserList=403`、`followToggle=200`、`afterLogoutFollowList=401` |
| 前端联调 | `3095` 首页 `200`，代理登录 `200`，有 token 且无 password |

### Files Touched / Verified
- `docs/checks/095-football-league-system.md`
- `docs/project-check-tracker.md`
- `progress.md`
- `task_plan.md`
- `095-backend/PRD.md`
- `095-backend/pom.xml`
- `095-backend/src/main/resources/application.yml`
- `095-backend/src/main/resources/application-mysql.yml`
- `095-backend/src/main/resources/application-mysql-verify.yml`
- `095-backend/src/main/resources/schema-h2.sql`
- `095-backend/src/main/resources/data-h2.sql`
- `095-backend/src/main/resources/schema-mysql.sql`
- `095-backend/src/main/resources/data-mysql.sql`
- `095-backend/src/test/java/com/football/FootballLeagueApplicationSmokeTest.java`
- `095-frontend/vite.config.js`
- `095-frontend/src/api/request.js`

## Session: 2026-05-03 项目巡检完成至 096

### Summary
- `096` 项目已完成巡检、修复、复测和文档回填：
  - `docs/project-check-tracker.md` 已推进到 `096`
  - `docs/checks/096-online-hospital-registration-system.md` 已新增
  - 本批 `001`-`096` 全部项目已验证完成
- `096` 项目结构与风险已确认：
  - 后端目录：`096-backend`
  - 前端目录：`096-frontend`
  - 默认后端端口：`8096`
  - 前端开发端口：`3096`
  - 正式 MySQL 数据库：`online_hospital_registration_db`
  - MySQL 账号密码：`root / 1234`
- `096` 最终复测结果：
  - `096-backend` 下执行 `mvn.cmd test`：通过，`Tests run: 1, Failures: 0, Errors: 0`
  - `096-frontend/npm.cmd run build`：通过，仅保留 Vite CJS API 和 chunk 体积提示
  - 后端 `18109` 默认 H2 环境真实启动抽测通过：匿名 `401`、公开公告/轮播/医生/排班/热门医生 `200`、三角色登录脱敏、患者访问用户管理越权 `403`、统计 `200`、挂号创建 `200`、他人支付 `403`、本人支付 `200`、医生完成 `200`、患者评价 `200`、登出后旧 token `401`
  - 后端 `18110` 使用 MySQL `root / 1234` 的 `mysql-verify` profile 真实启动抽测通过，覆盖同一业务链路
  - 前端 `3096` 页面响应和代理登录验证通过：首页 `200`、代理登录 `200`、角色 `ADMIN`、有 token 且无 `password` 字段
  - MySQL 临时库 `online_hospital_registration_096_verify` 已在验证后删除
  - `18109`、`18110`、`3096` 验证进程已清理

### Key Evidence
| Area | Evidence |
|------|----------|
| 后端测试 | `mvn.cmd test` 通过，新增 `OnlineHospitalRegistrationApplicationSmokeTest` |
| H2 真实链路 | HTTP 验证结果包含 `patientUserPage=403`、`otherPay=403`、`patientPay=200`、`doctorFinish=200`、`patientReview=200`、`afterLogoutCard=401` |
| MySQL 真实链路 | `online_hospital_registration_096_verify` 临时库，`root / 1234`，同 H2 链路通过 |
| 前端联调 | `3096` 首页 `200`，代理登录 `200`，有 token 且无 password |

### Problems Found / Fixed
| Problem | Fix |
|---------|-----|
| 原始默认环境依赖 MySQL/Redis 和 `8080`，不利于直接演示与自动化验证 | 默认 profile 切换为 H2 自举，新增 MySQL 正式与 `mysql-verify` profile，运行态 token 存储替代默认 Redis 依赖 |
| 前端端口/代理固定且请求头未补 Bearer | `vite.config.js` 改为 `3096` 并支持 `VITE_API_TARGET`，`request.js` 统一发送 `Bearer <token>` |
| 登录响应存在 password 泄露风险 | `SysUser.password` 增加 `@JsonIgnore`，登录与用户信息继续主动置空 |
| 全局异常 HTTP 状态不准确 | `GlobalExceptionHandler` 返回真实 `400/401/403/500` |
| H2 统计 SQL 与 MySQL 专属写法不兼容 | 修复 `value` 别名冲突，趋势统计改为通用日期聚合并由 Java 格式化 |
| 业务归属越权语义不清 | 他人支付、他人取消、非本人评价和医生完成非本人预约返回 `403` |

### Files Touched / Verified
- `docs/checks/096-online-hospital-registration-system.md`
- `docs/project-check-tracker.md`
- `progress.md`
- `task_plan.md`
- `096-backend/pom.xml`
- `096-backend/src/main/resources/application.yml`
- `096-backend/src/main/resources/application-mysql.yml`
- `096-backend/src/main/resources/application-mysql-verify.yml`
- `096-backend/src/main/resources/schema-h2.sql`
- `096-backend/src/main/resources/data-h2.sql`
- `096-backend/src/main/resources/schema-mysql.sql`
- `096-backend/src/main/resources/data-mysql.sql`
- `096-backend/src/main/resources/mapper/AppointmentRecordMapper.xml`
- `096-backend/src/main/java/com/hospital/common/GlobalExceptionHandler.java`
- `096-backend/src/main/java/com/hospital/config/JwtInterceptor.java`
- `096-backend/src/main/java/com/hospital/config/WebMvcConfig.java`
- `096-backend/src/main/java/com/hospital/entity/SysUser.java`
- `096-backend/src/main/java/com/hospital/service/AuthService.java`
- `096-backend/src/main/java/com/hospital/service/AppointmentService.java`
- `096-backend/src/main/java/com/hospital/service/OrderService.java`
- `096-backend/src/main/java/com/hospital/service/ReviewService.java`
- `096-backend/src/main/java/com/hospital/service/RuntimeStoreService.java`
- `096-backend/src/main/java/com/hospital/service/StatisticsService.java`
- `096-backend/src/test/java/com/hospital/OnlineHospitalRegistrationApplicationSmokeTest.java`
- `096-frontend/vite.config.js`
- `096-frontend/src/api/request.js`

## Session: 2026-05-05 新增项目 119/120

### Findings
- `119` 主题为设备备件寿命预测与维保决策，模块闭环为设备资产、备件目录、备件库存、入库记录、出库领用、使用记录、运行指标、故障记录、寿命预测、维保计划、采购申请、风险预警和操作日志。
- `120` 主题为数字孪生园区设备巡检，模块闭环为园区楼宇、孪生设备、巡检路线、巡检点位、巡检任务、巡检记录、缺陷报告、维修工单、传感数据、孪生模型、能耗监测、保养计划和操作日志。
- `119` 采用 MyBatis-Plus，`120` 采用 MyBatis 注解 SQL + PageHelper，符合新增项目交替技术路线。
- 两个项目均使用 MySQL `root / 1234`，Redis 登录态缓存，JWT 鉴权和 Vue3 前端。
- 静态验证确认 `119/120` 均为 14 张表、14 个实体、14 个 Mapper、16 个 Controller、17 个前端页面。
- 发现并修正初始化 SQL 中演示手机号位数过长的问题。

## Session: 2026-05-05 新增项目 121/122

### Findings
- `121` 主题为无人机巡检任务调度与缺陷上报，模块闭环为无人机设备、飞手档案、巡检区域、航线规划、巡检任务、飞行记录、缺陷报告、缺陷图片、整改工单、电池站点、维保记录、风险预警和操作日志。
- `122` 主题为智慧工地安全巡检与隐患整改，模块闭环为工地项目、施工班组、安全员档案、巡检计划、巡检任务、隐患上报、整改工单、验收记录、安全培训、设备检查、防护用品、风险预警和操作日志。
- `121` 采用 MyBatis-Plus，`122` 采用 MyBatis 注解 SQL + PageHelper，符合新增项目交替技术路线。
- 两个项目均使用 MySQL `root / 1234`，Redis 登录态缓存，JWT 鉴权和 Vue3 前端。
- 静态验证确认 `121/122` 均为 14 张表、14 个实体、14 个 Mapper、16 个 Controller、17 个前端页面。
- 首页统计来源已按业务语义调整为任务、缺陷、整改等核心模块计数。
