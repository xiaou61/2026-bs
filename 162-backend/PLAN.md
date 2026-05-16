# 生鲜门店临期商品预警与促销处置系统 开发计划

## 后端
- 完成 `com.freshretail` 包结构、统一响应、异常处理、JWT 拦截、Redis Token、CORS 收口
- 完成账号权限、门店档案、供应商档案、生鲜品类、商品批次、保质期规则、临期预警、促销策略、折扣执行、报损记录、库存周转、门店分析、操作日志接口
- 完成统计看板接口和 MySQL 初始化脚本

## 前端
- 完成登录、角色首页跳转、动态菜单、通用数据页、业务页面和 ECharts 看板
- 按 ADMIN / MANAGER / CLERK / STOCK / MARKETING / SUPPLIER 收口页面操作权限

## 验收
- 后端 `mvn.cmd clean test` 通过
- 前端 `npm.cmd run build` 通过
- 源码不再残留批量脚手架的通用包名与通用业务命名
