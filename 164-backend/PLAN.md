# 校园体育赛事报名编排与裁判评分系统 开发计划

## 后端
- 完成 `com.sportevent` 包结构、统一响应、异常处理、JWT 拦截、Redis Token、CORS 收口
- 完成账号权限、体育赛事、参赛队伍、运动员档案、赛事报名、报名分组、赛程编排、场地资源、裁判指派、裁判评分、成绩公示、申诉复核、操作日志接口
- 完成统计看板接口和 MySQL 初始化脚本

## 前端
- 完成登录、角色首页跳转、动态菜单、通用数据页、业务页面和 ECharts 看板
- 按 ADMIN / ORGANIZER / COACH / ATHLETE / REFEREE / COMMITTEE 收口页面操作权限

## 验收
- 后端 `mvn.cmd clean test` 通过
- 前端 `npm.cmd run build` 通过
- 源码不再残留批量脚手架的通用包名与通用业务命名
