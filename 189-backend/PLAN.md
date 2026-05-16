# 乡镇农机作业预约与维修保养跟踪平台 开发计划

## 后端
- 完成 `com.farmmachinery` 包结构、统一响应、异常处理、JWT 拦截、Redis Token、CORS 收口
- 完成账号权限、农机站点、农机档案、农户档案、机手档案、作业预约、机手调度、作业派单、作业回执、维修保养、维修跟踪、季节统计、操作日志接口
- 完成统计看板接口和 MySQL 初始化脚本

## 前端
- 完成登录、角色首页跳转、动态菜单、通用数据页、业务页面和 ECharts 看板
- 按 ADMIN / STATION / DISPATCH / DRIVER / MECHANIC / FARMER 收口页面操作权限

## 验收
- 按仓库规则当前不强制执行编译验证；如需正式交付，可执行后端 `mvn.cmd clean test`
- 如需前端构建验收，可在 `189-frontend` 执行 `npm.cmd install` 与 `npm.cmd run build`，执行后按约定清理 `node_modules`
- 源码不再残留批量脚手架的通用包名与通用业务命名
