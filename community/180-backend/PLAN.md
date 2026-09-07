# 物业报修派单与服务满意度评价平台 开发计划

## 后端
- 完成 `com.propertyrepair` 包结构、统一响应、异常处理、JWT 拦截、Redis Token、CORS 收口
- 完成账号权限、小区区域、业主档案、报修分类、报修工单、派单分配、上门处理、物料使用、费用登记、支付记录、满意评价、投诉回访、操作日志接口
- 完成统计看板接口和 MySQL 初始化脚本

## 前端
- 完成登录、角色首页跳转、动态菜单、通用数据页、业务页面和 ECharts 看板
- 按 ADMIN / PROPERTY / OWNER / DISPATCH / WORKER / FINANCE 收口页面操作权限

## 验收
- 后端 `mvn.cmd clean test` 通过
- 前端 `npm.cmd run build` 通过
- 源码不再残留批量脚手架的通用包名与通用业务命名
