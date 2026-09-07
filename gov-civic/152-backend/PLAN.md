# 工厂危险作业审批与监护巡检管理系统 开发计划

## 后端
- 完成 `com.hazardwork` 包结构、统一响应、异常处理、JWT 拦截、Redis Token、CORS 收口
- 完成账号权限、作业区域、风险源台账、作业人员档案、作业票申请、审批链路、安全交底、监护安排、监护记录、隐患闭环、气体检测、应急预案、操作日志接口
- 完成统计看板接口和 MySQL 初始化脚本

## 前端
- 完成登录、角色首页跳转、动态菜单、通用数据页、业务页面和 ECharts 看板
- 按 ADMIN / SAFETY / APPROVER / GUARDIAN / WORKER 收口页面操作权限

## 验收
- 后端 `mvn.cmd clean test` 通过
- 前端 `npm.cmd run build` 通过
- 源码不再残留旧泛化模板命名
