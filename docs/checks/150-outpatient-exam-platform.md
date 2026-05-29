# 150 医院门诊检查预约与报告回传管理系统检查报告

## 检查范围

- 项目编号：`150`
- 项目名称：医院门诊检查预约与报告回传管理系统
- 检查时间：`2026-05-20`
- 目录：
  - 后端：`150-backend`
  - 前端：`150-frontend`
- 主要对照文档：
  - `150-backend/PRD.md`
  - `150-backend/PLAN.md`

## 结论摘要

`150` 当前应标记为 `待修复`，并建议优先返修。本轮静态核对确认“检查报告”模块存在明确的患者全量数据暴露风险：

1. PRD 的用户角色包含“患者本人”：`150-backend/PRD.md:6-8`
2. 前端已将 `PATIENT` 放入“检查报告”页面 roles：`150-frontend/src/router/index.js:28`、`150-frontend/src/views/Layout.vue:36`
3. 后端 `/api/report/page` 同样允许 `PATIENT` 访问，但服务层和 mapper 完全没有“本人报告”过滤。

这意味着患者不是只能看“自己的报告”，而是有机会读取全量检查报告分页。

## 主要问题

### 150-1 患者可直接读取全量检查报告分页

- PRD 中角色明确是“患者本人”：`150-backend/PRD.md:6-8`
- PRD 中“检查报告”模块也是围绕患者本人报告回传：`150-backend/PRD.md:16`
- 后端分页接口允许 `PATIENT`：`150-backend/src/main/java/com/outpatientexam/controller/ExamReportController.java:27-34`
- `ExamReportService.page()` 只按 `keyword / status` 分页：`150-backend/src/main/java/com/outpatientexam/service/ExamReportService.java:15-18`
- `ExamReportMapper.selectPage()` 直接 `SELECT * FROM exam_report`，没有任何患者约束条件：`150-backend/src/main/java/com/outpatientexam/mapper/ExamReportMapper.java:16-26`

影响：

- 当前实现不区分“患者本人”与“全院全部报告”。
- 这是典型的高敏医疗数据越权读取问题。

### 150-2 报告模块没有体现“本人边界”

- 前端角色只是把 `PATIENT` 放进页面可见列表：`150-frontend/src/router/index.js:28`、`150-frontend/src/views/Layout.vue:36`
- 后端没有任何 `patientId`、`patientNo`、`当前登录用户` 相关过滤逻辑：`150-backend/src/main/java/com/outpatientexam/service/ExamReportService.java:15-18`、`150-backend/src/main/java/com/outpatientexam/mapper/ExamReportMapper.java:16-26`

影响：

- 当前“患者本人”在实现上没有被真正落地。
- PRD 里的角色边界只停留在文字层，没有转化为后端可执行规则。

## 静态核对记录

- 本轮已核对：
  - `PRD.md`
  - 前端 `src/router/index.js`
  - 前端 `src/views/Layout.vue`
  - `ExamReportController`
  - `ExamReportService`
  - `ExamReportMapper`
- 本轮未执行编译、启动和接口实测，当前结论来自静态源码对照。

## 当前判断

`150` 当前状态应标记为：`待修复`

- 高敏数据边界：`不通过`
- 患者本人过滤：`不通过`
- 编译与启动验证：`本轮未执行`

## 建议下一步

1. 为 `PATIENT` 视角单独增加“只查本人报告”的后端查询条件。
2. 如医生、技师、管理员需要全量报表，应与患者接口分流，而不是共用同一个分页接口。
3. 继续复查 `patient`、`doctor`、`department`、`checkin`、`delivery` 等患者也被纳入 roles 的相邻页面。
