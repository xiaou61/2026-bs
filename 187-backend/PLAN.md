# 旅行社团建行程报名与费用分摊管理平台 开发计划

## 后端
- 完成 `com.teambuilding` 包结构、统一响应、异常处理、JWT 拦截、Redis Token、CORS 收口
- 完成账号权限、旅行社档案、团队档案、团建行程、行程报名、成员确认、费用预算、费用分摊、缴费记录、通知同步、出行反馈、发票记录、操作日志接口
- 完成统计看板接口和 MySQL 初始化脚本

## 前端
- 完成登录、角色首页跳转、动态菜单、通用数据页、业务页面和 ECharts 看板
- 按 ADMIN / AGENCY / PLANNER / FINANCE / LEADER / PARTICIPANT 收口页面操作权限

## 验收
- 后端 `mvn.cmd clean test` 通过
- 前端 `npm.cmd run build` 通过
- 源码不再残留批量脚手架的通用包名与通用业务命名
