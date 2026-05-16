# 物流园区车辆入场预约与道口调度平台 开发计划

## 后端
- 完成 `com.logisticspark` 包结构、统一响应、异常处理、JWT 拦截、Redis Token、CORS 收口
- 完成账号权限、承运商档案、车辆档案、司机档案、入场预约、时段计划、门岗核验、排队叫号、道口资源、道口分配、装卸任务、周转统计、操作日志接口
- 完成统计看板接口和 MySQL 初始化脚本

## 前端
- 完成登录、角色首页跳转、动态菜单、通用数据页、业务页面和 ECharts 看板
- 按 ADMIN / DISPATCHER / GATEKEEPER / YARDMASTER / CARRIER 收口页面操作权限

## 验收
- 后端 `mvn.cmd clean test` 通过
- 前端 `npm.cmd run build` 通过
- 源码不再残留批量脚手架的通用包名与通用业务命名
