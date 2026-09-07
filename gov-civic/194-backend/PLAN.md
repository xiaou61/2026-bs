# 工业园区危废暂存与转运联动监管平台 开发计划

## 后端
- 完成 `com.hazardwaste` 包结构、统一响应、异常处理、JWT 拦截、Redis Token、CORS 收口
- 完成账号权限、园区企业、危废类别、暂存设施、入库登记、暂存巡检、转运联单、车辆调度、称重记录、处置交接、风险预警、台账审计、操作日志接口
- 完成统计看板接口和 MySQL 初始化脚本

## 前端
- 完成登录、角色首页跳转、动态菜单、通用数据页、业务页面和 ECharts 看板
- 按 ADMIN / PARK / WAREHOUSE / TRANSPORT / INSPECTOR / ENTERPRISE 收口页面操作权限

## 验收
- 后端可在 `194-backend` 执行 `mvn.cmd clean test`
- 前端可在 `194-frontend` 执行 `npm.cmd install` 与 `npm.cmd run build`，执行后按约定清理 `node_modules`
- 源码不再残留批量脚手架的通用包名与通用业务命名
