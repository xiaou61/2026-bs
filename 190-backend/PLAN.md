# 智慧楼宇访修协同与设备保养提醒系统 开发计划

## 后端
- 完成 `com.smartbuilding` 包结构、统一响应、异常处理、JWT 拦截、Redis Token、CORS 收口
- 完成账号权限、楼宇档案、设备档案、入驻档案、访修工单、维修派工、保养计划、保养任务、故障预警、巡检记录、备件库存、服务评价、操作日志接口
- 完成统计看板接口和 MySQL 初始化脚本

## 前端
- 完成登录、角色首页跳转、动态菜单、通用数据页、业务页面和 ECharts 看板
- 按 ADMIN / PROPERTY / DISPATCH / TECHNICIAN / INSPECTOR / TENANT 收口页面操作权限

## 验收
- 后端可在 `190-backend` 执行 `mvn.cmd clean test`
- 前端可在 `190-frontend` 执行 `npm.cmd install` 与 `npm.cmd run build`，执行后按约定清理 `node_modules`
- 源码不再残留批量脚手架的通用包名与通用业务命名
