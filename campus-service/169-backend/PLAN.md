# 校车乘车预约与学生上下车核验系统 开发计划

## 后端
- 完成 `com.schoolbus` 包结构、统一响应、异常处理、JWT 拦截、Redis Token、CORS 收口
- 完成账号权限、校车线路、站点档案、车辆档案、司机档案、学生档案、家长档案、乘车预约、上车核验、下车核验、异常告警、家长通知、操作日志接口
- 完成统计看板接口和 MySQL 初始化脚本

## 前端
- 完成登录、角色首页跳转、动态菜单、通用数据页、业务页面和 ECharts 看板
- 按 ADMIN / SCHOOL / DISPATCH / DRIVER / TEACHER / GUARDIAN 收口页面操作权限

## 验收
- 后端 `mvn.cmd clean test` 通过
- 前端 `npm.cmd run build` 通过
- 源码不再残留批量脚手架的通用包名与通用业务命名
