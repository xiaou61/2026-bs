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
