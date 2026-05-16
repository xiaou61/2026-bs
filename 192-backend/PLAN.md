# 医院陪护服务预约与护工评价管理系统 开发计划

## 后端
- 完成 `com.hospitalcare` 包结构、统一响应、异常处理、JWT 拦截、Redis Token、CORS 收口
- 完成账号权限、病区档案、患者档案、护工档案、陪护预约、预约审核、护工排班、服务派单、服务记录、家属沟通、护工评价、评价结算、操作日志接口
- 完成统计看板接口和 MySQL 初始化脚本

## 前端
- 完成登录、角色首页跳转、动态菜单、通用数据页、业务页面和 ECharts 看板
- 按 ADMIN / HOSPITAL / COORDINATOR / CAREGIVER / FINANCE / FAMILY 收口页面操作权限

## 验收
- 后端可在 `192-backend` 执行 `mvn.cmd clean test`
- 前端可在 `192-frontend` 执行 `npm.cmd install` 与 `npm.cmd run build`，执行后按约定清理 `node_modules`
- 源码不再残留批量脚手架的通用包名与通用业务命名
