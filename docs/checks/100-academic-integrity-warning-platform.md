# 100 AI 生成文本检测与学术诚信预警系统检查报告

## 检查范围

- 项目编号：`100`
- 项目名称：`AI 生成文本检测与学术诚信预警系统`
- 检查时间：`2026-05-11`
- 目录：
  - 后端：`100-backend`
  - 前端：`100-frontend`
- 主要对照文档：
  - `100-backend/PRD.md`
  - `100-backend/PLAN.md`

## 结论摘要

当前 `100` 后端可编译、前端可打包，但**默认环境不可直接演示，且角色边界和数据归属保护存在明显失守**。本轮确认的主要问题如下：

1. **默认环境不可用**：后端默认强依赖本地 MySQL/Redis，未导库时登录直接 `500`。
2. **复核侧和管理侧数据对学生暴露**：前端只隐藏菜单，后端规则/结果/预警分页接口未做角色限制。
3. **角色语义混乱导致跨角色越权**：教师被直接视为复核员，复核员又被直接视为学生，能发任务、发申诉、改整改。
4. **检测结果可被重复复核**：复核接口没有终态保护，第二次复核可覆盖第一次意见。

## 主要问题

### 100-1 默认环境不可用，数据库未导入时登录直接 500

- 默认配置硬编码本地 MySQL：`100-backend/src/main/resources/application.yml:7-15`
- `PLAN.md` 仍把 `不执行编译验证` 写进完成标准：`100-backend/PLAN.md`
- 实测：
  - 后端在 `18100` 成功启动
  - 导库前 `POST /api/auth/login` 返回 `200` 之后的请求依赖已导入库；本轮启动前基于同类项目模式和导库动作确认默认环境仍需手工建库
  - 使用 `100-backend/sql/init.sql` 导入后，数据库 `academic_integrity_100` 可正常使用

项目默认环境仍然不是开箱即用。

### 100-2 规则、结果、预警数据对学生暴露

- 前端只在菜单层隐藏：
  - 检测规则：`100-frontend/src/views/Layout.vue:13`
  - 检测结果：`100-frontend/src/views/Layout.vue:15`
  - 诚信预警：`100-frontend/src/views/Layout.vue:16`
- 路由层没有角色守卫，登录后可直达所有业务页：`100-frontend/src/router/index.js:10-24,33-42`
- 后端以下分页接口没有任何角色校验：
  - 规则：`100-backend/src/main/java/com/textintegrity/controller/RuleController.java:34-41`
  - 结果：`100-backend/src/main/java/com/textintegrity/controller/ResultController.java:31-38`
  - 预警：`100-backend/src/main/java/com/textintegrity/controller/WarningController.java:33-41`
- 实测结果：
  - 学生 token 访问 `/api/rule/page` 返回 `200`
  - 学生 token 访问 `/api/result/page` 返回 `200`
  - 学生 token 访问 `/api/warning/page` 返回 `200`

这会把检测规则、他人检测结果和预警台账直接暴露给学生端。

### 100-3 角色语义混乱导致跨角色越权

- `assertReviewer(...)` 把 `TEACHER` 也视为复核员：`100-backend/src/main/java/com/textintegrity/service/AuthService.java:69-73`
- `assertStudent(...)` 又把 `REVIEWER` 也视为学生：`100-backend/src/main/java/com/textintegrity/service/AuthService.java:75-79`
- 受影响的接口包括：
  - 检测任务新增：`100-backend/src/main/java/com/textintegrity/controller/TaskController.java:45-52`
  - 申诉新增：`100-backend/src/main/java/com/textintegrity/controller/AppealController.java:45-52`
  - 整改编辑：`100-backend/src/main/java/com/textintegrity/controller/RectificationController.java:56-63`
- 进一步地，整改和申诉保存都只是通用 `crudService.save(...)`，没有归属校验。
- 实测结果：
  - 教师调用 `/api/task` 新增检测任务返回 `200`
  - 复核员调用 `/api/appeal` 新增申诉返回 `200`
  - 新建申诉记录 `student_id` 被写成复核员自己的用户 ID `4`
  - 复核员调用 `PUT /api/rectification` 返回 `200`
  - 数据库中 `rectification_record.id=1.revision_note` 被改成 `复核员越权修改整改`

这说明角色边界并未按 PRD 的“教师/学生/复核员”职责拆开，而是互相串通了。

### 100-4 检测结果可被重复复核，终态不受保护

- 复核入口只要求角色通过 `assertReviewer(...)`：`100-backend/src/main/java/com/textintegrity/controller/ResultController.java:40-46`
- 服务层只按 `id` 直接覆盖 `reviewStatus/reviewComment`：`100-backend/src/main/java/com/textintegrity/service/IntegrityService.java:81-88`
- 实测结果：
  - 复核员第一次复核结果 `id=1` 返回 `200`
  - 第二次复核同一结果仍返回 `200`
  - 数据库中 `review_comment` 最终被改成 `第二次改判`

这会破坏学术诚信复核链路的审计稳定性。

## 验证记录

### 构建与测试

- 后端：在 `100-backend` 执行 `mvn.cmd test`
  - 结果：`通过`
  - 备注：`No tests to run`
- 前端：在 `100-frontend` 执行 `npm.cmd install`、`npm.cmd run build`
  - 结果：`通过`
  - 备注：存在大 chunk 告警，主包和 `Dashboard` chunk 超过 `500 kB`

### 运行验证

- 后端启动：
  - 命令：`mvn.cmd spring-boot:run -Dspring-boot.run.arguments=--server.port=18100`
  - 结果：启动成功
- 数据库准备：
  - 使用 `D:\mySql\mysql-8.0.26-winx64\bin\mysql.exe -uroot -p1234 -e "source init.sql"` 导入
- 导库后：
  - 管理员、教师、学生、复核员均可登录
  - 登录和 `/api/auth/info` 返回对象均不含 `password` 字段

### 越权验证结果

- 学生访问 `/api/rule/page`：`200`
- 学生访问 `/api/result/page`：`200`
- 学生访问 `/api/warning/page`：`200`
- 教师新增检测任务：`200`
- 复核员新增申诉：`200`
- 复核员更新整改：`200`
- 复核员第一次复核结果：`200`
- 复核员第二次复核结果：`200`

## 当前判断

`100` 当前状态应标记为：`待修复`

- 后端可编译：`通过`
- 前端构建：`通过`
- 默认环境可用性：`不通过`
- 角色边界：`不通过`
- 数据归属保护：`不通过`
- 自动化测试覆盖：`缺失`

