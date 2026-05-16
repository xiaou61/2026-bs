# 直播基地主播排班与选品样品管理系统 开发计划

## 后端
- 完成 `com.livebase` 包结构、统一响应、异常处理、JWT 拦截、Redis Token、CORS 收口
- 完成账号权限、直播间档案、主播档案、商家档案、商品选品、样品台账、样品借还、主播排班、选品评测、直播计划、直播场次、直播复盘、操作日志接口
- 完成统计看板接口和 MySQL 初始化脚本

## 前端
- 完成登录、角色首页跳转、动态菜单、通用数据页、业务页面和 ECharts 看板
- 按 ADMIN / BASE / ANCHOR / SELECTOR / SAMPLE / MERCHANT 收口页面操作权限

## 验收
- 后端 `mvn.cmd clean test` 通过
- 前端 `npm.cmd run build` 通过
- 源码不再残留批量脚手架的通用包名与通用业务命名
