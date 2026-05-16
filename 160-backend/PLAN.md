# 校园社团活动预算报销与物资借用系统 开发计划

## 后端
- 完成 `com.clubfinance` 包结构、统一响应、异常处理、JWT 拦截、Redis Token、CORS 收口
- 完成账号权限、社团档案、成员档案、活动立项、预算申请、预算明细、审批记录、报销申请、票据归档、物资台账、物资借用、归还验收、操作日志接口
- 完成统计看板接口和 MySQL 初始化脚本

## 前端
- 完成登录、角色首页跳转、动态菜单、通用数据页、业务页面和 ECharts 看板
- 按 ADMIN / UNION / CLUB / TREASURER / WAREHOUSE / MEMBER 收口页面操作权限

## 验收
- 后端 `mvn.cmd clean test` 通过
- 前端 `npm.cmd run build` 通过
- 源码不再残留批量脚手架的通用包名与通用业务命名
