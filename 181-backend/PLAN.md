# 社区儿童托管签到接送与安全告警系统 开发计划

## 后端
- 完成 `com.childcare` 包结构、统一响应、异常处理、JWT 拦截、Redis Token、CORS 收口
- 完成账号权限、托管班级、儿童档案、监护人档案、签到记录、接送授权、接送记录、安全告警、健康晨检、托管活动、家长通知、事件跟进、操作日志接口
- 完成统计看板接口和 MySQL 初始化脚本

## 前端
- 完成登录、角色首页跳转、动态菜单、通用数据页、业务页面和 ECharts 看板
- 按 ADMIN / CENTER / TEACHER / PARENT / SECURITY / NURSE 收口页面操作权限

## 验收
- 后端 `mvn.cmd clean test` 通过
- 前端 `npm.cmd run build` 通过
- 源码不再残留批量脚手架的通用包名与通用业务命名
