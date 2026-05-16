# 在线职业培训证书考试与续证提醒系统 开发计划

## 后端
- 完成 `com.certtraining` 包结构、统一响应、异常处理、JWT 拦截、Redis Token、CORS 收口
- 完成账号权限、培训课程、学员档案、讲师档案、课程报名、学习进度、考试安排、考试成绩、证书发放、证书台账、续证申请、提醒通知、操作日志接口
- 完成统计看板接口和 MySQL 初始化脚本

## 前端
- 完成登录、角色首页跳转、动态菜单、通用数据页、业务页面和 ECharts 看板
- 按 ADMIN / TRAINING / INSTRUCTOR / TRAINEE / EXAMINER / CERTIFIER 收口页面操作权限

## 验收
- 后端 `mvn.cmd clean test` 通过
- 前端 `npm.cmd run build` 通过
- 源码不再残留批量脚手架的通用包名与通用业务命名
