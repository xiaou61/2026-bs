# 城市共享充电宝投放巡检与收益结算系统 开发计划

## 后端
- 完成 `com.powerbank` 包结构、统一响应、异常处理、JWT 拦截、Redis Token、CORS 收口
- 完成账号权限、投放点位、设备柜档案、充电宝档案、点位投放、设备巡检、故障维修、异常回收、租借订单、商户收益、收益结算、库存调拨、操作日志接口
- 完成统计看板接口和 MySQL 初始化脚本

## 前端
- 完成登录、角色首页跳转、动态菜单、通用数据页、业务页面和 ECharts 看板
- 按 ADMIN / OPERATOR / SITE / INSPECTOR / FINANCE / MERCHANT 收口页面操作权限

## 验收
- 后端可在 `198-backend` 执行 `mvn.cmd clean test`
- 前端可在 `198-frontend` 执行 `npm.cmd install` 与 `npm.cmd run build`，执行后按约定清理 `node_modules`
- 源码不再残留批量脚手架的通用包名与通用业务命名
