# 社区助残器具借用与康复随访平台 开发计划

## 后端
- 完成 `com.assistivecare` 包结构、统一响应、异常处理、JWT 拦截、Redis Token、CORS 收口
- 完成账号权限、服务站点、居民档案、助残器具、器具借用、借用审核、器具交付、康复计划、康复训练、随访记录、回收提醒、器具维护、操作日志接口
- 完成统计看板接口和 MySQL 初始化脚本

## 前端
- 完成登录、角色首页跳转、动态菜单、通用数据页、业务页面和 ECharts 看板
- 按 ADMIN / COMMUNITY / AIDSTAFF / THERAPIST / VOLUNTEER / RESIDENT 收口页面操作权限

## 验收
- 后端可在 `191-backend` 执行 `mvn.cmd clean test`
- 前端可在 `191-frontend` 执行 `npm.cmd install` 与 `npm.cmd run build`，执行后按约定清理 `node_modules`
- 源码不再残留批量脚手架的通用包名与通用业务命名
