# 应急物资储备盘点与调拨审批平台 开发计划

## 后端
- 完成 `com.emergencysupply` 包结构、统一响应、异常处理、JWT 拦截、Redis Token、CORS 收口
- 完成账号权限、储备仓库、物资分类、物资台账、库存批次、库存盘点、盘点差异、申领工单、调拨审批、调度任务、出库记录、库存预警、操作日志接口
- 完成统计看板接口和 MySQL 初始化脚本

## 前端
- 完成登录、角色首页跳转、动态菜单、通用数据页、业务页面和 ECharts 看板
- 按 ADMIN / WAREHOUSE / CHECKER / APPLICANT / DISPATCH / AUDITOR 收口页面操作权限

## 验收
- 后端 `mvn.cmd clean test` 通过
- 前端 `npm.cmd run build` 通过
- 源码不再残留批量脚手架的通用包名与通用业务命名
