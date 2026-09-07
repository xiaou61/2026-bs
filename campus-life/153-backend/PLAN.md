# 校园二手物品寄卖与信用评价系统 开发计划

## 后端
- 完成 `com.campusresale` 包结构、统一响应、异常处理、JWT 拦截、Redis Token、CORS 收口
- 完成账号权限、物品分类、学生档案、寄卖物品、上架审核、担保订单、担保支付、交接确认、信用评价、违约记录、纠纷申诉、平台公告、操作日志接口
- 完成统计看板接口和 MySQL 初始化脚本

## 前端
- 完成登录、角色首页跳转、动态菜单、通用数据页、业务页面和 ECharts 看板
- 按 ADMIN / OPERATOR / SELLER / BUYER / ARBITER 收口页面操作权限

## 验收
- 后端 `mvn.cmd clean test` 通过
- 前端 `npm.cmd run build` 通过
- 源码不再残留批量脚手架的通用包名与通用业务命名
