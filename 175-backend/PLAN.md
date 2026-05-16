# 校园图书漂流借阅与读书打卡平台 开发计划

## 后端
- 完成 `com.bookdrift` 包结构、统一响应、异常处理、JWT 拦截、Redis Token、CORS 收口
- 完成账号权限、漂流书架、漂流图书、读者档案、图书捐赠、借阅记录、归还流转、读书打卡、读书笔记、积分勋章、阅读活动、消息通知、操作日志接口
- 完成统计看板接口和 MySQL 初始化脚本

## 前端
- 完成登录、角色首页跳转、动态菜单、通用数据页、业务页面和 ECharts 看板
- 按 ADMIN / LIBRARY / CURATOR / STUDENT / CLUB / TEACHER 收口页面操作权限

## 验收
- 后端 `mvn.cmd clean test` 通过
- 前端 `npm.cmd run build` 通过
- 源码不再残留批量脚手架的通用包名与通用业务命名
