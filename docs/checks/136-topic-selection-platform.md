# 136 导师课题双选与开题过程管理系统检查报告

## 检查范围

- 项目编号：`136`
- 项目名称：导师课题双选与开题过程管理系统
- 检查时间：`2026-05-20`
- 目录：
  - 后端：`136-backend`
  - 前端：`136-frontend`
- 主要对照文档：
  - `136-backend/PRD.md`
  - `136-backend/PLAN.md`

## 结论摘要

`136` 当前应标记为 `待修复`。本轮静态核对确认该项目既有权限边界缺口，也有明显的领域串项：

1. 前端只向 `ADMIN / TEACHER / AFFAIRS` 开放“导师档案”，但后端 `/api/teacher/page` 只校验“已登录”。
2. `TeacherProfileService` 没有按角色或本人过滤，学生角色可直接读导师档案分页。
3. `TeacherProfile` 相关实体、mapper、service 仍保留“预算分类”模板，实际在查 `budget_category` 表，字段也是 `categoryNo / categoryName / usageScope`。

这说明项目并非只差一个权限判断，而是“导师档案”模块本体仍未真正切到题目语义。

## 主要问题

### 136-1 导师档案前端未给学生开放，后端分页却只要登录

- 前端路由仅向 `ADMIN / TEACHER / AFFAIRS` 开放“导师档案”：`136-frontend/src/router/index.js:23`
- 侧边栏菜单同样未给 `STUDENT` 开口：`136-frontend/src/views/Layout.vue:30`
- 后端分页接口仅做 `assertAuthenticated(role)`：`136-backend/src/main/java/com/topicselect/controller/TeacherProfileController.java:27-34`

影响：

- 学生角色虽然前端没有导师档案入口，但仍可直接调用 `/api/teacher/page`。
- PRD 中导师档案应按教务/导师职责管理，当前实现并未在后端真正收口。

### 136-2 “导师档案”模块仍是预算分类模板

- `TeacherProfile` 字段仍是 `categoryNo / categoryName / usageScope / controlMode / managerName`：`136-backend/src/main/java/com/topicselect/entity/TeacherProfile.java:7-16`
- `TeacherProfileMapper` 直接查询 `budget_category` 表：`136-backend/src/main/java/com/topicselect/mapper/TeacherProfileMapper.java:16-26`
- `TeacherProfileService` 变量名仍是 `budgetCategoryMapper`：`136-backend/src/main/java/com/topicselect/service/TeacherProfileService.java:12-18`

影响：

- 当前“导师档案”并没有真实落在导师、课题、研究方向、职称等业务语义上。
- 即便接口返回成功，返回内容也与双选系统 PRD 明显错位。

## 静态核对记录

- 本轮已核对：
  - `PRD.md`
  - 前端 `src/router/index.js`
  - 前端 `src/views/Layout.vue`
  - `TeacherProfileController`
  - `TeacherProfileService`
  - `TeacherProfileMapper`
  - `TeacherProfile` 实体
- 本轮未执行编译、启动和接口实测，当前结论来自静态源码对照。

## 当前判断

`136` 当前状态应标记为：`待修复`

- 角色边界：`不通过`
- 核心业务建模：`不通过`
- 编译与启动验证：`本轮未执行`

## 建议下一步

1. 将 `/api/teacher/page` 收紧到真正需要的角色集合。
2. 重建导师档案的实体字段、数据表和 mapper 查询，彻底清理 `budget_category` 模板残留。
3. 继续复查 `student`、`application`、`proposal` 等相邻模块，确认是否还有预算/经费模板串项。
