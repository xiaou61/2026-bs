# 运动康复训练计划与体测评估管理系统 开发计划

## 后端
- 完成 `com.sportrehab` 包结构、统一响应、异常处理、JWT 拦截、Redis Token、CORS 收口
- 完成账号权限、康复中心、会员档案、教练档案、体测项目、体测评估、风险提醒、训练计划、训练安排、训练打卡、康复反馈、复评记录、操作日志接口
- 完成统计看板接口和 MySQL 初始化脚本

## 前端
- 完成登录、角色首页跳转、动态菜单、通用数据页、业务页面和 ECharts 看板
- 按 ADMIN / CENTER / ASSESSOR / COACH / THERAPIST / MEMBER 收口页面操作权限

## 验收
- 后端可在 `199-backend` 执行 `mvn.cmd clean test`
- 前端可在 `199-frontend` 执行 `npm.cmd install` 与 `npm.cmd run build`，执行后按约定清理 `node_modules`
- 源码不再残留批量脚手架的通用包名与通用业务命名
