# 药店处方审核与慢病续方提醒管理系统 开发计划

## 后端
- 完成 `com.pharmacycare` 包结构、统一响应、异常处理、JWT 拦截、Redis Token、CORS 收口
- 完成账号权限、药店门店、顾客档案、药品目录、处方登记、处方审核、风险复核、购药记录、用药指导、慢病方案、续方提醒、随访记录、操作日志接口
- 完成统计看板接口和 MySQL 初始化脚本

## 前端
- 完成登录、角色首页跳转、动态菜单、通用数据页、业务页面和 ECharts 看板
- 按 ADMIN / PHARMACY / PHARMACIST / CLERK / FOLLOWUP / CUSTOMER 收口页面操作权限

## 验收
- 后端可在 `196-backend` 执行 `mvn.cmd clean test`
- 前端可在 `196-frontend` 执行 `npm.cmd install` 与 `npm.cmd run build`，执行后按约定清理 `node_modules`
- 源码不再残留批量脚手架的通用包名与通用业务命名
