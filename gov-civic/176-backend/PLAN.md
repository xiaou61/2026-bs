# 水务巡线工单与阀门检修协同管理系统 开发计划

## 后端
- 完成 `com.waterpatrol` 包结构、统一响应、异常处理、JWT 拦截、Redis Token、CORS 收口
- 完成账号权限、水务站点、管线区段、巡线路线、阀门台账、巡线任务、巡线记录、故障上报、故障派工、检修回执、备件台账、风险预警、操作日志接口
- 完成统计看板接口和 MySQL 初始化脚本

## 前端
- 完成登录、角色首页跳转、动态菜单、通用数据页、业务页面和 ECharts 看板
- 按 ADMIN / DISPATCH / PATROL / REPAIR / WAREHOUSE / SUPERVISOR 收口页面操作权限

## 验收
- 后端 `mvn.cmd clean test` 通过
- 前端 `npm.cmd run build` 通过
- 源码不再残留批量脚手架的通用包名与通用业务命名
