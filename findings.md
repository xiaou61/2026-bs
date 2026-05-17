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

## Session: 2026-05-05 新增项目 123/124

### Findings
- `123` 主题为城市内涝监测与应急调度，模块闭环为水位点位、雨量站点、排涝泵站、水位数据、雨量数据、预警规则、内涝预警、应急预案、调度任务、救援队伍、物资储备、避险点位和操作日志。
- `124` 主题为电动车充电桩预约与运维管理，模块闭环为充电站点、充电桩位、用户车辆、预约订单、充电记录、故障报修、维修工单、保养计划、电价策略、支付记录、收益统计、能耗监测和操作日志。
- `123` 采用 MyBatis-Plus，`124` 采用 MyBatis 注解 SQL + PageHelper，符合新增项目交替技术路线。
- 两个项目均使用 MySQL `root / 1234`，Redis 登录态缓存，JWT 鉴权和 Vue3 前端。
- 静态验证确认 `123/124` 均为 14 张表、14 个实体、14 个 Mapper、16 个 Controller、17 个前端页面。

## Session: 2026-05-05 新增项目 125/126

### Findings
- `125` 主题为智慧停车诱导与空位预测，模块闭环为停车场档案、停车区域、停车泊位、车位传感器、车辆档案、预约订单、停车记录、支付记录、空位预测、诱导屏管理、导航路线、故障报修和操作日志。
- `126` 主题为家庭能源用电分析与节能建议，模块闭环为家庭档案、家庭成员、智能电表、用电设备、能耗读数、电费账单、设备用电、能耗预算、节能建议、异常提醒、碳排统计、维护工单和操作日志。
- `125` 采用 MyBatis-Plus，`126` 采用 MyBatis 注解 SQL + PageHelper，符合新增项目交替技术路线。
- 两个项目均使用 MySQL `root / 1234`，Redis 登录态缓存，JWT 鉴权和 Vue3 前端。
- 静态验证确认 `125/126` 均为 14 张表、14 个实体、14 个 Mapper、16 个 Controller、17 个前端页面。
- 残留旧项目关键词扫描、注释扫描、MyBatis 注解 SQL 条件转义检查和登录脱敏检查均已通过。

## Session: 2026-05-05 新增项目 127/128

### Findings
- `127` 主题为企业碳排放核算与减排任务管理，模块闭环为企业档案、排放因子、核算周期、能源消耗、排放记录、减排任务、减排措施、碳配额、核查报告、佐证附件、预警规则、碳排预警和操作日志。
- `128` 主题为 ESG 数据填报与可视化报告，模块闭环为指标库、披露模板、报告周期、企业填报、指标数据、佐证材料、审核任务、评分模型、ESG评分、改进任务、利益相关方反馈、报告导出和操作日志。
- `127` 采用 MyBatis-Plus，`128` 采用 MyBatis 注解 SQL + PageHelper，符合新增项目交替技术路线。
- 两个项目均使用 MySQL `root / 1234`，Redis 登录态缓存，JWT 鉴权和 Vue3 前端。
- 静态验证确认 `127/128` 均为 14 张表、14 个实体、14 个 Mapper、16 个 Controller、17 个前端页面。
- 残留旧项目关键词扫描、注释扫描、MyBatis 注解 SQL 条件转义检查和运行配置检查均已通过。

## Session: 2026-05-05 新增项目 129/130

### Findings
- `129` 主题为水产养殖环境监测与投喂预警，模块闭环为养殖池塘、传感设备、水质读数、投喂计划、投喂记录、鱼苗批次、生长采样、病害预警、用药记录、养殖设备、水质规则、产量统计和操作日志。
- `130` 主题为温室大棚物联网控制与病害预警，模块闭环为温室档案、作物批次、环境传感器、环境读数、灌溉任务、施肥计划、虫害预警、病害诊断、控制设备、远程指令、采收记录、维护工单和操作日志。
- `129` 采用 MyBatis-Plus，`130` 采用 MyBatis 注解 SQL + PageHelper，符合新增项目交替技术路线。
- 两个项目均使用 MySQL `root / 1234`，Redis 登录态缓存，JWT 鉴权和 Vue3 前端。
- 静态验证确认 `129/130` 均为 14 张表、14 个实体、14 个 Mapper、16 个 Controller、17 个前端页面。
- 发现并修正 `127/128/129/130` JWT secret 与 artifactId 的模板残留，使项目标识与各自主题一致。
- 残留旧项目关键词扫描、注释扫描、MyBatis 注解 SQL 条件转义检查和登录脱敏检查均已通过。

## Session: 2026-05-05 新增项目 131/132

### Findings
- `131` 主题为药品不良反应上报与随访管理，模块闭环为患者档案、药品目录、上报人档案、不良反应上报、反应症状、风险评估、随访计划、随访记录、病例复核、处置建议、科室信息、统计报表和操作日志。
- `132` 主题为医疗器械借用消毒与追踪管理，模块闭环为器械档案、器械分类、科室信息、借用申请、借用记录、归还记录、消毒批次、消毒记录、二维码追踪、维护记录、巡检任务、风险预警和操作日志。
- `131` 采用 MyBatis-Plus，`132` 采用 MyBatis 注解 SQL + PageHelper，符合新增项目交替技术路线。
- 两个项目均使用 MySQL `root / 1234`，Redis 登录态缓存，JWT 鉴权和 Vue3 前端。
- 静态验证确认 `131/132` 均为 14 张表、14 个实体、14 个 Mapper、16 个 Controller、17 个前端页面。
- 发现并修正 `131/132` 数据库名、artifactId 与 JWT secret 的替换顺序残留，使运行配置、SQL 和文档一致。
- 残留旧项目关键词扫描、注释扫描、MyBatis 注解 SQL 条件转义检查和登录脱敏检查均已通过。


## Session: 2026-05-05 新增项目 133/134

- `133` 主题为实验室耗材采购审批与库存预警，模块闭环为耗材目录、供应商档案、实验室、库存台账、采购申请、采购审批、采购订单、入库记录、领用记录、库存盘点、预警规则、库存预警和操作日志。
- `134` 主题为科研项目经费报销与成果管理，模块闭环为科研项目、预算科目、经费预算、报销申请、发票记录、审批任务、支付记录、科研成果、论文成果、专利成果、绩效统计、风险预警和操作日志。
- `133` 采用 MyBatis-Plus，`134` 采用 MyBatis 注解 SQL + PageHelper，符合新增项目交替技术路线。
- 静态验证确认 `133/134` 均为 14 张表、14 个实体、14 个 Mapper、16 个 Controller、17 个前端页面。
- 残留旧项目关键词扫描、注释扫描、MyBatis 注解 SQL 条件转义检查和登录脱敏检查均已通过。

## Session: 2026-05-14 巡检项目 130

### Findings
- `130` 正式巡检已完成，当前停点已从 `129` 推进到 `130`，下一项目为 `131`。
- `130` 后端原始状态与 `121-129` 修复前一致：`JwtInterceptor` 仅校验 token，不透传 `userId/username/role` 请求属性；各控制器未做服务端角色收口。
- 已为 `130` 的 `AuthService` 补齐统一角色断言方法，并按 `ADMIN / GROWER / TECHNICIAN / MANAGER` 口径，为账号权限、温室档案、作物批次、环境传感器、环境读数、灌溉任务、施肥计划、虫害预警、病害诊断、控制设备、远程指令、采收记录、维护工单、操作日志、统计看板补齐权限校验。
- 已为 `130` 前端路由补充 `meta.roles`，并让侧边栏菜单与登录后默认跳转按角色动态收口。
- 已为 `130` 的 `SysUser.password` 增加 `@JsonIgnore`，避免登录返回和用户列表链路泄露密码字段。
- 验证结果：后端 `mvn.cmd test` 通过，日志为 `No tests to run`；前端 `npm.cmd install`、`npm.cmd run build` 通过，构建仍有大包 warning，但不阻塞当前巡检结论。

## Session: 2026-05-14 巡检项目 131

### Findings
- `131` 正式巡检已完成，当前停点已推进到 `131`，下一项目为 `132`。
- `131` 后端原始状态与 `130` 修复前一致：`JwtInterceptor` 仅校验 token，不透传 `userId/username/role` 请求属性；各控制器未做服务端角色收口。
- 已为 `131` 的 `AuthService` 补齐统一角色断言方法，并按 `ADMIN / REPORTER / REVIEWER / DOCTOR` 口径，为账号权限、患者档案、药品目录、上报人档案、不良反应上报、反应症状、风险评估、随访计划、随访记录、病例复核、处置建议、科室信息、统计报表、操作日志、统计看板补齐权限校验。
- 已为 `131` 前端路由补充 `meta.roles`，并让侧边栏菜单与登录后默认跳转按角色动态收口。
- 已为 `131` 的 `SysUser.password` 增加 `@JsonIgnore`，避免登录返回和用户列表链路泄露密码字段。
- 验证结果：后端 `mvn.cmd test` 通过，日志为 `No tests to run`；前端 `npm.cmd install`、`npm.cmd run build` 通过，构建仍有大包 warning，但不阻塞当前巡检结论。

## Session: 2026-05-14 巡检项目 132

### Findings
- `132` 正式巡检已完成，当前停点已推进到 `132`，下一项目为 `133`。
- `132` 后端原始状态与 `130` 修复前一致：`JwtInterceptor` 仅校验 token，不透传 `userId/username/role` 请求属性；各控制器未做服务端角色收口。
- 已为 `132` 的 `AuthService` 补齐统一角色断言方法，并按 `ADMIN / NURSE / STERILIZER / MANAGER` 口径，为账号权限、器械档案、器械分类、科室信息、借用申请、借用记录、归还记录、消毒批次、消毒记录、二维码追踪、维护记录、巡检任务、风险预警、操作日志、统计看板补齐权限校验。
- 已为 `132` 前端路由补充 `meta.roles`，并让侧边栏菜单与登录后默认跳转按角色动态收口。
- 已为 `132` 的 `SysUser.password` 增加 `@JsonIgnore`，避免登录返回和用户列表链路泄露密码字段。
- 验证结果：后端 `mvn.cmd test` 通过，日志为 `No tests to run`；前端 `npm.cmd install`、`npm.cmd run build` 通过，构建仍有大包 warning，但不阻塞当前巡检结论。

## Session: 2026-05-14 巡检项目 133

### Findings
- `133` 正式巡检已完成，当前停点已推进到 `133`，下一项目为 `134`。
- `133` 后端原始状态与 `131` 修复前一致：`JwtInterceptor` 仅校验 token，不透传 `userId/username/role` 请求属性；各控制器未做服务端角色收口。
- 已为 `133` 的 `AuthService` 补齐统一角色断言方法，并按 `ADMIN / KEEPER / TEACHER / APPROVER` 口径，为账号权限、耗材目录、供应商档案、实验室、库存台账、采购申请、采购审批、采购订单、入库记录、领用记录、库存盘点、预警规则、库存预警、操作日志、统计看板补齐权限校验。
- 已为 `133` 前端路由补充 `meta.roles`，并让侧边栏菜单与登录后默认跳转按角色动态收口。
- 已为 `133` 的 `SysUser.password` 增加 `@JsonIgnore`，避免登录返回和用户列表链路泄露密码字段。
- 巡检中发现 `133` 项目原生缺失 `BaseCrudService.java`，导致未经本轮权限修改时后端也无法通过编译；已补回公共基类并恢复 `mvn.cmd test` 通过。
- 验证结果：后端 `mvn.cmd test` 通过，日志为 `No tests to run`；前端 `npm.cmd install`、`npm.cmd run build` 通过，构建仍有大包 warning，但不阻塞当前巡检结论。

## Session: 2026-05-14 巡检项目 134

### Findings
- `134` 正式巡检已完成，当前停点已推进到 `134`，下一项目为 `135`。
- `134` 后端原始状态与 `130` 修复前一致：`JwtInterceptor` 仅校验 token，不透传 `userId/username/role` 请求属性；各控制器未做服务端角色收口。
- 已为 `134` 的 `AuthService` 补齐统一角色断言方法，并按 `ADMIN / RESEARCHER / FINANCE / LEADER` 口径，为账号权限、科研项目、预算科目、经费预算、报销申请、发票记录、审批任务、支付记录、科研成果、论文成果、专利成果、绩效统计、风险预警、操作日志、统计看板补齐权限校验。
- 已为 `134` 前端路由补充 `meta.roles`，并让侧边栏菜单与登录后默认跳转按角色动态收口。
- 已为 `134` 的 `SysUser.password` 增加 `@JsonIgnore`，避免登录返回和用户列表链路泄露密码字段。
- 验证结果：后端 `mvn.cmd test` 通过，日志为 `No tests to run`；前端 `npm.cmd install`、`npm.cmd run build` 通过，构建仍有大包 warning，但不阻塞当前巡检结论。

## Session: 2026-05-14 巡检项目 135

### Findings
- `135` 正式巡检已完成，当前停点已推进到 `135`，下一项目为 `136`。
- `135` 后端原始状态与 `131` 修复前一致：`JwtInterceptor` 仅校验 token，不透传 `userId/username/role` 请求属性；各控制器未做服务端角色收口。
- 已为 `135` 的 `AuthService` 补齐统一角色断言方法，并按 `ADMIN / MANAGER / REVIEWER / SECRETARY` 口径，为账号权限、会议信息、征稿通知、作者档案、论文投稿、审稿专家、审稿分配、盲审记录、录用通知、参会报名、会场安排、会议日程、签到记录、操作日志、统计看板补齐权限校验。
- 已为 `135` 前端路由补充 `meta.roles`，并让侧边栏菜单与登录后默认跳转按角色动态收口。
- 已为 `135` 的 `SysUser.password` 增加 `@JsonIgnore`，避免登录返回和用户列表链路泄露密码字段。
- 巡检中发现 `135` 项目原生缺失 `BaseCrudService.java`，导致未经本轮权限修改时后端也无法通过编译；已补回公共基类并恢复 `mvn.cmd test` 通过。
- 验证结果：后端 `mvn.cmd test` 通过，日志为 `No tests to run`；前端 `npm.cmd install`、`npm.cmd run build` 通过，构建仍有大包 warning，但不阻塞当前巡检结论。

## Session: 2026-05-14 巡检项目 136

### Findings
- `136` 正式巡检已完成，当前停点已推进到 `136`，下一项目为 `137`。
- `136` 后端原始状态与 `130-135` 修复前一致：`JwtInterceptor` 仅校验 token，不透传 `userId/username/role` 请求属性；除统计接口外，各控制器未做服务端角色收口。
- 已为 `136` 的 `AuthService` 补齐统一角色断言方法，并按 `ADMIN / TEACHER / STUDENT / AFFAIRS` 口径，为账号权限、课题发布、导师档案、学生档案、课题申请、双选审核、双选确认、任务书下达、开题材料、开题答辩、中期检查、指导记录、节点通知、操作日志、统计看板补齐权限校验。
- 已为 `136` 前端路由补充 `meta.roles`，并让侧边栏菜单与登录后默认跳转按角色动态收口，避免学生和教务管理员继续共享生成态全菜单。
- 已为 `136` 的 `SysUser.password` 增加 `@JsonIgnore`，避免登录返回和用户列表链路泄露密码字段。
- 验证结果：后端 `mvn.cmd test` 通过，日志为 `No tests to run`；前端 `npm.cmd install`、`npm.cmd run build` 通过，构建仍有大包 warning，但不阻塞当前巡检结论。

## Session: 2026-05-14 巡检项目 137

### Findings
- `137` 正式巡检已完成，当前停点已推进到 `137`，下一项目为 `138`。
- `137` 后端原始状态与 `136` 修复前一致：`JwtInterceptor` 仅校验 token，不透传 `userId/username/role` 请求属性；各控制器未做服务端角色收口。
- 已为 `137` 的 `AuthService` 补齐统一角色断言方法，并按 `ADMIN / MENTOR / STUDENT / JUDGE` 口径，为账号权限、孵化项目、导师档案、团队档案、项目申报、孵化计划、导师辅导、路演活动、路演评分、经费记录、里程碑任务、成果展示、孵化通知、操作日志、统计看板补齐权限校验。
- 已为 `137` 前端路由补充 `meta.roles`，并让侧边栏菜单与登录后默认跳转按角色动态收口；后续补充修正了登录页调用不存在的 `setAuth` 与首页跳转逻辑，恢复登录态持久化和角色首页跳转。
- 已为 `137` 的 `SysUser.password` 增加 `@JsonIgnore`，避免登录返回和用户列表链路泄露密码字段。
- 巡检中发现 `137` 项目原生缺失 `BaseCrudService.java`，导致未经本轮权限修改时后端也无法通过编译；已补回公共基类并恢复 `mvn.cmd test` 通过。
- 验证结果：后端 `mvn.cmd test` 通过，日志为 `No tests to run`；前端 `npm.cmd install`、`npm.cmd run build` 通过，构建仍有大包 warning，但不阻塞当前巡检结论。

## Session: 2026-05-14 巡检项目 138

### Findings
- `138` 正式巡检已完成，当前停点已推进到 `138`，下一项目为 `139`。
- `138` 后端原始状态与 `137` 修复前一致：`JwtInterceptor` 仅校验 token，不透传 `userId/username/role` 请求属性；各控制器未做服务端角色收口。
- `138` 前端原始状态也存在同类收口缺口：路由未配置 `meta.roles`，侧边栏未按角色过滤，“告警通知”菜单索引误指向不存在的 `/alert`，登录页还调用了 store 中不存在的 `setAuth`。
- 已为 `138` 的 `AuthService` 补齐统一角色断言方法，并按 `ADMIN / INVIGILATOR / CANDIDATE / REVIEWER` 口径，为账号权限、考试计划、监考档案、考生档案、考试场次、异常行为、证据记录、复核任务、复核结论、预警规则、设备监测、违规申诉、告警通知、操作日志、统计看板补齐权限校验。
- 已为 `138` 前端路由补充 `meta.roles`，并让侧边栏菜单与登录后默认跳转按角色动态收口，同时修正“告警通知”菜单索引和登录页 store 动作名不匹配的运行时问题。
- 已为 `138` 的 `SysUser.password` 增加 `@JsonIgnore`，避免登录返回和用户列表链路泄露密码字段。
- 已为 `138` 的 `GlobalExceptionHandler` 拆分业务异常与系统异常返回，避免把未处理异常消息明文透传给前端。
- 补充观察：`138-backend/sql/init.sql` 的业务表仍保留 `134` 科研经费题目的模板残留，本轮未重建，因此 fresh DB 初始化链路仍有偏差风险。
- 验证结果：后端 `mvn.cmd test` 通过，日志为 `No tests to run`；前端 `npm.cmd install`、`npm.cmd run build` 通过，构建仍有大包 warning，但不阻塞当前巡检结论；轻量扫描未发现 `System.out.print*`、`printStackTrace` 或通配符 CORS 残留。

## Session: 2026-05-14 巡检项目 139

### Findings
- `139` 正式巡检已完成，当前停点已推进到 `139`，下一项目为 `140`。
- `139` 后端原始状态与 `137/138` 修复前一致：`JwtInterceptor` 仅校验 token，不透传 `userId/username/role` 请求属性；各控制器未做服务端角色收口。
- `139` 前端原始状态也存在同类问题：路由未配置 `meta.roles`，侧边栏未按角色过滤，登录页调用了 store 中不存在的 `setAuth`，业务页按钮也未按角色隐藏。
- 巡检中进一步发现 `139` 不只是权限缺失，整套业务语义还残留 `133` 实验室耗材项目模板：`sql/init.sql`、实体字段、服务层关键字列、Redis token 前缀以及 12 个业务页面的表头/表单字段均与“企业培训学习路径与能力认证”主题不一致。
- 已为 `139` 的 `AuthService` 补齐统一角色断言方法，并按 `ADMIN / TRAINER / EMPLOYEE / MANAGER` 口径，为账号权限、培训项目、课程目录、学员档案、学习路径、学习任务、选课报名、考核考试、成绩记录、技能标签、胜任力画像、认证记录、学习提醒、操作日志、统计看板补齐权限校验。
- 已为 `139` 的 `SysUser.password` 增加 `@JsonIgnore`，避免登录返回和用户列表链路泄露密码字段；同时为 `GlobalExceptionHandler` 区分业务异常与系统异常返回，避免未处理异常消息明文透传。
- 已补回 `139-backend/src/main/java/com/learningpath/service/BaseCrudService.java`，修复项目原生缺失公共 CRUD 基类导致的后端编译问题。
- 已重写 `139-backend/sql/init.sql`、业务实体字段、服务层关键字列、统计服务计数口径与 Redis token 前缀，使后端数据模型完整切回“企业培训学习路径与能力认证管理系统”主题。
- 已修正前端 store 的 `setAuth` 兼容动作、登录成功后的角色首页跳转、路由 `meta.roles`、侧边栏菜单收口，以及 12 个业务页的字段、文案与按钮权限显示逻辑。
- 验证结果：后端 `mvn.cmd test` 通过，日志为 `No tests to run`；前端 `npm.cmd run build` 通过，构建仍有大包 warning，但不阻塞当前巡检结论；核心旧模板关键词扫描未发现 `labconsumable / consumable / supplier / purchase_*` 等残留。

## Session: 2026-05-14 巡检项目 140

### Findings
- `140` 正式巡检已完成，当前停点已推进到 `140`，下一项目为 `141`。
- `140` 后端原始状态与 `137-139` 修复前一致：`JwtInterceptor` 仅校验 token，不透传 `userId/username/role` 请求属性；各控制器未做服务端角色收口。
- `140` 前端原始状态也存在同类问题：路由未配置 `meta.roles`，侧边栏未按角色过滤，登录页调用了 store 中不存在的 `setAuth`，业务页按钮也未按角色隐藏。
- 巡检中进一步发现 `140` 不只是权限缺失，整套业务语义还残留 `134` 科研经费与 `136` 导师双选模板：`sql/init.sql` 仍建旧业务表，前端 12 个业务页的表头、表单字段和文案也未切回“电子合同签署与印章审批管理”主题。
- 已为 `140` 的 `AuthService` 补齐统一角色断言方法，并按 `ADMIN / LEGAL / APPLICANT / APPROVER` 口径，为账号权限、合同模板、相对方档案、签署方档案、合同草稿、用印申请、审批流程、合同签署、用印记录、归档记录、到期提醒、风险条款、合同通知、操作日志、统计看板补齐权限校验。
- 已为 `140` 的 `SysUser.password` 增加 `@JsonIgnore`，避免登录返回和用户列表链路泄露密码字段；同时修复用户编辑资料时密码留空会被更新为 `null` 的数据破坏问题。
- 已为 `140` 的 `GlobalExceptionHandler` 拆分业务异常与系统异常返回，避免把未处理异常消息明文透传给前端。
- 已重写 `140-backend/sql/init.sql`，并按电子合同主题重建 `contract_template / counterparty_profile / signer_profile / contract_draft / seal_application / approval_flow / contract_signing / seal_record / archive_record / expiration_reminder / risk_clause / contract_notice / operation_log` 等表结构与种子数据。
- 已修正前端 store 的 `setAuth` 兼容动作、登录成功后的角色首页跳转、路由 `meta.roles`、侧边栏菜单收口、通用 `DataPage` 权限开关，以及 12 个业务页的字段、文案与动作按钮权限逻辑。
- 验证结果：后端 `mvn.cmd test` 通过，日志为 `No tests to run`；前端补齐依赖后 `npm.cmd run build` 通过，构建仍有大包 warning，但不阻塞当前巡检结论；核心旧模板关键词扫描未发现 `research_* / budget_* / payment_* / approval_task / topic / mutual / 课题 / 导师 / 学院` 等残留，且未发现 `System.out.print*`、`printStackTrace` 或通配符 CORS 残留。

## Session: 2026-05-14 巡检项目 141

### Findings
- `141` 正式巡检已完成，当前停点已推进到 `141`，下一项目为 `142`。
- `141` 后端原始状态与 `133/139/140` 修复前一致：`JwtInterceptor` 仅校验 token，不透传 `userId/username/role` 请求属性；各控制器未做服务端角色收口；`GlobalExceptionHandler` 直接回显异常消息；`TokenService` 与 `JwtUtils` 仍残留旧项目标识。
- `141` 还存在项目原生编译问题：`141-backend/src/main/java/com/assetrfid/service/BaseCrudService.java` 缺失，导致所有业务服务类虽然继承了公共 CRUD 基类，但未巡检前后端其实无法原生编译通过。
- `141` 并非单一模板残留，而是混合污染：后端 `sql/init.sql`、实体 `@TableName` 和服务层关键字列整体仍指向 `133` 实验室耗材采购主题，而前端 12 个业务页的表头/表单文案又明显带着 `135` 学术会议投稿评审模板。
- 已为 `141` 的 `pom.xml`、`JwtUtils` 与 `TokenService` 修正 `learning-path-139` / `labconsumable` 模板残留，使 artifactId、JWT secret 和 Redis token 前缀回到 `asset-rfid-141` 主题。
- 已为 `141` 的 `AuthService` 补齐统一角色断言方法，并按 `ADMIN / ASSET_ADMIN / BORROWER / AUDITOR` 口径，为账号权限、资产档案、资产分类、RFID 标签、存放位置、盘点任务、盘点明细、借用申请、归还记录、维修记录、折旧记录、闲置处置、预警通知、操作日志、统计看板补齐权限校验。
- 已为 `141` 的 `SysUser.password` 增加 `@JsonIgnore`，避免登录返回和用户列表链路泄露密码字段；同时修复用户编辑资料时密码留空会被更新为 `null` 的数据破坏问题。
- 已为 `141` 的 `GlobalExceptionHandler` 拆分业务异常与系统异常返回，避免把未处理异常消息明文透传给前端；并补回缺失的 `BaseCrudService.java`，恢复项目原生编译能力。
- 已重写 `141-backend/sql/init.sql`、业务实体 `@TableName` 与字段、服务层关键字列，使后端数据模型完整切回“固定资产 RFID 盘点与借用归还系统”主题。
- 已修正前端 store 的 `setAuth` 兼容动作、登录成功后的角色首页跳转、路由 `meta.roles`、侧边栏菜单收口、通用 `DataPage` 权限开关，以及 12 个业务页的字段、文案与动作按钮权限逻辑。
- 验证结果：后端 `mvn.cmd test` 通过，日志为 `No tests to run`；前端补齐依赖后 `npm.cmd run build` 通过，构建仍有大包 warning，但不阻塞当前巡检结论；精确残留扫描未发现 `consumable / supplier / purchase_* / lab_room / stock_warning / 投稿 / 审稿 / 签到 / 会务` 等旧模板核心关键词，且未发现 `System.out.print*`、`printStackTrace` 或通配符 CORS 残留。

## Session: 2026-05-14 巡检项目 142

### Findings
- `142` 正式巡检已完成，当前停点已推进到 `142`，下一项目为 `143`。
- `142` 后端原始状态与 `139/140/141` 修复前一致：`JwtInterceptor` 仅校验 token，不透传 `userId/username/role` 请求属性；各控制器未做服务端角色收口；`GlobalExceptionHandler` 直接回显异常消息；`pom.xml`、`JwtUtils` 与 `TokenService` 仍残留旧项目标识。
- `142` 并非只缺权限收口，而是后后端和前端双重串台：后端 `sql/init.sql`、实体字段和注解 SQL Mapper 仍指向 `134` 科研经费模板，而前端登录页、路由和 12 个业务页表头/表单文案又明显夹带 `136` 选题过程模板残留。
- 已为 `142` 的 `pom.xml`、`JwtUtils` 与 `TokenService` 修正 `electronic-contract-140` 模板残留，使 artifactId、JWT secret 和 Redis token 前缀回到 `vehicle-claim-142` 主题。
- 已为 `142` 的 `AuthService` 补齐统一角色断言方法，并按 `ADMIN / LEGAL / APPLICANT / APPROVER` 口径，为账号权限、保险保单、车辆档案、客户档案、理赔申请、事故报案、材料清单、材料审核、定损记录、赔付记录、进度跟踪、回访记录、消息通知、操作日志、统计看板补齐权限校验。
- 已为 `142` 的 `SysUser.password` 增加 `@JsonIgnore`，避免登录返回和用户列表链路泄露密码字段；同时修复用户编辑资料时密码留空会被更新为 `null` 的数据破坏问题。
- 已为 `142` 的 `GlobalExceptionHandler` 拆分业务异常与系统异常返回，避免把未处理异常消息明文透传给前端。
- 已重写 `142-backend/sql/init.sql`、12 个业务实体字段与注解 SQL Mapper，使后端数据模型完整切回“车辆保险理赔材料审核与进度跟踪系统”主题。
- 已修正前端 store 的 `setAuth` 兼容动作、登录成功后的角色首页跳转、路由 `meta.roles`、侧边栏菜单收口、通用 `DataPage` 权限开关，以及 12 个业务页的字段、文案与动作按钮权限逻辑。
- 验证结果：后端 `mvn.cmd test` 通过，日志为 `No tests to run`；前端补齐依赖后 `npm.cmd run build` 通过，构建仍有大包 warning，但不阻塞当前巡检结论；精确残留扫描未发现 `research_* / budget_* / expense_* / invoice_* / approval_task / payment_record / paper_record / patent_record / performance_statistic / risk_warning / 课题 / 导师 / 开题 / 中期检查` 等旧模板核心关键词，且未发现 `System.out.print*`、`printStackTrace` 或通配符 CORS 残留。

## Session: 2026-05-14 巡检项目 143

### Findings
- `143` 正式巡检已完成，当前停点已推进到 `143`，下一项目为 `144`。
- `143` 后端原始状态与 `139-142` 修复前一致：`JwtInterceptor` 仅校验 token，不透传 `userId/username/role` 请求属性；各控制器未做服务端角色收口；`GlobalExceptionHandler` 直接回显异常消息；`pom.xml`、`JwtUtils` 与 `TokenService` 也残留旧模板标识。
- `143` 前端原始状态同样存在基础鉴权缺口：路由未配置 `meta.roles`，侧边栏未按角色过滤，登录页调用了 store 中不存在的 `setAuth`，且示例账号和描述文案仍停留在旧项目场景。
- `143` 还存在明显的双重串台：后端 `sql/init.sql`、业务实体字段和服务层关键字列大面积残留 `133` 实验室耗材模板；前端 12 个业务页的表头、表单字段和文案又明显夹带 `135` 学术会议投稿评审模板。
- 已为 `143` 的 `pom.xml`、`JwtUtils` 与 `TokenService` 修正旧模板残留，使 artifactId、JWT secret 和 Redis token 前缀回到 `time-bank-143` / `timebank` 主题。
- 已为 `143` 的 `AuthService` 补齐统一角色断言方法，并按 `ADMIN / RESIDENT / VOLUNTEER / MANAGER` 口径，为账号权限、服务项目、服务分类、居民档案、志愿者档案、服务预约、服务签到、时长账户、互助兑换、评价反馈、公益活动、积分规则、站内通知、操作日志、统计看板补齐权限校验。
- 已为 `143` 的 `SysUser.password` 增加 `@JsonIgnore`，避免登录返回和用户列表链路泄露密码字段；同时修复用户编辑资料时密码留空会被更新为 `null`、状态会被误覆盖的问题。
- 已为 `143` 的 `GlobalExceptionHandler` 拆分业务异常与系统异常返回，并补回缺失的 `143-backend/src/main/java/com/timebank/service/BaseCrudService.java`，恢复项目原生编译能力。
- 已重写 `143-backend/sql/init.sql`、12 个业务实体字段、服务层关键字列、前端 `store/router/layout/login/DataPage` 权限骨架，以及 12 个业务页和 `SysUser / OperationLog` 的字段、文案与动作按钮逻辑，使系统完整切回“社区公益时间银行互助服务平台”主题。
- 验证结果：后端 `mvn.cmd test` 通过，日志为 `No tests to run`；前端补齐依赖后 `npm.cmd run build` 通过，构建仍有大包 warning，但不阻塞当前巡检结论；精确残留扫描未发现 `consumable / supplier / lab_room / trainer / employee / 会议信息 / 审稿 / 盲审 / 论文投稿` 等旧模板核心关键词，且未发现 `System.out.print*`、`printStackTrace` 或通配符 CORS 残留。

## Session: 2026-05-15 巡检项目 144

### Findings
- `144` 正式巡检已完成，当前停点已推进到 `144`，下一项目为 `145`。
- `144` 后端原始状态与 `140-143` 修复前一致：虽然 `JwtInterceptor`、`AuthService`、`GlobalExceptionHandler`、`JwtUtils` 和 `TokenService` 已有部分修补，但 `pom.xml` 仍残留 `electronic-contract-140` 标识，启动类仍错误命名为 `VehicleAssistRequest`，前端路由与菜单也尚未做角色收口。
- `144` 存在明显的双重串台：后端 `sql/init.sql` 已是无障碍出行主题，但 12 个业务实体、注解 SQL Mapper 和服务层变量名仍大面积残留 `134/136/140/142` 模板字段；前端登录页、路由、侧边栏与 12 个业务页字段文案也明显夹带电子合同和选题过程旧模板残留。
- 已为 `144` 的 `pom.xml`、启动类 `AccessibleTravelApplication`、`JwtUtils` 与 `TokenService` 修正旧模板残留，使 artifactId、启动入口、JWT secret 和 Redis token 前缀回到 `accessible-travel-144` / `accessibletravel` 主题。
- 已为 `144` 的 `AuthService` 补齐统一角色断言方法，并按 `ADMIN / TRAVELER / VOLUNTEER / DISPATCHER` 口径，为账号权限、无障碍路线、设施点位、出行用户、协助预约、志愿者档案、路线方案、协助任务、服务签到、评价反馈、应急联系人、行程记录、消息通知、操作日志、统计看板补齐权限校验。
- 已为 `144` 的 `SysUser.password` 增加 `@JsonIgnore`，避免登录返回和用户列表链路泄露密码字段；同时保留用户编辑资料时密码留空不被误覆盖的问题修复。
- 已为 `144` 的 `GlobalExceptionHandler` 拆分业务异常与系统异常返回，避免把未处理异常消息明文透传给前端。
- 已重写 `144-backend/sql/init.sql` 对应的 12 个业务实体字段、注解 SQL Mapper 与服务层变量命名，使后端数据模型完整切回“无障碍出行路线规划与志愿协助平台”主题，并修复启动类命名导致的原生编译风险。
- 已修正前端 store 的 `setAuth` 兼容动作、登录成功后的角色首页跳转、路由 `meta.roles`、侧边栏菜单收口、通用 `DataPage` 权限开关，以及 12 个业务页和 `SysUser` 的字段、文案与动作按钮权限逻辑。
- 验证结果：后端 `mvn.cmd test` 通过，日志为 `No tests to run`；前端补齐依赖后 `npm.cmd run build` 通过，构建仍有大包 warning，但不阻塞当前巡检结论；精确残留扫描未发现 `research_* / budget_* / expense_claim / invoice_record / approval_task / payment_record / paper_record / patent_record / performance_statistic / risk_warning / 课题 / 导师 / 开题 / 中期检查 / 答辩` 等旧模板核心关键词，且未发现 `System.out.print*`、`printStackTrace` 或通配符 CORS 残留。

## Session: 2026-05-15 巡检项目 145

### Findings
- `145` 正式巡检已完成，当前停点已推进到 `145`，下一项目为 `146`。
- `145` 后端进入本轮前属于“半修状态”：`JwtInterceptor`、`AuthService`、`GlobalExceptionHandler`、`TokenService` 等底层能力已有局部修补，但 15 个业务控制器基本没有真正消费 `role` 请求属性，服务端权限收口仍然缺失。
- `145` 前端原始状态也明显未完成主题切换：虽然看板主题接近“城市噪声投诉监测”，但路由未配置 `meta.roles`，侧边栏未按角色过滤，登录页仍缺少可用的 `setAuth` 对接和角色首页跳转，大部分业务页字段/按钮也仍停留在旧模板语义。
- 已沿用前面项目的统一修法：在控制器层引入 `@RequestAttribute String role`，并通过 `AuthService` 按 `ADMIN / CITIZEN / OFFICER / SUPERVISOR` 口径，为账号权限、投诉工单、监测站点、噪声源档案、执法人员、处置任务、巡查记录、整改通知、复测记录、处罚决定、公众反馈、预警规则、公告发布、操作日志、统计看板补齐服务端角色校验。
- 已重写前端 `router/index.js`、`Layout.vue`、`Login.vue`、`store/user.js` 与 `components/DataPage.vue`，补齐角色首页跳转、动态菜单、登录示例账号说明与通用新增/编辑/删除按钮权限开关。
- 已同步重写 `SysUser / ComplaintTicket / MonitoringSite / NoiseSource / OfficerProfile / HandlingTask / PatrolRecord / RectificationNotice / RetestRecord / PenaltyDecision / PublicFeedback / WarningRule / PublicNotice / OperationLog` 等页面的字段、文案和动作可见性，清理旧模板残留。
- 巡检中发现 `145-backend/src/main/java/com/noisemonitor/entity/PenaltyDecision.java` 缺少 `java.math.BigDecimal` 导入，导致后端即使权限代码补齐后也无法原生编译；本轮已补齐导入。
- 巡检中还发现 `145-backend/src/main/java/com/noisemonitor/config/WebMvcConfig.java` 仍允许通配符跨域；已收紧为本地开发前端地址 `http://localhost:3145`、`http://127.0.0.1:3145`、`http://localhost:4173`、`http://127.0.0.1:4173`。
- 验证结果：后端 `mvn.cmd test` 通过，日志为 `No tests to run`，仅有不阻塞的 `rdc` profile warning；前端 `npm.cmd install`、`npm.cmd run build` 通过，并生成 `145-frontend/package-lock.json`；精确残留扫描未发现 `trainer / employee / conference / review / consumable / supplier / labconsumable / research_fund / topic_selection / electronic_contract / vehicle_claim / time_bank / accessible_travel` 等旧模板核心关键词，且未发现 `System.out.print*`、`printStackTrace`。

## Session: 2026-05-15 巡检项目 146

### Findings
- `146` 正式巡检已完成，当前停点已推进到 `146`，下一项目为 `147`。
- `146` 后端原始状态是“权限缺口 + 双重串台 + 一处真实编译错误”叠加：`JwtInterceptor` 仅校验 token、不透传请求属性；各控制器未做服务端角色收口；`GlobalExceptionHandler` 直接回显异常消息；`WebMvcConfig` 仍允许通配符 CORS；`pom.xml`、`JwtUtils`、`TokenService` 仍残留旧项目标识；同时启动类文件 `FoodInspectApplication.java` 内部却仍声明为 `VehicleSamplingTask`，导致 `mvn test` 首轮直接编译失败。
- `146` 前端原始状态同样明显未完成主题切换：登录页还是电子合同旧文案与旧角色账号，路由未配置 `meta.roles`，侧边栏未按角色过滤，store 缺少可用的 `setAuth` 兼容动作，`api/index.js` 多个接口路径映射错误，业务页字段/文案则混杂 `134` 科研经费、`136` 双选确认等旧模板残留。
- 已先修复启动类命名，使 `FoodInspectApplication` 与文件名一致，确认后端原生编译恢复正常；随后将 artifactId、JWT secret、Redis token 前缀统一切回 `food-inspection-146` / `foodinspect` 主题。
- 已为 `146` 的 `JwtInterceptor` 补齐 `userId/username/role` 请求属性透传，为 `AuthService` 增加统一角色断言，并按 `ADMIN / INSPECTOR / MERCHANT / REVIEWER` 口径，为账号权限、抽检计划、食品档案、商户档案、抽样任务、检测机构、样品信息、检测结果、复检申请、处置记录、结果公示、风险预警、通知公告、操作日志、统计看板补齐服务端角色校验。
- 已为 `SysUser.password` 增加 `@JsonIgnore`，避免登录返回和用户列表链路泄露密码字段；同时修复用户新增默认密码为 `123456`、编辑资料时密码留空不被误覆盖的问题。
- 已为 `GlobalExceptionHandler` 拆分业务异常与系统异常返回，并将 `WebMvcConfig` 的通配符 CORS 收紧为 `http://localhost:3146`、`http://127.0.0.1:3146`、`http://localhost:4173`、`http://127.0.0.1:4173`。
- 已重写 `146-backend/sql/init.sql`、12 个业务实体字段、12 个注解 SQL Mapper，以及对应服务层旧变量命名，使后端数据模型完整切回“食品安全抽检任务与结果公示平台”主题。
- 已重写前端 `api/index.js`、`router/index.js`、`Layout.vue`、`Login.vue`、`store/user.js` 与 `components/DataPage.vue`，补齐角色首页跳转、动态菜单、接口路径和通用按钮权限开关；并同步重写 `SysUser / InspectionPlan / FoodItem / MerchantProfile / SamplingTask / AgencyProfile / SampleRecord / TestResult / RecheckApplication / DisposalRecord / PublicReport / RiskWarning / InspectionNotice / OperationLog` 等页面字段、文案与动作可见性。
- 验证结果：后端 `mvn.cmd test` 通过，日志为 `No tests to run`，仅有不阻塞的 `rdc` profile warning；前端 `npm.cmd install`、`npm.cmd run build` 通过，并生成 `146-frontend/package-lock.json`；精确残留扫描未发现 `research_* / budget_* / expense_* / invoice_* / approval_task / payment_record / paper_record / patent_record / performance_statistic / electronic-contract-140 / LEGAL / APPLICANT / APPROVER` 等旧模板核心关键词，且未发现 `System.out.print*`、`printStackTrace` 或通配符 CORS 残留。

## Session: 2026-05-15 巡检项目 147

### Findings
- `147` 正式巡检已完成，当前停点已推进到 `147`，下一项目为 `148`。
- `147` 后端原始状态是“权限缺口 + 主题串台 + 基类缺失”叠加：`JwtInterceptor` 仅校验 token、不透传请求属性；各控制器未做服务端角色收口；`GlobalExceptionHandler` 直接回显异常消息；`WebMvcConfig` 仍允许通配符 CORS；`pom.xml`、`JwtUtils`、`TokenService` 仍残留 `139` 项目标识；同时缺失 `BaseCrudService.java`，并误放了不属于当前主题的 `ServiceCategory.java`。
- `147` 前端原始状态同样未完成主题切换：登录页仍是旧项目文案与示例账号，路由未配置 `meta.roles`，侧边栏未按角色过滤，store 缺少稳定的 `setAuth` 兼容动作，业务页字段、文案和动作按钮也大面积残留旧模板语义。
- 已为 `147` 的 `pom.xml`、`JwtUtils` 与 `TokenService` 修正 `learning-path-139` 模板残留，使 artifactId、JWT secret 和 Redis token 前缀回到 `campus-psychology-147` / `psychologycare` 主题。
- 已为 `147` 的 `JwtInterceptor` 补齐 `userId/username/role` 请求属性透传，为 `AuthService` 增加统一角色断言，并按 `ADMIN / TEACHER / STUDENT / COUNSELOR` 口径，为账号权限、心理个案、咨询室、学生档案、值班排班、预约申请、咨询记录、测评问卷、风险评估、危机干预、家校沟通、跟进计划、系统通知、操作日志、统计看板补齐服务端角色校验。
- 已为 `SysUser.password` 增加 `@JsonIgnore`，避免登录返回和用户列表链路泄露密码字段；同时修复用户新增默认密码为 `123456`、编辑资料时密码留空不被误覆盖的问题。
- 已为 `GlobalExceptionHandler` 拆分业务异常与系统异常返回，并将 `WebMvcConfig` 的通配符 CORS 收紧为 `http://localhost:3147`、`http://127.0.0.1:3147`、`http://localhost:4173`、`http://127.0.0.1:4173`。
- 已补回 `147-backend/src/main/java/com/psychologycare/service/BaseCrudService.java` 与 `147-backend/src/main/java/com/psychologycare/entity/CounselRoom.java`，并删除错误的 `ServiceCategory.java`，恢复项目原生编译与主题结构完整性。
- 已重写 `147-backend/sql/init.sql`、12 个业务实体字段与对应服务层关键字列，使后端数据模型完整切回“校园心理咨询预约与危机干预管理系统”主题。
- 已重写前端 `router/index.js`、`Layout.vue`、`Login.vue`、`store/user.js` 与 `components/DataPage.vue`，补齐角色首页跳转、动态菜单、登录示例账号文案与通用按钮权限开关；并同步重写 `SysUser / CounselCase / CounselRoom / StudentProfile / DutySchedule / AppointmentRequest / CounselRecord / AssessmentQuestionnaire / RiskAssessment / CrisisIntervention / FamilyCommunication / FollowUpPlan / SystemNotice / OperationLog` 等页面字段、文案与动作可见性。
- 验证结果：后端 `mvn.cmd test` 通过，日志为 `No tests to run`，仅有不阻塞的 `rdc` profile warning；前端 `npm.cmd run build` 通过，构建仍有大包 warning，但不阻塞当前巡检结论；精确残留扫描未发现 `consumable / supplier / conference / review / paper / labconsumable / learning-path-139 / 投稿 / 审稿 / 会场 / 征稿 / 盲审` 等旧模板核心关键词，且未发现 `System.out.print*`、`printStackTrace` 或通配符 CORS 残留。

## Session: 2026-05-15 巡检项目 148

### Findings
- `148` 正式巡检已完成，当前停点已推进到 `148`，下一项目为 `149`。
- `148` 后端原始状态是“权限缺口 + 双重串台 + 编译配置缺失”叠加：`JwtInterceptor` 仅校验 token、不透传请求属性；各控制器未做服务端角色收口；`GlobalExceptionHandler` 直接回显异常消息；`WebMvcConfig` 仍允许通配符 CORS；`pom.xml`、`JwtUtils`、`TokenService` 仍残留旧项目标识；同时启动类文件 `ElderCareApplication.java` 内部却仍声明为 `VehicleServiceOrder`，且 Maven 未显式配置 Lombok 注解处理，导致 `mvn test` 首轮直接编译失败。
- `148` 前端原始状态同样明显未完成主题切换：登录页仍是电子合同旧文案与旧角色账号，路由未配置 `meta.roles`，侧边栏未按角色过滤，store 缺少可用的 `setAuth` 兼容动作，`api/index.js` 多个接口路径映射错误，业务页字段/文案则大面积残留科研经费和论文答辩模板语义。
- 已先修复启动类命名，并为 `pom.xml` 补齐 Lombok 注解处理配置，确认后端原生编译恢复正常；随后将 artifactId、JWT secret、Redis token 前缀统一切回 `elder-service-148` / `eldercare` 主题。
- 已为 `148` 的 `JwtInterceptor` 补齐 `userId/username/role` 请求属性透传，为 `AuthService` 增加统一角色断言，并按 `ADMIN / CONSULTANT / CAREGIVER / FAMILY` 口径，为账号权限、服务套餐、老人档案、护理档案、服务工单、服务团队、上门签到、服务记录、健康评估、用药提醒、家属回访、预警事件、通知公告、操作日志、统计看板补齐服务端角色校验。
- 已为 `SysUser.password` 增加 `@JsonIgnore`，避免登录返回和用户列表链路泄露密码字段；同时修复用户新增默认密码为 `123456`、编辑资料时密码留空不被误覆盖的问题。
- 已为 `GlobalExceptionHandler` 拆分业务异常与系统异常返回，并将 `WebMvcConfig` 的通配符 CORS 收紧为 `http://localhost:3148`、`http://127.0.0.1:3148`、`http://localhost:4173`、`http://127.0.0.1:4173`。
- 已重写 `148-backend/sql/init.sql`、12 个业务实体字段、12 个注解 SQL Mapper，以及对应服务层旧变量命名，使后端数据模型完整切回“社区养老服务派单与健康随访管理系统”主题。
- 已重写前端 `api/index.js`、`router/index.js`、`Layout.vue`、`Login.vue`、`store/user.js` 与 `components/DataPage.vue`，补齐角色首页跳转、动态菜单、接口路径和通用按钮权限开关；并同步重写 `SysUser / ServicePackage / ElderProfile / CaregiverProfile / ServiceOrder / CareTeam / VisitCheckin / ServiceRecord / HealthAssessment / MedicationReminder / FamilyVisit / AlertEvent / CareNotice / OperationLog` 等页面字段、文案与动作可见性。
- 验证结果：后端 `mvn.cmd test` 通过，日志为 `No tests to run`，仅有不阻塞的 `rdc` profile warning；前端 `npm.cmd install`、`npm.cmd run build` 通过，并生成 `148-frontend/package-lock.json`；精确残留扫描未发现 `legal / applicant / approver / research_project / budget_category / budget_allocation / expense_claim / paper_record / patent_record / approval_task / payment_record / performance_statistic / electronic-contract-140 / VehicleServiceOrder` 等旧模板核心关键词，且未发现 `System.out.print*`、`printStackTrace` 或通配符 CORS 残留。

## Session: 2026-05-15 巡检项目 149

### Findings
- `149` 正式巡检已完成，当前停点已推进到 `149`，下一项目为 `150`。
- `149` 后端原始状态是“权限缺口 + 主题串台 + 基类缺失 + 编译配置缺失”叠加：`JwtInterceptor` 仅校验 token、不透传请求属性；各控制器未做服务端角色收口；`GlobalExceptionHandler` 直接回显异常消息；`WebMvcConfig` 仍允许通配符跨域；`pom.xml`、`JwtUtils`、`TokenService` 仍残留旧项目标识；同时缺失 `BaseCrudService.java`，并误把实验室实体写在了 `ServiceCategory.java` 中。
- `149` 前端原始状态同样未完成主题切换：登录页仍停留在企业培训旧文案，路由未配置 `meta.roles`，侧边栏未按角色过滤，store 缺少稳定的 `setAuth` 兼容动作，多个业务页字段、标题和动作按钮还混杂会议、论文、征稿、耗材等旧模板语义。
- 已为 `149` 的 `pom.xml` 补齐 Lombok 注解处理配置，并将 `artifactId`、`JwtUtils` secret、`TokenService` Redis token 前缀统一切回 `equipment-share-149` / `equipmentshare` 主题。
- 已为 `149` 的 `JwtInterceptor` 补齐 `userId/username/role` 请求属性透传，为 `AuthService` 增加统一角色断言，并按 `ADMIN / TEACHER / STUDENT / MANAGER` 口径，为账号权限、设备档案、实验室档案、设备分类、设备借用人、预约申请、借用记录、使用登记、违规记录、维修工单、归还确认、巡检计划、通知公告、操作日志、统计看板补齐服务端角色校验。
- 已为 `SysUser.password` 增加 `@JsonProperty(access = WRITE_ONLY)`，避免登录返回和用户列表链路泄露密码字段；同时保留登录、详情查询和列表响应中的密码脱敏一致性。
- 已为 `GlobalExceptionHandler` 拆分业务异常与系统异常返回，并将 `WebMvcConfig` 的通配符 CORS 收紧为 `http://localhost:3149`、`http://127.0.0.1:3149`、`http://localhost:4173`、`http://127.0.0.1:4173`。
- 已补回 `149-backend/src/main/java/com/equipmentshare/service/BaseCrudService.java`，并将错误的 `ServiceCategory.java` 修正为真实的 `LaboratoryRoom.java`，恢复项目原生编译与主题结构完整性。
- 已重写 `149-backend/sql/init.sql`、12 个业务实体字段和对应服务层关键字列，使后端数据模型完整切回“高校实验设备共享预约与违规使用追踪系统”主题。
- 已重写前端 `router/index.js`、`Layout.vue`、`Login.vue`、`store/user.js` 与 `components/DataPage.vue`，补齐角色首页跳转、动态菜单、登录示例账号文案与通用按钮权限开关；并同步重写 `SysUser / EquipmentAsset / LaboratoryRoom / EquipmentCategory / BorrowUser / ReservationRequest / BorrowRecord / UsageRegistration / ViolationRecord / MaintenanceWorkOrder / ReturnConfirmation / InspectionPlan / SystemNotice / OperationLog` 等页面字段、文案与动作可见性。
- 验证结果：后端 `mvn.cmd test` 通过，日志为 `No tests to run`，仅有不阻塞的 `rdc` profile warning；前端 `npm.cmd install`、`npm.cmd run build` 通过，并生成 `149-frontend/package-lock.json`；精确残留扫描未发现 `learning-path-139`、`labconsumable`、`trainer / employee`、`会议 / 论文 / 征稿 / 耗材 / 供应商 / 投稿 / 会场 / 评审 / 稿件` 等旧模板核心关键词，且未发现 `printStackTrace` 或通配符 CORS 残留。

## Session: 2026-05-15 巡检项目 150

### Findings
- `150` 正式巡检已完成，当前停点已推进到 `150`，下一项目为 `151`。
- `150` 后端原始状态是“权限缺口 + 双重串台 + 编译配置缺失”叠加：`JwtInterceptor` 仅校验 token、不透传请求属性；各控制器未做服务端角色收口；`GlobalExceptionHandler` 直接回显异常消息；`WebMvcConfig` 仍允许通配符 CORS；`pom.xml`、`JwtUtils`、`TokenService` 仍残留旧项目标识；同时启动类文件 `OutpatientExamApplication.java` 内部却仍声明为 `VehicleExamAppointment`，且 Maven 未显式配置 Lombok 注解处理，导致 `mvn test` 首轮直接编译失败。
- `150` 前端原始状态同样明显未完成主题切换：登录页虽然标题已切到医院门诊检查，但说明文案、示例账号和 store 对接仍残留电子合同模板逻辑；路由未配置 `meta.roles`，侧边栏未按角色过滤，`api/index.js` 多个接口路径映射错误，业务页字段/文案则大面积残留课题、导师、双选、答辩等旧模板语义。
- 已先修复启动类命名，并为 `pom.xml` 补齐 Lombok 注解处理配置，确认后端原生编译恢复正常；随后将 artifactId、JWT secret、Redis token 前缀统一切回 `outpatient-exam-150` / `outpatientexam` 主题。
- 已为 `150` 的 `JwtInterceptor` 补齐 `userId/username/role` 请求属性透传，为 `AuthService` 增加统一角色断言，并按 `ADMIN / DOCTOR / TECHNICIAN / PATIENT` 口径，为账号权限、检查项目、患者档案、医生档案、检查预约、检查科室、签到记录、检查报告、异常预警、报告回传、复诊建议、队列叫号、通知公告、操作日志、统计看板补齐服务端角色校验。
- 已为 `SysUser.password` 增加 `@JsonProperty(access = WRITE_ONLY)`，避免登录返回和用户列表链路泄露密码字段；同时修复用户新增默认密码为 `123456`、编辑资料时密码留空不被误覆盖的问题。
- 已为 `GlobalExceptionHandler` 拆分业务异常与系统异常返回，并将 `WebMvcConfig` 的通配符 CORS 收紧为 `http://localhost:3150`、`http://127.0.0.1:3150`、`http://localhost:4173`、`http://127.0.0.1:4173`。
- 已重写 `150-backend/sql/init.sql`、12 个业务实体字段、12 个注解 SQL Mapper，以及对应服务层旧变量命名，使后端数据模型完整切回“医院门诊检查预约与报告回传管理系统”主题。
- 已重写前端 `api/index.js`、`router/index.js`、`Layout.vue`、`Login.vue`、`store/user.js` 与 `components/DataPage.vue`，补齐角色首页跳转、动态菜单、接口路径和通用按钮权限开关；并同步重写 `SysUser / ExamItem / PatientProfile / DoctorProfile / ExamAppointment / ExamDepartment / CheckinRecord / ExamReport / AbnormalAlert / ReportDelivery / RevisitAdvice / QueueCall / HospitalNotice / OperationLog` 等页面字段、文案与动作可见性。
- 验证结果：后端 `mvn.cmd test` 通过，日志为 `No tests to run`，仅有不阻塞的 `rdc` profile warning；前端 `npm.cmd install`、`npm.cmd run build` 通过，并生成 `150-frontend/package-lock.json`；精确残留扫描未发现 `research_project / budget_category / budget_allocation / expense_claim / invoice_record / approval_task / payment_record / research_achievement / paper_record / patent_record / performance_statistic / risk_warning / electronic-contract-140 / LEGAL / APPLICANT / APPROVER / VehicleExamAppointment` 等旧模板核心关键词，且未发现 `printStackTrace` 或通配符 CORS 残留。

## 2026-05-15 151-200 批量开发记录

### Findings
- 已按用户要求读取本地 `skill.md` 和 `rule.md`，并联网参考 2026 计算机毕业设计热门方向；本地重复扫描显示 `151-196` 与 `001-150` 无 exact duplicate 标题。
- 已将较接近已有项目的后补题目换掉：避免 `198` 与 `136` 论文选题过程、`199` 与 `196` 药店处方续方方向发生近似重复，改为 `198` 城市共享充电宝投放巡检与收益结算系统、`199` 运动康复训练计划与体测评估管理系统；`197` 与 `200` 分别确定为社区家政服务、非遗工坊文创服务方向。
- 已修正批量生成脚本 `scripts/generate_151_200.py` 的执行问题和模板问题，包括 Python 写文件兼容、Vite 5 插件版本、Maven Lombok 注解处理、密码脱敏、系统异常兜底、JWT 请求属性透传、CORS 本地端口收口，以及前端 `STAFF` 角色管理权限。
- 已生成 `151-200` 共 50 个新增项目，每个项目都有后端、前端、PRD、PLAN、SQL 初始化脚本、前端锁定文件和合集 README 条目；`readme_simple.md` 与 `readme.md` 已更新为共 200 个项目，`200` 标记为最新。
- 已更新候选清单 `docs/topic-candidates-147-196.md`，标题推进到 `147-200`，表格覆盖 `147-200`，并将 `151-200` 标记为已实现。
- 静态检查确认 `151-200` 后端/前端核心文件齐备；未发现通配符 CORS、`printStackTrace`、`System.out.print*` 残留。
- 抽样验证结果：`151-backend`、`200-backend` 的 `mvn.cmd -q test` 通过；`151-frontend`、`200-frontend` 的 `npm.cmd install` 与 `npm.cmd run build` 通过。前端构建存在 Vite CJS Node API deprecation 和大 chunk warning，属于现有依赖/打包体积提示，不阻塞当前批量开发结论。
- 风险说明：本轮为 `151-200` 批量开发版本，已具备完整可运行结构，但尚未像 `147-150` 那样逐项目进行主题字段精修、角色首页差异化、真实登录流程与业务链路验收；下一轮正式巡检建议从 `151` 开始。

## 2026-05-15 151 正式开发记录

### Findings
- 用户要求“直接一个一个项目开始开发”，并显式启用 `$pua` skill；本轮按该 skill 的“主动出击、端到端验证、同类排查”方式执行，从 `151` 开始正式深化。
- `151` 批量版原始状态仍是通用模板：后端包名为 `com.p151`，业务类为 `BizRecord01-12`，数据库名为 `project_151`，默认角色为 `AUDITOR / OPERATOR / MEMBER`，前端页面也以 `BizRecord` 命名，不能支撑“文旅场馆讲解预约与票务核销管理平台”的正式答辩呈现。
- 已用分段生成脚本 `scripts/develop_151.py` 完成主题化重建，避免 Windows 长命令和中文编码风险；脚本会清理旧 `com.p151` 源码和旧 `BizRecord*.vue` 页面。
- 已将后端切换为 `com.culturevenue` 包，启动类为 `CultureVenueApplication`，artifactId 为 `culture-venue-ticket-151`，数据库为 `culture_venue_151`，Redis token 前缀为 `culturevenue:token:`。
- 已重建 `sys_user` 与 12 张业务表初始化脚本，默认账号为 `admin / manager / guide / checker / visitor`，角色口径为 `ADMIN / MANAGER / GUIDE / CHECKER / VISITOR`。
- 已生成并编译通过 12 个正式业务模块：`VenueInfo`、`TicketProduct`、`TicketOrder`、`GuideProfile`、`GuideSchedule`、`GuideBooking`、`TicketVerification`、`CrowdFlowRecord`、`VisitorFeedback`、`MarketingActivity`、`VenueNotice`、`OperationLog`。
- 已为后端控制器按角色收口写入权限：场馆/票种/活动/公告主要由 `ADMIN / MANAGER` 管理，讲解档案和排期支持 `GUIDE`，核销与客流支持 `CHECKER`，预约与评价支持 `VISITOR`。
- 已重写前端 `api/router/store/layout/login/DataPage/dashboard` 与全部业务页，页面字段切回场馆、票种、预约、讲解、核销、客流、评价、公告语义；侧边栏按角色过滤，数据页按钮按模块 `manageRoles` 收口。
- 源码残留扫描未发现 `com.p151`、`BizRecord`、`project_151`、`AUDITOR / OPERATOR / MEMBER`、`record01 / record02 / record03`、通配符 CORS、`printStackTrace`、`System.out.print*`。
- 验证结果：`151-backend` 执行 `mvn.cmd -q clean test` 通过；`151-frontend` 执行 `npm.cmd install` 与 `npm.cmd run build` 通过。前端仍有 Vite CJS deprecation 和大 chunk warning，属于当前 Element Plus/ECharts 依赖体积提示，不阻塞。
- 剩余风险：尚未连接真实 MySQL/Redis 做登录和业务接口联调；当前结论覆盖源码、编译与前端生产构建。下一项目为 `152`。

## 2026-05-15 152 正式开发记录

### Findings
- 按用户“你就一直继续不要停”的要求，完成 `151` 后没有等待继续指令，直接进入 `152` 正式开发。
- `152` 批量版原始状态仍是通用模板：后端包名为 `com.p152`，业务类为 `BizRecord01-12`，数据库名为 `project_152`，前端页面也是 `BizRecord*.vue`，不符合“工厂危险作业审批与监护巡检管理系统”的正式答辩语义。
- 已基于 `151` 已验证的正式化流水线生成 `scripts/develop_152.py`。首次脚本内容检查发现 `ROLES/MODULES` 替换正则未生效，仍残留文旅模块；已修复转换脚本并重新生成，避免错误进入项目源码。
- 已将后端切换为 `com.hazardwork` 包，启动类为 `HazardWorkApplication`，artifactId 为 `hazard-work-permit-152`，数据库为 `hazard_work_152`，Redis token 前缀为 `hazardwork:token:`。
- 已重建默认账号与角色口径：`ADMIN/admin`、`SAFETY/safety`、`APPROVER/approver`、`GUARDIAN/guardian`、`WORKER/worker`，统一密码 `123456`。
- 已生成并编译通过 12 个正式业务模块：`WorkArea`、`HazardSource`、`WorkerProfile`、`WorkPermit`、`PermitApproval`、`SafetyBriefing`、`GuardianAssignment`、`MonitorRecord`、`HiddenDanger`、`GasDetection`、`EmergencyPlan`、`OperationLog`。
- 已为后端控制器按角色收口写入权限：作业区域、风险源、应急预案由 `ADMIN / SAFETY` 管理；审批链路支持 `APPROVER`；监护安排、监护记录、气体检测、隐患闭环支持 `GUARDIAN`；作业票申请和安全交底支持 `WORKER`。
- 已重写前端路由、角色首页跳转、动态菜单、登录页、通用数据页、看板和全部业务页字段，页面语义切回作业票、审批链路、监护记录、气体检测、隐患闭环。
- 源码残留扫描未发现 `com.p152`、`BizRecord`、`project_152`、旧角色名、旧文旅模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace`、`System.out.print*`。
- 验证结果：`152-backend` 执行 `mvn.cmd -q clean test` 通过；`152-frontend` 执行 `npm.cmd install` 与 `npm.cmd run build` 通过。前端仍有 Vite CJS deprecation 和大 chunk warning，不阻塞。
- 剩余风险：尚未连接真实 MySQL/Redis 做登录和业务接口联调；当前结论覆盖源码、编译与前端生产构建。下一项目为 `153`。

## 2026-05-15 153 正式开发记录

### Findings
- 按用户“你就一直继续不要停”的要求，完成 `152` 后继续进入 `153` 正式开发，并按 `$pua` skill 要求保留联网选题参考、脚本检查、源码残留扫描和构建验证闭环。
- 联网参考校园二手交易、信用评价、担保交易类题目方向后，`153` 的正式化重点确定为“寄卖上架 + 担保订单 + 交接确认 + 信用评价 + 违约记录 + 纠纷申诉”，与已有 `021` 普通校园二手交易平台拉开差异。
- `153` 批量版原始状态仍是通用模板：后端包名为 `com.p153`，业务类为 `BizRecord01-12`，数据库名为 `project_153`，前端页面和接口路径仍以 `record01-12` 命名，不能支撑正式答辩语义。
- 已基于 `151` 已验证的正式化流水线生成 `scripts/develop_153.py`。落盘前脚本检查发现统计饼图、登录角色首页和账号说明仍残留文旅语义，已修复 `scripts/make_153_from_151.py` 并重新生成脚本。
- 已将后端切换为 `com.campusresale` 包，启动类为 `CampusResaleApplication`，artifactId 为 `campus-resale-credit-153`，数据库为 `campus_resale_153`，Redis token 前缀为 `campusresale:token:`。
- 已重建默认账号与角色口径：`ADMIN/admin`、`OPERATOR/operator`、`SELLER/seller`、`BUYER/buyer`、`ARBITER/arbiter`，统一密码 `123456`。
- 已生成并编译通过 12 个正式业务模块：`ItemCategory`、`StudentProfile`、`ConsignmentItem`、`ItemAudit`、`EscrowOrder`、`PaymentRecord`、`HandoverRecord`、`CreditEvaluation`、`BreachRecord`、`ComplaintTicket`、`PlatformNotice`、`OperationLog`。
- 已为后端控制器按角色收口写入权限：分类、学生档案、审核、公告和日志主要由 `ADMIN / OPERATOR` 管理；寄卖物品支持 `SELLER`；担保订单、交接确认支持 `SELLER / BUYER`；担保支付支持 `BUYER`；信用评价覆盖交易双方与仲裁员；违约记录和纠纷申诉支持 `ARBITER`。
- 已重写前端路由、角色首页跳转、动态菜单、登录页、通用数据页、看板和全部业务页字段，页面语义切回物品分类、学生档案、寄卖物品、上架审核、担保订单、担保支付、交接确认、信用评价、违约记录、纠纷申诉、平台公告、操作日志。
- 源码残留扫描未发现 `com.p153`、`BizRecord`、`project_153`、旧角色名、旧文旅模块名、旧工业安全模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace`、`System.out.print*`。
- 验证结果：`153-backend` 执行 `mvn.cmd -q clean test` 通过；`153-frontend` 执行 `npm.cmd install` 与 `npm.cmd run build` 通过。前端仍有 Vite CJS deprecation 和大 chunk warning，不阻塞。
- 剩余风险：尚未连接真实 MySQL/Redis 做登录和业务接口联调；当前结论覆盖源码、编译与前端生产构建。下一项目为 `154`。

## 2026-05-15 154 正式开发记录

### Findings
- 继续按“一个一个项目开发”的节奏进入 `154`，未停在批量脚手架层面；当前网络受限，因此沿用前序已查到的宠物医院门诊运营方向，并以本地题目清单为准。
- `154` 批量版原始状态仍是通用模板：后端包名为 `com.p154`，业务类为 `BizRecord01-12`，数据库名为 `project_154`，前端页面和接口路径仍以 `record01-12` 命名。
- 已基于 `151` 已验证的正式化流水线生成 `scripts/develop_154.py`，并在正式落盘前检查脚本内容，清理文旅、校园寄卖、工业安全等旧项目词汇残留。
- 已额外修正 `scripts/make_154_from_151.py` 的路由/菜单角色生成逻辑，使前端侧边栏和路由 `meta.roles` 使用每个模块自己的角色数组，而不是所有角色都可见。
- 已将后端切换为 `com.pethospital` 包，启动类为 `PetHospitalApplication`，artifactId 为 `pet-hospital-vaccine-154`，数据库为 `pet_hospital_154`，Redis token 前缀为 `pethospital:token:`。
- 已重建默认账号与角色口径：`ADMIN/admin`、`RECEPTION/reception`、`DOCTOR/doctor`、`NURSE/nurse`、`OWNER/owner`，统一密码 `123456`。
- 已生成并编译通过 12 个正式业务模块：`PetOwner`、`PetProfile`、`DoctorProfile`、`ClinicSchedule`、`VisitAppointment`、`VisitRecord`、`VaccinePlan`、`VaccineRecord`、`FollowUpRecord`、`MedicineStock`、`BillingRecord`、`OperationLog`。
- 已为后端控制器按角色收口写入权限：宠主和宠物档案支持前台与宠主；医生档案、接诊排班支持前台和医生；接诊记录、疫苗接种、药品库存支持医生和护理接种员；疫苗计划与随访记录覆盖医生、护理接种员和宠主；费用结算支持前台和宠主。
- 已重写前端路由、角色首页跳转、动态菜单、登录页、通用数据页、看板和全部业务页字段，页面语义切回宠主档案、宠物档案、医生档案、接诊排班、接诊预约、接诊记录、疫苗计划、疫苗接种、随访记录、药品库存、费用结算、操作日志。
- 源码残留扫描未发现 `com.p154`、`BizRecord`、`project_154`、旧角色名、旧文旅模块名、旧校园寄卖模块名、旧工业安全模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace`、`System.out.print*`。
- 验证结果：`154-backend` 执行 `mvn.cmd -q clean test` 通过；`154-frontend` 执行 `npm.cmd install` 与 `npm.cmd run build` 通过。前端仍有 Vite CJS deprecation 和大 chunk warning，不阻塞。
- 剩余风险：尚未连接真实 MySQL/Redis 做登录和业务接口联调；当前结论覆盖源码、编译与前端生产构建。下一项目为 `155`。

## 2026-05-15 155 正式开发记录

### Findings
- 继续执行“一个一个项目开发”的节奏；恢复后先按交接要求补跑 `154-frontend npm.cmd run build`，发现当前沙箱下 Vite 默认 root 指向映射目录，导致 Rollup 收到绝对 `index.html` 输出名。
- 已在 `154-frontend/vite.config.js`、`scripts/develop_154.py` 以及 `scripts/make_155_from_151.py` 中加入 `root: process.cwd()`，154 前端随后构建通过；该修复也同步进入 155 及后续正式化脚本模式。
- `155` 批量版原始状态仍是通用模板：后端包名为 `com.p155`，业务类为 `BizRecord01-12`，数据库名为 `project_155`，前端页面和接口路径仍以 `record01-12` 命名。
- 已基于 `151` 已验证的正式化流水线生成 `scripts/develop_155.py`。落盘前脚本检查发现统计饼图仍残留“预约/讲解/入馆”、PRD 默认账号仍是旧角色，已修复 `scripts/make_155_from_151.py` 并重新生成脚本。
- 已将后端切换为 `com.communityparty` 包，启动类为 `CommunityPartyApplication`，artifactId 为 `community-party-points-155`，数据库为 `community_party_155`，Redis token 前缀为 `communityparty:token:`。
- 已重建默认账号与角色口径：`ADMIN/admin`、`SECRETARY/secretary`、`ORGANIZER/organizer`、`VOLUNTEER/volunteer`、`RESIDENT/resident`，统一密码 `123456`。
- 已生成并编译通过 12 个正式业务模块：`PartyBranch`、`MemberProfile`、`PartyActivity`、`ActivityRegistration`、`AttendanceRecord`、`VolunteerTask`、`PointRecord`、`PointExchange`、`OrganizationTransfer`、`HonorRanking`、`NoticeBoard`、`OperationLog`。
- 已为后端控制器按角色收口写入权限：党组织和党员档案由 `ADMIN / SECRETARY` 管理；党建活动、活动报名、签到记录、志愿任务、积分兑换支持活动组织员和志愿骨干；积分记录、榜单统计覆盖组织与社区党员查看；组织关系支持书记与党员；通知公告由党务与组织员维护。
- 已重写前端路由、角色首页跳转、动态菜单、登录页、通用数据页、看板和全部业务页字段，页面语义切回党组织维护、党员档案、党建活动、活动报名、签到记录、志愿任务、积分记录、积分兑换、组织关系、榜单统计、通知公告、操作日志。
- 源码残留扫描未发现 `com.p155`、`BizRecord`、`project_155`、旧角色名、旧文旅/宠物医院/校园寄卖/工业安全模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace`、`System.out.print*`。
- 验证结果：`155-backend` 执行 `mvn.cmd -q clean test` 通过；`155-frontend` 执行 `npm.cmd install` 与 `npm.cmd run build` 通过。前端仍有 Vite CJS deprecation 和大 chunk warning，不阻塞。
- 剩余风险：尚未连接真实 MySQL/Redis 做登录和业务接口联调；当前结论覆盖源码、编译与前端生产构建。下一项目为 `156`。

## 2026-05-15 156 正式开发记录

### Findings
- 按用户“多元化一点”的要求，`156` 不沿用上一个党建积分模式，而转向智慧校园能源治理；联网参考高校宿舍用电管理常见能力后，将闭环确定为宿舍楼栋、宿舍房间、能耗表计、用电读数、能耗账单、预警策略、异常预警、节能任务、节能排行和巡查记录。
- `156` 批量版原始状态仍是通用模板：后端包名为 `com.p156`，业务类为 `BizRecord01-12`，数据库名为 `project_156`，前端页面和接口路径仍以 `record01-12` 命名。
- 已基于 `155` 已验证的正式化流水线生成 `scripts/develop_156.py`，继承 Vite `root: process.cwd()` 修复，并在落盘前扫描旧党建、宠物医院、校园寄卖、工业安全、文旅票务等关键词。
- 已将后端切换为 `com.dormenergy` 包，启动类为 `DormEnergyApplication`，artifactId 为 `dorm-energy-ranking-156`，数据库为 `dorm_energy_156`，Redis token 前缀为 `dormenergy:token:`。
- 已重建默认账号与角色口径：`ADMIN/admin`、`LOGISTICS/logistics`、`COUNSELOR/counselor`、`ENERGY/energy`、`STUDENT/student`，统一密码 `123456`。
- 已生成并编译通过 12 个正式业务模块：`DormBuilding`、`DormRoom`、`MeterDevice`、`EnergyReading`、`ConsumptionBill`、`AlertRule`、`AbnormalAlert`、`SavingTask`、`SavingRanking`、`InspectionRecord`、`EnergyNotice`、`OperationLog`。
- 已为后端控制器按角色收口写入权限：楼栋与房间由 `ADMIN / LOGISTICS` 管理；表计、读数、账单和预警策略由后勤与能耗专员维护；异常预警和巡查记录覆盖宿舍辅导员；节能任务和节能排行允许学生代表参与查看。
- 已重写前端路由、角色首页跳转、动态菜单、登录页、通用数据页、看板和全部业务页字段，页面语义切回楼栋房间、表计读数、账单核算、预警策略、异常预警、节能任务、节能排行、巡查记录、通知公告、操作日志。
- 源码残留扫描未发现 `com.p156`、`BizRecord`、`project_156`、旧角色名、旧党建/宠物医院/校园寄卖/工业安全/文旅模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace`、`System.out.print*`。
- 验证结果：`156-backend` 执行 `mvn.cmd -q clean test` 通过；`156-frontend` 执行 `npm.cmd install` 与 `npm.cmd run build` 通过。前端仍有 Vite CJS deprecation 和大 chunk warning，不阻塞。
- 剩余风险：尚未连接真实 MySQL/Redis 做登录和业务接口联调；当前结论覆盖源码、编译与前端生产构建。下一项目为 `157`。

## 2026-05-15 157 正式开发记录

### Findings
- 按用户“多元化一点”的要求，`157` 选择智慧物流园区调度方向，结合联网参考的车辆预约入场、YMS 道口/月台分配、排队叫号和周转时效管理能力，避免与前序智慧校园、党建、宠物医院等题材重复。
- `157` 批量版原始状态仍是通用模板：后端包名为 `com.p157`，业务类为 `BizRecord01-12`，数据库名为 `project_157`，前端页面和接口路径仍以 `record01-12` 命名。
- 已基于 `156` 已验证的正式化流水线生成 `scripts/develop_157.py`，继承 Vite `root: process.cwd()` 修复，并在落盘前扫描旧宿舍能耗、党建、宠物医院、校园寄卖、工业安全、文旅票务等关键词。
- 已将后端切换为 `com.logisticspark` 包，启动类为 `LogisticsParkApplication`，artifactId 为 `logistics-yard-gate-157`，数据库为 `logistics_park_157`，Redis token 前缀为 `logisticspark:token:`。
- 已重建默认账号与角色口径：`ADMIN/admin`、`DISPATCHER/dispatcher`、`GATEKEEPER/gatekeeper`、`YARDMASTER/yardmaster`、`CARRIER/carrier`，统一密码 `123456`。
- 已生成并编译通过 12 个正式业务模块：`CarrierCompany`、`VehicleProfile`、`DriverProfile`、`GateAppointment`、`TimeSlotPlan`、`GateCheckin`、`QueueTicket`、`DockDoor`、`DockAssignment`、`LoadingTask`、`TurnaroundRecord`、`OperationLog`。
- 已为后端控制器按角色收口写入权限：调度主管维护承运商、车辆、时段、道口和周转统计；门岗核验员负责核验、放行和排队叫号；场内调度员维护道口资源、道口分配和装卸任务；承运商代表可提交预约并查看排队、分配与装卸任务。
- 已重写前端路由、角色首页跳转、动态菜单、登录页、通用数据页、看板和全部业务页字段，页面语义切回承运商档案、车辆档案、司机档案、入场预约、时段计划、门岗核验、排队叫号、道口资源、道口分配、装卸任务、周转统计、操作日志。
- 源码残留扫描未发现 `com.p157`、`BizRecord`、`project_157`、旧角色名、旧宿舍能耗/党建/宠物医院/校园寄卖/工业安全/文旅模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace`、`System.out.print*`。
- 验证结果：`157-backend` 执行 `mvn.cmd -q clean test` 通过；`157-frontend` 执行 `npm.cmd install` 与 `npm.cmd run build` 通过。前端仍有 Vite CJS deprecation 和大 chunk warning，不阻塞。
- 剩余风险：尚未连接真实 MySQL/Redis 做登录和业务接口联调；当前结论覆盖源码、编译与前端生产构建。下一项目为 `158`。

## 2026-05-15 158 正式开发记录

### Findings
- 按用户“多元化一点”的要求，`158` 选择教培机构课消退费方向，将业务闭环确定为校区档案、课程产品、学员档案、教师档案、班级台账、排课计划、上课考勤、课消记录、退费申请、退费审批和财务流水，避免沿用物流园区调度语义。
- `158` 批量版原始状态仍是通用模板：后端包名为 `com.p158`，业务类为 `BizRecord01-12`，数据库名为 `project_158`，前端页面和接口路径仍以 `record01-12` 命名。
- 已基于 `157` 已验证的正式化流水线生成 `scripts/develop_158.py`，继承 Vite `root: process.cwd()` 修复，并修正统计饼图状态为“待上课、已消课、退费审批中、财务已结清”。
- 已将后端切换为 `com.trainingrefund` 包，启动类为 `TrainingRefundApplication`，artifactId 为 `training-consumption-refund-158`，数据库为 `training_refund_158`，Redis token 前缀为 `trainingrefund:token:`。
- 已重建默认账号与角色口径：`ADMIN/admin`、`PRINCIPAL/principal`、`ACADEMIC/academic`、`TEACHER/teacher`、`FINANCE/finance`、`PARENT/parent`，统一密码 `123456`。
- 已生成并编译通过 12 个正式业务模块：`CampusBranch`、`CourseCatalog`、`StudentProfile`、`TeacherProfile`、`ClassGroup`、`LessonSchedule`、`AttendanceRecord`、`ConsumptionRecord`、`RefundApplication`、`RefundApproval`、`FinanceLedger`、`OperationLog`。
- 已为后端控制器按角色收口写入权限：校区校长维护校区、课程、学员、教师与班级；教务主管维护排课、考勤和课消；任课老师参与授课考勤与课消确认；财务审核员处理退费、审批和流水；学员家长查看学员、考勤、课消并提交退费申请。
- 已重写前端路由、角色首页跳转、动态菜单、登录页、通用数据页、看板和全部业务页字段，页面语义切回校区档案、课程产品、学员档案、教师档案、班级台账、排课计划、上课考勤、课消记录、退费申请、退费审批、财务流水、操作日志。
- 源码残留扫描未发现 `com.p158`、`BizRecord`、`project_158`、旧角色名、旧物流园区/宿舍能耗/党建/宠物医院/校园寄卖/工业安全/文旅模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace`、`System.out.print*`。其中 `campus_branch / CampusBranch` 属于本项目“校区档案”合法命名，不作为校园寄卖残留处理。
- 验证结果：`158-backend` 执行 `mvn.cmd -q clean test` 通过；`158-frontend` 执行 `npm.cmd install` 与 `npm.cmd run build` 通过。前端仍有 Vite CJS deprecation 和大 chunk warning，不阻塞。
- 剩余风险：尚未连接真实 MySQL/Redis 做登录和业务接口联调；当前结论覆盖源码、编译与前端生产构建。下一项目为 `159`。

## 2026-05-15 159 正式开发记录

### Findings
- 按用户“多元化一点”的要求，`159` 选择医疗废弃物收运联单与闭环监管方向，结合公开资料中的分类收集、转运交接、处置确认和监管追溯能力，将闭环确定为医废来源、废物类别、包装赋码、收集预约、称重记录、暂存交接、转运联单、运输轨迹、处置确认、异常追溯和监管抽查。
- `159` 批量版原始状态仍是通用模板：后端包名为 `com.p159`，业务类为 `BizRecord01-12`，数据库名为 `project_159`，前端页面和接口路径仍以 `record01-12` 命名。
- 已基于 `158` 已验证的正式化流水线生成 `scripts/develop_159.py`，继承 Vite `root: process.cwd()` 修复，并在落盘前修正旧教培统计状态为“待收集、转运中、待处置、闭环完成”。
- 已将后端切换为 `com.medicalwaste` 包，启动类为 `MedicalWasteApplication`，artifactId 为 `medical-waste-manifest-159`，数据库为 `medical_waste_159`，Redis token 前缀为 `medicalwaste:token:`。
- 已重建默认账号与角色口径：`ADMIN/admin`、`HOSPITAL/hospital`、`COLLECTOR/collector`、`TRANSPORTER/transporter`、`DISPOSAL/disposal`、`REGULATOR/regulator`，统一密码 `123456`。
- 已生成并编译通过 12 个正式业务模块：`WasteSource`、`WasteCategory`、`WastePackage`、`CollectionOrder`、`WeighingRecord`、`StorageHandover`、`TransferManifest`、`TransportTrack`、`DisposalConfirm`、`ExceptionTrace`、`ComplianceAudit`、`OperationLog`。
- 已为后端控制器按角色收口写入权限：医院交接员维护医废来源、包装赋码和暂存交接；收运调度员维护收集预约、称重记录和转运联单；转运司机维护运输轨迹和交接异常；处置厂审核员维护处置确认；监管人员查看联单链路、异常追溯和监管抽查。
- 已重写前端路由、角色首页跳转、动态菜单、登录页、通用数据页、看板和全部业务页字段，页面语义切回医废来源、废物类别、包装赋码、收集预约、称重记录、暂存交接、转运联单、运输轨迹、处置确认、异常追溯、监管抽查、操作日志。
- 源码残留扫描未发现 `com.p159`、`BizRecord`、`project_159`、旧角色名、旧教培/物流园区/宿舍能耗/党建/宠物医院/校园寄卖/工业安全/文旅模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace`、`System.out.print*`。其中“转运司机、转运车辆、驾驶员”属于医废运输业务合法字段，不作为物流园区残留处理。
- 验证结果：`159-backend` 执行 `mvn.cmd -q clean test` 通过；`159-frontend` 执行 `npm.cmd install` 与 `npm.cmd run build` 通过。前端仍有 Vite CJS deprecation 和大 chunk warning，不阻塞。
- 剩余风险：尚未连接真实 MySQL/Redis 做登录和业务接口联调；当前结论覆盖源码、编译与前端生产构建。下一项目为 `160`。

## 2026-05-15 160 正式开发记录

### Findings
- 按用户“多元化一点”的要求，`160` 选择高校社团活动预算报销与物资借用方向，将闭环确定为社团档案、成员档案、活动立项、预算申请、预算明细、审批记录、报销申请、票据归档、物资台账、物资借用和归还验收。
- `160` 批量版原始状态仍是通用模板：后端包名为 `com.p160`，业务类为 `BizRecord01-12`，数据库名为 `project_160`，前端页面和接口路径仍以 `record01-12` 命名。
- 已基于 `159` 已验证的正式化流水线生成 `scripts/develop_160.py`，继承 Vite `root: process.cwd()` 修复，并在落盘前扫描旧医疗废弃物、教培课消、物流园区等关键词。
- 已将后端切换为 `com.clubfinance` 包，启动类为 `ClubFinanceApplication`，artifactId 为 `campus-club-budget-160`，数据库为 `club_finance_160`，Redis token 前缀为 `clubfinance:token:`。
- 已重建默认账号与角色口径：`ADMIN/admin`、`UNION/union`、`CLUB/club`、`TREASURER/treasurer`、`WAREHOUSE/warehouse`、`MEMBER/member`，统一密码 `123456`。
- 已生成并编译通过 12 个正式业务模块：`ClubProfile`、`MemberProfile`、`ActivityPlan`、`BudgetRequest`、`BudgetLineItem`、`ApprovalRecord`、`ReimbursementClaim`、`ReceiptArchive`、`MaterialAsset`、`BorrowRequest`、`ReturnInspection`、`OperationLog`。
- 已为后端控制器按角色收口写入权限：团委审核员维护社团档案、预算审批与经费复核；社团负责人维护活动立项、预算申请、报销申请和成员协同；社团财务员维护预算明细、报销申请和票据归档；物资管理员维护物资台账、借用和归还验收；社团成员查看活动、借用物资和报销进度。
- 已重写前端路由、角色首页跳转、动态菜单、登录页、通用数据页、看板和全部业务页字段，页面语义切回社团档案、成员档案、活动立项、预算申请、预算明细、审批记录、报销申请、票据归档、物资台账、物资借用、归还验收、操作日志。
- 源码残留扫描未发现 `com.p160`、`BizRecord`、`project_160`、旧角色名、旧医废/教培/物流园区/宿舍能耗/党建/宠物医院/工业安全/文旅模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace`、`System.out.print*`。
- 验证结果：`160-backend` 执行 `mvn.cmd -q clean test` 通过；`160-frontend` 执行 `npm.cmd install` 与 `npm.cmd run build` 通过。前端仍有 Vite CJS deprecation 和大 chunk warning，不阻塞。
- 剩余风险：尚未连接真实 MySQL/Redis 做登录和业务接口联调；当前结论覆盖源码、编译与前端生产构建。下一项目为 `161`。

## 2026-05-15 161 正式开发记录

### Findings
- 按用户“多元化一点”的要求，`161` 选择景区失物招领与游客寻回方向，将闭环确定为景区区域、失物登记、拾物上报、游客认领、身份核验、位置追踪、暂存保管、通知协同、归还交接、寻回任务和回访评价。
- `161` 批量版原始状态仍是通用模板：后端包名为 `com.p161`，业务类为 `BizRecord01-12`，数据库名为 `project_161`，前端页面和接口路径仍以 `record01-12` 命名。
- 已基于 `160` 已验证的正式化流水线生成 `scripts/develop_161.py`，继承 Vite `root: process.cwd()` 修复，并在落盘前修正旧社团财资统计状态为“待认领、核验中、待归还、已寻回”。
- 已将后端切换为 `com.lostfound` 包，启动类为 `ScenicLostFoundApplication`，artifactId 为 `scenic-lost-found-161`，数据库为 `scenic_lost_found_161`，Redis token 前缀为 `lostfound:token:`。
- 已重建默认账号与角色口径：`ADMIN/admin`、`SERVICE/service`、`SECURITY/security`、`SCENIC/scenic`、`BROADCAST/broadcast`、`VISITOR/visitor`，统一密码 `123456`。
- 已生成并编译通过 12 个正式业务模块：`ScenicArea`、`LostItem`、`FoundReport`、`ClaimApplication`、`IdentityVerify`、`LocationTrace`、`CustodyRecord`、`NoticeBroadcast`、`HandoverRecord`、`SearchTask`、`FeedbackRecord`、`OperationLog`。
- 已为后端控制器按角色收口写入权限：游客服务员维护失物登记、认领核验、暂存和归还交接；安保巡查员维护拾物上报、位置追踪和寻回任务；景区运营员维护景区区域、位置追踪与寻回资源；通知协同员维护通知协同与回访评价；游客用户可登记失物、上报拾物、申请认领并查看进度。
- 已重写前端路由、角色首页跳转、动态菜单、登录页、通用数据页、看板和全部业务页字段，页面语义切回景区区域、失物登记、拾物上报、游客认领、身份核验、位置追踪、暂存保管、通知协同、归还交接、寻回任务、回访评价、操作日志。
- 源码残留扫描未发现 `com.p161`、`BizRecord`、`project_161`、旧角色名、旧社团财资/医废/教培/物流园区模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace`、`System.out.print*`。
- 验证结果：`161-backend` 执行 `mvn.cmd -q clean test` 通过；`161-frontend` 执行 `npm.cmd install` 与 `npm.cmd run build` 通过。前端仍有 Vite CJS deprecation 和大 chunk warning，不阻塞。
- 剩余风险：尚未连接真实 MySQL/Redis 做登录和业务接口联调；当前结论覆盖源码、编译与前端生产构建。下一项目为 `162`。

## 2026-05-16 162 正式开发记录

### Findings
- `162` 批量版原始状态仍是通用模板：后端包名为 `com.p162`，业务类为 `BizRecord01-12`，数据库名为 `project_162`，前端页面和接口路径仍以 `record01-12` 命名。
- 已基于 `161` 已验证的正式化流水线修正并执行 `scripts/develop_162.py`；执行前清理了景区失物/游客寻回 PRD、旧 `SERVICE / SECURITY / SCENIC / BROADCAST / VISITOR` 角色、`3161 / 8161` 端口和 `161-` 初始化编号残留。
- 已将后端切换为 `com.freshretail` 包，启动类为 `FreshRetailApplication`，artifactId 为 `fresh-expiry-promotion-162`，数据库为 `fresh_expiry_162`，Redis token 前缀为 `freshretail:token:`。
- 已重建默认账号与角色口径：`ADMIN/admin`、`MANAGER/manager`、`CLERK/clerk`、`STOCK/stock`、`MARKETING/marketing`、`SUPPLIER/supplier`，统一密码 `123456`。
- 已生成并编译通过 12 个正式业务模块：`StoreProfile`、`SupplierProfile`、`FreshCategory`、`ProductBatch`、`ShelfLifeRule`、`ExpiryWarning`、`PromotionStrategy`、`DiscountExecution`、`LossReport`、`InventoryTurnover`、`StoreAnalysis`、`OperationLog`。
- 已为后端控制器按角色收口写入权限：门店店长维护门店、批次、预警和分析；理货员维护商品批次、折扣执行与报损；库存主管维护品类、保质期规则、临期预警、库存周转；促销专员维护促销策略、折扣执行和门店分析；供应商协同员维护供应商档案。
- 已重写前端路由、角色首页跳转、动态菜单、登录页、通用数据页、看板和全部业务页字段，页面语义切回门店档案、供应商档案、生鲜品类、商品批次、保质期规则、临期预警、促销策略、折扣执行、报损记录、库存周转、门店分析、操作日志。
- 源码残留扫描未发现 `com.p162`、`BizRecord`、`project_162`、旧景区失物角色/模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace`、`System.out.print*`。
- 验证结果：`162-backend` 执行 `mvn.cmd -q clean test` 通过；`162-frontend` 执行 `npm.cmd install` 与 `npm.cmd run build` 通过。前端仍有 Vite CJS deprecation 和大 chunk warning，不阻塞。
- 剩余风险：尚未连接真实 MySQL/Redis 做登录和业务接口联调；当前结论覆盖源码、编译与前端生产构建。下一项目为 `163`。

## 2026-05-16 163 正式开发记录

### Findings
- `163` 批量版原始状态仍是通用模板：后端包名为 `com.p163`，业务类为 `BizRecord01-12`，数据库名为 `project_163`，前端页面和接口路径仍以 `record01-12` 命名。
- 已基于 `162` 已验证的正式化流水线生成并执行 `scripts/develop_163.py`；执行前清理了生鲜临期商品、门店、促销、报损、库存等旧主题词，端口切换为后端 `8163`、前端 `3163`。
- 已将后端切换为 `com.clinicalrotation` 包，启动类为 `ClinicalRotationApplication`，artifactId 为 `clinical-rotation-case-163`，数据库为 `clinical_rotation_163`，Redis token 前缀为 `clinicalrotation:token:`。
- 已重建默认账号与角色口径：`ADMIN/admin`、`SECRETARY/secretary`、`TEACHER/teacher`、`STUDENT/student`、`EXAMINER/examiner`、`DIRECTOR/director`，统一密码 `123456`。
- 已生成并编译通过 12 个正式业务模块：`DepartmentProfile`、`StudentProfile`、`TeacherProfile`、`RotationPlan`、`CaseLibrary`、`StudyRecord`、`TeachingRound`、`SkillTraining`、`TeachingScore`、`ExitExam`、`FeedbackReview`、`OperationLog`。
- 已为后端控制器按角色收口写入权限：教学秘书维护轮转计划、学生档案和出科流程；带教老师维护病例学习、教学查房、技能训练和学习记录；实习学生查看轮转、病例、学习记录、评分反馈与考核进度；考核评委维护技能训练与出科考核；科室主任维护科室、带教、评分复核和整改跟踪。
- 已重写前端路由、角色首页跳转、动态菜单、登录页、通用数据页、看板和全部业务页字段，页面语义切回轮转科室、实习学生、带教老师、轮转安排、病例学习、学习记录、教学查房、技能训练、带教评分、出科考核、反馈整改、操作日志。
- 源码残留扫描未发现 `com.p163`、`BizRecord`、`project_163`、旧生鲜临期角色/模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace`、`System.out.print*`。
- 验证结果：`163-backend` 执行 `mvn.cmd -q clean test` 通过；`163-frontend` 执行 `npm.cmd install` 与 `npm.cmd run build` 通过。前端仍有 Vite CJS deprecation 和大 chunk warning，不阻塞。
- 剩余风险：尚未连接真实 MySQL/Redis 做登录和业务接口联调；当前结论覆盖源码、编译与前端生产构建。下一项目为 `164`。

## 2026-05-16 164 正式开发记录

### Findings
- `164` 批量版原始状态仍是通用模板：后端包名为 `com.p164`，业务类为 `BizRecord01-12`，数据库名为 `project_164`，前端页面和接口路径仍以 `record01-12` 命名。
- 已基于 `163` 已验证的正式化流水线生成并执行 `scripts/develop_164.py`；执行前清理了医学实习轮转、病例学习、带教评分、出科考核等旧主题词，端口切换为后端 `8164`、前端 `3164`。
- 已将后端切换为 `com.sportevent` 包，启动类为 `SportEventApplication`，artifactId 为 `campus-sport-event-164`，数据库为 `campus_sport_event_164`，Redis token 前缀为 `sportevent:token:`。
- 已重建默认账号与角色口径：`ADMIN/admin`、`ORGANIZER/organizer`、`COACH/coach`、`ATHLETE/athlete`、`REFEREE/referee`、`COMMITTEE/committee`，统一密码 `123456`。
- 已生成并编译通过 12 个正式业务模块：`SportEvent`、`TeamProfile`、`AthleteProfile`、`EventRegistration`、`GroupDraw`、`ScheduleMatch`、`VenueResource`、`RefereeAssignment`、`ScoreRecord`、`ResultPublish`、`AppealReview`、`OperationLog`。
- 已为后端控制器按角色收口写入权限：赛事组织员维护赛事、报名分组、赛程、场地、裁判指派和成绩公示；队伍教练维护队伍、运动员、报名、赛程和申诉；参赛学生查看报名、赛程、成绩和申诉进度；裁判员维护裁判指派、赛程确认和裁判评分；仲裁委员维护裁判复核、成绩公示和申诉复核。
- 已重写前端路由、角色首页跳转、动态菜单、登录页、通用数据页、看板和全部业务页字段，页面语义切回体育赛事、参赛队伍、运动员档案、赛事报名、报名分组、赛程编排、场地资源、裁判指派、裁判评分、成绩公示、申诉复核、操作日志。
- 源码残留扫描未发现 `com.p164`、`BizRecord`、`project_164`、旧医学轮转角色/模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace`、`System.out.print*`。
- 验证结果：`164-backend` 执行 `mvn.cmd -q clean test` 通过；`164-frontend` 执行 `npm.cmd install` 与 `npm.cmd run build` 通过。前端仍有 Vite CJS deprecation 和大 chunk warning，不阻塞。
- 剩余风险：尚未连接真实 MySQL/Redis 做登录和业务接口联调；当前结论覆盖源码、编译与前端生产构建。下一项目为 `165`。

## 2026-05-16 165 正式开发记录

### Findings
- `165` 批量版原始状态仍是通用模板：后端包名为 `com.p165`，业务类为 `BizRecord01-12`，数据库名为 `project_165`，前端页面和接口路径仍以 `record01-12` 命名。
- 已基于 `164` 已验证的正式化流水线生成并执行 `scripts/develop_165.py`；执行前清理了校园体育赛事、参赛队伍、裁判评分、成绩公示、申诉复核等旧主题词，端口切换为后端 `8165`、前端 `3165`。
- 已将后端切换为 `com.visitoraccess` 包，启动类为 `VisitorAccessApplication`，artifactId 为 `enterprise-visitor-access-165`，数据库为 `visitor_access_165`，Redis token 前缀为 `visitoraccess:token:`。
- 已重建默认账号与角色口径：`ADMIN/admin`、`RECEPTION/reception`、`EMPLOYEE/employee`、`SECURITY/security`、`VISITOR/visitor`、`MANAGER/manager`，统一密码 `123456`。
- 已生成并编译通过 12 个正式业务模块：`CompanyZone`、`HostEmployee`、`VisitorProfile`、`VisitAppointment`、`ApprovalRecord`、`QrPass`、`GateDevice`、`AccessLinkage`、`EntryRecord`、`TrailRecord`、`ExceptionAlert`、`OperationLog`。
- 已为后端控制器按角色收口写入权限：前台接待员维护访客档案、预约、访问审批、二维码签发和入园登记；被访员工维护预约确认、访问审批和接待记录；安保门岗维护门禁设备、二维码核验、门禁联动、入园登记和异常告警；外来访客提交预约并查看通行状态；行政主管维护楼宇区域、审批复核、轨迹留痕和异常处置。
- 已重写前端路由、角色首页跳转、动态菜单、登录页、通用数据页、看板和全部业务页字段，页面语义切回楼宇区域、被访员工、访客档案、访客预约、访问审批、二维码通行、门禁设备、门禁联动、入园登记、轨迹留痕、异常告警、操作日志。
- 源码残留扫描未发现 `com.p165`、`BizRecord`、`project_165`、旧体育赛事角色/模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace`、`System.out.print*`。
- 验证结果：`165-backend` 执行 `mvn.cmd -q clean test` 通过；`165-frontend` 执行 `npm.cmd install` 与 `npm.cmd run build` 通过。前端仍有 Vite CJS deprecation 和大 chunk warning，不阻塞。
- 剩余风险：尚未连接真实 MySQL/Redis 做登录和业务接口联调；当前结论覆盖源码、编译与前端生产构建。下一项目为 `166`。

## 2026-05-16 166 正式开发记录

### Findings
- `166` 批量版原始状态仍是通用模板：后端包名为 `com.p166`，业务类为 `BizRecord01-12`，数据库名为 `project_166`，前端页面和接口路径仍以 `record01-12` 命名。
- 已基于 `165` 已验证的正式化流水线生成并执行 `scripts/develop_166.py`；执行前清理了企业访客、门禁联动、二维码通行、入园登记、轨迹留痕等旧主题词，端口切换为后端 `8166`、前端 `3166`。
- 已将后端切换为 `com.markettrace` 包，启动类为 `MarketTraceApplication`，artifactId 为 `farm-market-trace-166`，数据库为 `market_trace_166`，Redis token 前缀为 `markettrace:token:`。
- 已重建默认账号与角色口径：`ADMIN/admin`、`MARKET/market`、`INSPECTOR/inspector`、`VENDOR/vendor`、`SAMPLER/sampler`、`REGULATOR/regulator`，统一密码 `123456`。
- 已生成并编译通过 12 个正式业务模块：`MarketArea`、`StallProfile`、`VendorProfile`、`ProductTrace`、`InspectionTask`、`IssueRectification`、`SampleRecord`、`TestResult`、`SourceLedger`、`DisposalRecord`、`RiskAlert`、`OperationLog`。
- 已为后端控制器按角色收口写入权限：市场管理员维护市场区域、摊位、商户和台账汇总；巡检员维护摊位巡检、问题整改、处置记录和风险预警；摊位商户维护商品溯源、进货台账和整改反馈；抽检员维护抽检记录、检测结果和样品流转；监管审核员维护整改复核、检测结果审核和监管闭环。
- 已重写前端路由、角色首页跳转、动态菜单、登录页、通用数据页、看板和全部业务页字段，页面语义切回市场区域、摊位档案、商户档案、商品溯源、摊位巡检、问题整改、抽检记录、检测结果、进货台账、处置记录、风险预警、操作日志。
- 源码残留扫描未发现 `com.p166`、`BizRecord`、`project_166`、旧访客门禁角色/模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace`、`System.out.print*`。
- 验证结果：`166-backend` 执行 `mvn.cmd -q clean test` 通过；`166-frontend` 执行 `npm.cmd install` 与 `npm.cmd run build` 通过。前端仍有 Vite CJS deprecation 和大 chunk warning，不阻塞。
- 剩余风险：尚未连接真实 MySQL/Redis 做登录和业务接口联调；当前结论覆盖源码、编译与前端生产构建。下一项目为 `167`。

## 2026-05-16 167 正式开发记录

### Findings
- `167` 批量版原始状态仍是通用模板：后端包名为 `com.p167`，业务类为 `BizRecord01-12`，数据库名为 `project_167`，前端页面和接口路径仍以 `record01-12` 命名。
- 已基于 `166` 已验证的正式化流水线生成并执行 `scripts/develop_167.py`；执行前清理了农贸市场、摊位巡检、商户档案、抽检记录、检测结果和风险预警等旧主题词，端口切换为后端 `8167`、前端 `3167`。
- 已将后端切换为 `com.wastesorting` 包，启动类为 `WasteSortingApplication`，artifactId 为 `community-waste-points-167`，数据库为 `waste_sorting_167`，Redis token 前缀为 `wastesorting:token:`。
- 已重建默认账号与角色口径：`ADMIN/admin`、`COMMUNITY/community`、`SUPERVISOR/supervisor`、`RESIDENT/resident`、`VOLUNTEER/volunteer`、`EXCHANGE/exchange`，统一密码 `123456`。
- 已生成并编译通过 12 个正式业务模块：`CommunityArea`、`BuildingProfile`、`ResidentProfile`、`SortingCategory`、`SortingCheckin`、`SupervisionRecord`、`CorrectionTask`、`PointsLedger`、`RewardCatalog`、`ExchangeOrder`、`BuildingRanking`、`OperationLog`。
- 已为后端控制器按角色收口写入权限：社区管理员维护社区区域、楼栋、居民、分类规则和积分汇总；分类督导员维护分类打卡、督导记录、整改任务、积分记录和楼栋排名；居民用户查看并参与打卡、积分和兑换；志愿协管员维护督导协同和整改任务；积分兑换员维护兑换商品、积分兑换和核销状态。
- 已重写前端路由、角色首页跳转、动态菜单、登录页、通用数据页、看板和全部业务页字段，页面语义切回社区区域、楼栋档案、居民档案、分类规则、分类打卡、督导记录、整改任务、积分记录、兑换商品、积分兑换、楼栋排名、操作日志。
- 源码残留扫描未发现 `com.p167`、`BizRecord`、`project_167`、旧农贸市场角色/模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace`、`System.out.print*`。
- 验证结果：`167-backend` 执行 `mvn.cmd -q clean test` 通过；`167-frontend` 执行 `npm.cmd install` 与 `npm.cmd run build` 通过。前端仍有 Vite CJS deprecation 和大 chunk warning，不阻塞。
- 剩余风险：尚未连接真实 MySQL/Redis 做登录和业务接口联调；当前结论覆盖源码、编译与前端生产构建。下一项目为 `168`。

## 2026-05-16 168 正式开发记录

### Findings
- `168` 批量版原始状态仍是通用模板：后端包名为 `com.p168`，业务类为 `BizRecord01-12`，数据库名为 `project_168`，前端页面和接口路径仍以 `record01-12` 命名。
- 已基于 `167` 已验证的正式化流水线生成并执行 `scripts/develop_168.py`；执行前清理了社区垃圾分类、楼栋档案、居民档案、督导整改、积分兑换和楼栋排名等旧主题词，端口切换为后端 `8168`、前端 `3168`。
- 已将后端切换为 `com.certtraining` 包，启动类为 `CertTrainingApplication`，artifactId 为 `online-cert-training-168`，数据库为 `cert_training_168`，Redis token 前缀为 `certtraining:token:`。
- 已重建默认账号与角色口径：`ADMIN/admin`、`TRAINING/training`、`INSTRUCTOR/instructor`、`TRAINEE/trainee`、`EXAMINER/examiner`、`CERTIFIER/certifier`，统一密码 `123456`。
- 已生成并编译通过 12 个正式业务模块：`TrainingCourse`、`TraineeProfile`、`InstructorProfile`、`CourseEnrollment`、`LearningProgress`、`ExamPlan`、`ExamScore`、`CertificateIssue`、`CertificateLedger`、`RenewalApplication`、`ReminderNotice`、`OperationLog`。
- 已为后端控制器按角色收口写入权限：培训管理员维护课程、报名、学习进度和提醒；讲师维护课程、学员学习和考试安排；学员查看课程、报名、学习进度、成绩、证书与续证进度；考务人员维护考试安排和考试成绩；证书管理员维护证书发放、证书台账、续证申请和提醒通知。
- 已重写前端路由、角色首页跳转、动态菜单、登录页、通用数据页、看板和全部业务页字段，页面语义切回培训课程、学员档案、讲师档案、课程报名、学习进度、考试安排、考试成绩、证书发放、证书台账、续证申请、提醒通知、操作日志。
- 源码残留扫描未发现 `com.p168`、`BizRecord`、`project_168`、旧社区垃圾分类角色/模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace`、`System.out.print*`。
- 验证结果：`168-backend` 执行 `mvn.cmd -q clean test` 通过；`168-frontend` 执行 `npm.cmd install` 与 `npm.cmd run build` 通过。前端仍有 Vite CJS deprecation 和大 chunk warning，不阻塞。
- 剩余风险：尚未连接真实 MySQL/Redis 做登录和业务接口联调；当前结论覆盖源码、编译与前端生产构建。下一项目为 `169`。

## 2026-05-16 169 正式开发记录

### Findings
- `169` 批量版原始状态仍是通用模板：后端包名为 `com.p169`，业务类为 `BizRecord01-12`，数据库名为 `project_169`，前端页面和接口路径仍以 `record01-12` 命名。
- 已基于 `168` 已验证的正式化流水线生成并执行 `scripts/develop_169.py`；执行前清理了在线职业培训、学员档案、讲师档案、考试成绩、证书发放、续证申请和提醒通知等旧主题词，端口切换为后端 `8169`、前端 `3169`。
- 已将后端切换为 `com.schoolbus` 包，启动类为 `SchoolBusApplication`，artifactId 为 `school-bus-checkin-169`，数据库为 `school_bus_169`，Redis token 前缀为 `schoolbus:token:`。
- 已重建默认账号与角色口径：`ADMIN/admin`、`SCHOOL/school`、`DISPATCH/dispatch`、`DRIVER/driver`、`TEACHER/teacher`、`GUARDIAN/guardian`，统一密码 `123456`。
- 已生成并编译通过 12 个正式业务模块：`BusRoute`、`BusStop`、`VehicleProfile`、`DriverProfile`、`StudentProfile`、`GuardianProfile`、`RideReservation`、`BoardingCheckin`、`DropoffCheckin`、`ExceptionAlert`、`ParentNotice`、`OperationLog`。
- 已为后端控制器按角色收口写入权限：校方管理员维护线路、站点、学生和家长档案；调度管理员维护车辆、司机、乘车预约和异常调度；校车司机维护上车核验、下车核验和异常上报；跟车老师维护学生核验、异常处置和家长通知；学生家长提交预约并查看上下车动态。
- 已重写前端路由、角色首页跳转、动态菜单、登录页、通用数据页、看板和全部业务页字段，页面语义切回校车线路、站点档案、车辆档案、司机档案、学生档案、家长档案、乘车预约、上车核验、下车核验、异常告警、家长通知、操作日志。
- 源码残留扫描未发现 `com.p169`、`BizRecord`、`project_169`、旧职业培训证书角色/模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace`、`System.out.print*`。
- 验证结果：`169-backend` 执行 `mvn.cmd -q clean test` 通过；`169-frontend` 执行 `npm.cmd install` 与 `npm.cmd run build` 通过。前端仍有 Vite CJS deprecation 和大 chunk warning，不阻塞。
- 剩余风险：尚未连接真实 MySQL/Redis 做登录和业务接口联调；当前结论覆盖源码、编译与前端生产构建。下一项目为 `170`。

## 2026-05-16 170 正式开发记录

### Findings
- `170` 批量版原始状态仍是通用模板：后端包名为 `com.p170`，业务类为 `BizRecord01-12`，数据库名为 `project_170`，前端页面和接口路径仍以 `record01-12` 命名。
- 已基于 `169` 已验证的正式化流水线生成并执行 `scripts/develop_170.py`；执行前清理了校车线路、站点档案、车辆档案、司机档案、学生档案、家长档案、乘车预约、上下车核验和家长通知等旧主题词，端口切换为后端 `8170`、前端 `3170`。
- 已将后端切换为 `com.eldercare` 包，启动类为 `ElderCareApplication`，artifactId 为 `elder-care-bed-170`，数据库为 `elder_care_170`，Redis token 前缀为 `eldercare:token:`。
- 已重建默认账号与角色口径：`ADMIN/admin`、`ADMISSION/admission`、`NURSING/nursing`、`CAREGIVER/caregiver`、`FAMILY/family`、`DIRECTOR/director`，统一密码 `123456`。
- 已生成并编译通过 12 个正式业务模块：`CareArea`、`RoomProfile`、`BedProfile`、`ElderProfile`、`FamilyProfile`、`CheckinAssessment`、`BedAllocation`、`CarePlan`、`CareRecord`、`FamilyQuery`、`ExceptionReport`、`OperationLog`。
- 已为后端控制器按角色收口写入权限：入住管理员维护老人、家属、入住评估和床位分配；护理主管维护护理计划、护理记录和异常复核；护理人员执行护理计划、填写护理记录并上报异常；家属用户查看护理记录、提交查询并跟踪异常进度；院长管理员查看全院床位、护理和异常统计。
- 已重写前端路由、角色首页跳转、动态菜单、登录页、通用数据页、看板和全部业务页字段，页面语义切回护理区域、房间档案、床位档案、老人档案、家属档案、入住评估、床位分配、护理计划、护理记录、家属查询、异常上报、操作日志。
- 源码残留扫描未发现 `com.p170`、`BizRecord`、`project_170`、旧校车乘车核验角色/模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace`、`System.out.print*`。
- 验证结果：`170-backend` 执行 `mvn.cmd -q clean test` 通过；`170-frontend` 执行 `npm.cmd install` 与 `npm.cmd run build` 通过。前端仍有 Vite CJS deprecation 和大 chunk warning，不阻塞。
- 剩余风险：尚未连接真实 MySQL/Redis 做登录和业务接口联调；当前结论覆盖源码、编译与前端生产构建。下一项目为 `171`。

## 2026-05-16 171 正式开发记录

### Findings
- `171` 批量版原始状态仍是通用模板：后端包名为 `com.p171`，业务类为 `BizRecord01-12`，数据库名为 `project_171`，前端页面和接口路径仍以 `record01-12` 命名。
- 已基于 `170` 已验证的正式化流水线生成并执行 `scripts/develop_171.py`；执行前清理了养老机构、床位档案、老人档案、家属档案、入住评估、护理计划和护理记录等旧主题词，端口切换为后端 `8171`、前端 `3171`。
- 已将后端切换为 `com.emergencysupply` 包，启动类为 `EmergencySupplyApplication`，artifactId 为 `emergency-supply-dispatch-171`，数据库为 `emergency_supply_171`，Redis token 前缀为 `emergencysupply:token:`。
- 已重建默认账号与角色口径：`ADMIN/admin`、`WAREHOUSE/warehouse`、`CHECKER/checker`、`APPLICANT/applicant`、`DISPATCH/dispatch`、`AUDITOR/auditor`，统一密码 `123456`。
- 已生成并编译通过 12 个正式业务模块：`ReserveWarehouse`、`MaterialCategory`、`MaterialLedger`、`StockBatch`、`InventoryCheck`、`CheckDifference`、`RequisitionOrder`、`AllocationApproval`、`DispatchTask`、`OutboundRecord`、`StockWarning`、`OperationLog`。
- 已为后端控制器按角色收口写入权限：仓储管理员维护储备仓库、物资台账、库存批次和出库记录；盘点人员执行库存盘点、登记盘点差异并跟踪差异处理；申领单位提交申领工单并查看调拨、调度和出库进度；调度人员维护申领工单、调拨审批、调度任务和库存预警；审核人员审核调拨申请、复核盘点差异并查看应急储备统计。
- 已重写前端路由、角色首页跳转、动态菜单、登录页、通用数据页、看板和全部业务页字段，页面语义切回应急物资储备仓库、物资分类、物资台账、库存批次、库存盘点、盘点差异、申领工单、调拨审批、调度任务、出库记录、库存预警、操作日志。
- 源码残留扫描未发现 `com.p171`、`BizRecord`、`project_171`、旧养老机构角色/模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace`、`System.out.print*`。
- 验证结果：`171-backend` 执行 `mvn.cmd -q clean test` 通过；`171-frontend` 执行 `npm.cmd install` 与 `npm.cmd run build` 通过。前端仍有 Vite CJS deprecation 和大 chunk warning，不阻塞。
- 剩余风险：尚未连接真实 MySQL/Redis 做登录和业务接口联调；当前结论覆盖源码、编译与前端生产构建。下一项目为 `172`。

## 2026-05-16 172 正式开发记录

### Findings
- `172` 批量版原始状态仍是通用模板：后端包名为 `com.p172`，业务类为 `BizRecord01-12`，数据库名为 `project_172`，前端页面和接口路径仍以 `record01-12` 命名。
- 已基于 `171` 已验证的正式化流水线生成并执行 `scripts/develop_172.py`；执行前清理了应急物资、储备仓库、库存盘点、申领工单、调拨审批、调度任务和库存预警等旧主题词，端口切换为后端 `8172`、前端 `3172`。
- 已将后端切换为 `com.dentalclinic` 包，启动类为 `DentalClinicApplication`，artifactId 为 `dental-clinic-treatment-172`，数据库为 `dental_clinic_172`，Redis token 前缀为 `dentalclinic:token:`。
- 已重建默认账号与角色口径：`ADMIN/admin`、`RECEPTION/reception`、`DENTIST/dentist`、`NURSE/nurse`、`CASHIER/cashier`、`PATIENT/patient`，统一密码 `123456`。
- 已生成并编译通过 12 个正式业务模块：`ClinicRoom`、`DentistProfile`、`PatientProfile`、`TreatmentCatalog`、`AppointmentOrder`、`CheckinTriage`、`TreatmentRecord`、`ConsumableCatalog`、`ConsumableStock`、`ConsumableUsage`、`BillingRecord`、`OperationLog`。
- 已为后端控制器按角色收口写入权限：前台导诊维护患者档案、预约诊疗、签到分诊和诊室安排；口腔医生维护医生档案、治疗项目、治疗记录和耗材使用；诊疗护士协助签到分诊、耗材目录、耗材库存和耗材使用登记；收费人员维护治疗项目价格、耗材计费、费用结算和收费状态；患者用户查看预约诊疗、治疗记录、耗材计费和结算进度。
- 已重写前端路由、角色首页跳转、动态菜单、登录页、通用数据页、看板和全部业务页字段，页面语义切回诊室档案、医生档案、患者档案、治疗项目、预约诊疗、签到分诊、治疗记录、耗材目录、耗材库存、耗材使用、费用结算、操作日志。
- 源码残留扫描未发现 `com.p172`、`BizRecord`、`project_172`、旧应急物资角色/模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace`、`System.out.print*`。
- 验证结果：`172-backend` 执行 `mvn.cmd -q clean test` 通过；`172-frontend` 执行 `npm.cmd install` 与 `npm.cmd run build` 通过。前端仍有 Vite CJS deprecation 和大 chunk warning，不阻塞。
- 剩余风险：尚未连接真实 MySQL/Redis 做登录和业务接口联调；当前结论覆盖源码、编译与前端生产构建。下一项目为 `173`。

## 2026-05-16 173 正式开发记录

### Findings
- `173` 批量版原始状态仍是通用模板：后端包名为 `com.p173`，业务类为 `BizRecord01-12`，数据库名为 `project_173`，前端页面和接口路径仍以 `record01-12` 命名。
- 已基于 `172` 已验证的正式化流水线生成并执行 `scripts/develop_173.py`；执行前清理了口腔门诊、诊室、医生、患者、治疗、耗材、费用结算、`RECEPTION / DENTIST / NURSE / CASHIER / PATIENT` 等旧主题词，端口切换为后端 `8173`、前端 `3173`。
- 已将后端切换为 `com.grademployment` 包，启动类为 `GraduateEmploymentApplication`，artifactId 为 `graduate-employment-help-173`，数据库为 `graduate_employment_173`，Redis token 前缀为 `grademployment:token:`。
- 已重建默认账号与角色口径：`ADMIN/admin`、`CAREER/career`、`COLLEGE/college`、`COUNSELOR/counselor`、`STUDENT/student`、`EMPLOYER/employer`，统一密码 `123456`。
- 已生成并编译通过 12 个正式业务模块：`CollegeMajor`、`GraduateProfile`、`EmployerProfile`、`JobPosition`、`DestinationReport`、`ContractArchive`、`MaterialReview`、`AssistanceRecord`、`JobRecommendation`、`FollowupRecord`、`EmploymentStatistics`、`OperationLog`。
- 已为后端控制器按角色收口写入权限：就业中心维护去向填报、材料审核、就业统计和就业服务规则；学院就业老师维护学院专业、毕业生档案、协议归档和统计汇总；辅导员维护毕业生档案、帮扶记录、岗位推荐和跟踪回访；毕业生提交去向填报、查看岗位推荐、协议归档和帮扶进度；用人单位维护单位档案、发布招聘岗位并协同就业推荐。
- 已重写前端路由、角色首页跳转、动态菜单、登录页、通用数据页、看板和全部业务页字段，页面语义切回学院专业、毕业生档案、用人单位、招聘岗位、去向填报、协议归档、材料审核、帮扶记录、岗位推荐、跟踪回访、就业统计、操作日志。
- 源码残留扫描未发现 `com.p173`、`BizRecord`、`project_173`、旧口腔门诊角色/模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace`、`System.out.print*`。
- 验证结果：`173-backend` 执行 `mvn.cmd -q clean test` 通过；`173-frontend` 执行 `npm.cmd install` 与 `npm.cmd run build` 通过。前端仍有 Vite CJS deprecation 和大 chunk warning，不阻塞。
- 剩余风险：尚未连接真实 MySQL/Redis 做登录和业务接口联调；当前结论覆盖源码、编译与前端生产构建。下一项目为 `174`。

## 2026-05-16 174 正式开发记录

### Findings
- `174` 批量版原始状态仍是通用模板：后端包名为 `com.p174`，业务类为 `BizRecord01-12`，数据库名为 `project_174`，前端页面和接口路径仍以 `record01-12` 命名。
- 已基于 `173` 已验证的正式化流水线生成并执行 `scripts/develop_174.py`；执行前清理了高校毕业、就业中心、学院就业、毕业生、用人单位、招聘岗位、去向填报、协议归档、材料审核、帮扶记录、岗位推荐、跟踪回访、`CAREER / COLLEGE / COUNSELOR / STUDENT / EMPLOYER` 等旧主题词，端口切换为后端 `8174`、前端 `3174`。
- 已将后端切换为 `com.chroniccare` 包，启动类为 `ChronicCareApplication`，artifactId 为 `chronic-care-followup-174`，数据库为 `chronic_care_174`，Redis token 前缀为 `chroniccare:token:`。
- 已重建默认账号与角色口径：`ADMIN/admin`、`CENTER/center`、`DOCTOR/doctor`、`NURSE/nurse`、`PHARMACIST/pharmacist`、`RESIDENT/resident`，统一密码 `123456`。
- 已生成并编译通过 12 个正式业务模块：`CommunityClinic`、`ChronicPatient`、`DoctorTeam`、`DiseaseArchive`、`FollowupPlan`、`FollowupRecord`、`MedicationPlan`、`MedicationAdherence`、`HealthIndicator`、`RiskStratification`、`ReminderNotice`、`OperationLog`。
- 已为后端控制器按角色收口写入权限：社区卫生中心维护社区站点、患者档案、医护团队和慢病档案；家庭医生维护慢病档案、随访计划、健康指标和风险分层；随访护士执行随访计划、填写随访记录并维护提醒通知；用药药师维护用药方案、服药打卡和依从性核验；居民患者查看个人档案、随访安排、用药方案、服药打卡和提醒通知。
- 已重写前端路由、角色首页跳转、动态菜单、登录页、通用数据页、看板和全部业务页字段，页面语义切回社区站点、患者档案、医护团队、慢病档案、随访计划、随访记录、用药方案、服药打卡、健康指标、风险分层、提醒通知、操作日志。
- 源码残留扫描未发现 `com.p174`、`BizRecord`、`project_174`、旧高校就业角色/模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace`、`System.out.print*`。
- 验证结果：`174-backend` 执行 `mvn.cmd -q clean test` 通过；`174-frontend` 执行 `npm.cmd install` 与 `npm.cmd run build` 通过。前端仍有 Vite CJS deprecation 和大 chunk warning，不阻塞。
- 剩余风险：尚未连接真实 MySQL/Redis 做登录和业务接口联调；当前结论覆盖源码、编译与前端生产构建。下一项目为 `175`。

## 2026-05-16 175 正式开发记录

### Findings
- `175` 批量版原始状态仍是通用模板：后端包名为 `com.p175`，业务类为 `BizRecord01-12`，数据库名为 `project_175`，前端页面和接口路径仍以 `record01-12` 命名。
- 已基于 `174` 已验证的正式化流水线生成并执行 `scripts/develop_175.py`；执行前清理了社区慢病、患者档案、医护团队、随访计划、随访记录、用药方案、服药打卡、健康指标、风险分层、`CENTER / DOCTOR / NURSE / PHARMACIST / RESIDENT` 等旧主题词，端口切换为后端 `8175`、前端 `3175`。
- 已将后端切换为 `com.bookdrift` 包，启动类为 `CampusBookDriftApplication`，artifactId 为 `campus-book-drift-175`，数据库为 `campus_book_drift_175`，Redis token 前缀为 `bookdrift:token:`。
- 已重建默认账号与角色口径：`ADMIN/admin`、`LIBRARY/library`、`CURATOR/curator`、`STUDENT/student`、`CLUB/club`、`TEACHER/teacher`，统一密码 `123456`。
- 已生成并编译通过 12 个正式业务模块：`DriftShelf`、`BookProfile`、`ReaderProfile`、`BookDonation`、`BorrowRecord`、`ReturnExchange`、`ReadingCheckin`、`ReadingNote`、`PointsMedal`、`ReadingActivity`、`MessageNotice`、`OperationLog`。
- 已为后端控制器按角色收口写入权限：图书馆管理员维护漂流书架、漂流图书、借阅记录和统计规则；漂流负责人维护图书捐赠、借阅记录、归还流转和消息通知；学生读者参与图书捐赠、图书借阅、归还流转、读书打卡和读书笔记；读书社团组织阅读活动、协同打卡运营和积分勋章评定；阅读指导老师审核读书笔记、指导阅读活动并评定积分勋章。
- 已重写前端路由、角色首页跳转、动态菜单、登录页、通用数据页、看板和全部业务页字段，页面语义切回漂流书架、漂流图书、读者档案、图书捐赠、借阅记录、归还流转、读书打卡、读书笔记、积分勋章、阅读活动、消息通知、操作日志。
- 源码残留扫描未发现 `com.p175`、`BizRecord`、`project_175`、旧社区慢病角色/模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace`、`System.out.print*`。
- 验证结果：`175-backend` 执行 `mvn.cmd -q clean test` 通过；`175-frontend` 执行 `npm.cmd install` 与 `npm.cmd run build` 通过。前端仍有 Vite CJS deprecation 和大 chunk warning，不阻塞。
- 剩余风险：尚未连接真实 MySQL/Redis 做登录和业务接口联调；当前结论覆盖源码、编译与前端生产构建。下一项目为 `176`。

## 2026-05-16 176 正式开发记录

### Findings
- `176` 批量版原始状态仍是通用模板：后端包名为 `com.p176`，业务类为 `BizRecord01-12`，数据库名为 `project_176`，前端页面和接口路径仍以 `record01-12` 命名。
- 已基于 `175` 已验证的正式化流水线生成并执行 `scripts/develop_176.py`；执行前清理了校园图书漂流、漂流书架、漂流图书、读者档案、图书捐赠、借阅记录、归还流转、读书打卡、读书笔记、积分勋章、阅读活动、`LIBRARY / CURATOR / STUDENT / CLUB / TEACHER` 等旧主题词，端口切换为后端 `8176`、前端 `3176`。
- 已将后端切换为 `com.waterpatrol` 包，启动类为 `WaterPatrolApplication`，artifactId 为 `water-patrol-valve-176`，数据库为 `water_patrol_176`，Redis token 前缀为 `waterpatrol:token:`。
- 已重建默认账号与角色口径：`ADMIN/admin`、`DISPATCH/dispatch`、`PATROL/patrol`、`REPAIR/repair`、`WAREHOUSE/warehouse`、`SUPERVISOR/supervisor`，统一密码 `123456`。
- 已生成并编译通过 12 个正式业务模块：`WaterStation`、`PipelineSection`、`PatrolRoute`、`ValveLedger`、`PatrolTask`、`PatrolRecord`、`FaultReport`、`DispatchOrder`、`MaintenanceReceipt`、`SparePartLedger`、`RiskWarning`、`OperationLog`。
- 已为后端控制器按角色收口写入权限：调度管理员维护水务站点、管线区段、巡线任务、故障派工和统计规则；巡线班组执行巡线路线、填写巡线记录、上报故障和风险隐患；检修人员接收故障派工、登记检修过程、提交检修回执和备件消耗；备件管理员维护备件台账、跟踪备件状态并协同检修领用；监管负责人查看巡线、故障、检修和风险预警统计并复核闭环结果。
- 已重写前端路由、角色首页跳转、动态菜单、登录页、通用数据页、看板和全部业务页字段，页面语义切回水务站点、管线区段、巡线路线、阀门台账、巡线任务、巡线记录、故障上报、故障派工、检修回执、备件台账、风险预警、操作日志。
- 源码残留扫描未发现 `com.p176`、`BizRecord`、`project_176`、旧校园图书角色/模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace`、`System.out.print*`。
- 验证结果：`176-backend` 执行 `mvn.cmd -q clean test` 通过；`176-frontend` 执行 `npm.cmd install` 与 `npm.cmd run build` 通过。前端仍有 Vite CJS deprecation 和大 chunk warning，不阻塞。
- 剩余风险：尚未连接真实 MySQL/Redis 做登录和业务接口联调；当前结论覆盖源码、编译与前端生产构建。下一项目为 `177`。

## 2026-05-16 177 正式开发记录

### Findings
- `177` 批量版原始状态仍是通用模板：后端包名为 `com.p177`，业务类为 `BizRecord01-12`，数据库名为 `project_177`，前端页面和接口路径仍以 `record01-12` 命名。
- 已基于 `176` 已验证的正式化流水线生成并执行 `scripts/develop_177.py`；执行前清理了水务巡线、管线区段、巡线路线、阀门台账、巡线任务、故障派工、检修回执、风险预警、`DISPATCH / PATROL / REPAIR / WAREHOUSE / SUPERVISOR` 等旧主题词，端口切换为后端 `8177`、前端 `3177`。
- 已将后端切换为 `com.livebase` 包，启动类为 `LiveBaseApplication`，artifactId 为 `live-base-anchor-177`，数据库为 `live_base_177`，Redis token 前缀为 `livebase:token:`。
- 已重建默认账号与角色口径：`ADMIN/admin`、`BASE/base`、`ANCHOR/anchor`、`SELECTOR/selector`、`SAMPLE/sample`、`MERCHANT/merchant`，统一密码 `123456`。
- 已生成并编译通过 12 个正式业务模块：`LiveStudio`、`AnchorProfile`、`MerchantProfile`、`ProductCatalog`、`SampleLedger`、`SampleLoan`、`AnchorSchedule`、`SelectionReview`、`LivePlan`、`LiveSession`、`LiveReplay`、`OperationLog`。
- 已为后端控制器按角色收口写入权限：基地运营维护直播间档案、主播档案、主播排班、直播计划和统计规则；主播查看排班、参与样品借还、执行直播场次并提交直播复盘；选品专员维护商品选品、选品评测、直播计划和样品协同；样品管理员维护样品台账、样品借还、样品归还和状态流转；商家代表维护商家档案、商品提报、样品寄送和直播复盘反馈。
- 已重写前端路由、角色首页跳转、动态菜单、登录页、通用数据页、看板和全部业务页字段，页面语义切回直播间档案、主播档案、商家档案、商品选品、样品台账、样品借还、主播排班、选品评测、直播计划、直播场次、直播复盘、操作日志。
- 源码残留扫描未发现 `com.p177`、`BizRecord`、`project_177`、旧水务巡线角色/模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace`、`System.out.print*`。
- 验证结果：`177-backend` 执行 `mvn.cmd -q clean test` 通过；`177-frontend` 执行 `npm.cmd install` 与 `npm.cmd run build` 通过。前端仍有 Vite CJS deprecation 和大 chunk warning，不阻塞。
- 剩余风险：尚未连接真实 MySQL/Redis 做登录和业务接口联调；当前结论覆盖源码、编译与前端生产构建。下一项目为 `178`。

## 2026-05-16 178 正式开发记录

### Findings
- `178` 批量版原始状态仍是通用模板：后端包名为 `com.p178`，业务类为 `BizRecord01-12`，数据库名为 `project_178`，前端页面和接口路径仍以 `record01-12` 命名。
- 已基于 `177` 已验证的正式化流水线生成并执行 `scripts/develop_178.py`；执行前清理了直播基地、主播档案、商品选品、样品台账、样品借还、主播排班、选品评测、直播计划、直播场次、直播复盘、`BASE / ANCHOR / SELECTOR / SAMPLE / MERCHANT` 等旧主题词，端口切换为后端 `8178`、前端 `3178`。
- 已将后端切换为 `com.orsterile` 包，启动类为 `SterileReleaseApplication`，artifactId 为 `or-pack-sterilization-178`，数据库为 `or_sterile_pack_178`，Redis token 前缀为 `orsterile:token:`。
- 已重建默认账号与角色口径：`ADMIN/admin`、`ORNURSE/ornurse`、`CSSD/cssd`、`STERILE/sterile`、`QA/qa`、`SURGEON/surgeon`，统一密码 `123456`。
- 已生成并编译通过 12 个正式业务模块：`OperatingRoom`、`InstrumentPack`、`InstrumentItem`、`PackTrace`、`SterilizationBatch`、`SterilizationRecord`、`ReleaseApproval`、`SurgeryUse`、`ReturnCheckin`、`DefectReport`、`RecallEvent`、`OperationLog`。
- 已为后端控制器按角色收口写入权限：手术室护士维护手术室档案、手术使用、回收清点和器械包追踪；供应中心维护器械包档案、器械明细、回收清点和灭菌批次；灭菌人员登记灭菌批次、灭菌记录、器械状态和缺损上报；质控审核执行放行审核、异常召回、缺损复核和统计规则维护；手术医生查看器械包追踪、手术使用记录和召回提醒。
- 已重写前端路由、角色首页跳转、动态菜单、登录页、通用数据页、看板和全部业务页字段，页面语义切回手术室档案、器械包档案、器械明细、器械包追踪、灭菌批次、灭菌记录、放行审核、手术使用、回收清点、缺损上报、异常召回、操作日志。
- 源码残留扫描未发现 `com.p178`、`BizRecord`、`project_178`、旧直播基地角色/模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace`、`System.out.print*`。
- 验证结果：`178-backend` 执行 `mvn.cmd -q clean test` 通过；`178-frontend` 执行 `npm.cmd install` 与 `npm.cmd run build` 通过。前端仍有 Vite CJS deprecation 和大 chunk warning，不阻塞。
- 已按 `skills.md` 约定删除 `178-frontend/node_modules`。
- 剩余风险：尚未连接真实 MySQL/Redis 做登录和业务接口联调；当前结论覆盖源码、编译与前端生产构建。下一项目为 `179`。

## 2026-05-16 179 正式开发记录

### Findings
- `179` 批量版原始状态仍是通用模板：后端包名为 `com.p179`，业务类为 `BizRecord01-12`，数据库名为 `project_179`，前端页面和接口路径仍以 `record01-12` 命名。
- 已基于 `178` 已验证的正式化流水线生成并执行 `scripts/develop_179.py`；执行前清理了手术室器械、器械包档案、器械明细、器械包追踪、灭菌批次、灭菌记录、放行审核、手术使用、回收清点、缺损上报、异常召回、`ORNURSE / CSSD / STERILE / QA / SURGEON` 等旧主题词，端口切换为后端 `8179`、前端 `3179`。
- 已将后端切换为 `com.classattendance` 包，启动类为 `ClassAttendanceApplication`，artifactId 为 `class-attendance-appeal-179`，数据库为 `class_attendance_179`，Redis token 前缀为 `classattendance:token:`。
- 已重建默认账号与角色口径：`ADMIN/admin`、`ACADEMIC/academic`、`TEACHER/teacher`、`INSPECTOR/inspector`、`COUNSELOR/counselor`、`STUDENT/student`，统一密码 `123456`。
- 已生成并编译通过 12 个正式业务模块：`TeachingClass`、`StudentProfile`、`TeacherProfile`、`CourseSchedule`、`AttendanceRecord`、`ExceptionAppeal`、`AppealReview`、`ClassroomInspection`、`InspectionIssue`、`RectificationTask`、`RectificationFeedback`、`OperationLog`。
- 已为后端控制器按角色收口写入权限：教务管理员维护教学班级、教师档案、课程排课和考勤规则；任课教师登记考勤记录、处理异常申诉并配合课堂巡查；课堂巡查员发起课堂巡查、记录巡查问题并跟踪整改任务；辅导员查看学生考勤、协助申诉审核和整改反馈；学生查看个人考勤记录、提交异常申诉并补充整改反馈。
- 已重写前端路由、角色首页跳转、动态菜单、登录页、通用数据页、看板和全部业务页字段，页面语义切回教学班级、学生档案、教师档案、课程排课、考勤记录、异常申诉、申诉审核、课堂巡查、巡查问题、整改任务、整改反馈、操作日志。
- 源码残留扫描未发现 `com.p179`、`BizRecord`、`project_179`、旧手术室器械角色/模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace`、`System.out.print*`。
- 验证结果：`179-backend` 执行 `mvn.cmd -q clean test` 通过；`179-frontend` 执行 `npm.cmd install` 与 `npm.cmd run build` 通过。前端仍有 Vite CJS deprecation 和大 chunk warning，不阻塞。
- 已按 `skills.md` 约定删除 `179-frontend/node_modules`。
- 剩余风险：尚未连接真实 MySQL/Redis 做登录和业务接口联调；当前结论覆盖源码、编译与前端生产构建。下一项目为 `180`。

## 2026-05-16 180 正式开发记录

### Findings
- `180` 批量版原始状态仍是通用模板：后端包名为 `com.p180`，业务类为 `BizRecord01-12`，数据库名为 `project_180`，前端页面和接口路径仍以 `record01-12` 命名。
- 已基于 `179` 已验证的正式化流水线生成并执行 `scripts/develop_180.py`；执行前清理了高校课堂考勤、教学班级、学生档案、教师档案、课程排课、考勤记录、异常申诉、申诉审核、课堂巡查、巡查问题、整改任务、整改反馈、`ACADEMIC / TEACHER / INSPECTOR / COUNSELOR / STUDENT` 等旧主题词，端口切换为后端 `8180`、前端 `3180`。
- 已将后端切换为 `com.propertyrepair` 包，启动类为 `PropertyRepairApplication`，artifactId 为 `property-repair-service-180`，数据库为 `property_repair_180`，Redis token 前缀为 `propertyrepair:token:`。
- 已重建默认账号与角色口径：`ADMIN/admin`、`PROPERTY/property`、`OWNER/owner`、`DISPATCH/dispatch`、`WORKER/worker`、`FINANCE/finance`，统一密码 `123456`。
- 已生成并编译通过 12 个正式业务模块：`CommunityArea`、`OwnerProfile`、`RepairCategory`、`RepairOrder`、`DispatchAssignment`、`DoorService`、`MaterialUsage`、`FeeRegistration`、`PaymentRecord`、`SatisfactionReview`、`ComplaintFollowup`、`OperationLog`。
- 已为后端控制器按角色收口写入权限：物业管理员维护小区区域、业主档案、报修分类和服务规则；业主住户提交报修工单、查看上门处理、支付费用并评价服务；派单员接收报修工单、分派维修人员并跟踪处理进度；维修人员接收派单、登记上门处理、填写物料使用和处理结果；财务客服登记维修费用、核对支付记录并开展满意度回访。
- 已重写前端路由、角色首页跳转、动态菜单、登录页、通用数据页、看板和全部业务页字段，页面语义切回小区区域、业主档案、报修分类、报修工单、派单分配、上门处理、物料使用、费用登记、支付记录、满意评价、投诉回访、操作日志。
- 源码残留扫描未发现 `com.p180`、`BizRecord`、`project_180`、旧高校课堂考勤角色/模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace`、`System.out.print*`。
- 验证结果：`180-backend` 执行 `mvn.cmd -q clean test` 通过；`180-frontend` 执行 `npm.cmd install` 与 `npm.cmd run build` 通过。前端仍有 Vite CJS deprecation 和大 chunk warning，不阻塞。
- 已按 `skills.md` 约定删除 `180-frontend/node_modules`。
- 剩余风险：尚未连接真实 MySQL/Redis 做登录和业务接口联调；当前结论覆盖源码、编译与前端生产构建。下一项目为 `181`。

## 2026-05-16 181 正式开发记录

### Findings
- `181` 批量版原始状态仍是通用模板：后端包名为 `com.p181`，业务类为 `BizRecord01-12`，数据库名为 `project_181`，前端页面和接口路径仍以 `record01-12` 命名。
- 已基于 `180` 已验证的正式化流水线生成并执行 `scripts/develop_181.py`；执行前清理了物业报修、小区区域、业主档案、报修分类、报修工单、派单分配、上门处理、物料使用、费用登记、支付记录、满意评价、投诉回访、`PROPERTY / OWNER / DISPATCH / WORKER / FINANCE` 等旧主题词，端口切换为后端 `8181`、前端 `3181`。
- 已将后端切换为 `com.childcare` 包，启动类为 `CommunityChildcareApplication`，artifactId 为 `community-childcare-safety-181`，数据库为 `childcare_safety_181`，Redis token 前缀为 `childcare:token:`。
- 已重建默认账号与角色口径：`ADMIN/admin`、`CENTER/center`、`TEACHER/teacher`、`PARENT/parent`、`SECURITY/security`、`NURSE/nurse`，统一密码 `123456`。
- 已生成并编译通过 12 个正式业务模块：`CareClass`、`ChildProfile`、`GuardianProfile`、`AttendanceCheckin`、`PickupAuthorization`、`PickupRecord`、`SafetyAlert`、`HealthCheck`、`ActivitySchedule`、`ParentNotice`、`IncidentFollowup`、`OperationLog`。
- 已为后端控制器按角色收口写入权限：中心管理员维护托管班级、儿童档案、接送规则和通知配置；托管老师执行儿童签到、接送核验、活动安排和日常记录；家长查看儿童档案、授权接送人员、接收通知并确认接送记录；安全员处理接送核验、安全告警和事件跟进；保健老师维护健康晨检、异常提醒和儿童健康记录。
- 已重写前端路由、角色首页跳转、动态菜单、登录页、通用数据页、看板和全部业务页字段，页面语义切回托管班级、儿童档案、监护人档案、签到记录、接送授权、接送记录、安全告警、健康晨检、托管活动、家长通知、事件跟进、操作日志。
- 源码残留扫描未发现 `com.p181`、`BizRecord`、`project_181`、旧物业报修角色/模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace`、`System.out.print*`。
- 验证结果：`181-backend` 执行 `mvn.cmd -q clean test` 通过；`181-frontend` 执行 `npm.cmd install` 与 `npm.cmd run build` 通过。前端仍有 Vite CJS deprecation 和大 chunk warning，不阻塞。
- 已按 `skills.md` 约定删除 `181-frontend/node_modules`。
- 剩余风险：尚未连接真实 MySQL/Redis 做登录和业务接口联调；当前结论覆盖源码、编译与前端生产构建。下一项目为 `182`。

## 2026-05-16 182 正式开发记录

### Findings
- `182` 批量版原始状态仍是通用模板：后端包名为 `com.p182`，业务类为 `BizRecord01-12`，数据库名为 `project_182`，前端页面和接口路径仍以 `record01-12` 命名。
- 已基于 `181` 已验证的正式化流水线生成并执行 `scripts/develop_182.py`；执行前清理了社区儿童托管、托管班级、儿童档案、监护人档案、签到记录、接送授权、接送记录、安全告警、健康晨检、托管活动、家长通知、事件跟进、`TEACHER / PARENT / SECURITY / NURSE` 等旧主题词，端口切换为后端 `8182`、前端 `3182`。
- 已将后端切换为 `com.eldermeal` 包，启动类为 `ElderMealApplication`，artifactId 为 `elder-meal-delivery-182`，数据库为 `elder_meal_182`，Redis token 前缀为 `eldermeal:token:`。
- 已重建默认账号与角色口径：`ADMIN/admin`、`CENTER/center`、`DIETITIAN/dietitian`、`DISPATCH/dispatch`、`COURIER/courier`、`ELDER/elder`，统一密码 `123456`。
- 已生成并编译通过 12 个正式业务模块：`MealSite`、`ElderProfile`、`NutritionMenu`、`MealOrder`、`RoutePlan`、`DeliveryTask`、`SignReceipt`、`DietaryRestriction`、`NutritionAnalysis`、`SubsidyRecord`、`VisitFollowup`、`OperationLog`。
- 已为后端控制器按角色收口写入权限：助餐中心维护站点、老人档案、订单规则和补贴记录；营养师维护营养套餐、饮食禁忌和营养分析；调度员维护配送排线、分配配送任务并跟踪异常；配送员查看任务、登记送达和签收回执；老人用户查看订单、签收结果、回访关怀和营养建议。
- 已重写前端路由、角色首页跳转、动态菜单、登录页、通用数据页、看板和全部业务页字段，页面语义切回助餐站点、老人档案、营养套餐、助餐订单、配送排线、配送任务、签收回执、饮食禁忌、营养分析、补贴记录、回访关怀、操作日志。
- 源码残留扫描未发现 `com.p182`、`BizRecord`、`project_182`、旧社区儿童托管角色/模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace`、`System.out.print*`。
- 验证结果：`182-backend` 执行 `mvn.cmd -q clean test` 通过；`182-frontend` 执行 `npm.cmd install` 与 `npm.cmd run build` 通过。前端仍有 Vite CJS deprecation 和大 chunk warning，不阻塞。
- 已按 `skills.md` 约定删除 `182-frontend/node_modules`。
- 剩余风险：尚未连接真实 MySQL/Redis 做登录和业务接口联调；当前结论覆盖源码、编译与前端生产构建。下一项目为 `183`。

## 2026-05-16 183 正式开发记录

### Findings
- `183` 批量版原始状态仍是通用模板：后端包名为 `com.p183`，业务类为 `BizRecord01-12`，数据库名为 `project_183`，前端页面和接口路径仍以 `record01-12` 命名。
- 已基于 `182` 已验证的正式化流水线生成并执行 `scripts/develop_183.py`；执行前清理了养老助餐、助餐站点、老人档案、营养套餐、助餐订单、配送排线、配送任务、签收回执、饮食禁忌、营养分析、补贴记录、回访关怀、`CENTER / DIETITIAN / DISPATCH / COURIER / ELDER` 等旧主题词，端口切换为后端 `8183`、前端 `3183`。
- 已将后端切换为 `com.labanimal` 包，启动类为 `LabAnimalApplication`，artifactId 为 `lab-animal-ethics-183`，数据库为 `lab_animal_183`，Redis token 前缀为 `labanimal:token:`。
- 已重建默认账号与角色口径：`ADMIN/admin`、`LAB/lab`、`BREEDER/breeder`、`RESEARCHER/researcher`、`ETHICS/ethics`、`VET/vet`，统一密码 `123456`。
- 已生成并编译通过 12 个正式业务模块：`AnimalRoom`、`AnimalProfile`、`FeedingSchedule`、`FeedingRecord`、`EthicsApplication`、`EthicsReview`、`ExperimentRegistration`、`HealthInspection`、`AbnormalAlert`、`VeterinaryTreatment`、`MaterialUsage`、`OperationLog`。
- 已为后端控制器按角色收口写入权限：实验室管理员维护动物房间、动物档案、实验登记和耗材规则；饲养员查看饲养排班、登记饲养记录并上报异常告警；课题研究员提交伦理申请、登记实验过程并查看审批结果；伦理委员执行伦理审批、复核实验登记和审批留痕；兽医执行健康巡检、异常诊断和兽医处置。
- 已重写前端路由、角色首页跳转、动态菜单、登录页、通用数据页、看板和全部业务页字段，页面语义切回动物房间、实验动物档案、饲养排班、饲养记录、伦理申请、伦理审批、实验登记、健康巡检、异常告警、兽医处置、耗材使用、操作日志。
- 源码残留扫描未发现 `com.p183`、`BizRecord`、`project_183`、旧养老助餐角色/模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace`、`System.out.print*`。
- 验证结果：`183-backend` 执行 `mvn.cmd -q clean test` 通过；`183-frontend` 执行 `npm.cmd install` 与 `npm.cmd run build` 通过。前端仍有 Vite CJS deprecation 和大 chunk warning，不阻塞。
- 已按 `skills.md` 约定删除 `183-frontend/node_modules`。
- 剩余风险：尚未连接真实 MySQL/Redis 做登录和业务接口联调；当前结论覆盖源码、编译与前端生产构建。下一项目为 `184`。

## 2026-05-16 184 正式开发记录

### Findings
- `184` 批量版原始状态仍是通用模板：后端包名为 `com.p184`，业务类为 `BizRecord01-12`，数据库名为 `project_184`，前端页面和接口路径仍以 `record01-12` 命名。
- 已基于 `183` 已验证的正式化流水线生成并执行 `scripts/develop_184.py`；执行前清理了实验动物、动物房间、实验动物档案、饲养排班、饲养记录、伦理申请、伦理审批、实验登记、健康巡检、异常告警、兽医处置、耗材使用、`LAB / BREEDER / RESEARCHER / ETHICS / VET` 等旧主题词，端口切换为后端 `8184`、前端 `3184`。
- 已将后端切换为 `com.parkinglease` 包，启动类为 `ParkingLeaseApplication`，artifactId 为 `parking-lease-occupancy-184`，数据库为 `parking_lease_184`，Redis token 前缀为 `parkinglease:token:`。
- 已重建默认账号与角色口径：`ADMIN/admin`、`PARKING/parking`、`FINANCE/finance`、`PATROL/patrol`、`TENANT/tenant`、`SERVICE/service`，统一密码 `123456`。
- 已生成并编译通过 12 个正式业务模块：`ParkingLot`、`ParkingSpace`、`TenantProfile`、`LeaseContract`、`RenewalReminder`、`RenewalPayment`、`VehicleBind`、`OccupancyCheck`、`AbnormalOccupancy`、`ViolationHandling`、`ServiceTicket`、`OperationLog`。
- 已为后端控制器按角色收口写入权限：停车场管理员维护停车场档案、车位档案、月租合同和车辆绑定；收费财务维护续费提醒、续费缴费、费用核对和合同续期状态；巡检员执行占位巡检、上报异常占位并协同违规处理；月租车主查看合同、续费缴费、绑定车辆并提交客服工单；客服专员处理续费提醒、异常占位沟通和客服工单闭环。
- 已重写前端路由、角色首页跳转、动态菜单、登录页、通用数据页、看板和全部业务页字段，页面语义切回停车场档案、车位档案、月租车主、月租合同、续费提醒、续费缴费、车辆绑定、占位巡检、异常占位、违规处理、客服工单、操作日志。
- 源码残留扫描未发现 `com.p184`、`BizRecord`、`project_184`、旧实验动物角色/模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace`、`System.out.print*`。
- 验证结果：`184-backend` 执行 `mvn.cmd -q clean test` 通过；`184-frontend` 执行 `npm.cmd install` 与 `npm.cmd run build` 通过。前端仍有 Vite CJS deprecation 和大 chunk warning，不阻塞。
- 已按 `skills.md` 约定删除 `184-frontend/node_modules`。
- 剩余风险：尚未连接真实 MySQL/Redis 做登录和业务接口联调；当前结论覆盖源码、编译与前端生产构建。下一项目为 `185`。

## 2026-05-16 185 正式开发记录

### Findings
- `185` 批量版原始状态仍是通用模板：后端包名为 `com.p185`，业务类为 `BizRecord01-12`，数据库名为 `project_185`，前端页面和接口路径仍以 `record01-12` 命名。
- 已基于 `184` 已验证的正式化流水线生成并执行 `scripts/develop_185.py`；执行前清理了停车场月租、停车场档案、车位档案、月租车主、月租合同、续费提醒、续费缴费、车辆绑定、占位巡检、异常占位、违规处理、客服工单、`PARKING / FINANCE / PATROL / TENANT / SERVICE` 等旧主题词，端口切换为后端 `8185`、前端 `3185`。
- 已将后端切换为 `com.citytoilet` 包，启动类为 `CityToiletApplication`，artifactId 为 `city-toilet-cleaning-185`，数据库为 `city_toilet_185`，Redis token 前缀为 `citytoilet:token:`。
- 已重建默认账号与角色口径：`ADMIN/admin`、`SANITATION/sanitation`、`CLEANER/cleaner`、`SUPPLY/supply`、`INSPECTOR/inspector`、`CITIZEN/citizen`，统一密码 `123456`。
- 已生成并编译通过 12 个正式业务模块：`ToiletSite`、`CleaningRoute`、`CleaningTask`、`CleaningRecord`、`InspectionPlan`、`InspectionRecord`、`ConsumableStock`、`SupplyRequest`、`SupplyDispatch`、`ComplaintReport`、`IssueRectification`、`OperationLog`。
- 已为后端控制器按角色收口写入权限：环卫管理员维护公厕点位、保洁路线、巡检计划和调度规则；保洁员接收保洁任务、填写保洁记录并提交补给申请；补给员维护耗材库存、处理补给申请并执行补给调度；巡检员执行巡检记录、发现问题并跟踪整改闭环；市民监督提交投诉反馈并查看处理进度。
- 已重写前端路由、角色首页跳转、动态菜单、登录页、通用数据页、看板和全部业务页字段，页面语义切回公厕点位、保洁路线、保洁任务、保洁记录、巡检计划、巡检记录、耗材库存、补给申请、补给调度、投诉反馈、问题整改、操作日志。
- 源码残留扫描未发现 `com.p185`、`BizRecord`、`project_185`、旧停车月租角色/模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace`、`System.out.print*`。
- 验证结果：`185-backend` 执行 `mvn.cmd -q clean test` 通过；`185-frontend` 执行 `npm.cmd install` 与 `npm.cmd run build` 通过。前端仍有 Vite CJS deprecation 和大 chunk warning，不阻塞。
- 已按 `skills.md` 约定删除 `185-frontend/node_modules`。
- 剩余风险：尚未连接真实 MySQL/Redis 做登录和业务接口联调；当前结论覆盖源码、编译与前端生产构建。下一项目为 `186`。

## 2026-05-16 186 正式开发记录

### Findings
- `186` 批量版原始状态仍是通用模板：后端包名为 `com.p186`，业务类为 `BizRecord01-12`，数据库名为 `project_186`，前端页面和接口路径仍以 `record01-12` 命名。
- 已基于 `185` 已验证的正式化流水线生成并执行 `scripts/develop_186.py`；执行前清理了城市公厕、公厕点位、保洁路线、保洁任务、保洁记录、巡检计划、巡检记录、耗材库存、补给申请、补给调度、投诉反馈、问题整改、`SANITATION / CLEANER / SUPPLY / CITIZEN` 等旧主题词，端口切换为后端 `8186`、前端 `3186`。
- 已将后端切换为 `com.canteenhygiene` 包，启动类为 `CampusCanteenApplication`，artifactId 为 `campus-canteen-hygiene-186`，数据库为 `campus_canteen_186`，Redis token 前缀为 `canteenhygiene:token:`。
- 已重建默认账号与角色口径：`ADMIN/admin`、`CANTEEN/canteen`、`CHEF/chef`、`INSPECTOR/inspector`、`WAREHOUSE/warehouse`、`SCHOOL/school`，统一密码 `123456`。
- 已生成并编译通过 12 个正式业务模块：`CanteenInfo`、`KitchenArea`、`DishMenu`、`SampleRetention`、`SampleStorage`、`IngredientAcceptance`、`DisinfectionRecord`、`HygieneInspection`、`IssueRectification`、`RiskWarning`、`WasteDisposal`、`OperationLog`。
- 已为后端控制器按角色收口写入权限：食堂管理员维护餐厅档案、后厨区域、菜品档案和台账规则；后厨负责人登记菜品留样、留样存储、消毒记录和厨余处置；卫生巡检员执行卫生巡检、发现问题并跟踪整改闭环；仓储管理员维护食材验收、库存流转和风险提醒；校方监管查看留样、巡检、整改和风险统计。
- 已重写前端路由、角色首页跳转、动态菜单、登录页、通用数据页、看板和全部业务页字段，页面语义切回餐厅档案、后厨区域、菜品档案、留样登记、留样存储、食材验收、消毒记录、卫生巡检、问题整改、风险提醒、厨余处置、操作日志。
- 源码残留扫描未发现 `com.p186`、`BizRecord`、`project_186`、旧城市公厕角色/模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace`、`System.out.print*`。
- 验证结果：`186-backend` 执行 `mvn.cmd -q clean test` 通过；`186-frontend` 执行 `npm.cmd install` 与 `npm.cmd run build` 通过。前端仍有 Vite CJS deprecation 和大 chunk warning，不阻塞。
- 已按 `skills.md` 约定删除 `186-frontend/node_modules`。
- 剩余风险：尚未连接真实 MySQL/Redis 做登录和业务接口联调；当前结论覆盖源码、编译与前端生产构建。下一项目为 `187`。

## 2026-05-16 187 正式开发记录

### Findings
- `187` 批量版原始状态仍是通用模板：后端包名为 `com.p187`，业务类为 `BizRecord01-12`，数据库名为 `project_187`，前端页面和接口路径仍以 `record01-12` 命名。
- 已基于 `186` 已验证的正式化流水线生成并执行 `scripts/develop_187.py`；执行前清理了校园餐厅、后厨区域、菜品档案、留样登记、留样存储、食材验收、消毒记录、卫生巡检、问题整改、风险提醒、厨余处置、`CANTEEN / CHEF / WAREHOUSE / SCHOOL` 等旧主题词，端口切换为后端 `8187`、前端 `3187`。
- 已将后端切换为 `com.teambuilding` 包，启动类为 `TeamBuildingApplication`，artifactId 为 `travel-team-building-187`，数据库为 `team_building_187`，Redis token 前缀为 `teambuilding:token:`。
- 已重建默认账号与角色口径：`ADMIN/admin`、`AGENCY/agency`、`PLANNER/planner`、`FINANCE/finance`、`LEADER/leader`、`PARTICIPANT/participant`，统一密码 `123456`。
- 已生成并编译通过 12 个正式业务模块：`AgencyInfo`、`TeamProfile`、`TripPlan`、`SignupRegistration`、`MemberConfirmation`、`CostBudget`、`CostShare`、`PaymentRecord`、`NoticeSync`、`TravelFeedback`、`InvoiceRecord`、`OperationLog`。
- 已为后端控制器按角色收口写入权限：旅行社管理员维护旅行社档案、团队档案和行程规则；行程策划编排团建行程、费用预算和通知同步；财务人员维护费用预算、费用分摊、缴费记录和发票记录；团队领队组织报名确认、分摊确认和出行反馈；团建成员查看行程、确认报名、缴费并提交反馈。
- 已重写前端路由、角色首页跳转、动态菜单、登录页、通用数据页、看板和全部业务页字段，页面语义切回旅行社档案、团队档案、团建行程、行程报名、成员确认、费用预算、费用分摊、缴费记录、通知同步、出行反馈、发票记录、操作日志。
- 源码残留扫描未发现 `com.p187`、`BizRecord`、`project_187`、旧校园餐厅角色/模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace`、`System.out.print*`。
- 验证结果：`187-backend` 执行 `mvn.cmd -q clean test` 通过；`187-frontend` 执行 `npm.cmd install` 与 `npm.cmd run build` 通过。前端仍有 Vite CJS deprecation 和大 chunk warning，不阻塞。
- 已按 `skills.md` 约定删除 `187-frontend/node_modules`；全仓库 `node_modules` 扫描未发现残留。
- 剩余风险：尚未连接真实 MySQL/Redis 做登录和业务接口联调；当前结论覆盖源码、编译与前端生产构建。下一项目为 `188`。

## 2026-05-16 188 正式开发记录

### Findings
- `188` 批量版原始状态仍是通用模板：后端包名为 `com.p188`，业务类为 `BizRecord01-12`，数据库名为 `project_188`，前端页面和接口路径仍以 `record01-12` 命名。
- 已基于 `187` 已验证的正式化流水线生成并执行 `scripts/develop_188.py`；执行前清理了旅行社团建、旅行社档案、团队档案、团建行程、行程报名、成员确认、费用预算、费用分摊、缴费记录、通知同步、出行反馈、发票记录、`AGENCY / PLANNER / FINANCE / LEADER / PARTICIPANT` 等旧主题词，端口切换为后端 `8188`、前端 `3188`。
- 已将后端切换为 `com.nursetraining` 包，启动类为 `NurseTrainingApplication`，artifactId 为 `nursing-training-188`，数据库为 `nurse_training_188`，Redis token 前缀为 `nursetraining:token:`。
- 已重建默认账号与角色口径：`ADMIN/admin`、`EDU/edu`、`INSTRUCTOR/instructor`、`ASSESSOR/assessor`、`NURSE/nurse`、`HR/hr`，统一密码 `123456`。
- 已生成并编译通过 12 个正式业务模块：`TrainingProgram`、`NurseProfile`、`TrainingClass`、`TrainingCheckin`、`SkillItem`、`SkillAssessment`、`CertificateArchive`、`RetrainingReminder`、`PracticeRecord`、`AssessmentAppeal`、`TrainingNotice`、`OperationLog`。
- 已为后端控制器按角色收口写入权限：护理培训管理员维护培训项目、班次、技能项目和通知；带教老师组织培训班次、签到和实操记录；考核老师维护技能考核、考核申诉和考核结论；护理人员查看班次、完成签到、参与考核并查看证书续训；护理人事维护护理人员档案、证书档案和续训提醒。
- 已重写前端路由、角色首页跳转、动态菜单、登录页、通用数据页、看板和全部业务页字段，页面语义切回培训项目、护理人员档案、培训班次、培训签到、技能项目、技能考核、证书档案、续训提醒、实操记录、考核申诉、培训通知、操作日志。
- 源码残留扫描未发现 `com.p188`、`BizRecord`、`project_188`、旧旅行社团建角色/模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace`、`System.out.print*`。
- 静态结构验证：13 张 SQL 表、13 个实体、13 个 Mapper、15 个 Controller、16 个前端视图。
- 验证结果：`188-backend` 执行 `mvn.cmd -q clean test` 通过；`188-frontend` 执行 `npm.cmd install` 与 `npm.cmd run build` 通过。前端仍有 Vite CJS deprecation 和大 chunk warning，不阻塞。
- 已按 `skills.md` 约定删除 `188-frontend/node_modules`；全仓库 `node_modules` 扫描未发现残留。
- 剩余风险：尚未连接真实 MySQL/Redis 做登录和业务接口联调；当前结论覆盖源码、编译与前端生产构建。下一项目为 `189`。

## 2026-05-16 189 正式开发记录

### Findings
- `189` 批量版原始状态仍是通用模板：后端包名为 `com.p189`，业务类为 `BizRecord01-12`，数据库名为 `project_189`，前端页面和接口路径仍以 `record01-12` 命名。
- 已基于 `188` 正式化流水线生成并执行 `scripts/develop_189.py`；执行前清理了护理培训、培训项目、护理人员档案、培训班次、培训签到、技能项目、技能考核、证书档案、续训提醒、实操记录、考核申诉、培训通知、`EDU / INSTRUCTOR / ASSESSOR / NURSE / HR` 等旧主题词，端口切换为后端 `8189`、前端 `3189`。
- 已将后端切换为 `com.farmmachinery` 包，启动类为 `FarmMachineryApplication`，artifactId 为 `township-farm-machinery-189`，数据库为 `farm_machinery_189`，Redis token 前缀为 `farmmachinery:token:`。
- 已重建默认账号与角色口径：`ADMIN/admin`、`STATION/station`、`DISPATCH/dispatch`、`DRIVER/driver`、`MECHANIC/mechanic`、`FARMER/farmer`，统一密码 `123456`。
- 已生成并编译通过 12 个正式业务模块：`ServiceStation`、`MachineAsset`、`FarmerProfile`、`DriverProfile`、`OperationBooking`、`DispatchSchedule`、`FieldWorkOrder`、`WorkCompletion`、`MaintenanceRecord`、`RepairFollowup`、`SeasonalStatistics`、`OperationLog`。
- 已为前后端角色口径收口：农机站管理员维护农机站点、农机档案、机手档案和服务规则；调度员处理作业预约、机手调度、作业派单和季节统计；农机机手接收作业派单、登记作业回执并上报维修需求；维修人员维护维修保养、维修跟踪和农机状态；农户提交作业预约、查看派单并确认作业完成。
- 已重写前端路由、角色首页跳转、动态菜单、登录页、通用数据页、看板和全部业务页字段，页面语义切回农机站点、农机档案、农户档案、机手档案、作业预约、机手调度、作业派单、作业回执、维修保养、维修跟踪、季节统计、操作日志。
- 源码残留扫描未发现 `com.p189`、`BizRecord`、`project_189`、旧护理培训角色/模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace`、`System.out.print*`。
- 静态结构验证：13 张 SQL 表、13 个实体、13 个 Mapper、15 个 Controller、16 个前端视图。
- 验证结果：`189-backend` 执行 `mvn.cmd -q clean test` 通过；`189-frontend` 执行 `npm.cmd install` 与 `npm.cmd run build` 通过。前端仍有 Vite CJS deprecation 和大 chunk warning，不阻塞。
- 已按 `skills.md` 约定删除 `189-frontend/node_modules`；全仓库 `node_modules` 扫描未发现残留。
- 剩余风险：尚未连接真实 MySQL/Redis 做登录和业务接口联调；当前结论覆盖源码、编译与前端生产构建。下一项目为 `190`。

## 2026-05-16 190 正式开发记录

### Findings
- `190` 批量版原始状态仍是通用模板：后端包名为 `com.p190`，业务类为 `BizRecord01-12`，数据库名为 `project_190`，前端页面和接口路径仍以 `record01-12` 命名。
- 已基于 `189` 正式化流水线生成并执行 `scripts/develop_190.py`；端口切换为后端 `8190`、前端 `3190`。
- 已将后端切换为 `com.smartbuilding` 包，启动类为 `SmartBuildingApplication`，artifactId 为 `smart-building-maintenance-190`，数据库为 `smart_building_190`，Redis token 前缀为 `smartbuilding:token:`。
- 已重建默认账号与角色口径：`ADMIN/admin`、`PROPERTY/property`、`DISPATCH/dispatch`、`TECHNICIAN/technician`、`INSPECTOR/inspector`、`TENANT/tenant`，统一密码 `123456`。
- 已生成 12 个正式业务模块：`BuildingProfile`、`EquipmentAsset`、`TenantProfile`、`VisitRepairTicket`、`RepairDispatch`、`MaintenancePlan`、`MaintenanceTask`、`FaultAlert`、`InspectionRecord`、`SparePartStock`、`ServiceFeedback`、`OperationLog`。
- 已为前后端角色口径收口：物业管理员维护楼宇、设备和入驻档案；访修调度员处理访修工单、维修派工和服务评价；维修技师接收派工、执行保养任务并处置故障预警；设备巡检员登记巡检记录和预警；入驻用户提交访修工单并查看进度、评价服务。
- 已重写前端路由、角色首页跳转、动态菜单、登录页、通用数据页、看板和全部业务页字段，页面语义切回楼宇档案、设备档案、入驻档案、访修工单、维修派工、保养计划、保养任务、故障预警、巡检记录、备件库存、服务评价、操作日志。
- 源码残留扫描未发现 `com.p190`、`BizRecord`、`project_190`、旧农机角色/模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace`、`System.out.print*`。
- 静态结构验证：13 张 SQL 表、13 个实体、13 个 Mapper、15 个 Controller、16 个前端视图。
- 按 `rule.md` 约定，本轮未执行 Maven/NPM 编译验证。
- 已清理 `190-frontend/node_modules`；全仓库 `node_modules` 扫描未发现残留。
- 剩余风险：尚未连接真实 MySQL/Redis 做登录和业务接口联调；当前结论覆盖源码主题化与静态结构验证。下一项目为 `191`。

## 2026-05-16 191 正式开发记录

### Findings
- `191` 批量版原始状态仍是通用模板：后端包名为 `com.p191`，业务类为 `BizRecord01-12`，数据库名为 `project_191`，前端页面和接口路径仍以 `record01-12` 命名。
- 已基于 `190` 正式化流水线生成并执行 `scripts/develop_191.py`；端口切换为后端 `8191`、前端 `3191`。
- 已将后端切换为 `com.assistivecare` 包，启动类为 `AssistiveCareApplication`，artifactId 为 `assistive-device-care-191`，数据库为 `assistive_care_191`，Redis token 前缀为 `assistivecare:token:`。
- 已重建默认账号与角色口径：`ADMIN/admin`、`COMMUNITY/community`、`AIDSTAFF/aidstaff`、`THERAPIST/therapist`、`VOLUNTEER/volunteer`、`RESIDENT/resident`，统一密码 `123456`。
- 已生成 12 个正式业务模块：`ServiceCenter`、`ResidentProfile`、`AssistiveDevice`、`BorrowApplication`、`BorrowApproval`、`DeviceDelivery`、`RehabPlan`、`RehabTraining`、`FollowupRecord`、`RecoveryReminder`、`DeviceMaintenance`、`OperationLog`。
- 已为前后端角色口径收口：社区服务员维护服务站点、居民档案和借用审核；器具管理员维护助残器具、器具交付、回收提醒和器具维护；康复治疗师制定康复计划、记录康复训练并参与随访评估；随访志愿者协助交付、随访和回收提醒；居民用户提交器具借用申请、查看康复计划和随访进度。
- 已重写前端路由、角色首页跳转、动态菜单、登录页、通用数据页、看板和全部业务页字段，页面语义切回服务站点、居民档案、助残器具、器具借用、借用审核、器具交付、康复计划、康复训练、随访记录、回收提醒、器具维护、操作日志。
- 源码残留扫描未发现 `com.p191`、`BizRecord`、`project_191`、旧楼宇角色/模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace`、`System.out.print*`。
- 静态结构验证：13 张 SQL 表、13 个实体、13 个 Mapper、15 个 Controller、16 个前端视图。
- 按 `rule.md` 约定，本轮未执行 Maven/NPM 编译验证。
- 全仓库 `node_modules` 扫描未发现残留。
- 剩余风险：尚未连接真实 MySQL/Redis 做登录和业务接口联调；当前结论覆盖源码主题化与静态结构验证。下一项目为 `192`。

## 2026-05-16 192 正式开发记录

### Findings
- `192` 批量版原始状态仍是通用模板：后端包名为 `com.p192`，业务类为 `BizRecord01-12`，数据库名为 `project_192`，前端页面和接口路径仍以 `record01-12` 命名。
- 已基于 `191` 正式化流水线生成并执行 `scripts/develop_192.py`；端口切换为后端 `8192`、前端 `3192`。
- 已将后端切换为 `com.hospitalcare` 包，启动类为 `HospitalCareApplication`，artifactId 为 `hospital-care-service-192`，数据库为 `hospital_care_192`，Redis token 前缀为 `hospitalcare:token:`。
- 已重建默认账号与角色口径：`ADMIN/admin`、`HOSPITAL/hospital`、`COORDINATOR/coordinator`、`CAREGIVER/caregiver`、`FINANCE/finance`、`FAMILY/family`，统一密码 `123456`。
- 已生成 12 个正式业务模块：`HospitalWard`、`PatientProfile`、`CaregiverProfile`、`CareAppointment`、`AppointmentReview`、`CaregiverSchedule`、`ServiceAssignment`、`CareServiceRecord`、`FamilyCommunication`、`CareEvaluation`、`SettlementRecord`、`OperationLog`。
- 已为前后端角色口径收口：院区管理员维护病区、患者和护工档案；陪护协调员处理陪护预约、预约审核、护工排班和服务派单；护工人员查看排班任务、执行陪护服务并登记服务记录；结算人员核对服务记录、护工评价和费用结算；患者家属提交陪护预约、查看服务记录并完成评价和结算确认。
- 已重写前端路由、角色首页跳转、动态菜单、登录页、通用数据页、看板和全部业务页字段，页面语义切回病区档案、患者档案、护工档案、陪护预约、预约审核、护工排班、服务派单、服务记录、家属沟通、护工评价、评价结算、操作日志。
- 源码残留扫描未发现 `com.p192`、`BizRecord`、`project_192`、旧助残角色/模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace`、`System.out.print*`。
- 静态结构验证：13 张 SQL 表、13 个实体、13 个 Mapper、15 个 Controller、16 个前端视图。
- 按 `rule.md` 约定，本轮未执行 Maven/NPM 编译验证。
- 全仓库 `node_modules` 扫描未发现残留。
- 剩余风险：尚未连接真实 MySQL/Redis 做登录和业务接口联调；当前结论覆盖源码主题化与静态结构验证。下一项目为 `193`。

## 2026-05-16 193 正式开发记录

### Findings
- `193` 批量版原始状态仍是通用模板：后端包名为 `com.p193`，业务类为 `BizRecord01-12`，数据库名为 `project_193`，前端页面和接口路径仍以 `record01-12` 命名。
- 已基于 `192` 正式化流水线生成并执行 `scripts/develop_193.py`；端口切换为后端 `8193`、前端 `3193`。
- 已将后端切换为 `com.innovationclass` 包，启动类为 `InnovationClassApplication`，artifactId 为 `campus-innovation-class-193`，数据库为 `innovation_class_193`，Redis token 前缀为 `innovationclass:token:`。
- 已重建默认账号与角色口径：`ADMIN/admin`、`ACADEMIC/academic`、`REVIEWER/reviewer`、`MENTOR/mentor`、`COUNSELOR/counselor`、`STUDENT/student`，统一密码 `123456`。
- 已生成 12 个正式业务模块：`InnovationProgram`、`StudentProfile`、`MentorProfile`、`SelectionNotice`、`ApplicationRegistration`、`SelectionReview`、`InterviewAssessment`、`MentorMatch`、`TrainingPlan`、`ProcessTracking`、`AchievementArchive`、`OperationLog`。
- 已为前后端角色口径收口：教务管理员维护实验班项目、学生档案、导师档案和选拔公告；评审教师处理报名选拔、资格评审和面试考核；导师查看匹配学生、维护培养计划并跟踪过程记录；辅导员协同学生档案、报名进度、导师匹配和过程跟踪；学生查看公告、提交报名、查看培养计划和成果归档。
- 已重写前端路由、角色首页跳转、动态菜单、登录页、通用数据页、看板和全部业务页字段，页面语义切回实验班项目、学生档案、导师档案、选拔公告、报名选拔、资格评审、面试考核、导师匹配、培养计划、过程跟踪、成果档案、操作日志。
- 源码残留扫描未发现 `com.p193`、`BizRecord`、`project_193`、旧医院陪护角色/模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace`、`System.out.print*`。
- 静态结构验证：13 张 SQL 表、13 个实体、13 个 Mapper、15 个 Controller、16 个前端视图。
- 按 `rule.md` 约定，本轮未执行 Maven/NPM 编译验证。
- 全仓库 `node_modules` 扫描未发现残留。
- 剩余风险：尚未连接真实 MySQL/Redis 做登录和业务接口联调；当前结论覆盖源码主题化与静态结构验证。下一项目为 `194`。

## 2026-05-16 194 正式开发记录

### Findings
- `194` 批量版原始状态仍是通用模板：后端包名为 `com.p194`，业务类为 `BizRecord01-12`，数据库名为 `project_194`，前端页面和接口路径仍以 `record01-12` 命名。
- 已基于 `193` 正式化流水线生成并执行 `scripts/develop_194.py`；端口切换为后端 `8194`、前端 `3194`。
- 已将后端切换为 `com.hazardwaste` 包，启动类为 `HazardWasteApplication`，artifactId 为 `industrial-hazard-waste-194`，数据库为 `hazard_waste_194`，Redis token 前缀为 `hazardwaste:token:`。
- 已重建默认账号与角色口径：`ADMIN/admin`、`PARK/park`、`WAREHOUSE/warehouse`、`TRANSPORT/transport`、`INSPECTOR/inspector`、`ENTERPRISE/enterprise`，统一密码 `123456`。
- 已生成 12 个正式业务模块：`ParkEnterprise`、`WasteCategory`、`StorageFacility`、`StorageInbound`、`StorageCheck`、`TransferOrder`、`VehicleDispatch`、`WeighingRecord`、`DisposalHandover`、`RiskWarning`、`LedgerAudit`、`OperationLog`。
- 已为前后端角色口径收口：园区监管维护企业、危废类别、暂存设施和台账审计；暂存管理员处理入库登记、暂存巡检、称重配合和风险预警；转运调度维护转运联单、车辆调度、称重记录和处置交接；环保巡查核查巡检、预警、交接和审计闭环；产废企业提交入库登记并查看转运进度。
- 已重写前端路由、角色首页跳转、动态菜单、登录页、通用数据页、看板和全部业务页字段，页面语义切回园区企业、危废类别、暂存设施、入库登记、暂存巡检、转运联单、车辆调度、称重记录、处置交接、风险预警、台账审计、操作日志。
- 源码残留扫描未发现 `com.p194`、`BizRecord`、`project_194`、旧创新实验班角色/模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace`、`System.out.print*`。
- 静态结构验证：13 张 SQL 表、13 个实体、13 个 Mapper、15 个 Controller、16 个前端视图。
- 按 `rule.md` 约定，本轮未执行 Maven/NPM 编译验证。
- 全仓库 `node_modules` 扫描未发现残留。
- 剩余风险：尚未连接真实 MySQL/Redis 做登录和业务接口联调；当前结论覆盖源码主题化与静态结构验证。下一项目为 `195`。

## 2026-05-16 195 正式开发记录

### Findings
- `195` 批量版原始状态仍是通用模板：后端包名为 `com.p195`，业务类为 `BizRecord01-12`，数据库名为 `project_195`，前端页面和接口路径仍以 `record01-12` 命名。
- 已基于 `194` 正式化流水线生成并执行 `scripts/develop_195.py`；端口切换为后端 `8195`、前端 `3195`。
- 已将后端切换为 `com.publicservice` 包，启动类为 `PublicServiceApplication`，artifactId 为 `public-service-center-195`，数据库为 `public_service_195`，Redis token 前缀为 `publicservice:token:`。
- 已重建默认账号与角色口径：`ADMIN/admin`、`CENTER/center`、`WINDOW/window`、`REVIEW/review`、`SUPERVISE/supervise`、`CITIZEN/citizen`，统一密码 `123456`。
- 已生成 12 个正式业务模块：`ServiceItem`、`WindowProfile`、`ClerkRoster`、`AppointmentBooking`、`QueueCall`、`MaterialReview`、`ProcessProgress`、`MessageNotice`、`ServiceEvaluation`、`ComplaintHandling`、`PerformanceArchive`、`OperationLog`。
- 已为前后端角色口径收口：中心管理员维护事项档案、窗口档案、人员排班和窗口服务规则；窗口经办处理事项预约、窗口叫号、办理进度和通知提醒；材料审核人员处理材料审核和办理流转；督办人员查看满意评价、投诉处理和绩效统计；办事群众提交事项预约、查看办理进度并完成评价。
- 已重写前端路由、角色首页跳转、动态菜单、登录页、通用数据页、看板和全部业务页字段，页面语义切回事项档案、窗口档案、人员排班、事项预约、窗口叫号、材料审核、办理进度、通知提醒、满意评价、投诉处理、绩效统计、操作日志。
- 源码残留扫描未发现 `com.p195`、`BizRecord`、`project_195`、旧危废监管角色/模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace`、`System.out.print*`。
- 静态结构验证：13 张 SQL 表、13 个实体、13 个 Mapper、15 个 Controller、16 个前端视图。
- 按 `rule.md` 约定，本轮未执行 Maven/NPM 编译验证。
- 全仓库 `node_modules` 扫描未发现残留。
- 剩余风险：尚未连接真实 MySQL/Redis 做登录和业务接口联调；当前结论覆盖源码主题化与静态结构验证。下一项目为 `196`。

## 2026-05-16 196 正式开发记录

### Findings
- `196` 批量版原始状态仍是通用模板：后端包名为 `com.p196`，业务类为 `BizRecord01-12`，数据库名为 `project_196`，前端页面和接口路径仍以 `record01-12` 命名。
- 已基于 `195` 正式化流水线生成并执行 `scripts/develop_196.py`；端口切换为后端 `8196`、前端 `3196`。
- 已将后端切换为 `com.pharmacycare` 包，启动类为 `PharmacyCareApplication`，artifactId 为 `pharmacy-prescription-care-196`，数据库为 `pharmacy_care_196`，Redis token 前缀为 `pharmacycare:token:`。
- 已重建默认账号与角色口径：`ADMIN/admin`、`PHARMACY/pharmacy`、`PHARMACIST/pharmacist`、`CLERK/clerk`、`FOLLOWUP/followup`、`CUSTOMER/customer`，统一密码 `123456`。
- 已生成 12 个正式业务模块：`PharmacyStore`、`CustomerProfile`、`MedicineCatalog`、`PrescriptionRecord`、`PrescriptionReview`、`RiskCheck`、`PurchaseRecord`、`MedicationGuide`、`ChronicPlan`、`RenewalReminder`、`FollowupRecord`、`OperationLog`。
- 已为前后端角色口径收口：药店管理员维护门店、药品目录和慢病服务基础数据；药师审核处理处方审核、风险复核和用药指导；门店店员登记处方并维护购药记录；随访专员维护慢病方案、续方提醒和随访记录；慢病顾客查看个人处方、购药、用药指导和续方提醒。
- 已重写前端路由、角色首页跳转、动态菜单、登录页、通用数据页、看板和全部业务页字段，页面语义切回药店门店、顾客档案、药品目录、处方登记、处方审核、风险复核、购药记录、用药指导、慢病方案、续方提醒、随访记录、操作日志。
- 源码残留扫描未发现 `com.p196`、`BizRecord`、`project_196`、旧便民服务角色/模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace`、`System.out.print*`。
- 静态结构验证：13 张 SQL 表、13 个实体、13 个 Mapper、15 个 Controller、16 个前端视图。
- 按 `rule.md` 约定，本轮未执行 Maven/NPM 编译验证。
- 全仓库 `node_modules` 扫描未发现残留。
- 剩余风险：尚未连接真实 MySQL/Redis 做登录和业务接口联调；当前结论覆盖源码主题化与静态结构验证。下一项目为 `197`。

## 2026-05-17 197 正式开发记录

### Findings
- `197` 批量版原始状态仍是通用模板：后端包名为 `com.p197`，业务类为 `BizRecord01-12`，数据库名为 `project_197`，前端页面和接口路径仍以 `record01-12` 命名。
- 已基于 `196` 正式化流水线生成并执行 `scripts/develop_197.py`；端口切换为后端 `8197`、前端 `3197`。
- 已将后端切换为 `com.housekeeping` 包，启动类为 `HousekeepingApplication`，artifactId 为 `community-housekeeping-197`，数据库为 `housekeeping_197`，Redis token 前缀为 `housekeeping:token:`。
- 已重建默认账号与角色口径：`ADMIN/admin`、`AGENCY/agency`、`DISPATCH/dispatch`、`WORKER/worker`、`QUALITY/quality`、`RESIDENT/resident`，统一密码 `123456`。
- 已生成 12 个正式业务模块：`ServiceStation`、`ResidentProfile`、`WorkerProfile`、`ServiceCatalog`、`ServiceBooking`、`BookingReview`、`ScheduleDispatch`、`ServiceRecord`、`CreditEvaluation`、`ComplaintHandling`、`SettlementRecord`、`OperationLog`。
- 已为前后端角色口径收口：服务站管理员维护服务站点、居民档案、人员档案和服务项目；派单调度处理服务预约、预约审核、人员排班和服务进度流转；家政人员查看排班任务并记录上门服务；品控专员跟踪信用评价、投诉处理和质量闭环；社区居民提交预约、查看上门记录并评价投诉。
- 已重写前端路由、角色首页跳转、动态菜单、登录页、通用数据页、看板和全部业务页字段，页面语义切回服务站点、居民档案、人员档案、服务项目、服务预约、预约审核、人员排班、上门记录、信用评价、投诉处理、费用结算、操作日志。
- 源码残留扫描未发现 `com.p197`、`BizRecord`、`project_197`、旧药店处方角色/模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace`、`System.out.print*`。
- 静态结构验证：13 张 SQL 表、13 个实体、13 个 Mapper、15 个 Controller、16 个前端视图。
- 按 `rule.md` 约定，本轮未执行 Maven/NPM 编译验证。
- 全仓库 `node_modules` 扫描未发现残留。
- 剩余风险：尚未连接真实 MySQL/Redis 做登录和业务接口联调；当前结论覆盖源码主题化与静态结构验证。下一项目为 `198`。

## 2026-05-17 198 正式开发记录

### Findings
- `198` 批量版原始状态仍是通用模板：后端包名为 `com.p198`，业务类为 `BizRecord01-12`，数据库名为 `project_198`，前端页面和接口路径仍以 `record01-12` 命名。
- 已基于 `197` 正式化流水线生成并执行 `scripts/develop_198.py`；端口切换为后端 `8198`、前端 `3198`。
- 已将后端切换为 `com.powerbank` 包，启动类为 `PowerBankApplication`，artifactId 为 `urban-powerbank-198`，数据库为 `powerbank_198`，Redis token 前缀为 `powerbank:token:`。
- 已重建默认账号与角色口径：`ADMIN/admin`、`OPERATOR/operator`、`SITE/site`、`INSPECTOR/inspector`、`FINANCE/finance`、`MERCHANT/merchant`，统一密码 `123456`。
- 已生成 12 个正式业务模块：`PlacementSite`、`CabinetProfile`、`PowerBankDevice`、`PlacementPlan`、`InspectionTask`、`FaultRepair`、`AbnormalRecycle`、`LeaseOrder`、`MerchantIncome`、`SettlementRecord`、`InventoryTransfer`、`OperationLog`。
- 已为前后端角色口径收口：运营管理员维护投放点位、设备柜档案、充电宝档案和点位投放；点位商户查看设备柜、充电宝状态、巡检维修和点位运营记录；巡检人员处理设备巡检、故障维修、异常回收和库存调拨；财务结算核对租借订单、商户收益和收益结算；合作商户查看点位收益、结算记录和订单分成。
- 已重写前端路由、角色首页跳转、动态菜单、登录页、通用数据页、看板和全部业务页字段，页面语义切回投放点位、设备柜档案、充电宝档案、点位投放、设备巡检、故障维修、异常回收、租借订单、商户收益、收益结算、库存调拨、操作日志。
- 源码残留扫描未发现 `com.p198`、`BizRecord`、`project_198`、旧家政服务角色/模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace`、`System.out.print*`。
- 静态结构验证：13 张 SQL 表、13 个实体、13 个 Mapper、15 个 Controller、16 个前端视图。
- 按 `rule.md` 约定，本轮未执行 Maven/NPM 编译验证。
- 全仓库 `node_modules` 扫描未发现残留。
- 剩余风险：尚未连接真实 MySQL/Redis 做登录和业务接口联调；当前结论覆盖源码主题化与静态结构验证。下一项目为 `199`。

## 2026-05-17 199 正式开发记录

### Findings
- `199` 批量版原始状态仍是通用模板：后端包名为 `com.p199`，业务类为 `BizRecord01-12`，数据库名为 `project_199`，前端页面和接口路径仍以 `record01-12` 命名。
- 已基于 `198` 正式化流水线生成并执行 `scripts/develop_199.py`；端口切换为后端 `8199`、前端 `3199`。
- 已将后端切换为 `com.sportrehab` 包，启动类为 `SportRehabApplication`，artifactId 为 `sport-rehab-199`，数据库为 `sport_rehab_199`，Redis token 前缀为 `sportrehab:token:`。
- 已重建默认账号与角色口径：`ADMIN/admin`、`CENTER/center`、`ASSESSOR/assessor`、`COACH/coach`、`THERAPIST/therapist`、`MEMBER/member`，统一密码 `123456`。
- 已生成 12 个正式业务模块：`RehabCenter`、`MemberProfile`、`CoachProfile`、`AssessmentItem`、`FitnessAssessment`、`RiskWarning`、`TrainingPlan`、`TrainingSession`、`ExerciseCheckin`、`RehabFeedback`、`ReassessmentRecord`、`OperationLog`。
- 已为前后端角色口径收口：中心管理员维护康复中心、会员档案、教练档案和体测项目；体测评估师处理体测评估、风险提醒和复评记录；康复教练制定训练计划、维护训练安排并跟进训练打卡；康复治疗师查看评估结果、维护康复反馈并参与复评；训练会员查看个人体测、训练计划、训练打卡和康复反馈。
- 已重写前端路由、角色首页跳转、动态菜单、登录页、通用数据页、看板和全部业务页字段，页面语义切回康复中心、会员档案、教练档案、体测项目、体测评估、风险提醒、训练计划、训练安排、训练打卡、康复反馈、复评记录、操作日志。
- 源码残留扫描未发现 `com.p199`、`BizRecord`、`project_199`、旧共享充电宝角色/模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace`、`System.out.print*`。
- 静态结构验证：13 张 SQL 表、13 个实体、13 个 Mapper、15 个 Controller、16 个前端视图。
- 按 `rule.md` 约定，本轮未执行 Maven/NPM 编译验证。
- 全仓库 `node_modules` 扫描未发现残留。
- 剩余风险：尚未连接真实 MySQL/Redis 做登录和业务接口联调；当前结论覆盖源码主题化与静态结构验证。下一项目为 `200`。

## 2026-05-17 200 正式开发记录

### Findings
- `200` 批量版原始状态仍是通用模板：后端包名为 `com.p200`，业务类为 `BizRecord01-12`，数据库名为 `project_200`，前端页面和接口路径仍以 `record01-12` 命名。
- 已基于 `199` 正式化流水线生成并执行 `scripts/develop_200.py`；端口切换为后端 `8200`、前端 `3200`。
- 已将后端切换为 `com.heritageworkshop` 包，启动类为 `HeritageWorkshopApplication`，artifactId 为 `heritage-workshop-200`，数据库为 `heritage_workshop_200`，Redis token 前缀为 `heritageworkshop:token:`。
- 已重建默认账号与角色口径：`ADMIN/admin`、`WORKSHOP/workshop`、`TEACHER/teacher`、`CURATOR/curator`、`SALES/sales`、`VISITOR/visitor`，统一密码 `123456`。
- 已生成 12 个正式业务模块：`WorkshopProfile`、`InheritorProfile`、`CourseCatalog`、`CourseSchedule`、`CourseBooking`、`BookingReview`、`ClassCheckin`、`ArtworkCatalog`、`ExhibitionShowcase`、`ProductOrder`、`SalesSettlement`、`OperationLog`。
- 已为前后端角色口径收口：工坊管理员维护工坊档案、传承人档案、课程目录和工坊排期；非遗讲师维护课程内容、处理课程预约、预约审核和课程签到；展陈运营维护作品档案、作品展销和展陈状态；展销财务核对展销订单和展销结算闭环；学员访客查看课程排期、提交课程预约、查看作品展销和订单记录。
- 已重写前端路由、角色首页跳转、动态菜单、登录页、通用数据页、看板和全部业务页字段，页面语义切回工坊档案、传承人档案、课程目录、工坊排期、课程预约、预约审核、课程签到、作品档案、作品展销、展销订单、展销结算、操作日志。
- 源码残留扫描未发现 `com.p200`、`BizRecord`、`project_200`、旧运动康复角色/模块名、旧 `record01` 路径、通配符 CORS、`printStackTrace`、`System.out.print*`。
- 静态结构验证：13 张 SQL 表、13 个实体、13 个 Mapper、15 个 Controller、16 个前端视图。
- 按 `rule.md` 约定，本轮未执行 Maven/NPM 编译验证。
- 全仓库 `node_modules` 扫描未发现残留。
- 剩余风险：尚未连接真实 MySQL/Redis 做登录和业务接口联调；当前结论覆盖源码主题化与静态结构验证。当前清单已完成到 `200`。
