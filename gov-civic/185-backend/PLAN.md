# 城市公厕巡检保洁与耗材补给调度系统 开发计划

## 后端
- 完成 `com.citytoilet` 包结构、统一响应、异常处理、JWT 拦截、Redis Token、CORS 收口
- 完成账号权限、公厕点位、保洁路线、保洁任务、保洁记录、巡检计划、巡检记录、耗材库存、补给申请、补给调度、投诉反馈、问题整改、操作日志接口
- 完成统计看板接口和 MySQL 初始化脚本

## 前端
- 完成登录、角色首页跳转、动态菜单、通用数据页、业务页面和 ECharts 看板
- 按 ADMIN / SANITATION / CLEANER / SUPPLY / INSPECTOR / CITIZEN 收口页面操作权限

## 验收
- 后端 `mvn.cmd clean test` 通过
- 前端 `npm.cmd run build` 通过
- 源码不再残留批量脚手架的通用包名与通用业务命名
