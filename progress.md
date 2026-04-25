## Session: 2026-04-24

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
- 下一项目：`036`
