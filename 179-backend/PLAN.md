# 高校考勤异常申诉与课堂巡查管理系统 开发计划

## 后端
- 完成 `com.classattendance` 包结构、统一响应、异常处理、JWT 拦截、Redis Token、CORS 收口
- 完成账号权限、教学班级、学生档案、教师档案、课程排课、考勤记录、异常申诉、申诉审核、课堂巡查、巡查问题、整改任务、整改反馈、操作日志接口
- 完成统计看板接口和 MySQL 初始化脚本

## 前端
- 完成登录、角色首页跳转、动态菜单、通用数据页、业务页面和 ECharts 看板
- 按 ADMIN / ACADEMIC / TEACHER / INSPECTOR / COUNSELOR / STUDENT 收口页面操作权限

## 验收
- 后端 `mvn.cmd clean test` 通过
- 前端 `npm.cmd run build` 通过
- 源码不再残留批量脚手架的通用包名与通用业务命名
