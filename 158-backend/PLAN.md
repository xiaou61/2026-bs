# 校外培训机构课消统计与退费审批系统 开发计划

## 后端
- 完成 `com.trainingrefund` 包结构、统一响应、异常处理、JWT 拦截、Redis Token、CORS 收口
- 完成账号权限、校区档案、课程产品、学员档案、教师档案、班级台账、排课计划、上课考勤、课消记录、退费申请、退费审批、财务流水、操作日志接口
- 完成统计看板接口和 MySQL 初始化脚本

## 前端
- 完成登录、角色首页跳转、动态菜单、通用数据页、业务页面和 ECharts 看板
- 按 ADMIN / PRINCIPAL / ACADEMIC / TEACHER / FINANCE / PARENT 收口页面操作权限

## 验收
- 后端 `mvn.cmd clean test` 通过
- 前端 `npm.cmd run build` 通过
- 源码不再残留批量脚手架的通用包名与通用业务命名
