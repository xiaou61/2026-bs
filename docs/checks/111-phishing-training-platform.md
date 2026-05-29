# 111 网络钓鱼邮件演练与安全意识培训平台检查报告

## 检查范围

- 项目编号：`111`
- 项目名称：网络钓鱼邮件演练与安全意识培训平台
- 检查时间：`2026-05-21`
- 目录：
  - 后端：`111-backend`
  - 前端：`111-frontend`
- 主要对照文档：
  - `111-backend/PRD.md`
  - `111-backend/PLAN.md`
  - `111-backend/sql/init.sql`
  - `111-backend/src/main/resources/application.yml`
  - `111-frontend/src/router/index.js`
  - `111-frontend/src/views/Layout.vue`

## 结论摘要

`111` 当前建议标记为 `检查中`。本轮静态对照未发现明显的 PRD 失真、主题串项或角色码断裂：

1. `PRD.md`、`PLAN.md`、`sql/init.sql` 与 `application.yml` 在数据库名、端口和默认账号口径上保持一致。
2. 前端路由、菜单已经覆盖员工档案、部门分组、邮件模板、演练活动、目标名单、发送记录、点击追踪、培训课程、培训考试、考试题目、考试记录、风险评分和操作日志等核心模块。
3. 后端角色断言与前端按钮动作基本对应，能静态对应员工启用/冻结、邮件模板发布/归档、演练活动提交/启动/完成、发送记录状态流转、点击追踪处置、课程与考试发布、考试记录通过/补训和风险评分复核/整改等闭环动作。

当前主要缺口仍是运行验证：`PLAN.md` 继续把“不执行编译验证”列为完成标准，本轮也尚未执行 Maven / NPM / 启动验证，因此暂不直接标为 `已完成`。

## 主要结论

### 111-1 基础配置与 PRD 口径一致

- `PRD.md` 明确数据库名、前后端端口和默认账号：`111-backend/PRD.md:9-19`
- `PLAN.md` 明确创建 `phishing_training_111` 数据库和 `14` 张业务表：`111-backend/PLAN.md:11`
- 初始化 SQL 直接 `DROP/CREATE/USE phishing_training_111`：`111-backend/sql/init.sql:1-3`
- 实际运行配置使用后端端口 `8111`，数据源也指向 `phishing_training_111`：`111-backend/src/main/resources/application.yml:2`、`111-backend/src/main/resources/application.yml:6`

说明：

- 本轮未发现数据库串项、端口串项或默认账号口径错配。
- `111` 当前不像 `109/110` 那样存在角色名双轨问题。

### 111-2 前端路由与菜单基本覆盖 PRD 的 14 个核心模块

- 前端已定义四类角色首页：`ADMIN / SECURITY / TRAINER / AUDITOR`：`111-frontend/src/router/index.js:4-8`
- 路由层已覆盖看板、账号权限、员工档案、部门分组、邮件模板、演练活动、目标名单、发送记录、点击追踪、培训课程、培训考试、考试题目、考试记录、风险评分和操作日志：`111-frontend/src/router/index.js:20-34`
- 侧边菜单与路由保持一致：`111-frontend/src/views/Layout.vue:27-41`
- 前端账号管理表单角色枚举也与默认账号口径一致：`111-frontend/src/views/SysUser.vue:11-13`

说明：

- 从页面入口看，`111` 的功能骨架与 PRD 列出的核心模块是对齐的。
- 本轮未发现前端仍沿用其他项目主题名或错用角色常量的明显串项。

### 111-3 后端权限骨架与关键状态动作已基本落地

- `AuthService` 已抽出 `assertAdmin`、`assertAdminOrSecurity`、`assertAdminOrTrainer`、`assertAdminOrAuditor`、`assertAdminOrSecurityOrAuditor`、`assertAdminOrTrainerOrAuditor` 与 `assertAuthenticated`：`111-backend/src/main/java/com/phishingtraining/service/AuthService.java:53-78`
- 员工档案已覆盖 `activate / freeze`：`111-backend/src/main/java/com/phishingtraining/controller/EmployeeProfileController.java:58-66`
- 邮件模板已覆盖 `publish / archive`：`111-backend/src/main/java/com/phishingtraining/controller/MailTemplateController.java:58-66`
- 演练活动已覆盖 `submit / start / finish`：`111-backend/src/main/java/com/phishingtraining/controller/PhishingCampaignController.java:58-73`
- 发送记录已覆盖 `sent / open / fail`：`111-backend/src/main/java/com/phishingtraining/controller/MailSendRecordController.java:58-73`
- 点击追踪已覆盖 `confirm / educate / ignore`：`111-backend/src/main/java/com/phishingtraining/controller/ClickTrackingController.java:58-73`
- 培训课程已覆盖 `publish / archive`，培训考试已覆盖 `publish / close`：`111-backend/src/main/java/com/phishingtraining/controller/TrainingCourseController.java:58-66`、`111-backend/src/main/java/com/phishingtraining/controller/TrainingExamController.java:58-66`
- 考试记录已覆盖 `pass / fail / retrain`：`111-backend/src/main/java/com/phishingtraining/controller/ExamAttemptController.java:58-73`
- 风险评分已覆盖 `review / remediate`：`111-backend/src/main/java/com/phishingtraining/controller/RiskScoreController.java:58-66`

说明：

- 从控制器静态结构看，`111` 已具备演练、培训、考试和风险评分的基本闭环动作。
- 本轮未抓到像 `105` 那样“计划写了动作，但前后端都没落”的硬缺口。

### 111-4 前端按钮与后端动作基本对应

- 员工档案页面已接 `启用 / 冻结`：`111-frontend/src/views/EmployeeProfile.vue:8-16`
- 邮件模板页面已接 `发布 / 归档`：`111-frontend/src/views/MailTemplate.vue:8-16`
- 演练活动页面已接 `提交 / 启动 / 完成`：`111-frontend/src/views/PhishingCampaign.vue:8-17`
- 发送记录页面已接 `已发送 / 已打开 / 失败`：`111-frontend/src/views/MailSendRecord.vue:8-17`
- 点击追踪页面已接 `确认 / 培训 / 忽略`：`111-frontend/src/views/ClickTracking.vue:8-17`
- 培训课程页面已接 `发布 / 归档`，培训考试页面已接 `发布 / 关闭`：`111-frontend/src/views/TrainingCourse.vue:8-16`、`111-frontend/src/views/TrainingExam.vue:8-16`
- 考试记录页面已接 `通过 / 未通过 / 补训`：`111-frontend/src/views/ExamAttempt.vue:8-17`
- 风险评分页面已接 `复核 / 整改`：`111-frontend/src/views/RiskScore.vue:8-16`

说明：

- 静态上可以确认“页面按钮 -> API -> controller 状态动作”这一链路基本打通。
- 当前没有发现前端开放了动作但后端缺接口，或后端有关键动作但前端完全没接的明显断层。

### 111-5 看板趋势能力已具备静态落点

- 统计接口已提供 `dashboard`：`111-backend/src/main/java/com/phishingtraining/controller/StatisticsController.java:21-24`
- 前端看板已接入演练趋势和员工风险分布，其中趋势图标题为“近7次演练趋势”：`111-frontend/src/views/Dashboard.vue:6-7`、`111-frontend/src/views/Dashboard.vue:24-25`

说明：

- `111` 不只是列表 CRUD，PRD 要求的演练趋势与风险分布也有明确静态落点。
- 趋势和评分是否与真实发送/点击/考试数据联动正确，仍需后续联调验证。

### 111-6 当前缺口仍是编译与启动验证

- `PLAN.md` 完成标准仍明确写着“不执行编译验证”：`111-backend/PLAN.md:32`

影响：

- 当前只能说明 `111` 在文档、配置、页面入口和动作接口层面基本对齐。
- 还不能证明它在当前环境下能通过 JDK17 编译、前端构建和基础登录/页面联调。

## 静态核对记录

- 本轮已核对：
  - `PRD.md`
  - `PLAN.md`
  - `sql/init.sql`
  - `application.yml`
  - 前端 `router/index.js`
  - 前端 `Layout.vue`
  - 前端 `store/user.js`
  - 前端 `Login.vue`
  - 前端关键业务页
  - 前端 `api/index.js`
  - 后端 `AuthService`
  - 后端关键 controller
- 静态结构计数：
  - SQL 建表：`14`
  - Controller：`16`
  - 前端视图：`17`
- 本轮未执行 Maven / NPM 编译与启动验证。

## 当前判断

`111` 当前状态建议标记为：`检查中`

- 数据库、端口、默认账号口径：`通过`
- 路由、菜单、角色骨架：`基本通过`
- 演练/培训/考试/风险评分闭环动作：`基本通过`
- 编译与启动验证：`本轮未执行`

## 建议下一步

1. 先补 `111-backend` 的 `mvn.cmd test` 或至少一次编译验证。
2. 再补 `111-frontend` 的 `npm.cmd run build` 与一次基础登录/页面冒烟验证。
3. 若编译、启动和核心状态接口联调通过，再将 `111` 从 `检查中` 转为 `已完成`。
