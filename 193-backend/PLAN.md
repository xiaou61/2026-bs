# 校园创新实验班选拔与导师跟踪管理系统 开发计划

## 后端
- 完成 `com.innovationclass` 包结构、统一响应、异常处理、JWT 拦截、Redis Token、CORS 收口
- 完成账号权限、实验班项目、学生档案、导师档案、选拔公告、报名选拔、资格评审、面试考核、导师匹配、培养计划、过程跟踪、成果档案、操作日志接口
- 完成统计看板接口和 MySQL 初始化脚本

## 前端
- 完成登录、角色首页跳转、动态菜单、通用数据页、业务页面和 ECharts 看板
- 按 ADMIN / ACADEMIC / REVIEWER / MENTOR / COUNSELOR / STUDENT 收口页面操作权限

## 验收
- 后端可在 `193-backend` 执行 `mvn.cmd clean test`
- 前端可在 `193-frontend` 执行 `npm.cmd install` 与 `npm.cmd run build`，执行后按约定清理 `node_modules`
- 源码不再残留批量脚手架的通用包名与通用业务命名
