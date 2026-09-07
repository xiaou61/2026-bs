# 社区家政服务预约与人员信用评价系统 开发计划

## 后端
- 完成 `com.housekeeping` 包结构、统一响应、异常处理、JWT 拦截、Redis Token、CORS 收口
- 完成账号权限、服务站点、居民档案、人员档案、服务项目、服务预约、预约审核、人员排班、上门记录、信用评价、投诉处理、费用结算、操作日志接口
- 完成统计看板接口和 MySQL 初始化脚本

## 前端
- 完成登录、角色首页跳转、动态菜单、通用数据页、业务页面和 ECharts 看板
- 按 ADMIN / AGENCY / DISPATCH / WORKER / QUALITY / RESIDENT 收口页面操作权限

## 验收
- 后端可在 `197-backend` 执行 `mvn.cmd clean test`
- 前端可在 `197-frontend` 执行 `npm.cmd install` 与 `npm.cmd run build`，执行后按约定清理 `node_modules`
- 源码不再残留批量脚手架的通用包名与通用业务命名
