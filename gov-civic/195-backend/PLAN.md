# 便民服务中心事项预约与窗口评价平台 开发计划

## 后端
- 完成 `com.publicservice` 包结构、统一响应、异常处理、JWT 拦截、Redis Token、CORS 收口
- 完成账号权限、事项档案、窗口档案、人员排班、事项预约、窗口叫号、材料审核、办理进度、通知提醒、满意评价、投诉处理、绩效统计、操作日志接口
- 完成统计看板接口和 MySQL 初始化脚本

## 前端
- 完成登录、角色首页跳转、动态菜单、通用数据页、业务页面和 ECharts 看板
- 按 ADMIN / CENTER / WINDOW / REVIEW / SUPERVISE / CITIZEN 收口页面操作权限

## 验收
- 后端可在 `195-backend` 执行 `mvn.cmd clean test`
- 前端可在 `195-frontend` 执行 `npm.cmd install` 与 `npm.cmd run build`，执行后按约定清理 `node_modules`
- 源码不再残留批量脚手架的通用包名与通用业务命名
