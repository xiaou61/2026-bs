## Session: 2026-04-24 项目预览自动化

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
